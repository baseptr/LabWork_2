package lt.esdc.infohandler.component;

import lt.esdc.infohandler.component.impl.CompositeTextElement;
import lt.esdc.infohandler.component.impl.LeafTextElement;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CompositeComponentTests {

    @Test
    public void testLeafTextElementCreation() {
        LeafTextElement leaf = new LeafTextElement('A');
        assertEquals(leaf.toText(), "A");
    }

    @Test
    public void testLeafTextElementGetChildren() {
        LeafTextElement leaf = new LeafTextElement('B');
        assertTrue(leaf.getChildren().isEmpty());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testLeafTextElementAddThrowsException() {
        LeafTextElement leaf = new LeafTextElement('C');
        leaf.add(new LeafTextElement('D'));
    }

    @Test
    public void testCompositeTextElementCreation() {
        CompositeTextElement composite = new CompositeTextElement(ComponentType.WORD);
        assertEquals(composite.toText(), "");
    }

    @Test
    public void testCompositeTextElementAddChild() {
        CompositeTextElement composite = new CompositeTextElement(ComponentType.WORD);
        composite.add(new LeafTextElement('H'));
        assertEquals(composite.toText(), "H");
    }

    @Test
    public void testCompositeTextElementMultipleChildren() {
        CompositeTextElement word = new CompositeTextElement(ComponentType.WORD);
        word.add(new LeafTextElement('H'));
        word.add(new LeafTextElement('i'));
        assertEquals(word.toText(), "Hi");
    }

    @Test
    public void testSentenceWithSpaces() {
        CompositeTextElement sentence = new CompositeTextElement(ComponentType.SENTENCE);

        CompositeTextElement word1 = new CompositeTextElement(ComponentType.WORD);
        word1.add(new LeafTextElement('H'));
        word1.add(new LeafTextElement('i'));

        CompositeTextElement word2 = new CompositeTextElement(ComponentType.WORD);
        word2.add(new LeafTextElement('B'));
        word2.add(new LeafTextElement('y'));
        word2.add(new LeafTextElement('e'));

        sentence.add(word1);
        sentence.add(word2);

        assertEquals(sentence.toText(), "Hi Bye");
    }

    @Test
    public void testParagraphFormatting() {
        CompositeTextElement paragraph = new CompositeTextElement(ComponentType.PARAGRAPH);

        CompositeTextElement sentence = new CompositeTextElement(ComponentType.SENTENCE);
        CompositeTextElement lexeme = new CompositeTextElement(ComponentType.LEXEME);
        lexeme.add(new LeafTextElement('H'));
        lexeme.add(new LeafTextElement('i'));
        sentence.add(lexeme);

        paragraph.add(sentence);

        assertEquals(paragraph.toText(), "    Hi");
    }

    @Test
    public void testTextWithNewlines() {
        CompositeTextElement text = new CompositeTextElement(ComponentType.TEXT);

        CompositeTextElement paragraph1 = new CompositeTextElement(ComponentType.PARAGRAPH);
        CompositeTextElement sentence1 = new CompositeTextElement(ComponentType.SENTENCE);
        CompositeTextElement lexeme1 = new CompositeTextElement(ComponentType.LEXEME);
        lexeme1.add(new LeafTextElement('A'));
        sentence1.add(lexeme1);
        paragraph1.add(sentence1);

        CompositeTextElement paragraph2 = new CompositeTextElement(ComponentType.PARAGRAPH);
        CompositeTextElement sentence2 = new CompositeTextElement(ComponentType.SENTENCE);
        CompositeTextElement lexeme2 = new CompositeTextElement(ComponentType.LEXEME);
        lexeme2.add(new LeafTextElement('B'));
        sentence2.add(lexeme2);
        paragraph2.add(sentence2);

        text.add(paragraph1);
        text.add(paragraph2);

        assertEquals(text.toText(), "    A\n    B");
    }

    @Test
    public void testGetChildrenReturnsUnmodifiableList() {
        CompositeTextElement composite = new CompositeTextElement(ComponentType.WORD);
        composite.add(new LeafTextElement('A'));

        assertEquals(composite.getChildren().size(), 1);
    }

    @Test
    public void testEmptyCompositeToString() {
        CompositeTextElement composite = new CompositeTextElement(ComponentType.LEXEME);
        assertEquals(composite.toString(), "");
    }

    @Test
    public void testLeafToString() {
        LeafTextElement leaf = new LeafTextElement('Z');
        assertEquals(leaf.toString(), "Z");
    }
}