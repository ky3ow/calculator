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
                        new Const("2")
                )
        );
    }

    @Override
    public AST simplify() {
        left = left.simplify();
        right = right.simplify();

        // Negations
        if (left instanceof Negate && right instanceof Negate) {
            return new Division(((Negate) left).op, ((Negate) right).op).simplify();
        }
        if (left instanceof Negate) {
            return new Negate(new Division(((Negate) left).op, right)).simplify();
        }
        if (right instanceof Negate) {
            return new Negate(new Division(left, ((Negate) right).op)).simplify();
        }

        if (right instanceof Const && ((Const) right).isOne()) {
            return left;
        }

        // Collapse numbers
        if (left instanceof Const && right instanceof Const) {
            double collapsed = left.getNumericResult(0) / right.getNumericResult(0);
            return new Const(String.valueOf(collapsed));
        }

        return this;
    }
}

