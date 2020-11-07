package com.project.server;


import com.project.core.Connection;
import com.project.core.game.Game;
import com.project.core.Person;
import com.project.core.game.GameMove;
import com.project.core.game.GameResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Common area for clients
 * <p>
 * All common things are stored in here.
 * <p>
 * List of connections, Created games etc...
 */
public class Shared {

    /*--------------------------
        Connection
    --------------------------*/

    // list of connected clients
    private final List<Connection> connectionList = new ArrayList<>();

    /**
     * @return to get all persons connected to the server
     */
    public List<Person> getConnectionList() {
        List<Person> personList = new ArrayList<>();
        for (Connection conn : connectionList) {
            if (conn == null) {
                continue;
            }
            personList.add(conn.getPerson());
        }
        return personList;
    }


    /**
     * To add {@param newConnection} to the server
     *
     * @param newConnection connection will be added
     */
    public void addConnection(Connection newConnection) {
        connectionList.add(newConnection);
        System.out.println(newConnection + " is connected to the server");
    }

    /**
     * To remove {@param deadConnection} from the server
     *
     * @param deadConnection connection will be removed
     */
    public void removeConnection(Connection deadConnection) {
        connectionList.remove(deadConnection);
        leftGame(deadConnection);
        System.out.println(deadConnection + " is disconnected from the server");
    }


    /**
     * To get connection which has id of {@param connectionId}
     * <p>
     * It returns a connection {@param connectionId}
     * If there is no connection has id of {@param connectionId} then returns null
     *
     * @param connectionId id queried by server
     * @return search result
     */
    public Connection getConnectionById(String connectionId) {
        for (Connection each : connectionList) {
            if (each.hasId(connectionId)) {
                return each;
            }
        }
        return null;
    }


    /*--------------------------
        Game
    --------------------------*/

    // list of the game created
    private final List<Game> gameList = new ArrayList<>();

    /**
     * To create a game
     * <p>
     * Connections can only create one game at the same time.
     * <p>
     * If {@param connection} has already created or joined game  before and
     * it is still in that game, then the returns null
     *
     * @param connection who makes the request
     * @return created game
     */
    public Game createGame(Connection connection) {

        Person person = connection.getPerson();

        for (Game game : gameList) {
            if (game.hasPerson(person)) {
                return null;
            }
        }

        Game game = new Game(person);
        gameList.add(game);
        System.out.println(person + " has created game");

        return game;
    }

    //TODO: warn : other players can not join games

    /**
     * To add {@param connection} to the game with id {@param gameId}.
     * if the game with id {@param gameId} not found or {@param connection} can
     * not successfully added to the game returns null
     *
     * @param connection who makes the request
     * @param gameId     id of the game that {@param connection} wants to join
     * @return requested game
     */
    public Game joinGame(Connection connection, String gameId) {

        Person person = connection.getPerson();

        Game game = getGame(gameId);

        if (game == null) {
            System.out.println(person + " can not find game with id=[" + gameId + "]");
            return null;
        }

        if (!game.isEmpty()) {
            System.out.println(game + " has no more room for " + person + " to join game");
        }

        if (game.hasPerson(person)) {

            System.out.println(person + " has been already joined game");
            return null;
        }

        game.setOpponent(person);
        System.out.println(person + " has joined game");
        return game;

    }

    /**
     * To get game with id {@param gameId}
     *
     * @param gameId id queried by server to find the game
     * @return game
     */
    private Game getGame(String gameId) {
        for (Game game : gameList) {
            if (game.hasId(gameId)) {
                return game;
            }

        }
        return null;
    }

    /**
     * To handle left game request made by {@param connection}
     *
     * @param connection wants to left game
     * @return successfully removed from the game or not
     */
    public boolean leftGame(Connection connection) {

        Person person = connection.getPerson();

        Game game = getGame(connection);
        if (game == null) {
            return false;
        }

        if (!game.removePerson(person)) {
            return false;
        }
        System.out.println(person + " has left game!");

        if (game.isEmpty()) {
            gameList.remove(game);
            return true;
        }

        return false;
    }

    /**
     * To check if the other person left the game
     *
     * @param connection who makes the request
     * @return other person left the game or not
     */
    public boolean otherLeft(Connection connection) {
        Game game = getGame(connection);
        return game == null || game.isOtherLeft(connection.getPerson());
    }

    /**
     * @return list of the games created on the server at that time
     */
    public List<Game> getGameList() {
        return gameList;
    }

    /**
     * @param connection connection makes the request
     * @return the opponent in the game
     */
    public Person getOpponent(Connection connection) {
        Game game = getGame(connection);

        if (game == null) {
            return null;
        }

        return game.getOpponent();
    }


    /**
     * To get the game that {@param connection} is connected
     * <p>
     * Returns null if  there is no game has been created or joined by {@param connection}
     *
     * @param connection who makes the request
     * @return game
     */
    public Game getGame(Connection connection) {
        return getGame(connection.getPerson());
    }


    /**
     * To get the game that {@param person} is connected
     * <p>
     * Returns null if  there is no game has been created or joined by {@param person}
     *
     * @param person connection that makes the request
     * @return game
     */
    private Game getGame(Person person) {
        for (Game game : gameList) {
            if (game.hasPerson(person)) {
                return game;
            }
        }
        return null;
    }

    /**
     * To get the status of the game that {@param connection} connected
     *
     * @param connection who makes the request
     * @return the result of the game
     */
    public Game getGameStatus(Connection connection) {
        return getGame(connection);
    }


    /**
     * To handle a move and get updated version of the game
     * <p>
     * Returns null if the game can not handle the move
     *
     * @param gameMove move will be handled by the game
     * @return updated version of the game
     */
    public Game makeMove(GameMove gameMove) {
        if (gameMove == null) {
            System.out.println("No Game Move Found");
            return null;
        }

        Person owner = gameMove.getOwner();
        Game game = getGame(owner);

        if (game == null) {
            System.out.println("Game not found");
            return null;
        }
        if (!game.makeMove(gameMove)) {
            System.out.println("Not a valid Move");
            return null;
        }

        System.out.println("Move Success");

        return game;
    }

    /**
     * To get the result of the game that {@param connection} connected
     *
     * @param connection who makes the request
     * @return the result of the game that {@param connection} connected
     */
    public GameResult getGameResult(Connection connection) {
        Game game = getGame(connection);
        if (game == null) {
            return GameResult.UNDEFINED;
        }
        return game.getGameResult(connection.getPerson());
    }

    /*--------------------------
        Message
    --------------------------*/

    /**
     * To send public message to all clients
     *
     * @param msg message will be sent by server
     */
    public void sendMessageToAll(String msg) {
        for (Connection each : connectionList) {
            each.addMessage(msg);
        }
    }

}
