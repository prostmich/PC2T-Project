package main.java.database.models.base;


/**
 * Abstract object for storing base personal information
 */
public abstract class Person {
    private int id;
    private String name;
    private String surname;
    private String birthDate;

    /**
     * Class constructor
     *
     * @param id      unique personal identification number
     * @param name    person's first name
     * @param surname person's last name
     * @param birthDate person's date of birth
     */
    public Person(int id, String name, String surname, String birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    /**
     * Gets the person's first name
     *
     * @return first name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the person's first name
     *
     * @param name first name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the person's last name
     *
     * @return last name
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * Sets the person's last name
     *
     * @param surname last name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the person's full name
     *
     * @return full name
     */
    public String getFullName() {
        return name + " " + surname;
    }
    

    /**
     * Gets the person's date of birth
     *
     * @return date of birth
     */
    public String getBirthDate() {
        return birthDate;
    }
    
    /**
     * Sets the person's date of birth
     *
     * @param birthDate date of birth
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the person's unique identification number
     *
     * @return unique identification number
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the person's unique identification number
     * 
     * @param id unique identification number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Show full information about the person
     * (identifier, first name, last name and date of birth)
     */
    public void printInfo() {
        System.out.printf("""
                        ID: %d
                        Name: %s
                        Surname: %s
                        Date of birth: %s
                        """, id, name, surname, birthDate
        );
    }

    /**
     * Show abbreviated information about the person
     * (full name and identifier)
     */
    public void printSimpleInfo() {
        System.out.printf("%s (%d)\n", getFullName(), id);
    }
}