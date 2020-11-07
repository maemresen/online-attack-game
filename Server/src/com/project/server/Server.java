package com.project.server;


import com.project.core.ServerConf;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server
 */
class Server extends Thread implements Serializable {

    // socket that clients connect
    private ServerSocket serverSocket;

    // is server running
    private boolean running;

    // common area for all connections
    private Shared shared;

    Server() throws IOException {
        this.serverSocket = ServerConf.getServerSocket();
        initServer();
    }

    Server(int port) throws IOException {
        this.serverSocket = ServerConf.getServerSocket(port);
        initServer();
    }

    private void initServer() {
        this.shared = new Shared();
        this.running = true;
    }

    @Override
    public void run() {

        System.out.println("waiting for connections...");
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                new ClientConnection(socket, this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**/

    /**
     *
     * @return shared
     */
    Shared getShared() {
        return shared;
    }
}
