package ast;

public class Pow extends BinOp {

    public Pow(AST left, AST right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return String.format("Power {\n  left {\n  %s\n}\n  right {\n  %s\n}\n}",left, right);
    }
    @Override
    public double getNumericResult(double value) {
        return Math.pow(left.getNumericResult(value), right.getNumericResult(value));
    }
    @Override
    public String getStringRepresentation() {
        return String.format("%s^%s", left.getStringRepresentation(), right.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Multiplication(
               new Multiplication(
                       right,
                       new Pow(
                               left,
                               new Substraction(
                                       right,
                                       new Const(1)
                               )
                       )
               ),
               left.getDerivative()
        );
    }

    @Override
    public AST simplify() {
        left = left.simplify();
        right = right.simplify();

        if (right instanceof Const r && left instanceof Const l) {
            return new Const(
                    Math.pow(l.getNumericResult(), r.getNumericResult())
            );
        }

        if (right instanceof Const r && r.isOne()) {
            return left;
        }

        if (right instanceof Const r && r.isZero()) {
            return new Const(1);
        }

        return this;
    }
}

