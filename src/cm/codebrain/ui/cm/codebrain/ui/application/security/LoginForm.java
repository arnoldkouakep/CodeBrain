/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.security;

import cm.codebrain.main.business.controller.CodeBrainManager;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.Locale;
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.implement.Executable;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author KSA-INET
 */
public class LoginForm extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private final CodeBrainManager codeBrainManager;

    /**
     * Creates new form LoginForm
     * @param codeBrainManager
     * @param parent
     * @param modal
     */
    public LoginForm(CodeBrainManager codeBrainManager, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.codeBrainManager = codeBrainManager;

        Locale.initBundle();

        initComponents();

        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));

        if (Locale.LANGUAGE == 0) {
            btnLocal.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/en_en.png")).getImage().getScaledInstance(btnLocal.getWidth(), btnLocal.getHeight(), 0)));
        } else if (Locale.LANGUAGE == 1) {
            btnLocal.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/fr_fr.png")).getImage().getScaledInstance(btnLocal.getWidth(), btnLocal.getHeight(), 0)));
        }

    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnOk = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelUsername = new javax.swing.JLabel();
        labelPassword = new javax.swing.JLabel();
        passwordInput = new javax.swing.JPasswordField();
        usernameInput = new javax.swing.JTextField();
        logoLabel = new javax.swing.JLabel();
        btnLocal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(Dictionnaire.get(EnumLibelles.Business_Libelle_Login)); // NOI18N
        setModal(true);
        setResizable(false);

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        cancelButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Annuler)); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        labelUsername.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Username)); // NOI18N
        labelUsername.setName("usernameLabel"); // NOI18N

        labelPassword.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Password)); // NOI18N
        labelPassword.setName("labelPassword"); // NOI18N

        passwordInput.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        passwordInput.setToolTipText(Dictionnaire.get(EnumLibelles.Business_Libelle_Enter_Password)); // NOI18N

        usernameInput.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        usernameInput.setToolTipText(Dictionnaire.get(EnumLibelles.Business_Libelle_Enter_Username)); // NOI18N
        usernameInput.setName("usernameInput"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordInput, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(usernameInput))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordInput)
                    .addComponent(labelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnLocal.setToolTipText(Dictionnaire.get(EnumLibelles.Business_Libelle_Change_Language)); // NOI18N
        btnLocal.setName("btnLocal"); // NOI18N
        btnLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnOk, cancelButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(btnOk)
                    .addComponent(btnLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(btnOk);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
//        loading.show();
//        try {
        Loading.show(btnOk, new Executable() {

            @Override
            public void execute() {

                String login = usernameInput.getText();
                String password = String.valueOf(passwordInput.getPassword());

                try {
                    //            codeBrainManager.authenticate(login, password);
                    String res = codeBrainManager.authenticate(login, password).getLogin();

                    /**
                     *
                     * User connected
                     */
//                    JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnOk), "User : " + res + " Connecté.", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("User : " + res + " Connecté.");
                    codeBrainManager.load();
                    doClose(RET_OK);
                } catch (SQLException ex) {
//                    JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnOk), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(btnOk), Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void error(Exception ex) {
                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(btnOk), Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.ERROR_MESSAGE);
            }

        });
    }//GEN-LAST:event_btnOkActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
        dispose();
        System.exit(0);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void btnLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalActionPerformed
        if (Locale.LANGUAGE == 0) {
            Locale.LANGUAGE = 1;
            btnLocal.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/en_en.png")).getImage().getScaledInstance(btnLocal.getWidth(), btnLocal.getHeight(), 0)));
        } else if (Locale.LANGUAGE == 1) {
            Locale.LANGUAGE = 0;
            btnLocal.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/fr_fr.png")).getImage().getScaledInstance(btnLocal.getWidth(), btnLocal.getHeight(), 0)));
        }

        this.codeBrainManager.restart();
    }//GEN-LAST:event_btnLocalActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLocal;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JTextField usernameInput;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;

}
