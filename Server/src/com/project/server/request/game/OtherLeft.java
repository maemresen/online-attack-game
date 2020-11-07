package com.project.server.request.game;


import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class OtherLeft extends RequestHandler<Boolean> {

    public OtherLeft(ClientConnection clientConnection) {
        super(RequestType.OTHER_LEFT, clientConnection);
    }

    @Override
    protected Boolean handle() {
        return getShared().otherLeft(clientConnection);
    }
}
