package com.joedobo27.libs.item;

import java.util.Arrays;
import java.util.Objects;

public enum Material {
    NONE(0) ,
    FLESH(1),
    MEAT(2),
    RYE(3),
    OAT(4),
    BARLEY(5),
    WHEAT(6),
    GOLD(7),
    SILVER(8),
    STEEL(9),
    COPPER(10),
    IRON(11),
    LEAD(12),
    ZINC(13),
    BIRCHWOOD(14),
    STONE(15),
    LEATHER(16),
    COTTON(17),
    CLAY(18),
    POTTERY(19),
    GLASS(20),
    MAGIC(21),
    VEGETARIAN(22),
    FIRE(23),
    OIL(25),
    WATER(26),
    CHARCOAL(27),
    DAIRY(28),
    HONE(29),
    BRASS(30),
    BRONZE(31),
    FAT(32),
    PAPER(33),
    TIN(34),
    BONE(35),
    SALT(36),
    PINEWOOD(37),
    OAKENWOOD(38),
    CEDARWOOD(39),
    WILLOW(40),
    MAPLEWOOD(41),
    APPLEWOOD(42),
    LEMONWOOD(43),
    OLIVEWOOD(44),
    CHERRYWOOD(45),
    LAVENDERWOOD(46),
    ROSEWOOD(47),
    THORN(48),
    GRAPEWOOD(49),
    CAMELLIAWOOD(50),
    OLEANDERWOOD(51),
    CRYSTAL(52),
    WEMP(53),
    DIAMOND(54),
    ANIMAL(55),
    ADAMANTINE(56),
    GLIMMERSTEEL(57),
    TAR(58),
    PEAT(59),
    REED(60),
    SLATE(61),
    MARBLE(62),
    CHESTNUT(63),
    WALNUT(64),
    FIRWOOD(65),
    LINDENWOOD(66),
    SERYLL(67),
    IVY(68),
    WOOL(69),
    STRAW(70),
    HAZELNUTWOOD(71),
    BEAR(72),
    BEEF(73),
    CANINE(74),
    FELINE(75),
    DRAGON(76),
    FOWL(77),
    GAME(78),
    HORSE(79),
    HUMAN(80),
    HUMANOID(81),
    INSECT(82),
    LAMB(83),
    PORK(84),
    SEAFOOD(85),
    SNAKE(86),
    TOUGH(87),
    ORANGEWOOD(88),
    RASPBERRYWOOD(90),
    BLUEBERRYWOOD(91),
    LINGONBERRYWOOD(92);

    private final byte id;

    Material(int id) {
        this.id = (byte)id;
    }

    public static Material getFromString(String name) {
        return Arrays.stream(values())
                .filter(material -> Objects.equals(name.toLowerCase(), material.getName()))
                .findFirst()
                .orElse(Material.NONE);
    }

    /**
     * @return String formated so all lowercase, spaces removed, _ removed.
     */
    public String getName() {
        return this.name().toLowerCase().replaceAll("_", "")
                .replaceAll(" ", "");
    }

    public byte getId() {
        return id;
    }

}
