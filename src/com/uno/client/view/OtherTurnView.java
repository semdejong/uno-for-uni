package com.uno.client.view;

import com.uno.client.model.Card;

public class OtherTurnView {
    public static void updateView(String playerName, Card card){
        if (card == null){
            System.out.println(playerName + " drew a card");
        } else {
            System.out.println(playerName + " played: " + card.toString());
        }
    }
}
