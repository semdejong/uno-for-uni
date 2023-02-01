package com.uno.client.view;

import com.uno.client.controller.*;
import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Hand;

import java.util.Scanner;

public class JumpInView extends Thread{

    private static Hand hand;
    public static boolean played = false;

    public static void updateView() {
        hand = PlayerController.getOwnPlayer().getHand();
        played = false;
        System.out.println("You have a card in your hand that you could play using jump-in");
        System.out.println("Your opponents have the following amount of cards:\n" + GameController.displayHandPlayers());
        System.out.println("The card on the top of the stack is:\n" + CommandHandler.colorRizeString(Game.getActiveCard().toStringPerson(), Game.getActiveCard().getColor()));
        System.out.println("Your hand is:\n" + hand.toString());
        System.out.println("Type which card you want to play (1-"+ hand.getHandSize()+ ") but be quick");
        JumpInView jumpInView = new JumpInView();
        jumpInView.start();
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        int playerNumber = 0;
        while (true){
            if (scanner.hasNext() && !played) {
                input = scanner.nextLine();
            } else {
                System.out.println("sorry you were too slow");
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

