package com.project.core.game;


import com.project.core.Person;

import java.io.Serializable;

/**
 * Models a move int the game
 */
public class GameMove implements Serializable {

    // owner of the move
    private final Person owner;

    // type of the move
    private final GameMoveType gameMoveType;

    public GameMove(Person owner, GameMoveType gameMoveType) {
        this.owner = owner;
        this.gameMoveType = gameMoveType;
    }

    /* getters */
    public Person getOwner() {
        return owner;
    }

    GameMoveType getGameMoveType() {
        return gameMoveType;
    }

    @Override
    public String toString() {
        return "GameMove{" +
                "owner=" + owner +
                ", gameMoveType=" + gameMoveType +
                '}';
    }
}
