/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentation;

import domain.*;
import domain.operations.Operations;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


/**
 * Play level menu view of the application. The user selects an existing level to play it or 
 * generates a new one.
 * @author Pol Garcia Vernet
 */
public class PlayLevelMenu extends javax.swing.JPanel {

    
    ViewController viewController;
    String player;
    
    /**
     * Creates new form PlayLevelMenu
     * @param viewController is the viewController of the application.
     */
    public PlayLevelMenu(ViewController viewController) {
        initComponents();
        this.viewController = viewController;
        this.setSize(1280, 720);
        this.setVisible(false);
    }
    
        
    /**
     * Initializes the view. Creates a list with the names of the levels and 
     * shows it. Selecting a level with the mouse opens the boardView for that level.
     * @param user is the username of the current user.
     */
    public void init(String user) {
        player = user;
        this.levelsArray = new ArrayList<Level>();

        DefaultListModel<String> model = new DefaultListModel<>();
        List<List<Level>> levels = this.viewController.getLevelsBySize();
        int numLevels = 0;
        for (int l = 0; l < levels.size(); l++) {
            final var levelGroup = levels.get(l);
            String levelSize = "[Size " + levelGroup.get(0).getSize() + "]";
            for (int i = 0; i < levelGroup.size(); i++) {
                final Level level = levelGroup.get(i);
                numLevels += 1;
                String levelName = levelSize.concat(" - " + numLevels + " (" + level.getCreator() + ")");
                model.addElement(levelName);
                levelsArray.add(level);
            }
        }
        
        levelsList.setModel(model);

        //Looks if user has a game saved and asks him if he wants to continue it.
        if (viewController.userHasGameSaved(player)) {
            int userOption = JOptionPane.showConfirmDialog(this, "You have a game saved, do you want to continue it?", "Saved game", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (userOption == 0) {  //user clicked "YES"
                try {
                    Game game = viewController.continueGame(player);
                    viewController.BoardView(false, player, game);   
                } catch (Exception ex) {
                    System.err.println("User " + player + " has no saved game.");
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backButton = new javax.swing.JButton();
        editLevelTitle = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        levelsList = new javax.swing.JList<>();
        createNewLevelButton = new javax.swing.JButton();

        backButton.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        editLevelTitle.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        editLevelTitle.setText("PICK LEVEL");

        levelsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        levelsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                levelsListMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(levelsList);

        createNewLevelButton.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        createNewLevelButton.setText("Random Level");
        createNewLevelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewLevelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(530, 530, 530)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(createNewLevelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editLevelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollPane)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(545, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(editLevelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createNewLevelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Returns to the previous view.
     */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        this.viewController.popView();
    }//GEN-LAST:event_backButtonActionPerformed

    private void levelsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_levelsListMouseClicked
        try {
            int gameSelected = levelsArray.get(levelsList.getSelectedIndex()).getId();
            Game game = viewController.startNewGame(player, gameSelected);                                      
            viewController.BoardView(false, player, game);
        } catch (Exception ex) {
            System.err.println("Error when playing an existing level: " + ex.getMessage());
        }
    }//GEN-LAST:event_levelsListMouseClicked

    
    /**
     * Creates a new random game and opens the board view for that level to play it.
     */
    private void createNewLevelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewLevelButtonActionPerformed
        try {
            Random random = new Random();
            int size = random.nextInt(10 - 2 + 1) + 2;
            final var cells = new ArrayList<Cell>(size*size);
            for (int i = 0; i < size*size; ++i) {
                cells.add(new Cell());
            }
            ArrayList<Operations> operations = new ArrayList<>();
            operations.add(Operations.ADDITION);
            operations.add(Operations.PRODUCT);
            operations.add(Operations.SUBTRACTION);
            operations.add(Operations.DIVISION);
            operations.add(Operations.MODULUS);
            operations.add(Operations.POWER);
            Board generatedBoard = new BoardGenerator(new Board(size, cells, new ArrayList<>()))
                                      .generateWithLimits(1, 4, 50, operations)
                                      .get();
            //Clears the cells of the generated board                                      
            for (Cell c : generatedBoard.getCells()) c.clear();
            //Adds the level to the dataBase
            Level level = viewController.addLevelDatabase(generatedBoard, "RANDOM");
            Game game = viewController.startNewGame(player, level.getId());
            viewController.BoardView(false, player, game);

        } catch (Exception ex) {
            System.err.println("Error when creating new board: "  + ex.getMessage());
        }
    }//GEN-LAST:event_createNewLevelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton createNewLevelButton;
    private javax.swing.JLabel editLevelTitle;
    private javax.swing.JList<String> levelsList;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
    private ArrayList<Level> levelsArray;
}