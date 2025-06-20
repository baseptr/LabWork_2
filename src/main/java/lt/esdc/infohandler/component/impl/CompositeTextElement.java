package lt.esdc.infohandler.component.impl;

import lt.esdc.infohandler.component.ComponentType;
import lt.esdc.infohandler.component.TextElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeTextElement implements TextElement {
    private final ComponentType type;
    private final List<TextElement> children = new ArrayList<>();

    public CompositeTextElement(ComponentType type) {
        this.type = type;
    }

    @Override
    public void add(TextElement component) {
        children.add(component);
    }

    @Override
    public List<TextElement> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toText() {
        if (children.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < children.size(); i++) {
            TextElement child = children.get(i);

            if (type == ComponentType.PARAGRAPH && i == 0) {
                sb.append("    ");
            }

            sb.append(child.toText());

            if (i < children.size() - 1) {
                switch (type) {
                    case TEXT -> sb.append("\n");
                    case PARAGRAPH -> sb.append(" ");
                    case SENTENCE -> sb.append(" ");
                }
            }
        }

        if (type == ComponentType.TEXT) {
            return sb.toString();
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return toText();
    }
}
