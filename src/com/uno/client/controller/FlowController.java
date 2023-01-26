package com.uno.client.controller;

import com.uno.client.view.*;
import com.uno.server.Server;

public class FlowController {

    public static void entryPoint(String server){

        String serverInput = server.toLowerCase().trim();

        if(serverInput.contains("own")){
            Server ownServer = new Server();
            ownServer.setPort(Integer.parseInt(serverInput.split(":")[1]));
            ownServer.start();
        }


        Communicator com = new Communicator(serverInput.contains("own") ? "localhost" : serverInput.split(":")[0], Integer.parseInt(serverInput.split(":")[1]));
        com.start();

        WelcomeView.inputView();

        mainMenu();
    }

    public static void mainMenu(){
        emptyScreen();

        int choice = MainMenuView.updateView();

        if(choice == 1){
            createGame();
        }else if(choice == 2){
            joinGame();
        }else if(choice == 4){
            CommandSender.sendMessage("LeaveServer");
            System.exit(0);
        }
    }

    public static void createGame(){
        emptyScreen();

        CreateNewGameView.updateView();
        updateLobby(null);
    }

    public static void joinGame(){
        emptyScreen();
        JoinGameView.updateView();
        updateLobby(null);
    }

    public static void updateLobby(String[] players ){
        String[] defaultPlayers = new String[1];

        defaultPlayers[0] = PlayerController.getOwnPlayer().getName();

        emptyScreen();
        LobbyView.updateView(players != null ? players : defaultPlayers);
    }

    public static void emptyScreen(){
        for(int i = 0; i < 50; i++){
            System.out.println("");
        }
    }
}
