package com.joedobo27.libs.item;

import com.joedobo27.libs.Skill;
import com.joedobo27.libs.creature.BodyPart;
import com.joedobo27.libs.jdbCommon;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTemplateFactory;
//import org.everit.json.schema.Schema;
//import org.everit.json.schema.loader.SchemaLoader;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.IntStream;

import static com.joedobo27.libs.Constant.BYTES_PRIMITIVE_EMPTY;
import static com.joedobo27.libs.Constant.SHORTS_PRIMITIVE_EMPTY;

public class ItemTemplateImporter {

    private static Map<String, Integer> itemTemplateBuilderNames = new HashMap<>();

    public static void importTemplates(String templateDirectoryPath, String schemapath) {
        File fileFolder = new File(templateDirectoryPath);
        File[] files = fileFolder.listFiles();
        if (files == null)
            return;
        //JSONObject rawSchema = new JSONObject(new JSONTokener(new FileInputStream(
        //        "mods\\jdbCommon\\item_template.schema.json")));
        //SchemaLoader loader = SchemaLoader.builder()
        //        .schemaJson(rawSchema)
        //        .draftV6Support()
        //        .build();
        //Schema schema = loader.load().build();

        Arrays.stream(files)
                .forEach(file -> {
                    Reader reader = getFileReader(file);
                    String fileName = file.getName();
                    JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
                    //schema.validate(jsonObject);
                    itemTemplateImporter(jsonObject, fileName);
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

    static private void itemTemplateImporter(JSONObject jsonObject, String fileName) {
        String itemTemplateBuilderName = "";
        int templateId = -1;
        ItemSize size = ItemSize.NORMAL;
        String name = "";
        String pluralName = "";
        String itemDescriptionSuperb = "superb";
        String itemDescriptionNormal = "good";
        String itemDescriptionBad = "okay";
        String itemDescriptionRotten = "poor";
        String itemDescriptionLong = "";
        ItemTemplateType[] itemTypes = ItemTemplateType.EMPTY;
        short iconNumber = -1;
        BehaviourType behaviourType = BehaviourType.NONE;
        int combatDamage = 0;
        long decayWurmSeconds = -1;
        JSONObject dimensions_cm = null;
        int centimeters_out_x = -1;
        int centimeters_out_y = -1;
        int centimeters_out_z = -1;
        Skill primarySkill = Skill.NONE;
        BodyPart[] bodySpaces = BodyPart.EMPTY;
        String modelName = "model.";
        float difficulty = -1;
        int weightGrams = 0;
        Material material = Material.NONE;
        int value = 1;
        boolean isTraded = false;
        int dyeAmountOverrideGrams = -1;
        JSONObject nutrition_values = null;
        int calories = -1;
        int carbohydrates = -1;
        int fats = -1;
        int proteins = -1;
        int foodGroup = -1;
        int crushTo = -1;
        int pickSeeds = -1;
        JSONObject custom_container_size = null;
        int centimeters_in_x = -1;
        int centimeters_in_y = -1;
        int centimeters_in_z = -1;
        String secondaryItem = "";
        int growsPlant = -1;
        //int initialContainers = -1;
        int alcoholStrength = -1;
        float priceHalfSize = -1.0f;

        try {
            itemTemplateBuilderName = jsonObject.getString("id_factory_name");
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("id_factory_name isn't a valid string. Template %s won't be created.", fileName));
            return;
        }
        itemTemplateBuilderName = formatString(itemTemplateBuilderName);
        templateId = IdFactory.getIdFor(itemTemplateBuilderName, IdType.ITEMTEMPLATE);
        itemTemplateBuilderNames.put(itemTemplateBuilderName, templateId);

        size = ItemSize.getFromString(jsonObject.optString("size", "NORMAL"));

        try {
            name = jsonObject.getString("name");
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("name isn't a valid string. Template %s won't be created.", fileName));
            return;
        }

        pluralName = jsonObject.optString("plural_name", name);

        itemDescriptionSuperb = jsonObject.optString("item_description_superb", "superb");

        itemDescriptionNormal = jsonObject.optString("item_description_normal", "good");

        itemDescriptionBad = jsonObject.optString("item_description_bad", "okay");

        itemDescriptionRotten = jsonObject.optString("item_description_rotten", "poor");

        try {
            itemDescriptionLong = jsonObject.getString("item_description_long");
        }catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("item_description_long isn't a valid string. Template %s won't be created.", fileName));
            return;
        }

