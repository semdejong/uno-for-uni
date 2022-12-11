package com.uno.server;

import java.util.ArrayList;

public class DrawPile {
    ArrayList<Card> deck = new ArrayList<>();

    public DrawPile(){}

    public Card drawCard(){
        return deck.remove(0);
    }
    public void shuffle(){}


}
