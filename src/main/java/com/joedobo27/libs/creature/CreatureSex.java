package com.joedobo27.libs.creature;

import java.util.Arrays;
import java.util.Objects;

public enum CreatureSex {
    NEUTRAL(-1),
    MALE(0),
    FEMALE(1);

    private int id;

    CreatureSex(int id){
        this.id = id;
    }

    public byte getId() {
        return (byte) id;
    }

    static CreatureSex getSex(String name) {
        return Arrays.stream(values()).filter(creatureSex ->
                Objects.equals(name.toLowerCase(), creatureSex.name().toLowerCase()))
                .findFirst()
                .orElse(NEUTRAL);
    }
}
