package com.project.server.request;


import com.project.core.game.GameMove;
import com.project.core.game.GameMoveType;
import com.project.core.protocol.ProtocolModule;
import com.project.core.protocol.Request;
import com.project.server.ClientConnection;
import com.project.server.request.game.*;
import com.project.server.request.message.GetGameMessage;
import com.project.server.request.message.GetMessage;
import com.project.server.request.message.SendGameMessage;
import com.project.server.request.message.SendMessageToAll;
import com.project.server.request.connection.*;

import java.util.List;

public class RequestModule extends ProtocolModule<RequestHandler, Request, ClientConnection> {

    public RequestModule(ClientConnection clientConnection) {
        super(clientConnection);
    }


    /*--------------------------------------------------------------------
        Connection
    --------------------------------------------------------------------*/

    @Override
    protected RequestHandler handleCreateConnection(Request request) {

        List<Object> parameterList = request.getParameterList();
        String connectionName = (String) parameterList.get(0);
        connection.setConnectionName(connectionName);
        return new CreateConnection(connection);
    }

    @Override
    protected RequestHandler handleDeadConnection(Request request) {
        return new DeadConnection(connection);
    }

    @Override
    protected RequestHandler handleGetConnectionList(Request request) {
        return new GetConnectionList(connection);
    }

    @Override
    protected RequestHandler handleGetNewConnection(Request request) {
        return new GetNewConnection(connection);
    }

    @Override
    protected RequestHandler handleGetDeadConnection(Request request) {
        return new GetDeadConnection(connection);
    }


    /*--------------------------------------------------------------------
        Game
    --------------------------------------------------------------------*/

    @Override
    protected RequestHandler handleCreateGame(Request request) {
        return new CreateGame(connection);
    }

    @Override
    protected RequestHandler handleJoinGame(Request request) {
        List<Object> parameterList = request.getParameterList();
        String gameId = (String) parameterList.get(0);

        return new JoinGame(connection, gameId);
    }

    @Override
    protected RequestHandler handleLeftGame(Request request) {
        return new LeftGame(connection);
    }

    @Override
    protected RequestHandler handleGetGameList(Request request) {
        return new GetGameList(connection);
    }

    @Override
    protected RequestHandler handleGetOpponent(Request request) {
        return new GetOpponent(connection);
    }

    @Override
    protected RequestHandler handleOtherLeft(Request request) {
        return new OtherLeft(connection);
    }

    @Override
    protected RequestHandler handleMakeMove(Request request) {
        List<Object> parameterList = request.getParameterList();
        GameMoveType gameMoveType = (GameMoveType) parameterList.get(0);
        GameMove gameMove = new GameMove(connection.getPerson(), gameMoveType);
        return new MakeMove(connection, gameMove);
    }

    @Override
    protected RequestHandler handleGetGameStatus(Request request) {
        return new GetGameStatus(connection);
    }

    @Override
    protected RequestHandler handleGetGameResult(Request request) {
        return new GetGameResult(connection);
    }


    /*--------------------------------------------------------------------
        Messaging
    --------------------------------------------------------------------*/

    @Override
    protected RequestHandler handleGetMessage(Request request) {
        return new GetMessage(connection);
    }

    @Override
    protected RequestHandler handleGetGameMessage(Request request) {
        return new GetGameMessage(connection);
    }

    @Override
    protected RequestHandler handleSendGameMessage(Request request) {

        List<Object> parameterList = request.getParameterList();
        String message = (String) parameterList.get(0);
        return new SendGameMessage(connection, message);
    }

    @Override
    protected RequestHandler handleSendMessageToAll(Request request) {

        List<Object> parameterList = request.getParameterList();
        String message = (String) parameterList.get(0);

        return new SendMessageToAll(connection, message);
    }

    @Override
    protected RequestHandler handleInvalid(Request request) {
        return null;
    }
}