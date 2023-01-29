package com.uno.server.uno;

import java.util.ArrayList;
import java.util.Collections;

public class DrawPile {
    ArrayList<Card> deck;

    public DrawPile(){
        deck = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // Add all cards for each number 0-9 where 1-9 have 2 of each and 0 has 1 of each
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.RED, i));
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.YELLOW, i));
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.GREEN,i));
            deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.BLUE,i));

            if(i != 0) {
                deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.RED, i));
                deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.YELLOW, i));
                deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.GREEN,i));
                deck.add(new Card(Card.cardType.NUMBER, Card.cardColor.BLUE,i));
            }
        }

        // Add the special cards to the deck
        for(Card.cardColor color : Card.cardColor.values()) {
            if(color == Card.cardColor.BLACK) continue;
            for(int i = 0; i < 2; i++) {
                deck.add(new Card(Card.cardType.SKIP, color, -1));
                deck.add(new Card(Card.cardType.REVERSE, color, -2));
                deck.add(new Card(Card.cardType.DRAW_TWO, color, -3));
            }
        }

        // Add the wild cards to the deck
        for(int i = 0; i < 4; i++) {
            deck.add(new Card(Card.cardType.WILD, Card.cardColor.BLACK, -4));
            deck.add(new Card(Card.cardType.WILD_DRAW_FOUR, Card.cardColor.BLACK, -5));
        }
    }

    public Card drawCard(){
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public void dealCards(ArrayList<Player> players){
        shuffle();
        for (Player player : players) {
            player.getHand().clearHand();
            for (int i = 0; i < 7; i++) {
                player.addCard(drawCard());
            }
        }
    }

    public ArrayList<Card> getDeck(){
        return deck;
    }

    public void setDeck(ArrayList<Card> deck){
        this.deck = deck;
        for (Card card : deck){
            if (card.getType() == Card.cardType.WILD || card.getType() == Card.cardType.WILD_DRAW_FOUR){
                card.setColor(Card.cardColor.BLACK);
            }
        }
        shuffle();
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public void addCard(Card card){
        deck.add(card);
    }

}
