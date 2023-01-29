package com.uno.client.model;

import java.util.ArrayList;

public class Game {
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static Card activeCard;

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Game.players = players;
    }

    public static void removePlayer(Player player){
        players.remove(player);
    }

    public static Card getActiveCard() {
        return activeCard;
    }

    public static void setActiveCard(Card activeCard) {
        Game.activeCard = activeCard;
    }

    public static void addPlayer(Player player){
        players.add(player);
    }

    public static Player getPlayerByName(String name){
        for (Player player: players) {
            if(player.getName().equals(name)){
                return player;
            }
        }

        return null;
    }
}
