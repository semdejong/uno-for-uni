package com.uno.client.model;

import com.uno.client.controller.CommandHandler;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {

    private ArrayList<Card> hand;

    public Hand(){
        this.hand = new ArrayList<Card>();
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void removeCard(Card card){
        hand.remove(card);

    }

    public ArrayList<Card> getCards() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getHandSize() {
        return hand.size();
    }

    public ArrayList<Card> getPlayableCardsSortedOnValue(){
        ArrayList<Card> cardsToReturn = new ArrayList<>();

        for(Card card : hand){
            if(CommandHandler.playable(card)){
                cardsToReturn.add(card);
            }
        }

        Collections.sort(cardsToReturn);

        return cardsToReturn;
    }

    @Override
    public  String toString(){
        String handString = "";
        for (int i=0; i<getCards().size(); i++){
            handString += (i+1) + ": " + hand.get(i).toStringPerson();
            if(i != hand.size() -1){
                handString+= "\n";
            }
        }
        return handString;
    }
}
