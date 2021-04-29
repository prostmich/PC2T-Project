package main.java.database.controllers.repository;

import main.java.database.models.base.University;
import main.java.database.models.repository.Database;
import main.java.database.models.window.Form;
import main.java.database.models.window.FormInput;
import main.java.database.models.window.FormResponse;
import main.java.database.models.window.IWindow;
import main.java.database.models.window.WindowResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Window for connecting to an external database
 */
public class ConnectDatabase implements IWindow {
    private final Database database;

    public ConnectDatabase(Database database) {
        this.database = database;
    }

    @Override
    public WindowResponse open(University university) {
        if (database.isLoggedIn())
            return new WindowResponse(false, "Database is already connected");
        FormResponse answerPath = new Form("""
                -- Enter the path to the text file with the name of the database file --
                (default "secret.txt")""",
                new FormInput("Path: ", "path", String.class)
        ).display();
        String path = answerPath.findValueByName("path");
        try {
            List<String> allLines = Files.readAllLines(Paths.get(path));
            boolean result = database.Login(Paths.get(allLines.get(0)));
            if (result)
                return new WindowResponse(true, "You have successfully logged into database");
        } catch (NoSuchFileException e) {
            return new WindowResponse(false, "File is not exists");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new WindowResponse(false, "Bad credentials");
    }
}
