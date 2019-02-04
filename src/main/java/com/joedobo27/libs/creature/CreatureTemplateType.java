package com.joedobo27.libs.creature;

import com.joedobo27.libs.item.ItemTemplateType;
import com.joedobo27.libs.jdbCommon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum CreatureTemplateType {
    SENTINEL(0,""),
    TRADER(1,""),
    MOVERANDOM(2,""),
    ANIMAL(3,""),
    INVULNERABLE(4,""),
    NPC_TRADER(5,""),
    AGG_HUMAN(6,""),
    MOVE_LOCAL(7,""),
    MOVE_GLOBAL(8,""),
    GRAZER(9,""),
    HERD(10,""),
    VILLAGE_GUARD(11,""),
    SWIMMING(12,""),
    HUNTING(13,""),
    LEADABLE(14,""),
    MILK(15,""),
    MONSTER(16,""),
    HUMAN(17,""),
    REGENERATING(18,""),
    DRAGON(19,""),
    UNIQUE(20,""),
    KINGDOM_GUARD(21,""),
    GHOST(22,""),
    SPIRIT_GUARD(23,""),
    DEFEND_KINGDOM(24,""),
    AGG_WHITIE(25,""),
    BARTENDER(26,""),
    OMNIVORE(27,""),
    HERBIVORE(28,""),
    CARNIVORE(29,""),
    CLIMBER(30,""),
    REBORN(31,""),
    DOMINATABLE(32,""),
    UNDEAD(33,""),
    CAVEDWELLER(34,""),
    FLEEING(35,""),
    DETECTINVIS(36,""),
    SUBMERGED(37,""),
    FLOATING(38,""),
    NON_NEWBIE(39,""),
    FENCEBREAKER(40,""),
    VEHICLE(41,""),
    HORSE(42,""),
    DOMESTIC(43,""),
    CAREFUL(44,""),
    OPENDOORS(45,""),
    TOWERBASHER(46,""),
    ONLYATTACKPLAYERS(47,""),
    NOATTACKVEHICLE(48,""),
    PREY(49,""),
    VALREI(50,""),
    BEACH(51,""),
    WOOL(52,""),
    WARGUARD(53,""),
    BLACK_OR_WHITE(54,""),
    BURNING(55,""),
    RIFT(56,""),
    STEALTH(57,""),
    CASTER(58,""),
    SUMMONER(59,""),
    MISSION_OK(60,""),
    MISSION_TRAITOR_OK(61,""),
    NO_REBIRTH(62,""),
    BABY(63,"");

    int id;
    String description;

    CreatureTemplateType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * @return String formated so all lowercase, spaces removed, _ removed.
     */
    public String getName() {
        return this.name().toLowerCase().replaceAll("_", "").replaceAll(" ", "");
    }

    public static CreatureTemplateType[] getFromStrings(String[] names, String fileName) {
        IntStream.range(0, names.length)
                .filter(operand -> Arrays.stream(values())
                        .noneMatch(creatureTemplateType ->
                                Objects.equals(creatureTemplateType.getName(), formatName(names[operand]))))
                .forEach(operand -> jdbCommon.logger.warning(String.format("Creature template type %s isn't valid for %s.",
                        names[operand], fileName)));

        ArrayList<CreatureTemplateType> creatureTemplateTypes = Arrays.stream(values())
                .filter(creatureTemplateType -> IntStream.range(0, names.length)
                        .anyMatch(operand -> Objects.equals(creatureTemplateType.getName(), formatName(names[operand]))))
                .collect(Collectors.toCollection(ArrayList::new));
        CreatureTemplateType[] toReturn = new CreatureTemplateType[creatureTemplateTypes.size()];
        return creatureTemplateTypes.size() < 1 ? null : creatureTemplateTypes.toArray(toReturn);
    }

    private static String formatName(String name) {
        return name.toLowerCase()
                .replaceAll("_", "")
                .replaceAll(" ", "")
                .replaceAll("'", "")
                .replaceAll("-", "");
    }

    public static int[] creatureTypesToInts(CreatureTemplateType[] types) {
        int[] toReturn = new int[types.length];
        IntStream.range(0, types.length).forEach(
                value -> toReturn[value] = types[value].id);
        return toReturn;
    }
}
