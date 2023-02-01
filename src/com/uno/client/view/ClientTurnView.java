package com.uno.client.view;

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
        System.out.println("Your opponents have the following amount of cards:\n" + GameController.displayHandPlayers());
        System.out.println("The card on the top of the stack is:\n" + CommandHandler.colorRizeString(Game.getActiveCard().toStringPerson(), Game.getActiveCard().getColor()));
        System.out.println("Your hand is:\n" + hand.toString());
        System.out.println("Type which card you want to play (1-"+ hand.getHandSize()+ ") or type draw to draw a card");
    }
    public static void inputView(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine();
            int playerNumber = 0;
            if (input.equalsIgnoreCase("draw")){
                MessageHandler.drawnCard = true;
                CommandSender.sendMessage("DrawCard");
                return;
            }
            try {
                int number = Integer.parseInt(input);
                if (number < 1 || number > hand.getHandSize()){
                    if (MessageHandler.getChat()){
                        CommandSender.sendMessage("SendMessage|" + number);
                        continue;
                    }
                    System.out.println("Please enter a number between 1 and " + hand.getHandSize());
                } else {
                    if (CommandHandler.playable(hand.getCards().get(number-1))){
                        if (hand.getCards().get(number-1).getColor().equals(Card.cardColor.BLACK)){
                            System.out.println("What color do you want to change it to?");
                            while (true){
                                String color = scanner.nextLine();
                                if (color.equalsIgnoreCase("red") || color.equalsIgnoreCase("blue") || color.equalsIgnoreCase("green") || color.equalsIgnoreCase("yellow")){
                                    hand.getCards().get(number-1).setColor(Card.cardColor.valueOf(color.toUpperCase()));
                                    break;
                                } else {
                                    if (MessageHandler.getChat()){
                                        CommandSender.sendMessage("SendMessage|" + color);
                                        continue;
                                    }
                                    System.out.println("Please enter a valid color");
                                }
                            }
                        }
                        if (MessageHandler.SevenO && hand.getCards().get(number-1).getNumber() == 7){
                            System.out.println("You played a 7 please choose a player with whom to change hands");
                            System.out.println("The players have the following hands (these can be incorrect):");
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
                        CommandSender.sendMessage("PlayCard|" + hand.getCards().get(number-1).toString());
                        if (playerNumber != 0){
                            CommandSender.sendMessage("SwitchHand|" + Game.getPlayers().get(playerNumber-1).getName());
                        }
                        hand.removeCard(hand.getCards().get(number-1));
                        return;
                    } else{
                        System.out.println("You can't play that card");
                    }

                }
            } catch (NumberFormatException e){
                if (MessageHandler.getChat()){
                    CommandSender.sendMessage("SendMessage|" + input);
                    continue;
                }
                System.out.println("Please enter a number");
            }
        }
    }
}
