package com.uno.client.Computers;

import com.uno.client.model.Card;
import com.uno.client.model.Player;

public class MediumComputer extends Player implements AI{

    public MediumComputer(String name){
        super(name);
    }

    @Override
    public void determineMove(){
        for(Card card : this.getHand().getPlayableCardsSortedOnValue()){
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
}
