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

        } else if (this.currentToken.getType() == tokenType) {

            this.currentToken = input.get(cursor);
            cursor++;

        } else {
            throw new Error("Unexpected token");
        }

    }

    public AST operand() {
        // operand:  (UNARY_MINUS) operand | VARIABLE | NUMBER |  L_PAREN expr R_PAREN  |  FUNC L_PAREN expr R_PAREN
        Token token = this.currentToken;

        if (token.getType().equals("U_MINUS")) {
            eat("U_MINUS");
            return new Negate(operand());
        } else if (token.getType().equals("NUMBER")) {
            eat("NUMBER");
            return new Const(token.getValue());
        } else if (token.getType().equals("L_PAREN")) {
            eat("L_PAREN");
            AST node = expr();
            eat("R_PAREN");
            return node;
        } else if (token.getType().equals("VARIABLE")) {
            eat("VARIABLE");
            return new Variable();
        } else if(token.getType().equals("FUNC")) {
            eat("FUNC");
            eat("L_PAREN");
            AST node = null;
            switch (token.getValue()) {
                case "sin":
                    node = new Sin(expr());
                    break;
                case "asin":
                    node = new Asin(expr());
                    break;
                case "cos":
                    node = new Cos(expr());
                    break;
                case "acos":
                    node = new Acos(expr());
                    break;
                case "tan":
                    node = new Tan(expr());
                    break;
                case "atan":
                    node = new Atan(expr());
                    break;
                case "cot":
                    node = new Cot(expr());
                    break;
                case "acot":
                    node = new Acot(expr());
                    break;
                case "√":
                    node = new Sqrt(expr());
                    break;
                case "exp":
                    node = new Exp(expr());
                    break;
                case "log":
                    node = new Log(expr());
                    break;
            }
            eat("R_PAREN");
            return node;
        } else {
            return null;
        }
    }

    public AST factor() {

        AST node = operand();

        while (this.currentToken.getType().equals("RAISE")) {
            eat("RAISE");
            node = new Pow(node, factor());

        }

        return node;
    }

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


    public Parser() {
        this.cursor = 0;
        this.input = null;
        this.currentToken = null;
    }
}
