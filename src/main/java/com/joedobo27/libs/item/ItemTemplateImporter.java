package com.joedobo27.libs.item;

import com.joedobo27.libs.Skill;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import javax.json.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.IntStream;

import static com.joedobo27.libs.Constant.INTS_EMPTY;

public class ItemTemplateImporter {

    private static Map<String, Integer> itemTemplateBuilderNames = new HashMap<>();

    public static void ImportAllTemplates(String templateDirectoryPath) {
        File fileFolder = new File(templateDirectoryPath);
        File[] files = fileFolder.listFiles();
        if (files == null)
            return;
        Arrays.stream(files)
                .forEach(file -> {
                    Reader reader = getFileReader(file);
                    JsonReader jsonReader = Json.createReader(reader);
                    JsonObject jsonObject = jsonReader.readObject();
                    itemTemplateImporter(jsonObject);
                });
    }

    static private Reader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static private void itemTemplateImporter(JsonObject jsonObject) {
        String itemTemplateBuilderName = "";
        int templateId = -1;
        ItemSize size = ItemSize.LARGE;
        String name = "";
        String pluralName = "";
        String itemDescriptionSuperb = "superb";
        String itemDescriptionNormal = "good";
        String itemDescriptionBad = "ok";
        String itemDescriptionRotten = "poor";
        String itemDescriptionLong = "";
        ItemTemplateType[] itemTypes = ItemTemplateType.EMPTY;
        short iconNumber = -1;
        BehaviourType behaviourType = BehaviourType.NONE;
        int combatDamage = 0;
        long decayWurmSeconds = -1;
        int centimetersX = -1;
        int centimetersY = -1;
        int centimetersZ = -1;
        Skill primarySkill = Skill.NONE;
        BodyPart[] bodySpaces = BodyPart.EMPTY;
        String modelName = "model.";
        float difficulty = 0;
        int weightGrams = 0;
        Material material = Material.NONE;
        int value = 0;
        boolean isTraded = false;
        int dyeAmountOverrideGrams = 0;
        int[] nutritionValues = INTS_EMPTY;
        int foodGroup = -1;
        int crushTo = -1;
        int pickSeeds = -1;
        int[] customContainerSize = INTS_EMPTY;
        String secondaryItem = "";
        int growsPlant = -1;
        //int initialContainers = -1;
        int alcoholStrength = -1;

        if (jsonObject.containsKey("templateName")) {
            itemTemplateBuilderName = jsonObject.getString("name");
            templateId = IdFactory.getIdFor(itemTemplateBuilderName, IdType.ITEMTEMPLATE);
            itemTemplateBuilderNames.put(itemTemplateBuilderName, templateId);
        }
        if (jsonObject.containsKey("size"))
            size = ItemSize.getFromString(jsonObject.getString("size", "large"));
        if (jsonObject.containsKey("name"))
            name = jsonObject.getString("name", "");
        if (jsonObject.containsKey("pluralName"))
            pluralName = jsonObject.getString("pluralName", "");
        if (jsonObject.containsKey("itemDescriptionSuperb"))
            itemDescriptionSuperb = jsonObject.getString("itemDescriptionSuperb", "");
        if (jsonObject.containsKey("itemDescriptionNormal"))
            itemDescriptionNormal = jsonObject.getString("itemDescriptionNormal", "");
        if (jsonObject.containsKey("itemDescriptionBad"))
            itemDescriptionBad = jsonObject.getString("itemDescriptionBad", "");
        if (jsonObject.containsKey("itemDescriptionRotten"))
            itemDescriptionRotten = jsonObject.getString("itemDescriptionRotten", "");
        if (jsonObject.containsKey("itemDescriptionLong"))
            itemDescriptionLong = jsonObject.getString("itemDescriptionLong", "");
        if (jsonObject.containsKey("itemTypes")){
            if (jsonObject.getJsonArray("itemTypes").getValueType() != JsonValue.ValueType.ARRAY)
                throw new RuntimeException("itemTypes object is not in array format.");
            JsonArray array = jsonObject.getJsonArray("itemTypes");
            String[] strings = new String[array.size()];
            IntStream.range(0, array.size()).forEach(value1 ->
                    strings[value1] = array.getString(value1, "none"));
            itemTypes = ItemTemplateType.getFromStrings(strings);
        }
        if (jsonObject.containsKey("iconNumber")) {
            if (jsonObject.getJsonNumber("iconNumber").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("iconNumber object is not a number.");
            iconNumber = (short)jsonObject.getInt("iconNumber", 0);
        }
        if (jsonObject.containsKey("behaviourType")) {
            behaviourType = BehaviourType.getFromString(jsonObject.getString("behaviourType", "none"));
        }
        if (jsonObject.containsKey("combatDamage")) {
            if (jsonObject.getJsonNumber("combatDamage").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("combatDamage object is not a number.");
            combatDamage = jsonObject.getInt("combatDamage", 0);
        }
        if (jsonObject.containsKey("decayWurmSeconds")) {
            if (jsonObject.getJsonNumber("decayWurmSeconds").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("decayWurmSeconds object is not a number.");
            decayWurmSeconds = jsonObject.getJsonNumber("decayWurmSeconds").longValue();
        }
        if (jsonObject.containsKey("centimetersX")) {
            if (jsonObject.getJsonNumber("centimetersX").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("centimetersX object is not a number.");
            centimetersX = jsonObject.getJsonNumber("centimetersX").intValue();
        }
        if (jsonObject.containsKey("centimetersY")) {
            if (jsonObject.getJsonNumber("centimetersY").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("centimetersY object is not a number.");
            centimetersY = jsonObject.getJsonNumber("centimetersY").intValue();
        }
        if (jsonObject.containsKey("centimetersZ")) {
            if (jsonObject.getJsonNumber("centimetersZ").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("centimetersZ object is not a number.");
            centimetersZ = jsonObject.getJsonNumber("centimetersZ").intValue();
        }
        if (jsonObject.containsKey("primarySkill")) {
            primarySkill = Skill.getFromString(jsonObject.getString("primarySkill", "none"));
        }
        if (jsonObject.containsKey("bodySpaces")) {
            if (jsonObject.getJsonArray("bodySpaces").getValueType() != JsonValue.ValueType.ARRAY)
                throw new RuntimeException("bodySpaces object is not in array format.");
            JsonArray array = jsonObject.getJsonArray("bodySpaces");
            String[] strings = new String[array.size()];
            IntStream.range(0, array.size()).forEach(value1 ->
                    strings[value1] = array.getString(value1, "none"));
            bodySpaces = BodyPart.getFromStrings(strings);
        }
        if (jsonObject.containsKey("modelName"))
            modelName = jsonObject.getString("modelName", "");
        if (jsonObject.containsKey("difficulty")) {
            if (jsonObject.getJsonNumber("difficulty").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("difficulty object is not a number.");
            difficulty = (float)jsonObject.getJsonNumber("difficulty").doubleValue();
        }
        if (jsonObject.containsKey("weightGrams")) {
            if (jsonObject.getJsonNumber("weightGrams").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("weightGrams object is not a number.");
            weightGrams = jsonObject.getJsonNumber("weightGrams").intValue();
        }
        if (jsonObject.containsKey("material")) {
            material = Material.getMaterialFromName(jsonObject.getString("material", "none"));
        }
        if (jsonObject.containsKey("value")) {
            if (jsonObject.getJsonNumber("value").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("value object is not a number.");
            value = jsonObject.getJsonNumber("value").intValue();
        }
        if (jsonObject.containsKey("isTraded")) {
            if (jsonObject.getJsonObject("isTraded").getValueType() != JsonValue.ValueType.FALSE &&
                    jsonObject.getJsonObject("isTraded").getValueType() != JsonValue.ValueType.TRUE)
                throw new RuntimeException("isTraded is not a boolean");
            isTraded = jsonObject.getBoolean("isTraded");
        }
        if (jsonObject.containsKey("dyeAmountOverrideGrams")) {
            if (jsonObject.getJsonNumber("dyeAmountOverrideGrams").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("dyeAmountOverrideGrams object is not a number.");
            dyeAmountOverrideGrams = jsonObject.getJsonNumber("dyeAmountOverrideGrams").intValue();
        }
        if (jsonObject.containsKey("nutritionValues")) {
            if (jsonObject.getJsonArray("nutritionValues").getValueType() != JsonValue.ValueType.ARRAY)
                throw new RuntimeException("nutritionValues object is not in array format.");
            JsonArray array = jsonObject.getJsonArray("nutritionValues");
            int[] ints = new int[array.size()];
            IntStream.range(0, array.size()).forEach(value1 ->
                    ints[value1] = array.getInt(value1, 0));
            nutritionValues = ints;
        }
        if (jsonObject.containsKey("foodGroup")) {
            if (jsonObject.getJsonNumber("foodGroup").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("foodGroup object is not a number.");
            foodGroup = jsonObject.getJsonNumber("foodGroup").intValue();
        }
        if (jsonObject.containsKey("crushTo")) {
            if (jsonObject.getJsonNumber("crushTo").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("crushTo object is not a number.");
            crushTo = jsonObject.getJsonNumber("crushTo").intValue();
        }
        if (jsonObject.containsKey("pickSeeds")) {
            if (jsonObject.getJsonNumber("pickSeeds").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("pickSeeds object is not a number.");
            pickSeeds = jsonObject.getJsonNumber("pickSeeds").intValue();
        }
        if (jsonObject.containsKey("customContainerSize")) {
            if (jsonObject.getJsonArray("customContainerSize").getValueType() != JsonValue.ValueType.ARRAY)
                throw new RuntimeException("customContainerSize object is not in array format.");
            JsonArray array = jsonObject.getJsonArray("customContainerSize");
            int[] ints = new int[array.size()];
            IntStream.range(0, array.size()).forEach(value1 ->
                    ints[value1] = array.getInt(value1, 0));
            customContainerSize = ints;
        }
        if (jsonObject.containsKey("secondaryItem")) {
            secondaryItem = jsonObject.getString("secondaryItem", "");
        }
        if (jsonObject.containsKey("growsPlant")) {
            if (jsonObject.getJsonNumber("growsPlant").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("growsPlant object is not a number.");
            growsPlant = jsonObject.getJsonNumber("growsPlant").intValue();
        }
        //if (jsonObject.containsKey("initialContainers")) {
        //    if (jsonObject.getJsonObject("initialContainers").getValueType() != JsonValue.ValueType.NUMBER)
        //        throw new RuntimeException("initialContainers object is not a number.");
        //    initialContainers = jsonObject.getJsonNumber("initialContainers").intValue();
        //}
        if (jsonObject.containsKey("alcoholStrength")) {
            if (jsonObject.getJsonNumber("alcoholStrength").getValueType() != JsonValue.ValueType.NUMBER)
                throw new RuntimeException("alcoholStrength object is not a number.");
            alcoholStrength = jsonObject.getJsonNumber("alcoholStrength").intValue();
        }
        if (Objects.equals(itemTemplateBuilderName, "") || templateId == -1 || Objects.equals(name, "") ||
                Objects.equals(pluralName, "") || Objects.equals(itemDescriptionLong, "") || iconNumber == -1 ||
                behaviourType == BehaviourType.NONE || decayWurmSeconds == -1 || centimetersX == -1 ||
                centimetersY == -1 || centimetersZ == -1)
            throw new RuntimeException("Missing a required item template construction value.");

        try {
            ItemTemplate itemTemplate = ItemTemplateFactory.getInstance().createItemTemplate(templateId, size.getId(),
                    name, pluralName, itemDescriptionSuperb, itemDescriptionNormal, itemDescriptionBad,
                    itemDescriptionRotten, itemDescriptionLong, ItemTemplateType.ItemTypesToShorts(itemTypes), iconNumber,
                    behaviourType.getId(), combatDamage, decayWurmSeconds, centimetersX, centimetersY, centimetersZ,
                    primarySkill.getId(), BodyPart.bodyPartsTobytes(bodySpaces), modelName, difficulty, weightGrams,
                    material.getId(), value, isTraded, dyeAmountOverrideGrams);

                if (nutritionValues != INTS_EMPTY)
                    itemTemplate.setNutritionValues(nutritionValues[0], nutritionValues[1], nutritionValues[2],
                            nutritionValues[3]);
                if (foodGroup != -1)
                    setFoodGroup(itemTemplate, foodGroup);
                if (crushTo != -1)
                    setCrushTo(itemTemplate, crushTo);
                if (pickSeeds != -1)
                    setPickSeeds(itemTemplate, pickSeeds);
                if (customContainerSize != INTS_EMPTY)
                    itemTemplate.setContainerSize(customContainerSize[0], customContainerSize[1],
                            customContainerSize[2]);
                if (!Objects.equals(secondaryItem, ""))
                    itemTemplate.setSecondryItem(secondaryItem);
                if (growsPlant != -1)
                    setGrowsPlant(itemTemplate, growsPlant);
                if (alcoholStrength != -1)
                    setAlcoholStrength(itemTemplate, alcoholStrength);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | NullPointerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static void setFoodGroup(ItemTemplate itemTemplate, int foodGroup) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectionUtil.callPrivateMethod(itemTemplate, ReflectionUtil.getMethod(
                Class.forName("com.wurmonline.server.items.ItemTemplate"), "setFoodGroup"), foodGroup);
    }

    private static void setCrushTo(ItemTemplate itemTemplate, int crushTo) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectionUtil.callPrivateMethod(itemTemplate, ReflectionUtil.getMethod(
                Class.forName("com.wurmonline.server.items.ItemTemplate"), "setCrushsTo"), crushTo);
    }

    private static void setPickSeeds(ItemTemplate itemTemplate, int pickSeeds) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectionUtil.callPrivateMethod(itemTemplate, ReflectionUtil.getMethod(
                Class.forName("com.wurmonline.server.items.ItemTemplate"), "setPickSeeds"), pickSeeds);
    }

    private static void setGrowsPlant(ItemTemplate itemTemplate, int growsPlant) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectionUtil.callPrivateMethod(itemTemplate, ReflectionUtil.getMethod(
                Class.forName("com.wurmonline.server.items.ItemTemplate"), "setGrows"), growsPlant);
    }

    private static void setAlcoholStrength(ItemTemplate itemTemplate, int alcoholStrength) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectionUtil.callPrivateMethod(itemTemplate, ReflectionUtil.getMethod(
                Class.forName("com.wurmonline.server.items.ItemTemplate"), "setAlcoholStrength"),
                alcoholStrength);
    }

    public static Map<String, Integer> getItemTemplateBuilderNames() {
        return itemTemplateBuilderNames;
    }
}

