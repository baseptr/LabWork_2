package lt.esdc.infohandler.interpreter;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InterpreterTests {

    @Test
    public void testArithmeticAddition() {
        double result = ArithmeticEvaluator.evaluate("5+3");
        assertEquals(result, 8.0);
    }

    @Test
    public void testArithmeticSubtraction() {
        double result = ArithmeticEvaluator.evaluate("10-4");
        assertEquals(result, 6.0);
    }

    @Test
    public void testArithmeticMultiplication() {
        double result = ArithmeticEvaluator.evaluate("6*7");
        assertEquals(result, 42.0);
    }

    @Test
    public void testArithmeticDivision() {
        double result = ArithmeticEvaluator.evaluate("15/3");
        assertEquals(result, 5.0);
    }

    @Test
    public void testArithmeticWithParentheses() {
        double result = ArithmeticEvaluator.evaluate("(5+3)*2");
        assertEquals(result, 16.0);
    }

    @Test
    public void testArithmeticComplexExpression() {
        double result = ArithmeticEvaluator.evaluate("6*9/(3+4)");
        assertEquals(result, 54.0/7.0, 0.000001);
    }

    @Test
    public void testArithmeticWithSpaces() {
        double result = ArithmeticEvaluator.evaluate("5 + 3 * 2");
        assertEquals(result, 11.0);
    }

    @Test
    public void testArithmeticUnaryMinus() {
        double result = ArithmeticEvaluator.evaluate("-5+3");
        assertEquals(result, -2.0);
    }

    @Test
    public void testArithmeticNestedParentheses() {
        double result = ArithmeticEvaluator.evaluate("((10+5)*2)-10");
        assertEquals(result, 20.0);
    }

    @Test
    public void testArithmeticDecimalNumbers() {
        double result = ArithmeticEvaluator.evaluate("3.5+2.5");
        assertEquals(result, 6.0);
    }

    @Test
    public void testBitwiseAnd() {
        long result = BitwiseEvaluator.evaluate("5&3");
        assertEquals(result, 1L);
    }

    @Test
    public void testBitwiseOr() {
        long result = BitwiseEvaluator.evaluate("5|3");
        assertEquals(result, 7L);
    }

    @Test
    public void testBitwiseXor() {
        long result = BitwiseEvaluator.evaluate("5^3");
        assertEquals(result, 6L);
    }

    @Test
    public void testBitwiseNot() {
        long result = BitwiseEvaluator.evaluate("~5");
        assertEquals(result, -6L);
    }

    @Test
    public void testBitwiseShiftLeft() {
        long result = BitwiseEvaluator.evaluate("4<<2");
        assertEquals(result, 16L);
    }

    @Test
    public void testBitwiseShiftRight() {
        long result = BitwiseEvaluator.evaluate("16>>2");
        assertEquals(result, 4L);
    }

    @Test
    public void testBitwiseComplexExpression() {
        long result = BitwiseEvaluator.evaluate("(7^5|1&2)");
        assertEquals(result, 2L);
    }

    @Test
    public void testBitwiseWithParentheses() {
        long result = BitwiseEvaluator.evaluate("(8|4)&15");
        assertEquals(result, 12L);
    }

    @Test
    public void testBitwisePrecedence() {
        long result = BitwiseEvaluator.evaluate("8|4&2");
        assertEquals(result, 8L);
    }

    @Test
    public void testBitwiseMultipleOperations() {
        long result = BitwiseEvaluator.evaluate("15&7|8^4");
        assertEquals(result, 15L);
    }

    @Test
    public void testExpressionConverterArithmetic() {
        String result = ExpressionConverter.tryConvert("5+3");
        assertEquals(result, "8.0");
    }

    @Test
    public void testExpressionConverterBitwise() {
        String result = ExpressionConverter.tryConvert("5&3");
        assertEquals(result, "1");
    }

    @Test
    public void testExpressionConverterNoChange() {
        String result = ExpressionConverter.tryConvert("hello");
        assertEquals(result, "hello");
    }

    @Test
    public void testExpressionConverterWithNoise() {
        String result = ExpressionConverter.tryConvert("value++");
        assertEquals(result, "value++");
    }

    @Test
    public void testExpressionConverterToString() {
        String result = ExpressionConverter.tryConvert("obj.toString()");
        assertEquals(result, "obj.toString()");
    }

    @Test
    public void testExpressionConverterEmptyString() {
        String result = ExpressionConverter.tryConvert("");
        assertEquals(result, "");
    }

    @Test
    public void testExpressionConverterNull() {
        String result = ExpressionConverter.tryConvert(null);
        assertNull(result);
    }

    @Test
    public void testExpressionConverterComplexArithmetic() {
        String result = ExpressionConverter.tryConvert("(10+5)*2");
        assertEquals(result, "30.0");
    }

    @Test
    public void testExpressionConverterComplexBitwise() {
        String result = ExpressionConverter.tryConvert("15&7|8");
        assertEquals(result, "15");
    }
}