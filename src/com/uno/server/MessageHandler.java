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

    /**
     * It receives a message from the client, splits it into parts, checks if the client is connected, and then checks if
     * the message is a valid command. If it is, it executes the command
     *
     * @param message The message that was received from the client.
     */
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
                if (parts.length < 2){
                    client.sendError(Error.E09, "No choice has been selected"); //no card selected
                    return;
                }
                if (game.getActivePlayer().getClientHandler().equals(client) && game.getActivePlayer().getLastDrawnCard() != null){
                    if(parts[1].equals("yes")||parts[1].equals("true")){
                        Card lastCard = getPlayerByClient().getLastDrawnCard();
                        if (lastCard.getType() == Card.cardType.WILD || lastCard.getType() == Card.cardType.WILD_DRAW_FOUR){
                            if (parts.length < 3){
                                client.sendError(Error.E09, "No color has been selected"); //no color selected
                                return;
                            }
                            if (parts[2].equalsIgnoreCase("red") || parts[2].equalsIgnoreCase("blue") || parts[2].equalsIgnoreCase("green") || parts[2].equalsIgnoreCase("yellow")){
                                lastCard.setColor(Card.cardColor.valueOf(parts[2].toUpperCase()));
                            }else {
                                client.sendError(Error.E09, "Color is not valid"); //color not valid
                                return;
                            }
                        }
                        if (!game.playCard(lastCard, client)){
                            game.nextPlayer();
                            lobby.broadCastLobby("ActivePlayer|"+game.getActivePlayer().getName());
                        }
                    }else{
                        game.nextPlayer();
                        lobby.broadCastLobby("ActivePlayer|"+game.getActivePlayer().getName());
                    } getPlayerByClient().setLastDrawnCard(null);
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
                if (parts.length < 2){
                    client.sendError(Error.E09, "No message has been entered"); //no message entered
                    return;
                }
                if (lobby != null){
                    lobby.broadCastLobby("receiveMessage|"+parts[1]+"|"+client);
                } else{
                    Server.broadCast("receiveMessage|"+parts[1]+"|"+client);
                }
                break;
            case "crash":
                String ouchie = parts[1000];
                break;
            default:
                Server.sendMessage(client, "E02");
        }
    }


    /**
     * This function returns the lobby.
     *
     * @return The lobby object.
     */
    public Lobby getLobby(){
        return lobby;
    }

    /**
     * This function returns the client.
     *
     * @return The client object.
     */
    public ClientHandler getClient() {
        return client;
    }

    /**
     * This function sets the client variable to the client that is passed in.
     *
     * @param client The client that is connected to the server.
     */
    public void setClient(ClientHandler client) {
        this.client = client;
    }

    /**
     * This function sets the lobby of the player to the lobby that is passed in.
     *
     * @param lobby The lobby that the player is in.
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * This function returns the game object.
     *
     * @return The game object.
     */
    public Game getGame() {
        return game;
    }

    /**
     * This function sets the game variable to the game variable passed in.
     *
     * @param game The game object that the player is in.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Get the player that is associated with the client that is calling this function.
     *
     * @return The player that is connected to the client.
     */
    public Player getPlayerByClient(){

        for(Player player: game.getPlayers()){
            if(player.getClientHandler().equals(client)){
                return player;
            }
        }

        return null;
    }


    /**
     * If the client is in a game, remove the client from the game and broadcast the message to the other players
     */
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
            if (game.getPlayers().size() == 1){
                game.getPlayers().get(0).getClientHandler().sendMessage("GameOver|" + game.getPlayers().get(0).getName());
            }else if (game.getActivePlayer().getClientHandler().equals(client)){
                game.nextPlayer();
                lobby.broadCastLobby("ActivePlayer|"+game.getActivePlayer().getName());
            }
            game = null;
        }
    }

    /**
     * If the client is in a lobby, leave the lobby. If the client is in a game, leave the game. If the client is connected
     * to the server, close the connection
     */
    public void leaveServer(){
        if (lobby != null){
            leaveGame();
        }
        client.closeConnection();
    }
}
