package com.uno.test.serverProtocolTest;

import com.uno.server.Server;

public class ServerProtocolTest {
    public static Server server;
    public static void main(String[] args) throws InterruptedException {
        server = new Server();
        server.setPort(1234);
        server.start();
        Sender sender = new Sender();
        sender.start();
        sender.join();
        Receiver receiver = new Receiver(sender.in);
        receiver.start();
        sender.sendMessage("JOIN#");
        sender.sendMessage("Connect");
        sender.sendMessage("Connect|");
        sender.sendMessage("Connect|mart");
        sender.sendMessage("Connect|mart|");
        sender.sendMessage("Connect|mart|hihi");
        sender.sendMessage("RequestGame");
        sender.sendMessage("RequestGame|");
        sender.sendMessage("RequestGame|pajikfhi");
        sender.sendMessage("RequestGame|p~,~a~,~j~,~i~,~k~,~f~,~h~,~i");
        sender.sendMessage("RequestGame|pajikfhi|");
        sender.sendMessage("RequestGame|pajikfhi|2");
        sender.sendMessage("RequestGame|pajikfhi|2|");
        sender.sendMessage("RequestGame|pajikfhi|2|123");
        sender.sendMessage("JoinGame");
        sender.sendMessage("JoinGame|");
        sender.sendMessage("JoinGame|0");
        sender.sendMessage("JoinGame|123");
        sender.sendMessage("JoinGame|123|");
        sender.sendMessage("JoinGame|123|123");
        sender.sendMessage("Start");
        sender.sendMessage("Start|");
        sender.sendMessage("Start|123");
        sender.sendMessage("PlayCard");
        sender.sendMessage("PlayCard|");
        sender.sendMessage("PlayCard|123");
        sender.sendMessage("PlayCard|123$,$123");
        sender.sendMessage("PlayCard|BLACK$,$WILD");
        sender.sendMessage("PlayCard|BLACK$,$WILD$,$DRAW_FOUR$,$123$,$123");
        sender.sendMessage("PlayCard|BLACK$,$WILD~,~DRAW_FOUR~,~123$,$123");
        sender.sendMessage("PlayCard|BLACK$,$WILD~,~DRAW_FOUR~,~123$,$123|");
        sender.sendMessage("PlayCard|BLACK$,$WILD~,~DRAW_FOUR~,~123$,$123|123");
        sender.sendMessage("DrawCard");
        sender.sendMessage("DrawCard|");
        sender.sendMessage("DrawCard|123");
        sender.sendMessage("PlayDrawnCard");
        sender.sendMessage("PlayDrawnCard|");
        sender.sendMessage("PlayDrawnCard|123");
        sender.sendMessage("PlayDrawnCard|no|");
        sender.sendMessage("PlayDrawnCard|no|123");
        sender.sendMessage("PlayDrawnCard|yes|BLACK");
        sender.sendMessage("PlayDrawnCard|no|BLACK");
        sender.sendMessage("SwitchHand");
        sender.sendMessage("SwitchHand|");
        sender.sendMessage("SwitchHand|123");
        sender.sendMessage("SwitchHand|123|");
        sender.sendMessage("SwitchHand|123|123");
        sender.sendMessage("SendMessage");
        sender.sendMessage("SendMessage|");
        sender.sendMessage("SendMessage|123");
        sender.sendMessage("SendMessage|123|");
        sender.sendMessage("SendMessage|123|123");
        sender.sendMessage("LeaveGame");
        sender.sendMessage("LeaveGame|");
        sender.sendMessage("LeaveGame|123");
        sender.sendMessage("LeaveServer");
        sender.sendMessage("LeaveServer|");
        sender.sendMessage("LeaveServer|123");
    }

}

