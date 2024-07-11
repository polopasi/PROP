/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentation;

/**
 * Login view of the application. The user can write its username and login or create
 * the user to play.
 * @author Pol Garcia Vernet
 */
public class Login extends javax.swing.JPanel {
    
    ViewController viewController;
    
    /**
     * Creates new form Login
     * @param viewController is the viewController of the application.
     */
    public Login(ViewController viewController) {
        this.viewController = viewController;
        initComponents();
        this.emptyUsernameError.setVisible(false);
        this.setSize(1280, 720);
        this.setVisible(false);
    }
    
    public void init() {
        textField.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        loginText = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        textField = new javax.swing.JTextField();
        emptyUsernameError = new javax.swing.JLabel();

        loginText.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        loginText.setText("Login");

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldActionPerformed(evt);
            }
        });

        emptyUsernameError.setForeground(new java.awt.Color(255, 0, 0));
        emptyUsernameError.setText("The username can't be empty.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(500, 500, 500)
                .addComponent(loginText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emptyUsernameError)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(54, 54, 54))))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginText)
                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emptyUsernameError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addContainerGap(110, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    /**
     * Starts the game and logins or creates the user in the textField.
     */
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        if (!textField.getText().isEmpty()) {
            this.emptyUsernameError.setVisible(false);
            final String user = textField.getText();
            textField.setText("");
            viewController.loginUser(user);
            viewController.MainMenu(user);
        }
        else {
            this.emptyUsernameError.setVisible(true);
        }
    }                                           

    /**
     * Unused method of textField.
     */
    private void textFieldActionPerformed(java.awt.event.ActionEvent evt) {                                          
    }                                         


    // Variables declaration - do not modify                     
    private javax.swing.JLabel emptyUsernameError;
    private javax.swing.JLabel loginText;
    private javax.swing.JButton startButton;
    private javax.swing.JTextField textField;
    // End of variables declaration                   
}