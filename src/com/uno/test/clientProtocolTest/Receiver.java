package com.uno.test.clientProtocolTest;

import java.io.BufferedReader;
import java.io.IOException;

public class Receiver extends Thread {
    private BufferedReader reader;
    public Receiver(BufferedReader reader){
        this.reader = reader;
    }

    public void run() {
        try{
            String msg = reader.readLine();
            while (true) {
                System.out.println("client received: " + msg);
                msg = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while connecting to the server.");
            System.out.println(e.toString());
        }

    }
}
