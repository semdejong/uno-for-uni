package com.uno.client.view;

import java.util.Scanner;

public class PlayWithComputerPlayerView {
    public static void updateView(){
        System.out.println("You are playing with a computer player");
        System.out.println("Which computer player do you want to use:");
        System.out.println("|-----------------------------------------|");
        System.out.println("|                                         |");
        System.out.println("|   1) ............. Basic computer.      |");
        System.out.println("|   2) ............. Medium computer.     |");
        System.out.println("|                                         |");
        System.out.println("|-----------------------------------------|");

        System.out.println("");
        System.out.println("Make a choice:");
    }

    public static int inputView(){

        Scanner scanner = new Scanner(System.in);

        while(true){
            int choice = scanner.nextInt();
            if(choice < 1 || choice > 2){
                System.out.println("Invalid choice, try again:");
                continue;
            }

            return choice;
        }
    }
}
