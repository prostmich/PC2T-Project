package main.java.database.controllers.local;

import main.java.database.models.base.Grade;
import main.java.database.models.base.Student;
import main.java.database.models.base.University;
import main.java.database.models.window.*;

/**
 * Window for setting a new grade for a student in the local database
 */
public class SetGrade implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasStudents())
            return new WindowResponse(false, "There are no students at the university yet");
        FormResponse answerGradeInfo = new Form(
                "-- Enter Student ID, Subject and Grade --",
                new FormInput[]{
                        new FormInput("Student ID: ", "studentId", Integer.class),
                        new FormInput("Subject: ", "subject", String.class),
                        new FormInput("Grade: ", "grade", Double.class, 1.0, 5.0)
                }
        ).display();
        int studentId = answerGradeInfo.findValueByName("studentId");
        String subject = (String) answerGradeInfo.getValues().get("subject").getValue();
        double grade = (Double) answerGradeInfo.getValues().get("grade").getValue();

        Student student = university.getStudent(studentId);
        if (student == null)
            return new WindowResponse(false, "The Student is not exists");
        student.addGrade(new Grade(subject, grade));
        return new WindowResponse(true, "The Grade was added successfully");
    }
}
