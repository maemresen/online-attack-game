package com.project.server.request.game;


import com.project.core.game.GameResult;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;

public class GetGameResult extends RequestHandler<GameResult> {

    public GetGameResult(ClientConnection clientConnection) {
        super(RequestType.GET_GAME_RESULT, clientConnection);
    }

    @Override
    protected GameResult handle() {
        return getShared().getGameResult(clientConnection);
    }
}
