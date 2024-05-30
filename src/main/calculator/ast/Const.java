package ast;

public class Const implements AST {

    String value;

    public Const(String value) {
        this.value = value;
    }

    public boolean isZero() {
       return value.equals("0");
    }

    public boolean isOne() {
        return value.equals("1");
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

    @Override
    public AST simplify() {
        return this;
    }
}
