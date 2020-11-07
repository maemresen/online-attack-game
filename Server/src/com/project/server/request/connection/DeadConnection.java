package com.project.server.request.connection;


import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class DeadConnection extends RequestHandler<Boolean> {


    public DeadConnection(ClientConnection clientConnection) {
        super(RequestType.DEAD_CONNECTION, clientConnection);
    }

    @Override
    protected Boolean handle() {
        getShared().removeConnection(clientConnection);
        return true;
    }
}
