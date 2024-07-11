package persistence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import domain.Database;
import helpers.InOut;

public class PersistenceController {
    Database database;
    private InOut inout;
    
    public PersistenceController(Database database) {
        this.inout = new InOut();
        this.database = database;
    }
    
    /** Reads the Database from the default path. */
    public static Database readDatabase() throws Exception {
        return readDatabase("database.json");
    }
    
    /** Reads the Database from the specified path. */
    public static Database readDatabase(String path) throws Exception {
        String read = InOut.readToString(path);
        return Database.deserialize(read).get();
    }
    
    /** Writes the Database of this instance to the default path. */
    public void writeDatabase() {
        this.writeDatabase("database.json");
    }
    
    /** Writes the Database of this instance to the specified path. */
    public boolean writeDatabase(String path) {
        try {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
                out.write(database.serialize());
                out.flush();
                return true;
            }
        } catch (Exception e) {
            inout.printErr(e);
            e.printStackTrace();
            return false;
        }
    }
}
