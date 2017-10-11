package com.joedobo27.libs.action;

import com.joedobo27.libs.LinearScalingFunction;
import com.wurmonline.math.TilePos;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.RuneUtilities;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.Skill;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import com.wurmonline.shared.util.MaterialUtilities;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ActionMaster {

    protected final Action action;
    protected final Creature performer;
    protected final @Nullable Item activeTool;
    protected final @Nullable Integer usedSkill;
    protected final int minSkill;
    protected final int maxSkill;
    protected final int longestTime;
    protected final int shortestTime;
    protected final int minimumStamina;
    protected int actionTimeTenthSecond;

    protected ActionMaster(Action action, Creature performer, @Nullable Item activeTool, @Nullable Integer usedSkill, int minSkill, int maxSkill, int longestTime,
                           int shortestTime, int minimumStamina) {
        this.action = action;
        this.performer = performer;
        this.activeTool = activeTool;
        this.usedSkill = usedSkill;
        this.minSkill = minSkill;
        this.maxSkill = maxSkill;
        this.longestTime = longestTime;
        this.shortestTime = shortestTime;
        this.minimumStamina = minimumStamina;
    }

    public boolean isActionStartTime(float counter){
        return counter == 1.0f;
    }

    public boolean isActionTimedOut(Action action, float counter) {
        return counter-1 >= action.getTimeLeft() / 10.0f;
    }

    public void doActionStartMessages(String you, String broadcast) {
        this.performer.getCommunicator().sendNormalServerMessage(you);
        Server.getInstance().broadCastAction(broadcast, this.performer, 5);
    }

    public void doActionStartMessages() {
        String youMessage = String.format("You start %s.", this.action.getActionString());
        this.performer.getCommunicator().sendNormalServerMessage(youMessage);
        String broadcastMessage = String.format("%s starts to %s.", this.performer.getName(), this.action.getActionString());
        Server.getInstance().broadCastAction(broadcastMessage, this.performer, 5);
    }

    /**
     * It shouldn't be necessary to have a fantastic, 104woa, speed rune, 99ql, 99 skill in order to get the fastest time.
     * Aim for just skill as getting close to shortest time and the other boosts help at lower levels but aren't needed to have
     * the best at end game.
     */
    public void setInitialTime(ActionEntry actionEntry) {
        double MAX_WOA_EFFECT = 0.20;
        double TOOL_RARITY_EFFECT = 0.10;
        double ACTION_RARITY_EFFECT = 0.33;

        Skill toolSkill = null;
        double bonus = 0;
        if (this.activeTool != null && this.activeTool.hasPrimarySkill()) {
            try {
                toolSkill = this.performer.getSkills().getSkillOrLearn(this.activeTool.getPrimarySkill());
            } catch (NoSuchSkillException ignore) {}
        }
        if (toolSkill != null) {
            bonus = toolSkill.getKnowledge() / 10;
        }

        double modifiedKnowledge;
        if (this.usedSkill == null)
            modifiedKnowledge = 99;
        else
            modifiedKnowledge = this.performer.getSkills().getSkillOrLearn(this.usedSkill).getKnowledge(this.activeTool,
                bonus);
        LinearScalingFunction linearScalingFunction = LinearScalingFunction.make(this.minSkill, this.maxSkill,
                this.longestTime, this.shortestTime);
        double time = linearScalingFunction.doFunctionOfX(modifiedKnowledge);

        if (this.activeTool != null && this.activeTool.getSpellSpeedBonus() != 0.0f)
            time = Math.max(this.shortestTime, time * (1 - (MAX_WOA_EFFECT *
                    this.activeTool.getSpellSpeedBonus() / 100.0)));

        if (this.activeTool != null && this.activeTool.getRarity() != MaterialUtilities.COMMON)
            time = Math.max(this.shortestTime, time * (1 - (this.activeTool.getRarity() *
                    TOOL_RARITY_EFFECT)));

        if (this.action != null && this.action.getRarity() != MaterialUtilities.COMMON)
            time = Math.max(this.shortestTime, time * (1 - (this.action.getRarity() * ACTION_RARITY_EFFECT)));

        if (this.activeTool != null && this.activeTool.getSpellEffects() != null &&
                this.activeTool.getSpellEffects().getRuneEffect() != -10L)
            time = Math.max(this.shortestTime, time * (1 -
                    RuneUtilities.getModifier(this.activeTool.getSpellEffects().getRuneEffect(),
                    RuneUtilities.ModifierEffect.ENCH_USESPEED)));

        int timeInt = (int)time;
        if (this.action != null)
            this.action.setTimeLeft(timeInt);
        this.performer.sendActionControl(actionEntry.getVerbString(), true, timeInt);

        synchronized (this){
            this.actionTimeTenthSecond = timeInt;
        }
    }

    public void doActionEndMessages() {
        String youMessage = String.format("You finish %s.", this.action.getActionString());
        this.performer.getCommunicator().sendNormalServerMessage(youMessage);
        String broadcastMessage = String.format("%s finishes to %s.", this.performer.getName(), this.action.getActionString());
        Server.getInstance().broadCastAction(broadcastMessage, this.performer, 5);
    }

    protected @Nullable Item[] getInventoryItems(int findItemTemplateId) {
        Item[] inventoryItems = this.performer.getInventory().getItemsAsArray();
        Item[] itemsAll = Arrays.stream(inventoryItems)
                .filter(item -> item.getTemplateId() == findItemTemplateId)
                .toArray(Item[]::new);
        if (itemsAll == null || itemsAll.length == 0)
            return null;
        return itemsAll;
    }

    protected @Nullable Item[] getGroundItems(int findItemTemplateId, TilePos targetedTile) {
        TilePos[] tilesToCheck = {targetedTile, targetedTile.West(), targetedTile.NorthWest(), targetedTile.North()};
        ArrayList<Item[]> itemsGrouped = new ArrayList<>();
        Arrays.stream(tilesToCheck)
                .forEach(tilePos -> {
                    VolaTile volaTile = Zones.getTileOrNull(tilePos, this.performer.isOnSurface());
                    if (volaTile == null)
                        return;
                    Item[] items = volaTile.getItems();
                    if (items == null || items.length == 0)
                        return;
                    itemsGrouped.add(items);
                });
        if (itemsGrouped.size() == 0)
            return null;
        Item[] itemsUnfiltered = itemsGrouped.stream().flatMap(Arrays::stream).toArray(Item[]::new);
        Item[] itemsAll = Arrays.stream(itemsUnfiltered)
                .filter(item -> item.getTemplateId() == findItemTemplateId)
                .toArray(Item[]::new);
        if (itemsAll == null || itemsAll.length == 0)
            return null;
        return itemsAll;
    }

    public Action getAction() {
        return action;
    }

    public Creature getPerformer() {
        return performer;
    }

    abstract public Item getActiveTool();

    public int getActionId() {
        return this.action.getNumber();
    }

    public int getMinimumStamina() {
        return minimumStamina;
    }

    public int getActionTimeTenthSecond() {
        return actionTimeTenthSecond;
    }

    static public void setActionEntryMaxRangeReflect(ActionEntry actionEntry, Integer maxMeters, Logger logger) {
        try {
            ReflectionUtil.setPrivateField(actionEntry,
                    ReflectionUtil.getField(Class.forName("com.wurmonline.server.behaviours.ActionEntry"), "maxRange"),
                    maxMeters);
        }catch (ClassNotFoundException | NoSuchFieldException |IllegalAccessException e){
            logger.warning(e.getMessage());
        }
    }
}
