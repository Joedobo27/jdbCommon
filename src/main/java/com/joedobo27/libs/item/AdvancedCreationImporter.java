package com.joedobo27.libs.item;

import com.joedobo27.libs.Skill;
import com.joedobo27.libs.jdbCommon;
import com.wurmonline.server.items.AdvancedCreationEntry;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.CreationCategories;
//import org.everit.json.schema.Schema;
//import org.everit.json.schema.loader.SchemaLoader;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.Executor;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AdvancedCreationImporter {


    public static void importAdvancedCreation(String templateDirectoryPath, String schemapath) {
        File fileFolder = new File(templateDirectoryPath);
        File[] files = fileFolder.listFiles();
        if (files == null)
            return;
        //JSONObject rawSchema = new JSONObject(new JSONTokener(new FileInputStream(
        //        "mods\\jdbCommon\\advanced_creation.schema.json")));
        //SchemaLoader loader = SchemaLoader.builder()
        //       .schemaJson(rawSchema)
        //        .draftV6Support()
        //        .build();
        //Schema schema = loader.load().build();

        Arrays.stream(files)
                .forEach(file -> {
                    Reader reader = getFileReader(file);
                    String fileName = file.getName();
                    JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
                    //schema.validate(jsonObject);
                    advancedCreationImporter(jsonObject, fileName);
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

    static private void advancedCreationImporter(JSONObject jsonObject, String fileName) throws RuntimeException {
        String itemTemplateBuilderName = "";
        Skill skill = Skill.NONE;
        ItemTemplateJDB source = ItemTemplateJDB.NONE;
        ItemTemplateJDB target = ItemTemplateJDB.NONE;
        int templateId = -1;
        boolean destroyTarget = false;
        boolean useCapacity = false;
        float percentageLost = 0.0f;
        boolean destroyBoth = false;
        boolean createOnGround = true;
        int customCutOffChance = 0;
        double minimumSkill = 0.0d;
        CreationCategories creationCategories = CreationCategories.UNKNOWN;
        CreationRequirement[] creationRequirements = new CreationRequirement[0];

        try {
            itemTemplateBuilderName = jsonObject.getString("id_factory_name");
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("id_factory_name isn't a valid string. Advanced creation %s won't be created.", fileName));
            return;
        }
        itemTemplateBuilderName = formatString(itemTemplateBuilderName);
        templateId = IdFactory.getIdFor(itemTemplateBuilderName, IdType.ITEMTEMPLATE);
        if (templateId == -1) {
            jdbCommon.logger.warning(
                    String.format("Template id error. Advanced creation %s won't be created.", fileName));
            return;
        }

        try {
            skill = Skill.getFromString(jsonObject.getString("skill"));
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("skill isn't valid. Advanced creation %s won't be created.", fileName));
            return;
        }

        try {
            source = ItemTemplateJDB.getFromString(jsonObject.getString("source"));
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("source isn't valid. Advanced creation %s won't be created.", fileName));
            return;
        }

        try {
            target = ItemTemplateJDB.getFromString(jsonObject.getString("target"));
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("target isn't valid. Advanced creation %s won't be created.", fileName));
            return;
        }


        destroyTarget = jsonObject.optBoolean("destroy_target", false);

        useCapacity = jsonObject.optBoolean("use_capacity", false);

        percentageLost = jsonObject.optFloat("percentage_lost", 0);

        destroyBoth = jsonObject.optBoolean("destroy_both", false);

        createOnGround = jsonObject.optBoolean("create_on_ground", false);

        customCutOffChance = jsonObject.optInt("custom_cut_off_chance", -1);

        minimumSkill = jsonObject.optDouble("minimum_skill", -1);


        creationCategories = Arrays.stream(CreationCategories.values())
                .filter(creationCategories1 -> Objects.equals(creationCategories1.getCategoryName().toLowerCase(),
                        jsonObject.optString("creation_categories", "Unknown").toLowerCase()))
                .findFirst()
                .orElse(CreationCategories.UNKNOWN);
        JSONArray creation_equirements_a = jsonObject.optJSONArray("creation_requirements");
        if (creation_equirements_a != null) {
            creationRequirements = IntStream.range(0, creation_equirements_a.length())
                    .boxed()
                    .map(index -> creation_equirements_a.getJSONObject(index))
                    .filter((JSONObject jsonObject1) -> jsonObject1.optInt("order", -1) != -1 &&
                            !Objects.equals(jsonObject1.optString("item", "none"), "none") &&
                            jsonObject1.optInt("quantity", -1) != -1)
                    .map(jsonObject1 -> new CreationRequirement(
                            jsonObject1.optInt("order", 0),
                            ItemTemplateJDB.getFromString(jsonObject1.optString("item", "none")).getId(),
                            jsonObject1.optInt("quantity", 0),
                            jsonObject1.optBoolean("consume", false)))
                    .toArray(CreationRequirement[]::new);

        }

        AdvancedCreationEntry creationEntry = CreationEntryCreator.createAdvancedEntry(skill.getId(),
                source.getId(), target.getId(), templateId, destroyTarget, useCapacity, percentageLost,
                destroyBoth, createOnGround, customCutOffChance, minimumSkill, creationCategories);
        if (creationRequirements.length > 0) {
            Arrays.stream(creationRequirements)
                    .forEach(creationEntry::addRequirement);
        }
    }

    private static String formatString(String s) {
        return s.trim().replaceAll(" {2,}", " ");
    }

}
