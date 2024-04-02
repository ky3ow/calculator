package ast;

public class Sin extends UnaryOp {

    public Sin(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("sin {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.sin(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("sin(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Multiplication(
                new Cos(op),
                op.getDerivative()
        );
    }

}

