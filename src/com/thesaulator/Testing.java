// Bernard Allotey 18-10-26

package com.thesaulator;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Testing {
    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = null;
        try {
            jsonElement = jsonParser.parse(lookup("dog"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String lookup(String term) throws IOException {
        URL lookup = new URL("https://googledictionaryapi.eu-gb.mybluemix.net/?define=" + term);
        BufferedReader in = new BufferedReader(new InputStreamReader(lookup.openStream()));
        String inputLine;
        String totalPage = "";
        while ((inputLine = in.readLine()) != null) {
            //System.out.println(inputLine);
            totalPage = totalPage.concat(inputLine);
        }
        in.close();
        return totalPage;
    }
}
