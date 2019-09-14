package com.company.view;
import com.company.calculator.Calculation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JFrame
{
    private JTextField input;
    private Container container = new Container();
    private GridBagConstraints constraints = new GridBagConstraints();
    Calculation calculation = new Calculation();

    private void delete() {
        String expression = input.getText();
        input.setText(expression.substring(0, expression.length() - 1));
    }

    private void calculate() {
        try {
            double result = calculation.getResult(input.getText());
            String separate = String.valueOf(result).split("\\.")[1];
            if (separate.equals("0"))
                input.setText(Integer.toString((int) result));
            else input.setText(Double.toString(result));
        }
        catch (Exception re)
        {
            input.setText("Incorrect input");
        }
    }

    private void initComponent(JComponent component, int width, int x, int y)
    {
        constraints.gridwidth = width;
        constraints.gridheight = 1;
        constraints.gridx = x;
        constraints.gridy = y;
        container.add(component, constraints);

    }

    private void createTypeButton(String buttonText, int width, int x, int y)
    {
        createButton(buttonText, (ActionEvent e) ->
        {
            input.setText(input.getText() + buttonText);
        }, width, x, y);
    }

    private void createButton(String buttonText, ActionListener al, int width, int x, int y)
    {
        JButton button = new JButton(buttonText);
        button.addActionListener(al);
        button.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    delete();
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculate();
                }
                else if (calculation.getAllowedSymbols().contains(Character.toString(e.getKeyChar())) ||
                        Character.isDigit(e.getKeyChar())) {
                    input.setText(input.getText() + e.getKeyChar());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        initComponent(button, width, x, y);
    }

    public GUI()
    {
        container.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        container.setLayout(new GridBagLayout());

        input = new JTextField();
        input.setHorizontalAlignment(SwingConstants.RIGHT);
        input.setFont(new Font("consolas", Font.PLAIN,30));
        input.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    delete();
                }
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculate();
                }
                else if (!calculation.getAllowedSymbols().contains(Character.toString(e.getKeyChar())) &&
                        !Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.EAST;
        initComponent(input, 5, 0,0);

        createButton("AC", (ActionEvent e) -> {
            input.setText("");
        }, 1,  4, 1);

        createButton("=", (ActionEvent e) -> { calculate(); }, 1, 1, 5);

        createButton("C", (ActionEvent e) -> {
           delete();
        }, 2,  3, 6);

        createTypeButton("(", 1,  3, 1);
        createTypeButton(")", 1,  2, 1);
        createTypeButton("/", 1,  1, 1);
        createTypeButton("<", 1,  0, 1);


        createTypeButton("7", 1,  4, 2);
        createTypeButton("8", 1,  3, 2);
        createTypeButton("9", 1,  2, 2);
        createTypeButton("*", 1,  1, 2);
        createTypeButton(">", 1,  0, 2);


        createTypeButton("4", 1,  4, 3);
        createTypeButton("5", 1, 3, 3);
        createTypeButton("6", 1, 2, 3);
        createTypeButton("-", 1,  1, 3);
        createTypeButton("<=", 1,  0, 3);


        createTypeButton("1", 1,  4, 4);
        createTypeButton("2", 1,  3, 4);
        createTypeButton("3", 1,  2, 4);
        createTypeButton("+", 1,  1,4);
        createTypeButton(">=", 1,  0, 4);


        createTypeButton("0", 2,  3, 5);
        createTypeButton(".", 1,  2, 5);
        createTypeButton("==", 1,  0, 5);


        createTypeButton("?", 1,  2, 6);
        createTypeButton(":", 1, 1, 6);
        createTypeButton("!=", 1,  0, 6);


        add(container);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(375, 250);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);
        setTitle("calculator");
        setVisible(true);
    }
}
