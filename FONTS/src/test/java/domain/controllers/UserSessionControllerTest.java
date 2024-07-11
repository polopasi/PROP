package domain.controllers;

import static domain.TestUtils.assertListEquals;
import static domain.TestUtils.assertSortedEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import domain.Database;
import domain.User;

public class UserSessionControllerTest {
    @Test
    public void login() {
        final Database database = new Database();
        final UserSessionController userSessionC = new UserSessionController(database);
        
        // sign up
        final String name0 = "Alexandra";
        final User user0 = new User(name0);
        assertTrue(userSessionC.login(name0));
        assertEquals(Optional.of(user0), database.getUser(name0));
        
        // sign up
        final String name1 = "Dolores";
        final User user1 = new User(name1);
        assertTrue(userSessionC.login(name1));
        assertEquals(Optional.of(user1), database.getUser(name1));
        assertSortedEquals(new User[] { user0, user1 }, database.getUsers());
        
        // log in (do not create user)
        assertFalse(userSessionC.login(name0));
        assertSortedEquals(new User[] { user0, user1 }, database.getUsers());
    }
}
