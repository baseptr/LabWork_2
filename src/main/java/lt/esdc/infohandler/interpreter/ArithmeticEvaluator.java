package lt.esdc.infohandler.interpreter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Double.parseDouble;

public class ArithmeticEvaluator {
    private static final String TOKEN_DELIMITERS = "+-*/() ";
    private static final String NUMBER_REGEX = "\\d+(\\.\\d+)?";
    private static final String OPERATORS = "+-*/";

    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";

    private static final String LEFT_PAREN = "(";
    private static final String RIGHT_PAREN = ")";
    private static final String ZERO = "0";
    private static final String EMPTY = "";


    public static double evaluate(String expression) {
        List<String> rpn = toRPN(expression);
        return evalRPN(rpn);
    }

    private static List<String> toRPN(String expr) {
        Deque<String> ops = new ArrayDeque<>();
        List<String> out = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(expr, TOKEN_DELIMITERS, true);

        String prevToken = EMPTY;
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (token.matches(NUMBER_REGEX)) {
                out.add(token);
                prevToken = token;

            } else if (token.equals(LEFT_PAREN)) {
                ops.push(token);
                prevToken = token;

            } else if (token.equals(RIGHT_PAREN)) {
                while (!ops.isEmpty() && !ops.peek().equals(LEFT_PAREN)) {
                    out.add(ops.pop());
                }
                if (!ops.isEmpty()) ops.pop();
                prevToken = token;

            } else if (OPERATORS.contains(token)) {
                if (token.equals(MINUS) && (prevToken.isEmpty() || LEFT_PAREN.equals(prevToken) || OPERATORS.contains(prevToken))) {
                    out.add(ZERO);
                }

                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    out.add(ops.pop());
                }
                ops.push(token);
                prevToken = token;
            }
        }

        while (!ops.isEmpty()) {
            out.add(ops.pop());
        }

        return out;
    }


    private static int precedence(String op) {
        return switch (op) {
            case MULTIPLY, DIVIDE -> 2;
            case PLUS, MINUS -> 1;
            default -> 0;
        };
    }

    private static double evalRPN(List<String> rpn) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String token : rpn) {
            switch (token) {
                case PLUS -> stack.push(stack.pop() + stack.pop());
                case MINUS -> {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                }
                case MULTIPLY -> stack.push(stack.pop() * stack.pop());
                case DIVIDE -> {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                }
                default -> stack.push(parseDouble(token));
            }
        }
        return stack.pop();
    }
}
