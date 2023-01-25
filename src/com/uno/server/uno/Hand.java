package com.uno.server.uno;


import com.uno.server.ClientHandler;
import com.uno.server.uno.Card;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    public Hand(){
        this.hand = new ArrayList<Card>();
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void addCardSyncWithClient(Card card, ClientHandler client){
        addCard(card);
        client.sendMessage("GiveCard|"+card.toString());
        client.getJoinedLobby().broadCastLobby("CardDrawn|"+client.getClientName());
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

    public void clearHand(){
        hand.clear();
    }

    @Override
    public String toString(){
        String handString = "";

        for (Card card : getCards()){
            handString += card.toString();

            if(getCards().indexOf(card) != getCards().size() -1){
                handString+= "~,~";
            }
        }

        return handString;
    }


}
