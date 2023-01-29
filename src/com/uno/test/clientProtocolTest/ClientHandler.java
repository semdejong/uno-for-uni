package com.uno.test.clientProtocolTest;

import com.uno.server.MessageHandler;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private BufferedReader in;
    private BufferedWriter out;
    private String ClientName;
    private MessageHandler messageHandler;
    private Socket socket;

    public ClientHandler(Socket socketArg){
        socket = socketArg;
        try {
            in = new BufferedReader(new InputStreamReader(socketArg.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socketArg.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("error occurred.");
        }
    }

    public void run() {
        receiveMessage();
    }
    public void receiveMessage(){
        try {
            String msg = in.readLine();
            while (true){
                System.out.println(msg);
                msg = in.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
