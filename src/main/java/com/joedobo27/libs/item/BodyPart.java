package com.joedobo27.libs.item;

import com.joedobo27.libs.jdbCommon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum BodyPart {

    HEAD(1),
    TORSO(2),
    LEFT_ARM(3),
    RIGHT_ARM(4),
    LEFT_OVER_ARM(5),
    RIGHT_OVER_ARM(6),
    LEFT_THIGH(7),
    RIGHT_THIGH(8),
    LEFT_UNDER_ARM(9),
    RIGHT_UNDER_ARM(10),
    LEFT_CALF(11),
    RIGHT_CALF(12),
    LEFT_HAND(13),
    RIGHT_HAND(14),
    LEFT_FOOT(15),
    RIGHT_FOOT(16),
    NECK(17),
    LEFT_EYE(18),
    RIGHT_EYE(19),
    CENTER_EYE(20),
    CHEST(21),
    TOP_BACK(22),
    STOMACH(23),
    LOWER_BACK(24),
    CROTCH(25),
    LEFT_SHOULDER(26),
    RIGHT_SHOULDER(27),
    SECOND_HEAD(28),
    FACE(29),
    LEFT_LEG(30),
    RIGHT_LEG(31),
    HIP(32),
    BASE_OF_NOSE(33),
    LEGS(34);

    private byte id;

    BodyPart(int id){
        this.id = (byte)id;
    }

    public byte getId() {
        return id;
    }

    public String getName() {
        return this.name().toLowerCase().replace("_", " ")
                .replace("0", "-");
    }

    private static String formatName(String name) {
        return name.toLowerCase()
                .replaceAll("_", "")
                .replaceAll(" ", "")
                .replaceAll("'", "")
                .replaceAll("-", "");
    }

    public static BodyPart[] getFromStrings(String[] partNames, String fileName) {
        IntStream.range(0, partNames.length)
                .filter(operand -> Arrays.stream(values())
                        .noneMatch(bodyPart -> Objects.equals(bodyPart.getName(), formatName(partNames[operand]))))
                .forEach(operand -> jdbCommon.logger.warning(String.format("Body part %s isn't valid for %s.",
                        partNames[operand], fileName)));

        ArrayList<BodyPart> bodyParts = Arrays.stream(values())
                .filter(bodyPart1 -> IntStream.range(0, partNames.length)
                        .anyMatch(operand -> Objects.equals(bodyPart1.getName(), formatName(partNames[operand]))))
                .collect(Collectors.toCollection(ArrayList::new));
        BodyPart[] toReturn = new BodyPart[bodyParts.size()];
        return bodyParts.toArray(toReturn);
    }

    public static byte[] bodyPartsTobytes(BodyPart[] bodyParts) {
        byte[] toReturn = new byte[bodyParts.length];
        IntStream.range(0, bodyParts.length).forEach(value -> toReturn[value] = bodyParts[value].getId());
        return toReturn;
    }

    public static BodyPart[] EMPTY = new BodyPart[0];
}
