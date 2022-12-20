package com.uno.server;


import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    public Hand(){
        this.hand = new ArrayList<Card>();
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void removeCard(Card card){
        hand.remove(card);

    }

    public ArrayList<Card> getCards() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getHandSize() {
        return hand.size();
    }

    public String toString(){
        String handString = "";

        for(Card card : hand){
            handString += card.toString() + " ";
        }

        return "Hand: " + handString;
    }


}
