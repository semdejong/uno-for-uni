package com.uno.server.uno;

public class Card {
    public enum cardType {
        NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR
    };

    public enum cardColor {
        RED, YELLOW, GREEN, BLUE, BLACK
    };

    public Card(cardType type, cardColor color, int number) {
        this.type = type;
        this.color = color;
        this.number = number;
    }

    private cardType type;
    private cardColor color;
    private int number;


    public cardType getType() {
        return type;
    }

    public cardColor getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public void setType(cardType type) {
        this.type = type;
    }

    public void setColor(cardColor color) {
        this.color = color;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString() {
        if(this.number < 0){
            return this.color + "$,$" + this.type;
        }
        return this.color + "$,$" + this.number;
    }

}
