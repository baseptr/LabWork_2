package lt.esdc.infohandler.service;

import lt.esdc.infohandler.component.TextElement;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class TextAnalysisService {

    public static List<TextElement> findSentencesWithLongestWord(TextElement root) {
        List<TextElement> allSentences = TextElementService.getAllSentences(root);
        if (allSentences.isEmpty()) return new ArrayList<>();

        int maxWordLength = allSentences.stream()
                .flatMap(sentence -> sentence.getChildren().stream())
                .mapToInt(lexeme -> extractWord(lexeme.toText()).length())
                .max()
                .orElse(0);

        return allSentences.stream()
                .filter(sentence -> hasWordOfLength(sentence, maxWordLength))
                .collect(Collectors.toList());
    }

    public static List<TextElement> removeSentencesWithFewWords(TextElement root, int minWordCount) {
        return TextElementService.getAllSentences(root).stream()
                .filter(sentence -> TextElementService.countWordsInSentence(sentence) >= minWordCount)
                .collect(Collectors.toList());
    }

    public static Map<String, Long> countIdenticalWords(TextElement root) {
        return TextElementService.getAllSentences(root).stream()
                .flatMap(sentence -> sentence.getChildren().stream())
                .map(lexeme -> extractWord(lexeme.toText()))
                .filter(word -> !word.isEmpty())
                .map(String::toLowerCase)
                .collect(groupingBy(word -> word, counting()));
    }

    private static String extractWord(String text) {
        return text.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");
    }

    private static boolean hasWordOfLength(TextElement sentence, int targetLength) {
        return sentence.getChildren().stream()
                .anyMatch(lexeme -> extractWord(lexeme.toText()).length() == targetLength);
    }
}
