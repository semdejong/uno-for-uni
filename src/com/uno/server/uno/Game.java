package com.uno.server.uno;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private DrawPile drawPile;
    private PlayPile playPile;


    public Game(){
        players = new ArrayList<>();
        drawPile = new DrawPile();
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void startGame(){
        for(Player player : players){
            drawPile.dealCards(players);
        }

        playPile = new PlayPile(drawPile.drawCard());
    }


}
