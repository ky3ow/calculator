package ast;

public abstract class UnaryOp implements AST {
    AST op;

    UnaryOp(AST op) {
        this.op = op;
    }
}
