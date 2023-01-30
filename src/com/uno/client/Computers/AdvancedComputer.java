package com.uno.client.Computers;

import com.uno.client.controller.PlayerController;
import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Player;

import java.util.ArrayList;

public class AdvancedComputer extends Player implements AI {

    public AdvancedComputer(String name){
        super(name);
    }

    @Override
    // The method that determines the move of the advanced computer.
    public void determineMove(){
        boolean hasLeastCards = true;

        for(Player player : Game.getPlayers()){
            //When player has less cards then all other players keep wilds, and max 2 +2. Also keep them at start.
            if(PlayerController.getOwnPlayer().getHand().getCards().size() > player.getHand().getCards().size() + 1){
                hasLeastCards = false;
            }
        }

        for(Card card : this.getHand().getPlayableCardsSortedOnValue()){
            if(hasLeastCards){
                //if player has only two cards
                if(computerHasWildOrWild4() && PlayerController.getOwnPlayer().getHand().getCards().size() <= 2){
                    if(ComputerUtils.playCard(card)){
                        return;
                    }
                }

                if(card.getType() == Card.cardType.WILD || card.getType() == Card.cardType.WILD_DRAW_FOUR) {
                    continue;
                }
            }

            //if other
            if(hasWildAndPlus2(this.getHand().getPlayableCardsSortedOnValue())){
                if(card.getType() == Card.cardType.DRAW_TWO){
                    if(ComputerUtils.playCard(card)){
                        return;
                    }
                }
            }else if(hasWildAndSkip(this.getHand().getPlayableCardsSortedOnValue())){
                if(card.getType() == Card.cardType.SKIP){
                    if(ComputerUtils.playCard(card)){
                        return;
                    }
                }
            }else {
                if (ComputerUtils.playCard(card)) {
                    return;
                }
            }
        }


        ComputerUtils.drawCard();
    }

    @Override
    public void determineDrawPlay(Card card){
        ComputerUtils.playDrawnCard(card);
    }

    public boolean computerHasWildOrWild4(){

        boolean hasWild = false;

        for(Card card : PlayerController.getOwnPlayer().getHand().getCards()){
            if(card.getType() == Card.cardType.WILD || card.getType() == Card.cardType.WILD_DRAW_FOUR){
                hasWild = true;
            }
        }

        return hasWild;
    }

//    public Card.cardColor findLastColor(){
//        for(Card card : PlayerController.getOwnPlayer().getHand().getCards()){
//            if(!(card.getType().equals(Card.cardType.WILD) || card.getType().equals(Card.cardType.WILD_DRAW_FOUR))){
//                return card.getColor();
//            }
//        }
//
//        return Card.cardColor.RED;
//
//    }

    public boolean hasWildAndPlus2(ArrayList<Card> cards){
        boolean hasWild = false;
        boolean hasPlus2 = false;

        for (Card card: cards) {
            if(card.getType() == Card.cardType.WILD){
                hasWild = true;
            }

            if(card.getType() == Card.cardType.DRAW_TWO){
                hasPlus2 = true;
            }
        }

        return (hasWild && hasPlus2);
    }

    public boolean hasWildAndSkip(ArrayList<Card> cards){
        boolean hasWild = false;
        boolean hasSkip = false;

        for (Card card: cards) {
            if(card.getType() == Card.cardType.WILD){
                hasWild = true;
            }

            if(card.getType() == Card.cardType.SKIP){
                hasSkip = true;
            }
        }

        return (hasWild && hasSkip);
    }

//    public boolean hasCardOfDifferentColorButSameNumber(ArrayList<Card> cards) {
//        boolean sameNumber = false;
//
//        if (Game.getActiveCard().getNumber() < 30) {
//            for (Card card : cards) {
//                if (Game.getActiveCard().getNumber() == card.getNumber()) {
//                    return true;
//                }
//            }
//        }
//
//
//        return false;
//    }
//    }
}
