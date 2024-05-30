package interpreter;

import java.util.ArrayList;

import ast.*;

public class Parser {

    private int cursor;
    private ArrayList<Token> input;
    private Token currentToken;

    public void setInput(ArrayList<Token> input) {
        this.input = input;
        this.cursor = 0;
        this.currentToken = input.get(cursor++);
    }

    public void eat(String tokenType) {

        if (cursor >= input.size()) {

            this.currentToken = new Token("EOF", null);

        } else if (this.currentToken.getType().equals(tokenType)) {

            this.currentToken = input.get(cursor);
            cursor++;

        } else {
            throw new Error("Unexpected token");
        }

    }

    // operand:  (MINUS) operand | VARIABLE | NUMBER |  L_PAREN expr R_PAREN  |  FUNC L_PAREN expr R_PAREN
    public AST operand() {
        Token token = this.currentToken;

        switch (token.getType()) {
            case "MINUS" -> {
                eat("MINUS");
                return new Negate(operand());
            }
            case "NUMBER" -> {
                eat("NUMBER");
                return new Const(token.getValue());
            }
            case "L_PAREN" -> {
                eat("L_PAREN");
                AST node = expr();
                eat("R_PAREN");
                return node;
            }
            case "VARIABLE" -> {
                eat("VARIABLE");
                return new Variable();
            }
            case "FUNC" -> {
                eat("FUNC");
                eat("L_PAREN");
                AST node = switch (token.getValue()) {
                    case "sin" -> new Sin(expr());
                    case "asin" -> new Asin(expr());
                    case "cos" -> new Cos(expr());
                    case "acos" -> new Acos(expr());
                    case "tan" -> new Tan(expr());
                    case "atan" -> new Atan(expr());
                    case "cot" -> new Cot(expr());
                    case "acot" -> new Acot(expr());
                    case "√" -> new Sqrt(expr());
                    case "exp" -> new Exp(expr());
                    case "log" -> new Log(expr());
                    default -> null;
                };
                eat("R_PAREN");
                return node;
            }
            default -> {
                return null;
            }
        }
    }

    //  factor: operand ( ^ factor )*
    public AST factor() {
        AST node = operand();

        while (this.currentToken.getType().equals("RAISE")) {
            eat("RAISE");
            node = new Pow(node, factor());

        }

        return node;
    }

    // term: factor ( MUL|DIV factor )*
    public AST term() {

        AST node = factor();

        while (this.currentToken.getType().equals("DIV") || this.currentToken.getType().equals("MULT")) {

            Token token = this.currentToken;

            if(token.getType().equals("MULT")) {
                eat("MULT");
                node = new Multiplication(node, factor());
            } else {
                eat("DIV");
                node = new Division(node, factor());
            }

        }
        return node;

    }

    // expr: term ( PLUS|MINUS term )*
    public AST expr() {

        AST node = term();

        while (this.currentToken.getType().equals("PLUS") || this.currentToken.getType().equals("MINUS")) {

            Token token = this.currentToken;

            if(token.getType().equals("PLUS")) {
                eat("PLUS");
                node = new Addition(node, term());
            } else {
                eat("MINUS");
                node = new Substraction(node, term());
            }

        }

        return node;

    }


    /*
     **    expr: term ( PLUS|MINUS term )*
     **
     **    term: factor ( MUL|DIV factor )*
     **
     **    factor: operand ( ^ factor )*
     **
     **    operand:  (UNARY_MINUS) operand | VARIABLE | NUMBER |  L_PAREN expr R_PAREN  |  FUNC L_PAREN expr R_PAREN
     **
     */
    public Parser() {
        this.cursor = 0;
        this.input = null;
        this.currentToken = null;
    }
}
