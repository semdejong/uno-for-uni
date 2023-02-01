package com.uno.client.view;

import com.uno.client.controller.CommandSender;
import com.uno.client.controller.MessageHandler;

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
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            if (enoughPlayers){
                System.out.println("Type 'start' to start the game.");
                break;
            }
        }
        while (!started){
            if (scanner.hasNext()){
                String input = scanner.nextLine();
                if (input.equals("start")){
                    CommandSender.sendMessage("Start");
                    started = true;
                    break;
                } else {
                    if (MessageHandler.getChat()){
                        CommandSender.sendMessage("SendMessage|" + input);
                        continue;
                    }
                    System.out.println("Type 'start' to start the game.");
                }
            }
        }
    }

}
