package com.uno.server.uno;


import com.uno.server.ClientHandler;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    // Creating a new ArrayList of type Card.
    public Hand(){
        this.hand = new ArrayList<>();
    }

    /**
     * This function adds a card to the hand.
     *
     * @param card The card to add to the hand.
     */
    public void addCard(Card card){
        hand.add(card);
    }

    /**
     * It adds a card to the player's hand, sends the card to the client, and broadcasts to the lobby that the player has
     * drawn a card
     *
     * @param card The card that is being added to the deck
     * @param client The client that drew the card
     */
    public void addCardSyncWithClient(Card card, ClientHandler client){
        addCard(card);
        client.sendMessage("GiveCard|"+card.toString());
        client.getJoinedLobby().broadCastLobby("CardDrawn|"+client.getClientName());
    }

    /**
     * This function removes a card from the hand
     *
     * @param card The card to be removed from the hand.
     */
    public void removeCard(Card card){
        hand.remove(card);

    }

    /**
     * This function returns an ArrayList of Card objects.
     *
     * @return The hand of cards.
     */
    public ArrayList<Card> getCards() {
        return hand;
    }

    /**
     * This function sets the hand of the player to the hand passed in.
     *
     * @param hand The hand of cards that the player has.
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * This function returns the size of the hand.
     *
     * @return The size of the hand.
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * This function clears the hand of the player.
     */
    public void clearHand(){
        hand.clear();
    }

    /**
     * This function takes the cards in the hand and returns a string of the cards in the hand
     *
     * @return A string of the cards in the hand.
     */
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
