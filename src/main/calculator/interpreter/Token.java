package interpreter;

import java.util.Objects;

public class Token {

    private final String type;
    private final String value;

    Token (String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (!type.equals(token.type)) return false;
        return value.equals(token.value);
    }

    @Override
    public String toString() {
        return "Token: " + this.type + ", Value: " + this.value + "\n";
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }
}
