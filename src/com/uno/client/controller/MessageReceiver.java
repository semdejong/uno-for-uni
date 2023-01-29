package com.uno.client.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class MessageReceiver extends Thread{

    private BufferedReader reader;
    private int nullCount = 0;

    public MessageReceiver(BufferedReader reader){
        this.reader = reader;
    }
    public void run() {
        try{
            String msg = reader.readLine();
            while (true) {
                MessageHandler.receiveMessage(msg);
                msg = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while connecting to the server.");
            System.out.println(e.toString());
        }

    }
}
