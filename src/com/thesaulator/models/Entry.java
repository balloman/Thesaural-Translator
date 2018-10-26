// Bernard Allotey 18-10-22

package com.thesaulator.models;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

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

    public Entry(){}

    public static Entry StaticEntryBuilder(@NotNull JSONObject jsonObject) {
        Entry entry = new Entry();
        entry.entryBuilder(jsonObject);
        return entry;
    }

    public void entryBuilder(@NotNull JSONObject jsonObject) {
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
