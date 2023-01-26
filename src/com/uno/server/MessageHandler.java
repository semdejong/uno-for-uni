package com.uno.server;

import com.uno.server.uno.Game;
import com.uno.server.uno.Lobby;
import com.uno.server.uno.Player;

import java.util.Random;

public class MessageHandler {

    private ClientHandler client;
    private Lobby lobby;
    private Game game;

    public MessageHandler(ClientHandler client){
        this.client = client;
        lobby = null;
    }

    public void receiveMessage(String message) {
        System.out.println("Server received:" + message);
        String[] parts = message.split("\\|");
        parts[0] = parts[0].toLowerCase().replace(" ", "");

        if (parts[0].equals("connect")){
            if (Server.getClientHandlerByName(parts[1]) != null){
                client.sendError(Error.E01); //name not unique
                return;
            }
            client.setClientName(parts[1]);
            client.sendMessage("Welcome|"+parts[1]);
            return;
        }

        if (client.getClientName() == null){
            client.sendError(Error.E09, "Client has not connected"); //not connected yet
            return;
        }

        switch (parts[0]){
            case "requestgame":
                this.lobby = CommandHandler.requestGame(parts, client);
                break;
            case "joingame":
                this.lobby = CommandHandler.joinGame(parts, client);
                break;
            case "start":
                if (lobby.getPlayers().size() < 2){
                    client.sendError(Error.E06); //not enough players
                    return;
                } else if (game != null){
                    client.sendError(Error.E09, "Game has already started");
                } else{
                    lobby.startGame();
                }
                break;
            case "playcard":
                String[] card = parts[1].split("$,$");
                game.playCard(CommandHandler.makeCard(card), client);
                break;
            case "drawcard":
                game.drawCard(client);
                break;
            case "leavegame":
                lobby.broadCastLobby(client.getClientName() + " has left the game.");
                client.closeConnection();
                break;
            case "leaveserver":
                client.closeConnection();
                break;
            case "sendmessage":
                Server.broadCast(parts[1], client);
                break;
            default:
                Server.sendMessage(client, "E02");
        }
    }


    public Lobby getLobby(){
        return lobby;
    }

    public ClientHandler getClient() {
        return client;
    }

    public void setClient(ClientHandler client) {
        this.client = client;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayerByClient(){

        for(Player player: game.getPlayers()){
            if(player.getClientHandler().equals(client)){
                return player;
            }
        }

        return null;
    }
}
