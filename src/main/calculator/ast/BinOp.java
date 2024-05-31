package ast;

public abstract class BinOp implements AST {

    AST left;
    AST right;

    public BinOp(AST left, AST right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double getNumericResult() {
        return getNumericResult(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinOp binOp = (BinOp) o;

        if (!left.equals(binOp.left)) return false;
        return right.equals(binOp.right);
    }

    @Override
    public String toString() {
        return String.format("BinOp {\n  left {\n  %s\n}\n  right {\n  %s\n}\n}",left, right);
    }

    @Override
    public AST simplify() {
        left = left.simplify();
        right = right.simplify();
        return this;
    }
}

