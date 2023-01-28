package com.uno.client.Computers;

import com.uno.client.model.Card;
import com.uno.client.model.Player;

public class BasicComputer extends Player implements AI{
    public BasicComputer(String name) {
        super(name);
    }

    @Override
    public void determineMove(){
        for(Card card : this.getHand().getCards()){
            if(ComputerUtils.playCard(card)){
                return;
            }
        }

        ComputerUtils.drawCard();
    }

    @Override
    public void determineDrawPlay(Card card){
        ComputerUtils.playDrawnCard(card);
    }
    // Will be implemented after creating some game logic as it is not completely clear how the computer will play
}
