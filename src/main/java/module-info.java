module com.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.xml;
    requires jakarta.xml.ws;
    requires java.sql;
    requires com.gluonhq.charm.glisten;

    opens com.gui to javafx.fxml;
    opens com.service;
    opens com.model to javafx.base;
    exports com.gui;
}