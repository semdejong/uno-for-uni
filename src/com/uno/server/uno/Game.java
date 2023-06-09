package com.uno.server.uno;

import com.uno.server.ClientHandler;
import com.uno.server.Error;
import com.uno.server.Server;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<Player> players;
    private DrawPile drawPile;
    private PlayPile playPile;
    private Lobby lobby;
    private Player activePlayer;
    private int playedCards = 0;


    // This is the constructor for the Game class. It takes in an ArrayList of Player objects and a Lobby object.
    public Game(ArrayList<Player> players, Lobby lobby){
        this.players = players;
        this.lobby = lobby;
    }

    /**
     * This function returns an ArrayList of Player objects.
     *
     * @return An ArrayList of Player objects.
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * It shuffles the players, creates a new draw pile, checks if the cheat mode is enabled, deals the cards, draws the
     * first card and sets the active player
     */
    public void startGame(){
        drawPile = new DrawPile();
        Card firstCard = drawPile.drawCard();
        if (firstCard.getType() == Card.cardType.WILD || firstCard.getType() == Card.cardType.WILD_DRAW_FOUR){
            while (firstCard.getType() == Card.cardType.WILD || firstCard.getType() == Card.cardType.WILD_DRAW_FOUR){
                drawPile.addCard(firstCard);
                firstCard = drawPile.drawCard();
                drawPile.shuffle();
            }
        }
        drawPile.dealCards(players);
        playPile = new PlayPile(firstCard);
        Server.broadCast("StartingCard|"+playPile.getActiveCard().toString());

        for (Player player : players) {
            player.getClientHandler().sendMessage("giveHand|"+player.getHand().toString());
        }

        activePlayer = players.get((int)(Math.random()*players.size()));

        lobby.broadCastLobby("StartingPlayer|" + activePlayer.getName());
    }

    /**
     * If the card is valid, play it, and if it's a special card, do the special thing
     *
     * @param card The card that the player wants to play
     * @param client The client that is playing the card
     * @return A boolean value.
     */
    public synchronized boolean playCard(Card card, ClientHandler client){
        boolean skipped = false;

        if (lobby.getSupportedFeatures().contains("j") && playPile.getActiveCard().equals(card)){
            for (Player player : players) {
                if (player.getClientHandler().equals(client)){
                    activePlayer = player;
                }
            }
        }

        if(!activePlayer.getHand().getCards().contains(card)){
            client.sendError(Error.E05);
            return false;
        }
        if(!client.equals(activePlayer.getClientHandler())){
            client.sendError(Error.E07);
            return false;
        }
        if (!playPile.playCard(card, activePlayer.getHand())){
            client.sendError(Error.E03);
            return false;
        }
        if(card.getType() == Card.cardType.REVERSE){
            Collections.reverse(players);
        }
        if(card.getType() == Card.cardType.SKIP){
            skipped = true;
        }
        if(card.getType() == Card.cardType.DRAW_TWO){
            skipped = true;
            getNextPlayer().drawCardWithClientSync(2, this);
        }
        if(card.getType() == Card.cardType.WILD_DRAW_FOUR){
            skipped = true;
            getNextPlayer().drawCardWithClientSync(4, this);
        }

        playedCards++;
        activePlayer.removeCard(card);

        if (lobby.getSupportedFeatures().contains("s")&& card.getNumber()==0){
            Hand hand = players.get(players.size()-1).getHand();
            for (Player player : players) {
                hand = player.getAndSetHand(hand);
                player.getClientHandler().sendMessage("giveHand|"+player.getHand().toString());
            }
        }

        if (checkRoundEnd()){
            return true;
        }
        if (lobby.getSupportedFeatures().contains("s")&& card.getNumber()==7){
            client.getMessageHandler().SevenPlayed = card;
            return true;
        }
        lobby.broadCastLobby("CardPlayed|"+activePlayer.getName()+"|"+card);

        if (skipped){
            nextPlayer();
        }
        nextPlayer();
        lobby.broadCastLobby("ActivePlayer|" + activePlayer.getName());
        return true;
    }

    /**
     * If the client is not the active player, send an error message to the client. Otherwise, draw a card for the active
     * player
     *
     * @param client The client that sent the command.
     */
    public void drawCard(ClientHandler client){
        if(!client.equals(activePlayer.getClientHandler())){
            client.sendError(Error.E07);
            return;
        }
        activePlayer.drawCardWithClientSync(1, this);
    }

    /**
     * If the active player has no cards left, the round is over
     * Sends the points to all the clients in the lobby.
     *
     * @return A boolean value.
     */
    public boolean checkRoundEnd(){
        if (activePlayer.getHand().getCards().size() == 0){
            int points = calculatePoints();
            lobby.broadCastLobby("RoundOver|" + points + "|" + activePlayer.getName());
            activePlayer.setScore(points+activePlayer.getScore());
            if (checkGameEnd()){
                return true;
            }
            startGame();
            return true;
        }
        return false;
    }

    /**
     * If the active player's score is greater than or equal to 500, the game is over
     * Sends the name of the winner to all clients in lobby
     * @return A boolean value.
     */
    public boolean checkGameEnd(){
        if (activePlayer.getScore() >= 500){
            lobby.broadCastLobby("GameOver|" + activePlayer.getName());
            System.out.println("Winner: " + activePlayer.getName() + "-" + activePlayer.getScore());
            System.out.println("Loser: " + getNextPlayer().getName() + "-" + getNextPlayer().getScore());

            System.out.println("Played Cards: " + playedCards);
            return true;
        }

        return false;
    }

    /**
     * For each player, for each card in their hand, add the card's point value to the total.
     *
     * @return The total points of all the cards in the players' hands.
     */
    public int calculatePoints(){
        int points = 0;
        for (Player player : players){
            for (Card card : player.getHand().getCards()){
                switch (card.getType()){
                    case NUMBER:
                        points += card.getNumber();
                        break;
                    case SKIP:
                    case REVERSE:
                    case DRAW_TWO:
                        points += 20;
                        break;
                    case WILD:
                    case WILD_DRAW_FOUR:
                        points += 50;
                        break;
                }
            }
        }
        return points;
    }

    /**
     * If the active player is the last player in the list, set the active player to the first player in the list,
     * otherwise set the active player to the next player in the list
     */
    public void nextPlayer(){
        int index = players.indexOf(activePlayer);
        if (index == players.size() - 1){
            activePlayer = players.get(0);
        } else {
            activePlayer = players.get(index + 1);
        }
    }

    /**
     * If the active player is the last player in the list, return the first player, otherwise return the next player in
     * the list.
     *
     * @return The next player in the list.
     */
    public Player getNextPlayer(){
        int index = players.indexOf(activePlayer);
        if (index == players.size() - 1){
            return players.get(0);
        } else {
            return players.get(index + 1);
        }
    }

    /**
     * This function returns the drawPile
     *
     * @return The drawPile.
     */
    public DrawPile getDrawPile() {
        return drawPile;
    }



    /**
     * This function sets the players variable to the value of the player's parameter.
     *
     * @param players The list of players in the game.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * This function sets the draw pile to the draw pile passed in
     *
     * @param drawPile The DrawPile object that the player will draw from.
     */
    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    /**
     * This function returns the playPile.
     *
     * @return The playPile.
     */
    public PlayPile getPlayPile() {
        return playPile;
    }

    /**
     * This function returns the lobby.
     *
     * @return The lobby object.
     */
    public Lobby getLobby() {
        return lobby;
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
     * This function returns the active player.
     *
     * @return The active player.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * This function sets the active player to the player passed in.
     *
     * @param activePlayer The player who is currently playing.
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Remove the player from the game and add their cards to the draw pile.
     *
     * @param clientHandler The clientHandler of the player that is leaving the game.
     */
    public void removePlayer(ClientHandler clientHandler) {
        for (Player player : players) {
            if (player.getClientHandler().equals(clientHandler)) {
                players.remove(player);
                for (Card card : player.getHand().getCards()) {
                    drawPile.addCard(card);
                }
                break;
            }
        }
    }
}
