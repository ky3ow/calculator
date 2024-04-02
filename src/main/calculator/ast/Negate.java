package ast;

public class Negate extends UnaryOp {

    public Negate(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("negate {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return -op.getNumericResult(value);
    }

    @Override
    public String getStringRepresentation() {
        return String.format("-(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Negate(op.getDerivative());
    }

}
