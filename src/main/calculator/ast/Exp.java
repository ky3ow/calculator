package ast;

public class Exp extends UnaryOp {

    public Exp(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("exp {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.exp(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("exp(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Multiplication(
            new Exp(op),
            op.getDerivative()
        );
    }

}

