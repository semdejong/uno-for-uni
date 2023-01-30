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
        while (true){
            String name = scanner.nextLine();
            if (!name.equals("")){
                CommandSender.sendMessage("connect|"+name);
                break;
            }
        }
    }
}
