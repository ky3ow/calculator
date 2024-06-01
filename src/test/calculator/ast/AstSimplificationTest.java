package ast;

import org.junit.Test;

import java.net.ConnectException;

import static org.junit.Assert.assertEquals;

public class AstSimplificationTest {

    private final double testDelta = 0.0001;

    @Test
    public void testPow() {
        AST zeroPow = new Pow(
                new Variable(),
                new Pow(
                        new Variable(),
                        new Const(0)
                )
        );

        AST expected = new Variable();

        assertEquals(expected, zeroPow.simplify());

        AST numPow = new Pow(
                new Const(2),
                new Const(3)
        );

        assertEquals(new Const(8), numPow.simplify());
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
        AST doubleNegate = new Division(
                new Negate(new Const(1)),
                new Negate(new Const(3))
        );

        assertEquals(new Const(1/3.), doubleNegate.simplify());

        AST leftNegate = new Division(
                new Negate(new Variable()),
                new Const(1)
        );

        assertEquals(new Negate(new Variable()), leftNegate.simplify());

        AST rightNegate = new Division(
                new Const(2),
                new Negate(new Variable())
        );

        assertEquals(new Negate(
                new Division(
                        new Const(2),
                        new Variable()
                )
        ), rightNegate.simplify());
    }

    @Test
    public void testAddition() {
        AST leftZero = new Addition(
                new Const(0),
                new Variable()
        );

        assertEquals(new Variable(), leftZero.simplify());

        AST rightZero = new Addition(
                new Variable(),
                new Const(0)
        );

        assertEquals(new Variable(), rightZero.simplify());

        AST bothNumbers = new Addition(
                new Const(4),
                new Const(2)
        );

        assertEquals(new Const(6), bothNumbers.simplify());

        AST rightNestedL = new Addition(
                new Const(5),
                new Addition(
                        new Const(1),
                        new Variable()
                )
        );

        assertEquals(new Addition(
                new Const(6),
                new Variable()
        ), rightNestedL.simplify());

        AST rightNestedR = new Addition(
                new Const(5),
                new Addition(
                        new Variable(),
                        new Const(1)
                )
        );

        assertEquals(new Addition(
                new Const(6),
                new Variable()
        ), rightNestedR.simplify());

        AST leftNestedL = new Addition(
                new Addition(
                        new Const(4),
                        new Variable()
                ),
                new Const(9)
        );

        assertEquals(new Addition(
                new Const(13),
                new Variable()
        ), leftNestedL.simplify());

        AST leftNestedR = new Addition(
                new Addition(
                        new Variable(),
                        new Const(4)
                ),
                new Const(9)
        );

        assertEquals(new Addition(
                new Const(13),
                new Variable()
        ), leftNestedR.simplify());
    }

    @Test
    public void testMultiplication() {
        AST multiplyByZero = new Multiplication(
                new Const(0),
                new Variable()
        );

        assertEquals(new Const(0), multiplyByZero.simplify());

        AST leftOne = new Multiplication(
                new Const(1),
                new Variable()
        );

        assertEquals(new Variable(), leftOne.simplify());

        AST rightOne = new Multiplication(
                new Variable(),
                new Const(1)
        );

        assertEquals(new Variable(), rightOne.simplify());

        AST doubleNegate = new Multiplication(
                new Negate(new Variable()),
                new Negate(new Const(3))
        );

        assertEquals(new Multiplication(
                new Variable(),
                new Const(3)
        ), doubleNegate.simplify());

        AST leftNegate = new Multiplication(
                new Negate(new Const(3)),
                new Const(2)
        );

        assertEquals(new Negate(new Const(6)), leftNegate.simplify());

        AST rightNegate = new Multiplication(
                new Const(2),
                new Negate(new Const(3))
        );

        assertEquals(new Negate(new Const(6)), rightNegate.simplify());

        AST rightNestedL = new Multiplication(
                new Const(2),
                new Multiplication(
                        new Const(4),
                        new Variable()
                )
        );

        assertEquals(new Multiplication(
                new Const(8),
                new Variable()
        ), rightNestedL.simplify());

        AST rightNestedR = new Multiplication(
                new Const(2),
                new Multiplication(
                        new Variable(),
                        new Const(4)
                )
        );

        assertEquals(new Multiplication(
                new Const(8),
                new Variable()
        ), rightNestedR.simplify());

        AST leftNestedL = new Multiplication(
                new Multiplication(
                        new Const(4),
                        new Variable()
                ),
                new Const(5)
        );

        assertEquals(new Multiplication(
                new Const(20),
                new Variable()
        ), leftNestedL.simplify());

        AST leftNestedR = new Multiplication(
                new Multiplication(
                        new Variable(),
                        new Const(4)
                ),
                new Const(5)
        );

        assertEquals(new Multiplication(
                new Const(20),
                new Variable()
        ), leftNestedR.simplify());

    }

    @Test
    public void testDefaultSimplifications() {
        AST tree = new Sin(
                new Pow(
                        new Substraction(
                                new Const(1),
                                new Variable()
                        ),
                        new Variable()
                )
        );

        assertEquals(new Sin(
                new Pow(
                        new Substraction(
                                new Const(1),
                                new Variable()
                        ),
                        new Variable()
                )
        ), tree.simplify());
    }
}