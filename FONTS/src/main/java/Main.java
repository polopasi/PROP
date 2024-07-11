
import domain.Database;
import helpers.InOut;
import persistence.PersistenceController;
import presentation.ViewController;


public class Main {
  final static InOut inout = new InOut();

  public static void main(String[] args) throws Exception {
    PersistenceController persistenceController;
    Database database;
    try {
        database = PersistenceController.readDatabase("database.json");
        persistenceController = new PersistenceController(database);
    } catch (Exception e) {
        System.err.println("Database does not exist, creating default one.");
        database = new Database();
        persistenceController = new PersistenceController(database);
        persistenceController.writeDatabase("database.json");
    }
    ViewController viewController = new ViewController(database, persistenceController);
    
    final var levels = database.getLevels();
    if (args.length == 0 && levels.isEmpty()) {
        // Import default levels
        var directory = new java.io.File("boards").listFiles();
        if (directory == null) directory = new java.io.File[0];
        for (final var path : directory) {
            if (path.isDirectory()) {
                continue;
            }
            try {
                viewController.importLevel(path.getPath(), "Default");
            } catch (Exception e) {
                inout.printErr(e);
            }
        }
    }

    viewController.startMainFrame();
    viewController.LoginMenu();
  }
}