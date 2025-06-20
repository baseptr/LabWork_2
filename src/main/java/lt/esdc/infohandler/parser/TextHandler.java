package lt.esdc.infohandler.parser;

import lt.esdc.infohandler.component.TextElement;

public abstract class TextHandler {
    protected TextHandler next;

    public void setNext(TextHandler next) {
        this.next = next;
    }

    public abstract TextElement handle(String data);
}