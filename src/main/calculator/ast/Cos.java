package ast;

public class Cos extends UnaryOp {

    public Cos(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("cos {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.cos(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("cos(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Negate(
                new Multiplication(
                        new Sin(op),
                        op.getDerivative()
                )
        );
    }

}

