package lt.esdc.infohandler.parser.impl;

import lt.esdc.infohandler.component.ComponentType;
import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.component.impl.CompositeTextElement;
import lt.esdc.infohandler.parser.TextHandler;

public class ParagraphParser extends TextHandler {
    private static final String SENTENCE_DELIMITER = "(?<=[.!?…])\\s+";

    @Override
    public TextElement handle(String data) {
        CompositeTextElement paragraph = new CompositeTextElement(ComponentType.PARAGRAPH);
        String[] sentences = data.split(SENTENCE_DELIMITER);

        for (String sentence : sentences) {
            if (next != null) {
                paragraph.add(next.handle(sentence.trim()));
            }
        }

        return paragraph;
    }
}
