package com.uno.server;

import com.uno.server.uno.Card;
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
            if (parts.length == 1 || parts[1].equals("")){
                client.sendError(Error.E09, "no name has been entered"); //no name
                return;
            } else if (Server.getClientHandlerByName(parts[1]) != null){
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
                Lobby lobby1 = CommandHandler.requestGame(parts, client);
                if (lobby1 != null){
                    this.lobby = lobby1;
                }
                break;
            case "joingame":
                Lobby lobby2 = CommandHandler.joinGame(parts, client);
                if (lobby2 != null){
                    this.lobby = lobby2;
                }
                break;
            case "start":
                if (lobby == null){
                    client.sendError(Error.E09, "Client is not in a lobby"); //not in a lobby
                    return;
                } else
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
                if (parts.length < 2){
                    client.sendError(Error.E09, "No card has been selected"); //no card selected
                    return;
                }
                if (game == null){
                    client.sendError(Error.E09, "Client is not in a game"); //not in a game
                    return;
                }
                String[] cardString = parts[1].split("\\$,\\$");
                Card card = CommandHandler.makeCard(cardString);
                if (card == null){
                    client.sendError(Error.E09, "Card format is not valid"); //card not valid
                    return;
                }
                if (card.getColor() == Card.cardColor.BLACK){
                    client.sendError(Error.E09, "Card color is not valid if played"); //card color not valid
                }
                game.playCard(card, client);
                break;
            case "drawcard":
                if (game == null){
                    client.sendError(Error.E09, "Client is not in a game"); //not in a game
                    return;
                }
                game.drawCard(client);
                break;
            case "playdrawncard":
                if (game == null){
                    client.sendError(Error.E09, "Client is not in a game"); //not in a game
                    return;
                }
                if (game.getActivePlayer().getClientHandler().equals(client) && game.getActivePlayer().getLastDrawnCard() != null){
                    if(parts[1].equals("true")){
                        Card lastCard = getPlayerByClient().getLastDrawnCard();
                        if (lastCard.getType() == Card.cardType.WILD || lastCard.getType() == Card.cardType.WILD_DRAW_FOUR){
                            if (parts.length < 3){
                                client.sendError(Error.E09, "No color has been selected"); //no color selected
                                return;
                            }
                            lastCard.setColor(Card.cardColor.valueOf(parts[2].toUpperCase()));
                        }
                        game.playCard(lastCard, client);
                        getPlayerByClient().setLastDrawnCard(null);
                    }else{
                        game.nextPlayer();
                        lobby.broadCastLobby("ActivePlayer|"+game.getActivePlayer().getName());
                        getPlayerByClient().setLastDrawnCard(null);
                    }
                } else{
                    client.sendError(Error.E07);
                }
                break;
            case "leavegame":
                leaveGame();
                break;
            case "leaveserver":
                leaveServer();
                break;
            case "sendmessage":
                Server.broadCast(parts[1], client);
                break;
            case "crash":
                String ouchie = parts[1000];
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

    public void leaveGame(){
        if (lobby == null){
            client.sendError(Error.E09, "Client is not in a game"); //not in a game
            return;
        }
        lobby.broadCastLobby(client.getClientName() + " has left the game.");
        lobby.removePlayer(client);
        lobby = null;
        if (game != null){
            game.removePlayer(client);
            if (game.getActivePlayer().getClientHandler().equals(client)){
                if (game.getPlayers().size() == 1){
                    game.getPlayers().get(0).getClientHandler().sendMessage("GameOver|" + game.getPlayers().get(0).getName());
                }
                game.nextPlayer();
                lobby.broadCastLobby("ActivePlayer|"+game.getActivePlayer().getName());
            }
            game = null;
        }
    }

    public void leaveServer(){
        if (lobby != null){
            leaveGame();
        }
        client.closeConnection();
    }
}
