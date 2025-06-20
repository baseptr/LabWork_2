package lt.esdc.infohandler.util;

public class TestConstants {

    public static final String SIMPLE_TEXT = """
                It is a long established fact that a reader will be distracted.
                The point of using Lorem Ipsum is that it has a normal distribution.
            
                Making it look like readable English text for testing purposes.
                This is another sentence in the second paragraph.
            """;

    public static final String TEXT_WITH_ARITHMETIC = """
                The calculation 5+3 equals eight.
                More complex: 6*9/(3+4) should be calculated.
            
                Another paragraph with 10-2*3 in the middle.
            """;

    public static final String TEXT_WITH_BITWISE = """
                Binary operation 5&3 gives result.
                Shift left: 4<<2 becomes sixteen.
            
                Complex bitwise: (7^5|1&2) should be evaluated.
            """;

    public static final String MIXED_EXPRESSIONS_TEXT = """
                Arithmetic: 15+25 and bitwise: 8|4 in one text.
                Division 20/4 and XOR 6^2 operations.
            
                Final paragraph with 100-50 calculation.
            """;

    public static final String VARIED_SENTENCE_LENGTH_TEXT = """
                Short.
                This is a medium length sentence with several words.
            
                Very long sentence with many different words including supercalifragilisticexpialidocious.
                Another short one.
                Medium length sentence again.
            """;

    public static final String MULTILINGUAL_TEXT = """
                English text with calculation 10+5 here.
                Русский текст с вычислением 20-5 здесь.
            
                Mixed languages with битовая операция 15&7 operation.
            """;


    public static final String EXPECTED_ARITHMETIC_RESULT = """
                The calculation 8.0 equals eight.
                More complex: 7.714285714285714 should be calculated.
            
                Another paragraph with 4.0 in the middle.
            """;


    public static final String EXPECTED_BITWISE_RESULT = """
                Binary operation 1 gives result.
                Shift left: 16 becomes sixteen.
            
                Complex bitwise: 3 should be evaluated.
            """;


    public static final String WORD_COUNT_TEXT = """
                One word sentence.
                This sentence has exactly five words total.
            
                Short.
                This is a longer sentence with more words for testing purposes.
            """;


    public static final String REPEATED_WORDS_TEXT = """
                The quick brown fox jumps over the lazy dog.
                The brown dog was lazy and quick.
            
                Fox and dog are animals. The fox is quick.
            """;


    public static final String VOWEL_CONSONANT_TEXT = """
                Hello world with vowels and consonants.
                AEIOU are vowels in English alphabet.
            
                Русские гласные: а е ё и о у ы э ю я.
            """;
}