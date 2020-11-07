package com.project.core.game;


import com.project.core.Person;
import com.project.core.utils.Identifiable;
import com.project.core.utils.IdentifierHelper;

import java.io.Serializable;

/**
 * Models the game
 */
public class Game implements Serializable, Identifiable {

    // creator of the game
    private Person creator;

    // opponent of the creator
    private Person opponent;

    //0 = creator, 1 = opponent
    private Person turn;

    // is the game turn base or not
    private final boolean turnBased;

    // id of the game
    private final String gameId;

    public Game(Person creator) {
        this.creator = creator;
        this.creator.setHealth();
        this.gameId = IdentifierHelper.generateIdentifier();
        turn = creator;
        turnBased = false;
    }



    /* getters */
    public Person getCreator() {
        return creator;
    }

    public Person getOpponent() {
        return opponent;
    }

    /* setters */
    public void setOpponent(Person opponent) {
        this.opponent = opponent;
        this.opponent.setHealth();
    }

    /**/

    /**
     * To check is other player different than {@param person} is left the game or not
     *
     * @param person who asks the other player left the game
     * @return other player left the game or not
     */
    public boolean isOtherLeft(Person person) {
        return isOpponentLeft(person) || isCreatorLeft(person);
    }

    /**
     * To check is opponent left the game
     *
     * @param person who asks the other player left the game
     * @return opponent left the game or not
     */
    private boolean isOpponentLeft(Person person) {
        return isCreator(person) && opponent == null;
    }

    /**
     * To check is opponent left the game
     *
     * @param person who asks the other player left the game
     * @return creator left the game or not
     */
    private boolean isCreatorLeft(Person person) {
        return isOpponent(person) && creator == null;
    }

    /**/

    /**
     * @return has the game room to add person
     */
    public boolean isEmpty() {
        return opponent == null;
    }

    /**
     * To check if {@param person} is already created/joined the game before
     *
     * @param person who will be controlled
     * @return {@param person} is created/joined or not
     */
    public boolean hasPerson(Person person) {
        return person != null && (isCreator(person) || isOpponent(person));

    }

    /**
     * To try to remove {@param person} or not
     *
     * @param person who will be removed
     * @return successfully removed or not
     */
    public boolean removePerson(Person person) {
        if (isCreator(person)) {
            this.creator = null;
            return true;
        }

        if (isOpponent(person)) {
            this.opponent = null;
            return true;
        }
        return false;
    }

    /**/
    private boolean isCreator(Person person) {
        return person.equals(creator);
    }

    private boolean isOpponent(Person person) {
        return person.equals(opponent);
    }

    /**/
    public String getCreatorInfo() {
        return creator == null ? "" : creator.getInfo();
    }

    public String getOpponentInfo() {
        return opponent == null ? "" : opponent.getInfo();
    }


    /**
     * To get other player different then {@param person}
     * <p>
     * E.g. if {@param person} is creator return opponent otherwise returns creator
     *
     * @param person asks for the other player
     * @return other player different then {@param person}
     */
    private Person getOther(Person person) {
        if (isCreator(person)) {
            return opponent;
        }

        if (isOpponent(person)) {
            return creator;
        }

        return null;

    }


    /**/

    /**
     * To handle game move request made by persons
     *
     * @param gameMove move will be handled
     * @return gameMove successfully handled or not
     */
    public synchronized boolean makeMove(GameMove gameMove) {


        if (gameMove == null) {
            return false;
        }

        Person owner = gameMove.getOwner();
        if (owner == null) {
            System.out.println("owner is null");
            return false;
        }

        if (turnBased && !owner.equals(turn)) {
            System.out.println("it is not " + owner + " turn");
            return false;
        }

        Person opponent = getOther(owner);
        if (opponent == null) {
            System.out.println("opponent is null");
            return false;
        }

        if (!handleMove(owner, opponent, gameMove.getGameMoveType())) {
            System.out.println("Can not handle move");
            return false;
        }

        System.out.println("Move Success");
        return true;

    }

    /**
     * To handle move made by {@param owner} with type of {@param gameMoveType}
     *
     * @param owner        who wants to make move
     * @param other        other player
     * @param gameMoveType type of the move
     * @return the move successfully handled or not
     */
    private boolean handleMove(Person owner, Person other, GameMoveType gameMoveType) {

        switch (gameMoveType) {
            case ATTACK:
                other.removeHealth();
                System.out.println(owner.getInfo() + " attacks " + other.getInfo() + "!");
                turn = other;
                return true;

            case DEFENCE:
                owner.addHealth();
                System.out.println(owner.getInfo() + " defence!");
                turn = other;
                return true;

            default:
                System.out.println("NON OF TYPES gameMoveType=" + gameMoveType);
                return false;
        }


    }

    /**
     * To get result of the game
     * <p>
     * WIN, LOSE, UNDEFINED
     *
     * @param person who wants to learn the result
     * @return the result of the game
     */
    public GameResult getGameResult(Person person) {
        if (!hasPerson(person)) {
            return GameResult.UNDEFINED;
        }

        if (person.getHealth() == 0) {
            return GameResult.LOSE;
        }

        Person opponent = getOther(person);
        if (opponent == null) {
            return GameResult.WIN;
        }

        if (opponent.getHealth() == 0) {
            return GameResult.WIN;
        }

        return GameResult.UNDEFINED;
    }


    /**/
    @Override
    public String toString() {
        return "id:" + gameId + ", creator:" + getCreatorInfo() + ", opponent:" + getOpponentInfo();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Game)) {
            return false;
        }

        Game game = (Game) o;

        return turn == game.turn && (getCreator() != null ? getCreator().equals(game.getCreator()) : game.getCreator() == null) && (getOpponent() != null ? getOpponent().equals(game.getOpponent()) : game.getOpponent() == null) && (gameId != null ? gameId.equals(game.gameId) : game.gameId == null);
    }

    /**/
    @Override
    public boolean hasId(String id) {
        return gameId.equals(id);
    }

    //TODO: Add to Identifiable Interface
    public String getId() {
        return gameId;
    }
}

