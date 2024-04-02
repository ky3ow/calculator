package ast;

public class Sqrt extends UnaryOp {

    public Sqrt(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("sqrt {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.sqrt(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("√(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Division(
                op.getDerivative(),
                new Multiplication(
                        new Const("2"),
                        new Sqrt(op)
                )
        );
    }

}

