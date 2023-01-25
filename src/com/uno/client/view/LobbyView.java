package com.uno.client.view;

public class LobbyView {
    public static void updateView(String[] playerNames){
        System.out.println("Lobby SMUNO");
        for (String playerName: playerNames){
            System.out.println(playerName);
        }
    }


}
