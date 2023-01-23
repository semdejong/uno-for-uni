package com.uno.client.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class Communicator extends Thread{
    private  String ip;
    private int port;

    public Communicator(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        try{
            Socket connection = new Socket(ip, port);
            System.out.println("Connected to server");
            BufferedWriter outgoing = new BufferedWriter(new java.io.OutputStreamWriter(connection.getOutputStream()));
            Scanner i = new Scanner(System.in);
            while(true) {
                outgoing.write(i.nextLine());
                outgoing.newLine();
                outgoing.flush();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while connecting to the server.");
            System.out.println(e.toString());
        }

    }
}
