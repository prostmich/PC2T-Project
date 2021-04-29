package main.java.database.controllers.local;

import main.java.database.models.base.Teacher;
import main.java.database.models.base.University;
import main.java.database.models.window.*;

/**
 * Window for displaying a list of student teachers, sorted by average grade, stored in the local database
 */
public class ShowTeacherStudentsByAverage implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasPeople())
            return new WindowResponse(false, "There are no people at the university yet");
        FormResponse answerTeacherId = new Form(
                "-- Enter Teacher ID whose students you want to show --",
                new FormInput("Teacher ID: ", "teacherId", Integer.class)
        ).display();
        int teacherId = answerTeacherId.findValueByName("teacherId");

        Teacher teacher = university.getTeacher(teacherId);
        if (teacher == null)
            return new WindowResponse(false, "The Teacher is not exists");
        if (!teacher.hasStudents())
            return new WindowResponse(false, "The Teacher has not students");
        teacher.printStudentsByAverage();
        return new WindowResponse(true, "Success", false);
    }
}
