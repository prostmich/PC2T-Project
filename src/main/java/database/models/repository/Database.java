package main.java.database.models.repository;

import main.java.database.models.base.Grade;
import main.java.database.models.base.Person;
import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;
import main.java.database.models.common.Pair;
import main.java.database.services.repository.crud.DeleteQueries;
import main.java.database.services.repository.crud.InsertQueries;
import main.java.database.services.repository.crud.SelectQueries;
import main.java.database.utils.Helpers;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Database object for working with an external database
 */
public class Database {
    private static volatile Connection connection;
    private final SelectQueries sq = new SelectQueries();
    private final InsertQueries iq = new InsertQueries();
    private final DeleteQueries dq = new DeleteQueries();
    private boolean loggedIn = false;

    /**
     * Gets the actual connection to the external database
     *
     * @return JDBC connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Close connection to external database
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets connection to external database
     *
     * @param path path to a possibly existing external database file
     * @return true if the connection was successful, false otherwise
     */
    private boolean setConnection(Path path) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + path.toString());
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if the user logged in and can work with an external database
     *
     * @return true if the user logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Trying to authorize
     *
     * @param path path to a possibly existing external database file
     * @return true if the user logged in successfully, false otherwise
     */
    public boolean Login(Path path) {
        loggedIn = setConnection(path);
        return loggedIn;

    }

    /**
     * Get lists of students, and teachers stored in an external database
     *
     * @return list of people
     */
    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();
        people.addAll(sq.selectStudents());
        people.addAll(sq.selectTeachers());
        return people;
    }

    /**
     * Get person's profile stored in an external database
     *
     * @param personId person's identifier in the external database
     */
    public Person getPerson(int personId) {
        Student student = sq.selectStudent(personId);
        Teacher teacher = sq.selectTeacher(personId);
        if (student != null) return student;
        else if (teacher != null) return teacher;
        return null;
    }

    /**
     * Delete person from external database with all his relations
     *
     * @param personId person's identifier in the external database
     */
    public void deletePerson(int personId) {
    	Student student = sq.selectStudent(personId);
        Teacher teacher = sq.selectTeacher(personId);
        if (student != null) {
            dq.DeleteStudent(personId);
            return;
        }
        else if (teacher != null) {
        	dq.DeleteTeacher(personId);
        	return;
        }
        System.out.println("Person is not exists");
    }

    /**
     * Delete all records from all tables in the external database
     */
    public void clearTables() {
        dq.DeleteAllStudents();
        dq.DeleteAllTeachers();
    }

    /**
     * Insert new people to the external database
     *
     * @param people list of people to insert
     */
    public void insertPeople(List<Person> people) {
        List<Student> students = Helpers.filterStudents(people);
        List<Teacher> teachers = Helpers.filterTeachers(people);
        iq.insertStudents(students);
        iq.insertTeachers(teachers);
    }

    /**
     * Insert new grades to the external database
     *
     * @param grades list of pairs student:grade to insert
     */
    public void insertGrades(List<Pair<Student, Grade>> grades) {
        iq.insertGrades(grades);
    }

    /**
     * Insert new relations between teachers and students to the external database
     *
     * @param relations list of pairs teacher:student to insert
     */
    public void insertRelations(List<Pair<Teacher, Student>> relations) {
        iq.insertRelations(relations);
    }
}
