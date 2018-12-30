package com.joedobo27.libs.item;

import com.joedobo27.libs.Skill;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.CreationCategories;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;

import javax.json.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class AdvancedCreationImporter {

    public static void importAdvancedCreation(String templateDirectoryPath) {
        File fileFolder = new File(templateDirectoryPath);
        File[] files = fileFolder.listFiles();
        if (files == null)
            return;
        Arrays.stream(files)
                .forEach(file -> {
                    Reader reader = getFileReader(file);
                    JsonReader jsonReader = Json.createReader(reader);
                    JsonObject jsonObject = jsonReader.readObject();
                    advancedCreationImporter(jsonObject);
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

    static private void advancedCreationImporter(JsonObject jsonObject) throws RuntimeException {
        Skill skill = Skill.NONE;
        ItemTemplate source = ItemTemplate.NONE;
        ItemTemplate target = ItemTemplate.NONE;
        int templateId = 0;
        boolean destroyTarget = false;
        boolean useCapacity = false;
        float percentageLost = 0.0f;
        boolean destroyBoth = false;
        boolean createOnGround = true;
        int customCutOffChance = 0;
        double minimumSkill = 0.0d;
        CreationCategories creationCategories = CreationCategories.UNKNOWN;
        CreationRequirement[] creationRequirements = new CreationRequirement[0];


        if (jsonObject.containsKey("skill")) {
            skill = Skill.getFromString(jsonObject.getString("skill", "none"));
        }
        if (jsonObject.containsKey("source")) {
            source = ItemTemplate.getFromString(jsonObject.getString("source", "none"));
        }
        if (jsonObject.containsKey("target")) {
            target = ItemTemplate.getFromString(jsonObject.getString("target", "none"));
        }
        if (jsonObject.containsKey("templateName")) {
            String name = jsonObject.getString("templateName");
            templateId = IdFactory.getIdFor(name, IdType.ITEMTEMPLATE);
        }
        if (jsonObject.containsKey("destroyTarget")) {
            destroyTarget = jsonObject.getBoolean("destroyTarget", false);
        }
        if (jsonObject.containsKey("useCapacity")) {
            useCapacity = jsonObject.getBoolean("useCapacity", false);
        }
        if (jsonObject.containsKey("percentageLost")) {
            percentageLost = (float)jsonObject.getJsonNumber("percentageLost").doubleValue();
        }
        if (jsonObject.containsKey("destroyBoth")) {
            destroyBoth = jsonObject.getBoolean("destroyBoth", false);
        }
        if (jsonObject.containsKey("createOnGround")) {
            createOnGround = jsonObject.getBoolean("createOnGround", false);
        }
        if (jsonObject.containsKey("customCutOffChance")) {
            customCutOffChance = jsonObject.getInt("customCutOffChance", 0);
        }
        if (jsonObject.containsKey("minimumSkill")) {
            minimumSkill = jsonObject.getJsonNumber("minimumSkill").doubleValue();
        }
        if (jsonObject.containsKey("creationCategories")) {
            creationCategories = Arrays.stream(CreationCategories.values())
                    .filter(creationCategories1 -> Objects.equals(creationCategories1.getCategoryName().toLowerCase(),
                            jsonObject.getString("creationCategories", "Unknown").toLowerCase()))
                    .findFirst()
                    .orElse(CreationCategories.UNKNOWN);
        }
        if (jsonObject.containsKey("creationRequirements")) {
            if (jsonObject.getJsonArray("creationRequirements").getValueType() != JsonValue.ValueType.ARRAY)
                throw new RuntimeException("nutritionValues object is not in array format.");
            JsonArray array = jsonObject.getJsonArray("creationRequirements");
            creationRequirements = IntStream.range(0, array.size())
                    .boxed()
                    .map(value1 -> {
                        JsonObject jsonObject1 = array.getJsonObject(value1);
                        return new CreationRequirement(
                                jsonObject1.getInt("order"),
                                ItemTemplate.getFromString(jsonObject1.getString("item")).getTemplateId(),
                                jsonObject1.getInt("quantity"),
                                jsonObject1.getBoolean("consume"));
                    })
                    .toArray(CreationRequirement[]::new);
        }

        AdvancedCreationEntry creationEntry = CreationEntryCreator.createAdvancedEntry(skill.getId(),
                source.getTemplateId(), target.getTemplateId(), templateId, destroyTarget, useCapacity, percentageLost,
                destroyBoth, createOnGround, customCutOffChance, minimumSkill, creationCategories);
        if (creationRequirements.length > 0) {
            Arrays.stream(creationRequirements)
                    .forEach(creationEntry::addRequirement);
        }
    }

}
