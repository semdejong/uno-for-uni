package com.uno.client.view;

import java.util.Scanner;

public class MainMenuView {

    public static int updateView(){
        System.out.println("SMUNO");
        System.out.println("");
        System.out.println("Choose a option by number:");
        System.out.println("1) ............. Create a game.");
        System.out.println("2) ............. Join a game.");
        System.out.println("3) ............. Join game with an AI.");
        System.out.println("4) ............. exit.");
        System.out.println("");

        return inputView();
    }

    public static int inputView(){

        boolean validChoice = false;

        while(!validChoice) {
            System.out.println("Make a choice:");
            Scanner scanner = new Scanner(System.in);

            int choice = scanner.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("Choose other option!");
                continue;
            }

            validChoice = true;

            return choice;
        }

        return 1;
    }
}
