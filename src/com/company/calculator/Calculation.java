package com.company.calculator;

import com.company.calculator.operations.*;

import java.util.*;

public class Calculation
{
    private Map<String, Operation> operations;
    private Map<String, Integer> priority;
    private Map<String, String> unaryOperations;
    private Set<String> unusedSymbols;
    private Set<Character> allowedSymbols;
    private final String delimeter = " ";

    public Set<Character> getAllowedSymbols() {
        return allowedSymbols;
    }

    public Calculation()
    {
        unusedSymbols = new HashSet<>();
        unusedSymbols.add(":");

        unaryOperations = new HashMap<>();
        unaryOperations.put("-", "u-");

        operations = new HashMap<>();
        operations.put("+", new Add());
        operations.put("-", new Sub());
        operations.put("/", new Div());
        operations.put("*", new Mul());
        operations.put("<", new Less());
        operations.put("?", new TernaryOperator());
        operations.put("u-", new UnaryMinus());
        operations.put("<=", new LessOrEqual());
        operations.put(">", new Greater());
        operations.put(">=", new GreaterOrEqual());
        operations.put("==", new Equal());
        operations.put("!=", new NotEqual());

        allowedSymbols = new HashSet<>();
        for (Map.Entry<String, Operation> entry : operations.entrySet()) {
            for (Character ch : entry.getKey().toCharArray()) {
                if (ch != 'u')
                    allowedSymbols.add(ch);
            }
        }

        priority = new HashMap<>();
        priority.put(")", 0);
        priority.put("(", 0);
        priority.put("?", 1);
        priority.put("<", 2);
        priority.put("<=", 2);
        priority.put(">", 2);
        priority.put(">=", 2);
        priority.put("==", 2);
        priority.put("!=", 2);
        priority.put("-", 3);
        priority.put("+", 3);
        priority.put("*", 4);
        priority.put("/", 4);
        priority.put("u-", 5);
    }

    public String getPolska(String expression) throws Exception
    {
        expression = "(" + expression + ")";
        boolean isOperation = true;
        char parsed[] = expression.toCharArray();

        Stack<String> operationStack = new Stack<>();

        StringBuilder polska = new StringBuilder();
        for (int i = 0; i < parsed.length; ++i) {

            if (Character.isDigit(parsed[i]) || parsed[i] == '.') {
                int start = i;
                while (++i < parsed.length && (Character.isDigit(parsed[i]) || parsed[i] == '.')) {
                    //find the last char of number
                }

                polska.append(expression.substring(start, i)).append(delimeter);
                isOperation = false;
                --i;
                continue;
            }

            String subExpr = Character.toString(parsed[i]);

            if (allowedSymbols.contains(parsed[i])) {
                while (++i < parsed.length &&
                        allowedSymbols.contains(parsed[i]) &&
                        operations.containsKey(subExpr + parsed[i]))
                {
                    subExpr += parsed[i];
                }
                --i;
            }

            if (subExpr.equals("(")) {
                operationStack.push(subExpr);
            }
            else if (subExpr.equals(")")) {
                if (!operationStack.contains("("))
                    throw new Exception("Invalid note");
                while (!operationStack.peek().equals("("))
                    polska.append(operationStack.pop()).append(delimeter);
                operationStack.pop();
            }
            else {
                if (isOperation && unaryOperations.containsKey(subExpr)) {
                    subExpr = (unaryOperations.get(subExpr));
                }

                if (operations.containsKey(subExpr)) {

                    if (operationStack.empty()) {
                        operationStack.push(subExpr);
                        continue;
                    }

                    while (!operationStack.empty() && !operationStack.peek().equals("(") &&
                            priority.get(subExpr) <= priority.get(operationStack.peek())) {
                        polska.append(operationStack.pop()).append(delimeter);
                    }
                    operationStack.push(subExpr);
                }
                else if (!unusedSymbols.contains(subExpr))
                    throw new Exception("Invalid character: " + subExpr);
            }
            isOperation = true;
        }
        while (!operationStack.empty())
            polska.append(operationStack.pop()).append(delimeter);

        return polska.substring(0, polska.length() - delimeter.length());
    }

    public double getResult(String expression) throws Exception
    {
        expression = getPolska(expression);
        Stack<Double> numbers = new Stack<>();
        String[] parsedPolska = expression.split(delimeter);

        for (String op : parsedPolska) {
            if (operations.containsKey(op)) {
                operations.get(op).operate(numbers);
            }
            else {
                numbers.push(Double.parseDouble(op));
            }
        }
        return numbers.pop();
    }
}
