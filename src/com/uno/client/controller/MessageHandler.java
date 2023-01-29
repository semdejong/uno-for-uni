package com.uno.client.controller;

import com.uno.client.model.Game;
import com.uno.client.model.Hand;
import com.uno.client.model.Player;
import com.uno.client.view.*;
import com.uno.client.controller.CommandHandler;

import java.util.concurrent.Flow;

public class MessageHandler {
    public static boolean drawnCard = false;

    public static void receiveMessage(String message){
        System.out.println("Client received:" + message);
        String[] messageInParts = message.split("\\|");

        switch (messageInParts[0]){
            case "Welcome":
                PlayerController.createOwnPlayer(messageInParts[1]);
                WelcomeView.updateView(messageInParts[1]);
                break;
            case "PlayerJoined":
                WaitStartView.enoughPlayers = true;
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
                GameController.giveHand(messageInParts[1]);
                break;
            case "GameStarted":
                WaitStartView.started = true;
                break;
            case "StartingPlayer":
            case "ActivePlayer":
                if (messageInParts[1].equals(PlayerController.getOwnPlayer().getName())){
                    FlowController.clientTurn();
                }
                break;
            case "CardPlayed":
                // TODO: reduce player's card count
                GameController.updatePlayedCard(messageInParts[2]);
                OtherTurnView.updateView(messageInParts[1], Game.getActiveCard());
                // TODO:? update all player's card count
                break;
            case "CardDrawn":
                OtherTurnView.updateView(messageInParts[1], null);
                break;
            case "GiveCard":
                if (drawnCard){
                    FlowController.drawCard(CommandHandler.makeCard(messageInParts[1].split("\\$,\\$")));
                    drawnCard = false;
                } else{
                    ForcedDrawView.updateView(CommandHandler.makeCard(messageInParts[1].split("\\$,\\$")));
                }
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
                System.out.println(messageInParts[1]);
                break;
            default:
                System.out.println(message);
        }
    }
}
