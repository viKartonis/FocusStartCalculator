package com.company.calculator.operations;

import java.util.Stack;

public class Sub implements Operation
{
    public void operate(Stack<Double> numbers)
    {
        double a = numbers.pop(),
               b = numbers.pop();
        numbers.push(b-a);
    }
}
