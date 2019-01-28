package com.joedobo27.libs.item;

import java.util.Arrays;
import java.util.Objects;

public enum ItemSize {
    TINY(1),
    SMALL(2),
    NORMAL(3),
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
                .filter(itemSize1 -> Objects.equals(itemSize1.getName(),itemSize.toLowerCase()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
