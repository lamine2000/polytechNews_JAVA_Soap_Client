package com.gui;

import com.db.AdminUtility;
import com.model.HomeTableViewModel;
import com.service.SQLException_Exception;
import com.service.User;
import com.service.UserManager;
import com.service.UserManagerService;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private Button validerButton;

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;


    public ObservableList<HomeTableViewModel> list;

    public UserManager um;

    HomeTableViewModel row;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView createUserImage = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("images/addUser.png")).toExternalForm())));
        createUserButton.setGraphic(createUserImage);
        createUserButton.setTooltip(new Tooltip("Créer un nouvel utilisateur"));

        ImageView refreshImage = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("images/refresh.png")).toExternalForm())));
        refreshButton.setGraphic(refreshImage);
        refreshButton.setTooltip(new Tooltip("Rafraîchir la page..."));

        ImageView logoutImage = new ImageView(new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("images/disconnectBtnImage.png")).toExternalForm())));
        logoutButton.setGraphic(logoutImage);
        logoutButton.setText("Logout");
        logoutButton.setTooltip(new Tooltip("Se déconnecter"));

        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_modif.setCellValueFactory(new PropertyValueFactory<>("modif"));
        col_del.setCellValueFactory(new PropertyValueFactory<>("del"));

        //retrieve data
        updateTable();
    }

    public void handleCreateUserButtonAction(ActionEvent actionEvent) {
        //chargement page de creation de user
        System.out.println("create user");
    }

    public void handleRefreshButtonAction(ActionEvent actionEvent) {
        //rotation animation
        Timeline timeLine = new Timeline();
        KeyValue kv = new KeyValue(refreshButton.getGraphic().rotateProperty(), 720, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1.2), kv);
        timeLine.getKeyFrames().add(kf);

        timeLine.play();
        timeLine.setOnFinished((ActionEvent event) -> {
            refreshButton.getGraphic().setRotate(0);
            //refreshpage
            updateTable();
        });
    }

    public void handleLogoutButtonAction(ActionEvent actionEvent) throws IOException {
        //go back to auth page
        StackPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/Authentication.fxml")));

        parentContainer = (StackPane)container.getParent();

        parentContainer.getStylesheets().remove(0);
        parentContainer.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheets/Authentication.css")).toExternalForm());

        parentContainer.getChildren().remove(container);
        parentContainer.getChildren().addAll(root.getChildren());
    }

    private void loadModif(String login, String password, String type) throws IOException {
        MotherController.login = login;
        MotherController.password = password;
        MotherController.type = type;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/modifyUser.fxml")));
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheets/modif.css")).toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loadCreate(ActionEvent event){

    }

    private void updateTable(){
        //remove content
        list = FXCollections.observableArrayList();

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
            modifButtons[i].setGraphic(new ImageView(Objects.requireNonNull(getClass().getResource("images/modifyTask.png")).toExternalForm()));
            delButtons[i].setGraphic(new ImageView(Objects.requireNonNull(getClass().getResource("images/deleteTask.png")).toExternalForm()));

            //add tooltips
            modifButtons[i].setTooltip(modifTooltip);
            delButtons[i].setTooltip(delTooltip);

            //get user type
            try {
                types[i] = AdminUtility.isAdminByLogin(users.get(i).getLogin()) ? "admin" : "editor";
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //attach ids
            modifButtons[i].setId(users.get(i).getLogin() + "°" + types[i] + "°" + users.get(i).getPassword());
            delButtons[i].setId(users.get(i).getLogin());

            //attach modif actions
            modifButtons[i].setOnAction(event -> {
                //chargement page modif
                String[] id = ((Button)(event.getSource()))
                        .getId()
                        .split("°");

                String login = id[0];
                String type = id[1];
                String password = id[2];

                try {
                    loadModif(login, password, type);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

        table.refresh();
    }

}
