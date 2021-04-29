package main.java.database.controllers.local;

import main.java.database.models.base.University;
import main.java.database.models.window.IWindow;
import main.java.database.models.window.WindowResponse;

/**
 * Window for displaying a list of people, sorted by surname stored, stored in the local database
 */
public class ShowPeopleBySurname implements IWindow {
    @Override
    public WindowResponse open(University university) {
        if (!university.hasPeople())
            return new WindowResponse(false, "There are no people at the university yet");
        university.printPeopleBySurname();
        return new WindowResponse(true, "Success", false);
    }
}
