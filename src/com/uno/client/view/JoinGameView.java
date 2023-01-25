package com.uno.client.view;

import com.uno.client.controller.CommandSender;

import java.util.Scanner;

public class JoinGameView {

    public static void updateView(){
        System.out.println("What is the pin of the game you want to join?");
        inputView();
    }

    public static void inputView(){
        Scanner scanner = new Scanner(System.in);
        CommandSender.sendMessage("JOINGAME|"+scanner.nextLine());
    }
}
