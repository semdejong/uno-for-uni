package com.uno.test.serverProtocolTest;

import com.uno.server.Server;

import java.util.Scanner;

public class BootlegClient {
    public static void main(String[] args) throws InterruptedException {
        Sender sender = new Sender();
        sender.start();
        sender.join();
        Receiver receiver = new Receiver(sender.in);
        receiver.start();
        Scanner scanner = new Scanner(System.in);
        String msg;
        while (true){
            msg = scanner.nextLine();
            sender.sendMessage(msg);
        }
    }
}
