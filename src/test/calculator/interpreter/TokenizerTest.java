package interpreter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TokenizerTest {

    @Test
    public void testTokenization() {
        String expression = "123+6^(5)-2*1/9+5x";
        ArrayList<Token> tokens = Tokenizer.TokenizeExpression(expression);
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new Token("NUMBER", "123"));
        expected.add(new Token("PLUS", "+"));
        expected.add(new Token("NUMBER", "6"));
        expected.add(new Token("RAISE", "^"));
        expected.add(new Token("L_PAREN", "("));
        expected.add(new Token("NUMBER", "5"));
        expected.add(new Token("R_PAREN", ")"));
        expected.add(new Token("MINUS", "-"));
        expected.add(new Token("NUMBER", "2"));
        expected.add(new Token("MULT", "*"));
        expected.add(new Token("NUMBER", "1"));
        expected.add(new Token("DIV", "/"));
        expected.add(new Token("NUMBER", "9"));
        expected.add(new Token("PLUS", "+"));
        expected.add(new Token("NUMBER", "5"));
        expected.add(new Token("MULT", "*"));
        expected.add(new Token("VARIABLE", "x"));

        assertEquals(tokens.size(), expected.size());
        assertTrue(tokens.containsAll(expected) && expected.containsAll(tokens));
    }

    @Test
    public void testFunctions() {
        String expression = "π+e+5sin(5)";
        ArrayList<Token> tokens = Tokenizer.TokenizeExpression(expression);
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new Token("NUMBER", String.valueOf(3.1415)));
        expected.add(new Token("PLUS", "+"));
        expected.add(new Token("NUMBER", String.valueOf(2.718)));
        expected.add(new Token("PLUS", "+"));
        expected.add(new Token("NUMBER", "5"));
        expected.add(new Token("MULT", "*"));
        expected.add(new Token("FUNC", "sin"));
        expected.add(new Token("L_PAREN", "("));
        expected.add(new Token("NUMBER", "5"));
        expected.add(new Token("R_PAREN", ")"));

        assertEquals(tokens.size(), expected.size());
        assertTrue(tokens.containsAll(expected) && expected.containsAll(tokens));
    }

    @Test
    public void testExp() {
        String expression = "e^(5)";
        ArrayList<Token> tokens = Tokenizer.TokenizeExpression(expression);
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new Token("FUNC", "exp"));
        expected.add(new Token("L_PAREN", "("));
        expected.add(new Token("NUMBER", "5"));
        expected.add(new Token("R_PAREN", ")"));

        assertEquals(tokens.size(), expected.size());
        assertTrue(tokens.containsAll(expected) && expected.containsAll(tokens));
    }
}