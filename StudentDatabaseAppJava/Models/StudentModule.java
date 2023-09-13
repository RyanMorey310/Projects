package assignment.Models;

import java.io.Serializable;

public class StudentModule implements Serializable {
    public String name;
    public String grade;

    public StudentModule(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public void setName(String n) {
        this.name = n;
    }
    public String getName() {
        return name;
    }
    public void setGrade(String g) {
        this.grade = g;
    }
    public String getGrade() {
        return grade;
    }

    public String toString() {
        return "Name:" + name + ", Grade: " + grade;
    }
}
