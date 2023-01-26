package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.PlayerController;
import com.uno.client.model.Card;
import com.uno.client.controller.CommandHandler;

import java.util.Scanner;

public class DrawnCardView {
    public static void updateView(Card card){
        System.out.println("you drew: " + card.toStringPerson());
        inputView(card);
    }
    public static void inputView(Card card){
        Scanner in = new Scanner(System.in);
        System.out.println("would you like to play this card? y/n");
        while (true){
            if (in.nextLine().equals("y") && !CommandHandler.playable(card)){
                System.out.println("you can't play this card");
                PlayerController.getOwnPlayer().addCard(card);
            } else if (in.nextLine().equals("y")){
                CommandSender.sendMessage("PlayDrawnCard|true");
                break;
            } else if (in.nextLine().equals("n")){
                CommandSender.sendMessage("PlayDrawnCard|false");
                PlayerController.getOwnPlayer().addCard(card);
                break;
            } else {
                System.out.println("please enter y or n");
            }
        }

    }
}
