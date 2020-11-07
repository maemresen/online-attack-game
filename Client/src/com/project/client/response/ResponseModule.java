package com.project.client.response;


import com.project.client.ClientMain;
import com.project.client.ServerConnection;
import com.project.core.game.Game;
import com.project.core.Person;
import com.project.core.game.GameResult;
import com.project.core.protocol.ProtocolModule;
import com.project.core.protocol.Response;

import java.util.List;


public class ResponseModule extends ProtocolModule<Boolean, Response, ServerConnection> {


    public ResponseModule(ServerConnection connection) {
        super(connection);
    }


    /*--------------------------
        Connection
    --------------------------*/
    @Override
    protected Boolean handleCreateConnection(Response response) {
        //
        return true;
    }

    @Override
    protected Boolean handleDeadConnection(Response response) {
        return true;
    }

    @Override
    protected Boolean handleGetConnectionList(Response response) {
        @SuppressWarnings("unchecked") List<Person> personList = (List<Person>) response.getResponseValue();
        ClientMain.personList.clear();
        ClientMain.personList.addAll(personList);
        return true;
    }

    @Override
    protected Boolean handleGetDeadConnection(Response response) {
        Person person = (Person) response.getResponseValue();
        ClientMain.serverConnection.addDeadConnection(person);
        return null;
    }

    @Override
    protected Boolean handleGetNewConnection(Response response) {
        Person person = (Person) response.getResponseValue();
        if (person == null) {
            return false;
        }
        ClientMain.serverConnection.addNewConnection(person);
        return true;

    }

    /*--------------------------
        Game
    --------------------------*/
    @Override
    protected Boolean handleCreateGame(Response response) {

        Game game = (Game) response.getResponseValue();

        if (game == null) {
            return false;
        }

        ClientMain.game = game;

        return null;
    }

    @Override
    protected Boolean handleJoinGame(Response response) {

        Game game = (Game) response.getResponseValue();

        if (game == null) {
            return false;
        }

        ClientMain.game = game;

        return null;
    }

    @Override
    protected Boolean handleLeftGame(Response response) {
        return null;
    }

    @Override
    protected Boolean handleGetGameList(Response response) {
        @SuppressWarnings("unchecked") List<Game> gameList = (List<Game>) response.getResponseValue();
        ClientMain.gameList.clear();
        ClientMain.gameList.addAll(gameList);
        return true;
    }

    @Override
    protected Boolean handleGetOpponent(Response response) {

        if (ClientMain.game == null) {
            return null;
        }

        Person opponent = (Person) response.getResponseValue();

        if (opponent == null) {
            return false;
        }

        if (ClientMain.game == null) {
            return null;
        }

        if (ClientMain.game.getOpponent() == null) {
            System.out.println(opponent + " is connected to the game");
            ClientMain.game.setOpponent(opponent);
            return true;
        }

        return false;


    }

    @Override
    protected Boolean handleOtherLeft(Response response) {
        ClientMain.otherLeft = (Boolean) response.getResponseValue();
        return true;
    }

    @Override
    protected Boolean handleMakeMove(Response response) {
        Game game = (Game) response.getResponseValue();
        if (game == null) {
            return false;
        }

        ClientMain.game = game;
        return true;
    }

    @Override
    protected Boolean handleGetGameStatus(Response response) {
        Game game = (Game) response.getResponseValue();
        if (game == null) {
            return false;
        }

        ClientMain.game = game;
        return true;
    }

    @Override
    protected Boolean handleGetGameResult(Response response) {
        ClientMain.gameResult = (GameResult) response.getResponseValue();
        return null;
    }


    /*--------------------------
        Messaging
    --------------------------*/
    @Override
    protected Boolean handleGetMessage(Response response) {
        String message = (String) response.getResponseValue();
        ClientMain.serverConnection.addMessage(message);
        return true;
    }

    @Override
    protected Boolean handleGetGameMessage(Response response) {
        String message = (String) response.getResponseValue();
        ClientMain.serverConnection.addGameMessage(message);
        return true;
    }


    @Override
    protected Boolean handleSendMessageToAll(Response response) {
        //
        return true;
    }

    @Override
    protected Boolean handleSendGameMessage(Response response) {
        //
        return true;
    }

    /**/
    @Override
    protected Boolean handleInvalid(Response response) {
        System.out.println("Invalid Request Made");
        return false;
    }

}
