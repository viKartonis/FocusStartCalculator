package com.company.calculator.operations;

import java.util.Stack;

public class Add implements Operation
{
    public void operate(Stack<Double> numbers)
    {
        numbers.push(numbers.pop() + numbers.pop());
    }
}
