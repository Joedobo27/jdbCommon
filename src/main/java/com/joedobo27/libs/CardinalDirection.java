package com.joedobo27.libs;

import com.wurmonline.math.TilePos;

import java.util.Arrays;
import java.util.function.Function;

@SuppressWarnings("unused")
public enum CardinalDirection {
    CARDINALS_N(0, TilePos::North),
    CARDINALS_NE(1, TilePos::NorthEast),
    CARDINALS_E(2, TilePos::East),
    CARDINALS_SE(3, TilePos::SouthEast),
    CARDINALS_S(4, TilePos::South),
    CARDINALS_SW(5, TilePos::SouthWest),
    CARDINALS_W(6, TilePos::West),
    CARDINALS_NW(7, TilePos::NorthWest);

    private final int id;
    private final Function<TilePos, TilePos> tilePosFunction;

    CardinalDirection(int id, Function<TilePos, TilePos> tilePosFunction) {
        this.id = id;
        this.tilePosFunction = tilePosFunction;
    }

    public byte getId() {
        return (byte)this.id;
    }

    public static TilePos offsetByOneCardinal(int directionId, TilePos originPosition) {
        CardinalDirection cardinalDirection = Arrays.stream(values())
                .filter(cardinalDirection1 -> cardinalDirection1.id == directionId)
                .findFirst()
                .orElse(null);
        if (cardinalDirection == null)
            return originPosition;
        return cardinalDirection.tilePosFunction.apply(originPosition);
    }
}
