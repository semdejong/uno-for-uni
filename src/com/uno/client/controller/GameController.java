package com.uno.client.controller;

import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Hand;
import com.uno.client.model.Player;

import java.util.ArrayList;

public class GameController {

    public static void updatePlayers(String players){
        String[] playerNames = players.split("~,~");

        for (String playerName : playerNames){
            Player player = new Player(playerName);
            Game.addPlayer(player);
        }
    }

    public static void updatePlayedCard(String card){
        String[] cardInParts = card.split("$,$");
        Game.setActiveCard(CommandHandler.makeCard(cardInParts));
    }

    public static void giveHand(String hand){

        Hand.setHand(new ArrayList<>());

        for (String card : hand.split("~,~")){
            String[] cardInParts = card.split("$,$");
            Hand.addCard(CommandHandler.makeCard(cardInParts));
        }
    }

}
