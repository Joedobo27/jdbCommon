package com.joedobo27.libs.creature;

import com.joedobo27.libs.item.ItemSize;
import org.json.JSONException;

import java.util.Arrays;
import java.util.Objects;

public enum BodyType {
    HUMAN(0, ""),
    HORSE(1, ""),
    BEAR(2, ""),
    DOG(3, ""),
    ETTIN(4, ""),
    CYCLOPS(5, ""),
    DRAGON(6, ""),
    BIRD(7, ""),
    SPIDER(8, ""),
    SNAKE(9, "");

    private int id;
    private String description;

    BodyType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public byte getId() {
        return (byte)id;
    }

    private String getName() {
        return this.name().toLowerCase().replace("_", " ")
                .replace("0", "-");
    }

    static public BodyType getFromString(String bodyType) throws JSONException {
        return Arrays.stream(values())
                .filter(bodyType1 -> Objects.equals(bodyType1.getName(),bodyType.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new JSONException(String.format("Body type %s not found.", bodyType)));
    }

}
