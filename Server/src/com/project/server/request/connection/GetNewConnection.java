package com.project.server.request.connection;


import com.project.core.Person;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class GetNewConnection extends RequestHandler<Person> {

    public GetNewConnection(ClientConnection clientConnection) {
        super(RequestType.GET_NEW_CONNECTION, clientConnection);
    }

    @Override
    protected Person handle() {
        return clientConnection.getNewConnection();
    }

}
