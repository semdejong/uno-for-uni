package com.uno.server.uno;

public class Card {
    // Defining a new type of variable called cardType. It is a public enum, which means that it can be used by other
    // classes. It is a list of possible values that the variable can take.
    public enum cardType {
        NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR
    }

    // Defining a new type of variable called cardColor. It is a public enum, which means that it can be used by other
    // classes. It is a list of possible values that the variable can take.
    public enum cardColor {
        RED, YELLOW, GREEN, BLUE, BLACK
    }

    // This is a constructor. It is a method that is called when a new object of the class is created. It is used to
    // initialize the object.
    public Card(cardType type, cardColor color, int number) {
        this.type = type;
        this.color = color;
        this.number = number;
    }

    private cardType type;
    private cardColor color;
    private int number;


    /**
     * This function returns the type of the card.
     *
     * @return The type of the card.
     */
    public cardType getType() {
        return type;
    }

    /**
     * This function returns the color of the card.
     *
     * @return The color of the card.
     */
    public cardColor getColor() {
        return color;
    }

    /**
     * This function returns the value of the number variable.
     *
     * @return The number of the card.
     */
    public int getNumber() {
        return number;
    }


    /**
     * This function sets the type of the card to the type passed in
     *
     * @param type The type of card.
     */
    public void setType(cardType type) {
        this.type = type;
    }

    /**
     * This function sets the color of the card to the color passed in.
     *
     * @param color The color of the card.
     */
    public void setColor(cardColor color) {
        this.color = color;
    }

    /**
     * This function sets the number variable to the number passed in.
     *
     * @param number The number of the item in the list.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Checks if the card objects values are equal. Overwrites default behaviour.
     *
     * @param compareTo The object to compare to.
     * @return The return value is a boolean.
     */
    @Override
    public boolean equals(Object compareTo){
        if(this == compareTo) return true; //both addresses are the same in the memory.

        if(!(compareTo instanceof Card)) return false; // if compareTo is not a card, it can not be the same.

        Card card = (Card) compareTo; //Safe cast to card, because of instanceof check above

        return ((this.getType().equals(card.getType()) && this.getColor().equals(card.getColor()) && this.getNumber() == card.getNumber()) || (card.getType() == cardType.WILD && this.getType() == cardType.WILD)|| (card.getType() == cardType.WILD_DRAW_FOUR && this.getType() == cardType.WILD_DRAW_FOUR));
    }

    /**
     * The function takes in a card object and returns a string that contains the color and number of the card
     *
     * @return The color and the number of the card.
     */
    @Override
    public String toString() {
        if(this.number < 0){
            return this.color + "$,$" + this.type;
        }
        return this.color + "$,$" + this.number;
    }

}
