package main.java.database.controllers.local;

import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;
import main.java.database.models.base.University;
import main.java.database.models.window.*;

/**
 * Window for adding/removing a student to/from the teacher's list in the local database
 */
public class AddRemoveStudentToTeacher implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasPeople())
            return new WindowResponse(false, "There are no people at the university yet");
        FormResponse answerOperation = new Form(
                """
                        -- Choose what you want to do --
                        A: Add student
                        R: Remove student""",
                new FormInput("Operation: ", "operation", String.class,
                        "(?i)^a(?:dd)?|r(?:emove)?$", "[A/R]")
        ).display();
        String operation = answerOperation.findValueByName("operation");
        switch (operation.toLowerCase()) {
            case "a", "add" -> operation = "add";
            case "r", "remove" -> operation = "remove";
        }

        FormResponse answerTeacherId = new Form(
                String.format("-- Enter Teacher ID who you want to %s the student --", operation),
                new FormInput("Teacher ID: ", "teacherId", Integer.class)
        ).display();
        int teacherId = answerTeacherId.findValueByName("teacherId");

        Teacher teacher = university.getTeacher(teacherId);
        if (teacher == null)
            return new WindowResponse(false, "The Teacher is not exists");

        FormResponse answerStudentId = new Form(
                String.format("-- Enter Student ID to %s --", operation),
                new FormInput("Student ID: ", "studentId", Integer.class)
        ).display();
        int studentId = answerStudentId.findValueByName("studentId");
        Student student = university.getStudent(studentId);
        if (student == null)
            return new WindowResponse(false, "The Student is not exists");
        if (operation.equals("add")) {
            teacher.addStudent(student);
            student.addTeacher(teacher);
            return new WindowResponse(true, "The Student was successfully added to this Teacher");
        } else {
            teacher.removeStudent(student);
            student.removeTeacher(teacher);
            return new WindowResponse(true, "The Student was successfully removed from this teacher");
        }
    }
}
