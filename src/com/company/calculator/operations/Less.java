package com.company.calculator.operations;

import java.util.Stack;

public class Less implements Operation
{
    @Override
    public void operate(Stack<Double> numbers)
    {
        double a = numbers.pop();
        double b = numbers.pop();
        numbers.push(b < a ? 1.0 : 0.0);
    }
}
