package assignment.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class ModuleList implements Serializable {
    ArrayList<StudentModule> studentModuleList;
    
    public ModuleList() {
        studentModuleList = new ArrayList<>();
    }


}
