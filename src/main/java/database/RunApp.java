package main.java.database;

import main.java.database.controllers.local.*;
import main.java.database.controllers.repository.*;
import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;
import main.java.database.models.base.University;
import main.java.database.models.repository.Database;
import main.java.database.models.window.*;
/**
 * Application for storing and managing people at the university with connection to an external database
 * 
 * @author Mikhail Smolnikov (221296)
 */
public class RunApp {
    public static void main(String[] args) {
        University university = new University();
        Database database = new Database();
        university.insertStudent(new Student(0, "Mikhail", "Smolnikov", "01.01.2000"));
        university.insertTeacher(new Teacher(0, "Ivo", "Palach", "01.01.2000"));
        university.insertStudent(new Student(0, "Jan", "Krycha", "01.01.2000"));
        university.insertTeacher(new Teacher(0, "Pavel", "Gratzo", "01.01.2000"));
        university.insertStudent(new Student(0, "Petr", "Pospisil", "01.01.2000"));
        while (true) {
            IWindow window = null;
            FormResponse answerMenu = new Form("""                      
                            [MAIN MENU]
                            1:\tAdd new person
                            2:\tSet grade for student
                            3:\tRemove person
                            4:\tShow all student teachers
                            5:\tAdd or remove student to teacher
                            6:\tShow information about person
                            7:\tShow teachers sorted by current number of students
                            8:\tShow all teacher students sorted by average grade
                            9:\tShow list of people sorted by surname
                            10:\tShow total university expenses
                            11:\tConnect to database
                            12:\tLoad data from database
                            13:\tUpdate data in the database
                            14:\tDelete person from database
                            15:\tLoad person from database
                            0:\tQuit""",
                    new FormInput("Menu: ", "menu", Integer.class, 0, 15)
            ).display();
            int menu = answerMenu.findValueByName("menu");
            switch (menu) {
                // local windows
                case 1 -> window = new AddPerson();
                case 2 -> window = new SetGrade();
                case 3 -> window = new RemovePerson();
                case 4 -> window = new ShowStudentTeachers();
                case 5 -> window = new AddRemoveStudentToTeacher();
                case 6 -> window = new ShowPersonInfo();
                case 7 -> window = new ShowTeachersByStudentNumber();
                case 8 -> window = new ShowTeacherStudentsByAverage();
                case 9 -> window = new ShowPeopleBySurname();
                // repository windows
                case 10 -> window = new ShowUniversityExpenses();
                case 11 -> window = new ConnectDatabase(database);
                case 12 -> window = new LoadDatabase(database);
                case 13 -> window = new UpdateDatabase(database);
                case 14 -> window = new DeletePersonDatabase(database);
                case 15 -> window = new LoadPersonDatabase(database);
                case 0 -> {
                    Database.closeConnection();
                    return;
                }
            }
            if (window != null) {
                WindowResponse response = window.open(university);
                String status = response.getStatus() ? "SUCCESS" : "ERROR";
                if (response.isDisplayed())
                    System.out.printf("\n[%s] %s\n", status, response.getMessage());
            }
        }
    }
}
