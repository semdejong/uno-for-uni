package com.uno.client.view;

import com.uno.client.controller.CommandSender;

import java.util.Scanner;

public class WaitStartView extends Thread{
    public static boolean started = false;
    public static boolean enoughPlayers = false;
    public static void updateView(){
        System.out.println("Waiting for the game to start...");
        Thread thread = new WaitStartView();
        thread.start();

    }
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            if (enoughPlayers){
                System.out.println("Type 'start' to start the game.");
                break;
            }
        }
        while (!started){
            if (scanner.hasNext()){
                if (scanner.nextLine().equals("start")){
                    CommandSender.sendMessage("Start");
                    started = true;
                }
            }
        }
    }

}
