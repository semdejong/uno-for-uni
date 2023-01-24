package com.uno.server.uno;

import com.uno.server.uno.Card;
import com.uno.server.uno.Hand;
import com.uno.utils.TextIO;

import java.util.ArrayList;

public class PlayPile {
    private Card activeCard;
    private ArrayList<Card> discardPile = new ArrayList<>();
    public PlayPile(Card activeCard){
        this.activeCard = activeCard;
    }

    public Card getActiveCard() {
        return activeCard;
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

    public void clearDiscardPile(){
        discardPile.clear();
    }


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
    public boolean playable(Card card, Hand hand){
        if (hand == null){
            return true;
        } else if (card.getType() == Card.cardType.WILD_DRAW_FOUR) {
            for (Card card1 : hand.getCards()){
                if (card1.getColor() == activeCard.getColor()){
                    return false;
                }
            }
            return true;
        } else {
            return card.getColor().equals(activeCard.getColor()) || card.getNumber() == activeCard.getNumber() || card.getType().equals(Card.cardType.WILD);
        }
    }
}
