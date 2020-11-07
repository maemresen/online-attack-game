package com.project.core.protocol;

import com.project.core.Connection;

/**
 * @param <T> Type of return value of handling requests
 * @param <P> Type of protocol (response or request)
 * @param <C> Type of connection (Client or Server) connection
 */
public abstract class ProtocolModule<T, P extends Protocol, C extends Connection> {

    // connection that receive the protocol
    protected final C connection;

    protected ProtocolModule(C connection) {
        this.connection = connection;
    }

    /**
     * to handle a request or response according to type of it...
     *
     * @param p protocol
     * @return result of handling request.
     */
    public T handle(P p) {


        switch (p.getRequestType()) {

            /* Connection */
            case CREATE_CONNECTION:
                return handleCreateConnection(p);
            case DEAD_CONNECTION:
                return handleDeadConnection(p);
            case GET_CONNECTION_LIST:
                return handleGetConnectionList(p);
            case GET_NEW_CONNECTION:
                return handleGetNewConnection(p);
            case GET_DEAD_CONNECTION:
                return handleGetDeadConnection(p);

            /* Game */
            case CREATE_GAME:
                return handleCreateGame(p);
            case JOIN_GAME:
                return handleJoinGame(p);
            case LEFT_GAME:
                return handleLeftGame(p);
            case GET_GAME_LIST:
                return handleGetGameList(p);
            case GET_OPPONENT:
                return handleGetOpponent(p);
            case OTHER_LEFT:
                return handleOtherLeft(p);
            case MAKE_MOVE:
                return handleMakeMove(p);
            case GET_GAME_STATUS:
                return handleGetGameStatus(p);
            case GET_GAME_RESULT:
                return handleGetGameResult(p);

            /* Messaging */
            case GET_MESSAGE:
                return handleGetMessage(p);
            case GET_GAME_MESSAGE:
                return handleGetGameMessage(p);
            case SEND_MESSAGE_TO_ALL:
                return handleSendMessageToAll(p);
            case SEND_GAME_MESSAGE:
                return handleSendGameMessage(p);

            /* Invalid */
            default:
                return handleInvalid(p);
        }
    }


    /*--------------------------
        Connection
    --------------------------*/

    /**
     * to create new connection
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleCreateConnection(P p);

    /**
     * to dead connection
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleDeadConnection(P p);

    /**
     * to get connection list
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetConnectionList(P p);

    /**
     * to get new connection info
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetNewConnection(P p);

    /**
     * to get dead connection info
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetDeadConnection(P p);


    /*--------------------------
        Game
    --------------------------*/

    /**
     * to create a new game
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleCreateGame(P p);

    /**
     * to join the game
     *
     * @param p protocol type
     * @return response of *after handling
     */
    protected abstract T handleJoinGame(P p);

    /**
     * to left the game
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleLeftGame(P p);

    /**
     * to get game list
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetGameList(P p);

    /**
     * to get opponent info in-game
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetOpponent(P p);

    /**
     * to get dead connection info in-game
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleOtherLeft(P p);

    /**
     * to make move in game
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleMakeMove(P p);

    /**
     * to get status of the game
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetGameStatus(P p);

    /**
     * to get game result
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetGameResult(P p);


    /*--------------------------
        Messaging
    --------------------------*/

    /**
     * to get public message
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetMessage(P p);

    /**
     * to get in-game message
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleGetGameMessage(P p);

    /**
     * to send public message
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleSendMessageToAll(P p);

    /**
     * to send in-game message
     *
     * @param p protocol type
     * @return response of after handling
     */
    protected abstract T handleSendGameMessage(P p);


    /* Invalid */
    protected abstract T handleInvalid(P p);


}
