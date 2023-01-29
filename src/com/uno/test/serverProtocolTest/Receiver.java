package com.uno.test.serverProtocolTest;

import com.uno.client.controller.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;

public class Receiver extends Thread {
    private BufferedReader reader;
    public Receiver(BufferedReader reader){
        this.reader = reader;
    }
    private int nullCount = 0;

    public void run() {
        try{
            String msg = reader.readLine();
            while (true) {
                System.out.println(msg);
                if (msg.equals("")){
                    System.out.println("test");
                    nullCount++;
                    if (nullCount > 5){
                        System.out.println("Server is not responding. Please try again later.");
                        System.exit(0);
                    }
                } else {
                    nullCount = 0;
                }
                System.out.println("client received: " + msg);
                msg = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while connecting to the server.");
            System.out.println(e.toString());
        }

    }
}
