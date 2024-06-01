package interpreter;

import ast.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {
    private Parser parser = new Parser();

    @Test
    public void expr() {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token("NUMBER", String.valueOf(3.1415)));
        tokens.add(new Token("PLUS", "+"));
        tokens.add(new Token("NUMBER", String.valueOf(2.718)));
        tokens.add(new Token("PLUS", "+"));
        tokens.add(new Token("NUMBER", "5.0"));
        tokens.add(new Token("MULT", "*"));
        tokens.add(new Token("FUNC", "sin"));
        tokens.add(new Token("L_PAREN", "("));
        tokens.add(new Token("NUMBER", "5.0"));
        tokens.add(new Token("R_PAREN", ")"));

        parser.setInput(tokens);

        AST resultTree = parser.expr();
        AST expected = new Addition(
                new Addition(
                        new Const(3.1415),
                        new Const(2.718)
                ),
                new Multiplication(
                        new Const(5),
                        new Sin(new Const(5))
                )
        );

        assertEquals(expected, resultTree);
    }

    @Test
    public void testAdditions() {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token("NUMBER", "5.0"));
        tokens.add(new Token("PLUS", "+"));
        tokens.add(new Token("NUMBER", "2.0"));
        tokens.add(new Token("MINUS", "-"));
        tokens.add(new Token("NUMBER", "3.0"));

        parser.setInput(tokens);

        AST resultTree = parser.expr();
        AST expected = new Substraction(
                new Addition(
                        new Const(5),
                        new Const(2)
                ),
                new Const(3)
        );

        assertEquals(expected, resultTree);
    }

    @Test
    public void testMultiplications() {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token("NUMBER", "2.0"));
        tokens.add(new Token("MULT", "*"));
        tokens.add(new Token("L_PAREN", "("));
        tokens.add(new Token("NUMBER", "5.0"));
        tokens.add(new Token("MINUS", "-"));
        tokens.add(new Token("NUMBER", "1.0"));
        tokens.add(new Token("R_PAREN", ")"));
        tokens.add(new Token("DIV", "/"));
        tokens.add(new Token("NUMBER", "3.0"));

        parser.setInput(tokens);

        AST resultTree = parser.expr();
        AST expected = new Division(
                new Multiplication(
                    new Const(2),
                    new Substraction(
                            new Const(5),
                            new Const(1)
                    )
                ),
                new Const(3)
        );

        assertEquals(expected, resultTree);
    }

    @Test
    public void testPow() {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token("NUMBER", "2.0"));
        tokens.add(new Token("RAISE", "^"));
        tokens.add(new Token("L_PAREN", "("));
        tokens.add(new Token("NUMBER", "5.0"));
        tokens.add(new Token("RAISE", "^"));
        tokens.add(new Token("L_PAREN", "("));
        tokens.add(new Token("NUMBER", "3.0"));
        tokens.add(new Token("R_PAREN", ")"));
        tokens.add(new Token("R_PAREN", ")"));

        parser.setInput(tokens);

        AST resultTree = parser.expr();
        AST expected = new Pow(
                new Const(2),
                new Pow(
                        new Const(5),
                        new Const(3)
                )
        );

        assertEquals(expected, resultTree);
    }

    @Test
    public void testPower() {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token("MINUS", "-"));
        tokens.add(new Token("FUNC", "sin"));
        tokens.add(new Token("L_PAREN", "("));
        tokens.add(new Token("VARIABLE", "x"));
        tokens.add(new Token("R_PAREN", ")"));

        parser.setInput(tokens);

        AST resultTree = parser.expr();
        AST expected = new Negate(
                new Sin(
                        new Variable()
                )
        );

        assertEquals(expected, resultTree);
    }
}