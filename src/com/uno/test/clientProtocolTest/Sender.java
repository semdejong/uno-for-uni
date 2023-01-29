package com.uno.test.clientProtocolTest;

import com.uno.client.controller.FlowController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Sender extends Thread{
    public BufferedWriter out;
    public BufferedReader in;
    public void run(){
        try{
            Socket connection = new Socket("localhost", 1234);
            System.out.println("Connected to server");
            FlowController.comStarted = true;;
            out = new BufferedWriter(new java.io.OutputStreamWriter(connection.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }catch (IOException e) {
            System.out.println("Could not connect to server");
        }
        System.out.println("done");
    }
    public void sendMessage(String message){
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}