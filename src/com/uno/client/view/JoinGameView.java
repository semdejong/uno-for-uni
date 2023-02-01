package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;

import java.util.Scanner;

public class JoinGameView {

    public static void updateView(){
        System.out.println("What is the pin of the game you want to join?");
        inputView();
    }

    public static void inputView(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine();
            try {
                int pin = Integer.parseInt(input);
                if (pin < 0 || pin > 9999){
                    if (MessageHandler.getChat()){
                        CommandSender.sendMessage("SendMessage|" + pin);
                        continue;
                    }
                    System.out.println("Please type in a number between 0 and 9999");
                    continue;
                }
                CommandSender.sendMessage("JoinGame|"+pin);
                break;
            } catch (Exception e) {
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + input);
                    continue;
                }
                System.out.println("Please type in a number");
                inputView();
            }
        }

    }
}
