package main.java.database.services.repository.crud;

import main.java.database.models.base.Grade;
import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;
import main.java.database.models.repository.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Object for implementing methods for receiving data from the external database
 */
public class SelectQueries {

    /**
     * Gets student data from the table "students" by his identifier
     *
     * @param studentId student's identifier to get
     * @return student
     */
    public Student selectStudent(int studentId) {
        Connection conn = Database.getConnection();
        String selectStudent = "SELECT * FROM students WHERE id = ?";
        Student student = null;
        try (PreparedStatement prStmt = conn.prepareStatement(selectStudent)) {
            prStmt.setInt(1, studentId);
            ResultSet rs = prStmt.executeQuery();
            while (rs.next()) {
                student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthDate")
                );
                List<Teacher> teachers = selectStudentTeachers(student);
                List<Grade> grades = selectStudentGrades(student);
                student.setTeachers(teachers);
                student.setGrades(grades);
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets teacher data from the table "teachers" by his identifier
     *
     * @param teacherId teacher's identifier to get
     * @return teacher
     */
    public Teacher selectTeacher(int teacherId) {
        Connection conn = Database.getConnection();
        String selectStudent = "SELECT id, name, surname, birthDate FROM teachers WHERE id = ?";
        Teacher teacher = null;
        try (PreparedStatement prStmt = conn.prepareStatement(selectStudent)) {
            prStmt.setInt(1, teacherId);
            ResultSet rs = prStmt.executeQuery();
            while (rs.next()) {
                teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthDate")
                );
                List<Student> students = selectTeacherStudents(teacher);
                teacher.setStudents(students);
            }
            return teacher;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all teachers data from the table "teachers"
     *
     * @return list of teachers
     */
    public List<Teacher> selectTeachers() {
        Connection conn = Database.getConnection();
        String selectTeachers = "SELECT * FROM teachers";
        List<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement prStmt = conn.prepareStatement(selectTeachers)) {
            ResultSet rs = prStmt.executeQuery();
            while (rs.next()) {
            	Teacher teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthDate")
                );
                List<Student> students = selectTeacherStudents(teacher);
                teacher.setStudents(students);
                teachers.add(teacher);
            }
            return teachers;
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all students data from the table "students"
     *
     * @return list of students
     */
    public List<Student> selectStudents() {
        Connection conn = Database.getConnection();
        String selectStudents = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (PreparedStatement prStmt = conn.prepareStatement(selectStudents)) {
            ResultSet rs = prStmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthDate")
                );
                List<Teacher> teachers = selectStudentTeachers(student);
                List<Grade> grades = selectStudentGrades(student);
                student.setTeachers(teachers);
                student.setGrades(grades);
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all teacher students
     *
     * @param teacher the teacher whose students we want to get
     * @return list of teacher's students
     */
    private List<Student> selectTeacherStudents(Teacher teacher) {
        Connection conn = Database.getConnection();
        String selectTeacherStudents = """
                SELECT s.*
                FROM students AS s 
                JOIN teacher_student AS t_s 
                ON s.id = t_s.student_id
                WHERE t_s.teacher_id = ?
                """;
        List<Student> students = new ArrayList<>();
        try (PreparedStatement prStmt = conn.prepareStatement(selectTeacherStudents)) {
            prStmt.setInt(1, teacher.getId());
            ResultSet rs = prStmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthDate")
                ));
            }
            return students;
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all student teachers
     *
     * @param student the student whose teachers we want to get
     * @return list of student's teachers
     */
    private List<Teacher> selectStudentTeachers(Student student) {
        Connection conn = Database.getConnection();
        String selectStudentTeachers = """
                SELECT t.*
                FROM teachers AS t 
                JOIN teacher_student AS t_s 
                ON t.id = t_s.teacher_id
                WHERE t_s.student_id = ?
                """;
        List<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement prStmt = conn.prepareStatement(selectStudentTeachers)) {
            prStmt.setInt(1, student.getId());
            ResultSet rs = prStmt.executeQuery();
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthDate")
                ));
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all student grades
     *
     * @param student the student whose grades we want to get
     * @return list of student's grades
     */
    private List<Grade> selectStudentGrades(Student student) {
        Connection conn = Database.getConnection();
        String selectStudentGrades = """
                SELECT *
                FROM grades
                WHERE student_id = ?
                """;
        List<Grade> grades = new ArrayList<>();
        try (PreparedStatement prStmt = conn.prepareStatement(selectStudentGrades)) {
            prStmt.setInt(1, student.getId());
            ResultSet rs = prStmt.executeQuery();
            while (rs.next()) {
                grades.add(new Grade(
                        rs.getString("subject"),
                        rs.getDouble("value")
                ));
            }
            return grades;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
