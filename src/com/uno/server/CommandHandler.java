package com.uno.server;

import com.uno.server.uno.Lobby;
import com.uno.server.uno.Player;

public class CommandHandler {
    public static void requestGame(String[] parts, ClientHandler sender){
        Lobby lobby = null;
        Player player = new Player(sender);
        if (parts.length == 1){
            lobby = new Lobby();
            lobby.addPlayer(player);
        } else if (parts.length >= 2){
            if (parts[1].contains("pin")){
                int pin = (int)(Math.random()*1000);
                lobby = new Lobby(pin);
                Server.broadCast("A new lobby with the pin " + pin + " has been created");
                lobby.addPlayer(player);
            } else {
                lobby = new Lobby();
                lobby.addPlayer(player);
            }
            // TO DO - add special features implementation
        }
        if (parts.length == 3){
            lobby.setMaxPlayers(Integer.parseInt(parts[2]));
        }
        player.setLobby(lobby);
    }

    public static void joinGame(String[] parts, ClientHandler sender){
        if (Server.getLobbies().size() == 0){
            sender.sendError(Error.E08); //no lobbies
            return;
        }
        if (Server.getLobbies().size() == 1 && Server.getLobbies().get(0).getPin() == 0){
            Server.getLobbies().get(0).addPlayer(new Player(sender));
            return;
        }
        if ((Server.getLobbies().size() == 1 && Server.getLobbies().get(0).getPin() != 0)|| Server.getLobbies().size() > 1){
            for (Lobby lobby : Server.getLobbies()){
                if (lobby.getPin() == Integer.parseInt(parts[1])){
                    lobby.addPlayer(new Player(sender));
                    return;
                }
            }
        }
    }
}
