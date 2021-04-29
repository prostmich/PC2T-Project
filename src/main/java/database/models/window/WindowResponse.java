package main.java.database.models.window;

/**
 * Object to store the response after window activity ends
 */
public class WindowResponse {
    private final boolean status;
    private final String message;
    private final boolean displayed;

    /**
     * Class constructor
     *
     * @param status  activity process status
     * @param message response message
     */
    public WindowResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
        this.displayed = true;
    }

    /**
     * Class constructor
     *
     * @param status    activity process status
     * @param message   response message
     * @param displayed value to hide debug responses
     */
    public WindowResponse(boolean status, String message, boolean displayed) {
        this.status = status;
        this.message = message;
        this.displayed = displayed;
    }

    /**
     * Gets activity process status
     *
     * @return activity process status
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
     * Tests if a response should be shown
     *
     * @return true if a response should be shown, false otherwise
     */
    public boolean isDisplayed() {
        return displayed;
    }
}
