package com.uno.client.model;

import java.util.ArrayList;

public class Game {
    public Game(){
        this.players = new ArrayList<Player>();
        this.activeCard = new Card(Card.cardType.NUMBER, Card.cardColor.YELLOW, 6); // will be returned by the server
    }

    private static ArrayList<Player> players;
    private Card activeCard;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        Game.players = players;
    }


}
