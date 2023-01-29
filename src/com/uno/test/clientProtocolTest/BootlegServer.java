package com.uno.test.clientProtocolTest;

import com.uno.server.MessageHandler;
import com.uno.test.serverProtocolTest.BootlegClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BootlegServer extends Thread{
    private BufferedReader in;
    private BufferedWriter out;
    private String ClientName;
    private MessageHandler messageHandler;
    private Socket socket;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234);){
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BootlegServer bootlegServer = new BootlegServer();
            bootlegServer.start();
            System.out.println("Server started.");
        } catch (IOException e) {
            System.out.println("An error occurred while connecting to the server.");
            System.out.println(e.toString());
        }
    }
    public void sendTest(){
        sendMessage("TEST|TEST");
    }
    public void run() {
        try{
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
