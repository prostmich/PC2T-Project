package main.java.database.services.repository.crud;

import main.java.database.models.repository.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Object for implementing methods for deleting data from an external database
 */
public class DeleteQueries {
    /**
     * Delete student from the table "students" by his identifier
     *
     * @param studentId student's identifier to delete
     */
    public void DeleteStudent(int studentId) {
        Connection conn = Database.getConnection();
        String deleteStudent = "DELETE FROM students WHERE id = ?"; // TODO: did not delete relationships and grades
        try (PreparedStatement prStmt = conn.prepareStatement(deleteStudent)) {
            prStmt.setInt(1, studentId);
            int affectedRowsCount = prStmt.executeUpdate();
            if (affectedRowsCount == 0)
            	System.out.println("Student is not exists");
            else
            	System.out.println("Student was deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete teacher from the table "teachers" by his identifier
     *
     * @param teacherId teacher's identifier to delete
     */
    public void DeleteTeacher(int teacherId) {
        Connection conn = Database.getConnection();
        String deleteTeacher = "DELETE FROM teachers WHERE id = ?";
        try (PreparedStatement prStmt = conn.prepareStatement(deleteTeacher)) {
            prStmt.setInt(1, teacherId);
            int affectedRowsCount = prStmt.executeUpdate();
            if (affectedRowsCount == 0)
            	System.out.println("Teacher is not exists");
            else
            	System.out.println("Teacher was deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all students from the table "students"
     */
    public void DeleteAllStudents() {
        Connection conn = Database.getConnection();
        String deleteTeacher = "DELETE FROM students";
        try (PreparedStatement prStmt = conn.prepareStatement(deleteTeacher)) {
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all teachers from the table "teachers"
     */
    public void DeleteAllTeachers() {
        Connection conn = Database.getConnection();
        String deleteTeacher = "DELETE FROM teachers";
        try (PreparedStatement prStmt = conn.prepareStatement(deleteTeacher)) {
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all grades from the table "grades"
     */
    public void DeleteAllGrades() {
        Connection conn = Database.getConnection();
        String deleteTeacher = "DELETE FROM grades";
        try (PreparedStatement prStmt = conn.prepareStatement(deleteTeacher)) {
            prStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
