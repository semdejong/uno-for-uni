package com.uno.client.model;

import java.util.ArrayList;

public class Hand {

    private static ArrayList<Card> hand;

    public Hand(){
        this.hand = new ArrayList<Card>();
    }

    public static void addCard(Card card){
        hand.add(card);
    }

    public static void removeCard(Card card){
        hand.remove(card);

    }

    public ArrayList<Card> getCards() {
        return hand;
    }

    public static void setHand(ArrayList<Card> hand) {
        Hand.hand = hand;
    }

    public static int getHandSize() {
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
