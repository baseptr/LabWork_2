package lt.esdc.infohandler.service;

import lt.esdc.infohandler.component.TextElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TextElementService {

    public static Optional<TextElement> findSentenceWithLongestWord(TextElement root) {
        if (root == null || root.getChildren() == null) {
            return Optional.empty();
        }

        TextElement maxSentence = null;
        int maxLength = 0;

        for (TextElement paragraph : root.getChildren()) {
            for (TextElement sentence : paragraph.getChildren()) {
                for (TextElement lexeme : sentence.getChildren()) {
                    String word = lexeme.toText().replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");
                    if (word.length() > maxLength) {
                        maxLength = word.length();
                        maxSentence = sentence;
                    }
                }
            }
        }
        return Optional.ofNullable(maxSentence);
    }

    public static List<TextElement> getAllParagraphs(TextElement root) {
        if (root == null || root.getChildren() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(root.getChildren());
    }

    public static List<TextElement> getAllSentences(TextElement root) {
        List<TextElement> sentences = new ArrayList<>();
        if (root == null || root.getChildren() == null) return sentences;

        for (TextElement paragraph : root.getChildren()) {
            sentences.addAll(paragraph.getChildren());
        }
        return sentences;
    }

    public static List<TextElement> sortParagraphsBySentenceCount(TextElement root) {
        List<TextElement> paragraphs = getAllParagraphs(root);
        paragraphs.sort(Comparator.comparingInt(p -> p.getChildren().size()));
        return paragraphs;
    }

    public static int countWordsInSentence(TextElement sentence) {
        if (sentence == null || sentence.getChildren() == null) return 0;

        return (int) sentence.getChildren().stream()
                .map(TextElement::toText)
                .map(text -> text.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", ""))
                .filter(word -> !word.isEmpty())
                .count();
    }
}