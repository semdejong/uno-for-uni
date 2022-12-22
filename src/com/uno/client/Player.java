package com.uno.client;

public class Player {

    private String name;
    private Hand hand;

    public Player(String name){
        this.name = name;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void addCard(Card card){
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