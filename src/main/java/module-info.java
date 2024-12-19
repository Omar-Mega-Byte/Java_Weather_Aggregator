module com.example.apitest {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.json;


    opens com.example.weathergui to javafx.fxml;
    exports com.example.weathergui;
}