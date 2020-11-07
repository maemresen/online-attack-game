package com.project.server.request.game;

import com.project.core.game.Game;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;


public class GetGameStatus extends RequestHandler<Game> {

    public GetGameStatus(ClientConnection clientConnection) {
        super(RequestType.GET_GAME_STATUS, clientConnection);
    }

    @Override
    protected Game handle() {
        return getShared().getGameStatus(clientConnection);
    }
}
