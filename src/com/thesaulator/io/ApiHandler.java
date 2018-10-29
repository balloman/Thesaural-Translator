// Bernard Allotey 18-10-20

package com.thesaulator.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.thesaulator.models.Entry;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ApiHandler {

    private static Entry gLookup(String term) {
        JsonParser parser = new JsonParser();
        String totalPage = "";
        JsonElement jsonElement = null;
        try {
            URL lookup = new URL("https://googledictionaryapi.eu-gb.mybluemix.net/?define=" + term);
            BufferedReader in = new BufferedReader(new InputStreamReader(lookup.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                totalPage = totalPage.concat(inputLine);
            }
            in.close();
            jsonElement = parser.parse(totalPage);
        } catch (IOException e) {
            Entry entry = new Entry();
            entry.setTranslatable(false);
            return entry;
        }
        return Entry.StaticEntryBuilder(jsonElement);
    }

    public static List<String> jsonStringArrayToList(@NotNull JsonArray jsonArray) {
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            newList.add(jsonArray.get(i).getAsString());
        }
        return newList;
    }

    public static class LookupCallable implements Callable<Entry> {
        private String term;

        public LookupCallable(String term) {
            this.term = term;
        }

        @Override
        public Entry call() {
            return gLookup(term);
        }
    }
}
