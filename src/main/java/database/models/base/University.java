package main.java.database.models.base;

import main.java.database.models.common.Pair;
import main.java.database.utils.Helpers;

import java.util.*;

/**
 * Object for storing information about university organization
 */
public class University {
    private HashMap<Integer, Person> people;

    /**
     * Class constructor
     */
    public University() {
        this.people = new HashMap<>();
    }

    private Teacher findAvailableTeacher() {
        Optional<Teacher> teacherWithMinStudents = getAllTeachers()
                .stream()
                .min(Comparator.comparingInt(a -> a.getStudents().size()));
        return teacherWithMinStudents.orElse(null);
    }

    private Student findStudentWithoutTeacher() {
        Optional<Student> studentWithoutTeacher = getAllStudents()
                .stream()
                .filter(student -> student.getTeachers().isEmpty())
                .findFirst();
        return studentWithoutTeacher.orElse(null);
    }

    private List<Teacher> getAllTeachers() {
        return Helpers.filterTeachers(new ArrayList<>(people.values()));
    }

    private List<Student> getAllStudents() {
        return Helpers.filterStudents(new ArrayList<>(people.values()));
    }

    /**
     * Gets a teacher with a specific identifier
     *
     * @param id unique personal identifier
     * @return Teacher object, if found, null otherwise
     */
    public Teacher getTeacher(int id) {
        Person person = people.get(id);
        return (person instanceof Teacher) ? (Teacher) person : null;
    }

    /**
     * Gets a student with a specific identifier
     *
     * @param id unique personal identifier
     * @return Student object, if found, null otherwise
     */
    public Student getStudent(int id) {
        Person person = people.get(id);
        return (person instanceof Student) ? (Student) person : null;
    }

    /**
     * Gets a person (Teacher or Student) with a specific identifier
     *
     * @param id unique personal identifier
     * @return Person object, if found, null otherwise
     */
    public Person getPerson(int id) {
        return people.get(id);
    }

    /**
     * Gets a list of people at the university
     *
     * @return list of people
     */
    public List<Person> getPeople() {
        return new ArrayList<>(people.values());
    }

    /**
     * Sets a list of people present at the university
     *
     * @param people list of people
     */
    public void setPeople(List<Person> people) {
        this.people = Helpers.ListToHashMap(people);
    }

    /**
     * Tests if people list is not empty
     *
     * @return true if the list contains at least one person, false otherwise
     */
    public boolean hasPeople() {
        return !people.isEmpty();
    }

    /**
     * Tests if students list is not empty
     *
     * @return true if the list contains at least one student, false otherwise
     */
    public boolean hasStudents() {
        return !getAllStudents().isEmpty();
    }

    /**
     * Tests if teachers list is not empty
     *
     * @return true if the list contains at least one teacher, false otherwise
     */
    public boolean hasTeachers() {
        return !getAllTeachers().isEmpty();
    }

    /**
     * Gets list of relations teacher-student, i.e. who teaches whom
     *
     * @return list of pairs teacher-student
     */
    public List<Pair<Teacher, Student>> getTeacherStudentRelations() {
        List<Pair<Teacher, Student>> relations = new ArrayList<>();
        for (Teacher teacher : getAllTeachers()) {
            for (Student student : teacher.getStudents()) {
                relations.add(new Pair<>(teacher, student));
            }
        }
        return relations;
    }

    /**
     * Gets list of students grades
     *
     * @return list of pairs student-grade
     */
    public List<Pair<Student, Grade>> getAllGrades() {
        List<Pair<Student, Grade>> grades = new ArrayList<>();
        for (Student student : getAllStudents()) {
            for (Grade grade : student.getGrades()) {
                grades.add(new Pair<>(student, grade));
            }
        }
        return grades;
    }

    private int findAvailableId() {
        if (people.isEmpty())
            return 1;
        int maxId = Collections.max(people.keySet());
        for (int i = 1; i <= maxId; i++) {
            if (!people.containsKey(i))
                return i;
        }
        return maxId + 1;
    }

