package com.uno.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    BufferedReader in;
    BufferedWriter out;
    public ClientHandler(Server serverArg, Socket socketArg){
        try {
            in = new BufferedReader(new InputStreamReader(socketArg.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socketArg.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("error occurred.");
        }
    }

    public void run() {
        try{
            String msg = in.readLine();
            while(msg != null){
                System.out.println(msg);
                msg = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error occurred.");
        }
    }

    public void receiveMessage(){
        try {
            String msg = in.readLine();
            while (true) {
                MessageHandler messageHandler = new MessageHandler();
               messageHandler.receiveMessage(msg, this);
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

    public void sendError(Error error){
        sendMessage("ERROR|"+error.toString());
    }

    public void closeConnection(){
        Server.closeConnection(this);
    }

    public BufferedReader getIn(){
        return in;
    }

    public BufferedWriter getOut(){
        return out;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
}
