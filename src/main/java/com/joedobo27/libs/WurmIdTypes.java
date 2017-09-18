package com.joedobo27.libs;

@SuppressWarnings("unused")
public enum WurmIdTypes {
    ID_TYPE_TILE_SURFACE(3),
    ID_TYPE_HOUSE_WALL(5),
    ID_TYPE_FENCE(7),
    ID_TYPE_TILE_BORDER(12),
    ID_TYPE_TILE_CAVE(17),
    ID_TYPE_HOUSE_FLOOR(23),
    ID_TYPE_TILE_CORNER(27),
    ID_TYPE_BRIDGE_PART(28);

    private final int id;

    WurmIdTypes(int id) {
        this.id = id;
    }

    public byte getId() {
        return (byte)this.id;
    }
}
