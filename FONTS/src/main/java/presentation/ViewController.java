package presentation;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

import domain.Board;
import domain.Database;
import domain.Game;
import domain.Level;
import domain.controllers.GameController;
import domain.controllers.LevelController;
import domain.controllers.UserSessionController;
import persistence.PersistenceController;
import domain.User;

/**
 * Manages the User Interface of the program.
 * 
 * @author Liam Garriga
 */
public class ViewController {
    final Database database;
    final GameController gameController;
    final LevelController levelController;
    final UserSessionController userSessionController;
    final PersistenceController persistenceController;
    
    final Stack<JPanel> viewStack;
    final MainFrame mainFrame;

    final BoardView boardView;
    final ConsultMenu consultMenu;
    final ConsultLevelMenu consultLevelMenu;
    final EditLevelMenu editLevelMenu;
    final ImportLevelMenu importLevelMenu;
    final LevelInfoView levelInfoView;
    final Login login;
    final MainMenu mainMenu;
    final PlayLevelMenu playLevelMenu;
    final UsersView usersView;

    public ViewController(Database database, PersistenceController persistenceController) {
        this.database = database;
        this.persistenceController = persistenceController;
        this.gameController = new GameController(database);
        this.levelController = new LevelController(database);
        this.userSessionController = new UserSessionController(database);
        
        this.viewStack = new Stack<>();
        this.mainFrame = new MainFrame();

        this.boardView = new BoardView(this);
        this.consultMenu = new ConsultMenu(this);
        this.consultLevelMenu = new ConsultLevelMenu(this);
        this.editLevelMenu = new EditLevelMenu(this);
        this.importLevelMenu = new ImportLevelMenu(this);
        this.levelInfoView = new LevelInfoView(this);
        this.login = new Login(this);
        this.mainMenu = new MainMenu(this);
        this.playLevelMenu = new PlayLevelMenu(this);
        this.usersView = new UsersView(this);
    }
    
    /** Pops the current view, returning to the previous one. */
    public void popView() {
        if (!this.viewStack.isEmpty()) {
            this.viewStack.pop().setVisible(false);
            this.viewStack.peek().setVisible(true);
        }
    }
    
    /** Pushes the specified view to the stack, setting it visible. */
    private void pushView(JPanel view) {
        if (!this.viewStack.isEmpty()) {
            this.viewStack.peek().setVisible(false);
        }
        this.viewStack.push(view);
        view.setVisible(true);

        final var pane = this.mainFrame.getContentPane();
        pane.revalidate();
        pane.repaint();
        this.mainFrame.pack();
    }
    
    /** Pops the view stack until MainMenu. */
    public void returnToMainMenu(String user) {
        for (int i = 1; i < this.viewStack.size(); i++) {
            this.popView();
        }
    }
    
    /** Starts the main interface, should be called only once. */
    public void startMainFrame() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainFrame.setVisible(true);
                mainFrame.setTitle("Kenken - 41.1");
                
