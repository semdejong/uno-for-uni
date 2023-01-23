package com.uno.client.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class Communicator {
    public static final String ipAddress = "192.168.178.31";
    public static void main(String[] args) {
        Communicator hi = new Communicator();
        hi.connect(ipAddress, 1728);
    }
    public void connect(String ip, int port) {
        try{
            Socket connection = new Socket(ip, port);
            System.out.println("Connected to server");
            BufferedWriter outgoing = new BufferedWriter(new java.io.OutputStreamWriter(connection.getOutputStream()));
            outgoing.write("je bent een flikker");
        } catch (IOException e) {
            System.out.println("An error occurred while connecting to the server.");
            System.out.println(e.toString());
        }

    }
}
