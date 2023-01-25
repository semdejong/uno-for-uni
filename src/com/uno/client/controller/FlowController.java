package com.uno.client.controller;

import com.uno.client.view.CreateNewGameView;
import com.uno.client.view.MainMenuView;
import com.uno.client.view.WelcomeView;
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
        }
    }

    public static void createGame(){
        emptyScreen();

        CreateNewGameView.updateView();
    }

    public static void emptyScreen(){
        for(int i = 0; i < 50; i++){
            System.out.println("");
        }
    }
}
