package com.uno.server.uno;

import com.uno.server.ClientHandler;

import java.util.ArrayList;

public class Lobby {

    private ArrayList<Player> players;
    private int waitingToStart = 0;
    private int pin;
    private int maxPlayers;
    private Game game;
    private String[] supportedFeatures;

    public Lobby(int pin){
        this.pin = pin;
        players = new ArrayList<>();
    }
    public Lobby(){
        this.pin = 0;
        players = new ArrayList<>();
    }

    public void startGame(){
        game = new Game(players, this);
        game.startGame();
        String message = "GameStarted|";
//        for (String feature: supportedFeatures) {
//            message += feature + "|";
//        }
        broadCastLobby(message);

        broadCastLobby("PlayersAtTable|"+ getPlayersAsString());
        for (Player player: players) {
            player.getClientHandler().getMessageHandler().setGame(game);
        }
    }

    public Game getGame(){
        return game;
    }

    public void assignTeams(){}

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void broadCastLobby(String message){
        for (Player player: players) {
            player.getClientHandler().sendMessage(message);
        }
    }

    public void broadCastLobby(String message, ClientHandler exception){
        for (Player player: players){
            if(player.getClientHandler().equals(exception)){
                continue;
            }

            player.getClientHandler().sendMessage(message);
        }
    }

    public void broadCastLobby(String message, Player player){
        broadCastLobby(message, player.getClientHandler());
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getPin() {
        return pin;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getPlayersAsString(){
        String playersToReturn = "";

        for(Player player: players){
            playersToReturn += player.getName();

            if(players.indexOf(player) != players.size() - 1){
                playersToReturn += "|";
            }
        }

        return playersToReturn;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(ClientHandler clientHandler){
        players.add(new Player(clientHandler));
    }

    public int getWaitingToStart() {
        return waitingToStart;
    }

    public void setWaitingToStart(int waitingToStart) {
        this.waitingToStart = waitingToStart;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String[] getSupportedFeatures() {
        return supportedFeatures;
    }

    public void setSupportedFeatures(String[] supportedFeatures) {
        this.supportedFeatures = supportedFeatures;
    }
}
