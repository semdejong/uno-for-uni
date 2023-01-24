package com.uno.server.uno;

import com.uno.server.Error;
import com.uno.server.Server;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private DrawPile drawPile;
    private PlayPile playPile;
    private Lobby lobby;
    private Player activePlayer;


    public Game(ArrayList<Player> players, Lobby lobby){
        this.players = players;
        this.lobby = lobby;
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

        activePlayer = players.get(0);
        lobby.broadCastLobby("ActivePlayer|" + activePlayer.getName());
    }

    public void playCard(Card card){
        if (!playPile.playCard(card, activePlayer.getHand())){
            activePlayer.getClientHandler().sendError(Error.E03);
            return;
        }
        if(card.getType() == Card.cardType.REVERSE){
            Collections.reverse(players);
        }
        if(card.getType() == Card.cardType.SKIP){
            nextPlayer();
        }
        nextPlayer();
        lobby.broadCastLobby("ActivePlayer|" + activePlayer.getName());
    }
    public Player nextPlayer(){
        int index = players.indexOf(activePlayer);
        if (index == players.size() - 1){
            activePlayer = players.get(0);
        } else {
            activePlayer = players.get(index + 1);
        }
        return activePlayer;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }
}
