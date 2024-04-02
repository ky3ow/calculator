package ast;

public abstract class BinOp implements AST {

    AST left;
    AST right;

    protected static String symbol;

    public BinOp(AST left, AST right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public String toString() {
        return String.format("BinOp {\n  left {\n  %s\n}\n  right {\n  %s\n}\n}",left, right);
    }

    @Override
    public String getStringRepresentation() {
        StringBuilder b = new StringBuilder();
        b.append('(');
        left.getStringRepresentation();
        b.append(')');
        b.append(symbol);
        b.append('(');
        right.getStringRepresentation();
        b.append(')');
        return b.toString();
    }
}

