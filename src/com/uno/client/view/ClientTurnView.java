package com.uno.client.view;

import com.uno.client.Computers.BasicComputer;
import com.uno.client.controller.*;
import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Hand;

import java.util.Scanner;

public class ClientTurnView {
    public static Hand hand;
    public static void updateView(){
        hand = PlayerController.getOwnPlayer().getHand();
        System.out.println("It's your turn!");
        System.out.println("The card on the top of the stack is:\n" + Game.getActiveCard().toStringPerson());
        System.out.println("Your hand is:\n" + hand.toString());
        System.out.println("Type which card you want to play (1-"+ hand.getHandSize()+ ") or type draw to draw a card");
    }
    public static void inputView(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("draw")){
                MessageHandler.drawnCard = true;
                CommandSender.sendMessage("DrawCard");
                return;
            }
            try {
                int number = Integer.parseInt(input);
                if (number < 1 || number > hand.getHandSize()){
                    System.out.println("Please enter a number between 1 and " + hand.getHandSize());
                    continue;
                } else {
                    if (CommandHandler.playable(hand.getCards().get(number-1))){
                        if (hand.getCards().get(number-1).getColor().equals(Card.cardColor.BLACK)){
                            System.out.println("What color do you want to change it to?");
                            String color = scanner.nextLine();
                            while (true){
                                if (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("green") || color.equalsIgnoreCase("yellow")){
                                    hand.getCards().get(number-1).setColor(Card.cardColor.valueOf(color.toUpperCase()));
                                    break;
                                } else {
                                    System.out.println("Please enter a valid color");
                                    color = scanner.nextLine();
                                }
                            }
                        }
                        CommandSender.sendMessage("PlayCard|" + hand.getCards().get(number-1).toString());
                        hand.removeCard(hand.getCards().get(number-1));
                        return;
                    } else{
                        System.out.println("You can't play that card");
                        continue;
                    }

                }
            } catch (NumberFormatException e){
                System.out.println("Please enter a number");
                continue;
            }
        }
    }
}
