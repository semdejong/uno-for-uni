package com.uno.client;

import java.util.ArrayList;

public class Game {
    public Game(){
        this.players = new ArrayList<Player>();
    }

    private static ArrayList<Player> players;

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Game.players = players;
    }


}
