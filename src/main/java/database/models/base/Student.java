package main.java.database.models.base;

import main.java.database.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Object for storing student personal information
 */
public class Student extends Person {
    private List<Grade> grades;
    private List<Teacher> teachers;

    /**
     * Class constructor
     *
     * @param id      unique personal identification number
     * @param name    student's first name
     * @param surname student's last name
     * @param birthDate student's date of birth
     * @see Person
     */
    public Student(int id, String name, String surname, String birthDate) {
        super(id, name, surname, birthDate);
        teachers = new ArrayList<>();
        grades = new ArrayList<>();
    }

    
    /**
     * Sets student's teachers list
     * 
     * @param teachers
     */
    public void setTeachers(List<Teacher> teachers) {
    	this.teachers = teachers;
    }
    
    
    /**
     * Gets student's teachers list
     *
     * @return list of teachers
     */
    public List<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * Tests if student's teachers list is not empty
     *
     * @return true if the student has at least one teacher, false otherwise
     */
    public boolean hasTeachers() {
        return !teachers.isEmpty();
    }

    /**
     * Add a new teacher to student's teachers list
     *
     * @param teacher teacher for adding
     */
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    /**
     * Remove a teacher from student's teachers list
     *
     * @param teacher teacher for removing
     */
    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    /**
     * Show base information about student's teachers
     * (full name and identifier)
     */
    public void printTeachers() {
        teachers.forEach(t -> System.out.printf("%s (%d)\n", t.getFullName(), t.getId()));
    }

    /**
     * Add a new grade to a student's grade list
     *
     * @param grade grade for adding
     */
    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    
    /**
     * Sets student's grades list
     * 
     * @param grades
     */
    public void setGrades(List<Grade> grades) {
    	this.grades = grades;
    }
    
    /**
     * Gets student's grades list
     *
     * @return list of grades
     */
    public List<Grade> getGrades() {
        return grades;
    }

    /**
     * Gets the student's average grade
     *
     * @return average grade value or 0.0 if there are no grades
     */
    public double getAverage() {
        double average = grades
                .stream()
                .map(Grade::getValue)
                .reduce(0.0, Double::sum);
        return !grades.isEmpty() ? average / grades.size() : 0.0;
    }

    /**
     * Gets the student's scholarship value
     *
     * @return scholarship value
     */
    public double getScholarship() {
        return hasScholarship() ? Config.SCHOLARSHIP_VALUE : 0.0;
    }

    /**
     * Tests if the student receives a scholarship
     *
     * @return true if the student receives scholarship, false otherwise
     */
    public boolean hasScholarship() {
        return getAverage() <= Config.AVERAGE_FOR_SCHOLARSHIP && getAverage() != 0.0;
    }

    /**
     * Show abbreviated information about the student:
     * (full name, identifier and scholarship status)
     */
    @Override
    public void printSimpleInfo() {
        System.out.printf("%s (%d), scholarship - %s\n",
                this.getFullName(), this.getId(), this.hasScholarship() ? "Yes" : "No");
    }

    /**
     * Show full information about the student:
     * (identifier, first name, last name, date of birth, scholarship status and value)
     */
    @Override
    public void printInfo() {
        System.out.printf("""
                        ID: %d
                        Name: %s
                        Surname: %s
                        Date of birth: %s
                        Scholarship: %s (%.2f CZK)
                        """,
                this.getId(), this.getName(), this.getSurname(), this.getBirthDate(),
                this.hasScholarship() ? "Yes" : "No", this.getScholarship()
        );
    }
}