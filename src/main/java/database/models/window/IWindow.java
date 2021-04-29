package main.java.database.models.window;

import main.java.database.models.base.University;

/**
 * Interface for implementing window architecture
 */
public interface IWindow {
    WindowResponse open(University university);
}
