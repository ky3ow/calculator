package ast;

public class Substraction extends BinOp {

    public Substraction(AST left, AST right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("Substraction {\n  left {\n  %s\n}\n  right {\n  %s\n}\n}",left, right);
    }
    @Override
    public double getNumericResult(double value) {
        return left.getNumericResult(value) - right.getNumericResult(value);
    }
    @Override
    public String getStringRepresentation() {
        return String.format("(%s-%s)", left.getStringRepresentation(), right.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Substraction(
            left.getDerivative(),
            right.getDerivative()
        );
    }

    @Override
    public AST simplify() {
        left = left.simplify();
        right = right.simplify();

        if (left instanceof Const l && l.isZero()) {
            return right;
        }

        if (right instanceof Const r && r.isZero()) {
            return left;
        }

        if (left instanceof Const && right instanceof Const) {
            double sum = left.getNumericResult() - right.getNumericResult();
            return new Const(sum);
        }

        return this;
    }
}

