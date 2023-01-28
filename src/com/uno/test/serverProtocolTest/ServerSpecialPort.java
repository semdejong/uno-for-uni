package com.uno.test.serverProtocolTest;

import com.uno.server.Server;

public class ServerSpecialPort {


    public static Server server;

    public static void main(String[] args) {
        server = new Server();
        server.setPort(1234);
        server.start();
    }
}
