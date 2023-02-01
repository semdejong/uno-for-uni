package com.uno.client.controller;

import com.uno.client.Computers.AI;
import com.uno.client.Computers.AdvancedComputer;
import com.uno.client.Computers.BasicComputer;
import com.uno.client.Computers.MediumComputer;
import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Player;
import com.uno.client.view.*;

import java.util.Scanner;

public class FlowController {
    public static boolean comStarted = false;
    public static final int LINES_TO_CLEAR = 50;
    public static final int STANDARD_PORT = 3333;

    /**
     * It starts the communicator, and then starts the welcome view
     */
    public static void entryPoint(){

        Communicator com;

        System.out.println("Put in a UNO server address (ip:port)");
        Scanner scanner = new Scanner(System.in);
        String serverInput;
        while (!comStarted) {
            serverInput = scanner.nextLine().toLowerCase().trim();
            if (serverInput.split(":").length == 2) {
                com = new Communicator(serverInput.split(":")[0], Integer.parseInt(serverInput.split(":")[1]));
                com.run();
            }else{
                if(serverInput.equals("l")){
                    com = new Communicator("localhost", STANDARD_PORT);
                    com.run();
                }
            }
        }


        WelcomeView.inputView();

        mainMenu();
    }

    /**
     * It displays the main menu and depending on the user's choice, it either creates a game, joins a game, or exits the
     * program
     */
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

    /**
     * Create a new game, update the view to show the new game, and update the lobby to show the new game.
     */
    public static void createGame(){
        emptyScreen();

        CreateNewGameView.updateView();
        updateLobby(null);
        WaitStartView.updateView();
    }

    /**
     * It clears the screen, updates the JoinGameView, updates the lobby, sets the boolean enoughPlayers to true, and
     * updates the WaitStartView
     */
    public static void joinGame(){
        emptyScreen();
        JoinGameView.updateView();
        updateLobby(null);
        WaitStartView.enoughPlayers = true;
        WaitStartView.updateView();
    }

    /**
     * The function `playWithComputerPlayer` is called with the user's input from the `PlayWithComputerPlayerView` class
     */
    public static void playWithComputerPlayer(){
        PlayWithComputerPlayerView.updateView();
        playWithComputerPlayer(PlayWithComputerPlayerView.inputView());
    }

    /**
     * It removes the current player from the game, creates a new computer player based on the choice, and adds the new
     * computer player to the game
     *
     * @param choice 1 = BasicComputer, 2 = MediumComputer, 3 = AdvancedComputer
     */
    public static void playWithComputerPlayer(int choice){
        Player player = PlayerController.getOwnPlayer();
        Game.removePlayer(player);

        Player computerToPlay;

        if(choice == 1){
            computerToPlay = new BasicComputer(player.getName());
        }else if(choice == 2){
            computerToPlay = new MediumComputer(player.getName());
        }else if(choice == 3) {
            computerToPlay = new AdvancedComputer(player.getName());
        }else{
            computerToPlay = new MediumComputer(player.getName());
        }

        Game.addPlayer(computerToPlay);
        PlayerController.setOwnPlayer(computerToPlay);
    }

    /**
     * It updates the lobby view with the given players
     *
     * @param players An array of strings containing the names of the players in the lobby.
     */
    public static void updateLobby(String[] players){
        String[] defaultPlayers = new String[1];

        defaultPlayers[0] = PlayerController.getOwnPlayer().getName();

        emptyScreen();

        WaitStartView.enoughPlayers = players != null && players.length > 1;
        LobbyView.updateView(players != null ? players : defaultPlayers);
    }

    /**
     * If the player is a computer, then the computer will determine whether to play the card or not. If the player is a
     * human, then the human will be prompted to play the card or not.
     *
     * @param card The card that was drawn
     */
    public static void drawCard(Card card){
        DrawnCardView.updateView(card);

        if(PlayerController.isComputerPlayer()){
            AI ai = (AI) PlayerController.getOwnPlayer();
            ai.determineDrawPlay(card);

            return;
        }

        DrawnCardView.inputView(card);
    }

    /**
     * If the player is a computer, then the computer determines its move. Otherwise, the player is prompted to make a
     * move.
     */
    public static void clientTurn(){
        ClientTurnView.updateView();

        if(PlayerController.isComputerPlayer()){
            AI ai = (AI) PlayerController.getOwnPlayer();
            ai.determineMove();

            return;
        }

        ClientTurnView.inputView();
    }

    /**
     * This function prints 50 blank lines to the console.
     */
    public static void emptyScreen(){
        for(int i = 0; i < LINES_TO_CLEAR; i++){
            System.out.println();
        }
    }
}
