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
        String supportedFeatures = "";
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
            System.out.println("With what setting would you like to play?");
            System.out.println("add c to enable chat");
            System.out.println("add j for jump-in enabled");
            System.out.println("add s for Seven-O uno");
            System.out.println("if you do not want to add anything, type in nothing");
            System.out.println("Example: cjs");
            input = scanner.nextLine();
            if (input.contains("c")){
                supportedFeatures += "c";
            }
            if(input.contains("j")){
                supportedFeatures += "j";
            }
            if(input.contains("s")){
                supportedFeatures += "s";
            }
            CommandSender.sendMessage("REQUESTGAME|"+supportedFeatures+"mlt|"+choice);
//            Game.addPlayer(PlayerController.getOwnPlayer());
            return;
        }
    }
}
