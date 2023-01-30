package com.uno.server.uno;

import java.util.ArrayList;
import java.util.Collections;

public class DrawPile {
    ArrayList<Card> deck;

    // Creating a new deck of cards.
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

    /**
     * This function removes the first card from the deck and returns it.
     *
     * @return A card object is being returned.
     */
    public Card drawCard(){
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    /**
     * This function shuffles the deck and deals 7 cards to each player.
     *
     * @param players An ArrayList of Player objects.
     */
    public void dealCards(ArrayList<Player> players){
        shuffle();
        for (Player player : players) {
            player.getHand().clearHand();
            for (int i = 0; i < 7; i++) {
                player.addCard(drawCard());
            }
        }
    }

    /**
     * This function returns the deck of cards
     *
     * @return The deck of cards.
     */
    public ArrayList<Card> getDeck(){
        return deck;
    }

    /**
     * This function sets the deck to the given deck, and sets the color of all wild cards to black
     *
     * @param deck The deck of cards that the player will be using.
     */
    public void setDeck(ArrayList<Card> deck){
        this.deck = deck;
        for (Card card : deck){
            if (card.getType() == Card.cardType.WILD || card.getType() == Card.cardType.WILD_DRAW_FOUR){
                card.setColor(Card.cardColor.BLACK);
            }
        }
        shuffle();
    }

    /**
     * The function shuffle() takes the deck of cards and shuffles them
     */
    public void shuffle(){
        Collections.shuffle(deck);
    }

    /**
     * This function adds a card to the deck.
     *
     * @param card The card to be added to the deck.
     */
    public void addCard(Card card){
        deck.add(card);
    }

}
