package ast;

import org.junit.Test;

import static org.junit.Assert.*;

public class AstDerivativesTest {

    private final double testDelta = 0.0001;

    @Test
    public void testAddition() {
        AST tree = new Addition(
                new Const(5),
                new Variable()
        );

        AST expected = new Addition(
                new Const(0),
                new Const(1)
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(1, tree.getDerivative().getNumericResult(1), testDelta);

    }

    @Test
    public void testMultiplication() {
        AST tree = new Multiplication(
                new Sin(
                        new Addition(
                                new Const(2),
                                new Variable()
                        )
                ),
                new Cos(
                        new Variable()
                )
        );

        AST expected = new Addition(
                new Multiplication(
                        new Multiplication(
                                new Cos(
                                        new Addition(
                                                new Const(2),
                                                new Variable()
                                        )
                                ),
                                new Addition(
                                        new Const(0),
                                        new Const(1)
                                )
                        ),
                        new Cos(
                                new Variable()
                        )
                ),
                new Multiplication(
                        new Sin(
                                new Addition(
                                        new Const(2),
                                        new Variable()
                                )
                        ),
                        new Negate(
                                new Multiplication(
                                        new Sin(
                                                new Variable()
                                        ),
                                        new Const(1)
                                )
                        )
                )
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(-0.653644, tree.getDerivative().getNumericResult(1), testDelta);
    }

    @Test
    public void testDivision() {
        AST tree = new Division(
            new Tan(
                    new Variable()
            ),
            new Cot(
                    new Variable()
            )
        );

        AST expected = new Division(
                new Substraction(
                    new Multiplication(
                            new Division(
                                    new Const(1),
                                    new Pow(
                                            new Cos(
                                                    new Variable()
                                            ),
                                            new Const(2)
                                    )
                            ),
                            new Cot(
                                    new Variable()
                            )
                    ),
                    new Multiplication(
                            new Tan(
                                    new Variable()
                            ),
                            new Negate(
                                    new Division(
                                            new Const(1),
                                            new Pow(
                                                    new Sin(
                                                            new Variable()
                                                    ),
                                                    new Const(2)
                                            )
                                    )
                            )
                    )
                ),
                new Pow(
                        new Cot(
                                new Variable()
                        ),
                        new Const(2)
                )
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(-84.0253, tree.getDerivative().getNumericResult(5), testDelta);
    }

    @Test
    public void testPow() {
        AST tree = new Negate(
                new Pow(
                        new Variable(),
                        new Const(2)
                )
        );

        AST expected = new Negate(
                new Multiplication(
                        new Multiplication(
                                new Const(2),
                                new Pow(
                                        new Variable(),
                                        new Substraction(
                                                new Const(2),
                                                new Const(1)
                                        )
                                )
                        ),
                        new Const(1)
                )
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(-10.0, tree.getDerivative().getNumericResult(5), testDelta);
    }

    @Test
    public void testSubtract() {
        AST tree = new Substraction(
                new Acos(
                        new Variable()
                ),
                new Asin(
                        new Variable()
                )
        );

        AST expected = new Substraction(
                new Negate(
                        new Division(
                                new Const(1),
                                new Sqrt(
                                        new Substraction(
                                                new Const(1),
                                                new Pow(
                                                        new Variable(),
                                                        new Const(2)
                                                )
                                        )
                                )
                        )
                ),
                new Division(
                    new Const(1),
                    new Sqrt(
                            new Substraction(
                                    new Const(1),
                                    new Pow(
                                            new Variable(),
                                            new Const(2)
                                    )
                            )
                    )
                )
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(-2, tree.getDerivative().getNumericResult(0), testDelta);
    }

    @Test
    public void testArcfuncs() {
        AST tree = new Acot(
                new Atan(
                        new Variable()
                )
        );

        AST expected = new Negate(
                new Division(
                        new Division(
                                new Const(1),
                                new Addition(
                                        new Const(1),
                                        new Pow(
                                                new Variable(),
                                                new Const(2)
                                        )
                                )
                        ),
                        new Addition(
                                new Const(1),
                                new Pow(
                                        new Atan(
                                                new Variable()
                                        ),
                                        new Const(2)
                                )
                        )
                )
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(-0.0133259, tree.getDerivative().getNumericResult(5), testDelta);
    }

    @Test
    public void testExponent() {
        AST tree = new Log(
                new Exp(
                        new Variable()
                )
        );

        AST expected = new Division(
                new Multiplication(
                        new Exp(
                                new Variable()
                        ),
                        new Const(1)
                ),
                new Exp(
                        new Variable()
                )
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(1, tree.getDerivative().getNumericResult(5), testDelta);
    }

    @Test
    public void testSqrt() {
        AST tree = new Sqrt(new Variable());

        AST expected = new Division(
            new Const(1),
            new Multiplication(
                    new Const(2),
                    new Sqrt(
                            new Variable()
                    )
            )
        );

        assertEquals(expected, tree.getDerivative());
        assertEquals(0.288675, tree.getDerivative().getNumericResult(3), testDelta);
    }
}