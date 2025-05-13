module com.lock.stockit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires com.google.gson;

    opens com.lock.stockit to javafx.fxml;
    exports com.lock.stockit;
    exports com.lock.stockit.Controller;
    opens com.lock.stockit.Controller to javafx.fxml;
}