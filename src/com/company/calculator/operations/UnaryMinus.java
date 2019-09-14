package com.company.calculator.operations;

import java.util.Stack;

public class UnaryMinus implements Operation
{
    @Override
    public void operate(Stack<Double> numbers)
    {
        numbers.push((-1) * numbers.pop());
    }
}
