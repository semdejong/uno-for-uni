package com.uno.server;

import com.uno.server.uno.Lobby;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private BufferedReader in;
    private BufferedWriter out;
    private String ClientName;
    private MessageHandler messageHandler;
    private final Socket socket;

    // Creating a new client handler.
    public ClientHandler(Socket socketArg){
        socket = socketArg;
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
    /**
     * It reads a line from the input stream, and then passes it to the message handler
     */
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

    /**
     * It writes the message to the output stream, adds a new line, and flushes the stream
     *
     * @param message The message to be sent to the server
     */
    public void sendMessage(String message){
        try {
            System.out.println("\u001B[31m[SERVER-->CLIENT] " + message + "\u001B[0m");
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function sends an error message to the client.
     *
     * @param error The error to send.
     */
    public void sendError(Error error){
        sendMessage("ERROR|"+error.toString());
    }

    /**
     * It sends an error message to the client
     *
     * @param error The error code.
     * @param message The message to send to the client.
     */
    public void sendError(Error error, String message){
        sendMessage("ERROR|"+error.toString()+"|"+message);
    }

    /**
     * Close the connection to the server.
     */
    public void closeConnection(){
        Server.closeConnection(this);
    }

    /**
     * This function returns the BufferedReader object that was created in the constructor.
     *
     * @return The BufferedReader in.
     */
    public BufferedReader getIn(){
        return in;
    }

    /**
     * This function returns the BufferedWriter object that is used to write to the client.
     *
     * @return The BufferedWriter object out.
     */
    public BufferedWriter getOut(){
        return out;
    }

    /**
     * This function returns the name of the client
     *
     * @return The client name.
     */
    public String getClientName() {
        return ClientName;
    }

    /**
     * It returns the lobby that the client is currently in
     *
     * @return The lobby that the user is currently in.
     */
    public Lobby getJoinedLobby(){
        return messageHandler.getLobby();
    }

    /**
     * This function sets the client name
     *
     * @param clientName The name of the client.
     */
    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    /**
     * This function returns the message handler.
     *
     * @return The messageHandler object.
     */
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * This function returns the socket.
     *
     * @return The socket object.
     */
    public Socket getSocket() {
        return socket;
    }
}
