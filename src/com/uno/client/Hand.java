package com.uno.client;

import java.util.ArrayList;

public class Hand {

    private static ArrayList<Card> hand;

    public Hand(){
        this.hand = new ArrayList<Card>();
    }

    public static void addCard(Card card){
        if(hand.size() < 7){
            hand.add(card);
        }
    }

    public static void removeCard(Card card){
        hand.remove(card);

    }

    public static ArrayList<Card> getHand() {
        return hand;
    }

    public static void setHand(ArrayList<Card> hand) {
        Hand.hand = hand;
    }

    public static int getHandSize() {
        return hand.size();
    }


}
