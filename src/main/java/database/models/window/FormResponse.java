package main.java.database.models.window;

import java.util.HashMap;

/**
 * Object to store the form response after data entry
 */
public class FormResponse {
    private final boolean status;
    private final String message;
    private final HashMap<String, InputValue<?>> values;

    /**
     * Class constructor
     *
     * @param status  input process status
     * @param message response message
     * @param values  entered values
     */
    public FormResponse(boolean status, String message, HashMap<String, InputValue<?>> values) {
        this.status = status;
        this.message = message;
        this.values = values;
    }

    /**
     * Gets input process status
     *
     * @return input process status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Gets response message
     *
     * @return response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets retrieved values after data entry
     * @return map name:InputValue with retrieved values
     */
    public HashMap<String, InputValue<?>> getValues() {
        return values;
    }

    /**
     * Gets a value from retrieved data by input name
     * @param name input name
     * @param <T> expected data type
     * @return entered value
     */
    @SuppressWarnings("unchecked")
    public <T> T findValueByName(String name) {
        return (T) values.get(name).getValue();
    }

}
