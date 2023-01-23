package com.uno.server;

public class MessageHandler {

    //public String client;
    public void receiveMessage(String message, String sender) {
        String[] parts = message.split("|");
        switch (parts[0]){
            case "connect":
                //do something
                break;
            case "RequestGame":
                //do something
                break;
            case "JoinGame":
                //do something
                break;
            case "Start":
                //do something
                break;
            case "PlayCard":
                //do something
                break;
            case "DrawCard":
                //do something
                break;
            case "LeaveGame":
                //do something
                break;
            case "SendMessage":
                //do something
                break;
        }
    }

    public void sendMessage(String message, String recipient) {

    }


}
