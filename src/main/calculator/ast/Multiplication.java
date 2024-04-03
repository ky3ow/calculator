package ast;

public class Multiplication extends BinOp {

    public Multiplication(AST left, AST right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("Multiplication {\n  left {\n  %s\n}\n  right {\n  %s\n}\n}",left, right);
    }
    @Override
    public double getNumericResult(double value) {
        return left.getNumericResult(value) * right.getNumericResult(value);
    }
    @Override
    public String getStringRepresentation() {
        return String.format("%s*%s", left.getStringRepresentation(), right.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Addition(
                new Multiplication(
                        left.getDerivative(),
                        right
                ),
                new Multiplication(
                        left,
                        right.getDerivative()
                )
        );
    }
}

