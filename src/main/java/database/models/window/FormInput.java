package main.java.database.models.window;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Object for creating form inputs for data entry
 */
public class FormInput {
    private final String label;
    private final String name;
    private final Class<?> type;
    private Number min = null, max = null;
    private String pattern = null, example = null;

    /**
     * Class constructor
     *
     * @param label input label
     * @param name  input name
     * @param type  expected data type
     */
    public FormInput(String label, String name, Class<?> type) {
        this.label = label;
        this.name = name;
        this.type = type;
    }

    /**
     * Class constructor
     *
     * @param label input label
     * @param name  input name
     * @param type  expected numeric data type
     * @param min   minimal numeric value
     * @param max   maximal numeric value
     */
    public FormInput(String label, String name, Class<? extends Number> type, Number min, Number max) {
        this.label = label;
        this.name = name;
        this.type = type;
        this.max = max;
        this.min = min;
    }

    /**
     * Class constructor
     *
     * @param label   input label
     * @param name    input name
     * @param type    expected string data type
     * @param pattern pattern for matching the entered data
     * @param example example of expected data
     */
    public FormInput(String label, String name, Class<String> type, String pattern, String example) {
        this.label = label;
        this.name = name;
        this.type = type;
        this.pattern = pattern;
        this.example = example;
    }

    /**
     * Gets label of input
     *
     * @return input label
     */
    public String getLabel() {
        return label;
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
     * Displays the input label to the user and waiting for the correct answer
     *
     * @return InputValue instance with entered data
     */
    public InputValue<?> askValue() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(label);
            switch (type.getSimpleName()) {
                case "Integer":
                    try {
                        int result = Integer.parseInt(sc.nextLine());
                        if (isValidInteger(result))
                            return new InputValue<>(name, result);
                        else
                            System.out.printf("Please try again! The expected value is in the range %d to %d\n", min, max);
                    } catch (NumberFormatException e) {
                        System.out.println("Please try again! The expected value is of type Integer");
                    }
                    break;
                case "Double":
                    try {
                        double result = Double.parseDouble(sc.nextLine());
                        if (isValidDouble(result))
                            return new InputValue<>(name, result);
                        else
                            System.out.printf("Please try again! The expected value is in the range %.1f to %.1f\n", min, max);
                    } catch (NumberFormatException e) {
                        System.out.println("Please try again! The expected value is of type Double");
                    }
                    break;
                default:
                    String result = sc.nextLine();
                    if (isValidString(result))
                        return new InputValue<>(name, result);
                    else
                        System.out.printf("Please try again! The expected value should match the pattern %s\n", example);
                    break;
            }
        }
    }

    private boolean isValidInteger(int value) {
        return min == null || max == null || (int) min <= value && value <= (int) max;
    }

    private boolean isValidDouble(double value) {
        return min == null || max == null || (double) min <= value && value <= (double) max;
    }

    private boolean isValidString(String value) {
        return pattern == null || Pattern.matches(pattern, value);
    }
}
