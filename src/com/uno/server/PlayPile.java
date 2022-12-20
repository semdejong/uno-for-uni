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
    public void playCard(Card card){
        if (playable(card)){
            if (card.getType() == Card.cardType.WILD){
                card.setColor(askColorWild());
            }
            if (card.getType() == Card.cardType.WILD_DRAW_FOUR){
                card.setColor(askColorWild());
                forcedDraw(4);
            }
            if (card.getType() == Card.cardType.DRAW_TWO){
                forcedDraw(2);
            }
            if (card.getType() == Card.cardType.SKIP){
                skipTurn();
            }
            if (card.getType() == Card.cardType.REVERSE){
                reverse();
            }
            discardPile.add(activeCard);
            activeCard = card;
        }else {
            System.out.println("Card is not playable");
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
    public boolean playable(Card card){
        return card.getColor().equals(activeCard.getColor()) || card.getType().equals(activeCard.getType()) || card.getType().equals(Card.cardType.WILD) || card.getType().equals(Card.cardType.WILD_DRAW_FOUR);
    }

}
