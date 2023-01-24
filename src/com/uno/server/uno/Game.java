package com.uno.server.uno;

import com.uno.server.Server;

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
        drawPile.dealCards(players);
        playPile = new PlayPile(drawPile.drawCard());

        Server.broadCast("StartingCard|CARDREPR");

        for (Player player : players) {
            player.getClientHandler().sendMessage("GiveHand|CARDREP|CARDREP|CARDREP|CARDREP....");
        }
    }


}
