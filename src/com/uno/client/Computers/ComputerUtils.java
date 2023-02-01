package com.uno.client.Computers;

import com.uno.client.controller.CommandHandler;
import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;
import com.uno.client.controller.PlayerController;
import com.uno.client.model.Card;

import java.util.HashMap;
import java.util.Map;

public class ComputerUtils {

    public static boolean playCard(Card card){
        if(!CommandHandler.playable(card)){
            return false;
        }
        if(card.getColor().equals(Card.cardColor.BLACK)){
            Card.cardColor color = Card.cardColor.valueOf(mostColorInHand());

            if(color.equals(Card.cardColor.BLACK)){
                color = Card.cardColor.YELLOW; //Make sure to never change the color to black.
            }

            card.setColor(color); //if computer does not change the color itself it will be automatically changed to most common color in hand
        }

        CommandSender.sendMessage("PlayCard|" + card);

        PlayerController.getOwnPlayer().getHand().removeCard(card);

        return true;
    }


    public static void playDrawnCard(Card card){
        if(CommandHandler.playable(card)){
            if(card.getColor().equals(Card.cardColor.BLACK)){
                CommandSender.sendMessage("PlayDrawnCard|true|"+mostColorInHand());
                return;
            }
            CommandSender.sendMessage("PlayDrawnCard|true");
        }else{
            CommandSender.sendMessage("PlayDrawnCard|false");
            PlayerController.getOwnPlayer().getHand().addCard(card);
        }
    }

    public static void drawCard(){
        CommandSender.sendMessage("DrawCard");
        MessageHandler.drawnCard = true;
    }

    public static String mostColorInHand(){
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

        if(winnerColor.equals("")){
            winnerColor = "RED"; //prevent from returning ""
        }

        return winnerColor;
    }
}
