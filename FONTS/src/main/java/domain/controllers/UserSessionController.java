package domain.controllers;

import domain.Database;

/**
 * Manages the User log-in and sign-up operations.
 * 
 * @author Liam Garriga
 */
public class UserSessionController {
    Database database;

    public UserSessionController(Database database) {
        this.database = database;
    }
    
    /**
     * Logs-in the user with the specified name.
     * 
     * If the user does not exist, it creates a new one.
     * @return <code>true</code> if the user did not exist and has been created.
     */
    public boolean login(String username) {
        if (database.getUser(username).isEmpty()) {
            database.addUser(username);
            return true;
        }
        return false;
    }
}
