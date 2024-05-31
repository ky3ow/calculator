package ast;

public class Addition extends BinOp {

    public Addition (AST left, AST right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("Addition {\n  left {\n  %s\n}\n  right {\n  %s\n}\n}",left, right);
    }
    @Override
    public double getNumericResult(double value) {
        return left.getNumericResult(value) + right.getNumericResult(value);
    }
    @Override
    public String getStringRepresentation() {
        return String.format("(%s+%s)", left.getStringRepresentation(), right.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Addition(
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
            double sum = left.getNumericResult() + right.getNumericResult();
            return new Const(sum);
        }

        // Collapse numbers in nested additions
        if (left instanceof Const && right instanceof Addition rightAddition) {
            if (rightAddition.left instanceof Const) {
                double collapsed = left.getNumericResult() + rightAddition.left.getNumericResult();
                return new Addition(new Const(collapsed), rightAddition.right).simplify();
            } else if (rightAddition.right instanceof Const) {
                double collapsed = left.getNumericResult() + rightAddition.right.getNumericResult();
                return new Addition(new Const(collapsed), rightAddition.left).simplify();
            }
        }

        if (right instanceof Const && left instanceof Addition leftAddition) {
            if (leftAddition.left instanceof Const) {
                double collapsed = right.getNumericResult() + leftAddition.left.getNumericResult();
                return new Addition(new Const(collapsed), leftAddition.right).simplify();
            } else if (leftAddition.right instanceof Const) {
                double collapsed = right.getNumericResult() + leftAddition.right.getNumericResult();
                return new Addition(new Const(collapsed), leftAddition.left).simplify();
            }
        }


        return this;
    }
}

