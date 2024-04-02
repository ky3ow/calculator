package ast;

public interface AST {
    public double getNumericResult(double value);

    public String getStringRepresentation();

    public AST getDerivative();

}
