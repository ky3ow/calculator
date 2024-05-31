package ast;

public class Asin extends UnaryOp {

    public Asin(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("asin {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.asin(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("asin(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Division(
                op.getDerivative(),
                new Sqrt(
                        new Substraction(
                                new Const(1),
                                new Pow(
                                        op,
                                        new Const(2)
                                )
                        )
                )
        );
    }

}

