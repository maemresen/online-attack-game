package com.project.server.request.game;

import com.project.core.game.Game;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class CreateGame extends RequestHandler<Game> {

    public CreateGame(ClientConnection clientConnection) {
        super(RequestType.CREATE_GAME, clientConnection);
    }

    @Override
    protected Game handle() {
        return getShared().createGame(clientConnection);
    }
}
