package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.PlayerController;
import com.uno.client.model.Game;

import java.util.Scanner;

public class CreateNewGameView {

    public static void updateView(){
        System.out.println("With how many players do you want to play?");
        inputView();
    }

    public static void inputView(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please type in a number between 2 and 10");
                continue;
            }

            if (choice < 2 || choice > 10) {
                System.out.println("That is not an option");
                continue;
            }
            CommandSender.sendMessage("REQUESTGAME|"+"m|"+choice);
            Game.addPlayer(PlayerController.getOwnPlayer());
            break;
        }
    }
}
