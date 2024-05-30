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

        if (left instanceof Const && ((Const) left).isZero()) {
            return right;
        }

        if (right instanceof Const && ((Const) right).isZero()) {
            return left;
        }

        if (left instanceof Const && right instanceof Const) {
            double sum = left.getNumericResult(0) + right.getNumericResult(0);
            return new Const(String.valueOf(sum));
        }

        // Collapse numbers in nested additions
        if (left instanceof Const && right instanceof Addition rightAddition) {
            if (rightAddition.left instanceof Const) {
                double collapsed = left.getNumericResult(0) + rightAddition.left.getNumericResult(0);
                return new Addition(new Const(String.valueOf(collapsed)), rightAddition.right).simplify();
            } else if (rightAddition.right instanceof Const) {
                double collapsed = left.getNumericResult(0) + rightAddition.right.getNumericResult(0);
                return new Addition(new Const(String.valueOf(collapsed)), rightAddition.left).simplify();
            }
        }

        if (right instanceof Const && left instanceof Addition leftAddition) {
            if (leftAddition.left instanceof Const) {
                double collapsed = right.getNumericResult(0) + leftAddition.left.getNumericResult(0);
                return new Addition(new Const(String.valueOf(collapsed)), leftAddition.right).simplify();
            } else if (leftAddition.right instanceof Const) {
                double collapsed = right.getNumericResult(0) + leftAddition.right.getNumericResult(0);
                return new Addition(new Const(String.valueOf(collapsed)), leftAddition.left).simplify();
            }
        }


        return this;
    }
}

