package com.project.server;


import com.project.core.Connection;
import com.project.core.protocol.Request;
import com.project.core.protocol.RequestType;
import com.project.core.protocol.Response;
import com.project.server.request.RequestHandler;
import com.project.server.request.RequestModule;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Connection {


    // server that client connected
    private Server server;

    // module to handle requests comes from client
    private RequestModule requestModule;


    /**
     * @param socket that has been used by client to connect server
     * @param server server has been connected by client
     * @throws IOException ...
     */
    ClientConnection(Socket socket, Server server) throws IOException {
        super(socket);
        this.server = server;
        this.requestModule = new RequestModule(this);
    }

    @Override
    public void initConnection() {
        try {

            /**/
            RequestType requestType;
            Request request;
            do {
                request = (Request) ioModule.getNext();
                requestType = request.getRequestType();
            } while (requestType != RequestType.CREATE_CONNECTION);

            handleRequest(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initConnectionLoop() {
        while (true) {
            if (isDead()) {
                return;
            }

            try {
                Request request = (Request) ioModule.getNext();
                handleRequest(request);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    //TODO: null problem, null may not be transferred through socket...
    private void handleRequest(Request request) {
        RequestHandler requestHandler = requestModule.handle(request);

        if (requestHandler == null) {
            return;
        }

        Response response = requestHandler.getResponse();


        if (response.getResponseValue() == null) {
            return;
        }

        ioModule.send(response);
    }

    /**/
    public Shared getShared() {
        return server.getShared();
    }

    //to close connection
    public void closeConnection() {
        Request request = new Request(RequestType.DEAD_CONNECTION);
        handleRequest(request);
    }

}
