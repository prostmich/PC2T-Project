package main.java.database.utils;

import main.java.database.models.base.Person;
import main.java.database.models.base.Student;
import main.java.database.models.base.Teacher;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Object to store some useful helper methods
 */
public class Helpers {

    /**
     * Converts a list of people to a HashMap PersonId:Person
     *
     * @param people list of people to convert to HashMap
     * @param <T>    Person data type
     * @return HashMap PersonId:Person
     */
    @SuppressWarnings("unchecked")
    public static <T extends Person> HashMap<Integer, T> ListToHashMap(List<Person> people) {
        return (HashMap<Integer, T>) people
                .stream()
                .collect(Collectors.toMap(Person::getId, p -> (T) p));
    }

    /**
     * Filters only teachers from the people list
     *
     * @param people list of people to filter
     * @return list of teachers
     */
    public static List<Teacher> filterTeachers(List<Person> people) {
        return people
                .stream()
                .filter(t -> t instanceof Teacher)
                .map(t -> (Teacher) t)
                .collect(Collectors.toList());
    }

    /**
     * Filters only students from the people list
     *
     * @param people list of people to filter
     * @return list of students
     */
    public static List<Student> filterStudents(List<Person> people) {
        return people
                .stream()
                .filter(t -> t instanceof Student)
                .map(t -> (Student) t)
                .collect(Collectors.toList());
    }

    /**
     * Get string with placeholders for prepared INSERT or UPDATE SQL statement
     *
     * @param placeholderNumber number of placeholders, i.e. number of columns to insert/update
     * @param valuesNumber      number of values to insert/update
     * @return string in format (?, ?, ?...), (?...
     */
    public static String getSQLPlaceholders(int placeholderNumber, int valuesNumber) {
        String placeholders = IntStream.range(0, placeholderNumber).mapToObj(i -> "?").collect(Collectors.joining(", "));
        return String.join(", ", Collections.nCopies(valuesNumber, String.format("(%s)", placeholders)));
    }
}
