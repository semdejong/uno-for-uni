package com.uno.client.model;

public class Card implements Comparable<Card>{
    public enum cardType {
        NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR, UNKNOWN
    };

    public enum cardColor {
        RED, YELLOW, GREEN, BLUE, BLACK, UNKNOWN
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

    @Override
    public boolean equals(Object compareTo){
        if(this == compareTo) return true; //both adresses are the same in the memory.

        if(!(compareTo instanceof com.uno.server.uno.Card)) return false; // if compareTo is not a card, it can not be the same.

        Card card = (Card) compareTo; //Safe cast to card, because of instanceof check above

        return ((this.getType().equals(card.getType()) && this.getColor().equals(card.getColor()) && this.getNumber() == card.getNumber()) || card.getType() == cardType.WILD && this.getType() == cardType.WILD|| card.getType() == cardType.WILD_DRAW_FOUR && this.getType() == cardType.WILD_DRAW_FOUR);
    }

    @Override
    public String toString() {
        if(this.number > 9){
            return this.color + "$,$" + this.type;
        }
        return this.color + "$,$" + this.number;
    }

    @Override
    public int compareTo(Card otherCard){
        Integer thisCardValue = (Integer) this.getNumber();
        Integer otherCardValue = (Integer) otherCard.getNumber();

        return otherCardValue.compareTo(thisCardValue);
    }

    public String toStringPerson(){
        if(this.number > 9){
            return this.color + " " + this.type;
        }
        return this.color + " " + this.number;
    }

}
