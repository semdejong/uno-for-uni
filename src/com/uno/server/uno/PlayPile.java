package com.uno.server.uno;

import java.util.ArrayList;

public class PlayPile {
    private Card activeCard;
    private ArrayList<Card> discardPile = new ArrayList<>();
    // This is a constructor. It is called when you create a new PlayPile object.
    public PlayPile(Card activeCard){
        this.activeCard = activeCard;
    }

    /**
     * This function returns the active card.
     *
     * @return The activeCard
     */
    public Card getActiveCard() {
        return activeCard;
    }

    /**
     * This function returns the discard pile
     *
     * @return The discard pile.
     */
    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }

    /**
     * This function sets the discard pile to the given discard pile
     *
     * @param discardPile The discard pile is where the cards that are discarded by the players go.
     */
    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

    /**
     * This function clears the discard pile by creating a new ArrayList
     */
    public void clearDiscardPile(){
        discardPile = new ArrayList<>();
    }

    /**
     * This function adds a card to the discard pile
     *
     * @param card The card to be added to the discard pile.
     */
    public void addCardToDiscardPile(Card card){
        discardPile.add(card);
    }


    /**
     * If the card is playable, discard the active card and set the active card to the new card
     *
     * @param card The card that the player wants to play
     * @param hand the hand of the player who is playing the card
     * @return A boolean value.
     */
    public boolean playCard(Card card, Hand hand){
        if (playable(card, hand)){
            discardPile.add(activeCard);
            activeCard = card;
            return true;
        }else {
            System.out.println("Card is not playable");
            return false;
        }
    }

    /**
     * If the card is a wild draw four, then check if the hand has any cards of the same color as the active card. If it
     * does, then the card is not playable. Otherwise, the card is playable
     *
     * otherwise check if type or color are the same.
     *
     * @param card The card that is being played
     * @param hand The hand of the player who is playing the card.
     * @return A boolean value.
     */
    public boolean playable(Card card, Hand hand){
        if (hand == null){
            return true;
        } else if (card.getType() == Card.cardType.WILD_DRAW_FOUR) {
            for (Card card1 : hand.getCards()){
                if (card1.getColor() == activeCard.getColor() && card.getType() != Card.cardType.WILD_DRAW_FOUR){
                    return false;
                }
            }
            return true;
        } else {
            return card.getColor().equals(activeCard.getColor()) || card.getNumber() == activeCard.getNumber() || card.getType().equals(Card.cardType.WILD);
        }
    }
}
