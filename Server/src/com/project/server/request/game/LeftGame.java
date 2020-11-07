package com.project.server.request.game;


import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class LeftGame extends RequestHandler<Boolean> {

    public LeftGame(ClientConnection clientConnection) {
        super(RequestType.LEFT_GAME, clientConnection);
    }

    @Override
    protected Boolean handle() {
        return clientConnection.getShared().leftGame(clientConnection);
    }
}