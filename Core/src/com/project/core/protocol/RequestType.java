package com.project.core.protocol;


import java.io.Serializable;

/**
 * Simple Definitions for Request types
 */
public enum RequestType implements Serializable {

    /* Connection */
    CREATE_CONNECTION, DEAD_CONNECTION, GET_CONNECTION_LIST, GET_NEW_CONNECTION, GET_DEAD_CONNECTION,

    /* Game */
    CREATE_GAME, JOIN_GAME, LEFT_GAME, GET_GAME_LIST, GET_OPPONENT, OTHER_LEFT, MAKE_MOVE, GET_GAME_STATUS,
    GET_GAME_RESULT,

    /* Messaging */
    GET_MESSAGE, GET_GAME_MESSAGE, SEND_MESSAGE_TO_ALL, SEND_GAME_MESSAGE,


}
