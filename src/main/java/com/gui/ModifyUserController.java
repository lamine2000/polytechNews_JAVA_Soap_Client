package com.gui;

import com.db.AdminUtility;
import com.gluonhq.charm.glisten.control.DropdownButton;
import com.gluonhq.charm.glisten.control.TextField;
import com.service.SQLException_Exception;
import com.service.User;
import com.service.UserManager;
import com.service.UserManagerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyUserController extends MotherController implements Initializable  {
    @FXML
    public AnchorPane container;
    @FXML
    public TextField loginField;
    @FXML
    public Button fermerButton;
    @FXML
    public TextField passwordField;
    @FXML
    public Text title;
    @FXML
    public Button validerButton;
    @FXML
    public CheckBox isAdminCheckbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fermerButton = new Button("Fermer");
        fermerButton.setTooltip(new Tooltip("Fermer la fenêtre"));

        isAdminCheckbox.setSelected(MotherController.type.contains("admin"));

        loginField.setText(MotherController.login);

        title.setText("Modifier utilisateur <" + MotherController.login + ">");
    }

    public void handleValiderButtonAction(ActionEvent actionEvent) throws SQLException, SQLException_Exception {
        UserManager um = new UserManagerService().getUserManagerPort();

        String login = loginField.getText();
        String password = passwordField.getText();

        if(!login.isEmpty() && !password.isEmpty()){
            String type = isAdminCheckbox.isSelected() ? "administrator" : "editor";
            int id = AdminUtility.getIdByLogin(MotherController.login);

            um.updateUser("mytoken", new User(login, password), type, id);

            handleFermerButtonAction(actionEvent);
        }

        else {
            AlertHelper.showAlert(
                    Alert.AlertType.ERROR,
                    loginField.getScene().getWindow(),
                    "Données insuffisantes",
                    "Tous les champs sont obogatoires ! \n Veuillez réessayer"
            );
        }
    }

    public void handleFermerButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) (loginField.getScene().getWindow());
        stage.close();
    }

}
