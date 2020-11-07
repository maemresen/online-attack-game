package com.project.client;


import com.project.client.response.ResponseModule;
import com.project.client.response.RequestMaker;
import com.project.client.utils.runnable.RunnableList;
import com.project.core.Connection;
import com.project.core.protocol.Response;

import java.io.IOException;
import java.net.Socket;


public class ServerConnection extends Connection {

    /* module to handle response */
    private ResponseModule responseModule;

    /* helper to send requests to server */
    private RequestMaker requestMaker;

    /**
     * @param socket connection socket to connect server
     * @throws IOException ...
     */
    ServerConnection(Socket socket) throws IOException {
        super(socket);
        responseModule = new ResponseModule(this);
        requestMaker = new RequestMaker(ioModule);

        new RunnableList(
                requestMaker::requestGetConnectionList,
                requestMaker::requestGetGameList
        ).startAll();
    }


    @Override
    public void initConnection() {
        requestMaker.requestCreateConnection(connectionName);
    }

    @Override
    public void initConnectionLoop() {
        while (true) {

            if (isDead()) {
                return;
            }
            try {
                Response response = (Response) ioModule.getNext();
                responseModule.handle(response);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }

        }
    }

    public RequestMaker getRequestMaker() {
        return requestMaker;
    }


}