                final var pane = mainFrame.getContentPane();
                pane.add(boardView);
                pane.add(consultMenu);
                pane.add(consultLevelMenu);
                pane.add(editLevelMenu);
                pane.add(importLevelMenu);
                pane.add(levelInfoView);
                pane.add(login);
                pane.add(mainMenu);
                pane.add(playLevelMenu);
                pane.add(usersView);
            }
        });
    }
    
    /** Pushes the Login interface. */
    public void LoginMenu() {
        this.login.init();
        this.pushView(this.login);
        System.err.println("Started 'Login' interface");
        // this.viewStack.add(login);
    }
    
    /** Pushes the MainMenu interface. */
    public void MainMenu(String user) {
        this.mainMenu.init(user);
        this.pushView(this.mainMenu);
        System.err.println("Started 'MainMenu' interface");
    }
    
    /** Pushes the PlayLevelMenu interface. */
    public void PlayLevelMenu(String user) {
        this.pushView(this.playLevelMenu);
        this.playLevelMenu.init(user);
        System.err.println("Started 'PlayLevelMenu' interface");
    }

    /** Pushes the EditLevelMenu interface. */
    public void EditLevelMenu(String user) {
        this.editLevelMenu.init(user);
        this.pushView(this.editLevelMenu);
        System.err.println("Started 'EditLevelMenu' interface");
    }

    /** Pushes the ImportLevelMenu interface. */
    public void ImportLevelMenu(String creator) {
        this.importLevelMenu.init(creator);
        this.pushView(this.importLevelMenu);
        System.err.println("Started 'ImportLevelMenu' interface");
    }

    /** Pushes the ConsultMenu interface. */
    public void ConsultMenu() {
        this.consultMenu.init();
        this.pushView(this.consultMenu);
        System.err.println("Started 'ConsultMenu' interface");
    }

    /** Pushes the BoardView interface. */
    public void BoardView(boolean edit, String player, Game game) {
        boardView.init(edit, player, game);
        this.pushView(boardView);
        System.err.println("Started 'BoardView' interface");
    }

    /** Pushes the UsersView interface. */
    public void UsersView() {
        this.usersView.init();
        this.pushView(this.usersView);
        System.err.println("Started 'UsersView' interface");
    }

    /** Pushes the ConsultLevelMenu interface. */
    public void ConsultLevelMenu() {
        this.consultLevelMenu.init();
        this.pushView(this.consultLevelMenu);
        System.err.println("Started 'ConsultLevelMenu' interface");
    }

    /** Pushes the LevelInfoView interface. */
    public void LevelInfoView(int levelId) {
        this.levelInfoView.init(levelId);
        this.pushView(this.levelInfoView);
        System.err.println("Started 'LevelInfoView' interface");
    }
    
    /************/
    // Database //
    /************/
    
    /** @return An immutable view on the <code>Users</code> in the database. */
    public Collection<User> getUsersDatabase() {
        return this.database.getUsers();
    }
    
    /**
     * Adds a new Level with the specified creator and board.
     * 
     * If a user with name <code>creator</code> does not exist, the level will still
     * be added.
     * 
     * @param board   Board of the level.
     * @param creator Name of the creator of the level.
     */
    public Level addLevelDatabase(Board board, String creator) {
        return this.database.addLevel(board, creator);
    }

    /******************/
    // GameController //
    /******************/

    /**
     * @return <code>true</code> if the User with the specified name has a pending
     *         Game.
     */
    public boolean userHasGameSaved(String user) {
        return this.gameController.hasGameSaved(user);
    }

    /**
     * Creates a new Game with the specified username and level and returns it.
     * <p>
     * If the User already had a pending Game, it will be replaced.
     * <p>
     * 
     * @param user  Name of the User.
     * @param levelId ID of the Level the User will play.
     * @return Returns the created Game.
     * @throws Exception If a Level with the ID does not exist.
     */
    public Game startNewGame(String user, int levelId) throws Exception {
        return this.gameController.startNewGame(user, levelId);
    }

    /**
     * @param user Name of the User with a pending Game.
     * @return The User's pending Game
     * @throws Exception If the specified User does not have a pending Game.
     */
    public Game continueGame(String user) throws Exception {
        return this.gameController.continueGame(user);
    }

    /**
     * Saves the Game to be continued on the future.
     * 
     * @param game Game to be saved.
     * @param user Name of the player of the specified Game.
     */
    public void saveGame(String user, Game game) {
        this.gameController.saveGame(user, game);
    }

    /**
     * Adds the Game into the ranking of the Level.
     * 
     * @param game Completed Game.
     * @param user Name of the player of the specified Game.
     */
    public void setAsCompleted(Game game, String user) {
        this.gameController.setAsCompleted(game, user);
    }
    

    /*******************/
    // LevelController //
    /*******************/

    /**
     * Imports a Level from a file in the specified path and saves it into the Database.<p>
     * 
     * Expects the PROP format: https://www.cs.upc.edu/prop/data/uploads/formatokenken.pdf<p>
     * 
     * If the path does not exist, or the parsing fails, an exception will be thrown.
     * 
     * @param path Path where the level is.
     * @param creator Username of the level's creator.
     * 
     * @throws Exception
     */
    public Level importLevel(String path, String creator) throws Exception {
        final var level = this.levelController.importLevel(path, creator);
        this.persistDatabase();
        return level;
    }

    /**
     * @return All the Levels ordered by difficulty (size of the Board).
     */
    public List<List<Level>> getLevelsBySize() {
        return this.levelController.getLevelsBySize();
    }

    
    /*************************/
    // UserSessionController //
    /*************************/
    
    /**
     * Logs-in the user with the specified name.
     * 
     * If the user does not exist, it creates a new one.
     * @return <code>true</code> if the user did not exist and has been created.
     */
    public boolean loginUser(String username) {
        var c = this.userSessionController.login(username);
        this.persistDatabase();
        return c;
    }

    /*************************/
    // PersistenceController //
    /*************************/
    /** Persists the Database to the default path. */
    public void persistDatabase() {
        this.persistenceController.writeDatabase("database.json");
    }

}
