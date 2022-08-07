package com.gui;

import com.db.AdminUtility;
import com.gui.model.HomeTableViewModel;
import com.service.SQLException_Exception;
import com.service.User;
import com.service.UserManager;
import com.service.UserManagerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TableView<HomeTableViewModel> table = new TableView<>();

    @FXML
    private TableColumn<HomeTableViewModel, String> col_type;

    @FXML
    private TableColumn<HomeTableViewModel, String> col_login;

    @FXML
    private TableColumn<HomeTableViewModel, String> col_password;

    @FXML
    private TableColumn<HomeTableViewModel, Button> col_modif;

    @FXML
    private TableColumn<HomeTableViewModel, Button> col_del;

    @FXML
    private Button createUserButton, refreshButton, logoutButton;

    @FXML
    private Text title;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    public ObservableList<HomeTableViewModel> list;

    public UserManager um;

    HomeTableViewModel row;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView createUserImage = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("registerBtnImage.png")).toExternalForm())));
        createUserButton.setGraphic(createUserImage);
        createUserButton.setTooltip(new Tooltip("Créer un nouvel utilisateur"));

        ImageView refreshImage = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("refresh.png")).toExternalForm())));
        createUserButton.setGraphic(refreshImage);
        createUserButton.setTooltip(new Tooltip("Rafraîchir la page..."));

        ImageView logoutImage = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("disconnectBtnImage.png")).toExternalForm())));
        createUserButton.setGraphic(logoutImage);
        createUserButton.setTooltip(new Tooltip("Se déconnecter"));

        list = FXCollections.observableArrayList();

        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_modif.setCellValueFactory(new PropertyValueFactory<>("modif"));
        col_del.setCellValueFactory(new PropertyValueFactory<>("del"));

        //retrieve users list
        um = new UserManagerService().getUserManagerPort();
        List<User> users;
        try {
            users = um.listUsers("mytoken");
        } catch (SQLException_Exception e) {
            throw new RuntimeException(e);
        }

        int nbUsers = users.size();
        title.setText("Il y a " + nbUsers + " utilisateurs...");

        Button[] modifButtons = new Button[nbUsers];
        Button[] delButtons = new Button[nbUsers];
        Tooltip modifTooltip = new Tooltip("modifier utilisateur");
        Tooltip delTooltip = new Tooltip("supprimer utilisateur");
        String[] types = new String[nbUsers];

        for(int i = 0; i < nbUsers; i++){
            modifButtons[i] = new Button();
            delButtons[i] = new Button();
            types[i] = "";

            //add images
            modifButtons[i].setGraphic(new ImageView(Objects.requireNonNull(getClass().getResource("modifyTask.png")).toExternalForm()));
            delButtons[i].setGraphic(new ImageView(Objects.requireNonNull(getClass().getResource("deleteTask.png")).toExternalForm()));

            //add tooltips
            modifButtons[i].setTooltip(modifTooltip);
            delButtons[i].setTooltip(delTooltip);

            //get user type
            try {
                types[i] = AdminUtility.isAdmin(users.get(i)) ? "admin" : "editor";
            } catch (SQLException_Exception | SQLException e) {
                throw new RuntimeException(e);
            }

            //attach modif actions
            modifButtons[i].setOnAction(event -> {
                //chargement page modif
                System.out.println("modif");
            });

            //attach del actions
            delButtons[i].setOnAction(event -> {
                //chargement page modif
                System.out.println("del");
            });

            //insert row into list
            row = new HomeTableViewModel(
                    types[i],
                    users.get(i).getLogin(),
                    users.get(i).getPassword(),
                    modifButtons[i],
                    delButtons[i]);

            list.add(row);
        }

        //insert list of rows into table
        table.setItems(list);
    }

    public void handleCreateUserButtonAction(ActionEvent actionEvent) {
        //chargement page de creation de user
        System.out.println("create user");
    }

    public void handleRefreshButtonAction(ActionEvent actionEvent) {
        //refreshpage
        System.out.println("refresh page");
    }

    public void handleLogoutButtonAction(ActionEvent actionEvent) {
        //go back to auth page
        System.out.println("logout");
    }

}
