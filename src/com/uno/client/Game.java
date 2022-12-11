package com.uno.client;

import java.util.ArrayList;

public class Game {
    public Game(){
        this.players = new ArrayList<Player>();
        this.activeCard = new Card(Card.cardType.NUMBER, Card.cardColor.YELLOW, 6); // will be returned by the server
    }

    private static ArrayList<Player> players;
    private static Card activeCard;

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Game.players = players;
    }


}
