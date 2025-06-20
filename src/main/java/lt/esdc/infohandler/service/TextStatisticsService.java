package lt.esdc.infohandler.service;

import lt.esdc.infohandler.component.TextElement;

import java.util.Map;
import java.util.HashMap;

public class TextStatisticsService {

    private static final String VOWELS = "аеёиоуыэюяaeiou";
    private static final String CONSONANTS = "бвгджзйклмнпрстфхцчшщbcdfghjklmnpqrstvwxyz";

    public static Map<String, Integer> countVowelsAndConsonants(TextElement sentence) {
        Map<String, Integer> result = new HashMap<>();
        result.put("vowels", 0);
        result.put("consonants", 0);

        if (sentence == null || sentence.getChildren() == null) {
            return result;
        }

        String text = sentence.toText().toLowerCase();

        for (char c : text.toCharArray()) {
            if (VOWELS.indexOf(c) != -1) {
                result.merge("vowels", 1, Integer::sum);
            } else if (CONSONANTS.indexOf(c) != -1) {
                result.merge("consonants", 1, Integer::sum);
            }
        }

        return result;
    }

    public static int countSentencesInParagraph(TextElement paragraph) {
        if (paragraph == null || paragraph.getChildren() == null) {
            return 0;
        }
        return paragraph.getChildren().size();
    }

    public static int countWordsInText(TextElement root) {
        return TextElementService.getAllSentences(root).stream()
                .mapToInt(TextElementService::countWordsInSentence)
                .sum();
    }
}