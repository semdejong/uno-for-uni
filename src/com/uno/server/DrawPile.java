package com.uno.server;

import java.util.ArrayList;
import java.util.Collections;

public class DrawPile {
    ArrayList<Card> deck;

    public DrawPile(){
        deck = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // Add a red card for each number 0-9
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.RED,i));

            // Add a yellow card for each number 0-9
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.YELLOW,i));

            // Add a green card for each number 0-9
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.GREEN,i));

            // Add a blue card for each number 0-9
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.BLUE,i));
        }

        // Add the special cards to the deck
        for(Card.cardColor color : Card.cardColor.values()) {
            deck.add(new Card(Card.cardType.SKIP, color, -1));
            deck.add(new Card(Card.cardType.REVERSE, color, -1));
            deck.add(new Card(Card.cardType.DRAW_TWO, color, -1));
        }

        // Add the wild cards to the deck
        deck.add(new Card(Card.cardType.WILD, Card.cardColor.BLACK, -1));
        deck.add(new Card(Card.cardType.WILD_DRAW_FOUR, Card.cardColor.BLACK, -1));
    }

    public Card drawCard(){
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public void dealCards(ArrayList<Player> players){
        shuffle();
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.addCard(drawCard());
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }


}
