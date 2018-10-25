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
        final String[] primaryPartOfSpeech = new String[1];
//        meaning.forEach((k, v) -> {
//            if (k.equals("determiner")){
//                primaryPartOfSpeech[0] = (String) k;
//                return;
//            }else {
//                primaryPartOfSpeech[0] = (String) k;
//            }
//        });
        if (meaning.containsKey("determiner") || meaning.containsKey("exclamation")) {
            primaryPartOfSpeech[0] = null;
        }
        String partOfSpeech = primaryPartOfSpeech[0];
        JSONObject retrievedDefinition = (JSONObject) ((JSONArray) meaning.get(partOfSpeech)).get(0);
        JSONArray synonyms = (JSONArray) meaning.get(partOfSpeech);
        entry.setPartOfSpeech(primaryPartOfSpeech[0]);
        entry.setDefinition((String) retrievedDefinition.get("definition"));
        entry.setExample((String) retrievedDefinition.get("example"));
        entry.setSynonyms(synonyms);
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
