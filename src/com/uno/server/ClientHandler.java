package com.uno.server;

import com.uno.server.uno.Lobby;

import javax.print.attribute.standard.Severity;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private BufferedReader in;
    private BufferedWriter out;
    private String ClientName;

    private MessageHandler messageHandler;

    public ClientHandler(Socket socketArg){
        try {
            in = new BufferedReader(new InputStreamReader(socketArg.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socketArg.getOutputStream()));
            messageHandler = new MessageHandler(this);
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
                messageHandler.receiveMessage(msg);
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

    public Lobby getJoinedLobby(){
        return messageHandler.getLobby();
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
}
