package interpreter;

import java.util.ArrayList;
import java.util.Set;

public class Tokenizer {

    private static final Set<String> operators = Set.of(
            "+", "-", "*", "/", "^", "(", ")" );

    // checks if '-' sign is unary, unary -> true, binary -> false
    private static boolean unaryMinusCheck(ArrayList<Token> tokens) {
        if (tokens.size() > 0) {
            Token previousToken = tokens.get(tokens.size() - 1);

            if (previousToken.getType() == "NUMBER"
                    ||  previousToken.getType() == "VARIABLE"
                    ||  previousToken.getType() == "R_PAREN" ) {
                return false;
            }
        }
        return true;
    }

    // checks if there is omitted multiplication in expression (e.g 5x = 5*x)
    private static boolean omittedMultiplication(ArrayList<Token> tokens) {
        if (tokens.size() > 0) {

            Token previousToken = tokens.get(tokens.size() - 1);
            boolean operandBehindX = previousToken.getType() == "NUMBER";
            boolean parenthesisBehindX = previousToken.getType() == "R_PAREN";
            boolean xBehindAnything = previousToken.getType() == "VARIABLE";

            return operandBehindX || parenthesisBehindX || xBehindAnything ;
        }
        return false;
    }

    // fills omitted multiplications
    private static void fillMultiplication(ArrayList<Token> tokens) {
        if (omittedMultiplication(tokens)) tokens.add(new Token("MULT", "*"));
    }

    // checks type of symbol (operator, number, variable ,function)
    private static String checkType(char symbol) {
        if (operators.contains(Character.toString(symbol)))
            return "operator";
        if (Character.isDigit(symbol) || symbol == '.')
            return "number";
        if (symbol == 'x')
            return "variable";

        return "function";
    }

    public static ArrayList<Token> TokenizeExpression(String stringToTokenize) {

        ArrayList<Token> tokens = new ArrayList<Token>();

        for (int i = 0; i < stringToTokenize.length(); i++) {

            char symbol = stringToTokenize.charAt(i);

            switch (checkType(symbol)) {

                case "operator":

                    switch (symbol) {
                        case '-':
                            if (unaryMinusCheck(tokens)) {
                                tokens.add(new Token("U_MINUS", "-"));
                            } else {
                                tokens.add(new Token("MINUS", "-"));
                            }
                            break;
                        case '+':
                            tokens.add(new Token("PLUS", "+"));
                            break;
                        case '*':
                            tokens.add(new Token("MULT", "*"));
                            break;
                        case '/':
                            tokens.add(new Token("DIV", "/"));
                            break;
                        case '^':
                            if (tokens.get(tokens.size() - 1).getValue().equals("" + 2.718)) {
                                tokens.set(tokens.size() - 1, new Token("FUNC", "exp"));
                            } else {
                                tokens.add(new Token("RAISE", "^"));
                            }
                            break;
                        case '(':
                            fillMultiplication(tokens);
                            tokens.add(new Token("L_PAREN", "("));
                            break;
                        case ')':
                            tokens.add(new Token("R_PAREN", ")"));
                            break;
                    } break;
                case "number":

                    String number = "";
                    while (i < stringToTokenize.length()) {
                        char digit = stringToTokenize.charAt(i);
                        if (!checkType(digit).equals("number")) {
                            break;
                        }
                        number += digit;
                        i++;
                    }

                    fillMultiplication(tokens);
                    tokens.add(new Token("NUMBER", number));
                    i--;

                    break;

                case "variable":

                    fillMultiplication(tokens);
                    tokens.add(new Token("VARIABLE", "x"));

                    break;

                case "function":

                    String func = "";

                    while (i < stringToTokenize.length()) {

                        if (func.equals("e") || func.equals("π")) break;

                        char letter = stringToTokenize.charAt(i);

                        if (checkType(letter).equals("operator") || checkType(letter).equals("number")) {
                            break;
                        }
                        func += letter;
                        i++;

                    }

                    fillMultiplication(tokens);

                    switch(func) {
                        case "e":
                            tokens.add(new Token("NUMBER", "" + 2.718));
                            break;
                        case "π":
                            tokens.add(new Token("NUMBER", "" + 3.1415));
                            break;
                        default:
                            tokens.add(new Token("FUNC", func));
                    }

                    i--;

                    break;
            }
        }
        return tokens;
    }
}
