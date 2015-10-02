package com.company;

public class Token {
    private final Integer sign;

    public Token(Integer sign) {
        this.sign = sign;
    }

    public Token(Token token) {
        this.sign = token.sign;
    }

    public boolean isEmpty() {
        return this.sign == 0;
    }

    public Integer getSign() {
        return this.sign;
    }

    @Override
    public String toString() {
        return sign.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Token && ((Token)o).sign.equals(this.sign);
    }

    @Override
    public int hashCode() {
        return sign;
    }
}
