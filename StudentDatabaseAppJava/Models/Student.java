package assignment.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private String name;
    private String dob;
    private String ID;
    private ArrayList<StudentModule> stStudentModuleList;

    public Student(String name, String dob, String ID) {
        this.name = name;
        this.dob = dob;
        this.ID = ID;
        stStudentModuleList = new ArrayList<>();
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setDob(String d) {
        this.dob = d;
    }

    public void setID(String i) {
        this.ID = i;
    }
    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getID() {
        return ID;
    }
    public void addModule(StudentModule temp) {
        stStudentModuleList.add(temp);
    }
    public ArrayList<StudentModule> getModuleList() {
        return stStudentModuleList;
    }
    public String toString(){
        return name + "," + dob + "," + ID;
    }
}
