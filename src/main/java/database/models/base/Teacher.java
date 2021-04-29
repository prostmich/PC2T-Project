package main.java.database.models.base;

import main.java.database.config.Config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Object for storing student personal information
 */
public class Teacher extends Person {
    private List<Student> students;

    /**
     * Class constructor
     *
     * @param id      unique personal identification number
     * @param name    teacher's first name
     * @param surname teacher's last name
     * @param birthDate teacher's date of birth
     * @see Person
     */
    public Teacher(int id, String name, String surname, String birthDate) {
        super(id, name, surname, birthDate);
        this.students = new ArrayList<>();
    }    
    
    
    /**
     * Sets teacher's students list
     * 
     * @param students
     */
    public void setStudents(List<Student> students) {
    	this.students = students;
    }

    /**
     * Gets teacher's students list
     *
     * @return list of teachers
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Tests if teacher's students list is not empty
     *
     * @return true if the teacher has at least one student, false otherwise
     */
    public boolean hasStudents() {
        return !students.isEmpty();
    }

    /**
     * Add a new student to teacher's students list
     *
     * @param student student for adding
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Remove a student from teacher's students list
     *
     * @param student student for removing
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    private double getAwardsForScholarships() {
        int scholarshipsCount = (int) students
                .stream()
                .filter(Student::hasScholarship)
                .count();
        return scholarshipsCount * Config.AWARD_FOR_SCHOLARSHIPS;
    }

    /**
     * Gets teacher's gross salary, i.e. salary before tax
     *
     * @return gross salary
     */
    public double getGrossSalary() {
        return students.size() * Config.AWARD_FOR_STUDENT + getAwardsForScholarships();
    }

    /**
     * Gets teacher's final salary, i.e. salary after tax
     *
     * @return final salary
     */
    public double getFinalSalary() {
        return getGrossSalary() * (1 - Config.TAX / 100);
    }

    /**
     * Show information about the teacher's students, sorted by average grade:
     * (full name, identifier and average grade value)
     */
    public void printStudentsByAverage() {
        List<Student> studentList = students;
        studentList.sort(Comparator.comparingDouble(Student::getAverage));
        System.out.println();
        studentList.forEach(s ->
                System.out.printf("%s (%d), average grade - %.1f\n", s.getFullName(), s.getId(), s.getAverage())
        );
    }

    /**
     * Show full information about the teacher:
     * (identifier, first name, last name, date of birth and final salary)
     */
    @Override
    public void printInfo() {
        System.out.printf("""
                        ID: %d
                        Name: %s
                        Surname: %s
                        Date of birth: %s
                        Salary: %.2f CZK
                        """,
                this.getId(), this.getName(), this.getSurname(), this.getBirthDate(), this.getFinalSalary()
        );
    }

    /**
     * Show abbreviated information about the teacher:
     * full name, identifier and final salary
     */
    @Override
    public void printSimpleInfo() {
        System.out.printf("%s (%d), salary - %.2f CZK\n",
                this.getFullName(), this.getId(), this.getFinalSalary());
    }

}