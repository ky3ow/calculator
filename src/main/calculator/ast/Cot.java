package ast;

public class Cot extends UnaryOp {

    public Cot(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("cot {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return 1 / Math.tan(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("cot(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Negate(
            new Division(
                    op.getDerivative(),
                    new Pow(
                            new Sin(op),
                            new Const("2")
                    )
            )
        );
    }

}

