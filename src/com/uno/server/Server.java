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
        Server server = new Server();
        server.start();
    }


    /**
     * The server starts a new thread for each client that connects to it
     */
    public void run(){
        try(ServerSocket ssock = new ServerSocket(port);){
            System.out.println("Server started");
            while(true){
                Socket sock = ssock.accept();
                System.out.println("Client connected");
                ClientHandler handler = new ClientHandler(sock);
                handler.start();
                handler.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                    public void uncaughtException(Thread t, Throwable e) {
                        System.out.println("exception " + e + " from thread " + t);
                        e.printStackTrace();
                        handler.getMessageHandler().leaveServer();
                    }
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
     * For each client, if the client is not the exception, send the message to the client.
     *
     * @param message The message to be sent to all clients.
     * @param exception The client that sent the message.
     */
    public static void broadCast(String message, ClientHandler exception){
        for (ClientHandler handler : clients){
            if(handler.equals(exception)){
                continue;
            }
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
     * Sends an error to a client.
     *
     * @param clientToReceive The client that will receive the message.
     * @param error The error to send.
     */
    public static void sendError(ClientHandler clientToReceive, Error error){
        clientToReceive.sendError(error);
    }

    /**
     * It sends an error to a client
     *
     * @param clientToReceive The client that will receive the error.
     * @param error The error code to send.
     * @param message The message to be sent to the client.
     */
    public static void sendError(ClientHandler clientToReceive, Error error, String message){
       clientToReceive.sendError(error, message);
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
     * This function returns the listener variable.
     *
     * @return The listener variable is being returned.
     */
    public static ServerSocket getListener() {
        return listener;
    }

    /**
     * This function sets the listener variable to the value of the listener parameter.
     *
     * @param listener The server socket that listens for incoming connections.
     */
    public static void setListener(ServerSocket listener) {
        Server.listener = listener;
    }

    /**
     * This function returns the connection variable.
     *
     * @return The connection variable is being returned.
     */
    public static Socket getConnection() {
        return connection;
    }

    /**
     * This function sets the connection variable to the connection variable passed in
     *
     * @param connection The socket that the server is connected to.
     */
    public static void setConnection(Socket connection) {
        Server.connection = connection;
    }

    /**
     * This function returns the port number.
     *
     * @return The port number
     */
    public static int getPort() {
        return port;
    }

    /**
     * It returns the ArrayList of ClientHandlers
     *
     * @return An ArrayList of ClientHandler objects.
     */
    public static ArrayList<ClientHandler> getClients() {
        return clients;
    }

    /**
     * This function sets the clients variable to the clients variable passed in.
     *
     * @param clients This is an ArrayList of ClientHandler objects.
     */
    public static void setClients(ArrayList<ClientHandler> clients) {
        Server.clients = clients;
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
     * It sets the lobbies variable to the lobbies parameter
     *
     * @param lobbies An ArrayList of all the lobbies on the server.
     */
    public static void setLobbies(ArrayList<Lobby> lobbies) {
        Server.lobbies = lobbies;
    }

    /**
     * This function returns the games ArrayList.
     *
     * @return The games arraylist
     */
    public static ArrayList<Game> getGames() {
        return games;
    }

    /**
     * This function sets the games variable to the games variable passed in.
     *
     * @param games This is an ArrayList of Game objects.
     */
    public static void setGames(ArrayList<Game> games) {
        Server.games = games;
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
