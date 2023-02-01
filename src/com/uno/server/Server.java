package com.uno.server;

import com.uno.server.uno.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{

    private static final ArrayList<ClientHandler> clients = new ArrayList<>();
    private static final ArrayList<Lobby> lobbies = new ArrayList<>();
    private static int port = 3333;

    public static void main(String[] args){
        Server server = new Server();
        server.start();
    }


    /**
     * The server starts a new thread for each client that connects to it
     */
    public void run(){
        try(ServerSocket sSock = new ServerSocket(port)){
            System.out.println("Server started");
            while(true){
                Socket sock = sSock.accept();
                System.out.println("Client connected");
                ClientHandler handler = new ClientHandler(sock);
                handler.start();
                handler.setUncaughtExceptionHandler((t, e) -> {
                    System.out.println("exception " + e + " from thread " + t);
                    e.printStackTrace();
                    handler.getMessageHandler().leaveServer();
                });
                clients.add(handler);
            }
        }catch (IOException e){
            System.out.println("error occurred");
            e.printStackTrace();
        }
    }

    /**
     * This function sets the port to the chosen port
     *
     * @param chosenPort The port you want to use.
     */
    public static void setPort(int chosenPort){
        port = chosenPort;
    }


    /**
     * For each client, send the message.
     *
     * @param message The message to be sent to all clients.
     */
    public static void broadCast(String message){
        for (ClientHandler handler : clients){
            handler.sendMessage(message);
        }
    }

    /**
     * Send a message to a specific client.
     *
     * @param clientToReceive The client that will receive the message.
     * @param message The message to be sent.
     */
    public static void sendMessage(ClientHandler clientToReceive, String message){
        clientToReceive.sendMessage(message);
    }

    /**
     * Send a message to a client by name.
     *
     * @param clientNameToReceive The name of the client you want to send the message to.
     * @param message The message to be sent to the client.
     */
    public static void sendMessage(String clientNameToReceive, String message){
        getClientHandlerByName(clientNameToReceive).sendMessage(message);
    }

    /**
     * It closes the connection to the client
     *
     * @param clientToClose The client to close the connection with.
     */
    public static void closeConnection(ClientHandler clientToClose) {
        clients.remove(clientToClose);
        try {
            clientToClose.getSocket().close();
            clientToClose.getIn().close();
            clientToClose.getOut().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (clientToClose.isAlive()){
            clientToClose.interrupt();
        }
    }


    /**
     * It loops through all the clients and returns the client handler that has the same name as the name passed in
     *
     * @param name The name of the client you want to get the ClientHandler for.
     * @return A ClientHandler object.
     */
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

    /**
     * It returns an ArrayList of Lobby objects
     *
     * @return The ArrayList of lobbies.
     */
    public static ArrayList<Lobby> getLobbies() {
        return lobbies;
    }
    /**
     * It adds a lobby to the list of lobbies
     *
     * @param lobby The lobby to add to the list of lobbies.
     */
    public static void addLobby(Lobby lobby){
        lobbies.add(lobby);
    }

}
