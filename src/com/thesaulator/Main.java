// Bernard Allotey 18-10-20

package com.thesaulator;

import com.thesaulator.io.ApiHandler;
import com.thesaulator.models.Entry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) {
        WordParseResult result = parseWords();
        Map<Integer, String> stringMap = result.parsed;
        Map<Integer, String> originalMap = result.original;
        Map<Integer, Entry> entryMap = aProduceEntriesWithKeys(stringMap);
        System.out.println(sentence(originalMap, entryMap));
    }

    @NotNull
    @Contract(" -> new")
    private static WordParseResult parseWords() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter sentence here: ");
        String s = scanner.nextLine();
        String[] arr = s.split("\\b");
        Map<Integer, String> integerStringMap = new HashMap<>();
        int i = 0;
        for (String ss : arr) {
            integerStringMap.put(i, ss);
            i++;
        }
        Map<Integer, String> words = new HashMap<>();
        for (Integer key : integerStringMap.keySet()) {
            String ss = integerStringMap.get(key);
            if (ss.isBlank()) continue;
            boolean isSpecial = false;
            char[] chars = ss.toCharArray();
            for (char _char : chars) if (!Character.isLetter(_char)) isSpecial = true;
            if (!isSpecial) words.put(key, ss);
        }
        return new WordParseResult(integerStringMap, words);
    }

    private static Map<Integer, Entry> aProduceEntriesWithKeys(@NotNull Map<Integer, String> words) {
        Map<Integer, Entry> entries = new HashMap<>();
        Map<Integer, FutureTask<Entry>> futureTasks = new HashMap<>();
        for (Integer key : words.keySet()) {
            ApiHandler.LookupCallable callable = new ApiHandler.LookupCallable(words.get(key));
            futureTasks.put(key, new FutureTask<>(callable));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(futureTasks.size());
        for (Integer key : futureTasks.keySet()) {
            executorService.execute(futureTasks.get(key));
        }
        while (true) {
            try {
                boolean complete = false;
                for (Integer key : futureTasks.keySet()) {
                    if (futureTasks.get(key).isDone()) {
                        complete = true;
                    } else {
                        complete = false;
                        break;
                    }
                }
                if (complete) {
                    for (Integer key : futureTasks.keySet()) {
                        entries.put(key, futureTasks.get(key).get());
                    }
                    break;
                } else {
                    System.out.print(".");
                    Thread.sleep(750);
                }
            } catch (InterruptedException | ExecutionException ignored) {
            }
        }
        System.out.println();
        return entries;
    }

    private static String sentence(@NotNull Map<Integer, String> originalMap, Map<Integer, Entry> entryMap) {
        String sentence = "";
        for (Integer position : originalMap.keySet()) {
            if (entryMap.containsKey(position)) {
                if (entryMap.get(position).isTranslatable()) {
                    originalMap.put(position, entryMap.get(position).getSynonyms().get(0));
                }
            }
        }
        List<String> words = new ArrayList<>(originalMap.values());
        for (String s : words) {
            sentence = sentence.concat(s);
        }
        return sentence;
    }

    final private static class WordParseResult {
        private Map<Integer, String> original;
        private Map<Integer, String> parsed;

        private WordParseResult(Map<Integer, String> original, Map<Integer, String> parsed) {
            this.original = original;
            this.parsed = parsed;
        }
    }
}
