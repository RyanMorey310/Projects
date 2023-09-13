package assignment.View;

import assignment.Models.StudentModule;
import assignment.Models.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignment.Controllers.*;
import java.io.IOException;
import java.util.Optional;

public class View extends Application {
    public static void main(String[] args) {launch(args);}
    public void start(Stage stage) {
        //Student Lists
        ObservableList<String> stData = FXCollections.observableArrayList();
        ListView<String> stList;
        stList = new ListView<>(stData);

        //Module lists
        ObservableList<String> mdData = FXCollections.observableArrayList();
        ListView<String> mdList;
        mdList = new ListView<>(mdData);

        //Create Labels for the text fields FIRST TAB
        Label nameL = new Label("Name - ");
        TextField nameF = new TextField();

        Label idL = new Label("ID - ");
        TextField idF = new TextField();

        Label dobL = new Label("DoB - ");
        TextField dobF = new TextField();

        //Creating buttons for the application FIRST TAB
        Button addBut = new Button("Add");
        addBut.setOnAction(e ->  {
            try {
                StudentController.addFunc(nameF.getText(), idF.getText(), dobF.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            stData.setAll(StudentController.getStudentString());
            nameF.clear();
            idF.clear();
            dobF.clear();
        });

        Button remBut = new Button("Remove");
        remBut.setOnAction(e -> {
            int index = stList.getSelectionModel().getSelectedIndex();
            StudentController.removeFunc(index);
            stData.setAll(StudentController.getStudentString());
        });

        //HBox for the add and remove button FIRST TAB
        HBox buttons = new HBox(10, addBut, remBut);

        //Creating more buttons; load, save and exit FIRST TAB
        Button loadBut = new Button("Load");
        loadBut.setOnAction(e -> {
            try {
                    FileController.load();
                    stData.setAll(StudentController.getStudentString());
                    loadBut.setDisable(true);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button saveBut = new Button("Save");
        saveBut.setOnAction(e -> {
            try {
                FileController.save();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            loadBut.setDisable(true);
        });

        Button exitBut = new Button("Exit");
        exitBut.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save before exiting?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.APPLY.OK) {
                try {
                    FileController.save();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            stage.close();
        });
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Student List", new Label("Student Info"));
        Tab tab2 = new Tab("Modules"  , new Label("Add Module"));
        Tab tab3 = new Tab("Records" , new Label("Display Student Records"));

        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        //Creating the HBox for the save, load and exit buttons FIRST TAB
        HBox fileBut = new HBox(10, loadBut, saveBut, exitBut);
        fileBut.setAlignment((Pos.CENTER_RIGHT));

        //Creating the gridpane for the text fields and add/remove buttons FIRST TAB
        GridPane input = new GridPane();
        input.setHgap(10);
        input.setVgap(10);
        input.setPadding(new Insets(10));
        input.addRow(0, nameL, nameF);
        input.addRow(1, idL, idF);
        input.addRow(2, dobL, dobF);
        input.add(buttons, 0, 3, 2, 1);

        //VBox for everything below the add/remove buttons; the list and other buttons FIRST TAB
        VBox display = new VBox(10, new Label("Student Information\nOrder of info: Name-ID-DOB"), stList);
        display.setPadding(new Insets(10));
        VBox tabContent1 = new VBox(10, input, display, fileBut);

        //Getting second tab ready
        VBox tabContent2 = new VBox();
        tabContent2.setPadding(new Insets(10));
        tabContent2.setSpacing(10);
        tab2.setContent(tabContent2);

        // Create a dropdown menu for module selection SECOND TAB
        Label topSentence = new Label("Make Sure That The List Isn't Empty To See Dropdown Options.");
        Label studentLabel = new Label("Select Student:");
        ComboBox<String> studentDropdown = new ComboBox<>(stData);
        HBox forTopSentence = new HBox(topSentence);
        forTopSentence.setPadding(new Insets(10));
        tabContent2.getChildren().add(forTopSentence);
        HBox moduleBox = new HBox(studentLabel, studentDropdown);
        moduleBox.setSpacing(10);
        tabContent2.getChildren().add(moduleBox);

        // Create input fields for module name and grade SECOND TAB
        Label nameLabel = new Label("Module Name:");
        TextField nameField = new TextField();
        HBox nameBox = new HBox(nameLabel, nameField);
        nameBox.setSpacing(10);
        tabContent2.getChildren().add(nameBox);

        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();
        HBox gradeBox = new HBox(gradeLabel, gradeField);
        gradeBox.setSpacing(10);
        tabContent2.getChildren().add(gradeBox);

        // Create an "Add" button SECOND TAB
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String name = nameField.getText();
            String grade = gradeField.getText();
            Student student = StudentController.getStArray().get(studentDropdown.getSelectionModel().getSelectedIndex());
            StudentModule temp = new StudentModule(name, grade);
            student.addModule(temp);
            nameField.clear();
            gradeField.clear();
        });
        tabContent2.getChildren().add(addButton);

        //Getting the THIRD TAB ready
        VBox tabContent3 = new VBox();
        tabContent3.setPadding(new Insets(10));
        tabContent3.setSpacing(10);
        tab3.setContent(tabContent3);

        //The choose student dropdown menu for the user for the THIRD TAB
        Label studentLabelTab3 = new Label("Choose Student:");
        ComboBox<String> studentDropdownTab3 = new ComboBox<>(stData);
        HBox chooseStudentTab3 = new HBox(studentLabelTab3, studentDropdownTab3);
        chooseStudentTab3.setSpacing(10);
        tabContent3.getChildren().add(chooseStudentTab3);

        //Confirm button for THIRD TAB
        Button confirmBut = new Button("Confirm");
        confirmBut.setOnAction(e -> {
            Student student = StudentController.getStArray().get(studentDropdownTab3.getSelectionModel().getSelectedIndex());
            mdData.setAll(student.getModuleList().toString());
        });
        tabContent3.getChildren().add(confirmBut);

        //Adding module info observable to the THIRD TAB
        VBox display2 = new VBox(10, new Label("Module Info"), mdList);
        tabContent3.getChildren().add(display2);

        //Setting the stage
        Scene completed = new Scene(tabPane);
        tab1.setContent(tabContent1);
        tab2.setContent(tabContent2);
        stage.setScene(completed);
        stage.setTitle("MTU Student Information Management");
        stage.show();
    }

}
