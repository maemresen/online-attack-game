package com.project.server.request.game;


import com.project.core.Person;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class GetOpponent extends RequestHandler<Person> {


    public GetOpponent(ClientConnection clientConnection) {
        super(RequestType.GET_OPPONENT, clientConnection);
    }


    @Override
    protected Person handle() {
        return getShared().getOpponent(clientConnection);
    }
}
