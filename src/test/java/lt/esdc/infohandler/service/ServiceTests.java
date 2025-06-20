package lt.esdc.infohandler.service;

import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.parser.TextParserFacade;
import lt.esdc.infohandler.util.TestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.*;

public class ServiceTests {

    private TextParserFacade textParserFacade;

    @BeforeMethod
    public void setUp() {
        textParserFacade = new TextParserFacade();
    }

    @Test
    public void testGetAllParagraphs() {
        TextElement text = textParserFacade.parse(TestConstants.SIMPLE_TEXT);
        List<TextElement> paragraphs = TextElementService.getAllParagraphs(text);
        assertTrue(paragraphs.size() >= 2);
    }

    @Test
    public void testGetAllSentences() {
        TextElement text = textParserFacade.parse(TestConstants.SIMPLE_TEXT);
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        assertTrue(sentences.size() >= 3);
    }

    @Test
    public void testFindSentenceWithLongestWord() {
        TextElement text = textParserFacade.parse(TestConstants.VARIED_SENTENCE_LENGTH_TEXT);
        Optional<TextElement> sentence = TextElementService.findSentenceWithLongestWord(text);
        assertNotNull(sentence);
        assertTrue(sentence.get().toText().contains("supercalifragilisticexpialidocious"));
    }

    @Test
    public void testSortParagraphsBySentenceCount() {
        TextElement text = textParserFacade.parse(TestConstants.VARIED_SENTENCE_LENGTH_TEXT);
        List<TextElement> sortedParagraphs = TextElementService.sortParagraphsBySentenceCount(text);
        assertTrue(sortedParagraphs.get(0).getChildren().size() <= sortedParagraphs.get(1).getChildren().size());
    }

