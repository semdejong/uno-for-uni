package com.uno.client.Computers;

import com.uno.client.controller.CommandHandler;
import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;
import com.uno.client.controller.PlayerController;
import com.uno.client.model.Card;
import com.uno.client.model.Hand;

import java.util.HashMap;
import java.util.Map;

public class ComputerUtils {

    public static boolean playCard(Card card){
        if(!CommandHandler.playable(card)){
            return false;
        }

        if(card.getColor().equals(Card.cardColor.BLACK)){
            card.setColor(Card.cardColor.valueOf(mostColorInHand(PlayerController.getOwnPlayer().getHand()))); //if computer does not change the color it self it will be automatically changed to most common color in hand
        }

        CommandSender.sendMessage("PlayCard|" + card.toString());
        PlayerController.getOwnPlayer().getHand().removeCard(card);

        return true;
    }


    public static boolean playDrawnCard(Card card){
        if(CommandHandler.playable(card)){
            if(card.getColor().equals(Card.cardColor.BLACK)){
                CommandSender.sendMessage("PlayDrawnCard|true|"+mostColorInHand(PlayerController.getOwnPlayer().getHand()));
                return true;
            }
            CommandSender.sendMessage("PlayDrawnCard|true");
            return true;
        }else{
            CommandSender.sendMessage("PlayDrawnCard|false");
            PlayerController.getOwnPlayer().getHand().addCard(card);
            return false;
        }
    }

    public static void drawCard(){
        CommandSender.sendMessage("DrawCard");
        MessageHandler.drawnCard = true;
    }

    public static String mostColorInHand(Hand hand){
        HashMap<String, Integer> colors = new HashMap<>();
        colors.put("RED", 0);
        colors.put("BLUE", 0);
        colors.put("GREEN", 0);
        colors.put("YELLOW", 0);

        for(Card cardFromLoop :PlayerController.getOwnPlayer().getHand().getCards()){
            if(cardFromLoop.getColor().equals(Card.cardColor.BLUE)){
                colors.put("BLUE", colors.get("BLUE") + 1);
            }

            if(cardFromLoop.getColor().equals(Card.cardColor.RED)){
                colors.put("RED", colors.get("RED") + 1);
            }

            if(cardFromLoop.getColor().equals(Card.cardColor.GREEN)){
                colors.put("GREEN", colors.get("GREEN") + 1);
            }

            if(cardFromLoop.getColor().equals(Card.cardColor.YELLOW)){
                colors.put("YELLOW", colors.get("YELLOW") + 1);
            }
        }

        String winnerColor = "";
        int amount = 0;

        for(Map.Entry<String, Integer> values: colors.entrySet()){
            if(values.getValue() > amount){
                winnerColor = values.getKey();
                amount = values.getValue();
            }
        }

        return winnerColor;
    }
}
