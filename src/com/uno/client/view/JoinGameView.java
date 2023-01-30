package com.uno.client.view;

import com.uno.client.controller.CommandSender;

import java.util.Scanner;

public class JoinGameView {

    public static void updateView(){
        System.out.println("What is the pin of the game you want to join?");
        inputView();
    }

    public static void inputView(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                int pin = Integer.parseInt(scanner.nextLine());
                if (pin < 0 || pin > 9999){
                    System.out.println("Please type in a number between 0 and 9999");
                    continue;
                }
                CommandSender.sendMessage("JOINGAME|"+pin);
                break;
            } catch (Exception e) {
                System.out.println("Please type in a number");
                inputView();
            }
        }

    }
}
