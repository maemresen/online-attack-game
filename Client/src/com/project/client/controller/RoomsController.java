package com.project.client.controller;

import com.project.client.ClientMain;
import com.project.client.utils.PageHelper;
import com.project.client.utils.runnable.FXRunnableList;
import com.project.client.utils.runnable.FXRunnableTimer;
import com.project.core.game.Game;
import com.project.core.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.io.IOException;

public class RoomsController {

    // list of the players on the server
    @FXML
    private ListView<Person> playerListView;

    // messages sent by players
    @FXML
    private ListView<String> messageListView;

    // rooms created by players
    @FXML
    private ListView<Game> roomsList;

    // id of the game that client wants to join
    @FXML
    private TextField gameIdInput;

    // message that client wants send to server
    @FXML
    private TextField messageInput;


    private final FXRunnableList fxRunnableList = new FXRunnableList(
            this::refreshConnectionList,
            this::refreshGameList,
            this::refreshMessageList,
            this::requestGetMessage
    );

    public void initialize() {
        fxRunnableList.startAll();
    }

    /*---------------------------------
        Events
    ---------------------------------*/

    // to send message in text field
    @FXML
    public void msgFieldKeyPressed() {
        sendMessage();
    }

    // to send message
    @FXML
    private void sendMessage() {

        String message = messageInput.getText();
        if (message == null) {
            return;
        }
        if (message.equalsIgnoreCase("")) {
            return;
        }

        ClientMain.serverConnection.getRequestMaker().requestSendMessageToAll(message);
        messageInput.clear();
    }

    // to create a game
    @FXML
    public void createGame() {
        ClientMain.serverConnection.getRequestMaker().requestCreateGame();
        fxRunnableList.stopAll();

        gameCheck.startRunnable();
        ClientMain.creator = true;
    }

    //to join game
    @FXML
    private void joinGame() throws IOException {
        ClientMain.creator = false;
        String gameId = gameIdInput.getText();

        if (gameId.equalsIgnoreCase("")) {
            return;
        }

        ClientMain.serverConnection.getRequestMaker().requestJoinGame(gameId);
        fxRunnableList.stopAll();
        gameCheck.startRunnable();

    }


    /*---------------------------------
        Checking is game started
    ---------------------------------*/
    private final FXRunnableTimer gameCheck = new FXRunnableTimer(this::openGame);

    private void openGame() {
        try {
            if (ClientMain.game == null) {
                return;
            }
            gameCheck.stopRunnable();
            PageHelper.loadGameScreenPage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*---------------------------------
        Periodic Operations
    ---------------------------------*/
    // to refreshing connection list
    private void refreshConnectionList() {

        if (ClientMain.personList == null) {
            return;
        }
        ObservableList<Person> items = FXCollections.observableList(ClientMain.personList);
        playerListView.setItems(items);
    }

    // to refreshing game list
    private void refreshGameList() {

        if (ClientMain.gameList == null) {
            return;
        }
        ObservableList<Game> items = FXCollections.observableList(ClientMain.gameList);
        roomsList.setItems(items);
    }

    // to refreshing message list
    private void refreshMessageList() {
        String message = ClientMain.serverConnection.getMessage();
        if (message == null) {
            return;
        }

        if (message.equalsIgnoreCase("")) {
            return;
        }

        messageListView.getItems().add(message);
    }

    /*---------------------------------
        Requests
    ---------------------------------*/
    private void requestGetMessage() {
        ClientMain.serverConnection.getRequestMaker().requestGetMessage();
    }
}
