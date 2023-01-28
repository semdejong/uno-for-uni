package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.PlayerController;
import com.uno.client.model.Game;

import java.util.Scanner;

public class CreateNewGameView {

    public static void updateView(){
        System.out.println("With how many players do you want to play?");
        inputView();
    }

    public static void inputView(){
        Scanner scanner = new Scanner(System.in);
        CommandSender.sendMessage("REQUESTGAME|"+"m|"+scanner.nextInt());
        Game.addPlayer(PlayerController.getOwnPlayer());
    }
}
