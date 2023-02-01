package com.uno.client.controller;

import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Player;
import com.uno.client.view.*;

public class MessageHandler {
    public static boolean drawnCard = false;
    private static boolean chat = false;
    private static boolean jumpIn = false;
    public static boolean SevenO = false;

    /**
     * It receives a message from the server, splits it into parts, and then calls the appropriate function in the
     * appropriate controller
     *
     * @param message The message received from the server
     */
    public static void receiveMessage(String message){
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
                GameController.updatePlayedCard(null, messageInParts[1]);
                break;
            case "giveHand":
                GameController.giveHand(messageInParts[1]);
                break;
            case "GameStarted":
                WaitStartView.started = true;
                if (messageInParts.length > 1){
                    if (messageInParts[1].contains("c")){
                        chat = true;
                    }
                    if (messageInParts[1].contains("j")){
                        jumpIn = true;
                    }
                    if (messageInParts[1].contains("s")){
                        SevenO = true;
                    }
                }
                GameStartedView.updateView();
                break;
            case "StartingPlayer":
            case "ActivePlayer":
                if (messageInParts[1].equals(PlayerController.getOwnPlayer().getName())){
                    FlowController.clientTurn();
                }
                break;
            case "CardPlayed":
                JumpInView.played = true;
                GameController.updatePlayedCard(messageInParts[1], messageInParts[2]);
                OtherTurnView.updateView(messageInParts[1], Game.getActiveCard());
                if (jumpIn){
                    for (Card card : PlayerController.getOwnPlayer().getHand().getCards()){
                        if (card.equals(Game.getActiveCard())){
                            JumpInView.updateView();
                            break;
                        }
                    }
                }

                break;
            case "CardDrawn":
                GameController.updateDrawnCard(messageInParts[1]);
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
                 CommandHandler.roundOver();
                break;
            case "GameOver":
                if (messageInParts.length < 2){
                    System.out.println("Game over");
                }
                GameOverView.updateView(messageInParts[1]);
                break;
            case "receiveMessage":
                if (messageInParts.length <3){
                    return;
                }
                System.out.println(messageInParts[2] + "Said: " + messageInParts[1]);
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
    public static boolean getChat(){
        return chat;
    }
}
