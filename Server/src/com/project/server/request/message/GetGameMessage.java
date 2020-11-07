package com.project.server.request.message;


import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class GetGameMessage extends RequestHandler<String> {


    public GetGameMessage(ClientConnection clientConnection) {
        super(RequestType.GET_GAME_MESSAGE, clientConnection);
    }

    @Override
    protected String handle() {
        return clientConnection.getGameMessage();
    }
}
