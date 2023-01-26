package com.uno.client.controller;

import com.uno.client.model.Card;
import com.uno.client.model.Game;
import com.uno.client.model.Hand;

import java.util.Arrays;

public class CommandHandler {
    public static final String[] colors = {"RED", "BLUE", "GREEN", "YELLOW", "BLACK"};
    public static final String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

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
                type = Card.cardType.SKIP;
                value = -1;
                break;
            case "REVERSE":
                type = Card.cardType.REVERSE;
                value = -2;
                break;
            case "DRAW_TWO":
                type = Card.cardType.DRAW_TWO;
                value = -3;
                break;
            case "WILD":
                type = Card.cardType.WILD;
                value = -4;
                break;
            case "WILD_DRAW_FOUR":
                type = Card.cardType.WILD_DRAW_FOUR;
                value = -5;
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
}
