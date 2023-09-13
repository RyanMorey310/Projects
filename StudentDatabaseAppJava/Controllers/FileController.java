package assignment.Controllers;
import assignment.Models.Student;

import java.io.*;
import java.util.ArrayList;

public class FileController {
    private static String FILE_NAME = "students.ser";

    public static void load() throws IOException, ClassNotFoundException {
        FileInputStream load = new FileInputStream(FILE_NAME);
        ObjectInputStream objLoad = new ObjectInputStream(load);
        StudentController.setStArray( (ArrayList<Student>) objLoad.readObject());
        objLoad.close();
        load.close();
    }
    public static void save() throws IOException {
        FileOutputStream save = new FileOutputStream(FILE_NAME);
        ObjectOutputStream object = new ObjectOutputStream(save);
        object.writeObject(StudentController.getStArray());
        object.close();
        save.close();
    }
}
