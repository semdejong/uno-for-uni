package com.uno.client.view;

import com.uno.client.controller.PlayerController;
import com.uno.client.model.Card;

public class ForcedDrawView {
    public static void updateView(Card card){
        System.out.println("You have been forced to draw " + card.toStringPerson());
        PlayerController.getOwnPlayer().addCard(card);
    }
}
