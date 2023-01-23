package com.uno.server.uno;

import java.util.ArrayList;

public class Lobby {

    private ArrayList<Player> players = new ArrayList<>();
    private int waitingToStart = 0;
    private int pin;
    private int maxPlayers;

    public Lobby(int pin){
        this.pin = pin;
    }
    public Lobby(){
        this.pin = 0;
    }
    public void startMatch(){}
    public void assignTeams(){}

    public void addPlayer(Player player) {
        players.add(player);
    }
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getPin() {
        return pin;
    }
}
