package main.java.database.controllers.local;

import main.java.database.models.base.Person;
import main.java.database.models.base.University;
import main.java.database.models.window.*;

/**
 * Window for removing a person from the local database
 */
public class RemovePerson implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasPeople())
            return new WindowResponse(false, "There are no people at the university yet");
        FormResponse answerPersonId = new Form(
                "-- Enter Person ID to remove --",
                new FormInput("Person ID: ", "personId", Integer.class)
        ).display();
        int personId = answerPersonId.findValueByName("personId");
        Person person = university.getPerson(personId);
        if (person == null)
            return new WindowResponse(true, "The Person is not exists");
        university.removePerson(person);
        return new WindowResponse(true, "The Person was removed successfully");
    }

}
