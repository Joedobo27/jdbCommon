package com.joedobo27.libs.item;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public enum ItemTemplateType {
    NONE(-1),
    HOLLOW(1),
    WEAPON_SLASH(2),
    SHIELD(3),
    ARMOUR(4),
    FOOD(5),
    MAGIC(6),
    TOOL_FIELD(7),
    BODYPART(8),
    INVENTORY(9),
    TOOL_MINING(10),
    TOOL_CARPENTRY(11),
    TOOL_SMITHING(12),
    WEAPON_PIERCE(13),
    WEAPON_CRUSH(14),
    WEAPON_AXE(15),
    WEAPON_SWORD(16),
    WEAPON_KNIFE(17),
    WEAPON_MISC(18),
    TOOL_DIGGING(19),
    SEED(20),
    WOOD(21),
    METAL(22),
    LEATHER(23),
    CLOTH(24),
    STONE(25),
    LIQUID(26),
    MELTING(27),
    MEAT(28),
    VEGETABLE(29),
    POTTERY(30),
    NOTAKE(31),
    LIGHT(32),
    CONTAINER_LIQUID(33),
    LIQUID_INFLAMMABLE(34),
    WEAPON_MELEE(35),
    FISH(36),
    WEAPON(37),
    TOOL(38),
    LOCK(39),
    INDESTRUCTIBLE(40),
    KEY(41),
    NODROP(42),
    DUST(43),
    REPAIRABLE(44),
    TEMPORARY(45),
    COMBINE(46),
    LOCKABLE(47),
    HASDATA(48),
    OUTSIDE_ONLY(49),
    COIN(50),
    TURNABLE(51),
    DECORATION(52),
    FULLPRICE(53),
    NORENAME(54),
    LOWNUTRITION(55),
    DRAGGABLE(56),
    VILLAGEDEED(57),
    HOMESTEADDEED(58),
    ALWAYSPOLL(59),
    FLOATING(60),
    NOTRADE(61),
    BUTCHERED(62),
    NOPUT(63),
    LEADCREATURE(64),
    FIRE(65),
    DOMAIN(66),
    USE_GROUND_ONLY(67),
    HUGEALTAR(68),
    ARTIFACT(69),
    UNIQUE(70),
    DESTROY_HUGEALTAR(71),
    PASS_FULLDATA(72),
    FORM(73),
    MEDIUMNUTRITION(74),
    GOODNUTRITION(75),
    HIGHNUTRITION(76),
    FOODMAKER(77),
    HERB(78),
    POISON(79),
    FRUIT(80),
    DESC_IS_EXAM(81),
    DISH(82),
    SERVERBOUND(83),
    TWOHANDED(84),
    KINGDOM_MARKER(85),
    DESTROYABLE(86),
    MATERIAL_PRICEEFFECT(87),
    LIQUID_COOKING(88),
    POSITIVE_DECAY(89),
    LIQUID_DRINKABLE(90),
    COLOR(91),
    COLORABLE(92),
    GEM(93),
    WEAPON_BOW(94),
    WEAPON_BOW_UNSTRINGED(95),
    EGG(96),
    NEWBIEITEM(97),
    TILE_ALIGNED(98),
    DRAGONARMOUR(99),
    COMPASS(100),
    OILCONSUMING(101),
    HEALING_POWER_1(102),
    HEALING_POWER_2(103),
    HEALING_POWER_3(104),
    HEALING_POWER_4(105),
    HEALING_POWER_5(106),
    HEALING(107),
    NAMED(108),
    ONE_PER_TILE(109),
    BED(110),
    INSIDE_ONLY(111),
    NOBANK(112),
    RECYCLED(113),
    LOADED(114),
    FLICKERING(115),
    LIGHT_BRIGHT(116),
    VEHICLE(117),
    FLOWER(118),
    IMPROVEITEM(119),
    DEATHPROT(120),
    TOOLBELT(121),
    ROYAL(122),
    NOMOVE(123),
    WIND(124),
    DREDGE(125),
    MINEDOOR(126),
    NOSELLBACK(127),
    SPRINGFILLED(128),
    DECAYDESTROYS(129),
    RECHARGEABLE(130),
    SERVERPORTAL(131),
    TRAP(132),
    DISARM_TRAP(133),
    VEHICLE_DRAGGED(134),
    OWNER_DESTROYABLE(135),
    CREATURE_WEARABLE(136),
    NONUTRITION(137),
    PUPPET(138),
    OVERRIDEENCHANT(139),
    MEDITATION(140),
    TRANSMUTABLE(141),
    SIGN(142),
    STREETLAMP(143),
    VISIBLEDECAY(144),
    BULKCONTAINER(145),
    BULK(146),
    MISSION(147),
    COMBINECOLD(148),
    SPAWNSTREES(149),
    KILLSTREES(150),
    CRUDE(151),
    MINABLE(152),
    ENCHANT_JEWELRY(153),
    WEAPON_POLEARM(154),
    ALWAYS_BANKABLE(155),
    ALWAYS_LIT(156),
    NOT_MISSION(157),
    MASSPRODUCTION(158),
    CAN_HAVE_INSCRIPTION(159),
    NOWORKPARENT(160),
    WARTARGET(161),
    SOURCESPRING(162),
    SOURCE(163),
    COLORCOMPONENT(164),
    TUTORIAL(165),
    TEN_PER_TILE(166),
    FOUR_PER_TILE(167),
    ABILITY(168),
    PLANTED_FLOWERPOT(169),
    EQUIPMENTSLOT(170),
    INVENTORY_GROUP(171),
    MAGICAL_STAFF(172),
    IMPROVE_USES_TYPE_AS_MATERIAL(173),
    NODISCARD(174),
    INSTADISCARD(175),
    TRANSPORTABLE(176),
    WARMACHINE(177),
    NEVER_SHOW_CREATION_WINDOW_OPTION(178),
    BRAZIER(179),
    USES_SPECIFIED_CONTAINER_VOLUME(180),
    TENT(181),
    USEMATERIAL_AND_KINGDOM(182),
    SMEARABLE(183),
    CARPET(184),
    ONE_PER_TILEBORDER(185),
    NATURE_PLANTABLE(186),
    NO_IMPROVE(187),
    TAPESTRY(188),
    CHALL_NEWBIE(189),
    UNFINISHED_NOTAKE(190),
    MILK(191),
    CHEESE(192),
    CART(193),
    OWNER_TURNABLE(194),
    OWNER_MOVEABLE(195),
    UNFIRED(196),
    CHAIR(197),
    LEAD_MULTIPLE_CREATURES(198),
    PLANTABLE(199),
    PLANT_ONE_A_WEEK(200),
    HITCH_TARGET(201),
    SPICE(205),
    POTABLE(206),
    NO_CREATE(207),
    FOOD_GROUP(208),
    COOKER(209),
    TOOL_COOKING(210),
    RECIPE_ITEM(211),
    USES_FOOD_STATE(212),
    FERMENTED(213),
    DISTILLED(214),
    SEALABLE(215),
    USE_REAL_TEMPLATE_ICON(216),
    COOKING_OIL(217),
    HOVER(218),
    FOOD_BONUS_HOT(219),
    FOOD_BONUS_COLD(220),
    POTTED(221),
    CAN_BE_PAPYRUS_WRAPPED(222),
    CAN_BE_RAW_WRAPPED(223),
    CAN_BE_CLOTH_WRAPPED(224),
    SURFACE_ONLY(225),
    MUSHROOM(226),
    HARVESTABLE(227),
    SHOW_RAW(228),
    NOT_SPELL_TARGET(229),
    TRELLIS(230),
    INGREDIENTS_ONLY(231),
    COMPONENT_ITEM(232),
    USES_REAL_TEMPLATE(233),
    LARDER(234),
    RUNE(235),
    PEGABLE(236),
    DECAY_ON_DEED(237),
    INSULATED(238),
    GUARD_TOWER(239),
    PARENT_ON_GROUND(240),
    ROAD_MARKER(241),
    PAVEABLE(242),
    CAVE_PAVEABLE(243),
    DECORATION_WHEN_PLANTED(244),
    DESC_IS_NAME(245),
    ANY_CEREAL_FOOD_GROUP(1157),
    ANY_VEGGIE_FOOD_GROUP(1156),
    ANY_CHEESE_FOOD_GROUP(1198),
    ANY_MILK_FOOD_GROUP(1200),
    ANY_MEAT_FOOD_GROUP(1261),
    ANY_FISH_FOOD_GROUP(1201),
    ANY_MUSHROOM_FOOD_GROUP(1199),
    ANY_HERB_FOOD_GROUP(1158),
    ANY_FLOWER_FOOD_GROUP(1267),
    ANY_BERRY_FOOD_GROUP(1179),
    ANY_NUT_FOOD_GROUP(1197),
    ANY_FRUIT_FOOD_GROUP(1163),
    ANY_SPICE_FOOD_GROUP(1159);

    private final short typeId;

    ItemTemplateType(int typeId) {
        this.typeId = (short)typeId;
    }

    public short getTypeId() {
        return typeId;
    }

    static private ItemTemplateType getTemplateTypeFromId(int id){
        return Arrays.stream(values())
                .filter(itemTemplateType -> itemTemplateType.typeId == id)
                .findFirst()
                .orElse(NONE);
    }

    static ItemTemplateType[] buildTypes(int[] ids) {
        return Arrays.stream(ids)
                .mapToObj(ItemTemplateType::getTemplateTypeFromId)
                .toArray(ItemTemplateType[]::new);
    }

    public String getName() {
        return this.name().toLowerCase().replace("_", " ")
                .replace("0", "-");
    }

    public static ItemTemplateType[] getFromStrings(String[] strings) throws RuntimeException{
        ItemTemplateType[] toReturn = new ItemTemplateType[strings.length];
        IntStream.range(0, strings.length)
                .forEach(ordinal -> Arrays.stream(values())
                        .filter(itemTemplateType -> Objects.equals(itemTemplateType.getName(), strings[ordinal]))
                        .forEach(itemTemplateType -> toReturn[ordinal] = itemTemplateType));
        return toReturn;
    }

    public static short[] ItemTypesToShorts(ItemTemplateType[] itemTemplateTypes) {
        short[] toReturn = new short[itemTemplateTypes.length];
        IntStream.range(0, itemTemplateTypes.length).forEach(
                value -> toReturn[value] = itemTemplateTypes[value].getTypeId());
        return toReturn;
    }

    public boolean isAnyFoodType() {
        return this.equals(ANY_CEREAL_FOOD_GROUP) || this.equals(ANY_VEGGIE_FOOD_GROUP) || this.equals(ANY_CHEESE_FOOD_GROUP) ||
                this.equals(ANY_MILK_FOOD_GROUP) || this.equals(ANY_MEAT_FOOD_GROUP) || this.equals(ANY_FISH_FOOD_GROUP) ||
                this.equals(ANY_MUSHROOM_FOOD_GROUP) || this.equals(ANY_HERB_FOOD_GROUP) || this.equals(ANY_FLOWER_FOOD_GROUP) ||
                this.equals(ANY_BERRY_FOOD_GROUP) || this.equals(ANY_NUT_FOOD_GROUP) || this.equals(ANY_FRUIT_FOOD_GROUP) ||
                this.equals(ANY_SPICE_FOOD_GROUP);
    }

    public static ItemTemplateType[] EMPTY = new ItemTemplateType[0];
}


