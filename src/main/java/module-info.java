module ma.emsi.glvoiturefx.glvoiturefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.sql;


    opens ma.emsi.glvoiturefx to javafx.fxml;
    opens ma.emsi.glvoiturefx.entities to javafx.base;

    exports ma.emsi.glvoiturefx;
}
