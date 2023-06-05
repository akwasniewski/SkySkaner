module com.skyskaner.skyskaner {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.skyskaner.skyskaner to javafx.fxml;
    exports com.skyskaner.skyskaner;
}