package com.uno.client.controller;

import com.uno.client.Computers.AI;
import com.uno.client.Computers.BasicComputer;
import com.uno.client.Computers.MediumComputer;
import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Player;
import com.uno.client.view.*;
import com.uno.server.Server;

import java.util.Scanner;

public class FlowController {
    public static boolean comStarted = false;

    public static void entryPoint(){

        Communicator com;

        System.out.println("Put in a UNO server address (ip:port), type (own:port) for own server on that port.");
        Scanner scanner = new Scanner(System.in);
        String serverInput = "";
        while (!comStarted) {
            serverInput = scanner.nextLine().toLowerCase().trim();
            if (serverInput.split(":").length == 2) {
                if(serverInput.contains("own")){
                    Server ownServer = new Server();
                    ownServer.setPort(Integer.parseInt(serverInput.split(":")[1]));
                    ownServer.start();
                }
                if(serverInput.equals("l")){
                    com = new Communicator("localhost", 3333);
                }else {
                    com = new Communicator(serverInput.contains("own") ? "localhost" : serverInput.split(":")[0], Integer.parseInt(serverInput.split(":")[1]));
                }
                com.run();
            }
        }


        WelcomeView.inputView();

        mainMenu();
    }

    public static void mainMenu(){
        emptyScreen();

        int choice = MainMenuView.updateView();

        if(choice == 1){
            createGame();
        } else if (choice == 2){
            playWithComputerPlayer();
            createGame();
        }else if(choice == 3){
            joinGame();
        } else if (choice == 4){
            playWithComputerPlayer();
            joinGame();
        }else if(choice == 5){
            CommandSender.sendMessage("LeaveServer");
            System.exit(0);
        }
    }

    public static void createGame(){
        emptyScreen();

        CreateNewGameView.updateView();
        updateLobby(null);
        WaitStartView.updateView();
    }

    public static void joinGame(){
        emptyScreen();
        JoinGameView.updateView();
        updateLobby(null);
        WaitStartView.enoughPlayers = true;
        WaitStartView.updateView();
    }

    public static void playWithComputerPlayer(){
        PlayWithComputerPlayerView.updateView();
        playWithComputerPlayer(PlayWithComputerPlayerView.inputView());
    }

    public static void playWithComputerPlayer(int choice){
        Player player = PlayerController.getOwnPlayer();
        Game.removePlayer(player);

        Player computerToPlay = null;

        if(choice == 1){
            computerToPlay = new BasicComputer(player.getName());
        }else if(choice == 2){
            computerToPlay = new MediumComputer(player.getName());
        }else{
            computerToPlay = new MediumComputer(player.getName());
        }

        Game.addPlayer(computerToPlay);
        PlayerController.setOwnPlayer(computerToPlay);
    }

    public static void updateLobby(String[] players){
        String[] defaultPlayers = new String[1];

        defaultPlayers[0] = PlayerController.getOwnPlayer().getName();

        emptyScreen();
//      WaitStartView.enoughPlayers = players != null && players.length > 1;
        WaitStartView.enoughPlayers = true;
        LobbyView.updateView(players != null ? players : defaultPlayers);
        System.out.println(players != null && players.length > 1);
    }

    public static void drawCard(Card card){
        DrawnCardView.updateView(card);

        if(PlayerController.isComputerPlayer()){
            AI ai = (AI) PlayerController.getOwnPlayer();
            ai.determineDrawPlay(card);

            return;
        }

        DrawnCardView.inputView(card);
    }

    public static void clientTurn(){
        ClientTurnView.updateView();

        if(PlayerController.isComputerPlayer()){
            AI ai = (AI) PlayerController.getOwnPlayer();
            ai.determineMove();

            return;
        }

        ClientTurnView.inputView();
    }

    public static void emptyScreen(){
        for(int i = 0; i < 50; i++){
            System.out.println("");
        }
    }
}
