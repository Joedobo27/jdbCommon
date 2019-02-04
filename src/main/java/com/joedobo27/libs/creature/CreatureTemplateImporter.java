package com.joedobo27.libs.creature;

import com.joedobo27.libs.Constant;
import com.joedobo27.libs.Skill;
import com.joedobo27.libs.item.ItemTemplateJDB;
import com.joedobo27.libs.item.Material;
import com.joedobo27.libs.jdbCommon;
import com.wurmonline.server.creatures.CreatureTemplate;
import com.wurmonline.server.creatures.CreatureTemplateFactory;
import com.wurmonline.server.creatures.ai.CreatureAI;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.skills.Skills;
import com.wurmonline.server.skills.SkillsFactory;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modsupport.IdFactory;
import org.gotti.wurmunlimited.modsupport.IdType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.IntStream;

public class CreatureTemplateImporter {

    public static void importTemplates(String templateDirectoryPath, String schemapath,
                                       HashMap<String, CreatureAI> creatureAIHashMap) {
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
                    creatureTemplateImporter(jsonObject, fileName, creatureAIHashMap);
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

    static private void creatureTemplateImporter(JSONObject jsonObject, String fileName,
                                                 HashMap<String, CreatureAI> creatureAIHashMap) {
        String idFactoryName = "";
        int creautreTemplateId = -1;
        String name = "";
        String pluralName = "";
        String longDescription = "";
        String modelName = "model.";
        CreatureTemplateType[] templateTypes = null;
        BodyType bodyType = null;
        Skills skills = SkillsFactory.createSkills(name);
        short visionRange = 5;
        CreatureSex npcSex = CreatureSex.NEUTRAL;
        JSONObject creatureSize;
        short centimetersHigh = 0;
        short centimetersLong = 0;
        short centimetersWide = 0;
        String deathSoundMale = "sound.";
        String deathSoundFemale = "sound.";
        String hitSoundMale = "sound.";
        String hitSoundFemale = "sound.";
        float naturalArmor = 0f;
        float handDamage = 0f;
        float kickDamage = 0f;
        float biteDamage = 0f;
        float headDamage = 0f;
        float breathDamage = 0f;
        float speed = 0f;
        int moveRate = 0;
        ItemTemplateJDB[] itemsButchered = null;
        int maxHuntDistance;
        int aggressive = 0;
        Material meatMaterial = Material.NONE;
        float alignment = 99.9f;
        float baseCombatRating = 10f;
        CombatDamageType damageType = null;
        boolean isNoSkillGain = true;
        boolean isNoCorpse = false;

        try {
            idFactoryName = jsonObject.getString("id_factory_name");
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("id_factory_name isn't a valid string. Template %s won't be created.", fileName));
            return;
        }
        if(!creatureAIHashMap.containsKey(idFactoryName)) {
            jdbCommon.logger.warning(
                    String.format("id_factory_name doesn't match any CreatureAI. Template %s won't be created.", fileName));
            return;
        }

        idFactoryName = formatString(idFactoryName);
        creautreTemplateId = IdFactory.getIdFor(idFactoryName, IdType.CREATURETEMPLATE);

        try {
            name = jsonObject.getString("name");
        } catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("name isn't a valid string. Template %s won't be created.", fileName));
            return;
        }

        pluralName = jsonObject.optString("plural_name", name);

        try {
            longDescription = jsonObject.getString("description_long");
        }catch (JSONException e) {
            jdbCommon.logger.warning(
                    String.format("description_long isn't a valid string. Template %s won't be created.", fileName));
            return;
        }

        try {
            modelName = jsonObject.optString("model_Name", "");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("model_Name isn't a valid string. Template %s won't be created.", fileName));
            return;
        }

        JSONArray creature_types_a = jsonObject.optJSONArray("creature_types");
        if (creature_types_a != null) {
            String[] creature_types_s = new String[creature_types_a.length()];
            IntStream.range(0, creature_types_a.length()).forEach(value1 ->
                    creature_types_s[value1] = formatString(creature_types_a.optString(value1, "none")));
            templateTypes = CreatureTemplateType.getFromStrings(creature_types_s, fileName);
        }
        if (templateTypes == null) {
            jdbCommon.logger.warning(String.format("creature_types isn't valid. Template %s won't be created.", fileName));
            return;
        }

        try {
            bodyType = BodyType.getFromString(jsonObject.getString("body_type"));
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("body_type isn't valid. Template %s won't be created.", fileName));
            return;
        }

        JSONArray skills_a = jsonObject.getJSONArray("skills");
        if (skills_a != null && skills_a.length() > 0) {
            // Check if any of the JSONObjects inside the JSONArray won't load right.
            IntStream.range(0, skills_a.length())
                    .boxed()
                    .map(index -> skills_a.optJSONObject(index))
                    .filter(jsonObject1 -> jsonObject1 == null)
                    .forEach(value -> jdbCommon.logger.warning(
                            "One or more Skills entries arn't in JSONObject format and won't be created."));

            // Check if any of the skill_name or skill_level won't load right.
            IntStream.range(0, skills_a.length())
                    .boxed()
                    .map(index -> skills_a.optJSONObject(index))
                    .filter(jsonObject1 -> jsonObject1 != null)
                    .filter(jsonObject1 -> Arrays.stream(Skill.values())
                            .noneMatch(skill -> Objects.equals(
                                    jsonObject1.optString("skill_name", "none").toLowerCase(),
                                    skill.getName())))
                    .filter(jsonObject1 -> "none".equalsIgnoreCase(jsonObject1.optString("skill_name", "none")))
                    .filter(jsonObject1 -> jsonObject1.optFloat("skill_level", -1f) == -1f)
                    .forEach(jsonObject1 -> jdbCommon.logger.warning(String.format(
                            "Skills object %s - %d isn't valid and it won't be created.",
                            jsonObject1.optString("skill_name", "none"),
                            jsonObject1.optFloat("skill_level", -1f) == -1f)));

            // Add skills to the Skills object.
            IntStream.range(0, skills_a.length())
                    .boxed()
                    .map(index -> skills_a.optJSONObject(index))
                    .filter(jsonObject1 -> jsonObject1 != null)
                    .filter(jsonObject1 -> Arrays.stream(Skill.values())
                            .anyMatch(skill -> Objects.equals(
                                    jsonObject1.optString("skill_name", "none").toLowerCase(),
                                    skill.getName())))
                    .filter(jsonObject1 -> !"none".equalsIgnoreCase(jsonObject1.optString("skill_name", "none")))
                    .filter(jsonObject1 -> jsonObject1.optFloat("skill_level", -1f) != -1f)
                    .forEach(jsonObject1 -> skills.learnTemp(Skill.getFromString(
                            jsonObject1.optString("skill_name", "none")).getId(),
                            jsonObject1.optFloat("skill_level", 0f)));
        } else {
            jdbCommon.logger.warning(String.format("skills isn't valid. Template %s won't be created.", fileName));
            return;
        }

        visionRange = jsonObject.optNumber("vision_range", 5).shortValue();

        npcSex = CreatureSex.getSex(jsonObject.optString("npc_sex", "MALE"));

        try {
            creatureSize = jsonObject.getJSONObject("creature_size");
            centimetersHigh = creatureSize.getNumber("centimeters_high").shortValue();
            centimetersLong = creatureSize.getNumber("centimeters_long").shortValue();
            centimetersWide = creatureSize.getNumber("centimeters_wide").shortValue();
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("creature_size isn't valid. Template %s won't be created.", fileName));
            return;
        }

        try {
            deathSoundMale = jsonObject.getString("death_sound_male");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("death_sound_male isn't valid. Template %s won't be created.", fileName));
            return;
        }

        try {
            deathSoundFemale = jsonObject.getString("death_sound_female");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("death_sound_female isn't valid. Template %s won't be created.", fileName));
            return;
        }

        try {
            hitSoundMale = jsonObject.getString("hit_sound_male");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("hit_sound_male isn't valid. Template %s won't be created.", fileName));
            return;
        }

        try {
            hitSoundFemale = jsonObject.getString("hit_sound_female");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("hit_sound_female isn't valid. Template %s won't be created.", fileName));
            return;
        }

        naturalArmor = jsonObject.optFloat("natural_armor", 0.3f);

        handDamage = jsonObject.optFloat("hand_damage", 1f);

        kickDamage = jsonObject.optFloat("kick_damage", 1f);

        biteDamage = jsonObject.optFloat("bite_damage", 1f);

        headDamage = jsonObject.optFloat("head_damage", 1f);

        breathDamage = jsonObject.optFloat("breath_damage", 1f);

        speed = jsonObject.optFloat("speed", 1f);

        moveRate = jsonObject.optInt("move_rate", 100);

        JSONArray itemsButchered_a = jsonObject.optJSONArray("items_butchered");
        if (itemsButchered_a != null) {
            String[] itemsButchered_s = new String[itemsButchered_a.length()];
            IntStream.range(0, itemsButchered_a.length())
                    .forEach(value1 -> itemsButchered_s[value1] = itemsButchered_a.optString(value1, "none"));
            itemsButchered = ItemTemplateJDB.getFromStrings(itemsButchered_s, fileName);
        }

        maxHuntDistance = jsonObject.optInt("max_hunt_distance", 10);

        aggressive = jsonObject.optInt("aggressive", 80);

        meatMaterial = Material.getFromString(jsonObject.optString("meat_material", "none"));

        try {
            alignment = jsonObject.getFloat("alignment");
        } catch (JSONException e) {
            jdbCommon.logger.warning(String.format("alignment isn't valid. Template %s won't be created.", fileName));
            return;
        }

        baseCombatRating = jsonObject.optFloat("base_combat_rating", 5f);

        damageType = CombatDamageType.getFromString(jsonObject.optString("damage_type", "none"));

        isNoSkillGain = jsonObject.optBoolean("is_no_skill_gain", true);

        isNoCorpse = jsonObject.optBoolean("is_no_corpse", false);

        try {
            CreatureTemplate temp = CreatureTemplateFactory.getInstance().createCreatureTemplate(creautreTemplateId, name,
                    pluralName, longDescription, modelName, CreatureTemplateType.creatureTypesToInts(templateTypes),
                    bodyType.getId(), skills, visionRange, npcSex.getId(), centimetersHigh, centimetersLong, centimetersWide,
                    deathSoundMale, deathSoundFemale, hitSoundMale, hitSoundFemale, naturalArmor, handDamage, kickDamage,
                    biteDamage, headDamage, breathDamage, speed, moveRate,
                    itemsButchered_a == null || itemsButchered.length < 1
                            ? Constant.INTS_PRIMITIVE_EMPTY : ItemTemplateJDB.templatesToInts(itemsButchered),
                    maxHuntDistance, aggressive, meatMaterial.getId());
            temp.setAlignment(alignment);
            temp.setBaseCombatRating(baseCombatRating);
            temp.setCombatDamageType(damageType == CombatDamageType.NONE ? 0 : damageType.getId());
            temp.setNoSkillgain(isNoSkillGain);
            temp.setCreatureAI(creatureAIHashMap.get(idFactoryName));
            if (isNoCorpse){
                setIsNoCorpse(temp, isNoCorpse);
            }
        } catch (IOException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException |
                InvocationTargetException e) {
            jdbCommon.logger.warning(e.getMessage());
            return;
        }
    }

    private static void setIsNoCorpse(CreatureTemplate creatureTemplate, boolean isNoCorpse) throws ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        ReflectionUtil.setPrivateField(creatureTemplate, ReflectionUtil.getField(
                Class.forName("com.wurmonline.server.creatures.CreatureTemplate"), "noCorpse"), isNoCorpse);
    }

    private static String formatString(String s) {
        return s.trim().replaceAll(" {2,}", " ");
    }
}
