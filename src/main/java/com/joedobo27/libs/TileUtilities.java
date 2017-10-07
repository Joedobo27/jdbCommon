package com.joedobo27.libs;


import com.wurmonline.math.TilePos;
import com.wurmonline.mesh.BushData;
import com.wurmonline.mesh.Tiles;
import com.wurmonline.mesh.TreeData;
import com.wurmonline.server.Server;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import com.wurmonline.server.zones.NoSuchZoneException;
import com.wurmonline.server.zones.Zone;
import com.wurmonline.server.zones.Zones;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Don't make a tile instance for every tile on the map. Just use this as a wrapper around the various WU tile interaction methods.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class TileUtilities {

    public static void test(Object... args) {
        int i = 1;
    }

    public static short getSurfaceHeight(TilePos tilePos) {
        return Tiles.decodeHeight(Server.surfaceMesh.getTile(tilePos));
    }

    public static void setSurfaceHeight(TilePos tilePos, int elevation) {
        Server.surfaceMesh.setTile(tilePos.x, tilePos.y, Tiles.encode((short)elevation, getSurfaceTypeId(tilePos),
                getSurfaceData(tilePos)));
    }

    public static byte getSurfaceTypeId(TilePos tilePos) {
        return Tiles.decodeType(Server.surfaceMesh.getTile(tilePos));
    }

    public static void setSurfaceTypeId(TilePos tilePos, int newTileTypeId) {
        Server.surfaceMesh.setTile(tilePos.x, tilePos.y, Tiles.encode(getSurfaceHeight(tilePos), (byte)newTileTypeId,
                getSurfaceData(tilePos)));
    }

    public static Tiles.Tile getSurfaceType(TilePos tilePos) {
        return Tiles.getTile(getSurfaceTypeId(tilePos));
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

    public static void setRockHeight(TilePos tilePos, int elevation) {
        Server.rockMesh.setTile(tilePos.x, tilePos.y, Tiles.encode((short)elevation, getRockType(tilePos),
                getRockData(tilePos)));
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

    public static void setCaveFloorHeight(TilePos tilePos, int elevation) {
        Server.caveMesh.setTile(tilePos.x, tilePos.y, Tiles.encode((short)elevation, getCaveFloorType(tilePos),
                getCaveCeilingOffset(tilePos)));
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

    public static void setCaveCeilingOffset(TilePos tilePos, int offset) {
        Server.caveMesh.setTile(tilePos.x, tilePos.y, Tiles.encode(getCaveFloorHeight(tilePos), getCaveFloorType(tilePos),
                (byte)offset));
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
     * tiles which are being transmuted have a metric to track that process's progress. For the Server.resourceMesh
     * encoded int, this value is fetched with bit/masking: 0x0000FF00 >> 8
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

    @SuppressWarnings("SameReturnValue")
    private static boolean hasDigCount(TilePos tilePos) {
        return true;
    }

    @SuppressWarnings("SameReturnValue")
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

    //<editor-fold desc=" FARMING ">

    /**
     * tiles.tile_type_field = 7
     * tiles.tile_type_field2 = 43
     * byte id's for fields 3,4,5... would likely start at 44. there is space in the tile type to do so there and it looks
     * like that is what wo would also do.
     * additional farm field types would need to be added with byte code making the field references unavailable. thus, the
     * reason for the internal vars scheme.
     *
     * @param encodedTile int primitive, wu serialized data hex: ttddhhhh. t=tile type, d=data, h=height.
     * @return boolean primitive.
     */
    public static boolean isFarmTile(int encodedTile){
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
    public static byte encodeSurfaceFarmTileData(boolean isFarmed, int tileAge, int cropId){
        int encodedTile = 0;
        if (isFarmed)
            encodedTile = 0B10000000;
        if (tileAge != 0)
            encodedTile += (tileAge << 4);
        encodedTile += cropId;
        return (byte) encodedTile;
    }

    /**
     * The encoded value is stored in the resourceMesh.
     * int worldResource = Server.getWorldResource(tilex, tiley); // worldResource is a 0xFFFF size.
     * int farmedCountMask = 0B1111 1000 0000 0000 - 0 to 248 tho it should never exceed 5.
     * int farmedChanceMask = 0B0000 0111 1111 1111 - 0 to 2047
     */
    public static int encodeResourceFarmTileData(int farmCount, int farmChance) {
        return (farmCount << 11) + (farmChance);
    }

    public static int getFarmTileAge(TilePos tilePos) {
        return (getSurfaceData(tilePos) & 0B01110000) >>> 4;
    }

    public static int getFarmTileCropId(TilePos tilePos) {
        int toReturn = 0;
        switch (TileUtilities.getSurfaceTypeId(tilePos)) {
            case Tiles.TILE_TYPE_FIELD:
                toReturn = TileUtilities.getSurfaceData(tilePos) & 0B00001111;
                break;
            case Tiles.TILE_TYPE_FIELD2:
                toReturn = (TileUtilities.getSurfaceData(tilePos) & 0B00001111) + 16;
                break;
        }
        return toReturn;
    }

    public static int getFarmTileTendCount(TilePos tilePos) {
        return Server.getWorldResource(tilePos.x, tilePos.y) >> 11;
    }

    public static int getFarmTileCumulativeChance(TilePos tilePos) {
        return Server.getWorldResource(tilePos.x, tilePos.y) & 0x7FF;
    }

    public static ItemTemplate getItemTemplateFromHarvestTile(TilePos harvestTile) {
        int itemTemplateId;
        switch (TileUtilities.getFarmTileCropId(harvestTile)){
            case 1:
                itemTemplateId = ItemList.barley;
                break;
            case 2:
                itemTemplateId = ItemList.wheat;
                break;
            case 3:
                itemTemplateId = ItemList.oat;
                break;
            case 4:
                itemTemplateId = ItemList.corn;
                break;
            case 5:
                itemTemplateId = ItemList.pumpkin;
                break;
            case 6:
                itemTemplateId = ItemList.potato;
                break;
            case 7:
                itemTemplateId = ItemList.cotton;
                break;
            case 8:
                itemTemplateId = ItemList.wemp;
                break;
            case 9:
                itemTemplateId = ItemList.garlic;
                break;
            case 10:
                itemTemplateId = ItemList.onion;
                break;
            case 11:
                itemTemplateId = ItemList.reed;
                break;
            case 12:
                itemTemplateId = ItemList.rice;
                break;
            case 13:
                itemTemplateId = ItemList.strawberries;
                break;
            case 14:
                itemTemplateId = ItemList.carrot;
                break;
            case 15:
                itemTemplateId = ItemList.cabbage;
                break;
            case 16:
                itemTemplateId = ItemList.tomato;
                break;
            case 17:
                itemTemplateId = ItemList.sugarBeet;
                break;
            case 18:
                itemTemplateId = ItemList.lettuce;
                break;
            case 19:
                itemTemplateId = ItemList.peaPod;
                break;
            case 20:
                itemTemplateId = ItemList.cucumber;
                break;
            default:
                itemTemplateId = -1;
        }
        return ItemTemplateFactory.getInstance().getTemplateOrNull(itemTemplateId);
    }

    //</editor-fold>

    //<editor-fold desc=" TREE BUSH ">

    /**
     * int TreeAge = 0B1111 0000; 16 values, see mesh.FoliageAge.enumextend
     * int hasFruit = 0B0000 1000; 2 vales, has or not.
     * int isCentered = 0B0000 0100; 2 values, is centered or not.
     * int grassLength = 0B0000 0011; 4 values, see mesh.GrassData.GrowthTreeStage.enumextend
     * @return a tree's encoded data value.
     */
    public static int setSurfaceTreeTileData(int treeAge, int hasFruit, int isCentered, int grassLength) {
        int one = treeAge << 4;
        int two = hasFruit << 3;
        int three = isCentered << 2;
        return one + two + three + grassLength;
    }

    /**
     * Surface mesh data's use and tracking a tree's age.
     * int TreeAge = 0B1111 0000; 16 values, see mesh.FoliageAge.enumextend
     *
     * @param tilePos the tilePos to fetch the tree's age.
     * @return tree age, see  mesh.FoliageAge.enumextend.
     */
    public static int getTreeAge(TilePos tilePos) {
        return (getSurfaceData(tilePos) & 0B11110000) >>> 4;
    }

    /**
     * Surface mesh data's use and tracking if a tree has fruit.
     * int hasFruit = 0B0000 1000; 2 vales, has or not.
     *
     * @param tilePos the tilePos to test if it has fruit.
     * @return does the tree have fruit.
     */
    public static boolean treeHasFruit(TilePos tilePos) {
        int value = (getSurfaceData(tilePos) & 0B00001000) >>> 3;
        return value == 1;
    }

    /**
     * Surface mesh data's use and tracking if a tree is centered on tile.
     * int isCentered = 0B0000 0100; 2 vales, has or not.
     *
     * @param tilePos the tilePos to test if centered.
     * @return is the tree centered.
     */
    public static boolean treeIsCentered(TilePos tilePos) {
        int value = (getSurfaceData(tilePos) & 0B00000100) >>> 2;
        return value == 1;
    }

    /**
     * Surface mesh data's use and tracking a tree's grass length.
     * int grassLength = 0B0000 0011; 4 values, see mesh.GrassData.GrowthTreeStage.enumextend
     *
     * @param tilePos the tilePost to fetch grass length.
     * @return a grass length identifier, see mesh.GrassData.GrowthTreeStage.enumextend
     */
    public static int treeGrassLength(TilePos tilePos) {
        return (getSurfaceData(tilePos) & 0B00000011);
    }

    public static @Nullable BushData.BushType getBushType(TilePos tilePos) {
        if (!getSurfaceType(tilePos).isBush())
            return null;
        int tileTypeId = getSurfaceTypeId(tilePos);
        BushData.BushType bushTypeNormal = Arrays.stream(BushData.BushType.values())
                .filter(bushType -> bushType.asNormalBush() == tileTypeId)
                .findAny()
                .orElseGet(null);
        if (bushTypeNormal != null)
            return bushTypeNormal;
        BushData.BushType bushTypeEnchanted = Arrays.stream(BushData.BushType.values())
                .filter(bushType -> bushType.asEnchantedBush() == tileTypeId)
                .findAny()
                .orElseGet(null);
        if (bushTypeEnchanted != null)
            return bushTypeEnchanted;
        BushData.BushType bushTypeMyceliumBush = Arrays.stream(BushData.BushType.values())
                .filter(bushType -> bushType.asMyceliumBush() == tileTypeId)
                .findAny()
                .orElseGet(null);
        if (bushTypeMyceliumBush != null)
            return bushTypeMyceliumBush;
        return null;
    }

    public static @Nullable TreeData.TreeType getTreeType(TilePos tilePos) {
        if (!getSurfaceType(tilePos).isTree())
            return null;
        int tileTypeId = getSurfaceTypeId(tilePos);
        TreeData.TreeType treeTypeNormal = Arrays.stream(TreeData.TreeType.values())
                .filter(treeType -> treeType.asNormalTree() == tileTypeId)
                .findAny()
                .orElseGet(null);
        if (treeTypeNormal != null)
            return treeTypeNormal;
        TreeData.TreeType treeTypeEnchanted = Arrays.stream(TreeData.TreeType.values())
                .filter(treeType -> treeType.asEnchantedTree() == tileTypeId)
                .findAny()
                .orElseGet(null);
        if (treeTypeEnchanted != null)
            return treeTypeEnchanted;
        TreeData.TreeType treeTypeMycelium = Arrays.stream(TreeData.TreeType.values())
                .filter(treeType -> treeType.asMyceliumTree() == tileTypeId)
                .findAny()
                .orElseGet(null);
        if (treeTypeMycelium != null)
            return treeTypeMycelium;
        return null;
    }

    //</editor-fold>

    public static TilePos getPerformerNearestTile(Creature performer) {
        float METERS_PER_TILE = 4F;
        int tileX = Math.round(performer.getPosX() / METERS_PER_TILE);
        int tileY = Math.round(performer.getPosY() / METERS_PER_TILE);
        return TilePos.fromXY(tileX, tileY);
    }

    public static TilePos getPerformerOccupiedTile(Creature performer) {
        int METERS_PER_TILE = 4;
        int tileX = (int)(performer.getPosX() / METERS_PER_TILE);
        int tileY = (int)(performer.getPosY() / METERS_PER_TILE);
        return TilePos.fromXY(tileX, tileY);
    }

    public static boolean isTargetedSurfaceRock(int encodedTile){
        return Tiles.decodeType(encodedTile) == Tiles.Tile.TILE_ROCK.id || Tiles.decodeType(encodedTile) == Tiles.Tile.TILE_CLIFF.id;
    }

    public static boolean isPerformerNearestTileRock(Creature performer) {
        byte tileType = getSurfaceTypeId(getPerformerNearestTile(performer));
        return tileType == Tiles.Tile.TILE_ROCK.id || tileType == Tiles.Tile.TILE_CLIFF.id;
    }

    static boolean performerIsWithinDistance(Creature performer, int targetX, int targetY, int tileCountProximity) {
        TilePos targetTilePos = TilePos.fromXY(targetX, targetY);
        TilePos performerTilePos = getPerformerOccupiedTile(performer);
        return Math.abs(performerTilePos.x - targetTilePos.x) <= tileCountProximity &&
                Math.abs(performerTilePos.y - targetTilePos.y) <= tileCountProximity;
    }

    public static @Nullable Zone getZoneSafe(TilePos tilePos, boolean isOnSurface) {
        Zone toReturn = null;
        try {
            toReturn = Zones.getZone(tilePos.x, tilePos.y, isOnSurface);
        } catch (NoSuchZoneException ignored){}
        return toReturn;
    }

    public static boolean isTileOverriddenByDirt(TilePos tilePos) {
        byte type = getSurfaceTypeId(tilePos);
        return type == Tiles.Tile.TILE_GRASS.id || type == Tiles.Tile.TILE_MYCELIUM.id || type == Tiles.Tile.TILE_STEPPE.id ||
                type == Tiles.Tile.TILE_LAWN.id || type == Tiles.Tile.TILE_MYCELIUM_LAWN.id;
    }

    public static boolean isImmutableTile(TilePos tilePos) {
        byte type = getSurfaceTypeId(tilePos);
        return type == Tiles.Tile.TILE_CLAY.id || type == Tiles.Tile.TILE_MARSH.id || type == Tiles.Tile.TILE_PEAT.id ||
                type == Tiles.Tile.TILE_TAR.id || type == Tiles.Tile.TILE_HOLE.id || type == Tiles.Tile.TILE_MOSS.id ||
                type == Tiles.Tile.TILE_LAVA.id || Tiles.isMineDoor(type);
    }

    public static boolean isResourceTile(TilePos tilePos) {
        byte type = getSurfaceTypeId(tilePos);
        return type == Tiles.Tile.TILE_CLAY.id || type == Tiles.Tile.TILE_PEAT.id || type == Tiles.Tile.TILE_TAR.id ||
                type == Tiles.Tile.TILE_MOSS.id;
    }

    public static boolean isPackable(TilePos tilePos) {
        byte tileType = getSurfaceTypeId(tilePos);
        return !isRockTile(tilePos) && !isImmutableTile(tilePos) && !Tiles.isTree(tileType) && !Tiles.isBush(tileType)
                && tileType != Tiles.Tile.TILE_DIRT_PACKED.id && !Tiles.isRoadType(tileType) &&
                tileType != Tiles.Tile.TILE_SAND.id && !Tiles.isReinforcedFloor(tileType);
    }

    public static boolean isRockTile(TilePos tilePos) {
        byte tileType = getSurfaceTypeId(tilePos);
        return Tiles.isSolidCave(tileType) || tileType == Tiles.Tile.TILE_CAVE.id || tileType == Tiles.Tile.TILE_CAVE_EXIT.id ||
                tileType == Tiles.Tile.TILE_CLIFF.id || tileType == Tiles.Tile.TILE_ROCK.id ||
                tileType == Tiles.Tile.TILE_CAVE_FLOOR_REINFORCED.id;
    }

    public static long getTileBorderId(int x, int y, int heightOffset, byte layer, int TileBorderDirectionId) {
        return Tiles.getBorderObjectId(x, y, heightOffset, layer, TileBorderDirectionId,
                WurmIdTypes.ID_TYPE_TILE_BORDER.getId());
    }

    public static long getTileCornerId(int x, int y, int heightOffset, byte layer) {
        return Tiles.getBorderObjectId(x, y, heightOffset, layer, Tiles.TileBorderDirection.CORNER.getCode(),
                WurmIdTypes.ID_TYPE_TILE_CORNER.getId());
    }

    public static int getDirtDepth(TilePos tilePos) {
        return getSurfaceHeight(tilePos) - getRockHeight(tilePos);
    }

    public static int getSteepestSlope(TilePos targetedTile) {
        TilePos[] tilePos = {targetedTile.North(), targetedTile.East(), targetedTile.South(), targetedTile.West()};
        int[] ints = Arrays.stream(tilePos)
                .mapToInt(value -> Math.abs(TileUtilities.getSurfaceHeight(targetedTile) - TileUtilities.getSurfaceHeight(value)))
                .toArray();
        Arrays.sort(ints);
        return ints[ints.length-1];
    }

    public static TilePos getTilePosFromMeshArrayOrdinal(int ordinal) {
        int columns = (int) Math.pow(Server.caveMesh.getSizeLevel(), 2.0d); // Bitwise, WU usually does this: 1 << getSizeLevel()
        int y = ordinal / columns; // Bitwise: ordinal >> getSizeLevel()
        int x = ordinal % columns; // Bitwise: ordinal & (~(1 << getSizeLevel()))
        return TilePos.fromXY(x, y);
        /*
        int meshArraySize = Server.caveMesh.getData().length;
        int columns = (int) Math.pow(Server.caveMesh.getSizeLevel(), 2.0d); // Bitwise, WU usually does this: 1 << getSizeLevel()
        for(int meshArrayOrdinal=0; meshArrayOrdinal<meshArraySize; meshArrayOrdinal++) {
            int y = meshArrayOrdinal / columns; // Bitwise: ordinal >> getSizeLevel()
            int x = meshArrayOrdinal % columns; // Bitwise: ordinal & (~(1 << getSizeLevel()))
        }
        */
    }
}
