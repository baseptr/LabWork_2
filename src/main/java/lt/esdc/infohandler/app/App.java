package lt.esdc.infohandler.app;

import lt.esdc.infohandler.component.TextElement;
import lt.esdc.infohandler.parser.TextParserFacade;
import lt.esdc.infohandler.reader.FileTextReader;
import lt.esdc.infohandler.reader.TextReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        try {
            TextReader textReader = new FileTextReader();
            String text = textReader.readText("resources/bit_input.txt");

            TextParserFacade textParserFacade = new TextParserFacade();
            TextElement parsedText = textParserFacade.parse(text);

            logger.info("Text parsing completed successfully");
            System.out.println("=== PARSED TEXT ===");
            System.out.println(parsedText.toText());
            System.out.println("=== END OF PARSED TEXT ===");
        } catch (Exception e) {
            logger.error("Application failed", e);
            System.err.println("Error: " + e.getMessage());
        }
    }
}