package main.java.database.models.window;

/**
 * Object to store entered value
 *
 * @param <T> entry data type
 */
public class InputValue<T> {
    private final String name;
    private final T value;

    /**
     * Class constructor
     *
     * @param name  input name
     * @param value entered value
     */
    public InputValue(String name, T value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Gets name of input
     *
     * @return input name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets entered value
     *
     * @return entered value
     */
    public T getValue() {
        return value;
    }
}
