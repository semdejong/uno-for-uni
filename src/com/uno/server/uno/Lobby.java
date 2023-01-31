package com.uno.server.uno;

import com.uno.server.ClientHandler;
import com.uno.server.CommandHandler;

import java.util.ArrayList;

public class Lobby {

    private ArrayList<Player> players;
    private int waitingToStart = 0;
    private int pin;
    private int maxPlayers;
    private Game game;
    private String supportedFeatures;

    // A constructor for the Lobby class.
    public Lobby(int pin){
        this.pin = pin;
        players = new ArrayList<>();
        supportedFeatures = "tl";
    }
    // This is a constructor for the Lobby class.
    public Lobby(){
        this.pin = 0;
        players = new ArrayList<>();
    }

    /**
     * It starts a game
     * and broadcasts it to all clients in lobbies, including all needed data.
     */
    public void startGame(){
        game = new Game(players, this);
        String message = "GameStarted|" + supportedFeatures;

        broadCastLobby(message);

        broadCastLobby("PlayersAtTable|"+ getPlayersAsString());
        for (Player player: players) {
            player.getClientHandler().getMessageHandler().setGame(game);
        }

        game.startGame();
    }

    /**
     * This function returns the game object.
     *
     * @return The game object.
     */
    public Game getGame(){
        return game;
    }

    /**
     * This function assigns teams to players.
     */
    public void assignTeams(){}

    /**
     * This function adds a player to the list of players.
     *
     * @param player The player to add to the game.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * This function sends a message to all players in the lobby.
     *
     * @param message The message you want to send to the players.
     */
    public void broadCastLobby(String message){
        for (Player player: players) {
            player.getClientHandler().sendMessage(message);
        }
    }

    /**
     * This function sends a message to all players in the lobby, except for the player who is passed in as an argument.
     *
     * @param message The message that will be sent to all the players in the lobby.
     * @param exception The client that should not receive the message.
     */
    public void broadCastLobby(String message, ClientHandler exception){
        for (Player player: players){
            if(player.getClientHandler().equals(exception)){
                continue;
            }

            player.getClientHandler().sendMessage(message);
        }
    }

    /**
     * Broadcast a message to all players in the lobby, except the player who sent the message.
     *
     * @param message The message you want to send to the lobby
     * @param player The player who sent the message
     */
    public void broadCastLobby(String message, Player player){
        broadCastLobby(message, player.getClientHandler());
    }

    /**
     * This function sets the maximum number of players in the game.
     *
     * @param maxPlayers The maximum number of players that can join the game.
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * This function returns the pin number of the card.
     *
     * @return The pin number.
     */
    public int getPin() {
        return pin;
    }

    /**
     * This function returns an ArrayList of Player objects.
     *
     * @return An ArrayList of Player objects.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * This function returns a string of all the players in the game, separated by a pipe character.
     *
     * @return A string of all the players names separated by a pipe.
     */
    public String getPlayersAsString(){
        String playersToReturn = "";

        for(Player player: players){
            playersToReturn += player.getName();

            if(players.indexOf(player) != players.size() - 1){
                playersToReturn += "~,~";
            }
        }

        return playersToReturn;
    }

    /**
     * This function sets the players variable to the players variable passed in.
     *
     * @param players The list of players in the game.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * This function adds a new player to the game.
     *
     * @param clientHandler The clientHandler object that is passed in from the server.
     */
    public void addPlayer(ClientHandler clientHandler){
        players.add(new Player(clientHandler));
    }

    /**
     * This function returns the number of threads waiting to start.
     *
     * @return The number of threads waiting to start.
     */
    public int getWaitingToStart() {
        return waitingToStart;
    }

    /**
     * This function sets the number of players waiting to start the game.
     *
     * @param waitingToStart The number of threads that are waiting to start.
     */
    public void setWaitingToStart(int waitingToStart) {
        this.waitingToStart = waitingToStart;
    }

    /**
     * This function sets the pin to the value of the parameter pin.
     *
     * @param pin The pin number that the servo is connected to.
     */
    public void setPin(int pin) {
        this.pin = pin;
    }

    /**
     * This function returns the maximum number of players that can be in a game.
     *
     * @return The maxPlayers variable is being returned.
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Returns an array of strings containing the names of the features supported by this implementation.
     *
     * @return The array of supported features.
     */

    /**
     * > This function sets the supported features of the device
     *
     * @param supportedFeatures A list of features that the device supports.
     */

    /**
     * Remove the player from the list of players.
     *
     * @param clientHandler The clientHandler of the player that is being removed.
     */
    public void removePlayer(ClientHandler clientHandler) {
        for (Player player : players) {
            if (player.getClientHandler().equals(clientHandler)) {
                players.remove(player);
                break;
            }
        }
    }
    public String getSupportedFeatures() {
        return supportedFeatures;
    }
    public void setSupportedFeatures(String  supportedFeatures) {
        this.supportedFeatures = supportedFeatures;
    }
    public void addSupportedFeature(String feature){
        supportedFeatures += feature;
    }
}
