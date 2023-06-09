package com.uno.client.controller;

import com.uno.client.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandHandler {
    public static final String[] colors = {"RED", "BLUE", "GREEN", "YELLOW", "BLACK"};
    public static final String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final int STARTING_CARDS_AMOUNT = 7;

    public static Card makeCard(String[] parts) {
        Card.cardColor color = null;
        Card.cardType type = null;
        int value = 0;
        if (Arrays.asList(colors).contains(parts[0].toUpperCase())){
            color = Card.cardColor.valueOf(parts[0].toUpperCase());
        }
        if (Arrays.asList(numbers).contains(parts[1])){
            type = Card.cardType.NUMBER;
            value = Integer.parseInt(parts[1]);
        }
        switch (parts[1].toUpperCase()){
            case "SKIP":
                value = 22;
                type = Card.cardType.SKIP;
                break;
            case "REVERSE":
                type = Card.cardType.REVERSE;
                value = 21;
                break;
            case "DRAW_TWO":
                type = Card.cardType.DRAW_TWO;
                value = 23;
                break;
            case "WILD":
                type = Card.cardType.WILD;
                value = 54;
                break;
            case "WILD_DRAW_FOUR":
                type = Card.cardType.WILD_DRAW_FOUR;
                value = 55;
                break;
        }
        return new Card(type, color, value);
    }
    public static boolean playable(Card card){
        if (PlayerController.getOwnPlayer().getHand().getCards() == null){
            return true;
        } else if (card.getType() == Card.cardType.WILD_DRAW_FOUR) {
            for (Card card1 : PlayerController.getOwnPlayer().getHand().getCards()){
                if (card1.getColor() == Game.getActiveCard().getColor()){
                    return false;
                }
            }
            return true;
        } else {
            return card.getColor().equals(Game.getActiveCard().getColor()) || card.getNumber() == Game.getActiveCard().getNumber() || card.getType().equals(Card.cardType.WILD);
        }
    }

    public static void roundOver(){
        for(Player player : Game.getPlayers()){
            if(!player.getName().equals(PlayerController.getOwnPlayer().getName())){
                Hand hand = new Hand();
                ArrayList<Card> handList = new ArrayList<>();

                for(int i =0; i < STARTING_CARDS_AMOUNT; i++){
                    handList.add(new ClosedCard());
                }

                hand.setHand(handList);
                player.setHand(hand);
            }
        }
    }

    public static String colorRizeString(String text, Card.cardColor color){
        if(Card.cardColor.RED.equals(color)){
            return "\u001B[31m" + text + "\u001B[0m";
        }

        if(Card.cardColor.BLUE.equals(color)){
            return "\u001B[34m" + text + "\u001B[0m";
        }

        if(Card.cardColor.YELLOW.equals(color)){
            return "\u001B[33m" + text + "\u001B[0m";
        }

        if(Card.cardColor.GREEN.equals(color)){
            return "\u001B[32m" + text + "\u001B[0m";
        }


        return text;
    }
}
