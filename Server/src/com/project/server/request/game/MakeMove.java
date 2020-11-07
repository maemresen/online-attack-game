package com.project.server.request.game;


import com.project.core.game.Game;
import com.project.core.game.GameMove;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class MakeMove extends RequestHandler<Game> {

    private final GameMove gameMove;

    public MakeMove(ClientConnection clientConnection, GameMove gameMove) {
        super(RequestType.MAKE_MOVE, clientConnection);
        this.gameMove = gameMove;
    }

    @Override
    protected Game handle() {
        return getShared().makeMove(gameMove);
    }
}
