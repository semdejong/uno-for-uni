package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;

import java.util.Scanner;

public class PlayWithComputerPlayerView {
    public static void updateView(){
        System.out.println("You are playing with a computer player");
        System.out.println("Which computer player do you want to use:");
        System.out.println("|-----------------------------------------|");
        System.out.println("|                                         |");
        System.out.println("|   1) ............. Basic computer.      |");
        System.out.println("|   2) ............. Medium computer.     |");
        System.out.println("|   3) ............. Advanced computer.   |");
        System.out.println("|                                         |");
        System.out.println("|-----------------------------------------|");

        System.out.println("");
        System.out.println("Make a choice:");
    }

    public static int inputView(){

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
                System.out.println("Please type in a number between 1 and 3");
                continue;
            }
            if(choice < 1 || choice > 3){
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + choice);
                    continue;
                }
                System.out.println("Please type in a number between 1 and 3");
                continue;
            }

            return choice;
        }
    }
}
