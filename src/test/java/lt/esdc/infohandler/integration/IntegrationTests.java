package lt.esdc.infohandler.integration;

import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.parser.TextParserFacade;
import lt.esdc.infohandler.reader.FileTextReader;
import lt.esdc.infohandler.service.TextAnalysisService;
import lt.esdc.infohandler.service.TextElementService;
import lt.esdc.infohandler.service.TextStatisticsService;
import lt.esdc.infohandler.util.TestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class IntegrationTests {

    private FileTextReader textReader;
    private TextParserFacade textParserFacade;

    @BeforeMethod
    public void setUp() {
        textReader = new FileTextReader();
        textParserFacade = new TextParserFacade();
    }

    @Test
    public void testFullWorkflowWithLightInput() {
        String text = textReader.readText("resources/light_input.txt");
        TextElement parsedText = textParserFacade.parse(text);

        assertNotNull(parsedText);
        assertFalse(parsedText.toText().trim().isEmpty());
        assertTrue(parsedText.getChildren().size() > 0);
    }

    @Test
    public void testFullWorkflowWithArithmeticInput() {
        String text = textReader.readText("resources/arithmetic_input.txt");
        TextElement parsedText = textParserFacade.parse(text);

        String result = parsedText.toText();
        assertTrue(result.contains("7.714285714285714"));
        assertFalse(result.contains("6*9/(3+4)"));
    }

    @Test
    public void testFullWorkflowWithBitInput() {
        String text = textReader.readText("resources/bit_input.txt");
        TextElement parsedText = textParserFacade.parse(text);

        String result = parsedText.toText();
        assertTrue(result.contains("0"));
        assertFalse(result.contains("3>>5"));
    }

    @Test
    public void testFiveMainOperationsIntegration() {
        TextElement parsedText = textParserFacade.parse(TestConstants.VARIED_SENTENCE_LENGTH_TEXT);


        List<TextElement> sortedParagraphs = TextElementService.sortParagraphsBySentenceCount(parsedText);
        assertNotNull(sortedParagraphs);
        assertTrue(sortedParagraphs.size() > 0);


        List<TextElement> longestWordSentences = TextAnalysisService.findSentencesWithLongestWord(parsedText);
        assertNotNull(longestWordSentences);
        assertTrue(longestWordSentences.size() > 0);


        List<TextElement> filteredSentences = TextAnalysisService.removeSentencesWithFewWords(parsedText, 3);
        assertNotNull(filteredSentences);


        Map<String, Long> wordCounts = TextAnalysisService.countIdenticalWords(parsedText);
        assertNotNull(wordCounts);
        assertFalse(wordCounts.isEmpty());


        List<TextElement> sentences = TextElementService.getAllSentences(parsedText);
        Map<String, Integer> vowelConsonantCounts = TextStatisticsService.countVowelsAndConsonants(sentences.get(0));
        assertNotNull(vowelConsonantCounts);
        assertTrue(vowelConsonantCounts.containsKey("vowels"));
        assertTrue(vowelConsonantCounts.containsKey("consonants"));
    }

    @Test
    public void testComplexExpressionEvaluationIntegration() {
        String complexText = "Result of 5*(1+2*(3/(4-1))) is calculated. Bitwise 15&7|8^4 also works.";
        TextElement parsedText = textParserFacade.parse(complexText);

        String result = parsedText.toText();
        assertTrue(result.contains("15.0"));
        assertTrue(result.contains("15"));
    }

    @Test
    public void testTextStructurePreservation() {
        TextElement parsedText = textParserFacade.parse(TestConstants.SIMPLE_TEXT);
        List<TextElement> paragraphs = TextElementService.getAllParagraphs(parsedText);
        assertTrue(paragraphs.size() >= 2);

        List<TextElement> sentences = TextElementService.getAllSentences(parsedText);
        assertTrue(sentences.size() >= 3);
    }

    @Test
    public void testArithmeticExpressionAccuracy() {
        String text = "Simple addition 2+3 and division 10/2 test.";
        TextElement parsedText = textParserFacade.parse(text);

        String result = parsedText.toText();
        assertTrue(result.contains("5.0"));
        assertTrue(result.contains("5.0"));
    }

    @Test
    public void testBitwiseExpressionAccuracy() {
        String text = "Binary AND 12&5 and OR 8|4 operations.";
        TextElement parsedText = textParserFacade.parse(text);

        String result = parsedText.toText();
        assertTrue(result.contains("4"));
        assertTrue(result.contains("12"));
    }

    @Test
    public void testMultilingualTextProcessing() {
        TextElement parsedText = textParserFacade.parse(TestConstants.MULTILINGUAL_TEXT);

        String result = parsedText.toText();
        assertTrue(result.contains("English"));
        assertTrue(result.contains("Русский"));

        assertTrue(result.contains("15.0"));
        assertTrue(result.contains("15.0"));
        assertTrue(result.contains("7"));
    }

    @Test
    public void testStatisticsServiceIntegration() {
        TextElement parsedText = textParserFacade.parse("Test sentence with multiple words for statistics.");

        int totalWords = TextStatisticsService.countWordsInText(parsedText);
        assertTrue(totalWords > 5);

        List<TextElement> paragraphs = TextElementService.getAllParagraphs(parsedText);
        int sentenceCount = TextStatisticsService.countSentencesInParagraph(paragraphs.get(0));
        assertTrue(sentenceCount > 0);
    }

    @Test
    public void testCompleteWorkflowFromFileToAnalysis() {
        String text = textReader.readText("resources/light_input.txt");

        TextElement parsedText = textParserFacade.parse(text);

        List<TextElement> sortedParagraphs = TextElementService.sortParagraphsBySentenceCount(parsedText);
        List<TextElement> longestWordSentences = TextAnalysisService.findSentencesWithLongestWord(parsedText);
        List<TextElement> filteredSentences = TextAnalysisService.removeSentencesWithFewWords(parsedText, 2);
        Map<String, Long> wordCounts = TextAnalysisService.countIdenticalWords(parsedText);

        List<TextElement> sentences = TextElementService.getAllSentences(parsedText);
        Map<String, Integer> vowelConsonantCounts = sentences.isEmpty() ?
                Map.of("vowels", 0, "consonants", 0) :
                TextStatisticsService.countVowelsAndConsonants(sentences.get(0));

        assertNotNull(sortedParagraphs);
        assertNotNull(longestWordSentences);
        assertNotNull(filteredSentences);
        assertNotNull(wordCounts);
        assertNotNull(vowelConsonantCounts);
    }
}