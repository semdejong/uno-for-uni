package com.uno.client.model;

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

    @Override
    public  String toString(){
        String handString = "";
        for (int i=0; i<getCards().size(); i++){
            handString += (i+1) + ": " + hand.get(i).toStringPerson();
            if(i != hand.size() -1){
                handString+= "\n";
            }
        }
        return handString;
    }
}
