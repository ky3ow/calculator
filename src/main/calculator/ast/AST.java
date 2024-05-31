package ast;

public interface AST {
    public double getNumericResult(double value);
    public double getNumericResult();

    public String getStringRepresentation();

    public AST getDerivative();

    public AST simplify();

}
