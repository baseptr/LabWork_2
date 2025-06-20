package lt.esdc.infohandler.interpreter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExpressionConverter {
    private static final Logger logger = LogManager.getLogger(ExpressionConverter.class);

    private static final String ARITHMETIC_PATTERN = "^[\\d+\\-*/().\\s]+$";
    private static final String BITWISE_PATTERN = "^[\\d()~&|^<>]+$";
    private static final String NOISE_PATTERN = ".*(\\+\\+|--|\\?|toString).*";

    public static String tryConvert(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        if (input.matches(NOISE_PATTERN)) {
            return input;
        }

        String bitwiseResult = tryBitwiseConversion(input);
        if (bitwiseResult != null) {
            return bitwiseResult;
        }

        String arithmeticResult = tryArithmeticConversion(input);
        if (arithmeticResult != null) {
            return arithmeticResult;
        }

        return input;
    }

    private static String tryBitwiseConversion(String input) {
        String expr = input.replaceAll("\\s+", "");

        if (!expr.matches(BITWISE_PATTERN) || !isBalanced(expr) || !hasValidStructure(expr)) {
            return null;
        }

        try {
            long result = BitwiseEvaluator.evaluate(expr);
            logger.debug("Converted bitwise expression '{}' to '{}'", input, result);
            return String.valueOf(result);
        } catch (Exception e) {
            logger.warn("Failed to evaluate bitwise expression: '{}'", expr, e);
            return null;
        }
    }

    private static String tryArithmeticConversion(String input) {
        if (!input.matches(ARITHMETIC_PATTERN) || !isBalanced(input) || !hasValidArithmeticStructure(input)) {
            return null;
        }

        try {
            double result = ArithmeticEvaluator.evaluate(input);
            logger.debug("Converted arithmetic expression '{}' to '{}'", input, result);
            return String.valueOf(result);
        } catch (Exception e) {
            logger.warn("Failed to evaluate arithmetic expression: '{}'", input, e);
            return null;
        }
    }

    private static boolean isBalanced(String expr) {
        int balance = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    private static boolean hasValidStructure(String expr) {
        return expr.matches(".*\\d.*") && expr.matches(".*(~|&|\\||\\^|<<|>>).*");
    }

    private static boolean hasValidArithmeticStructure(String input) {
        String cleaned = input.replaceAll("\\s+", "");
        return cleaned.matches(".*\\d.*") &&
                cleaned.matches(".*(\\+|\\-|\\*|/).*") &&
                !cleaned.matches("^[+\\-*/]+$") &&
                !cleaned.matches(".*[+\\-*/]{2,}.*");
    }
}