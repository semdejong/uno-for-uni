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
        System.out.println("|   3) ............. Advanced computer.   |");
        System.out.println("|                                         |");
        System.out.println("|-----------------------------------------|");

        System.out.println("");
        System.out.println("Make a choice:");
    }

    public static int inputView(){

        Scanner scanner = new Scanner(System.in);

        while(true){
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please type in a number between 1 and 3");
                continue;
            }
            if(choice < 1 || choice > 3){
                System.out.println("Please type in a number between 1 and 3");
                continue;
            }

            return choice;
        }
    }
}
