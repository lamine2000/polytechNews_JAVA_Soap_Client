package com.gui.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class HomeTableViewModel {
    private final SimpleStringProperty type, login, password;
    private final SimpleObjectProperty<Button> modif, del;

    public HomeTableViewModel(String type, String login, String password, Button modif, Button del) {
        this.type = new SimpleStringProperty(type);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.modif = new SimpleObjectProperty<>(modif);
        this.del = new SimpleObjectProperty<>(del);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public Button getModif() {
        return modif.get();
    }

    public SimpleObjectProperty<Button> modifProperty() {
        return modif;
    }

    public Button getDel() {
        return del.get();
    }

    public SimpleObjectProperty<Button> delProperty() {
        return del;
    }
}
