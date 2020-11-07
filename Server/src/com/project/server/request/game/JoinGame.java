package com.project.server.request.game;

import com.project.core.game.Game;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;


public class JoinGame extends RequestHandler<Game> {

    private final String gameId;

    public JoinGame(ClientConnection clientConnection, String gameId) {
        super(RequestType.JOIN_GAME, clientConnection);
        this.gameId = gameId;
    }

    @Override
    protected Game handle() {
        return getShared().joinGame(clientConnection, gameId);
    }
}
