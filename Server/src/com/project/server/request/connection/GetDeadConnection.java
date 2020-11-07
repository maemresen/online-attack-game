package com.project.server.request.connection;


import com.project.core.Person;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class GetDeadConnection extends RequestHandler<Person>{


    public GetDeadConnection(ClientConnection clientConnection) {
        super(RequestType.GET_DEAD_CONNECTION, clientConnection);
    }

    @Override
    protected Person handle() {
        return clientConnection.getDeadConnection();
    }
}
