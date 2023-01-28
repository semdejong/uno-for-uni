package com.uno.server;

import com.uno.server.uno.Card;
import com.uno.server.uno.Lobby;
import com.uno.server.uno.Player;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandHandler {
    public static final String[] colors = {"RED", "BLUE", "GREEN", "YELLOW", "BLACK"};
    public static final String[] types = {"SKIP", "REVERSE", "DRAW_TWO", "WILD", "WILD_DRAW_FOUR"};
    public static final String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static boolean multipleGames = false;
    public static Lobby requestGame(String[] parts, ClientHandler sender){
        Lobby lobby = null;
        Player player = new Player(sender);

        if (parts.length == 1){
            if (Server.getLobbies().size() == 0){
                lobby = new Lobby();
                lobby.addPlayer(player);
                Server.broadCast("A new lobby has been created without pin");
            } else {
                sender.sendError(Error.E09, "A lobby already exists"); //no lobbies
                return null;
            }
        } else if (parts.length >= 2){
            if (parts[1].toLowerCase().contains("m")){
                int pin = (int)(Math.random()*1000);
                lobby = new Lobby(pin);
                Server.broadCast("A new lobby with the pin " + pin + " has been created");
                multipleGames = true;
                lobby.addPlayer(player);
            } else {
                if (Server.getLobbies().size() == 0){
                    lobby = new Lobby();
                    lobby.addPlayer(player);
                } else {
                    sender.sendError(Error.E09, "A lobby already exists"); //no lobbies
                    return null;
                }
            }
            // TODO - add special features implementation
            // lobby.setSupportedFeatures(parts[1].split(","));
        }
        if (parts.length >= 3){
            int maxPlayers = 0;
            try {
                maxPlayers = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e){
                sender.sendError(Error.E09, "Max players must be a number, lobby created with max players 10"); //max players must be a number
                maxPlayers = 10;
            }
            if (maxPlayers > 10){
                sender.sendError(Error.E09, "Max players cannot be more than 10"); //max players cannot be more than 10
                return null;
            } else if (maxPlayers < 2){
                sender.sendError(Error.E09, "Max players cannot be less than 2"); //max players cannot be less than 2
                return null;
            } else {
                lobby.setMaxPlayers(maxPlayers);
            }
        } else {
            lobby.setMaxPlayers(10);
        }
        player.setLobby(lobby);
        Server.addLobby(lobby);
        return lobby;
    }

    public static Lobby joinGame(String[] parts, ClientHandler sender){
        if (Server.getLobbies().size() == 0){
            sender.sendError(Error.E09, "no lobbies"); //no lobbies
            return null;
        }
        if (sender.getJoinedLobby() != null){
            sender.sendError(Error.E09, "player has joined a lobby already"); //player has joined a lobby already
            return null;
        }
        ArrayList<Lobby> lobbies = Server.getLobbies();
        Lobby firstLobby = lobbies.get(0);
        Player player = new Player(sender);

        if (!multipleGames) {
            if (firstLobby.getPlayers().size() == firstLobby.getMaxPlayers()) {
                sender.sendError(Error.E09, "lobby is full"); //lobby is full
                return null;
            }
            firstLobby.addPlayer(player);
            firstLobby.broadCastLobby("PlayerJoined|" +sender.getClientName(), sender);
            player.setLobby(firstLobby);
            return firstLobby;
        }else if (parts.length >= 1){
            for (Lobby lobby : lobbies){
                if (lobby.getPin() == Integer.parseInt(parts[1])){
                    if (lobby.getPlayers().size() == firstLobby.getMaxPlayers()) {
                        sender.sendError(Error.E09, "lobby is full"); //lobby is full
                        return null;
                    }
                    lobby.addPlayer(new Player(sender));
                    lobby.broadCastLobby("PlayerJoined|" +sender.getClientName(), sender);
                    player.setLobby(lobby);
                    return lobby;
                }
            }

            sender.sendError(Error.E09, "could not find lobby"); // could not find lobby
        }
        sender.sendError(Error.E04); //no lobbies
        return null;
    }
    public static Card makeCard(String[] parts) {
        Card.cardColor color = null;
        Card.cardType type = null;
        int value = 0;
        if (Arrays.asList(colors).contains(parts[0].toUpperCase())){
            color = Card.cardColor.valueOf(parts[0].toUpperCase());
        }
        if (Arrays.asList(numbers).contains(parts[1])){
            type = Card.cardType.NUMBER;
            value = Integer.parseInt(parts[1]);
        }
        switch (parts[1].toUpperCase()){
            case "SKIP":
                type = Card.cardType.SKIP;
                value = -1;
                break;
            case "REVERSE":
                type = Card.cardType.REVERSE;
                value = -2;
                break;
            case "DRAW_TWO":
                type = Card.cardType.DRAW_TWO;
                value = -3;
                break;
            case "WILD":
                type = Card.cardType.WILD;
                value = -4;
                break;
            case "WILD_DRAW_FOUR":
                type = Card.cardType.WILD_DRAW_FOUR;
                value = -5;
                break;
        }
        return new Card(type, color, value);
    }
}
