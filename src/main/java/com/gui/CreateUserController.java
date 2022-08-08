package com.gui;

import com.gluonhq.charm.glisten.control.TextField;
import com.service.SQLException_Exception;
import com.service.User;
import com.service.UserManager;
import com.service.UserManagerService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    public AnchorPane container;
    public TextField loginField;
    public Button fermerButton;
    public TextField passwordField;
    public Text title;
    public Button validerButton;
    public CheckBox isAdminCheckbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fermerButton = new Button("Fermer");
        fermerButton.setTooltip(new Tooltip("Fermer la fenêtre"));

        validerButton.setText("Valider");
        validerButton.setTooltip(new Tooltip("Valider la création"));

        isAdminCheckbox.setSelected(false);

        title.setText("Nouvel utilisateur");
    }

    public void handleValiderButtonAction(ActionEvent actionEvent) throws SQLException, SQLException_Exception {
        UserManager um = new UserManagerService().getUserManagerPort();

        String login = loginField.getText();
        String password = passwordField.getText();

        if(!login.isEmpty() && !password.isEmpty()){
            String type = isAdminCheckbox.isSelected() ? "administrator" : "editor";

            um.addUser("mytoken", new User(login, password), type);

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
