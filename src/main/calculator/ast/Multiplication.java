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
        if (left instanceof Negate l && right instanceof Negate r) {
            return new Multiplication(l.op, r.op).simplify();
        }
        if (left instanceof Negate l) {
           return new Negate(new Multiplication(l.op, right)).simplify();
        }
        if (right instanceof Negate r) {
            return new Negate(new Multiplication(left, r.op)).simplify();
        }

        // Constants
        if (left instanceof Const l && l.isZero() || right instanceof Const r && r.isZero()) {
            return new Const(0);
        }
        if (left instanceof Const l && l.isOne()) {
            return right;
        }
        if (right instanceof Const r && r.isOne()) {
            return left;
        }

        // Collapse numbers
        if (left instanceof Const && right instanceof Const) {
            double collapsed = left.getNumericResult() * right.getNumericResult();
            return new Const(collapsed);
        }

        // Collapse numbers in nested multiplications
        if (left instanceof Const && right instanceof Multiplication rightMult) {
            if (rightMult.left instanceof Const) {
                double collapsed = left.getNumericResult() * rightMult.left.getNumericResult();
                return new Multiplication(new Const(collapsed), rightMult.right).simplify();
            } else if (rightMult.right instanceof Const) {
                double collapsed = left.getNumericResult() * rightMult.right.getNumericResult();
                return new Multiplication(new Const(collapsed), rightMult.left).simplify();
            }
        }

        if (right instanceof Const && left instanceof Multiplication leftMult) {
            if (leftMult.left instanceof Const) {
                double collapsed = right.getNumericResult() * leftMult.left.getNumericResult();
                return new Multiplication(new Const(collapsed), leftMult.right).simplify();
            } else if (leftMult.right instanceof Const) {
                double collapsed = right.getNumericResult() * leftMult.right.getNumericResult();
                return new Multiplication(new Const(collapsed), leftMult.left).simplify();
            }
        }

        return this;
    }
}

