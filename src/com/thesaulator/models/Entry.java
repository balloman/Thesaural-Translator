// Bernard Allotey 18-10-22

package com.thesaulator.models;

public class Entry {
    private String word;
    private PartOfSpeech partOfSpeech;

    public Entry(String word, PartOfSpeech partOfSpeech) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public class Definition{
        public String definition;
        public String example;

        public Definition(String definition, String example){
            this.definition = definition;
            this.example = example;
        }
    }

    public enum PartOfSpeech {
        pronoun,
        noun,
        verb,
        determiner
    }
}
