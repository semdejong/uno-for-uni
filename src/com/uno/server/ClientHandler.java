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
}
