package lt.esdc.infohandler.parser.impl;

import lt.esdc.infohandler.component.ComponentType;
import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.component.impl.CompositeTextElement;
import lt.esdc.infohandler.parser.TextHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextParser extends TextHandler {
    private static final Logger logger = LogManager.getLogger(TextParser.class);

    @Override
    public TextElement handle(String data) {
        CompositeTextElement text = new CompositeTextElement(ComponentType.TEXT);
        String[] paragraphs = data.split("\\n\\s*\\n|(?=\\n\\s{4})|(?=\\n\\t)");

        logger.debug("Split text into {} paragraphs", paragraphs.length);

        for (String paragraph : paragraphs) {
            String trimmedParagraph = paragraph.trim();
            if (!trimmedParagraph.isEmpty() && next != null) {
                text.add(next.handle(trimmedParagraph));
            }
        }

        return text;
    }
}