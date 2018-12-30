package com.joedobo27.libs.item;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.joedobo27.libs.item.ItemTemplateType.*;

public enum ItemTemplate {
    NONE("none", -1, new ItemTemplateType[]{ItemTemplateType.NONE}),
    GREEN_APPLE("green apple", 6, new ItemTemplateType[] {FOOD, BULK, FRUIT, NOT_MISSION, LOWNUTRITION, ANY_FRUIT_FOOD_GROUP}),
    HAND("hand", 14, buildTypes(new int[]{28, 8, 1, 48, 210})),
    SHAFT("shaft", 23, buildTypes(new int[]{133, 146, 21, 14, 144, 37, 84, 129, 158, 165})),
    BARLEY("barley", 28, buildTypes(new int[]{146, 103, 20, 5, 55, 129, 1157})),
    WHEAT("wheat", 29, buildTypes(new int[]{146, 102, 20, 5, 55, 129, 1157})),
    RYE("rye", 30, buildTypes(new int[]{20, 146, 5, 55, 129, 1157})),
    OAT("oat", 31, buildTypes(new int[]{20, 146, 5, 55, 129, 1157})),
    CORN("corn", 32, buildTypes(new int[]{146, 102, 20, 5, 55, 129, 217, 29, 212, 223, 1156})),
    PUMPKIN("pumpkin", 33, buildTypes(new int[]{146, 102, 5, 55, 129, 212, 29, 223, 1156})),
    PUMPKIN_SEED("pumpkin seed", 34, buildTypes(new int[]{20, 146, 5, 55, 129})),
    POTATO("potato", 35, buildTypes(new int[]{5, 146, 20, 55, 129, 212, 29, 223, 1156})),
    CAMPFIRE("campfire", 37, buildTypes(new int[]{52, 21, 1, 31, 59, 65, 147, 165, 49, 209})),
    CHEESE_DRILL("cheese drill", 65, buildTypes(new int[]{108, 44, 144, 38, 21, 92, 147, 51, 210})),
    CHEESE("cheese", 66, buildTypes(new int[]{27, 5, 88, 146, 108, 192, 212, 220, 224, 74, 1198})),
    GOAT_CHEESE("goat cheese", 67, buildTypes(new int[]{27, 5, 88, 146, 108, 192, 157, 212, 220, 224, 74, 1198})),
    FETA_CHEESE("feta cheese", 68, buildTypes(new int[]{27, 5, 88, 146, 108, 192, 212, 220, 224, 74, 1198})),
    BUFFALO_CHEESE("buffalo cheese", 69, buildTypes(new int[]{27, 5, 88, 146, 108, 192, 212, 220, 224, 74, 1198})),
    HONEY("honey", 70, buildTypes(new int[]{26, 5, 55, 88})),
    LYE("lye", 73, buildTypes(new int[]{26, 211})),
    FRYING_PAN("frying pan", 75, buildTypes(new int[]{108, 147, 44, 38, 22, 18, 77, 1})),
    POTTERY_JAR("pottery jar", 76, buildTypes(new int[]{108, 30, 1, 33, 92, 215, 48, 211, 77})),
    POTTERY_BOWL("pottery bowl", 77, buildTypes(new int[]{108, 30, 1, 77, 92, 97, 33})),
    MEAT("meat", 92, buildTypes(new int[]{28, 5, 62, 74, 219, 129, 146, 212, 223, 1261})),
    BUTCHERING_KNIFE("butchering knife", 93, buildTypes(new int[]{108, 44, 38, 22, 17, 210, 13, 147})),
    HANDLE("handle", 99, buildTypes(new int[]{21, 146, 113, 211})),
    WATER("water", 128, buildTypes(new int[]{26, 88, 90, 113})),
    COOKED_MEAT("cooked meat", 129, buildTypes(new int[]{28, 146, 5, 212, 74, 219, 157, 1261})),
    CANDLE("candle", 133, buildTypes(new int[]{27, 32, 59, 115, 52, 210})),
    HAZELNUTS("hazelnuts", 134, buildTypes(new int[]{5, 21, 146, 80, 217, 74, 212, 1197})),
    FAT("fat", 140, buildTypes(new int[]{146, 46, 211})),
    MILK("milk", 142, buildTypes(new int[]{26, 88, 90, 113, 191, 212, 1200})),
    COTTON_SEED("cotton seed", 145, buildTypes(new int[]{20, 146, 113, 211, 217})),
    PIKE("pike", 157, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    SMALLMOUTH_BASS("smallmouth bass", 158, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    HERRING("herring", 159, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    CATFISH("catfish", 160, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    SNOOK("snook", 161, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    ROACH("roach", 162, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    PERCH("perch", 163, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    CARP("carp", 164, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    BROOK_TROUT("brook trout", 165, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    WOOD_SCRAP("wood scrap", 169, buildTypes(new int[]{21, 146, 46, 113, 129, 157, 174, 211})),
    LEATHER_PIECES("leather pieces", 172, buildTypes(new int[]{23, 146, 46, 113, 129, 174, 157, 211})),
    PIG_FOOD("pig food", 173, buildTypes(new int[]{5, 146, 46, 55, 113, 129, 157, 174})),
    OVEN("oven", 178, buildTypes(new int[]{108, 135, 1, 31, 25, 51, 86, 52, 59, 44, 147, 176, 199, 180, 209})),
    FORGE("forge", 180, buildTypes(new int[]{108, 135, 1, 31, 25, 51, 86, 52, 59, 44, 147, 176, 180, 209, 199})),
    DOUGH("dough", 200, buildTypes(new int[]{5, 146, 74})),
    FLOUR("flour", 201, buildTypes(new int[]{5, 146, 74, 108})),
    GRINDSTONE("grindstone", 202, buildTypes(new int[]{25, 210, 44})),
    BREAD("bread", 203, buildTypes(new int[]{82, 74, 212, 222, 233, 146})),
    GREEN_MUSHROOM("green mushroom", 246, buildTypes(new int[]{105, 147, 5, 146, 55, 212, 226, 1199})),
    BLACK_MUSHROOM("black mushroom", 247, buildTypes(new int[]{106, 147, 5, 146, 55, 212, 226, 1199})),
    BROWN_MUSHROOM("brown mushroom", 248, buildTypes(new int[]{103, 147, 5, 146, 55, 212, 226, 1199})),
    YELLOW_MUSHROOM("yellow mushroom", 249, buildTypes(new int[]{104, 147, 5, 146, 55, 212, 226, 1199})),
    BLUE_MUSHROOM("blue mushroom", 250, buildTypes(new int[]{104, 147, 5, 146, 55, 212, 226, 1199})),
    RED_MUSHROOM("red mushroom", 251, buildTypes(new int[]{5, 146, 147, 79, 55, 212, 226, 1199})),
    SPOON("spoon", 257, buildTypes(new int[]{108, 22, 147, 44, 210, 87})),
    KNIFE("knife", 258, buildTypes(new int[]{108, 22, 147, 44, 210, 87})),
    FORK("fork", 259, buildTypes(new int[]{108, 22, 147, 44, 210, 87})),
    CORPSE("corpse", 272, buildTypes(new int[]{1, 60, 28, 48, 52, 54, 63, 112, 211, 237})),
    OPEN_HELM("open helm", 287, buildTypes(new int[]{108, 44, 22, 4, 77, 92, 147, 1, 33})),
    TOOTH("tooth", 303, buildTypes(new int[]{105, 62, 129, 46, 146, 211})),
    HORN("horn", 304, buildTypes(new int[]{104, 23, 62, 129, 46, 146, 211})),
    HOOF("hoof", 306, buildTypes(new int[]{103, 62, 129, 46, 146, 211})),
    TAIL("tail", 307, buildTypes(new int[]{103, 62, 129, 46, 146, 211})),
    EYE("eye", 308, buildTypes(new int[]{104, 28, 62, 5, 55, 129, 46, 146})),
    BLADDER("bladder", 309, buildTypes(new int[]{103, 62, 129, 46, 146, 211})),
    GLAND("gland", 310, buildTypes(new int[]{106, 62, 129, 46, 146, 211})),
    TWISTED_HORN("twisted horn", 311, buildTypes(new int[]{106, 62, 129, 46, 146, 211})),
    LONG_HORN("long horn", 312, buildTypes(new int[]{104, 62, 129, 46, 146, 211})),
    STEW("stew", 345, buildTypes(new int[]{5, 219, 26, 74, 233, 108})),
    CASSEROLE("casserole", 346, buildTypes(new int[]{82, 76, 219, 222, 233})),
    MEAL("meal", 347, buildTypes(new int[]{82, 76, 219, 222, 233})),
    GULASCH("gulasch", 348, buildTypes(new int[]{5, 75, 26, 219, 233, 108})),
    SALT("salt", 349, buildTypes(new int[]{5, 146, 55})),
    SAUCE_PAN("sauce pan", 350, buildTypes(new int[]{108, 44, 22, 18, 77, 1, 147, 33})),
    CAULDRON("cauldron", 351, buildTypes(new int[]{108, 147, 44, 22, 77, 1, 33})),
    SOUP("soup", 352, buildTypes(new int[]{5, 219, 55, 26, 90, 233, 108})),
    LOVAGE("lovage", 353, buildTypes(new int[]{146, 105, 5, 78, 55, 212, 206, 221, 1158})),
    SAGE("sage", 354, buildTypes(new int[]{146, 104, 5, 55, 78, 212, 206, 221, 1158})),
    ONION("onion", 355, buildTypes(new int[]{146, 102, 5, 55, 29, 212, 20, 223, 1156})),
    GARLIC("garlic", 356, buildTypes(new int[]{146, 103, 5, 29, 20, 212, 55, 223, 1156})),
    OREGANO("oregano", 357, buildTypes(new int[]{5, 146, 78, 212, 55, 206, 221, 1158})),
    PARSLEY("parsley", 358, buildTypes(new int[]{146, 102, 5, 55, 78, 212, 206, 221, 1158})),
    BASIL("basil", 359, buildTypes(new int[]{5, 146, 55, 78, 212, 206, 221, 1158})),
    THYME("thyme", 360, buildTypes(new int[]{5, 146, 78, 55, 212, 206, 221, 1158})),
    BELLADONNA("belladonna", 361, buildTypes(new int[]{5, 146, 79, 78, 55, 212, 206, 221, 1158})),
    STRAWBERRIES("strawberries", 362, buildTypes(new int[]{5, 146, 80, 46, 55, 212, 1179})),
    ROSEMARY("rosemary", 363, buildTypes(new int[]{146, 105, 5, 78, 55, 212, 206, 221, 1158})),
    BLUEBERRY("blueberry", 364, buildTypes(new int[]{5, 146, 80, 46, 55, 212, 1179})),
    NETTLES("nettles", 365, buildTypes(new int[]{146, 104, 5, 78, 55, 212, 1158})),
    SASSAFRAS("sassafras", 366, buildTypes(new int[]{146, 103, 5, 78, 55, 212, 1158})),
    LINGONBERRY("lingonberry", 367, buildTypes(new int[]{5, 146, 80, 46, 55, 212, 1179})),
    MEAT_FILLET("meat fillet", 368, buildTypes(new int[]{28, 5, 62, 174, 219, 108, 74, 212, 146, 223, 1261})),
    FISH_FILLET("fish fillet", 369, buildTypes(new int[]{5, 62, 36, 75, 219, 108, 174, 212, 146, 223, 233})),
    PORRIDGE("porridge", 373, buildTypes(new int[]{5, 219, 26, 74, 108})),
    CHERRIES("cherries", 409, buildTypes(new int[]{5, 146, 80, 46, 157, 55, 1163})),
    LEMON("lemon", 410, buildTypes(new int[]{146, 103, 5, 80, 157, 55, 1163})),
    BLUE_GRAPES("blue grapes", 411, buildTypes(new int[]{5, 146, 80, 46, 157, 55, 1163})),
    OLIVES("olives", 412, buildTypes(new int[]{5, 146, 46, 157, 55, 1163})),
    FRUIT_PRESS("fruit press", 413, buildTypes(new int[]{108, 44, 144, 38, 21, 139, 33, 1, 147, 210})),
    GREEN_GRAPES("green grapes", 414, buildTypes(new int[]{5, 146, 80, 46, 157, 55, 1163})),
    MAPLE_SYRUP("maple syrup", 415, buildTypes(new int[]{26, 88, 90})),
    MAPLE_SAP("maple sap", 416, buildTypes(new int[]{26, 211})),
    FRUIT_JUICE("fruit juice", 417, buildTypes(new int[]{26, 88, 90, 216, 233})),
    OLIVE_OIL("olive oil", 418, buildTypes(new int[]{108, 26, 88, 34, 158, 113, 1163})),
    RED_WINE("red wine", 419, buildTypes(new int[]{108, 26, 88, 89, 90, 213, 212})),
    WHITE_WINE("white wine", 420, buildTypes(new int[]{108, 26, 88, 89, 90, 213, 212})),
    CAMELLIA("camellia", 422, buildTypes(new int[]{146, 104, 211, 55, 78, 113, 157, 212})),
    OLEANDER("oleander", 423, buildTypes(new int[]{5, 146, 78, 79, 55, 113, 157, 212, 1158})),
    LAVENDER_FLOWER("lavender flower", 424, buildTypes(new int[]{146, 103, 113, 157, 212, 211})),
    TEA("tea", 425, buildTypes(new int[]{26, 88, 90, 233})),
    ROSE_FLOWER("rose flower", 426, buildTypes(new int[]{146, 102, 113, 157, 211, 212, 233})),
    LEMONADE("lemonade", 427, buildTypes(new int[]{26, 88, 90, 233})),
    JAM("jam", 428, buildTypes(new int[]{108, 5, 220, 74, 233})),
    COCHINEAL("cochineal", 439, buildTypes(new int[]{146, 79, 113, 164, 211})),
    EGG("egg", 464, buildTypes(new int[]{5, 48, 96, 113, 219, 212, 146, 74})),
    HUGE_EGG("huge egg", 465, buildTypes(new int[]{112, 5, 48, 96, 59, 219})),
    SANDWICH("sandwich", 488, buildTypes(new int[]{82, 212, 75, 220, 222, 233})),
    BOUQUET_OF_YELLOW_FLOWERS("bouquet of yellow flowers", 498, buildTypes(new int[]{118, 146, 211, 1267})),
    BOUQUET_OF_ORANGE_RED_FLOWERS("bouquet of orange-red flowers", 499, buildTypes(new int[]{118, 146, 211, 1267})),
    BOUQUET_OF_PURPLE_FLOWERS("bouquet of purple flowers", 500, buildTypes(new int[]{118, 146, 211, 1267})),
    BOUQUET_OF_WHITE_FLOWERS("bouquet of white flowers", 501, buildTypes(new int[]{118, 146, 211, 1267})),
    BOUQUET_OF_BLUE_FLOWERS("bouquet of blue flowers", 502, buildTypes(new int[]{118, 146, 211, 1267})),
    BOUQUET_OF_GREENISH_YELLOW_FLOWERS("bouquet of greenish-yellow flowers", 503, buildTypes(new int[]{118, 146, 211, 1267})),
    BOUQUET_OF_WHITE_DOTTED_FLOWERS("bouquet of white-dotted flowers", 504, buildTypes(new int[]{118, 146, 211, 157, 1267})),
    MARLIN("marlin", 569, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    BLUE_SHARK("blue shark", 570, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    WHITE_SHARK("white shark", 571, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    OCTOPUS("octopus", 572, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    SAILFISH("sailfish", 573, buildTypes(new int[]{5, 36, 146, 74, 212, 219, 223, 1201})),
    DORADO("dorado", 574, buildTypes(new int[]{5, 36, 146, 212, 75, 219, 223, 1201})),
    TUNA("tuna", 575, buildTypes(new int[]{5, 36, 146, 212, 76, 219, 223, 1201})),
    DISHWATER("dishwater", 634, buildTypes(new int[]{5, 137, 26, 90})),
    HEART("heart", 636, buildTypes(new int[]{28, 5, 62, 74, 48, 106, 129, 46, 146})),
    SLEEP_POWDER("sleep powder", 666, buildTypes(new int[]{43, 42, 5, 53, 76, 127})),
    BRANCH("branch", 688, buildTypes(new int[]{133, 146, 21, 14, 144, 37, 84, 129, 151, 113, 175, 211})),
    CAKE("cake", 729, buildTypes(new int[]{82, 32, 76, 220, 233})),
    CAKE_SLICE("cake slice", 730, buildTypes(new int[]{82, 74, 220, 222, 233})),
    REED_FIBRE("reed fibre", 745, buildTypes(new int[]{46, 146, 129, 211})),
    RICE("rice", 746, buildTypes(new int[]{5, 146, 20, 55, 129, 212})),
    PRESS("press", 747, buildTypes(new int[]{108, 44, 144, 38, 21, 139, 210})),
    PAPYRUS_SHEET("papyrus sheet", 748, buildTypes(new int[]{21, 159, 146, 211})),
    STRAWBERRY_SEEDS("strawberry seeds", 750, buildTypes(new int[]{20, 5, 146, 55, 129})),
    BLACK_INK("black ink", 753, buildTypes(new int[]{26, 88, 90})),
    COOKED_RICE("cooked rice", 754, buildTypes(new int[]{82, 74, 219, 222})),
    KELP("kelp", 755, buildTypes(new int[]{146, 46, 113, 55, 211})),
    SOURCE("source", 763, buildTypes(new int[]{26, 88, 90, 163})),
    SOURCE_SALT("source salt", 764, buildTypes(new int[]{5, 55, 146, 163, 46})),
    WINE_BARREL("wine barrel", 768, buildTypes(new int[]{108, 135, 51, 1, 21, 33, 86, 52, 147, 44, 92, 77, 67, 178, 199, 236})),
    WALNUT("walnut", 832, buildTypes(new int[]{5, 21, 146, 80, 212, 217, 74, 1197})),
    CHESTNUT("chestnut", 833, buildTypes(new int[]{5, 21, 146, 80, 212, 74, 1197})),
    RICE_PORRIDGE("rice porridge", 856, buildTypes(new int[]{82, 74, 26, 219})),
    RISOTTO("risotto", 857, buildTypes(new int[]{82, 76, 219, 222})),
    RICE_WINE("rice wine", 858, buildTypes(new int[]{108, 26, 88, 90, 213, 212})),
    CRAB_MEAT("crab meat", 900, buildTypes(new int[]{28, 5, 62, 129, 146, 76, 219, 212, 223, 1261})),
    SHEEP_MILK("sheep milk", 1012, buildTypes(new int[]{26, 88, 90, 113, 191, 212, 1200})),
    BISON_MILK("bison milk", 1013, buildTypes(new int[]{26, 88, 90, 113, 191, 212, 1200})),
    RIFT_WOOD("rift wood", 1104, buildTypes(new int[]{21, 146, 46, 112, 211, 129, 48, 157})),
    MINT("mint", 1130, buildTypes(new int[]{146, 5, 78, 103, 206, 55, 212, 221, 1158})),
    FENNEL("fennel", 1131, buildTypes(new int[]{146, 5, 78, 103, 55, 212, 217, 1158})),
    FENNEL_PLANT("fennel plant", 1132, buildTypes(new int[]{146, 5, 78, 212, 221})),
    CARROT("carrot", 1133, buildTypes(new int[]{146, 5, 55, 29, 212, 223, 1156})),
    CABBAGE("cabbage", 1134, buildTypes(new int[]{146, 5, 55, 29, 212, 223, 1156})),
    TOMATO("tomato", 1135, buildTypes(new int[]{146, 5, 55, 29, 212, 223, 1156})),
    SUGAR_BEET("sugar beet", 1136, buildTypes(new int[]{146, 5, 55, 212})),
    LETTUCE("lettuce", 1137, buildTypes(new int[]{146, 5, 55, 29, 212, 223, 1156})),
    PEA_POD("pea pod", 1138, buildTypes(new int[]{146, 5, 55, 29, 212, 223, 1156})),
    SUGAR("sugar", 1139, buildTypes(new int[]{146, 137, 5})),
    CUMIN("cumin", 1140, buildTypes(new int[]{206, 55, 146, 5, 205, 212, 221, 1159})),
    GINGER("ginger", 1141, buildTypes(new int[]{206, 55, 146, 5, 205, 212, 221, 1159})),
    NUTMEG("nutmeg", 1142, buildTypes(new int[]{146, 55, 5, 205, 105, 212, 217, 1159})),
    PAPRIKA("paprika", 1143, buildTypes(new int[]{146, 55, 5, 205, 102, 212, 221, 1159})),
    TURMERIC("turmeric", 1144, buildTypes(new int[]{146, 55, 5, 205, 103, 212, 221, 1159})),
    CARROT_SEED("carrot seed", 1145, buildTypes(new int[]{20, 55, 146, 5})),
    CABBAGE_SEED("cabbage seed", 1146, buildTypes(new int[]{20, 55, 146, 5})),
    TOMATO_SEED("tomato seed", 1147, buildTypes(new int[]{20, 55, 146, 5})),
    SUGAR_BEET_SEED("sugar beet seed", 1148, buildTypes(new int[]{20, 55, 146, 5})),
    LETTUCE_SEED("lettuce seed", 1149, buildTypes(new int[]{20, 55, 146, 5})),
    PEA("pea", 1150, buildTypes(new int[]{20, 55, 146, 5, 29, 212, 223, 1156})),
    FENNEL_SEED("fennel seed", 1151, buildTypes(new int[]{206, 55, 146, 5, 205, 212, 1159})),
    COCOA("cocoa", 1152, buildTypes(new int[]{5, 129, 212, 55})),
    PAPRIKA_SEED("paprika seed", 1153, buildTypes(new int[]{206, 55, 146, 5, 205, 212})),
    TURMERIC_SEED("turmeric seed", 1154, buildTypes(new int[]{206, 55, 146, 5, 205, 212})),
    COCOA_BEAN("cocoa bean", 1155, buildTypes(new int[]{146, 55, 5, 212})),
    ANY_VEG("any veg", 1156, buildTypes(new int[]{207, 208, 5, 29, 223})),
    ANY_CEREAL("any cereal", 1157, buildTypes(new int[]{207, 208, 5})),
    ANY_HERB("any herb", 1158, buildTypes(new int[]{207, 208, 5, 78})),
    ANY_SPICE("any spice", 1159, buildTypes(new int[]{207, 208, 5, 205})),
    ANY_FRUIT("any fruit", 1163, buildTypes(new int[]{207, 208, 5, 80})),
    PIE_DISH("pie dish", 1165, buildTypes(new int[]{108, 30, 1, 77, 33, 231})),
    CAKE_TIN("cake tin", 1166, buildTypes(new int[]{108, 44, 22, 1, 33, 77, 231})),
    BAKING_STONE("baking stone", 1167, buildTypes(new int[]{108, 44, 25, 1, 33, 77, 231})),
    ROASTING_DISH("roasting dish", 1169, buildTypes(new int[]{108, 30, 1, 77, 33, 231})),
    SLICE_OF_BREAD("slice of bread", 1170, buildTypes(new int[]{82, 212, 74, 222})),
    PLATE("plate", 1173, buildTypes(new int[]{108, 21, 1, 77, 44, 92, 33, 231})),
    BATTER("batter", 1174, buildTypes(new int[]{5, 129, 212, 55, 108})),
    FUDGE_SAUCE("fudge sauce", 1176, buildTypes(new int[]{129, 212, 55, 219, 26, 88})),
    PIE("pie", 1177, buildTypes(new int[]{82, 129, 76, 219, 220, 222, 233})),
    STILL("still", 1178, buildTypes(new int[]{108, 22, 147, 209, 52, 67, 59, 1, 44, 178, 33, 199, 180, 112})),
    ANY_BERRY("any berry", 1179, buildTypes(new int[]{207, 208, 5, 80})),
    MEAD("mead", 1180, buildTypes(new int[]{108, 26, 88, 90, 212, 213})),
    CIDER("cider", 1181, buildTypes(new int[]{108, 26, 88, 90, 212, 213})),
    BEER("beer", 1182, buildTypes(new int[]{108, 26, 88, 90, 212, 213, 233})),
    WHISKY("whisky", 1183, buildTypes(new int[]{108, 26, 88, 90, 213, 212, 214, 89, 233})),
    PINENUT("pinenut", 1184, buildTypes(new int[]{5, 55, 146, 80, 212, 217, 1197})),
    CHOCOLATE("chocolate", 1185, buildTypes(new int[]{82, 75, 146, 220, 222})),
    BUTTER("butter", 1186, buildTypes(new int[]{82, 55, 222, 233})),
    OMELETTE("omelette", 1187, buildTypes(new int[]{82, 76, 219, 222, 233})),
    CURRY("curry", 1188, buildTypes(new int[]{82, 76, 219, 222, 233})),
    SALAD("salad", 1189, buildTypes(new int[]{82, 212, 76, 220, 222, 233})),
    SAUSAGE("sausage", 1190, buildTypes(new int[]{82, 212, 146, 74, 219, 222, 233})),
    BACON("bacon", 1191, buildTypes(new int[]{82, 146, 212, 74, 219, 222, 228})),
    CORN_DOUGH("corn dough", 1192, buildTypes(new int[]{5, 146, 129, 74, 108})),
    COOKING_OIL("cooking oil", 1193, buildTypes(new int[]{26, 88, 34, 158, 113, 233})),
    GRAVY("gravy", 1194, buildTypes(new int[]{26, 88, 74, 219})),
    CUSTARD("custard", 1195, buildTypes(new int[]{5, 26, 88, 74, 108})),
    RASPBERRIES("raspberries", 1196, buildTypes(new int[]{5, 146, 80, 46, 212, 55, 1179})),
    ANY_NUT("any nut", 1197, buildTypes(new int[]{207, 208, 5})),
    ANY_CHEESE("any cheese", 1198, buildTypes(new int[]{207, 208, 5, 192, 224})),
    ANY_MUSHROOM("any mushroom", 1199, buildTypes(new int[]{207, 208, 5, 226})),
    ANY_MILK("any milk", 1200, buildTypes(new int[]{207, 208, 26, 88, 191})),
    ANY_FISH("any fish", 1201, buildTypes(new int[]{207, 208, 5, 36, 223})),
    BISCUIT("biscuit", 1202, buildTypes(new int[]{82, 129, 212, 76, 220, 222, 233})),
    FRIES("fries", 1203, buildTypes(new int[]{82, 129, 212, 74, 219, 222, 228})),
    GELATINE("gelatine", 1204, buildTypes(new int[]{129, 137, 108, 26, 88})),
    HONEY_WATER("honey water", 1205, buildTypes(new int[]{129, 26, 213, 108, 88, 212})),
    PASSATA("passata", 1206, buildTypes(new int[]{129, 212, 26, 108, 88, 55})),
    PASTA("pasta", 1207, buildTypes(new int[]{82, 129, 212, 76, 219, 222, 233})),
    PASTRY("pastry", 1208, buildTypes(new int[]{82, 129, 212, 75, 222})),
    PESTO("pesto", 1209, buildTypes(new int[]{129, 212, 26, 88, 55})),
    STOCK("stock", 1210, buildTypes(new int[]{129, 55, 26, 88})),
    TOMATO_KETCHUP("tomato ketchup", 1211, buildTypes(new int[]{82, 129, 74, 222})),
    WHITE_SAUCE("white sauce", 1212, buildTypes(new int[]{129, 212, 55, 26, 88, 108, 233, 82})),
    WORT("wort", 1213, buildTypes(new int[]{129, 26, 213, 108, 233})),
    RAT_ON_A_STICK("rat-on-a-stick", 1214, buildTypes(new int[]{82, 129, 212, 76, 219, 222, 228})),
    HOG_ROAST("hog roast", 1215, buildTypes(new int[]{82, 129, 212, 76, 219, 222, 228, 52, 237})),
    LAMB_SPIT("lamb spit", 1216, buildTypes(new int[]{82, 129, 212, 76, 219, 222, 228, 52, 237})),
    MUSHY_PEAS("mushy peas", 1217, buildTypes(new int[]{5, 129, 212, 55, 108})),
    CROUTONS("croutons", 1218, buildTypes(new int[]{5, 129, 74, 108})),
    HAGGIS("haggis", 1219, buildTypes(new int[]{82, 129, 75, 219, 222})),
    CORNFLOUR("cornflour", 1220, buildTypes(new int[]{5, 146, 129, 55, 108})),
    CHEESECAKE("cheesecake", 1221, buildTypes(new int[]{82, 129, 76, 220, 222, 233})),
    KIELBASA("kielbasa", 1222, buildTypes(new int[]{82, 129, 76, 219, 222})),
    MUSHROOM("mushroom", 1223, buildTypes(new int[]{1, 129, 5, 82, 77, 74, 233})),
    STUFFED_MUSHROOM("stuffed mushroom", 1224, buildTypes(new int[]{82, 129, 74, 219, 222})),
    CRISPS("crisps", 1225, buildTypes(new int[]{82, 129, 76, 220, 222})),
    JELLY("jelly", 1226, buildTypes(new int[]{82, 129, 55, 220, 222, 233})),
    SCONE("scone", 1227, buildTypes(new int[]{82, 129, 76, 220, 222, 233})),
    TOAST("toast", 1228, buildTypes(new int[]{82, 129, 74, 222, 233})),
    PASTY("pasty", 1229, buildTypes(new int[]{82, 129, 76, 219, 220, 222, 233})),
    CHOCOLATE_NUT_SPREAD("chocolate nut spread", 1230, buildTypes(new int[]{5, 129, 74, 108})),
    VODKA("vodka", 1231, buildTypes(new int[]{108, 26, 88, 90, 213, 212, 214, 89})),
    BRANDY("brandy", 1232, buildTypes(new int[]{108, 26, 88, 90, 212, 214, 89, 233})),
    MOONSHINE("moonshine", 1233, buildTypes(new int[]{108, 26, 88, 90, 213, 212, 214, 89})),
    GIN("gin", 1234, buildTypes(new int[]{108, 26, 88, 90, 213, 212, 214, 89, 233})),
    PINEAPPLE("pineapple", 1235, buildTypes(new int[]{146, 5, 80, 212, 55})),
    SAUSAGE_SKIN("sausage skin", 1236, buildTypes(new int[]{5, 1, 77, 55})),
    MORTAR_AND_PESTLE("mortar and pestle", 1237, buildTypes(new int[]{25, 210, 44})),
    ROCK_SALT("rock salt", 1238, buildTypes(new int[]{25, 146, 112, 113, 129, 48, 175, 211})),
    SPAGHETTI("spaghetti", 1240, buildTypes(new int[]{82, 212, 76, 219, 222})),
    ICING("icing", 1241, buildTypes(new int[]{82, 212, 55, 233})),
    CAKE_MIX("cake mix", 1242, buildTypes(new int[]{5, 74})),
    BREADCRUMBS("breadcrumbs", 1244, buildTypes(new int[]{82, 146, 212, 74, 222})),
    MEATBALLS("meatballs", 1245, buildTypes(new int[]{82, 146, 212, 74, 219, 222, 233})),
    VINEGAR("vinegar", 1246, buildTypes(new int[]{108, 26, 88, 212, 233})),
    CUCUMBER("cucumber", 1247, buildTypes(new int[]{146, 55, 5, 29, 212, 223, 1156})),
    CUCUMBER_SEED("cucumber seed", 1248, buildTypes(new int[]{20, 55, 146, 5})),
    CREAM("cream", 1249, buildTypes(new int[]{212, 26, 88, 233})),
    GOBLIN_SKULL("goblin skull", 1250, buildTypes(new int[]{62, 187, 211})),
    BISCUIT_MIX("biscuit mix", 1256, buildTypes(new int[]{5, 74})),
    SCONE_MIX("scone mix", 1257, buildTypes(new int[]{5, 74})),
    TART("tart", 1258, buildTypes(new int[]{82, 129, 76, 219, 220, 222, 233})),
    STIR_FRY("stir fry", 1259, buildTypes(new int[]{82, 129, 212, 76, 219, 222, 233})),
    NORI("nori", 1260, buildTypes(new int[]{146, 46, 113, 5, 55})),
    ANY_MEAT("any meat", 1261, buildTypes(new int[]{207, 208, 5, 28, 223})),
    PIZZA("pizza", 1262, buildTypes(new int[]{82, 129, 212, 76, 219, 222})),
    ANY_OIL("any oil", 1263, buildTypes(new int[]{108, 26, 88, 34, 158, 113, 208})),
    SUSHI("sushi", 1264, buildTypes(new int[]{82, 129, 212, 76, 220, 222, 233})),
    PICKLE("pickle", 1265, buildTypes(new int[]{82, 129, 212, 74, 220, 222, 233})),
    PUDDING("pudding", 1266, buildTypes(new int[]{82, 129, 212, 76, 220, 222, 233})),
    ANY_FLOWER("any flower", 1267, buildTypes(new int[]{207, 208, 211, 118})),
    BROTH("broth", 1268, buildTypes(new int[]{82, 219, 76, 26, 90, 88, 233})),
    WOOD_PULP("wood pulp", 1270, buildTypes(new int[]{21, 46, 146, 129, 211})),
    PAPER_SHEET("paper sheet", 1272, buildTypes(new int[]{21, 159, 146, 211})),
    HOPS("hops", 1273, buildTypes(new int[]{146, 5, 55, 212})),
    SNOWBALL("snowball", 1276, buildTypes(new int[]{5, 27})),
    COCONUT("coconut", 1280, buildTypes(new int[]{146, 5, 80, 212, 55})),
    ICE_CREAM("ice cream", 1281, buildTypes(new int[]{5, 55, 220, 27, 108, 233})),
    SWEET("sweet", 1282, buildTypes(new int[]{82, 74, 108, 212, 233, 220, 234})),
    ORANGE("orange", 1283, buildTypes(new int[]{146, 104, 5, 80, 212, 157, 74, 1179})),
    BOILER("boiler", 1284, buildTypes(new int[]{22, 1, 31, 54, 232, 240, 178, 33, 77, 229, 112, 157})),
    RUM("rum", 1286, buildTypes(new int[]{108, 26, 88, 90, 213, 212, 214, 89})),
    MUFFIN("muffin", 1287, buildTypes(new int[]{82, 129, 212, 76, 219, 222, 233})),
    COOKIE("cookie", 1288, buildTypes(new int[]{82, 129, 212, 76, 220, 222, 233}));

    private final String name;
    private final int templateId;
    private final ItemTemplateType[] types;
    private static ArrayList<ItemTemplate> itemTemplates = new ArrayList<>();

    ItemTemplate(String name, int templateId, ItemTemplateType[] types) {
        this.name = name;
        this.templateId = templateId;
        this.types = types;
    }

    public static ItemTemplate getFromString(String name){
        return Arrays.stream(values())
                .filter(itemTemplate -> Objects.equals(name, itemTemplate.name))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    boolean isContainer() {
        return Arrays.stream(types).allMatch(itemTemplateType -> itemTemplateType == HOLLOW);
    }

    public boolean isFood() {
        return Arrays.stream(types).anyMatch(itemTemplateType -> itemTemplateType == FOOD ||
                itemTemplateType == DISH);
    }

    public boolean isLiquidCooking() {
        return Arrays.stream(types).anyMatch(itemTemplateType -> itemTemplateType == LIQUID_COOKING);
    }

    public boolean isCookingTool() {
        return Arrays.stream(types).anyMatch(itemTemplateType -> itemTemplateType == TOOL_COOKING);
    }

    public boolean isRecipeItem() {
        return Arrays.stream(types).anyMatch(itemTemplateType -> itemTemplateType == RECIPE_ITEM);
    }

    boolean canBeFermended() {
        return Arrays.stream(types).anyMatch(itemTemplateType -> itemTemplateType == FERMENTED);
    }

    boolean isHollow() {
       return Arrays.stream(types).anyMatch(itemTemplateType -> itemTemplateType == HOLLOW);
    }

    boolean isFoodMaker() {
        return Arrays.stream(types).anyMatch(itemTemplateType -> itemTemplateType == FOODMAKER);
    }

    public String getName() {
        return name;
    }

    public int getTemplateId() {
        return templateId;
    }

    public boolean isAnyMeatGroup(){
        return Arrays.stream(this.types)
                .anyMatch(itemTemplateType -> itemTemplateType.equals(ANY_MEAT_FOOD_GROUP));
    }

    public ItemTemplateType[] getTypes() {
        return types;
    }

    public ArrayList<ItemTemplate> getCompatibleAnyTemplates() {
        return Arrays.stream(values())
                .filter(itemTemplate -> Arrays.stream(itemTemplate.types)
                        .anyMatch(itemTemplateType1 -> itemTemplateType1.equals(this.getTypeFromTemplate())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ItemTemplateType getTypeFromTemplate(){
        ItemTemplateType itemTemplateType = ItemTemplateType.NONE;
        switch (this){
            case ANY_MEAT:
                itemTemplateType = ANY_MEAT_FOOD_GROUP;
                break;
            case ANY_VEG:
                itemTemplateType = ANY_VEGGIE_FOOD_GROUP;
                break;
            case ANY_HERB:
                itemTemplateType = ANY_HERB_FOOD_GROUP;
                break;
            case ANY_SPICE:
                itemTemplateType = ANY_SPICE_FOOD_GROUP;
                break;
        }
        return itemTemplateType;
    }

    public boolean isAnyFoodGrouping(){
       return Arrays.stream(this.types)
                .anyMatch(ItemTemplateType::isAnyFoodType);
    }
}

