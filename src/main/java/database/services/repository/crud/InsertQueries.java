package main.java.database.services.repository.crud;

import main.java.database.models.base.Grade;
import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;
import main.java.database.models.common.Pair;
import main.java.database.models.repository.Database;
import main.java.database.utils.Helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Object for implementing methods for inserting data to the external database
 */
public class InsertQueries {
    /**
     * Insert new students to the table "students"
     * @param students list of students
     */
    public void insertStudents(List<Student> students) {
        Connection conn = Database.getConnection();
        String insertStudents = "INSERT INTO students (id, name, surname, year) VALUES "
                + Helpers.getSQLPlaceholders(4, students.size());
        try (PreparedStatement prStmt = conn.prepareStatement(insertStudents)) {
            for (int i = 0; i < students.size(); i++) {
                prStmt.setInt(i * 4 + 1, students.get(i).getId());
                prStmt.setString(i * 4 + 2, students.get(i).getName());
                prStmt.setString(i * 4 + 3, students.get(i).getSurname());
                prStmt.setString(i * 4 + 4, students.get(i).getBirthDate());
            }
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Insert new teachers to the table "teachers"
     * @param teachers list of teachers
     */
    public void insertTeachers(List<Teacher> teachers) {
        Connection conn = Database.getConnection();
        String insertTeachers = "INSERT INTO teachers (id, name, surname, year) VALUES "
                + Helpers.getSQLPlaceholders(4, teachers.size());
        try (PreparedStatement prStmt = conn.prepareStatement(insertTeachers)) {
            for (int i = 0; i < teachers.size(); i++) {
                prStmt.setInt(i * 4 + 1, teachers.get(i).getId());
                prStmt.setString(i * 4 + 2, teachers.get(i).getName());
                prStmt.setString(i * 4 + 3, teachers.get(i).getSurname());
                prStmt.setString(i * 4 + 4, teachers.get(i).getBirthDate());
            }
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert new grades to the table "grades"
     * @param grades list of pairs student:grade
     */
    public void insertGrades(List<Pair<Student, Grade>> grades) {
        Connection conn = Database.getConnection();
        String insertGrades = "INSERT INTO grades (student_id, subject, value) VALUES "
                + Helpers.getSQLPlaceholders(3, grades.size());
        try (PreparedStatement prStmt = conn.prepareStatement(insertGrades)) {
            for (int i = 0; i < grades.size(); i++) {
                prStmt.setInt(i * 3 + 1, grades.get(i).getKey().getId());
                prStmt.setString(i * 3 + 2, grades.get(i).getValue().getSubject());
                prStmt.setDouble(i * 3 + 3, grades.get(i).getValue().getValue());
            }
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Insert new relations between teachers and students to the table "teacher_student"
     * @param relations list of pairs student:teacher
     */
    public void insertRelations(List<Pair<Teacher, Student>> relations) {
        Connection conn = Database.getConnection();
        String insertRelations = "INSERT INTO teacher_student (teacher_id, student_id) VALUES "
                + Helpers.getSQLPlaceholders(2, relations.size());
        try (PreparedStatement prStmt = conn.prepareStatement(insertRelations)) {
            for (int i = 0; i < relations.size(); i++) {
                prStmt.setInt(i * 2 + 1, relations.get(i).getKey().getId());
                prStmt.setInt(i * 2 + 2, relations.get(i).getValue().getId());
            }
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
