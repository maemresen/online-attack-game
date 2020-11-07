package com.project.server.request.connection;

import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class CreateConnection extends RequestHandler<Boolean> {

    public CreateConnection(ClientConnection clientConnection) {
        super(RequestType.CREATE_CONNECTION, clientConnection);
    }

    @Override
    protected Boolean handle() {
        getShared().addConnection(clientConnection);
        return true;
    }
}
