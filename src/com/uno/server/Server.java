package com.uno.server;

import com.uno.client.controller.Communicator;
import com.uno.server.uno.Game;
import com.uno.server.uno.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private static ServerSocket listener;
    private static Socket connection;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ArrayList<Lobby> lobbies = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();

    public static void main(String[] args){
        Communicator communicator = new Communicator("localhost", 1728);
        Server hi = new Server();
        hi.start();
        communicator.start();
    }
    public void run(){
        try(ServerSocket ssock = new ServerSocket(1728);){
            System.out.println("Server started");
            while(true){
                Socket sock = ssock.accept();
                System.out.println("Client connected");
                ClientHandler handler = new ClientHandler(sock);
                handler.start();

            }
        }catch (IOException e){
            System.out.println("error occurred");
            e.printStackTrace();
        }
    }

    public void handShakeAndMessage(){

    }


    public static ClientHandler getClientHandlerByName(String name){
        for (ClientHandler handler : clients){
            if(handler.getClientName().equals(name)){
                return handler;
            }
        }
        return null;
    }

    public static ArrayList<ClientHandler> getClients() {
        return clients;
    }

    public static void setClients(ArrayList<ClientHandler> clients) {
        Server.clients = clients;
    }

    public static ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public static void setLobbies(ArrayList<Lobby> lobbies) {
        Server.lobbies = lobbies;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void setGames(ArrayList<Game> games) {
        Server.games = games;
    }
}
