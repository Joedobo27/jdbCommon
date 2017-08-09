// 
// Decompiled by Procyon v0.5.30
// 

package com.joedobo27.libs;

@SuppressWarnings("unused")
public enum ActionTypes {
    ACTION_QUICK(0,""),
    ACTION_NEED_FOOD(1,"Action blocked if food is too low."),
    ACTION_SPELL(2,""),
    ACTION_ATTACK(3,""),
    ACTION_FATIGUE(4,""),
    ACTION_POLICED(5,""),
    ACTION_NOMOVE(6,"Actions is cancelled if toon moves."),
    ACTION_NON_LIBILAPRIEST(7,""),
    ACTION_NON_WHITEPRIEST(8,""),
    ACTION_NON_RELIGION(9,"Doing Actions are considered unfaithful."),
    ACTION_ATTACK_HIGH(12,""),
    ACTION_ATTACK_LOW(13,""),
    ACTION_ATTACK_LEFT(14,""),
    ACTION_ATTACK_RIGHT(15,""),
    ACTION_DEFEND(16,""),
    ACTION_STANCE_CHANGE(17,""),
    ACTION_ALLOW_MAGRANON(18,""),
    ACTION_ALLOW_FO(19,""),
    ACTION_ALLOW_VYNORA(20,""),
    ACTION_ALLOW_LIBILA(21,""),
    ACTION_NO_OPPORTUNITY(22,""),
    ACTION_IGNORERANGE(23,""),
    ACTION_VULNERABLE(24,""),
    ACTION_MISSION(25,""),
    ACTION_NOTVULNERABLE(26,""),
    ACTION_NONSTACKABLE(27,""),
    ACTION_NONSTACKABLE_FIGHT(28,""),
    ACTION_BLOCKED_NONE(29,""),
    ACTION_BLOCKED_FENCE(30,""),
    ACTION_BLOCKED_WALL(31,""),
    ACTION_BLOCKED_FLOOR(32,""),
    ACTION_BLOCKED_ALL_BUT_OPEN(33,""),
    ACTION_BLOCKED_TARGET_TILE(34,""),
    ACTION_MAYBE_USE_ACTIVE_ITEM(35,""),
    ACTION_ALWAYS_USE_ACTIVE_ITEM(36,""),
    ACTION_NEVER_USE_ACTIVE_ITEM(37,""),
    ACTION_ALLOW_MAGRANON_IN_CAVE(38,""),
    ACTION_ALLOW_FO_ON_SURFACE(39,""),
    ACTION_ALLOW_LIBILA_IN_CAVE(40,""),
    ACTION_USES_NEW_SKILL_SYSTEM(41,""),
    ACTION_VERIFIED_NEW_SKILL_SYSTEM(42,""),
    ACTION_SHOW_ON_SELECT_BAR(43,"Show action's icon on bar. Custom actions don't have icons and require client mod to do."),
    ACTION_SAME_BRIDGE(44,""),
    ACTION_PERIMETER(45,""),
    ACTION_CORNER(46,""),
    ACTION_ENEMY_NEVER(47,""),
    ACTION_ENEMY_ALWAYS(48,""),
    ACTION_ENEMY_NO_GUARDS(49,""),
    ACTION_BLOCKED_NOT_DOOR(50,"");

    private final byte id;
    private final String description;

    ActionTypes(int aId, String aDescription){
        this.id = (byte)aId;
        this.description = aDescription;
    }

    public byte getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
