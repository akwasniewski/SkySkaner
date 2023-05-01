module com.skyskaner.skyskaner {
    requires javafx.controls;
    requires javafx.fxml;

        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;

    opens com.skyskaner.skyskaner to javafx.fxml;
    exports com.skyskaner.skyskaner;
}