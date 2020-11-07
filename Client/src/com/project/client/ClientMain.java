package com.project.client;

import com.project.client.utils.PageHelper;

import com.project.core.game.Game;
import com.project.core.Person;
import com.project.core.ServerConf;
import com.project.core.game.GameResult;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientMain extends Application {

    /* name of the client (only used to set connection name at the start ) */
    private static String name = "";

    /* server connection */
    public static ServerConnection serverConnection = null;

    /* game the connected in the server */
    public static Game game = null;

    /* result of the connected game */
    public static GameResult gameResult = GameResult.UNDEFINED;

    /* is the client creator of the game or not */
    public static Boolean creator = false;

    /* is the other player left game */
    public static Boolean otherLeft = false;

    /* persons connected to the server */
    public final static List<Person> personList = new ArrayList<>();

    /* games created on the server */
    public final static List<Game> gameList = new ArrayList<>();

    /* socket used for server connection */
    private static Socket socket = null;

    public static void main(String[] args) throws IOException {
        socket = ServerConf.getSocketByArgs(args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        connectServer();
        PageHelper.setStage(primaryStage);
        PageHelper.loadRoomsPage();
    }

    /**
     * To connect server
     */
    private void connectServer() {


        try {
            showEnterNameDialog();

            /**/
            serverConnection = new ServerConnection(socket);
            serverConnection.setConnectionName(name);
            serverConnection.start();

        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * To get name from the client at the start
     */
    private static void showEnterNameDialog() {

        do {
            TextInputDialog dialog = new TextInputDialog("");

            dialog.setTitle("Welcome The Chess ChessGame");
            dialog.setHeaderText("Look, a Text Input Dialog");
            dialog.setContentText("Please enter your name:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(n -> {
                if (!n.equalsIgnoreCase("")) {
                    name = n;
                }

            });
        } while (name.equalsIgnoreCase(""));
    }


}
