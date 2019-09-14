package com.company.calculator.operations;

import java.util.Stack;

public class TernaryOperator implements Operation
{
    public void operate(Stack<Double> numbers)
    {
        double a = numbers.pop(),
               b = numbers.pop(),
               c = numbers.pop();

        numbers.push(c == 1.0 ? b : a);
    }
}
