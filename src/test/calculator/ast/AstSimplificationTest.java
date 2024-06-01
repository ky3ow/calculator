package ast;

import org.junit.Test;

import java.net.ConnectException;

import static org.junit.Assert.assertEquals;

public class AstSimplificationTest {

    private final double testDelta = 0.0001;

    @Test
    public void testPow() {
        AST tree = new Pow(
                new Variable(),
                new Pow(
                        new Variable(),
                        new Const(0)
                )
        );

        AST expected = new Variable();

        assertEquals(expected, tree.simplify());
    }

    @Test
    public void testSubtract() {
        AST tree = new Substraction(
            new Const(2),
            new Substraction(
                    new Const(5),
                    new Substraction(
                            new Const(0),
                            new Const(0)
                    )
            )
        );

        AST expected = new Const(-3);

        assertEquals(expected, tree.simplify());
    }

    @Test
    public void testNegation() {
        AST zero = new Negate(new Const(0));

        assertEquals(new Const(0), zero.simplify());

        AST nested = new Negate(new Negate(new Variable()));

        assertEquals(new Variable(), nested.simplify());
    }

    @Test
    public void testDivision () {
        AST zero = new Negate(new Const(0));

        assertEquals(new Const(0), zero.simplify());

        AST nested = new Negate(new Negate(new Variable()));

        assertEquals(new Variable(), nested.simplify());
    }

}