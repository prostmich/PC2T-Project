package main.java.database.controllers.local;

import main.java.database.models.base.University;
import main.java.database.models.window.IWindow;
import main.java.database.models.window.WindowResponse;

/**
 * Window for displaying a list of teachers, sorted by the number of students, stored in the local database
 */
public class ShowTeachersByStudentNumber implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasTeachers())
            return new WindowResponse(false, "There are no teachers at the university yet");
        university.printAllTeachers();
        return new WindowResponse(true, "Success", false);
    }
}
