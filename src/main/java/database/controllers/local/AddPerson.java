package main.java.database.controllers.local;

import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;
import main.java.database.models.base.University;
import main.java.database.models.window.*;

/**
 * Window for adding a new person to the local database
 */
public class AddPerson implements IWindow {
    @Override
    public WindowResponse open(University university) {
        FormResponse answerRole = new Form(
                """
                        -- Choose the role of the person to add --
                        S: Student
                        T: Teacher""",
                new FormInput("Person role: ", "role", String.class, "(?i)^s(?:tudent)?|t(?:eacher)?$", "[S/T]")
        ).display();
        FormResponse answerPersonInfo = new Form(
                "-- Input name, surname and year of birth --",
                new FormInput[]{
                        new FormInput("Name: ", "name", String.class),
                        new FormInput("Surname: ", "surname", String.class),
                        new FormInput("Date of birth: ", "birthDate", String.class, "^\\d{1,2}.\\d{1,2}.\\d{4}$", "22.1.2021"),
                }
        ).display();
        String role = answerRole.findValueByName("role");
        String name = answerPersonInfo.findValueByName("name");
        String surname = answerPersonInfo.findValueByName("surname");
        String birthDate = answerPersonInfo.findValueByName("birthDate");
        switch (role.toLowerCase()) {
            case "student", "s" -> {
                university.insertStudent(new Student(0, name, surname, birthDate));
                return new WindowResponse(true, "The Student was added successfully");
            }
            case "teacher", "t" -> {
                university.insertTeacher(new Teacher(0, name, surname, birthDate));
                return new WindowResponse(true, "The Teacher was added successfully");
            }
        }
        return new WindowResponse(true, "Success", false);
    }

}
