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
        return String.format("%s/%s", left.getStringRepresentation(), right.getStringRepresentation());
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
                        new Const(2)
                )
        );
    }

    @Override
    public AST simplify() {
        left = left.simplify();
        right = right.simplify();

        // Negations
        if (left instanceof Negate l && right instanceof Negate r) {
            return new Division(l.op, r.op).simplify();
        }
        if (left instanceof Negate l) {
            return new Negate(new Division(l.op, right)).simplify();
        }
        if (right instanceof Negate r) {
            return new Negate(new Division(left, r.op)).simplify();
        }

        if (right instanceof Const r && r.isOne()) {
            return left;
        }

        // Collapse numbers
        if (left instanceof Const && right instanceof Const) {
            double collapsed = left.getNumericResult() / right.getNumericResult();
            return new Const(collapsed);
        }

        return this;
    }
}

