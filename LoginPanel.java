
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JPanel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dylan
 */
public class LoginPanel extends javax.swing.JPanel {

    boolean isOwner = false;
    String user = "User";
    String owner = "Data Owner";
    /**
     * Creates new form LoginPanel
     */
    public LoginPanel() {
        initComponents();
        //userSelectBox.removeAllItems();
        //userSelectBox.addItem(user);
        //userSelectBox.addItem(owner);
        //ThreadManager tm = new ThreadManager();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        UserNameText = new javax.swing.JTextField();
        PasswordText = new javax.swing.JPasswordField();
        LogInButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        badLoginLablel = new javax.swing.JLabel();

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        UserNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserNameTextActionPerformed(evt);
            }
        });

        LogInButton.setText("Log in");
        LogInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Welcome");

        badLoginLablel.setText("Username or Password was incorrect");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(UserNameText)
                        .addComponent(PasswordText, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LogInButton)
                        .addGap(18, 18, 18)
                        .addComponent(badLoginLablel)))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PasswordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(UserNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LogInButton)
                    .addComponent(badLoginLablel))
                .addContainerGap(260, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void LogInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogInButtonActionPerformed
        
        //convert Password to string
        String passwordvalue = new String(PasswordText.getPassword());
        String password = "password:"+ passwordvalue;
        boolean a = false;
        String usernamevalue = UserNameText.getText();
        String username = "username:"+ usernamevalue;
              

        SocketClient client = new SocketClient();
        
        a = client.login(username, password);
        
        if( a == true)
        {
            Frame[] frame = FoundationFrame.getFrames();
            frame[0].remove(this);
            JPanel homePanel = new HomePanel(isOwner);
            frame[0].add(homePanel , BorderLayout.CENTER);
            frame[0].pack(); 
        }
        else
        {
            Frame[] frame = FoundationFrame.getFrames();
            frame[0].remove(this);
            JPanel loginPanel = new LoginPanel();
            frame[0].add(loginPanel , BorderLayout.CENTER);
            frame[0].pack();
        }
        
        
    }//GEN-LAST:event_LogInButtonActionPerformed

    private void UserNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserNameTextActionPerformed
        
        
    }//GEN-LAST:event_UserNameTextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogInButton;
    private javax.swing.JPasswordField PasswordText;
    private javax.swing.JTextField UserNameText;
    private javax.swing.JLabel badLoginLablel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
