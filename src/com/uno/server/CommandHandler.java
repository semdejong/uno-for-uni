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
                sender.sendError(Error.E09, "Max players cannot be more than 10, lobby created with max players 10"); //max players cannot be more than 10
                maxPlayers = 10;
            } else if (maxPlayers < 2){
                sender.sendError(Error.E09, "Max players cannot be less than 2, lobby created with max players 2"); //max players cannot be less than 2
                maxPlayers = 2;
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
        ArrayList<Lobby> lobbies = Server.getLobbies();

        if (lobbies.size() == 0){
            sender.sendError(Error.E09, "no lobbies"); //no lobbies
            return null;
        }
        if (sender.getJoinedLobby() != null){
            sender.sendError(Error.E09, "player has joined a lobby already"); //player has joined a lobby already
            return null;
        }

        Lobby firstLobby = lobbies.get(0);

        if (!multipleGames) {
            return joinLobby(firstLobby, sender);
        }else if (parts.length >= 2){
            int pin;
            try {
                pin = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e){
                sender.sendError(Error.E09, "pin must be a number"); //pin must be a number
                return null;
            }
            for (Lobby lobby : lobbies){
                if (lobby.getPin() == pin){
                    return joinLobby(lobby, sender);
                }
            }
        }
        sender.sendError(Error.E09, "could not find a lobby"); //no lobbies
        return null;
    }
    public static Lobby joinLobby(Lobby lobby, ClientHandler sender){
        Player player = new Player(sender);
        if (lobby.getPlayers().size() == lobby.getMaxPlayers()) {
            sender.sendError(Error.E09, "lobby is full"); //lobby is full
            return null;
        }
        if (lobby.getGame() != null) {
            sender.sendError(Error.E09, "game has already started"); //game has already started
            return null;
        }
        lobby.addPlayer(player);
        lobby.broadCastLobby("PlayerJoined|" +sender.getClientName(), sender);
        player.setLobby(lobby);
        return lobby;
    }
    public static Card makeCard(String[] parts){
        Card.cardColor color = null;
        Card.cardType type = null;
        int value = 0;
        if (parts.length < 2){
            return null;
        }
        if (Arrays.asList(colors).contains(parts[0].toUpperCase())){
            color = Card.cardColor.valueOf(parts[0].toUpperCase());
        } else {
            return null;
        }
        if (Arrays.asList(numbers).contains(parts[1])){
            type = Card.cardType.NUMBER;
            value = Integer.parseInt(parts[1]);
            return new Card(type, color, value);
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
            default:
                return null;
        }
        return new Card(type, color, value);
    }
}
