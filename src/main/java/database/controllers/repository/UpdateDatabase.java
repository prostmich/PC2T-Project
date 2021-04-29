package main.java.database.controllers.repository;

import main.java.database.models.base.*;
import main.java.database.models.common.Pair;
import main.java.database.models.repository.Database;
import main.java.database.models.window.*;

import java.util.List;

/**
 * Window for overwriting external database data based on local database data
 */
public class UpdateDatabase implements IWindow {
    private final Database database;

    public UpdateDatabase(Database database) {
        this.database = database;
    }

    @Override
    public WindowResponse open(University university) {
        if (!database.isLoggedIn())
            return new WindowResponse(false, "Please connect to database first");
        FormResponse answerUpdateConfirm = new Form("""
                        -- Are you seriously want to rewrite database? --
                        Y: Yes
                        N: No""",
                new FormInput("Answer: ", "updateConfirm", String.class, "(?i)^y(?:es)?|no?$", "[Yes/No]")
        ).display();
        String updateConfirm = answerUpdateConfirm.findValueByName("updateConfirm");
        switch (updateConfirm.toLowerCase()) {
            case "y", "yes" -> {
                database.clearTables();
                List<Person> people = university.getPeople();
                List<Pair<Student, Grade>> grades = university.getAllGrades();
                List<Pair<Teacher, Student>> relationships = university.getTeacherStudentRelations();
                database.insertPeople(people);
                database.insertGrades(grades);
                database.insertRelations(relationships);
                return new WindowResponse(true, "Updating information in the database was successful");
            }
            case "n", "no" -> {
                return new WindowResponse(true, "Updating was canceled");
            }
        }
        return new WindowResponse(true, "Success", false);
    }
}
