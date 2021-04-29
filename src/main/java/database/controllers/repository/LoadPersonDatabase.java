package main.java.database.controllers.repository;

import main.java.database.models.base.Person;
import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;
import main.java.database.models.base.University;
import main.java.database.models.repository.Database;
import main.java.database.models.window.*;

/**
 * Window for loading the person's profile stored in the external database
 */
public class LoadPersonDatabase implements IWindow {
    private final Database database;

    public LoadPersonDatabase(Database database) {
        this.database = database;
    }

    @Override
    public WindowResponse open(University university) {
    	if (!database.isLoggedIn())
            return new WindowResponse(false, "Please connect to database first");
        FormResponse answerPersonId = new Form("""
        		-- Enter Person ID you want to load from external database database --
        		Warning! Further actions may overwrite existing information""",
                new FormInput("Person ID: ", "personId", Integer.class)
        ).display();
        int personId = answerPersonId.findValueByName("personId");
        Person personDB = database.getPerson(personId);
        if (personDB == null) {
        	return new WindowResponse(false, "Person is not exists");
        }
        Person personRAM = university.getPerson(personId);
        if (personDB instanceof Student) {
        	Student studentDB = (Student) personDB;
        	if (personRAM == null)
        		university.insertStudent(studentDB);
        	else {
        		Student studentRAM = (Student) personRAM;
        		university.removeStudent(studentRAM);
        		studentRAM.setName(studentDB.getName());
        		studentRAM.setSurname(studentDB.getSurname());
        		studentRAM.setBirthDate(studentDB.getBirthDate());
        		studentRAM.setGrades(studentDB.getGrades());
        		studentRAM.setTeachers(studentDB.getTeachers());
        		university.insertStudent(studentRAM);
        	}
        }
        else {
        	Teacher teacherDB = (Teacher) personDB;
        	if (personRAM == null)
        		university.insertTeacher(teacherDB);
        	else {
        		Teacher teacherRAM = (Teacher) personRAM;
        		university.removeTeacher(teacherRAM);
        		teacherRAM.setName(teacherDB.getName());
        		teacherRAM.setSurname(teacherDB.getSurname());
        		teacherRAM.setBirthDate(teacherDB.getBirthDate());
        		teacherRAM.setStudents(teacherDB.getStudents());
        		university.insertTeacher(teacherRAM);
        	}
        }
        return new WindowResponse(true, "Person was loaded successfully");
    }
}
