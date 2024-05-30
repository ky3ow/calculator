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

        // Negations
        if (left instanceof Negate && right instanceof Negate) {
            return new Multiplication(((Negate) left).op, ((Negate) right).op).simplify();
        }
        if (left instanceof Negate) {
           return new Negate(new Multiplication(((Negate) left).op, right)).simplify();
        }
        if (right instanceof Negate) {
            return new Negate(new Multiplication(left, ((Negate) right).op)).simplify();
        }

        // Constants
        if (left instanceof Const && ((Const) left).isZero() || right instanceof Const && ((Const) right).isZero()) {
            return new Const("0");
        }
        if (left instanceof Const && ((Const) left).isOne()) {
            return right;
        }
        if (right instanceof Const && ((Const) right).isOne()) {
            return left;
        }

        // Collapse numbers
        if (left instanceof Const && right instanceof Const) {
            double collapsed = left.getNumericResult(0) * right.getNumericResult(0);
            return new Const(String.valueOf(collapsed));
        }

        // Collapse numbers in nested multiplications
        if (left instanceof Const && right instanceof Multiplication rightMult) {
            if (rightMult.left instanceof Const) {
                double collapsed = left.getNumericResult(0) * rightMult.left.getNumericResult(0);
                return new Multiplication(new Const(String.valueOf(collapsed)), rightMult.right).simplify();
            } else if (rightMult.right instanceof Const) {
                double collapsed = left.getNumericResult(0) * rightMult.right.getNumericResult(0);
                return new Multiplication(new Const(String.valueOf(collapsed)), rightMult.left).simplify();
            }
        }

        if (right instanceof Const && left instanceof Multiplication leftMult) {
            if (leftMult.left instanceof Const) {
                double collapsed = right.getNumericResult(0) * leftMult.left.getNumericResult(0);
                return new Multiplication(new Const(String.valueOf(collapsed)), leftMult.right).simplify();
            } else if (leftMult.right instanceof Const) {
                double collapsed = right.getNumericResult(0) * leftMult.right.getNumericResult(0);
                return new Multiplication(new Const(String.valueOf(collapsed)), leftMult.left).simplify();
            }
        }

        return this;
    }
}

