package interpreter;

public class Token {

    private String type;
    private String value;

    Token (String type, String value) {
        this.type = type;
        this.value = value;
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