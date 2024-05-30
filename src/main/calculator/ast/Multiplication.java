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

    @Override
    public AST simplify() {
        left = left.simplify();
        right = right.simplify();

        if (left instanceof Const && ((Const) left).isZero() || right instanceof Const && ((Const) right).isZero()) {
            return new Const("0");
        }
        if (left instanceof Const && ((Const) left).isOne()) {
            return right;
        }
        if (right instanceof Const && ((Const) right).isOne()) {
            return left;
        }

        return this;
    }
}

