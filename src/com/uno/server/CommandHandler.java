package com.uno.server;

import com.uno.server.uno.Lobby;
import com.uno.server.uno.Player;

import java.util.ArrayList;

public class CommandHandler {
    public static Lobby requestGame(String[] parts, ClientHandler sender){
        Lobby lobby = null;
        Player player = new Player(sender);

        if (parts.length == 2){
            lobby = new Lobby();
            lobby.addPlayer(player);
            Server.broadCast("A new lobby has been created without pin");
        } else if (parts.length >= 3){
            if (parts[2].toLowerCase().contains("p")){
                int pin = (int)(Math.random()*1000);
                lobby = new Lobby(pin);
                Server.broadCast("A new lobby with the pin " + pin + " has been created");
                lobby.addPlayer(player);
            } else {
                lobby = new Lobby();
                lobby.addPlayer(player);
            }
            // TO DO - add special features implementation
            // lobby.setSupportedFeatures(parts[1].split(","));
        }
        if (parts.length == 3){
            lobby.setMaxPlayers(Integer.parseInt(parts[2]));
        }
        player.setLobby(lobby);
        Server.addLobby(lobby);
        return lobby;
    }

    public static Lobby joinGame(String[] parts, ClientHandler sender){
        if (Server.getLobbies().size() == 0){
            sender.sendError(Error.E08); //no lobbies
            return;
        }

        ArrayList<Lobby> lobbies = Server.getLobbies();
        Lobby firstLobby = lobbies.get(0);

        if (lobbies.size() == 1 && firstLobby.getPin() == 0) {
            firstLobby.addPlayer(new Player(sender));
            firstLobby.broadCastLobby("PlayerJoined|" +sender.getClientName(), sender);
            return firstLobby;
        }else if ((lobbies.size() == 1 && firstLobby.getPin() != 0)|| lobbies.size() > 1){
            for (Lobby lobby : lobbies){
                if (lobby.getPin() == Integer.parseInt(parts[1])){
                    lobby.addPlayer(new Player(sender));
                    lobby.broadCastLobby("PlayerJoined|" +sender.getClientName(), sender);
                    return lobby;
                }
            }
        }
        return null;
    }
}
