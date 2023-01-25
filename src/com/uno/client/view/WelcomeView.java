package com.uno.client.view;

import com.uno.client.controller.CommandSender;

import java.util.Scanner;

public class WelcomeView {
    public static void updateView(String name){
        System.out.println("Welcome " + name);
    }

    public static void inputView(){
        System.out.println("What is your name?");
        Scanner scanner = new Scanner(System.in);

        CommandSender.sendMessage("Connect|"+scanner.nextLine());
    }
}
