
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
public class HomePanel extends javax.swing.JPanel {

    ThreadManager tm;
    SocketClient client;
    /**
     * Creates new form HomePanel
     * 
     */
    public HomePanel(ThreadManager tm, SocketClient client) {
        this.tm = tm;
        initComponents();
        if(!tm.getIsOwner()){
            registerButton.setEnabled(false);
            uploadFileButton.setEnabled(false);
            changePermissionsButton.setEnabled(false);
            welcomeLabel.setText("Welcome, User");
        } else {
            welcomeLabel.setText("Welcome, Data Owner");
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

        downloadFileButton = new javax.swing.JButton();
        uploadFileButton = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();
        changePermissionsButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();

        downloadFileButton.setText("Download File");
        downloadFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadFileButtonActionPerformed(evt);
            }
        });

        uploadFileButton.setText("Upload File");
        uploadFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFileButtonActionPerformed(evt);
            }
        });

        welcomeLabel.setText("Welcome, [USER OR OWNER]");

        logOutButton.setText("Log Out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        changePermissionsButton.setText("Change Permissions");
        changePermissionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePermissionsButtonActionPerformed(evt);
            }
        });

        registerButton.setText("Register User");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logOutButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(welcomeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(changePermissionsButton)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(uploadFileButton)
                                .addGap(18, 18, 18)
                                .addComponent(downloadFileButton))
                            .addComponent(registerButton))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(downloadFileButton)
                    .addComponent(uploadFileButton)
                    .addComponent(welcomeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logOutButton)
                .addGap(18, 18, 18)
                .addComponent(changePermissionsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerButton)
                .addContainerGap(311, Short.MAX_VALUE))
        );

        registerButton.getAccessibleContext().setAccessibleName("registerButton");
    }// </editor-fold>//GEN-END:initComponents

    private void uploadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadFileButtonActionPerformed
        Frame[] frame = FoundationFrame.getFrames();
        frame[0].remove(this);
        JPanel uploadPanel = new FileUploadPanel(tm, client, null);
        frame[0].add(uploadPanel , BorderLayout.CENTER);
        frame[0].pack();
    }//GEN-LAST:event_uploadFileButtonActionPerformed

    private void downloadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadFileButtonActionPerformed
        Frame[] frame = FoundationFrame.getFrames();
        frame[0].remove(this);
        JPanel downloadPanel = new FileDownloadPanel(tm, client);
        frame[0].add(downloadPanel , BorderLayout.CENTER);
        frame[0].pack();
    }//GEN-LAST:event_downloadFileButtonActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        Frame[] frame = FoundationFrame.getFrames();
        frame[0].remove(this);
        JPanel loginPanel = new LoginPanel(client);
        frame[0].add(loginPanel , BorderLayout.CENTER);
        frame[0].pack();
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void changePermissionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePermissionsButtonActionPerformed
        Frame[] frame = FoundationFrame.getFrames();
        frame[0].remove(this);
        JPanel loginPanel = new PermissionsPanel(tm, client);
        frame[0].add(loginPanel , BorderLayout.CENTER);
        frame[0].pack();
    }//GEN-LAST:event_changePermissionsButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
         Frame[] frame = FoundationFrame.getFrames();
        frame[0].remove(this);
        JPanel signupPanel = new SignupPanel(tm, client);
        frame[0].add( signupPanel, BorderLayout.CENTER);
        frame[0].pack();    }//GEN-LAST:event_registerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changePermissionsButton;
    private javax.swing.JButton downloadFileButton;
    private javax.swing.JButton logOutButton;
    private javax.swing.JButton registerButton;
    private javax.swing.JButton uploadFileButton;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
