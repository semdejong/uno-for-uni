package com.uno.client.controller;

import java.io.BufferedWriter;
import java.io.IOException;

public class CommandSender {

    private static BufferedWriter out;


    public static void setOut(BufferedWriter outgoing){
        out = outgoing;
    }

    public static void sendMessage(String message){
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
