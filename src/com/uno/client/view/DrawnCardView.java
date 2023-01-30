package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;
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
        while (true){
            String input = scanner.nextLine();
            if (input.equals("y") && !CommandHandler.playable(card)){
                System.out.println("you can't play this card");
                CommandSender.sendMessage("PlayDrawnCard|no");
                PlayerController.getOwnPlayer().addCard(card);
                break;
            } else if (input.equals("y")){
                if (card.getColor().equals(Card.cardColor.BLACK)){
                    System.out.println("What color do you want to change it to?");
                    String color = scanner.nextLine();
                    while (true){
                        if (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("green") || color.equalsIgnoreCase("yellow")){

                            CommandSender.sendMessage("PlayDrawnCard|yes|"+color.toUpperCase());
                            return;
                        } else {
                            System.out.println("Please enter a valid color");
                            color = scanner.nextLine();
                        }
                    }
                }

                CommandSender.sendMessage("PlayDrawnCard|yes");
                break;
            } else if (input.equals("n")){
                CommandSender.sendMessage("PlayDrawnCard|no");
                PlayerController.getOwnPlayer().addCard(card);
                break;
            } else {
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + input);
                    continue;
                }
                System.out.println("please enter y or n");
            }
        }

    }
}
