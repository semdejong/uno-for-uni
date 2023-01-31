package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;
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
            String input = scanner.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(input);
            } catch (Exception e) {
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + input);
                    continue;
                }
                System.out.println("Please type in a number between 2 and 10");
                continue;
            }

            if (choice < 2 || choice > 10) {
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + choice);
                    continue;
                }
                System.out.println("That is not an option");
                continue;
            }
            CommandSender.sendMessage("REQUESTGAME|"+"m|"+choice);
            Game.addPlayer(PlayerController.getOwnPlayer());
            break;
        }
    }
}
