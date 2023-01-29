package com.uno.client.controller;

import com.uno.client.model.*;
import com.uno.client.view.DrawnCardView;

import java.util.ArrayList;
import java.util.Scanner;

public class GameController {

    public static void updatePlayers(String players){
        String[] playerNames = players.split("~,~");

        for (String playerName : playerNames){
            Player player = new Player(playerName);
            player.setHand(createClosedHand());
            Game.addPlayer(player);
        }
    }

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

    public static void giveHand(String hand){

        PlayerController.getOwnPlayer().setHand(new Hand());

        for (String card : hand.split("~,~")){
            String[] cardInParts = card.split("\\$,\\$");
            PlayerController.getOwnPlayer().getHand().addCard(CommandHandler.makeCard(cardInParts));
        }
    }

    public static void drawnCard(String card){
        String[] cardInParts = card.split("\\$,\\$");
        Card card1 = CommandHandler.makeCard(cardInParts);
        PlayerController.getOwnPlayer().getHand().addCard(card1);
        DrawnCardView.updateView(card1);
    }

    public static Hand createClosedHand(){
        ArrayList<Card> cards = new ArrayList<>();

        for(int i = 0; i < 7; i++){
            cards.add(new ClosedCard());
        }

        Hand hand = new Hand();
        hand.setHand(cards);

        return hand;
    }

}
