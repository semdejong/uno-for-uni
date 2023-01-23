package com.uno.server;

import com.uno.server.uno.Player;

import java.util.Random;

public class MessageHandler {

    //public String client;
    public void receiveMessage(String message, ClientHandler sender) {
        String[] parts = message.split("\\|");
        parts[0] = parts[0].toLowerCase().replace(" ", "");

        if (parts[0].equals("connect")){
            if (Server.getClientHandlerByName(parts[1]) != null){
                sender.sendError(Error.E01); //name not unique
                return;
            }
            sender.setClientName(parts[1]);
            return;
        }

        if (sender.getClientName() == null){
            sender.sendError(Error.E08); //not connected yet
            return;
        }

        switch (parts[0]){
            case "requestgame":
                CommandHandler.requestGame(parts, sender);
                break;
            case "joingame":
                CommandHandler.joinGame(parts, sender);
                break;
            case "start":
                //do something
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
