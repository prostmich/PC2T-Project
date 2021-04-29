package test.java.database;

import org.junit.Test;

import static main.java.database.utils.Helpers.getSQLPlaceholders;
import static org.junit.Assert.assertEquals;

public class DatabaseTests {
    @Test
    public void testGetSQLPlaceholders() {
        String expectedResult = "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)";
        String actualResult = getSQLPlaceholders(3, 4);
        assertEquals(expectedResult, actualResult);
    }
}
