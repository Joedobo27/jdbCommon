package com.joedobo27.libs;


import com.wurmonline.math.TilePos;
import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.zones.NoSuchZoneException;
import com.wurmonline.server.zones.Zone;
import com.wurmonline.server.zones.Zones;
import org.jetbrains.annotations.Nullable;

/**
 * Don't make a tile instance for every tile on the map. Just use this as a wrapper around the various WU tile interaction methods.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class TileUtilities {

    public static void test(Object... args) {
        int i = 1;
    }

    public static TilePos getPerformerNearestTile(Creature performer) {
        final float METERS_PER_TILE = 4F;
        int tileX = Math.round(performer.getPosX() / METERS_PER_TILE);
        int tileY = Math.round(performer.getPosY() / METERS_PER_TILE);
        return TilePos.fromXY(tileX, tileY);
    }

    public static TilePos getPerformerOccupiedTile(Creature performer) {
        final int METERS_PER_TILE = 4;
        int tileX = (int)(performer.getPosX() / METERS_PER_TILE);
        int tileY = (int)(performer.getPosY() / METERS_PER_TILE);
        return TilePos.fromXY(tileX, tileY);
    }

    public static boolean isTargetedSurfaceRock(int encodedTile){
        return Tiles.decodeType(encodedTile) == Tiles.Tile.TILE_ROCK.id || Tiles.decodeType(encodedTile) == Tiles.Tile.TILE_CLIFF.id;
    }

    public static boolean isPerformerNearestTileRock(Creature performer) {
        byte tileType = getSurfaceType(getPerformerNearestTile(performer));
        return tileType == Tiles.Tile.TILE_ROCK.id || tileType == Tiles.Tile.TILE_CLIFF.id;
    }

    public static short getSurfaceHeight(TilePos tilePos) {
        return Tiles.decodeHeight(Server.surfaceMesh.getTile(tilePos));
    }

    public static void setSurfaceHeight(TilePos tilePos, int elevation) {
        Server.surfaceMesh.setTile(tilePos.x, tilePos.y, Tiles.encode((short)elevation, getSurfaceType(tilePos),
                getSurfaceData(tilePos)));
    }

    public static byte getSurfaceType(TilePos tilePos) {
        return Tiles.decodeType(Server.surfaceMesh.getTile(tilePos));
    }

    public static void setSurfaceType(TilePos tilePos, int newTileTypeId) {
        Server.surfaceMesh.setTile(tilePos.x, tilePos.y, Tiles.encode(getSurfaceHeight(tilePos), (byte)newTileTypeId,
                getSurfaceData(tilePos)));
    }

    public static byte getSurfaceData(TilePos tilePos) {
        return Tiles.decodeData(Server.surfaceMesh.getTile(tilePos));
    }

    public static int getSurfaceEncodedValue(TilePos tilePos) {
        return Server.surfaceMesh.getTile(tilePos);
    }

    public static short getRockHeight(TilePos tilePos) {
        return Tiles.decodeHeight(Server.rockMesh.getTile(tilePos));
    }

    public static byte getRockType(TilePos tilePos) {
        return Tiles.decodeType(Server.rockMesh.getTile(tilePos));
    }

    public static byte getRockData(TilePos tilePos) {
        return Tiles.decodeData(Server.rockMesh.getTile(tilePos));
    }

    public static short getCaveFloorHeight(TilePos tilePos) {
        return Tiles.decodeHeight(Server.caveMesh.getTile(tilePos));
    }

    public static byte getCaveFloorType(TilePos tilePos) {
        return Tiles.decodeType(Server.caveMesh.getTile(tilePos));
    }

    /**
     * cave ceiling is tracked with an offset from the cave floor elevation. Each cave tile has an integer worth of
     * encoded data which is stored one of Server.caveMesh field array values. This piece of data is masked with 0x00FF0000.
     * There is only a byte of data available which limits cave ceiling heights to 255 meter offset.
     *
     * @param tilePos WU object. Object holding an x,y coordinate.
     * @return byte primitive, the ceiling's offset form the floor in meters
     */
    public static byte getCaveCeilingOffset(TilePos tilePos) {
        return Tiles.decodeData(Server.caveMesh.getTile(tilePos));
    }

    public static int getCaveCeilingHeight(TilePos tilePos) {
        return getCaveFloorHeight(tilePos) + getCaveCeilingOffset(tilePos);
    }

    /**
     * Mine cave tiles have up to 10k possible mine actions and that counter is tracked in encodedCaveTile.
     * For the Server.resourceMesh encoded int, this value is fetched with bit/masking: 0xFFFF0000 >> 16
     *  @return int primitive, How many mine actions left for a cave wall of any type.
     */
    public static int getCaveResourceCount(TilePos tilePos) {
        if (!hasCaveResources(tilePos)) {
            String tileName = Tiles.getTile(Tiles.decodeType(Server.caveMesh.getTile(tilePos))).getName();
            throw new TileException("The " + tileName + " at " + tilePos.toString() + " does not have a resource count.");
        }
        else
            return Server.getCaveResource(tilePos.x, tilePos.y);
    }

    /**
     * Tiles which can be dug have a dig counter attached to them. For the Server.resourceMesh encoded int,
     * this value is fetched with bit/masking: 0x000000FF
     *
     * @return int primitive, How many dig done for tile.
     */
    public static int getDigCount(TilePos tilePos) {
        if(hasDigCount(tilePos)) {
            String tileName = Tiles.getTile(Tiles.decodeType(Server.caveMesh.getTile(tilePos))).getName();
            throw new TileException("The " + tileName + " at " + tilePos.toString() + " does not have a dig count.");
        }
        else
            return Server.getDigCount(tilePos.x, tilePos.y);
    }

    /**
     * tiles which are being transmuted have a metric to track that process's progress. For the Server.resourceMesh encoded int,
     * this value is fetched with bit/masking: 0x0000FF00 >> 8
     * @return int primitive, what is current quality level of the transmute in-process.
     */
    public static int getPotionQLCount(TilePos tilePos) {
        if (!hasPotionCount(tilePos)) {
            String tileName = Tiles.getTile(Tiles.decodeType(Server.caveMesh.getTile(tilePos))).getName();
            throw new TileException("The " + tileName + " at " + tilePos.toString() + " does not have a potion count.");
        }
        else
            return Server.getPotionQLCount(tilePos.x, tilePos.y);
    }

    private static boolean hasDigCount(TilePos tilePos) {
        return true;
    }

    private static boolean hasPotionCount(TilePos tilePos) {
        return true;
    }

    private static boolean hasCaveResources(TilePos tilePos){
        boolean toReturn;
        switch ((int) Tiles.decodeType(Server.caveMesh.getTile(tilePos))){
            case Tiles.TILE_TYPE_CAVE_WALL:
            case Tiles.TILE_TYPE_CAVE_WALL_ROCKSALT:
            case Tiles.TILE_TYPE_CAVE_WALL_SLATE:
            case Tiles.TILE_TYPE_CAVE_WALL_MARBLE:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_GOLD:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_SILVER:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_IRON:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_COPPER:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_LEAD:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_ZINC:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_TIN:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_ADAMANTINE:
            case Tiles.TILE_TYPE_CAVE_WALL_ORE_GLIMMERSTEEL:
                toReturn = true;
                break;
            default:
                toReturn = false;
                break;
        }
        return toReturn;
    }

    /**
     * Tiles.TILE_TYPE_FIELD = 7
     * Tiles.TILE_TYPE_FIELD2 = 43
     * byte id's for fields 3,4,5... would likely start at 44. There is space in the tile type to do so there and it looks
     * like that is what WO would also do.
     * Additional farm field types would need to be added with byte code making the field references unavailable. Thus, the
     * reason for the internal vars scheme.
     *
     * @param encodedTile int primitive, WU serialized data hex: TTDDHHHH. T=tile type, D=data, H=height.
     * @return boolean primitive.
     */
    static boolean isFarmTile(int encodedTile){
        final int field1 = Tiles.TILE_TYPE_FIELD; // 7
        final int field2 = Tiles.TILE_TYPE_FIELD2; // 43
        // potentially field3 at 44
        return Tiles.decodeType(encodedTile) == field1
                || Tiles.decodeType(encodedTile) == field2;
    }

    /**
     * The encoded value is stored in the surfaceMesh data. In other words, a surfaceMesh int is decoded to get data and
     * then for farm tiles that data is again decoded to get: isFarmed, tileAge, and cropId.
     * TileData is 1111 1111 or 0xFF or byte.
     * isFarmedMask = 0B10000000 // 0 or 1 for base 10.
     * tileAgeMask = 0B01110000 // 0 to 7 for base 10.
     * int cropTypeMask = 0B00001111 // 0 to 15 for base 10
     *
     * @param isFarmed boolean primitive
     * @param tileAge int primitive
     * @param cropId int primitive
     * @return byte encoded value
     */
    static byte encodeSurfaceFarmTileData(boolean isFarmed, int tileAge, int cropId){
        int encodedTile = 0;
        if (isFarmed)
            encodedTile = 0B10000000;
        if (tileAge != 0)
            encodedTile += (tileAge << 4);
        encodedTile += cropId;
        return (byte) encodedTile;
    }

    static int getFarmTileAge(TilePos tilePos) {
        return (getSurfaceData(tilePos) & 0B01110000) >>> 4;
    }

    static int getFarmTileCropId(TilePos tilePos) {
        int toReturn = 0;
        switch (TileUtilities.getSurfaceType(tilePos)) {
            case Tiles.TILE_TYPE_FIELD:
                toReturn = TileUtilities.getSurfaceData(tilePos) & 0B00001111;
                break;
            case Tiles.TILE_TYPE_FIELD2:
                toReturn = (TileUtilities.getSurfaceData(tilePos) & 0B00001111) + 16;
                break;
        }
        return toReturn;
    }

    static int getFarmTileTendCount(TilePos tilePos) {
        return Server.getWorldResource(tilePos.x, tilePos.y) >> 11;
    }


    /**
     * The encoded value is stored in the resourceMesh.
     * int worldResource = Server.getWorldResource(tilex, tiley); // worldResource is a 0xFFFF size.
     * int farmedCountMask = 0B1111 1000 0000 0000 - 0 to 248 tho it should never exceed 5.
     * int farmedChanceMask = 0B0000 0111 1111 1111 - 0 to 2047
     */
    static int encodeResourceFarmTileData(int farmCount, int farmChance) {
        return (farmCount << 11) + (farmChance);
    }

    static boolean performerIsWithinDistance(Creature performer, int targetX, int targetY, int tileCountProximity) {
        TilePos targetTilePos = TilePos.fromXY(targetX, targetY);
        TilePos performerTilePos = getPerformerOccupiedTile(performer);
        return Math.abs(performerTilePos.x - targetTilePos.x) <= tileCountProximity && Math.abs(performerTilePos.y - targetTilePos.y) <= tileCountProximity;
    }

    static void voidWorldResourceEntry(){

    }

    public static @Nullable Zone getZoneSafe(TilePos tilePos, boolean isOnSurface) {
        Zone toReturn = null;
        try {
            toReturn = Zones.getZone(tilePos.x, tilePos.y, isOnSurface);
        } catch (NoSuchZoneException ignored){}
        return toReturn;
    }

    public static boolean isTileOverriddenByDirt(byte type) {
        return type == Tiles.Tile.TILE_GRASS.id || type == Tiles.Tile.TILE_MYCELIUM.id || type == Tiles.Tile.TILE_STEPPE.id || type == Tiles.Tile.TILE_LAWN.id || type == Tiles.Tile.TILE_MYCELIUM_LAWN.id;
    }
}
