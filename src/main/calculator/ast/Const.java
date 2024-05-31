package ast;

public class Const implements AST {

    String value;

    public Const(String value) {
        this.value = value;
    }

    public Const(double value) {
        this.value = String.valueOf(value);
    }

    public boolean isZero() {
       return Double.parseDouble(value) == 0;
    }

    public boolean isOne() {
        return Double.parseDouble(value) == 1;
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
    public double getNumericResult() {
        return getNumericResult(0);
    }

    @Override
    public String getStringRepresentation() {
        return value;
    }

    @Override
    public AST getDerivative() {
        return new Const(0);
    }

    @Override
    public AST simplify() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Const aConst = (Const) o;

        return value.equals(aConst.value);
    }
}
