package lt.esdc.infohandler.component.impl;

import lt.esdc.infohandler.component.TextElement;

import java.util.Collections;
import java.util.List;

public class LeafTextElement implements TextElement {
    private final char symbol;

    public LeafTextElement(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void add(TextElement component) {
        throw new UnsupportedOperationException("Leaf component doesn't support add().");
    }

    @Override
    public List<TextElement> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toText() {
        return String.valueOf(symbol);
    }

    @Override
    public String toString() {
        return toText();
    }
}