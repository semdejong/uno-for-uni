package com.uno.client.controller;

import com.uno.client.model.Game;
import com.uno.client.model.Player;
import com.uno.client.view.PlayerJoinedView;
import com.uno.client.view.WelcomeView;
import com.uno.client.controller.CommandHandler;

import java.util.concurrent.Flow;

public class MessageHandler {

    public static void receiveMessage(String message){
        String[] messageInParts = message.split("\\|");
        switch (messageInParts[0]){
            case "Welcome":
                PlayerController.createOwnPlayer(messageInParts[1]);
                WelcomeView.updateView(messageInParts[1]);
                break;
            case "PlayerJoined":
                Game.addPlayer(new Player(messageInParts[1]));

                String[] playerNames = new String[Game.getPlayers().size()];

                for (int i = 0; i < Game.getPlayers().size(); i++){
                    playerNames[i] = Game.getPlayers().get(i).getName();
                }

                FlowController.updateLobby(playerNames);
                break;
            case "PlayersAtTable":
                GameController.updatePlayers(messageInParts[1]);
                break;
            case "StartingCard":
                GameController.updatePlayedCard(messageInParts[1]);
                break;
            case "giveHand":
                //TODO: make hand and clear old one
                GameController.giveHand(messageInParts[1]);
                break;
            case "GameStarted":
                break;
            case "StartingPlayer":
                break;
            case "ActivePlayer":
                //
                break;
            case "CardPlayed":
                // TODO: reduce player's card count
                GameController.updatePlayedCard(messageInParts[2]);
                // TODO:? update all player's card count
                break;
            case "CardDrawn":
                break;
            case "GiveCard":
                break;
            case "RoundOver":
                break;
            case "GameOver":
                break;
            case "receiveMessage":
                break;
            case "ERROR":
                if (messageInParts[1].equals("E09")){
                    System.out.println(messageInParts[2]);
                }
            default:
                System.out.println(message);
        }
    }
}