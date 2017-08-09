package com.joedobo27.libs;


import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.RuneUtilities;
import com.wurmonline.server.skills.NoSuchSkillException;
import com.wurmonline.server.skills.Skill;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;


public class ActionUtilities {

    static public void maxRangeReflect(ActionEntry actionEntry, Integer maxMeters, Logger logger) {
        try {
            ReflectionUtil.setPrivateField(actionEntry,
                    ReflectionUtil.getField(Class.forName("com.wurmonline.server.behaviours.ActionEntry"), "maxRange"),
                    maxMeters);
        }catch (ClassNotFoundException | NoSuchFieldException |IllegalAccessException e){
            logger.warning(e.getMessage());
        }
    }

    /**
     * It shouldn't be necessary to have a fantastic, 104woa, speed rune, 99ql, 99 skill in order to get the fastest time.
     * Aim for just skill as getting close to shortest time and the other boosts help at lower levels but aren't needed to have
     * the best at end game.
     */
    static public double getActionTime(int minSkill, int maxSkill, int longestTime, int shortestTime,
                                       @Nullable Item activeTool, Creature performer, Action action, int actionSkillId) {
        final double MAX_WOA_EFFECT = 0.20;
        final double TOOL_RARITY_EFFECT = 0.1;
        final double ACTION_RARITY_EFFECT = 0.33;
        // CONFIGURE SCALING METRIC
        // need a y = mx + b where x will be the modified skill.
        double slope = (longestTime - shortestTime) / (minSkill - maxSkill);
        double yIntercept = minSkill - (slope * maxSkill);
        double time;
        // Is there an active tool and does it have a primary skill?
        Skill toolSkill = null;
        double bonus = 0;
        if (activeTool != null && activeTool.hasPrimarySkill()) {
            try {
                toolSkill = performer.getSkills().getSkillOrLearn(activeTool.getPrimarySkill());
            } catch (NoSuchSkillException ignore) {}
        }
        if (toolSkill != null) {
            bonus = toolSkill.getKnowledge() / 10;
        }

        // Get skill value modified by tool quality and any bonuses.
        double modifiedKnowledge = performer.getSkills().getSkillOrLearn(actionSkillId).getKnowledge(activeTool,
               bonus);
        time = (modifiedKnowledge * slope) + yIntercept;
        time = Math.min(longestTime, time);
        // woa
        if (activeTool != null && activeTool.getSpellSpeedBonus() > 0.0f)
            time = Math.max(shortestTime, time * (1 - (MAX_WOA_EFFECT * activeTool.getSpellSpeedBonus() / 100.0)));
        // rare item, 10% speed reduction per rarity level.
        if (activeTool != null && activeTool.getRarity() > 0)
            time = Math.max(shortestTime, time * (1 - (activeTool.getRarity() * TOOL_RARITY_EFFECT)));
        // rare action, 33% speed reduction per rarity level.
        if (action.getRarity() > 0)
            time = Math.max(shortestTime, time * (1 - (action.getRarity() * ACTION_RARITY_EFFECT)));
        // rune effects
        if (activeTool != null && activeTool.getSpellEffects() != null && activeTool.getSpellEffects().getRuneEffect() != -10L)
            time = Math.max(shortestTime, time * (1 - RuneUtilities.getModifier(activeTool.getSpellEffects().getRuneEffect(),
                    RuneUtilities.ModifierEffect.ENCH_USESPEED)));
        return time;
    }

}
