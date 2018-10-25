// Bernard Allotey 18-10-22

package com.thesaulator.models;

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
