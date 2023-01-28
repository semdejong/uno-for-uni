package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.PlayerController;
import com.uno.client.model.Card;
import com.uno.client.controller.CommandHandler;

import java.util.Scanner;

public class DrawnCardView {
    public static void updateView(Card card){
        System.out.println("you drew: " + card.toStringPerson());
    }
    public static void inputView(Card card){
        Scanner scanner = new Scanner(System.in);

        System.out.println("would you like to play this card? y/n");
        String input = scanner.nextLine();
        while (true){
            if (input.equals("y") && !CommandHandler.playable(card)){
                System.out.println("you can't play this card");
                CommandSender.sendMessage("PlayDrawnCard|false");
                PlayerController.getOwnPlayer().addCard(card);
                break;
            } else if (input.equals("y")){
                if (card.getColor().equals(Card.cardColor.BLACK)){
                    System.out.println("What color do you want to change it to?");
                    String color = scanner.nextLine();
                    while (true){
                        if (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("green") || color.equalsIgnoreCase("yellow")){

                            CommandSender.sendMessage("PlayDrawnCard|true|"+color.toUpperCase());
                            return;
                        } else {
                            System.out.println("Please enter a valid color");
                            color = scanner.nextLine();
                        }
                    }
                }

                CommandSender.sendMessage("PlayDrawnCard|true");
                break;
            } else if (input.equals("n")){
                CommandSender.sendMessage("PlayDrawnCard|false");
                PlayerController.getOwnPlayer().addCard(card);
                break;
            } else {
                System.out.println("please enter y or n");
            }

            input = scanner.nextLine();
        }

    }
}
