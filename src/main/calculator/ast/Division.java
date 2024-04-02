package ast;

public class Division extends BinOp {

    public Division(AST left, AST right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("Division {\n  left {\n  %s\n}\n  right {\n  %s\n}\n}",left, right);
    }
    @Override
    public double getNumericResult(double value) {
        return left.getNumericResult(value) / right.getNumericResult(value);
    }
    @Override
    public String getStringRepresentation() {
        return String.format("(%s)/(%s)", left.getStringRepresentation(), right.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Division(
                new Substraction(
                        new Multiplication(
                                left.getDerivative(),
                                right
                        ),
                        new Multiplication(
                                left,
                                right.getDerivative()
                        )
                ),
                new Pow(
                        right,
                        new Const("2")
                )
        );
    }
}

