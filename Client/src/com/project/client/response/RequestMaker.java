package com.project.client.response;

import com.project.core.game.GameMoveType;
import com.project.core.protocol.Request;
import com.project.core.protocol.RequestType;
import com.project.core.protocol.IOModule;


/**
 * To send requests to the server
 */
public class RequestMaker {

    private final IOModule ioModule;

    public RequestMaker(IOModule ioModule) {
        this.ioModule = ioModule;
    }


    /*--------------------------
        Connection
    --------------------------*/
    public void requestCreateConnection(String connectionName) {
        sendRequest(new Request(RequestType.CREATE_CONNECTION, connectionName));
    }


    public void requestGetConnectionList() {
        sendRequest(new Request(RequestType.GET_CONNECTION_LIST));
    }

    /*--------------------------
        Game
    --------------------------*/
    public void requestCreateGame() {
        sendRequest(new Request(RequestType.CREATE_GAME));
    }

    public void requestJoinGame(String gameId) {
        sendRequest(new Request(RequestType.JOIN_GAME, gameId));
    }

    public void requestLeftGame() {
        sendRequest(new Request(RequestType.LEFT_GAME));
    }

    public void requestGetGameList() {
        sendRequest(new Request(RequestType.GET_GAME_LIST));
    }

    public void requestGetOpponent() {
        sendRequest(new Request(RequestType.GET_OPPONENT));
    }

    public void requestOtherLeft() {
        sendRequest(new Request(RequestType.OTHER_LEFT));
    }

    public void requestMakeMove(GameMoveType gameMoveType) {
        sendRequest(new Request(RequestType.MAKE_MOVE, gameMoveType));
    }

    public void requestGetGameStatus() {
        sendRequest(new Request(RequestType.GET_GAME_STATUS));
    }

    public void requestGetGameResult() {
        sendRequest(new Request(RequestType.GET_GAME_RESULT));
    }

    /*--------------------------
        Messaging
    --------------------------*/

    public void requestGetMessage() {
        sendRequest(new Request(RequestType.GET_MESSAGE));
    }

    public void requestGetGameMessage() {
        sendRequest(new Request(RequestType.GET_GAME_MESSAGE));
    }

    public void requestSendGameMessage(String message) {
        sendRequest(new Request(RequestType.SEND_GAME_MESSAGE, message));
    }

    public void requestSendMessageToAll(String message) {
        sendRequest(new Request(RequestType.SEND_MESSAGE_TO_ALL, message));
    }

    /**/
    private void sendRequest(Request request) {
        ioModule.send(request);
    }
}
