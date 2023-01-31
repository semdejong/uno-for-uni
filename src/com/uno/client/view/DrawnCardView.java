package com.uno.client.view;

import com.uno.client.controller.*;
import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Hand;

import java.util.ArrayList;
import java.util.Scanner;

public class DrawnCardView {
    public static void updateView(Card card){
        System.out.println("you drew: " + CommandHandler.colorRizeString(card.toStringPerson(), card.getColor()));
    }
    public static void inputView(Card card){
        Scanner scanner = new Scanner(System.in);
        Hand hand= PlayerController.getOwnPlayer().getHand();
        System.out.println("would you like to play this card? y/n");
        while (true){
            String input = scanner.nextLine();
            if (input.equals("y") && !CommandHandler.playable(card)){
                System.out.println("you can't play this card");
                CommandSender.sendMessage("PlayDrawnCard|no");
                PlayerController.getOwnPlayer().addCard(card);
                break;
            } else if (input.equals("y")){
                int playerNumber = 0;
                if (card.getColor().equals(Card.cardColor.BLACK)){
                    System.out.println("What color do you want to change it to?");
                    System.out.println(CommandHandler.colorRizeString("RED", Card.cardColor.RED) + "    " + CommandHandler.colorRizeString("BLUE", Card.cardColor.BLUE) + "    " + CommandHandler.colorRizeString("YELLOW", Card.cardColor.YELLOW) + "    " + CommandHandler.colorRizeString("GREEN", Card.cardColor.GREEN));
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
                if (MessageHandler.SevenO && card.getNumber() == 7){
                    System.out.println("You played a 7 please choose a player with whom to change hands");
                    System.out.println("The players have the following hands:");
                    System.out.println(GameController.displayHandPlayers());
                    while (true){
                        String player = scanner.nextLine();

                        try{
                            playerNumber = Integer.parseInt(player);
                        } catch (NumberFormatException e){
                            if (MessageHandler.getChat()){
                                CommandSender.sendMessage("SendMessage|" + player);
                                continue;
                            }
                            System.out.println("Please enter a number");
                            continue;
                        }
                        if (playerNumber > 0 && playerNumber <= Game.getPlayers().size()){
                            break;
                        } else {
                            if (MessageHandler.getChat()){
                                CommandSender.sendMessage("SendMessage|" + playerNumber);
                                continue;
                            }
                            System.out.println("Please enter a valid player");
                        }
                    }
                }
                CommandSender.sendMessage("PlayDrawnCard|yes");
                if (playerNumber != 0){
                    CommandSender.sendMessage("SwitchHand|" + Game.getPlayers().get(playerNumber-1).getName());
                }
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
