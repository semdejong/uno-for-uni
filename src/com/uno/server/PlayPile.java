package com.uno.server;

public class PlayPile {
    private Card activeCard;

    public PlayPile(Card activeCard){
        this.activeCard = activeCard;
    }

    public Card getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(Card activeCard) {
        this.activeCard = activeCard;
    }
}
