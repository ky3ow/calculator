package ast;

public class Const implements AST {

    String value;

    public Const(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Number: %s", value);
    }

    @Override
    public double getNumericResult(double value) {
        return Double.parseDouble(this.value);
    }

    @Override
    public String getStringRepresentation() {
        return value;
    }

    @Override
    public AST getDerivative() {
        return new Const("0");
    }
}