    @Test
    public void testCountWordsInSentence() {
        TextElement text = textParserFacade.parse("This sentence has exactly five words.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        int wordCount = TextElementService.countWordsInSentence(sentences.get(0));
        assertEquals(wordCount, 6);
    }

    @Test
    public void testCountWordsInSentenceWithNullSentence() {
        int wordCount = TextElementService.countWordsInSentence(null);
        assertEquals(wordCount, 0);
    }

    @Test
    public void testGetAllParagraphsWithNullText() {
        List<TextElement> paragraphs = TextElementService.getAllParagraphs(null);
        assertTrue(paragraphs.isEmpty());
    }

    @Test
    public void testGetAllSentencesWithNullText() {
        List<TextElement> sentences = TextElementService.getAllSentences(null);
        assertTrue(sentences.isEmpty());
    }

    @Test
    public void testFindSentenceWithLongestWordWithNullText() {
        Optional<TextElement> sentence = TextElementService.findSentenceWithLongestWord(null);
        assertTrue(sentence.isEmpty());
    }

    @Test
    public void testFindSentencesWithLongestWord() {
        TextElement text = textParserFacade.parse(TestConstants.VARIED_SENTENCE_LENGTH_TEXT);
        List<TextElement> sentences = TextAnalysisService.findSentencesWithLongestWord(text);
        assertFalse(sentences.isEmpty());
        assertTrue(sentences.get(0).toText().contains("supercalifragilisticexpialidocious"));
    }

    @Test
    public void testRemoveSentencesWithFewWords() {
        TextElement text = textParserFacade.parse(TestConstants.WORD_COUNT_TEXT);
        List<TextElement> filteredSentences = TextAnalysisService.removeSentencesWithFewWords(text, 4);
        assertTrue(filteredSentences.size() < TextElementService.getAllSentences(text).size());
    }

    @Test
    public void testCountIdenticalWords() {
        TextElement text = textParserFacade.parse(TestConstants.REPEATED_WORDS_TEXT);
        Map<String, Long> wordCounts = TextAnalysisService.countIdenticalWords(text);
        assertEquals(wordCounts.get("the").longValue(), 4L);
        assertEquals(wordCounts.get("fox").longValue(), 3L);
        assertEquals(wordCounts.get("dog").longValue(), 3L);
    }

    @Test
    public void testFindSentencesWithLongestWordEmptyText() {
        TextElement text = textParserFacade.parse("");
        List<TextElement> sentences = TextAnalysisService.findSentencesWithLongestWord(text);
        assertTrue(sentences.isEmpty());
    }

    @Test
    public void testRemoveSentencesWithFewWordsMinimumZero() {
        TextElement text = textParserFacade.parse(TestConstants.WORD_COUNT_TEXT);
        List<TextElement> filteredSentences = TextAnalysisService.removeSentencesWithFewWords(text, 0);
        assertEquals(filteredSentences.size(), TextElementService.getAllSentences(text).size());
    }

    @Test
    public void testCountIdenticalWordsWithDifferentCases() {
        TextElement text = textParserFacade.parse("The THE the Test test.");
        Map<String, Long> wordCounts = TextAnalysisService.countIdenticalWords(text);
        assertEquals(wordCounts.get("the").longValue(), 3L);
        assertEquals(wordCounts.get("test").longValue(), 2L);
    }

    @Test
    public void testCountVowelsAndConsonants() {
        TextElement text = textParserFacade.parse("Hello world.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        Map<String, Integer> counts = TextStatisticsService.countVowelsAndConsonants(sentences.get(0));
        assertTrue(counts.get("vowels") > 0);
        assertTrue(counts.get("consonants") > 0);
    }

    @Test
    public void testCountSentencesInParagraph() {
        TextElement text = textParserFacade.parse(TestConstants.SIMPLE_TEXT);
        List<TextElement> paragraphs = TextElementService.getAllParagraphs(text);
        int sentenceCount = TextStatisticsService.countSentencesInParagraph(paragraphs.get(0));
        assertTrue(sentenceCount >= 1);
    }

    @Test
    public void testCountWordsInText() {
        TextElement text = textParserFacade.parse("One two three. Four five.");
        int totalWords = TextStatisticsService.countWordsInText(text);
        assertEquals(totalWords, 5);
    }

    @Test
    public void testCountVowelsAndConsonantsMultilingual() {
        TextElement text = textParserFacade.parse("Hello мир.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        Map<String, Integer> counts = TextStatisticsService.countVowelsAndConsonants(sentences.get(0));
        assertTrue(counts.get("vowels") >= 3);
        assertTrue(counts.get("consonants") >= 4);
    }

    @Test
    public void testCountVowelsAndConsonantsWithNullSentence() {
        Map<String, Integer> counts = TextStatisticsService.countVowelsAndConsonants(null);
        assertEquals(counts.get("vowels").intValue(), 0);
        assertEquals(counts.get("consonants").intValue(), 0);
    }

    @Test
    public void testCountSentencesInParagraphWithNullParagraph() {
        int sentenceCount = TextStatisticsService.countSentencesInParagraph(null);
        assertEquals(sentenceCount, 0);
    }

    @Test
    public void testCountWordsInTextEmptyText() {
        TextElement text = textParserFacade.parse("");
        int totalWords = TextStatisticsService.countWordsInText(text);
        assertEquals(totalWords, 0);
    }

    @Test
    public void testCountVowelsAndConsonantsOnlyVowels() {
        TextElement text = textParserFacade.parse("AEIOU aeiou.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        Map<String, Integer> counts = TextStatisticsService.countVowelsAndConsonants(sentences.get(0));
        assertEquals(counts.get("vowels").intValue(), 10);
        assertEquals(counts.get("consonants").intValue(), 0);
    }

    @Test
    public void testCountVowelsAndConsonantsOnlyConsonants() {
        TextElement text = textParserFacade.parse("BCDFG bcdfg.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        Map<String, Integer> counts = TextStatisticsService.countVowelsAndConsonants(sentences.get(0));
        assertEquals(counts.get("vowels").intValue(), 0);
        assertEquals(counts.get("consonants").intValue(), 10);
    }

    @Test
    public void testCountVowelsAndConsonantsWithNumbers() {
        TextElement text = textParserFacade.parse("Test123 with numbers.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        Map<String, Integer> counts = TextStatisticsService.countVowelsAndConsonants(sentences.get(0));
        assertTrue(counts.get("vowels") > 0);
        assertTrue(counts.get("consonants") > 0);
    }

    @Test
    public void testCountWordsInSentenceSimple() {
        TextElement text = textParserFacade.parse("One two three four five.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        int wordCount = TextElementService.countWordsInSentence(sentences.get(0));
        assertEquals(wordCount, 5);
    }

    @Test
    public void testCountIdenticalWordsSimple() {
        TextElement text = textParserFacade.parse("Cat dog cat. Dog cat dog.");
        Map<String, Long> wordCounts = TextAnalysisService.countIdenticalWords(text);
        assertEquals(wordCounts.get("cat").longValue(), 3L);
        assertEquals(wordCounts.get("dog").longValue(), 3L);
    }

    @Test
    public void testGetAllParagraphsSimple() {
        TextElement text = textParserFacade.parse("First paragraph.\n\nSecond paragraph.");
        List<TextElement> paragraphs = TextElementService.getAllParagraphs(text);
        assertEquals(paragraphs.size(), 2);
    }

    @Test
    public void testCountVowelsAndConsonantsRussianText() {
        TextElement text = textParserFacade.parse("Привет мир.");
        List<TextElement> sentences = TextElementService.getAllSentences(text);
        Map<String, Integer> counts = TextStatisticsService.countVowelsAndConsonants(sentences.get(0));
        assertTrue(counts.get("vowels") >= 3);
        assertTrue(counts.get("consonants") >= 5);
    }
}
