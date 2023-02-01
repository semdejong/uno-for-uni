package com.uno.client.controller;

import com.uno.client.Computers.AI;
import com.uno.client.model.Player;

public class PlayerController {
    private static Player ownPlayer;


    public static void createOwnPlayer(String name){
        ownPlayer = new Player(name);
    }

    public static Player getOwnPlayer(){
        return ownPlayer;
    }

    public static void setOwnPlayer(Player player){
        ownPlayer = player;
    }

    public static boolean isComputerPlayer(){
        return AI.class.isAssignableFrom(ownPlayer.getClass());
    }

}
