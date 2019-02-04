package com.joedobo27.libs.creature;

import com.joedobo27.libs.Skill;

import java.util.Arrays;
import java.util.Objects;

public enum CombatDamageType {

    NONE(-1, ""),
    CRUSH(0, ""),
    SLASH(1, ""),
    PIERCE(2, ""),
    BITE(3, ""),
    BURN(4, ""),
    POISON(5, ""),
    INFECTION(6, ""),
    WATER(7, ""),
    COLD(8, ""),
    INTERNAL(9, ""),
    ACID(10, "");

    private int id;
    private String description;

    CombatDamageType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getName() {
        return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }

    public static CombatDamageType getFromString(String damageName) {
        return Arrays.stream(values())
                .filter(combatDamageType -> Objects.equals(combatDamageType.getName(), damageName.toLowerCase()))
                .findFirst()
                .orElse(NONE);
    }

    public byte getId() {
        return (byte)id;
    }
}
