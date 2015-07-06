
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dylan
 */
public class FileUploadPanel extends javax.swing.JPanel {

    boolean isOwner;
    File selectedFile;
    /**
     * Creates new form FileUploadPanel
     */
    public FileUploadPanel(boolean isOwner, File file) {
        this.isOwner = isOwner;
        initComponents();
        this.selectedFile = file;
        if(selectedFile != null){
            FileText.setText(selectedFile.getAbsolutePath());
            noFileSelectedLabel.setVisible(false);
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

        block64 = new javax.swing.JRadioButton();
        noFileSelectedLabel = new javax.swing.JLabel();
        block128 = new javax.swing.JRadioButton();
        block256 = new javax.swing.JRadioButton();
        block512 = new javax.swing.JRadioButton();
        block1024 = new javax.swing.JRadioButton();
        block2048 = new javax.swing.JRadioButton();
        size128 = new javax.swing.JRadioButton();
        size256 = new javax.swing.JRadioButton();
        size512 = new javax.swing.JRadioButton();
        size1024 = new javax.swing.JRadioButton();
        size2048 = new javax.swing.JRadioButton();
        size4096 = new javax.swing.JRadioButton();
        BLSButton = new javax.swing.JRadioButton();
        RSAButton = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        SettingsButton = new javax.swing.JButton();
        ChooseFileButton = new javax.swing.JButton();
        FileText = new javax.swing.JTextField();
        UploadButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(600, 450));
        setVerifyInputWhenFocusTarget(false);

        block64.setText("64 byte");

        noFileSelectedLabel.setText("No file selected, please select a file");

        block128.setText("128 byte");

        block256.setSelected(true);
        block256.setText("256 byte");

        block512.setText("512 byte");

        block1024.setText("1024 byte");

        block2048.setText("2048 byte");

        size128.setText("128 bit");

        size256.setText("256 bit");

        size512.setSelected(true);
        size512.setText("512 bit");

        size1024.setText("1024 bit");

        size2048.setText("2048 bit");

        size4096.setText("4096 bit");

        BLSButton.setSelected(true);
        BLSButton.setText("BLS");

        RSAButton.setText("RSA");

        SettingsButton.setText("User Access Settings");
        SettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsButtonActionPerformed(evt);
            }
        });

        ChooseFileButton.setText("Choose File");
        ChooseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseFileButtonActionPerformed(evt);
            }
        });

        FileText.setEditable(false);
        FileText.setText("Chosen File");

        UploadButton.setText("Upload File");
        UploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadButtonActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setText("Block Size:");

        jLabel2.setText("Encryption Key Size:");

        jLabel3.setText("Signature Type:");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(block64)
                                    .addComponent(jLabel1)
                                    .addComponent(block128)
                                    .addComponent(block256)
                                    .addComponent(block512)
                                    .addComponent(block1024)
                                    .addComponent(block2048))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(size128)
                                            .addComponent(size512)
                                            .addComponent(size256)
                                            .addComponent(size1024)
                                            .addComponent(size2048)
                                            .addComponent(size4096)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel2)))
                                .addGap(40, 40, 40)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RSAButton)
                                    .addComponent(BLSButton)
                                    .addComponent(jLabel3)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ChooseFileButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FileText, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(SettingsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(noFileSelectedLabel)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelButton)
                            .addComponent(UploadButton))
                        .addGap(56, 56, 56)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChooseFileButton)
                    .addComponent(FileText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(block64)
                            .addComponent(size128)
                            .addComponent(BLSButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(block128)
                            .addComponent(size256)
                            .addComponent(RSAButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(block256)
                            .addComponent(size512))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(block512)
                            .addComponent(size1024))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(block1024)
                            .addComponent(size2048))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(block2048)
                            .addComponent(size4096)))
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator4))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SettingsButton)
                    .addComponent(UploadButton)
                    .addComponent(noFileSelectedLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void SettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsButtonActionPerformed
        Frame[] frame = FoundationFrame.getFrames();
        frame[0].remove(this);
        JPanel panel = new ManagePermissionsPanel(isOwner, selectedFile);
        frame[0].add(panel , BorderLayout.CENTER);
        frame[0].pack();
    }//GEN-LAST:event_SettingsButtonActionPerformed

    private void ChooseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseFileButtonActionPerformed
        JFileChooser jc = new JFileChooser();
        FileNameExtensionFilter ff = new FileNameExtensionFilter(null, "txt");
        jc.setFileFilter(ff);
        int returnVal = jc.showOpenDialog(ChooseFileButton);
        if(jc.getSelectedFile() != null){
            selectedFile = jc.getSelectedFile();
            FileText.setText(selectedFile.getAbsolutePath());
            noFileSelectedLabel.setVisible(false);
        }
    }//GEN-LAST:event_ChooseFileButtonActionPerformed

    private void UploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadButtonActionPerformed
        if(selectedFile == null){
            noFileSelectedLabel.setVisible(true);
        } else {
            int blockSize = findBlockSize();
            int keySize = findKeySize();
            String signatureType = "BLS";
            if(RSAButton.isSelected()){signatureType = "RSA";}
            
            ThreadManager up = new ThreadManager();
            
            try {               
                up.preprocess(selectedFile, blockSize);
            } catch (IOException ex) {
               JOptionPane.showMessageDialog(this, "File not found");
            }
            String fileName = selectedFile.getName();
            int pos = fileName.lastIndexOf(".");
            String baseName = "";
            if (pos > 0) {
            baseName = fileName.substring(0, pos);
            }
            String fileExtension;
            int pos2 = fileName.length();
            fileExtension = fileName.substring(pos, pos2);
            String filePath = selectedFile.getPath();
            int pathLength = filePath.length();
            filePath = filePath.substring(0, pathLength - pos2);
            up.Encrypt(filePath, baseName, fileExtension);
        }
    }//GEN-LAST:event_UploadButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        Frame[] frame = FoundationFrame.getFrames();
        frame[0].remove(this);
        JPanel homePanel = new HomePanel(isOwner);
        frame[0].add(homePanel , BorderLayout.CENTER);
        frame[0].pack();
    }//GEN-LAST:event_cancelButtonActionPerformed

    public int findBlockSize(){
       int blockSize = 256;
       if(block64.isSelected()){ blockSize = 64;}
       if(block128.isSelected()){ blockSize = 128;}
       if(block256.isSelected()){ blockSize = 256;}
       if(block512.isSelected()){ blockSize = 512;}
       if(block1024.isSelected()){ blockSize = 1024;}
       if(block2048.isSelected()){ blockSize = 2048;}
       return blockSize;
    }

    public int findKeySize(){
        int keySize = 512;
        if(size128.isSelected()){keySize = 128;}
        if(size256.isSelected()){keySize = 256;}
        if(size512.isSelected()){keySize = 512;}
        if(size1024.isSelected()){keySize = 1024;}
        if(size2048.isSelected()){keySize = 2048;}
        if(size4096.isSelected()){keySize = 4096;}
        return keySize;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton BLSButton;
    private javax.swing.JButton ChooseFileButton;
    private javax.swing.JTextField FileText;
    private javax.swing.JRadioButton RSAButton;
    private javax.swing.JButton SettingsButton;
    private javax.swing.JButton UploadButton;
    private javax.swing.JRadioButton block1024;
    private javax.swing.JRadioButton block128;
    private javax.swing.JRadioButton block2048;
    private javax.swing.JRadioButton block256;
    private javax.swing.JRadioButton block512;
    private javax.swing.JRadioButton block64;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel noFileSelectedLabel;
    private javax.swing.JRadioButton size1024;
    private javax.swing.JRadioButton size128;
    private javax.swing.JRadioButton size2048;
    private javax.swing.JRadioButton size256;
    private javax.swing.JRadioButton size4096;
    private javax.swing.JRadioButton size512;
    // End of variables declaration//GEN-END:variables
}
