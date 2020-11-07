package com.project.client.utils;


import com.project.client.ClientMain;
import com.project.client.constants.Directory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PageHelper {

    private enum Page {

        ROOMS("rooms"),
        GAME_SCREEN("gameScreen");

        private final String pageName;

        /**
         * @param pageName name of the fxml file
         */
        Page(String pageName) {
            this.pageName = pageName;

        }

        /**/
        private String getFileName() {
            return pageName + ".fxml";
        }

        private String getPath() {
            return Directory.PAGE_FOLDER.getPath() + "/" + getFileName();
        }

        private FXMLLoader getLoader() {
            return new FXMLLoader(ClientMain.class.getClass().getResource(getPath()));
        }

    }

    /**
     * Main Stage for JavaFX application
     */
    private static Stage stage;

    /**
     * To set main stage loaded at the start
     *
     * @param stage stage will be set
     */
    public static void setStage(Stage stage) {
        PageHelper.stage = stage;
        PageHelper.stage.setOnCloseRequest(e -> System.exit(0));
    }

    /**
     * To load {@param page}
     *
     * @param page page wanted to be loaded
     * @throws IOException ..
     */
    private static void loadPage(Page page) throws IOException {

        if (stage == null) {
            return;
        }

        FXMLLoader loader = page.getLoader();
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**/
    public static void loadRoomsPage() throws IOException {
        loadPage(Page.ROOMS);
    }

    public static void loadGameScreenPage() throws IOException {
        loadPage(Page.GAME_SCREEN);
    }

}

