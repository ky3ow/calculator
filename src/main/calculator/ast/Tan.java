package ast;

public class Tan extends UnaryOp {

    public Tan(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("tan {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.tan(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("tan(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Division(
            op.getDerivative(),
            new Pow(
                    new Cos(op),
                    new Const("2")
            )
        );
    }

}

