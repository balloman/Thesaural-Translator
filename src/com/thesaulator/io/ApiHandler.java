// Bernard Allotey 18-10-20

package com.thesaulator.io;

import com.thesaulator.models.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApiHandler {

    private static Entry mapDefinition(JSONObject jsonObject){
        Entry entry = new Entry();
        entry.setWord((String) jsonObject.get("word"));
        JSONObject meaning = ((JSONObject) jsonObject.get("meaning"));
        final String[] primaryPartOfSpeech = new String[1];
        meaning.forEach((k, v) -> {
            primaryPartOfSpeech[0] = (String) k;
        });
        String partOfSpeech = primaryPartOfSpeech[0];
        JSONObject retrievedDefinition = (JSONObject) ((JSONArray) meaning.get(partOfSpeech)).get(0);
        JSONArray synonyms = (JSONArray) meaning.get(partOfSpeech);
        entry.setPartOfSpeech(primaryPartOfSpeech[0]);
        entry.setDefinition((String) retrievedDefinition.get("definition"));
        entry.setExample((String) retrievedDefinition.get("example"));
        entry.setSynonyms((String[]) synonyms.toArray(new String[0]));
        return entry;
    }

    public static Entry lookup(String term){
        JSONParser parser = new JSONParser();
        URL lookup = null;
        BufferedReader in = null;
        String totalPage = "";
        JSONObject jsonObject = null;
        try {
            lookup = new URL("https://googledictionaryapi.eu-gb.mybluemix.net/?define=" + term);
            in = new BufferedReader(new InputStreamReader(lookup.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                //System.out.println(inputLine);
                totalPage = totalPage.concat(inputLine);
            }
            in.close();
            jsonObject = (JSONObject) parser.parse(totalPage);
            System.out.println(jsonObject);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return mapDefinition(jsonObject);
    }
}
