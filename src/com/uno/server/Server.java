package com.uno.server;

import com.uno.client.controller.Communicator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    static ServerSocket listener;
    static Socket connection;

    public void run(){
        try(ServerSocket ssock = new ServerSocket(1728);){
            System.out.println("Server started");
            while(true){
                Socket sock = ssock.accept();
                System.out.println("Client connected");
                ClientHandler handler = new ClientHandler(this, sock);
                handler.start();

            }
        }catch (IOException e){
            System.out.println("error occurred");
            e.printStackTrace();
        }
    }

    public void handShakeAndMessage(){

    }

    public static void main(String[] args){
        Communicator communicator = new Communicator("localhost", 1728);
        Server hi = new Server();
        hi.start();
        communicator.start();
    }


}
