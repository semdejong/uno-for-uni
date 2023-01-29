package com.uno.client.model;

public class Player {

    private String name;
    private Hand hand;
    private int score;

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

    //Removes a closed card from a player.
    public void removeClosedCard(){
        for(Card card : hand.getCards()){
            if(card instanceof ClosedCard){
                hand.removeCard(card);
                return;
            }
        }
    }

    public int getHandSize(){
        return hand.getHandSize();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toString(){
        return "Player: " + this.name + " " + this.hand;
    }

}
