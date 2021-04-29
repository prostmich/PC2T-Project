package main.java.database.models.window;


import java.util.HashMap;

/**
 * Object for creating and displaying forms for further user input
 */
public class Form {
    private final String question;
    private final FormInput[] formInputs;

    /**
     * Class constructor
     *
     * @param question  title question
     * @param formInput FormInput instance for further user input
     */
    public Form(String question, FormInput formInput) {
        this.question = question;
        this.formInputs = new FormInput[]{formInput};
    }

    /**
     * Class constructor
     *
     * @param question   title question
     * @param formInputs array of FormInput instances for further user input
     */
    public Form(String question, FormInput[] formInputs) {
        this.question = question;
        this.formInputs = formInputs;
    }

    /**
     * Display form to user
     *
     * @return FormResponse instance with response data
     */
    public FormResponse display() {
        System.out.println("\n" + question + "\n");
        HashMap<String, InputValue<?>> values = new HashMap<>();
        for (FormInput formInput : formInputs) {
            values.put(formInput.getName(), formInput.askValue());
        }
        return new FormResponse(true, "Display", values);
    }

}
