package com.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("views/Authentication.fxml"));
        Parent root = fxmlLoader.load();
        root.getStylesheets().add(String.valueOf(ClientApplication.class.getResource("stylesheets/Authentication.css")));
        stage.setTitle("polytechNews Client!");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}