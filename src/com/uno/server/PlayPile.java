package com.uno.server;

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
            if (card.getType() == Card.cardType.WILD){
                card.setColor(askColorWild());
            }
            if (card.getType() == Card.cardType.WILD_DRAW_FOUR){
                card.setColor(askColorWild());
//                forcedDraw(4);
            }
            if (card.getType() == Card.cardType.DRAW_TWO){
//                forcedDraw(2);
            }
            if (card.getType() == Card.cardType.SKIP){
            }
            if (card.getType() == Card.cardType.REVERSE){
//                reverse();
            }
            discardPile.add(activeCard);
            activeCard = card;
            return true;
        }else {
            System.out.println("Card is not playable");
            return false;
        }
    }
    public Card.cardColor askColorWild(){
        System.out.println("What color do you want to change to?");
        System.out.println("1. Red");
        System.out.println("2. Yellow");
        System.out.println("3. Green");
        System.out.println("4. Blue");
        int choice = TextIO.getlnInt();
        switch (choice){
            case 1:
                return Card.cardColor.RED;
            case 2:
                return Card.cardColor.YELLOW;
            case 3:
                return Card.cardColor.GREEN;
            case 4:
                return Card.cardColor.BLUE;
            default:
                return Card.cardColor.RED;
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
            return card.getColor().equals(activeCard.getColor()) || card.getNumber() == activeCard.getNumber() || card.getType().equals(Card.cardType.WILD) || card.getType().equals(Card.cardType.WILD_DRAW_FOUR);
        }
    }
}
