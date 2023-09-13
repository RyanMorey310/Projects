package assignment.Controllers;
import assignment.Models.*;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private static ArrayList<Student> stArray = new ArrayList<Student>();

    public static void setStArray(ArrayList<Student> temp) {
        stArray.clear();
        stArray.addAll(temp);
    }

    public static ArrayList<Student> getStArray() {
        return stArray;
    }

    public static List<String> getStudentString () {
        List<String> stStringList = new ArrayList<String>(stArray.size());
        for (Student student : stArray) {
            stStringList.add(student.toString());

        }
        return stStringList;
    }
    public static void addFunc(String name, String id, String dob) throws Exception {
        if (name.length() > 40) {
            throw new Exception("Name too long.");
        }
        if (id.length() != 9) {
            throw new Exception("ID must be 9 characters long.");
        }
        stArray.add(new Student(name, id, dob));
    }
    //Method for the remove function used on the remove button
    public static void removeFunc(int index) {
        if (index != -1) {
            stArray.remove(index);
        }
    }


}
