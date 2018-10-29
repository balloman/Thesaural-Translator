// Bernard Allotey 18-10-22

package com.thesaulator.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thesaulator.io.ApiHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Entry {
    private String word;
    private boolean isTranslatable;
    private String definition;
    private String example;
    private List<String> synonyms;

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

    public static Entry StaticEntryBuilder(@NotNull JsonElement jsonElement) {
        Entry entry = new Entry();
        entry.entryBuilder(jsonElement);
        return entry;
    }

    private void entryBuilder(@NotNull JsonElement jsonElement) {
        JsonObject object = (JsonObject) jsonElement;
        this.setWord(object.get("word").getAsString());
        JsonObject definitions = object.get("meaning").getAsJsonObject();
        JsonObject retrievedDefinition = definitions
                .getAsJsonArray((String) definitions.keySet().toArray()[0]).get(0).getAsJsonObject();
        this.setTranslatable(!definitions.keySet().containsAll(PROHIBITED));
        this.setDefinition(retrievedDefinition.get("definition").getAsString());
        if (retrievedDefinition.get("example") != null)
            this.setExample(retrievedDefinition.get("example").getAsString());
        if (retrievedDefinition.get("synonyms") != null) {
            this.setSynonyms(ApiHandler.jsonStringArrayToList(retrievedDefinition.get("synonyms").getAsJsonArray()));
        } else {
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

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }
}
