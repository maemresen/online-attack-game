package com.project.client.controller;


import com.project.client.ClientMain;
import com.project.client.response.RequestMaker;
import com.project.client.utils.PageHelper;
import com.project.client.utils.runnable.FXRunnableList;
import com.project.client.utils.runnable.FXRunnableTimer;
import com.project.core.Person;
import com.project.core.game.Game;
import com.project.core.game.GameMoveType;
import com.project.core.game.GameResult;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;


public class GameScreenController {

    /*---------------------------------
        Name Labels
    ---------------------------------*/

    /* label for creator name */
    @FXML
    private Label creatorNameLabel;

    /* label for opponent name */
    @FXML
    private Label opponentNameLabel;


    /*---------------------------------
        Progressbar
    ---------------------------------*/


    /* progressbar for the health of creator */
    @FXML
    private ProgressBar creatorHealth;
    private final ProgressIndicator creatorHealthIndicator = new ProgressIndicator();

    /* progressbar for the health of opponent */
    @FXML
    private ProgressBar opponentHealth;
    private final ProgressIndicator opponentHealthIndicator = new ProgressIndicator();

    /*---------------------------------
        Messaging
    ---------------------------------*/

    /* message of the client */
    @FXML
    private TextField msgInput;

    /* messages comes from the server */
    @FXML
    private ListView<String> msgListView;

    /* some periodic operations */
    private final FXRunnableList fxRunnableList = new FXRunnableList(
            this::refreshMessageList,
            this::refreshGameStatus,
            this::updateGameResult,
            this::otherLeft,

            this::requestGetGameStatus,
            this::requestOtherLeft,
            this::requestGetGameResult,
            this::requestOpponent
    );

    /* is game started or not */
    private boolean started;

    /* is game end  */
    private boolean dead = false;

    public void initialize() {
        started = false;

        if (ClientMain.game == null) {
            return;
        }

        RequestMaker rm = ClientMain.serverConnection.getRequestMaker();
        new FXRunnableTimer(rm::requestGetGameMessage).startRunnable();


        setCreatorName();


        if (ClientMain.game.getOpponent() != null) {
            setOpponentName();
            started = true;
            fxRunnableList.startAll();
            updateHealths();
            return;
        }

        new FXRunnableList(
                this::requestOpponent,
                this::setOpponent
        ).startAll();


    }

    /* to set opponent */
    private void setOpponent() {

        if (started) {
            return;
        }

        if (ClientMain.game == null) {
            return;
        }

        if (ClientMain.game.getOpponent() == null) {
            return;
        }

        setOpponentName();
        started = true;
        updateHealths();
        fxRunnableList.startAll();
    }

    /* set name labels */
    private void setCreatorName() {
        creatorNameLabel.setText(ClientMain.creator ? "You" : ClientMain.game.getCreatorInfo());
    }

    private void setOpponentName() {
        opponentNameLabel.setText(ClientMain.creator ? ClientMain.game.getOpponentInfo() : "You");
    }


    /*---------------------------------
        Events
    ---------------------------------*/

    // sending message
    @FXML
    private void sendMessage() {

        String message = msgInput.getText();
        if (message.equalsIgnoreCase("")) {
            return;
        }

        ClientMain.serverConnection.getRequestMaker().requestSendGameMessage(message);
        msgInput.clear();


    }


    // make moves
    @FXML
    public void attack() {
        requestMakeMove(GameMoveType.ATTACK);
    }

    @FXML
    public void defence() {
        requestMakeMove(GameMoveType.DEFENCE);
    }

    // exiting game
    @FXML
    public void exitGame() throws IOException {
        leftGame();
        PageHelper.loadRoomsPage();
    }


    /*---------------------------------
        Requests
    ---------------------------------*/
    // to get opponent
    private void requestOpponent() {
        ClientMain.serverConnection.getRequestMaker().requestGetOpponent();
    }

    // to send request to check is the other player left
    private void requestOtherLeft() {
        ClientMain.serverConnection.getRequestMaker().requestOtherLeft();
    }

    // to send request to check game status
    private void requestGetGameStatus() {
        ClientMain.serverConnection.getRequestMaker().requestGetGameStatus();
    }

    // to send request to check game result
    private void requestGetGameResult() {
        ClientMain.serverConnection.getRequestMaker().requestGetGameResult();
    }

    // to send request to make a move in game
    private void requestMakeMove(GameMoveType gameMoveType) {
        ClientMain.serverConnection.getRequestMaker().requestMakeMove(gameMoveType);
    }

    /*---------------------------------
        Periodic Operations
    ---------------------------------*/
    // to refresh messages
    private void refreshMessageList() {
        addMessage(ClientMain.serverConnection.getGameMessage());
    }

    private void addMessage(String message) {
        if (message == null) {
            return;
        }
        if (message.equalsIgnoreCase("")) {
            return;
        }
        msgListView.getItems().add(message);

    }

    // to update game status
    private void refreshGameStatus() {
        updateHealths();
    }

    private void updateHealths() {
        Game game = ClientMain.game;
        if (game == null) {
            return;
        }

        updatePersonHealth(game.getCreator(), creatorHealth, creatorHealthIndicator, creatorNameLabel, ClientMain.creator);
        updatePersonHealth(game.getOpponent(), opponentHealth, opponentHealthIndicator, opponentNameLabel, !ClientMain.creator);
    }

    private void updatePersonHealth(Person person, ProgressBar progressBar, ProgressIndicator progressIndicator, Label nameLabel, boolean you) {
        if (person == null) {
            return;
        }
        nameLabel.setText(you ? "You" : person.getInfo());
        double health = person.getHealth() * 0.10;
        progressBar.setProgress(health);
        progressIndicator.setProgress(health);
    }

    // to update game result
    private void updateGameResult() {
        if (dead) {
            return;
        }
        if (ClientMain.gameResult == GameResult.UNDEFINED) {
            return;
        }
        addMessage(ClientMain.gameResult.name());
        leftGame();
        dead = true;
    }

    // to check is the other player left the game
    private void otherLeft() {

        if (!started) {
            return;
        }

        if (dead) {
            return;
        }

        if (ClientMain.otherLeft) {
            addMessage("Opponent has left game! You win");
            leftGame();
            dead = true;
        }

    }

    // to handle left game
    private void leftGame() {
        synchronized (this) {
            ClientMain.serverConnection.getRequestMaker().requestLeftGame();
            ClientMain.game = null;
            ClientMain.creator = false;
            ClientMain.gameResult = GameResult.UNDEFINED;
            ClientMain.otherLeft = false;
        }

    }


}



