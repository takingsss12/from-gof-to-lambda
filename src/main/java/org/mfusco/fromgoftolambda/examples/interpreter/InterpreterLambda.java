package org.mfusco.fromgoftolambda.examples.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BinaryOperator;

public class InterpreterLambda {
    static Map<String, BinaryOperator<Integer>> opMap = new HashMap<>();

    static {
        opMap.put("+", (a, b) -> a + b);
        opMap.put("*", (a, b) -> a * b);
        opMap.put("-", (a, b) -> a - b);
    }

    public static int evaluate(String expression) {
        Stack<Integer> stack = new Stack<>();
        for (String s : expression.split(" ")) {
            BinaryOperator<Integer> op = opMap.get( s );
            if (op != null) {
                int right = stack.pop();
                int left = stack.pop();
                stack.push(op.apply( left, right ));
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }

    public static void main( String[] args ) {
        String expression = "7 3 - 2 1 + *";
        System.out.println( evaluate( expression ) );
    }

}
