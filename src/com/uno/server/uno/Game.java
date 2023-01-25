package com.uno.server.uno;

import com.uno.server.ClientHandler;
import com.uno.server.Error;
import com.uno.server.Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Game {
    private ArrayList<Player> players;
    private DrawPile drawPile;
    private PlayPile playPile;
    private Lobby lobby;
    private Player activePlayer;


    public Game(ArrayList<Player> players, Lobby lobby){
        this.players = players;
        this.lobby = lobby;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void startGame(){
        drawPile = new DrawPile();
        drawPile.dealCards(players);
        playPile = new PlayPile(drawPile.drawCard());

        Server.broadCast("StartingCard|"+playPile.getActiveCard().toString());

        for (Player player : players) {
            player.getClientHandler().sendMessage("giveHand|"+player.getHand().toString());
        }
        activePlayer = players.get((int)(Math.random()*players.size()));
        lobby.broadCastLobby("ActivePlayer|" + activePlayer.getName());
    }

    public void playCard(Card card, ClientHandler client){
        boolean skipped = false;
        if(!client.equals(activePlayer.getClientHandler())){
            client.sendError(Error.E07);
        }
        if(!activePlayer.getHand().getCards().contains(card)){
            client.sendError(Error.E05);
        }
        if (!playPile.playCard(card, activePlayer.getHand())){
            activePlayer.getClientHandler().sendError(Error.E03);
            return;
        }
        if(card.getType() == Card.cardType.REVERSE){
            Collections.reverse(players);
        }
        if(card.getType() == Card.cardType.SKIP){
            skipped = true;
        }
        if(card.getType() == Card.cardType.DRAW_TWO){
            skipped = true;
            getNextPlayer().drawCardWithClientSync(2);
        }
        if(card.getType() == Card.cardType.WILD_DRAW_FOUR){
            skipped = true;
            getNextPlayer().drawCardWithClientSync(4);
        }

        activePlayer.removeCard(card);
        if (checkRoundEnd()){
            return;
        }
        lobby.broadCastLobby("CardPlayed|"+activePlayer.getName()+"|"+card.toString());
        if (skipped){
            nextPlayer();
        }
        nextPlayer();
        lobby.broadCastLobby("ActivePlayer|" + activePlayer.getName());
    }

    public void drawCard(ClientHandler client){
        if(!client.equals(activePlayer.getClientHandler())){
            client.sendError(Error.E07);
        }

        activePlayer.drawCardWithClientSync(1);
    }

    public boolean checkRoundEnd(){
        if (activePlayer.getHand().getCards().size() == 0){
            int points = calculatePoints();
            lobby.broadCastLobby("RoundOver|" + points);
            activePlayer.setScore(points+activePlayer.getScore());
            if (checkGameEnd()){
                return true;
            }
            startGame();
            return true;
        }
        return false;
    }

    public boolean checkGameEnd(){
        if (activePlayer.getScore() >= 500){
            lobby.broadCastLobby("GameOver|" + activePlayer.getName());
            return true;
        }

        return false;
    }

    public int calculatePoints(){
        int points = 0;
        for (Player player : players){
            for (Card card : player.getHand().getCards()){
                switch (card.getType()){
                    case NUMBER:
                        points += card.getNumber();
                        break;
                    case SKIP:
                        points += 20;
                        break;
                    case REVERSE:
                        points += 20;
                        break;
                    case DRAW_TWO:
                        points += 20;
                        break;
                    case WILD:
                        points += 50;
                        break;
                    case WILD_DRAW_FOUR:
                        points += 50;
                        break;
                }
            }
        }
        return points;
    }

    public void nextPlayer(){
        int index = players.indexOf(activePlayer);
        if (index == players.size() - 1){
            activePlayer = players.get(0);
        } else {
            activePlayer = players.get(index + 1);
        }
    }

    public Player getNextPlayer(){
        int index = players.indexOf(activePlayer);
        if (index == players.size() - 1){
            return players.get(0);
        } else {
            return players.get(index + 1);
        }
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }
}
