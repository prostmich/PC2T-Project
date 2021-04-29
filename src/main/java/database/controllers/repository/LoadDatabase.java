package main.java.database.controllers.repository;

import main.java.database.models.base.Person;
import main.java.database.models.base.University;
import main.java.database.models.repository.Database;
import main.java.database.models.window.*;

import java.util.List;

/**
 * Window for overwriting local database data based on external database
 */
public class LoadDatabase implements IWindow {
    private final Database database;

    public LoadDatabase(Database database) {
        this.database = database;
    }

    @Override
    public WindowResponse open(University university) {
        if (!database.isLoggedIn())
            return new WindowResponse(false, "Please connect to database first");
        FormResponse answerLoadConfirm = new Form(
                """
                        -- Are you seriously want to overwrite local database? --
                        Y: Yes
                        N: No""",
                new FormInput("Answer: ", "loadConfirm", String.class, "(?i)^y(?:es)?|no?$", "[Yes/No]")
        ).display();
        String loadConfirm = answerLoadConfirm.findValueByName("loadConfirm");
        switch (loadConfirm.toLowerCase()) {
            case "y", "yes" -> {
                List<Person> people = database.getPeople();
                university.setPeople(people);
                return new WindowResponse(true, "Loading information from the database was successful");
            }
            case "n", "no" -> {
                return new WindowResponse(true, "Loading was canceled");
            }
        }
        return new WindowResponse(true, "Success", false);
    }
}
