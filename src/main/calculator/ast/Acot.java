package ast;

public class Acot extends UnaryOp {

    public Acot(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("acot {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return 1 / Math.atan(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("acot(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Negate(
                new Division(
                    op.getDerivative(),
                    new Addition(
                            new Const("1"),
                            new Pow(
                                    op,
                                    new Const("2")
                            )
                    )
            )
        );
    }

}

