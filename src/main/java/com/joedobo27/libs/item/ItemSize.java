package com.joedobo27.libs.item;

import javax.json.JsonObject;
import java.util.Arrays;
import java.util.Objects;

public enum ItemSize {
    TINY(1),
    SMALL(2),
    LARGE(3),
    HUGE(4);

    private int id;

    ItemSize(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name().toLowerCase().replace("_", " ")
                .replace("0", "-");
    }

    static public ItemSize getFromString(String itemSize) throws RuntimeException{
        return Arrays.stream(values())
                .filter(skill -> Objects.equals(skill.getName(), itemSize))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
