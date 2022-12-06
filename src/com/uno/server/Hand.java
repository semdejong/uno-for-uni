package com.uno.server;

import com.uno.client.Card;

import java.util.ArrayList;

public class Hand {

    private static ArrayList<com.uno.client.Card> hand;

    public Hand(){
        this.hand = new ArrayList<com.uno.client.Card>();
    }

    public static void addCard(com.uno.client.Card card){
        hand.add(card);
    }

    public static void removeCard(com.uno.client.Card card){
        hand.remove(card);

    }

    public static ArrayList<com.uno.client.Card> getHand() {
        return hand;
    }

    public static void setHand(ArrayList<Card> hand) {
        Hand.hand = hand;
    }

    public static int getHandSize() {
        return hand.size();
    }


}
