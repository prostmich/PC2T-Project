package main.java.database.controllers.local;

import main.java.database.models.base.University;
import main.java.database.models.window.IWindow;
import main.java.database.models.window.WindowResponse;

/**
 * Window for displaying the expenses of the university for the payment of salaries and scholarships
 * based on data from a local database
 */
public class ShowUniversityExpenses implements IWindow {
    @Override
    public WindowResponse open(University university) {
        university.printExpenses();
        return new WindowResponse(true, "Success", false);
    }
}
