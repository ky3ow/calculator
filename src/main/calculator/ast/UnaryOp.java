package ast;

public abstract class UnaryOp implements AST {
    AST op;

    UnaryOp(AST op) {
        this.op = op;
    }

    @Override
    public double getNumericResult() {
        return getNumericResult(0);
    }

    @Override
    public AST simplify() {
        op = op.simplify();
        return this;
    }
}
