package com.uno.client.controller;

import com.uno.client.model.*;

import java.util.ArrayList;

public class GameController {

    /**
     * This function takes a string of player names separated by a delimiter, creates a player object for each name, and
     * adds the player to the game
     *
     * @param players A string of player names separated by "~,~"
     */
    public static void updatePlayers(String players){
        String[] playerNames = players.split("~,~");

        Game.setPlayers(new ArrayList<>());

        for (String playerName : playerNames){
            if(!playerName.equals(PlayerController.getOwnPlayer().getName())) {
                Player player = new Player(playerName);
                player.setHand(createClosedHand());
                Game.addPlayer(player);
            }
        }

        Game.addPlayer(PlayerController.getOwnPlayer());
    }

    /**
     * It removes the closed card from the player who played the card and sets the active card to the card that was played
     *
     * @param clientName The name of the player who played the card.
     * @param card The card that was played.
     */
    public static void updatePlayedCard(String clientName, String card){

        if(clientName != null){

            Player player = Game.getPlayerByName(clientName);

            if(player != null) {
                if (!PlayerController.getOwnPlayer().equals(player)){
                    player.removeClosedCard();
                }
            }
        }

        String[] cardInParts = card.split("\\$,\\$");
        Game.setActiveCard(CommandHandler.makeCard(cardInParts));
    }

    /**
     * If the player is not the own player, add a closed card to the player's hand
     *
     * @param clientName The name of the player who drew the card.
     */
    public static void updateDrawnCard(String clientName){
        if(clientName != null){

            Player player = Game.getPlayerByName(clientName);

            if(player != null) {
                if (!PlayerController.getOwnPlayer().equals(player)){
                    player.addCard(new ClosedCard());
                }
            }
        }
    }

    /**
     * It takes a string, splits it into an array of strings, splits each string into an array of strings, and then makes a
     * card out of each array of strings
     *
     * @param hand A string of cards separated by "~,~"
     */
    public static void giveHand(String hand){

        PlayerController.getOwnPlayer().setHand(new Hand());

        for (String card : hand.split("~,~")){
            String[] cardInParts = card.split("\\$,\\$");
            PlayerController.getOwnPlayer().getHand().addCard(CommandHandler.makeCard(cardInParts));
        }
    }

    /**
     * Create a hand with 7 closed cards.
     *
     * @return A hand with 7 closed cards.
     */
    public static Hand createClosedHand(){
        ArrayList<Card> cards = new ArrayList<>();

        for(int i = 0; i < 7; i++){
            cards.add(new ClosedCard());
        }

        Hand hand = new Hand();
        hand.setHand(cards);

        return hand;
    }

    public static String displayHandPlayers() {
        String hand = "";
        ArrayList<Player> players = Game.getPlayers();
        for(int i = 0; i < players.size(); i++) {
            hand += (i+1)+": "+players.get(i).getName() + " has " + players.get(i).getHand().getCards().size() + " cards\n";
        }
        return hand;
    }
}
