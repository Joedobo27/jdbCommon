package com.joedobo27.libs.item;

import java.util.Arrays;
import java.util.Objects;

public enum BehaviourType {

    NONE(0),
    ITEM(1),
    ITEM_PILE(2),
    CREATURE(4),
    TILE(5),
    STRUCTURE(6),
    TILE_TREE(7),
    TILE_GRASS(8),
    TILE_ROCK(9),
    BODY_PART(10),
    EXAMINE(11),
    TILE_DIRT(15),
    VEGETABLE(16),
    TILE_FIELD(17),
    FIRE(18),
    WALL(20),
    WRIT(21),
    FENCE(22),
    UNFINISHED_ITEM(23),
    VILLAGE_DEED(24),
    VILLAGE_TOKEN(25),
    TOY(26),
    WOUND(27),
    CORPSE(28),
    TRADER_BOOK(29),
    CORNUCOPIA(30),
    PRACTICE_DOLL(31),
    TILE_BORDER(32),
    DOMAIN_ITEM(33),
    HUGE_ALTAR(34),
    ARTIFACT(35),
    PLANET(36),
    HUGE_LOG(37),
    CAVE_WALL(38),
    CAVE_TILE(39),
    WAR_MACHINE(40),
    VEHICLE(41),
    SKILL(42),
    MISSION(43),
    PAPYRUS(44),
    FLOOR(45),
    SHARD(46),
    FLOWER_POT(47),
    GRAVE_STONE(48),
    INVENTORY(49),
    TICKET(50),
    BRIDGE_PART(51),
    OWNERSHIP_PAPER(52),
    MENU_REQUEST(53),
    TILE_CORNER(54),
    PLANTER(55),
    MARKER(56),
    ALMANAC(57),
    TRELLIS(58),
    WAGONER_CONTRACT(59),
    BRIDGE_CORNER(60),
    WAGONER_CONTAINER(61);
    
    private short id;

    BehaviourType(int id){
        this.id = (short)id;
    }

    public short getId() {
        return id;
    }

    public String getName() {
        return this.name().toLowerCase().replace("_", " ")
                .replace("0", "-");
    }

    static public BehaviourType getFromString(String itemSize) throws RuntimeException{
        return Arrays.stream(values())
                .filter(behaviourType -> Objects.equals(behaviourType.getName().toLowerCase(), itemSize.toLowerCase()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
