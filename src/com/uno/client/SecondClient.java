package com.uno.client;

import com.uno.client.controller.FlowController;

import java.util.Scanner;

public class SecondClient {
    public static void main(String[] args){
        System.out.println("Put in a UNO server address (ip:port), type (own:port) for own server on that port.");
        Scanner scanner = new Scanner(System.in);
        FlowController.entryPoint(scanner.nextLine());
    }
}
