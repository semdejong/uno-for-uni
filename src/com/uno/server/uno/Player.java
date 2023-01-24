package com.uno.server.uno;

import com.uno.server.ClientHandler;
import com.uno.server.uno.Card;
import com.uno.server.uno.Hand;

public class Player {

    private Hand hand;
    private int score;
    private ClientHandler clientHandler;
    private Lobby lobby;
    
    public Player(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
        this.hand = new Hand();
    }


    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void addCard(Card card){
        hand.addCard(card);
    }

    public void removeCard(Card card){
        hand.removeCard(card);
    }

    public int getHandSize(){
        return hand.getHandSize();
    }

    public ClientHandler getClientHandler(){
        return clientHandler;
    }

    public String toString(){
        return "Player: " + this.clientHandler.getClientName() + " " + this.hand;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName(){
        return clientHandler.getClientName();
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
