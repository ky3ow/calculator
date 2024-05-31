package ast;

public class Atan extends UnaryOp {

    public Atan(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("atan {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.atan(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("atan(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Division(
                op.getDerivative(),
                new Addition(
                        new Const(1),
                        new Pow(
                                op,
                                new Const(2)
                        )
                )
        );
    }

}

