module com.example.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment2 to javafx.fxml;
    exports com.example.assignment2.GUI;
    opens com.example.assignment2.GUI to javafx.fxml;
    exports com.example.assignment2.BusinessLogic;
    opens com.example.assignment2.BusinessLogic to javafx.fxml;
    exports com.example.assignment2.Model;
    opens com.example.assignment2.Model to javafx.fxml;
    exports com.example.assignment2;
}