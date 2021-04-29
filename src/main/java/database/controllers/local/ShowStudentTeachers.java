package main.java.database.controllers.local;

import main.java.database.models.base.Student;
import main.java.database.models.base.University;
import main.java.database.models.window.*;

/**
 * Window for displaying a list of student teachers stored in the local database
 */
public class ShowStudentTeachers implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasPeople())
            return new WindowResponse(false, "There are no people at the university yet");
        FormResponse answerStudentId = new Form(
                "-- Enter Student ID whose teachers you want to show --",
                new FormInput("Student ID: ", "studentId", Integer.class)
        ).display();
        int studentId = answerStudentId.findValueByName("studentId");
        Student student = university.getStudent(studentId);
        if (student == null)
            return new WindowResponse(false, "The Student is not exists");
        if (!student.hasTeachers())
            return new WindowResponse(false, "The Student has not teachers");
        student.printTeachers();
        return new WindowResponse(true, "Success", false);
    }
}
