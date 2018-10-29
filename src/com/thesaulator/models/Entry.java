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

    // basically the not allowed parts of speech, so if a word has any of these parts of speech, it won't translate it
    private static final Collection<String> PROHIBITED = Arrays.asList(
            "determiner",
            "exclamation",
            "pronoun",
            "conjunction");

    /**
     * Constructor thats never used, idk why this is here, but don't use it, pls use the builder
     *
     * @param word           Entry word
     * @param isTranslatable is it translatable
     * @param definition     the definition of the word
     * @param example        examples of the word
     * @param synonyms       a list of synonyms for this word
     */
    public Entry(String word, boolean isTranslatable, String definition, String example,
            ArrayList<String> synonyms) {
        this.word = word;
        this.isTranslatable = isTranslatable;
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
    }

    public Entry() {
    }

    /**
     * Builds a new Entry for you
     * @param jsonElement The json page to use
     * @return An Entry object for the passed in jsonElement
     */
    public static Entry StaticEntryBuilder(@NotNull JsonElement jsonElement) {
        Entry entry = new Entry();
        entry.entryBuilder(jsonElement);
        return entry;
    }

    /**
     * Maps the json element objects to the objects fields
     * @param jsonElement The json elemtent to map with
     */
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
