package lt.esdc.infohandler.reader;

import lt.esdc.infohandler.exception.FileReadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileTextReader implements TextReader {
    private static final Logger logger = LogManager.getLogger(FileTextReader.class);

    @Override
    public String readText(String filePath) {
        try {
            logger.info("Reading text from file: {}", filePath);
            String content = Files.readString(Path.of(filePath));
            logger.info("Successfully read {} characters from file", content.length());
            return content;
        } catch (IOException e) {
            logger.error("Failed to read file: {}", filePath, e);
            throw new FileReadException("Cannot read file: " + filePath, e);
        }
    }
}
