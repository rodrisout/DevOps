package org.example

import groovy.transform.Field

@Field
final Integer DEFAULT_OCCURRENCES = 10

@Field String TEXT_URL = "https://gist.githubusercontent.com/jsdario/6d6c69398cb0c73111e49f1218960f79/raw/8d4fc4548d437e2a7203a5aeeace5477f598827d/el_quijote.txt"

List<String> getMostFrequent(String text, int occurrences = DEFAULT_OCCURRENCES) {
    Map<String, Integer> wordFreqMap = text
            .tokenize()
            .collect{ it.toLowerCase() }
            .groupBy { it }
            .collectEntries {_, items ->
                [items[0] as String, items.size()]
            }

    wordFreqMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(occurrences).map(Map.Entry::getKey) as List
}

def url = new URL(TEXT_URL)
def fileContent = url.getText()
List<String> mostFrequent = getMostFrequent(fileContent)
new File("quijote_most_frequent.txt").text = mostFrequent.join("\n")