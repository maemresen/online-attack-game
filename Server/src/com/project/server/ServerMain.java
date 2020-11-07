package com.project.server;


import java.io.IOException;

class ServerMain {


    public static void main(String[] args) {

        try {

            Thread server;

            try {
                int port = Integer.parseInt(args[0]);
                server = new Server(port);
            } catch (Exception e) {
                server = new Server();
            }

            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

