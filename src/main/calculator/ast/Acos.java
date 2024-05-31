package ast;

public class Acos extends UnaryOp {

    public Acos(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("acos {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.acos(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("acos(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Negate(
                new Division(
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
                )
        );
    }

}