    /**
     * Insert new student it into the list of people in the university.
     * Also print the information about assignment to some teacher
     *
     * @param student student to insert
     */
    public void insertStudent(Student student) {
    	if (student.getId() == 0) {
	        int id = findAvailableId();
	        student.setId(id);
    	}
        System.out.println("\nStudent's identification number is " + student.getId());
        Teacher availableTeacher = findAvailableTeacher();
        if (availableTeacher != null) {
            System.out.printf("\nThe student was automatically assigned to teacher %s (%d)\n",
                    availableTeacher.getFullName(), availableTeacher.getId()
            );
            availableTeacher.addStudent(student);
            student.addTeacher(availableTeacher);
        } else
            System.out.println("""                                        
                    Unfortunately, there are no available teachers yet 
                    As soon as someone appears, it will automatically be assigned to this student"""
            );
        people.put(student.getId(), student);
    }

    /**
     * Insert new teacher into the list of people in the university.
     * Also print the information about assignment to some student
     *
     * @param teacher teacher to insert
     */
    public void insertTeacher(Teacher teacher) {
    	if (teacher.getId() == 0) {
            int id = findAvailableId();
            teacher.setId(id);
    	}

        System.out.println("\nTeacher's identification number is " + teacher.getId());
        Student studentWithoutTeacher = findStudentWithoutTeacher();
        if (studentWithoutTeacher != null) {
            System.out.printf("""                
                            The student %s (%d) currently does not have any teacher
                            This teacher will be automatically assigned to him""",
                    studentWithoutTeacher.getFullName(), studentWithoutTeacher.getId()
            );
            studentWithoutTeacher.addTeacher(teacher);
            teacher.addStudent(studentWithoutTeacher);
        }
        people.put(teacher.getId(), teacher);
    }

    /**
     * Remove a teacher from list of people in the university
     * Also remove him from his students' teacher lists
     *
     * @param teacher teacher to remove
     */
    public void removeTeacher(Teacher teacher) {
        teacher.getStudents().forEach(s -> s.removeTeacher(teacher));
        people.remove(teacher.getId());
    }

    /**
     * Remove a student from list of people in the university
     * Also remove him from his teachers' student lists
     *
     * @param student student to remove
     */
    public void removeStudent(Student student) {
        student.getTeachers().forEach(t -> t.removeStudent(student));
        people.remove(student.getId());
    }

    /**
     * Completely remove a person from list of people in the university
     * Also remove him from other lists
     *
     * @param person person to remove
     */
    public void removePerson(Person person) {
        if (person instanceof Teacher)
            this.removeTeacher((Teacher) person);
        else
            this.removeStudent((Student) person);
    }

    /**
     * Show full information about the person
     * (identifier, first name, last name, year of birth,
     * final salary for the teacher and amount of the scholarship (if any) for the student)
     *
     * @param person person to remove
     */
    public void printPerson(Person person) {
        if (person instanceof Student) {
            person.printInfo();
        } else {
            person.printInfo();
        }
    }

    /**
     * Show the list of university teachers
     * (full name, identifier and number of students)
     */
    public void printAllTeachers() {
        List<Teacher> teachers = getAllTeachers();
        teachers.sort(Comparator.comparingInt(a -> -a.getStudents().size()));
        System.out.println();
        teachers.forEach(teacher ->
                System.out.printf("%s (%d), number of students - %d\n",
                        teacher.getFullName(), teacher.getId(), teacher.getStudents().size())
        );
    }

    /**
     * Show a list of all people in the university, divided into teachers and students
     * (full name, identifier,
     * final salary for the teacher and amount of the scholarship (if any) for the student)
     */
    public void printPeopleBySurname() {
        List<Teacher> teachers = getAllTeachers();
        List<Student> students = getAllStudents();
        teachers.sort(Comparator.comparing(Person::getSurname));
        students.sort(Comparator.comparing(Person::getSurname));
        if (!teachers.isEmpty()) System.out.println("\nTeachers:");
        teachers.forEach(Teacher::printSimpleInfo);
        if (!students.isEmpty()) System.out.println("\nStudents:");
        students.forEach(Student::printSimpleInfo);
    }

    /**
     * Show the total expenses that the university covers in one month, divided by scholarships and gross salaries
     */
    public void printExpenses() {
        double scholarships = getAllStudents()
                .stream()
                .map(Student::getScholarship)
                .reduce(0.0, Double::sum);
        double salaries = getAllTeachers()
                .stream()
                .map(Teacher::getFinalSalary)
                .reduce(0.0, Double::sum);

        System.out.printf("Scholarships: %.2f Kč\n", scholarships);
        System.out.printf("Salaries: %.2f Kč\n", salaries);
        System.out.printf("Total: %.2f Kč\n", scholarships + salaries);
    }
}