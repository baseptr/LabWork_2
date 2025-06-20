package lt.esdc.infohandler.parser.impl;

import lt.esdc.infohandler.component.ComponentType;
import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.component.impl.CompositeTextElement;
import lt.esdc.infohandler.component.impl.LeafTextElement;
import lt.esdc.infohandler.parser.TextHandler;
import lt.esdc.infohandler.interpreter.ExpressionConverter;

public class LexemeParser extends TextHandler {

    @Override
    public TextElement handle(String data) {
        CompositeTextElement lexeme = new CompositeTextElement(ComponentType.LEXEME);
        String converted = ExpressionConverter.tryConvert(data);

        for (char c : converted.toCharArray()) {
            lexeme.add(new LeafTextElement(c));
        }

        return lexeme;
    }
}