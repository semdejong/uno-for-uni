package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;

import java.util.Scanner;

public class MainMenuView {

    public static int updateView(){
        System.out.println("SMUNO");
        System.out.println("");
        System.out.println("Choose a option by number:");
        System.out.println("|---------------------------------------------------|");
        System.out.println("|                                                   |");
        System.out.println("|      1) ............. Create a game.              |");
        System.out.println("|      2) ............. Create a game with an AI    |");
        System.out.println("|      3) ............. Join a game.                |");
        System.out.println("|      4) ............  Join game with an AI.       |");
        System.out.println("|      5) ............. exit.                       |");
        System.out.println("|                                                   |");
        System.out.println("|---------------------------------------------------|");
        System.out.println("");

        return inputView();
    }

    public static int inputView(){

        boolean validChoice = false;
        System.out.println("Make a choice:");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + input);
                    continue;
                }
                System.out.println("Please type in a number between 1 and 5");
                continue;
            }

            if (choice < 1 || choice > 5) {
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + choice);
                    continue;
                }
                System.out.println("That is not an option");
                continue;
            }

            return choice;
        }
    }
}
