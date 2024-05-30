package ast;

public class Variable implements AST {

    @Override
    public String toString() {
        return "Variable: x";
    }

    @Override
    public double getNumericResult(double value) {
        return value;
    }

    @Override
    public String getStringRepresentation() {
        return "x";
    }

    @Override
    public AST getDerivative() {
        return new Const("1");
    }

    @Override
    public AST simplify() {
        return this;
    }
}


