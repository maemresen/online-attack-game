package com.project.server.request.connection;


import com.project.core.Person;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

import java.util.List;

public class GetConnectionList extends RequestHandler<List<Person>> {


    public GetConnectionList(ClientConnection clientConnection) {
        super(RequestType.GET_CONNECTION_LIST, clientConnection);
    }

    @Override
    protected List<Person> handle() {
        return getShared().getConnectionList();
    }
}
