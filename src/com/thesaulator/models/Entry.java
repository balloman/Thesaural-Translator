// Bernard Allotey 18-10-22

package com.thesaulator.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Entry {
    private String word;
    private boolean isTranslatable;
    private String definition;
    private String example;
    private ArrayList<String> synonyms;

    public Entry(String word, boolean isTranslatable, String definition, String example,
            ArrayList<String> synonyms) {
        this.word = word;
        this.isTranslatable = isTranslatable;
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
    }

    private static final Collection<String> PROHIBITED = Arrays.asList(
            "determiner",
            "exclamation",
            "pronoun",
            "conjunction");

    public Entry(){}

    public static Entry StaticEntryBuilder(@NotNull JSONObject jsonObject) {
        Entry entry = new Entry();
        entry.entryBuilder(jsonObject);
        return entry;
    }

    public static Entry StaticEntryBuilder(@NotNull JsonElement jsonElement) {
        Entry entry = new Entry();
        entry.entryBuilder(jsonElement);
        return entry;
    }

    private void entryBuilder(@NotNull JSONObject jsonObject) {
        this.setWord((String) jsonObject.get("word"));
        JSONObject meaning = ((JSONObject) jsonObject.get("meaning"));
        if (meaning.containsKey("determiner") || meaning.containsKey("exclamation") || meaning.containsKey("pronoun")
                || meaning.containsKey("conjunction")) {
            this.setTranslatable(false);
        } else {
            this.setTranslatable(true);
        }
        JSONObject retrievedDefinition = (JSONObject) ((JSONArray)
                meaning.values().toArray()[0]).get(0);
        this.setDefinition((String) retrievedDefinition.get("definition"));
        this.setExample((String) retrievedDefinition.get("example"));
        this.setSynonyms((JSONArray) retrievedDefinition.get("synonyms"));
        if (this.getSynonyms() == null) {
            this.setTranslatable(false);
        }
    }

    private void entryBuilder(@NotNull JsonElement jsonElement) {
        JsonObject object = (JsonObject) jsonElement;
        this.setWord(object.get("word").getAsString());
        JsonObject definitions = object.get("meaning").getAsJsonObject();
        JsonObject retrievedDefinition = definitions
                .getAsJsonArray((String) definitions.keySet().toArray()[0]).get(0).getAsJsonObject();
        if (definitions.keySet().containsAll(PROHIBITED)) {

        }
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isTranslatable() {
        return isTranslatable;
    }

    public void setTranslatable(boolean translatable) {
        isTranslatable = translatable;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
    }
}
