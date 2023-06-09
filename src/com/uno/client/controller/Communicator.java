package com.uno.client.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Communicator{
    private final String ip;
    private final int port;

    public Communicator(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run(){
        try{
            Socket connection = new Socket(ip, port);
            System.out.println("Connected to server");
            FlowController.comStarted = true;
            BufferedWriter outgoing = new BufferedWriter(new java.io.OutputStreamWriter(connection.getOutputStream()));
            BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            CommandSender.setOut(outgoing);
            MessageReceiver receiver = new MessageReceiver(incoming);
            receiver.start();
        }catch (IOException e) {
            System.out.println("Could not connect to server");
        }
    }
}
