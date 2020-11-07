package com.project.server.request.game;


import com.project.core.game.Game;
import com.project.core.protocol.RequestType;
import com.project.server.ClientConnection;
import com.project.server.request.RequestHandler;
import java.util.List;

public class GetGameList extends RequestHandler<List<Game>> {


    public GetGameList(ClientConnection clientConnection) {
        super(RequestType.GET_GAME_LIST, clientConnection);

    }

    @Override
    protected List<Game> handle() {
        return getShared().getGameList();
    }

}
