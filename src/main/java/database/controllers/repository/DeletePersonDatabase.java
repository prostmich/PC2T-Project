package main.java.database.controllers.repository;

import main.java.database.models.base.University;
import main.java.database.models.repository.Database;
import main.java.database.models.window.*;

/**
 * Window for deleting a person from an external database
 */
public class DeletePersonDatabase implements IWindow {
    private final Database database;

    public DeletePersonDatabase(Database database) {
        this.database = database;
    }

    @Override
    public WindowResponse open(University university) {
        FormResponse answerPersonId = new Form(
                "-- Enter Person ID you want to delete from database --",
                new FormInput("Person ID: ", "personId", Integer.class)
        ).display();
        int personId = answerPersonId.findValueByName("personId");
        database.deletePerson(personId);
        return new WindowResponse(true, "Deleting information from database was successful");
    }
}
