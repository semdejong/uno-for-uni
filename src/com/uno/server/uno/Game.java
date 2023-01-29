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
    private boolean started = false;
    private int playedCards = 0;


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
        Collections.shuffle(players);
        drawPile = new DrawPile();
        drawPile.dealCards(players);
        Card firstCard = drawPile.drawCard();
        if (firstCard.getType() == Card.cardType.WILD || firstCard.getType() == Card.cardType.WILD_DRAW_FOUR){
            while (firstCard.getType() == Card.cardType.WILD || firstCard.getType() == Card.cardType.WILD_DRAW_FOUR){
                drawPile.addCard(firstCard);
                firstCard = drawPile.drawCard();
                drawPile.shuffle();
            }
        }
        playPile = new PlayPile(firstCard);
        Server.broadCast("StartingCard|"+playPile.getActiveCard().toString());

        for (Player player : players) {
            player.getClientHandler().sendMessage("giveHand|"+player.getHand().toString());
        }
        activePlayer = players.get((int)(Math.random()*players.size()));
        lobby.broadCastLobby("ActivePlayer|" + activePlayer.getName());
        started = true;
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
            getNextPlayer().drawCardWithClientSync(2, this);
        }
        if(card.getType() == Card.cardType.WILD_DRAW_FOUR){
            skipped = true;
            getNextPlayer().drawCardWithClientSync(4, this);
        }
        playedCards++;
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
            return;
        }
        activePlayer.drawCardWithClientSync(1, this);
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
        if (activePlayer.getScore() >= 50000){
            lobby.broadCastLobby("GameOver|" + activePlayer.getName());
            for (Player player : players){
                if (activePlayer.getScore() >= 50000){
                    nextPlayer();
                    System.out.println("Winner: " + activePlayer.getName() + "-" + activePlayer.getScore());
                } else {
                    nextPlayer();
                    System.out.println("Loser: " + activePlayer.getName() + "-" + activePlayer.getScore());
                }
                player.setScore(0);
            }
            System.out.println("Played Cards: " + playedCards);
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

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    public PlayPile getPlayPile() {
        return playPile;
    }

    public void setPlayPile(PlayPile playPile) {
        this.playPile = playPile;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }
    public void removePlayer(ClientHandler clientHandler) {
        for (Player player : players) {
            if (player.getClientHandler().equals(clientHandler)) {
                players.remove(player);
                for (Card card : player.getHand().getCards()) {
                    drawPile.addCard(card);
                }
                break;
            }
        }
    }
}
