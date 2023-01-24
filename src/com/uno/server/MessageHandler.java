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
    }

    public void receiveMessage(String message) {
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
            client.sendError(Error.E08); //not connected yet
            return;
        }

        switch (parts[0]){
            case "requestgame":
                lobby = CommandHandler.requestGame(parts, client);
                break;
            case "joingame":
                lobby = CommandHandler.joinGame(parts, client);
                break;
            case "start":
                if (lobby.getPlayers().size() < 2){
                    client.sendError(Error.E06); //not enough players
                    return;
                } else {
                    game = lobby.startGame();
                }
                break;
            case "playcard":
                //do something
                break;
            case "drawcard":
                //do something
                break;
            case "leavegame":
                Server.broadCast(sender.getClientName() + " has left the game.");
                sender.closeConnection();
            case "sendmessage":
                Server.broadCast(parts[1], sender);
                break;
            default:
                Server.sendMessage(sender, "E02");
        }
    }


}
