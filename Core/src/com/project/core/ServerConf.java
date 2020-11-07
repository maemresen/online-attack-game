package com.project.core;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Configuration of server...
 */
public class ServerConf implements Serializable {


    /**
     * Constant Definitions for some servers...
     */
    private enum ServerDef {

        LOCALHOST("localhost", 8088),

        BAUM_CBA("10.45.1.97", 9000);

        private final String serverName;
        private final int port;

        /**
         * @param serverName name of the server
         * @param port       port opened on server
         */
        ServerDef(String serverName, int port) {
            this.serverName = serverName;
            this.port = port;
        }
    }

    /**
     * server that app will be served
     */
    private static final ServerDef DEFAULT_SERVER_DEF = ServerDef.LOCALHOST;


    /*---------------------
        Client Socket
    ---------------------*/

    /**
     * It gets {@param args[0]} as server name, {@param args[1]} as port
     * and tries to connect server...
     *
     * @param args argument given at runtime
     * @return connection socket for server
     * @throws IOException ..
     */
    public static Socket getSocketByArgs(String args[]) throws IOException {

        if (args.length < 2) {
            return getSocket();
        }

        try {
            int port = Integer.parseInt(args[1]);
            return getSocket(args[0], port);
        } catch (Exception e) {
            return getSocket();
        }

    }

    /**
     * @return connection socket for default server
     * @throws IOException ..
     */
    private static Socket getSocket() throws IOException {
        return getSocket(DEFAULT_SERVER_DEF.serverName, DEFAULT_SERVER_DEF.port);
    }

    /**
     * @param serverName host of the server
     * @param port       port of the server
     * @return connection socket for server with name as {@param serverName} and port as {@param port}
     * @throws IOException ..
     */
    private static Socket getSocket(String serverName, int port) throws IOException {
        return new Socket(serverName, port);
    }

    /*---------------------
        Server Socket
    ---------------------*/

    /**
     * @return open a socket for default server
     * @throws IOException ..
     */
    public static ServerSocket getServerSocket() throws IOException {
        return getServerSocket(DEFAULT_SERVER_DEF.port);
    }

    /**
     * @param port port of the server
     * @return open a port on {@param port} for the server
     * @throws IOException ..
     */
    public static ServerSocket getServerSocket(int port) throws IOException {
        System.out.println("Server is started on port[" + port + "]");
        return new ServerSocket(port);
    }

}
