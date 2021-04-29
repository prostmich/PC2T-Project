package main.java.database.models.common;

/**
 * Generic object for storing values
 * Simpler than a single element HashMap
 *
 * @param <X> key's datatype
 * @param <Y> value's datatype
 */
public class Pair<X, Y> {
    private final X key;
    private final Y value;

    /**
     * Class constructor
     *
     * @param key   key
     * @param value value
     */
    public Pair(X key, Y value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key of the pair
     *
     * @return key
     */
    public X getKey() {
        return key;
    }

    /**
     * Gets the value of the pair
     *
     * @return value
     */
    public Y getValue() {
        return value;
    }

}
