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
}