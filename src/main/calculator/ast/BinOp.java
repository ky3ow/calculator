package ast;

public abstract class BinOp implements AST {

    AST left;
    AST right;

    public BinOp(AST left, AST right) {
        this.left = left;
        this.right = right;
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

