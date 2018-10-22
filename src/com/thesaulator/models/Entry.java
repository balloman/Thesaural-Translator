// Bernard Allotey 18-10-22

package com.thesaulator.models;

public class Entry {
    private String word;
    private PartOfSpeech partOfSpeech;
    private String definition;
    private String example;
    private String[] synonyms;

    public Entry(String word, PartOfSpeech partOfSpeech, String definition) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
    }

    public Entry(){}

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = PartOfSpeech.valueOf(partOfSpeech);
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

    public String[] getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }

    public enum PartOfSpeech {
        pronoun,
        noun,
        verb,
        determiner,
        exclamation
    }
}
