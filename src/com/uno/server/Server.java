package com.uno.server;

import com.uno.client.controller.Communicator;
import com.uno.server.uno.Game;
import com.uno.server.uno.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{

    private static ServerSocket listener;
    private static Socket connection;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ArrayList<Lobby> lobbies = new ArrayList<>();
    private static ArrayList<Game> games = new ArrayList<>();
    private static int port = 3333;

    public static void main(String[] args){
        Server hi = new Server();
        hi.start();
    }
    public void run(){
        try(ServerSocket ssock = new ServerSocket(port);){
            System.out.println("Server started");
            while(true){
                Socket sock = ssock.accept();
                System.out.println("Client connected");
                ClientHandler handler = new ClientHandler(sock);
                handler.start();
                clients.add(handler);
            }
        }catch (IOException e){
            System.out.println("error occurred");
            e.printStackTrace();
        }
    }

    public static void setPort(int chosenPort){
        port = chosenPort;
    }

    //Send message to all connected clients
    public static void broadCast(String message){
        for (ClientHandler handler : clients){
            handler.sendMessage(message);
        }
    }

    //Send message to all clients, but the exception
    public static void broadCast(String message, ClientHandler exception){
        for (ClientHandler handler : clients){
            if(handler.equals(exception)){
                continue;
            }
            handler.sendMessage(message);
        }
    }

    //Send message to a certain client
    public static void sendMessage(ClientHandler clientToReceive, String message){
        clientToReceive.sendMessage(message);
    }
    public static void sendMessage(String clientNameToReceive, String message){
        getClientHandlerByName(clientNameToReceive).sendMessage(message);
    }

    public static void sendError(ClientHandler clientToReceive, Error error){
        clientToReceive.sendError(error);
    }
    public static void sendError(ClientHandler clientToReceive, Error error, String message){
       clientToReceive.sendError(error, message);
    }
    public static void closeConnection(ClientHandler clientToClose){
        clientToClose.stop();
        clients.remove(clientToClose);
    }


    public static ClientHandler getClientHandlerByName(String name){
        for (ClientHandler handler : clients){

            if(handler.getClientName() == null){
                continue;
            }

            if(handler.getClientName().equals(name)){
                return handler;
            }
        }
        return null;
    }

    public static ServerSocket getListener() {
        return listener;
    }

    public static void setListener(ServerSocket listener) {
        Server.listener = listener;
    }

    public static Socket getConnection() {
        return connection;
    }

    public static void setConnection(Socket connection) {
        Server.connection = connection;
    }

    public static int getPort() {
        return port;
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

    public static void addLobby(Lobby lobby){
        lobbies.add(lobby);
    }
}
