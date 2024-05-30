package main;

import javax.swing.*;

import ast.AST;
import interpreter.Parser;
import interpreter.Token;
import interpreter.Tokenizer;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Calculator implements ActionListener {

    String state = "symbolic";

    Font font = new Font("serif", Font.BOLD, 16);

    JFrame frame;

    JTextField inputField;
    JTextArea outputField;
    JTextField setVariable;

    JButton changeState;
    JButton showMore;
    JButton[] numberButtons = new JButton[10];

    JButton addButton = new JButton("+"),
            subButton = new JButton("-"),
            mulButton = new JButton("*"),
            divButton = new JButton("/"),
            decButton = new JButton("."),
            powButton = new JButton("^"),
            equButton = new JButton("d/dx f(x)"),

    xButton = new JButton("x"),
            eButton = new JButton("e"),
            piButton = new JButton("π"),
            delButton = new JButton("<<<"),
            clrButton = new JButton("C"),

    sinButton = new JButton("sin"),
            cosButton = new JButton("cos"),
            asinButton = new JButton("asin"),
            acosButton = new JButton("acos"),
            tanButton = new JButton("tan"),
            cotButton = new JButton("cot"),
            atanButton = new JButton("atan"),
            acotButton = new JButton("acot"),
            logButton = new JButton("log"),
            sqrtButton = new JButton("√"),

    leftParenButton = new JButton("("),
            rightParenButton = new JButton(")");

    JButton[] functionButtons = {
            addButton,
            subButton,
            mulButton,
            divButton,
            decButton,
            powButton,
            equButton,
            xButton,
            eButton,
            piButton,
            delButton,
            clrButton,
            sinButton,
            cosButton,
            asinButton,
            acosButton,
            tanButton,
            cotButton,
            atanButton,
            acotButton,
            logButton,
            sqrtButton,
            leftParenButton,
            rightParenButton,
    };

    JPanel varPanel;
    JPanel funcPanel;
    JPanel numPanel;
    JPanel opPanel;

    Calculator() {

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 600);
        frame.setLayout(null);
        frame.setResizable(false);

        inputField = new JTextField();
        inputField.setFont(font);
        inputField.setBounds(60, 25, 320, 50);
        inputField.setEditable(false);

        outputField = new JTextArea();
        outputField.setLineWrap(true);
        outputField.setFont(font);
        outputField.setBounds(60, 80, 280, 50);
        outputField.setEditable(false);

        showMore = new JButton();
        showMore.addActionListener(this);
        showMore.setBounds(340, 80, 40, 50);
        showMore.setText("...");

        changeState = new JButton("X");
        changeState.addActionListener(this);
        changeState.setBackground(new Color(255,75,75));
        changeState.setBounds(10, 25, 40, 50);
        changeState.setBorder(inputField.getBorder());

        setVariable = new JTextField();
        setVariable.setEditable(false);
        setVariable.setBounds(10, 80, 40, 50);

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(font);
            numberButtons[i].addActionListener(this);
        }


        for (JButton jButton : functionButtons) {
            jButton.addActionListener(this);
            jButton.setFont(font);
        }

        varPanel = new JPanel();
        varPanel.setBounds(10, 140, 370, 30);
        varPanel.setLayout(new GridLayout(1,5,12,0));
        varPanel.add(xButton);
        varPanel.add(eButton);
        varPanel.add(piButton);
        varPanel.add(clrButton);
        varPanel.add(delButton);

        funcPanel = new JPanel();
        funcPanel.setBounds(10, 180, 370, 70);
        funcPanel.setLayout(new GridLayout(2,5,12,5));
        funcPanel.add(sinButton);
        funcPanel.add(cosButton);
        funcPanel.add(tanButton);
        funcPanel.add(cotButton);
        funcPanel.add(logButton);
        funcPanel.add(asinButton);
        funcPanel.add(acosButton);
        funcPanel.add(atanButton);
        funcPanel.add(acotButton);
        funcPanel.add(sqrtButton);

        numPanel = new JPanel();
        numPanel.setBounds(10, 270, 220, 290);
        numPanel.setLayout(new GridLayout(4,3,5,5));
        numPanel.add(numberButtons[7]);
        numPanel.add(numberButtons[8]);
        numPanel.add(numberButtons[9]);
        numPanel.add(numberButtons[4]);
        numPanel.add(numberButtons[5]);
        numPanel.add(numberButtons[6]);
        numPanel.add(numberButtons[1]);
        numPanel.add(numberButtons[2]);
        numPanel.add(numberButtons[3]);
        numPanel.add(leftParenButton);
        numPanel.add(numberButtons[0]);
        numPanel.add(rightParenButton);

        opPanel = new JPanel();
        opPanel.setBounds(255, 270, 124, 215);
        opPanel.setLayout(new GridLayout(3,2,5,5));
        opPanel.add(addButton);
        opPanel.add(subButton);
        opPanel.add(mulButton);
        opPanel.add(divButton);
        opPanel.add(decButton);
        opPanel.add(powButton);

        equButton.setBounds(255, 490, 124, 68);
        equButton.setFont(font);

        frame.add(inputField);
        frame.add(outputField);
        frame.add(changeState);
        frame.add(showMore);
        frame.add(setVariable);
        frame.add(equButton);

        frame.add(varPanel);
        frame.add(funcPanel);
        frame.add(numPanel);
        frame.add(opPanel);

        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        try {
            if (button.equals(equButton)) {   // 1

                if (inputField.getText().isEmpty()) inputField.setText("0");
                String text = inputField.getText();
                ArrayList<Token> tokens = Tokenizer.TokenizeExpression(text);
                Parser par = new Parser();
                par.setInput(tokens);
                String output;
                AST derivative = par.expr().getDerivative().simplify();
                System.out.println(derivative);

                if (state.equals("symbolic")){ // 2
                    output = derivative.getStringRepresentation();
                } else {
                    if (setVariable.getText().isEmpty()) setVariable.setText("0");
                    double value = Double.parseDouble(setVariable.getText());
                    output = "" + derivative.getNumericResult(value);
                }

                if (output.equals("NaN")) output = "Ділення на 0 або корінь з від'ємного числа";
                outputField.setText(output);

            } else if (button.equals(changeState)) { // 3

                if (state.equals("symbolic")) {
                    state = "numeric";
                    button.setBackground(new Color(117,255,121));
                    setVariable.setEditable(true);
                } else {
                    state = "symbolic";
                    button.setBackground(new Color(255,75,75));
                    setVariable.setEditable(false);
                }

            } else if (button.equals(delButton)) { // 4

                String text = inputField.getText();
                inputField.setText(text.substring(0, text.length()-1));
                if (inputField.getText().isEmpty()) inputField.setText("0");

            } else if (button.equals(clrButton)) { // 5

                inputField.setText("0");
                outputField.setText("0");

            } else if (button.equals(showMore)) { // 6
                JTextArea text = new JTextArea(5,40);
                text.setText(outputField.getText());
                text.setEditable(false);
                JScrollPane scroll = new JScrollPane(text);
                text.setLineWrap(true);
                text.setFont(font);
                JOptionPane.showMessageDialog(null, scroll, "Похiдна", JOptionPane.INFORMATION_MESSAGE);
            } else { // 7
                if (inputField.getText().equals("0")){
                    inputField.setText(button.getText());
                } else {
                    inputField.setText(inputField.getText().concat(button.getText()));
                }
            }
        } catch (NullPointerException err) {
            outputField.setText("Хибний математичний вираз");
        }
    }
}
