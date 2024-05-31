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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnaryOp unaryOp = (UnaryOp) o;

        return op.equals(unaryOp.op);
    }

    @Override
    public AST simplify() {
        op = op.simplify();
        return this;
    }
}
