// 
// Decompiled by Procyon v0.5.30
// 

package com.joedobo27.libs;

import com.wurmonline.server.items.WurmColor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ColorDefinitions
{
    public static final int COLOR_ID_SYSTEM = 100;
    public static final int COLOR_ID_ERROR = 101;
    public static final int COLOR_ID_WHITE = 0;
    public static final int COLOR_ID_BLACK = 1;
    public static final int COLOR_ID_NAVY_BLUE = 2;
    public static final int COLOR_ID_GREEN = 3;
    public static final int COLOR_ID_RED = 4;
    public static final int COLOR_ID_MAROON = 5;
    public static final int COLOR_ID_PURPLE = 6;
    public static final int COLOR_ID_ORANGE = 7;
    public static final int COLOR_ID_YELLOW = 8;
    public static final int COLOR_ID_LIME = 9;
    public static final int COLOR_ID_TEAL = 10;
    public static final int COLOR_ID_CYAN = 11;
    public static final int COLOR_ID_ROYAL_BLUE = 12;
    public static final int COLOR_ID_FUCHSIA = 13;
    public static final int COLOR_ID_GREY = 14;
    public static final int COLOR_ID_SILVER = 15;
    public static final int COLOR_ID_NONE = -1;

    private static HashMap<Integer, float[]> mapColors = new HashMap<>(19);

    private static void initializeColors() {
        addNewColor(COLOR_ID_SYSTEM, new float[] { 0.5f, 1.0f, 0.5f });
        addNewColor(COLOR_ID_ERROR, new float[] { 1.0f, 0.3f, 0.3f });
        addNewColor(COLOR_ID_WHITE, new float[] { 1.0f, 1.0f, 1.0f });
        addNewColor(COLOR_ID_BLACK, new float[] { 0.0f, 0.0f, 0.0f });
        addNewColor(COLOR_ID_NAVY_BLUE, new float[] { 0.23f, 0.39f, 1.0f });
        addNewColor(COLOR_ID_GREEN, new float[] { 0.08f, 1.0f, 0.08f });
        addNewColor(COLOR_ID_RED, new float[] { 1.0f, 0.0f, 0.0f });
        addNewColor(COLOR_ID_MAROON, new float[] { 0.5f, 0.0f, 0.0f });
        addNewColor(COLOR_ID_PURPLE, new float[] { 0.5f, 0.0f, 0.5f });
        addNewColor(COLOR_ID_ORANGE, new float[] { 1.0f, 0.85f, 0.24f });
        addNewColor(COLOR_ID_YELLOW, new float[] { 1.0f, 1.0f, 0.0f });
        addNewColor(COLOR_ID_LIME, new float[] { 0.0f, 1.0f, 0.0f });
        addNewColor(COLOR_ID_TEAL, new float[] { 0.0f, 0.5f, 0.5f });
        addNewColor(COLOR_ID_CYAN, new float[] { 0.0f, 1.0f, 1.0f });
        addNewColor(COLOR_ID_ROYAL_BLUE, new float[] { 0.23f, 0.39f, 1.0f });
        addNewColor(COLOR_ID_FUCHSIA, new float[] { 1.0f, 0.0f, 1.0f });
        addNewColor(COLOR_ID_GREY, new float[] { 0.5f, 0.5f, 0.5f });
        addNewColor(COLOR_ID_SILVER, new float[] { 0.75f, 0.75f, 0.75f });
    }

    synchronized static void addNewColor(@NotNull int colorId, @NotNull float[] rgbPercentages) {
        mapColors.put(colorId, rgbPercentages);
    }

    public static float[] getColor(int colorCode) {
        return mapColors.get(colorCode);
    }

    public static int encodeRgbToInt(int colorDefinitionsId) {
        float[] rgbPercents;
        if (!mapColors.containsKey(colorDefinitionsId))
            rgbPercents = mapColors.get(COLOR_ID_BLACK);
        else
            rgbPercents = mapColors.get(colorDefinitionsId);
        return WurmColor.createColor((int)(rgbPercents[0] * 255), (int)(rgbPercents[1] * 255), (int)(rgbPercents[2] * 255));
    }

    static {
        initializeColors();
    }
}
