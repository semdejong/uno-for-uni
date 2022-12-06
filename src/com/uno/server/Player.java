package com.uno.server;

import com.uno.client.Card;
import com.uno.client.Hand;

public class Player {

    private String name;
    private com.uno.client.Hand hand;

    public Player(String name){
        this.name = name;
        this.hand = new com.uno.client.Hand();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.uno.client.Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void addCard(com.uno.client.Card card){
        hand.addCard(card);
    }

    public void removeCard(Card card){
        hand.removeCard(card);
    }

    public int getHandSize(){
        return hand.getHandSize();
    }

    public String toString(){
        return "Player: " + this.name + " " + this.hand;
    }

}
