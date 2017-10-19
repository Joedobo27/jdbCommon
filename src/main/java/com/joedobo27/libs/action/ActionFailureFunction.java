package com.joedobo27.libs.action;

import com.joedobo27.libs.TileUtilities;
import com.wurmonline.math.TilePos;
import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Constants;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.structures.BridgePart;
import com.wurmonline.server.structures.Fence;
import com.wurmonline.server.structures.Structure;
import com.wurmonline.server.villages.Village;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class ActionFailureFunction {

    public static final int FAILURE_FUNCTION_EMPTY = 0;
    public static final int FAILURE_FUNCTION_INSUFFICIENT_STAMINA = 1;
    public static final int FAILURE_FUNCTION_SERVER_BOARDER_TOO_CLOSE = 2;
    public static final int FAILURE_FUNCTION_TILE_GOD_PROTECTED = 3;
    public static final int FAILURE_FUNCTION_PVE_VILLAGE_ENEMY_TILE_ACTION = 4;
    public static final int FAILURE_FUNCTION_PVP_VILLAGE_ENEMY_TILE_ACTION = 5;
    public static final int FAILURE_FUNCTION_ROCK_MESH_AND_CAVE_CEILING_TOO_CLOSE = 6;
    public static final int FAILURE_FUNCTION_CORNER_OCCUPIED_BY_FENCE = 7;
    public static final int FAILURE_FUNCTION_TILE_OCCUPIED_BY_HOUSE = 8;
    public static final int FAILURE_FUNCTION_TILE_OCCUPIED_BY_BRIDGE_SUPPORT = 9;
    public static final int FAILURE_FUNCTION_TILE_OCCUPIED_BY_BRIDGE_EXIT = 10;
    public static final int FAILURE_FUNCTION_TILE_OCCUPIED_BY_CAVE_ENTRANCE = 11;
    public static final int FAILURE_FUNCTION_IS_DIGGING_ROCK = 12;
    public static final int FAILURE_FUNCTION_NO_DIRT_NEARBY = 13;
    public static final int FAILURE_FUNCTION_NO_CONCRETE_NEARBY = 14;
    public static final int FAILURE_FUNCTION_CAVE_FLOOR_AND_CEILING_PROXIMITY = 15;
    public static final int FAILURE_FUNCTION_CAVE_ENTRANCE_BORDER = 16;
    public static final int FAILURE_FUNCTION_PAVING_DEPTH = 17;
    public static final int FAILURE_FUNCTION_PARTIAL_PAVER = 18;
    public static final int FAILURE_FUNCTION_NULL_TARGET_TILE = 19;
    public static final int FAILURE_FUNCTION_NULL_ACTIVE_ITEM = 20;


    private final String name;
    private final Function<ActionMaster, Boolean> function;
    private static HashMap<Integer, ActionFailureFunction> failureFunctions = new HashMap<>();

    private ActionFailureFunction(String name, Function<ActionMaster, Boolean> function) {
        this.name = name;
        this.function = function;
    }

    public static Function<ActionMaster, Boolean> getFunction(int functionId) {
        if (failureFunctions.containsKey(functionId))
            return failureFunctions.get(functionId).function;
        else
            return failureFunctions.get(0).function;
    }

    public static Function<ActionMaster, Boolean> getFunction(String functionName) {
        Function<ActionMaster, Boolean> toReturn = failureFunctions.values().stream()
                .filter(integerActionFailureFunctionEntry -> Objects.equals(
                        integerActionFailureFunctionEntry.name, functionName))
                .map(integerActionFailureFunctionEntry -> integerActionFailureFunctionEntry.function)
                .findFirst()
                .orElseGet(null);
        if (toReturn == null)
            toReturn = failureFunctions.get(0).function;
        return toReturn;
    }

    public Function<ActionMaster, Boolean> getFunction() {
        return function;
    }

    static {
        failureFunctions.put(0, new ActionFailureFunction("FAILURE_FUNCTION_EMPTY", null));
        failureFunctions.put(1, new ActionFailureFunction("FAILURE_FUNCTION_INSUFFICIENT_STAMINA",
                (ActionMaster actionMaster) -> {
                    if (actionMaster.getPerformer().getStatus().getStamina() < actionMaster.getMinimumStamina()) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage(
                                "You don't have enough stamina to " + actionMaster.getAction().getActionString() + ".");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(2, new ActionFailureFunction("FAILURE_FUNCTION_SERVER_BOARDER_TOO_CLOSE",
                (ActionMaster actionMaster) -> {
                    if (actionMaster.getTargetTile().x < 0 || actionMaster.getTargetTile().x > 1 << Constants.meshSize ||
                            actionMaster.getTargetTile().y < 0 || actionMaster.getTargetTile().y > 1 << Constants.meshSize) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage(
                                "You can't " + actionMaster.getAction().getActionString() + " this close to the server boarder.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(3, new ActionFailureFunction("FAILURE_FUNCTION_TILE_GOD_PROTECTED",
                (ActionMaster actionMaster) -> {
                    if (Zones.isTileProtected(actionMaster.getTargetTile().x, actionMaster.getTargetTile().y)) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "This tile is protected by the gods. You can not " + actionMaster.getAction().getActionString() + " here.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(4, new ActionFailureFunction("FAILURE_FUNCTION_PVE_VILLAGE_ENEMY_TILE_ACTION",
                (ActionMaster actionMaster) -> {
                    Village village = Zones.getVillage(actionMaster.getTargetTile().x, actionMaster.getTargetTile().y,
                            actionMaster.getPerformer().isOnSurface());
                    if (village != null && !village.isActionAllowed(actionMaster.action.getNumber(), actionMaster.getPerformer(),
                            false,
                            TileUtilities.getSurfaceEncodedValue(actionMaster.getTargetTile()), 0) &&
                            !Zones.isOnPvPServer(actionMaster.getTargetTile().x, actionMaster.getTargetTile().y)) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "This tile is controlled by a deed which hasn't given you permission to change it.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(5, new ActionFailureFunction("FAILURE_FUNCTION_PVP_VILLAGE_ENEMY_TILE_ACTION",
                (ActionMaster actionMaster) -> {
                    Village village = Zones.getVillage(actionMaster.getTargetTile().x, actionMaster.getTargetTile().y,
                            actionMaster.getPerformer().isOnSurface());
                    if (village != null && !village.isActionAllowed(actionMaster.action.getNumber(), actionMaster.getPerformer(),
                            false, TileUtilities.getSurfaceEncodedValue(actionMaster.getTargetTile()), 0) &&
                            !village.isEnemy(actionMaster.getPerformer()) && actionMaster.getPerformer().isLegal()) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "That would be illegal here. You can check the settlement token for the local laws.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(6, new ActionFailureFunction("FAILURE_FUNCTION_ROCK_MESH_AND_CAVE_CEILING_TOO_CLOSE",
                (ActionMaster actionMaster) -> {
                    if (!actionMaster.getPerformer().isOnSurface())
                        return false;
                    if (TileUtilities.getCaveFloorHeight(actionMaster.getTargetTile()) +
                            TileUtilities.getCaveCeilingOffset(actionMaster.getTargetTile()) +
                            1 >= TileUtilities.getRockHeight(actionMaster.getTargetTile())) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "A cave is preventing the " + actionMaster.getAction().getActionString() + " action.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(7, new ActionFailureFunction("FAILURE_FUNCTION_CORNER_OCCUPIED_BY_FENCE",
                (ActionMaster actionMaster) -> {
                    Tiles.TileBorderDirection[] tileBorderDirections = {Tiles.TileBorderDirection.DIR_HORIZ,
                            Tiles.TileBorderDirection.DIR_HORIZ, Tiles.TileBorderDirection.DIR_DOWN,
                            Tiles.TileBorderDirection.DIR_DOWN};
                    TilePos[] fenceTiles = {actionMaster.getTargetTile(), actionMaster.getTargetTile().West(),
                            actionMaster.getTargetTile().North(), actionMaster.getTargetTile()};
                    if (IntStream.range(0, fenceTiles.length)
                            .anyMatch(value -> {
                                VolaTile volaTile1 = Zones.getOrCreateTile(fenceTiles[value],
                                        actionMaster.getPerformer().isOnSurface());
                                Fence[] fences = volaTile1.getFencesForDir(tileBorderDirections[value]);
                                return fences != null && fences.length > 0;
                            })) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "A "+actionMaster.getAction().getActionString()+
                                " action can't modify a corner occupied by a fence.");
                        return true;
                    } else
                        return false;
                }));
        failureFunctions.put(8, new ActionFailureFunction("FAILURE_FUNCTION_IS_OCCUPIED_BY_HOUSE",
                (ActionMaster actionMaster) -> {
                    TilePos[] buildings = {actionMaster.getTargetTile(), actionMaster.getTargetTile().West(),
                            actionMaster.getTargetTile().NorthWest(), actionMaster.getTargetTile().North()};
                    if (IntStream.range(0,4)
                            .anyMatch(value -> {
                                VolaTile volaTile = Zones.getTileOrNull(buildings[value],
                                        actionMaster.getPerformer().isOnSurface());
                                if (volaTile == null)
                                    return false;
                                Structure structure = volaTile.getStructure();
                                return structure != null && volaTile.getStructure().isTypeHouse();
                            })) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "A "+actionMaster.getAction().getActionString()+
                                " action can't modify a corner or tile occupied by a house.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(9, new ActionFailureFunction("FAILURE_FUNCTION_TILE_OCCUPIED_BY_BRIDGE_SUPPORT",
                (ActionMaster actionMaster) -> {
                    TilePos[] buildings = {actionMaster.getTargetTile(), actionMaster.getTargetTile().West(),
                            actionMaster.getTargetTile().NorthWest(), actionMaster.getTargetTile().North()};
                    if (IntStream.range(0,4)
                            .anyMatch(value -> {
                                VolaTile volaTile = Zones.getTileOrNull(buildings[value],
                                        actionMaster.getPerformer().isOnSurface());
                                if (volaTile == null)
                                    return false;
                                BridgePart[] bridgeParts = volaTile.getBridgeParts();
                                if (bridgeParts == null || bridgeParts.length == 0)
                                    return false;
                                return Arrays.stream(bridgeParts)
                                        .anyMatch(bridgePart -> bridgePart.getType().isSupportType());
                            })) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "A "+actionMaster.getAction().getActionString()+
                                " action can't modify a corner or tile occupied by a bridge support.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(10, new ActionFailureFunction("FAILURE_FUNCTION_TILE_OCCUPIED_BY_BRIDGE_EXIT",
                (ActionMaster actionMaster) -> {
                    TilePos[] bridgeExits = {actionMaster.getTargetTile().West(),
                            actionMaster.getTargetTile().North(), actionMaster.getTargetTile().East(),
                            actionMaster.getTargetTile().South()};
                    ArrayList<Function<BridgePart, Boolean>> functions = new ArrayList<>(Arrays.asList(
                            BridgePart::hasEastExit, BridgePart::hasWestExit, BridgePart::hasSouthExit,
                            BridgePart::hasNorthExit));
                    if (IntStream.range(0, functions.size())
                            .anyMatch(value -> {
                                VolaTile volaTile = Zones.getTileOrNull(bridgeExits[value],
                                        actionMaster.getPerformer().isOnSurface());
                                if (volaTile == null)
                                    return false;
                                BridgePart[] bridgeParts = volaTile.getBridgeParts();
                                if (bridgeParts == null || bridgeParts.length == 0)
                                    return false;
                                return Arrays.stream(bridgeParts)
                                        .anyMatch(bridgePart -> functions.get(value).apply(bridgePart));
                            })){
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "A "+actionMaster.getAction().getActionString()+
                                " action can't modify a corner or tile occupied by a bridge exit.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(11, new ActionFailureFunction("FAILURE_FUNCTION_IS_OCCUPIED_BY_CAVE_ENTRANCE",
                (ActionMaster actionMaster) -> {
                    if (!actionMaster.getPerformer().isOnSurface())
                        return false;
                    TilePos[] caveOpenings = {actionMaster.getTargetTile(), actionMaster.getTargetTile().West(),
                            actionMaster.getTargetTile().NorthWest(), actionMaster.getTargetTile().North()};
                    if (IntStream.range(0,4)
                            .anyMatch(value -> {
                                Tiles.Tile tileType = Tiles.getTile(TileUtilities.getSurfaceTypeId(caveOpenings[value]));
                                return tileType.isCaveDoor() || tileType.getId() == Tiles.Tile.TILE_HOLE.id;
                            })) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "A "+actionMaster.getAction().getActionString()+
                                " action can't modify a corner or tile occupied by a cave entrance.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(12, new ActionFailureFunction("FAILURE_FUNCTION_IS_DIGGING_ROCK",
                (ActionMaster actionMaster) -> {
                    if (TileUtilities.getDirtDepth(actionMaster.getTargetTile()) <= 0){
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "You can't do "+actionMaster.getAction().getActionEntry().getVerbString()+
                                " in rock.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(13, new ActionFailureFunction("FAILURE_FUNCTION_NO_DIRT_NEARBY",
                (ActionMaster actionMaster) -> {
                    Item[] groundItems = actionMaster.getGroundItems(ItemList.dirtPile, actionMaster.getTargetTile());
                    Item[] inventoryItems = actionMaster.getInventoryItems(ItemList.dirtPile);
                    if ((groundItems == null || groundItems.length == 0) && (inventoryItems == null ||
                            inventoryItems.length == 0)) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "There is no dirt nearby to do "+actionMaster.getAction().getActionEntry().getVerbString()+
                                " with.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(14, new ActionFailureFunction("FAILURE_FUNCTION_NO_CONCRETE_NEARBY",
                (ActionMaster actionMaster) -> {
                    Item[] groundItems = actionMaster.getGroundItems(ItemList.concrete, actionMaster.getTargetTile());
                    Item[] inventoryItems = actionMaster.getInventoryItems(ItemList.concrete);
                    if ((groundItems == null || groundItems.length == 0) && (inventoryItems == null ||
                            inventoryItems.length == 0)) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "There is no concrete nearby to do "+actionMaster.getAction().getActionEntry().getVerbString()+
                                " with.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(15, new ActionFailureFunction("FAILURE_FUNCTION_CAVE_FLOOR_AND_CEILING_PROXIMITY",
                (ActionMaster actionMaster) -> {
                    if (!actionMaster.getPerformer().isOnSurface() &&
                            TileUtilities.getCaveCeilingOffset(actionMaster.getTargetTile()) <= 20) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "The cave floor and ceiling can't be any closer.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(16, new ActionFailureFunction("FAILURE_FUNCTION_CAVE_ENTRANCE_BORDER",
                (ActionMaster actionMaster) -> {
                    TilePos tilePos = actionMaster.getTargetTile();
                    int surfaceHeight = TileUtilities.getSurfaceHeight(tilePos);
                    if (surfaceHeight == TileUtilities.getCaveFloorHeight(tilePos) && surfaceHeight ==
                            TileUtilities.getRockHeight(tilePos)) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "The cave entrance boarder tile can't be changed.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(17, new ActionFailureFunction("FAILURE_FUNCTION_PAVING_DEPTH",
                (actionMaster -> {
                    TilePos[] tilePoss = {actionMaster.getTargetTile(), actionMaster.getTargetTile().East(),
                    actionMaster.getTargetTile().SouthEast(), actionMaster.getTargetTile().South()};
                    if (Arrays.stream(tilePoss)
                            .anyMatch(tilePos -> TileUtilities.getSurfaceHeight(tilePos) < -100)) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                        "Tiles deeper then -100 can't be paved.");
                        return true;
                    }
                    return false;
                })));
        failureFunctions.put(18, new ActionFailureFunction("FAILURE_FUNCTION_PARTIAL_PAVER",
                actionMaster -> {
                    if (actionMaster.getActiveTool().getTemplateId() != ItemList.stoneChisel &&
                            actionMaster.getActiveTool().getWeightGrams() <
                                    actionMaster.getActiveTool().getTemplate().getWeightGrams()){
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage(""+
                                "The amount of " + actionMaster.getActiveTool().getName() +
                                " is too little to pave. You may need to combine them with other " +
                                actionMaster.getActiveTool().getTemplate().getPlural() + ".");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(19, new ActionFailureFunction("FAILURE_FUNCTION_NULL_TARGET_TILE",
                actionMaster -> {
                    if (actionMaster.getTargetTile() == null) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage("" +
                                "Something went wrong, sorry.");
                        return true;
                    }
                    return false;
                }));
        failureFunctions.put(20, new ActionFailureFunction("FAILURE_FUNCTION_NULL_ACTIVE_ITEM",
                actionMaster -> {
                    if (actionMaster.getActiveTool() == null) {
                        actionMaster.getPerformer().getCommunicator().sendNormalServerMessage(""+
                                "Something went wrong, sorry.");
                        return true;
                    }
                    return false;
                }));
    }

}
