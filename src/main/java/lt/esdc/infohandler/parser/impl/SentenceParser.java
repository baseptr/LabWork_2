package lt.esdc.infohandler.parser.impl;

import lt.esdc.infohandler.component.ComponentType;
import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.component.impl.CompositeTextElement;
import lt.esdc.infohandler.parser.TextHandler;

public class SentenceParser extends TextHandler {
    private static final String LEXEME_DELIMITER = "\\s+";

    @Override
    public TextElement handle(String data) {
        CompositeTextElement sentence = new CompositeTextElement(ComponentType.SENTENCE);
        String[] lexemes = data.split(LEXEME_DELIMITER);

        for (String lexeme : lexemes) {
            if (next != null) {
                sentence.add(next.handle(lexeme));
            }
        }

        return sentence;
    }
}
