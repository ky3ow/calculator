package ast;

public class Log extends UnaryOp {

    public Log(AST op) {
        super(op);
    }

    @Override
    public String toString() {
        return String.format("log {\n %s\n}", op.toString());
    }

    @Override
    public double getNumericResult(double value) {
        return Math.log(op.getNumericResult(value));
    }

    @Override
    public String getStringRepresentation() {
        return String.format("log(%s)", op.getStringRepresentation());
    }

    @Override
    public AST getDerivative() {
        return new Division(
            op.getDerivative(),
            op
        );
    }

}

