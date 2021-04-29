package main.java.database.controllers.local;

import main.java.database.models.base.Person;
import main.java.database.models.base.University;
import main.java.database.models.window.*;

/**
 * Window for displaying the person's profile information stored in the local database
 */
public class ShowPersonInfo implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasPeople())
            return new WindowResponse(false, "There are no people at the university yet");
        FormResponse answerPersonId = new Form(
                "-- Enter Person ID you want to show information about --",
                new FormInput("Person ID: ", "personId", Integer.class)
        ).display();
        int personId = answerPersonId.findValueByName("personId");

        Person person = university.getPerson(personId);
        if (person == null)
            return new WindowResponse(false, "The person is not exists");
        university.printPerson(person);
        return new WindowResponse(true, "Success", false);
    }
}
