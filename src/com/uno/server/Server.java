package com.uno.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static ServerSocket listener;
    static Socket connection;

    public void startServer(){
        try(ServerSocket ssock = new ServerSocket(1728);){
            while(true){
                Socket sock = ssock.accept();
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
        Server hi = new Server();
        hi.startServer();
    }


}
