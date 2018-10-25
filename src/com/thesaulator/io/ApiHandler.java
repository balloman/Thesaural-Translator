// Bernard Allotey 18-10-20

package com.thesaulator.io;

import com.thesaulator.models.Entry;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ApiHandler {

    private static Entry mapDefinition(@NotNull JSONObject jsonObject) {
        Entry entry = new Entry();
        entry.setWord((String) jsonObject.get("word"));
        JSONObject meaning = ((JSONObject) jsonObject.get("meaning"));
        if (meaning.containsKey("determiner") || meaning.containsKey("exclamation") || meaning.containsKey("pronoun")) {
            entry.setTranslatable(false);
        } else {
            entry.setTranslatable(true);
        }
        JSONObject retrievedDefinition = (JSONObject) ((JSONArray) meaning.values().toArray()[0]).get(0);
        entry.setDefinition((String) retrievedDefinition.get("definition"));
        entry.setExample((String) retrievedDefinition.get("example"));
        entry.setSynonyms((JSONArray) retrievedDefinition.get("synonyms"));
        return entry;
    }

    public static Entry lookup(String term){
        JSONParser parser = new JSONParser();
        String totalPage = "";
        JSONObject jsonObject = null;
        try {
            URL lookup = new URL("https://googledictionaryapi.eu-gb.mybluemix.net/?define=" + term);
            BufferedReader in = new BufferedReader(new InputStreamReader(lookup.openStream()));
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
