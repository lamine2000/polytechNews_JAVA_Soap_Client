package com.gui;

import com.db.AdminUtility;
import com.service.SQLException_Exception;
import com.service.User;
import com.service.UserManager;
import com.service.UserManagerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AuthenticationController {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button submitButton;
    @FXML
    public AnchorPane container;
    @FXML
    public StackPane parentContainer;
    @FXML
    public GridPane gridPane;

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws SQLException_Exception, SQLException, IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        Window window = submitButton.getScene().getWindow();

        //link with soap service
        UserManager um = new UserManagerService().getUserManagerPort();

        //check if all the fields are filled
        if(login.isEmpty() || password.isEmpty()){
            AlertHelper.showAlert(
                    Alert.AlertType.ERROR,
                    window,
                    "Données insuffisantes!",
                    "Tous les champs sont obligatoires!\n Veuillez Réessayer");
        }

        //check if the user is an admin
        else if(AdminUtility.isAdmin(new User(login, password))){
            //navigate to admin page

            StackPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/home.fxml")));

            parentContainer = (StackPane)container.getParent();

            parentContainer.getStylesheets().remove(0);
            parentContainer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheets/home.css")).toExternalForm());

            parentContainer.getChildren().remove(container);
            parentContainer.getChildren().addAll(root.getChildren());

        }


        else{
            AlertHelper.showAlert(
                    Alert.AlertType.ERROR,
                    window,
                    "Accès refusé!",
                    "Les identifiants fournis ne sont pas ceux d'un admin!\n Veuillez Réessayer");
        }
    }
}
