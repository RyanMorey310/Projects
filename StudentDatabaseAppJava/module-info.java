module assignment {
    requires javafx.controls;
    requires javafx.fxml;

    opens assignment.View to javafx.fxml, javafx.controls;
    opens assignment.Models to javafx.fxml, javafx.controls;
    opens assignment.Controllers to javafx.fxml, javafx.controls;
    exports assignment.View;
    exports assignment.Models;
    exports assignment.Controllers;
}