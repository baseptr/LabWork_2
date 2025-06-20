package lt.esdc.infohandler.parser;

import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.parser.impl.LexemeParser;
import lt.esdc.infohandler.parser.impl.ParagraphParser;
import lt.esdc.infohandler.parser.impl.SentenceParser;
import lt.esdc.infohandler.parser.impl.TextParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextParserFacade {
    private static final Logger logger = LogManager.getLogger(TextParserFacade.class);
    private final TextHandler entry;

    public TextParserFacade() {
        TextHandler symbolParser = new LexemeParser();
        TextHandler lexemeParser = new SentenceParser();
        TextHandler sentenceParser = new ParagraphParser();
        TextHandler paragraphParser = new TextParser();

        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);
        lexemeParser.setNext(symbolParser);

        this.entry = paragraphParser;
    }

    public TextElement parse(String rawText) {
        logger.info("Starting parsing of text.");
        TextElement handle = entry.handle(rawText);
        logger.info("Finished parsing of text.");
        return handle;
    }
}
