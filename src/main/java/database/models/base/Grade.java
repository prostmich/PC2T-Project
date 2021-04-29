package main.java.database.models.base;

/**
 * Grade object for storing student grade information
 */
public class Grade {

    private final String subject;
    private final double value;

    /**
     * Class constructor
     *
     * @param subject subject of grade
     * @param value   value of grade
     */
    public Grade(String subject, double value) {
        this.subject = subject;
        this.value = value;
    }

    /**
     * Gets a value of grade
     *
     * @return value of grade
     */
    public double getValue() {
        return value;
    }

    /**
     * Gets a subject of grade
     *
     * @return subject of grade
     */
    public String getSubject() {
        return subject;
    }


}
