package lt.esdc.infohandler.parser;

import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.parser.impl.LexemeParser;
import lt.esdc.infohandler.parser.impl.ParagraphParser;
import lt.esdc.infohandler.parser.impl.SentenceParser;
import lt.esdc.infohandler.parser.impl.TextParser;
import lt.esdc.infohandler.util.TestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ParserTests {

    private TextParserFacade textParserFacade;
    private LexemeParser lexemeParser;
    private SentenceParser sentenceParser;
    private ParagraphParser paragraphParser;
    private TextParser textParser;

    @BeforeMethod
    public void setUp() {
        textParserFacade = new TextParserFacade();
        lexemeParser = new LexemeParser();
        sentenceParser = new SentenceParser();
        paragraphParser = new ParagraphParser();
        textParser = new TextParser();
    }

    @Test
    public void testLexemeParserSimpleWord() {
        TextElement result = lexemeParser.handle("hello");
        assertEquals(result.toText(), "hello");
    }

    @Test
    public void testLexemeParserWithArithmetic() {
        TextElement result = lexemeParser.handle("5+3");
        assertEquals(result.toText(), "8.0");
    }

    @Test
    public void testLexemeParserWithBitwise() {
        TextElement result = lexemeParser.handle("5&3");
        assertEquals(result.toText(), "1");
    }

    @Test
    public void testLexemeParserEmptyString() {
        TextElement result = lexemeParser.handle("");
        assertEquals(result.toText(), "");
    }

    @Test
    public void testSentenceParserSingleWord() {
        sentenceParser.setNext(lexemeParser);
        TextElement result = sentenceParser.handle("Hello");
        assertEquals(result.toText(), "Hello");
    }

    @Test
    public void testSentenceParserMultipleWords() {
        sentenceParser.setNext(lexemeParser);
        TextElement result = sentenceParser.handle("Hello world test");
        assertEquals(result.toText(), "Hello world test");
    }

    @Test
    public void testSentenceParserWithExpression() {
        sentenceParser.setNext(lexemeParser);
        TextElement result = sentenceParser.handle("Result is 10+5");
        assertEquals(result.toText(), "Result is 15.0");
    }

    @Test
    public void testParagraphParserSingleSentence() {
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        TextElement result = paragraphParser.handle("This is a test.");
        assertEquals(result.toText(), "    This is a test.");
    }

    @Test
    public void testParagraphParserMultipleSentences() {
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        TextElement result = paragraphParser.handle("First sentence. Second sentence!");
        assertEquals(result.toText(), "    First sentence. Second sentence!");
    }

    @Test
    public void testTextParserSingleParagraph() {
        textParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        TextElement result = textParser.handle("Single paragraph text.");
        assertEquals(result.toText(), "    Single paragraph text.");
    }

    @Test
    public void testTextParserMultipleParagraphs() {
        textParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        TextElement result = textParser.handle("First paragraph.\n\nSecond paragraph.");
        assertTrue(result.toText().contains("First paragraph."));
        assertTrue(result.toText().contains("Second paragraph."));
    }

    @Test
    public void testTextParserFacadeSimpleText() {
        TextElement result = textParserFacade.parse("Hello world.");
        assertEquals(result.toText(), "    Hello world.");
    }

    @Test
    public void testTextParserFacadeWithArithmetic() {
        TextElement result = textParserFacade.parse(TestConstants.TEXT_WITH_ARITHMETIC);
        assertTrue(result.toText().contains("8.0"));
    }

    @Test
    public void testTextParserFacadeWithBitwise() {
        TextElement result = textParserFacade.parse(TestConstants.TEXT_WITH_BITWISE);
        assertTrue(result.toText().contains("1"));
        assertTrue(result.toText().contains("16"));
    }

    @Test
    public void testTextParserFacadeMixedExpressions() {
        TextElement result = textParserFacade.parse(TestConstants.MIXED_EXPRESSIONS_TEXT);
        assertTrue(result.toText().contains("40.0"));
        assertTrue(result.toText().contains("12"));
        assertTrue(result.toText().contains("5.0"));
        assertTrue(result.toText().contains("4"));
        assertTrue(result.toText().contains("50.0"));
    }

    @Test
    public void testTextParserFacadeMultilingual() {
        TextElement result = textParserFacade.parse(TestConstants.MULTILINGUAL_TEXT);
        assertTrue(result.toText().contains("15.0"));
        assertTrue(result.toText().contains("15.0"));
        assertTrue(result.toText().contains("7"));
    }

    @Test
    public void testChainSetupCorrectly() {
        TextHandler handler1 = new LexemeParser();
        TextHandler handler2 = new SentenceParser();
        handler1.setNext(handler2);

        assertNotNull(handler1);
        assertNotNull(handler2);
    }

    @Test
    public void testParsingPreservesStructure() {
        TextElement result = textParserFacade.parse("First paragraph.\n\nSecond paragraph with 2+3 calculation.");
        assertEquals(result.getChildren().size(), 2);
    }

    @Test
    public void testComplexExpressionParsing() {
        String complexText = "Expression (10+5)*2 and bitwise 8|4&15 operations.";
        TextElement result = textParserFacade.parse(complexText);
        assertTrue(result.toText().contains("30.0"));
        assertTrue(result.toText().contains("12"));
    }
}