        JSONArray item_types_a = jsonObject.optJSONArray("item_types");
        if (item_types_a != null) {
            String[] item_types_s = new String[item_types_a.length()];
            IntStream.range(0, item_types_a.length()).forEach(value1 ->
                    item_types_s[value1] = formatString(item_types_a.optString(value1, "none")));
            itemTypes = ItemTemplateType.getFromStrings(item_types_s, fileName);
        }
        try {
            iconNumber = jsonObject.getNumber("icon_number").shortValue();
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("icon_number isn't a valid short-number. Template %s won't be created.", fileName));
            return;
        }

        try {
            behaviourType = BehaviourType.getFromString(jsonObject.getString("behaviour_type"));
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("behaviour_type isn't valid. Template %s won't be created.", fileName));
            return;
        }

        combatDamage = jsonObject.optInt("combat_damage", 0);

        try {
            decayWurmSeconds = jsonObject.getLong("decay_wurm_seconds");
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("decay_wurm_seconds isn't a valid long-number. Template %s won't be created.", fileName));
            return;
        }


        try {
            dimensions_cm = jsonObject.getJSONObject("dimensions_cm");
            centimeters_out_x = dimensions_cm.getInt("centimeters_x");
            centimeters_out_y = dimensions_cm.getInt("centimeters_y");
            centimeters_out_z = dimensions_cm.getInt("centimeters_z");
        } catch (JSONException e) {
            jdbCommon.logger.warning(e.getMessage() +
                    String.format(" Template %s won't be created.", fileName));
            return;
        }

        primarySkill = Skill.getFromString(jsonObject.optString("primary_skill", "none"));

        JSONArray body_spaces_a = jsonObject.optJSONArray("body_spaces");
        if (body_spaces_a != null) {
            String[] body_spaces_s = new String[body_spaces_a.length()];
            IntStream.range(0, body_spaces_a.length()).forEach(ordinal ->
                    body_spaces_s[ordinal] = body_spaces_a.optString(ordinal, "none"));
            bodySpaces = BodyPart.getFromStrings(body_spaces_s, fileName);
        }

        try {
            modelName = jsonObject.optString("model_Name", "");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("model_Name isn't a valid string. Template %s won't be created.", fileName));
            return;
        }

        try {
            difficulty = jsonObject.getFloat("difficulty");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("difficulty isn't a valid float-number. Template %s won't be created.", fileName));
            return;
        }

        weightGrams = jsonObject.optInt("weight_grams", 0);
        if (weightGrams == 0)
            jdbCommon.logger.warning(String.format("weight_grams is 0 for %s.", fileName));

        material = Material.getFromString(jsonObject.optString("material", "none"));
        if (material.getId() == 0)
            jdbCommon.logger.warning(String.format("material is %s for %s.", material.getName(), fileName));

        value = jsonObject.optInt("value", 1);
        if (value == 1)
            jdbCommon.logger.warning(String.format("value is 1 for %s.", fileName));

        isTraded = jsonObject.optBoolean("is_traded", false);

        dyeAmountOverrideGrams = jsonObject.optInt("dye_amount_override_grams", -1);

        try {
            nutrition_values = jsonObject.optJSONObject("nutrition_values");
            if (nutrition_values != null) {
                calories = nutrition_values.getInt("calories");
                carbohydrates = nutrition_values.getInt("carbohydrates");
                fats = nutrition_values.getInt("fats");
                proteins = nutrition_values.getInt("proteins");
            }
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("nutrition_values error. " + e.getMessage() +
                    " Template %s won't be created.", fileName));
            return;
        }

        foodGroup = ItemTemplateJDB.getAnyFoodGroup(jsonObject.optString("food_group"));

        crushTo = ItemTemplateJDB.getFromString(jsonObject.optString("crush_to", "none")).getId();

        pickSeeds = ItemTemplateJDB.getFromString(jsonObject.optString("pick_seeds", "none")).getId();

        try {
            custom_container_size = jsonObject.optJSONObject("custom_container_size");
            if (custom_container_size != null) {
                centimeters_in_x = custom_container_size.getInt("centimeters_x");
                centimeters_in_y = custom_container_size.getInt("centimeters_y");
                centimeters_in_z = custom_container_size.getInt("centimeters_z");
            }
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("custom_container_size error. " + e.getMessage() +
                    " Template %s won't be created.", fileName));
            return;
        }

        secondaryItem = jsonObject.optString("secondary_item", "");

        growsPlant = ItemTemplateJDB.getFromString(jsonObject.optString("grows_plant", "none")).getId();

        // JSONObject initialContainers = jsonObject.optJSONObject("initialContainers");
        // if (jsonObject.containsKey("initialContainers")) {
        // array of these  new InitialContainer(1294, "thermos", (byte)19)
        // which would be an array of Objects with keys: "template_name", "name", "material".

        alcoholStrength = jsonObject.optInt("alcohol_strength", -1);

        priceHalfSize = jsonObject.optNumber("price_half_size", -1).floatValue();


        // ["id_factory_name", "name", "item_description_long", "icon_number", "behaviour_type", "dimensions_cm",
        // "decay_wurm_seconds", "model_Name", "difficulty", "material"]
        if (Objects.equals(itemTemplateBuilderName, "") || templateId == -1 || Objects.equals(name, "") ||
                Objects.equals(itemDescriptionLong, "") || iconNumber == -1 ||
                behaviourType == BehaviourType.NONE || decayWurmSeconds == -1 || centimeters_out_x == -1 ||
                centimeters_out_y == -1 || centimeters_out_z == -1 || Objects.equals(modelName,"") || difficulty == -1 ||
                material == Material.NONE) {
            jdbCommon.logger.warning(String.format("Error importing %s and it won't be created.", fileName));
            return;
        }

        try {
            ItemTemplate itemTemplate = ItemTemplateFactory.getInstance().createItemTemplate(templateId, size.getId(),
                    name, pluralName, itemDescriptionSuperb, itemDescriptionNormal, itemDescriptionBad,
                    itemDescriptionRotten, itemDescriptionLong,
                    itemTypes == ItemTemplateType.EMPTY ? SHORTS_PRIMITIVE_EMPTY : ItemTemplateType.ItemTypesToShorts(itemTypes),
                    iconNumber, behaviourType.getId(), combatDamage, decayWurmSeconds, centimeters_out_x, centimeters_out_y,
                    centimeters_out_z, primarySkill.getId(),
                    bodySpaces == BodyPart.EMPTY ? BYTES_PRIMITIVE_EMPTY : BodyPart.bodyPartsTobytes(bodySpaces),
                    modelName, difficulty, weightGrams, material.getId(), value, isTraded, dyeAmountOverrideGrams);

                if (calories != -1 || carbohydrates  != -1 || fats  != -1 || proteins  != -1)
                    itemTemplate.setNutritionValues(calories, carbohydrates, fats, proteins);
                if (foodGroup != -1)
                    setFoodGroup(itemTemplate, foodGroup);
                if (crushTo != -1)
                    setCrushTo(itemTemplate, crushTo);
                if (pickSeeds != -1)
                    setPickSeeds(itemTemplate, pickSeeds);
                if (dimensions_cm != null && centimeters_in_x != -1 && centimeters_in_y != -1 && centimeters_in_z != -1)
                    itemTemplate.setContainerSize(centimeters_in_x, centimeters_in_y, centimeters_in_z);
                if (!Objects.equals(secondaryItem, ""))
                    itemTemplate.setSecondryItem(secondaryItem);
                if (growsPlant != -1)
                    setGrowsPlant(itemTemplate, growsPlant);
                if (alcoholStrength != -1)
                    setAlcoholStrength(itemTemplate, alcoholStrength);
                if (priceHalfSize != -1)
                    itemTemplate.priceHalfSize = priceHalfSize;
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | NullPointerException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String formatString(String s) {
        return s.trim().replaceAll(" {2,}", " ");
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

