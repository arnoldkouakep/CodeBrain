/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.implement.Action;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author KSA-INET
 */
public final class MessageForm extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    
    public static Action action;
    
    public static javax.swing.JDialog messageForm;

    /**
     * Creates new form MessageForm
     */
    private MessageForm(String message) {
        super();
        
        this.setModal(true);
        
        initComponents();

        this.textLabel.setText(message);
        
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        
        this.setVisible(true);
    }

    private MessageForm(String message, boolean visibleCancelButton) {
        super();
        
        this.setModal(true);
        
        initComponents();
        
        this.textLabel.setText(message);
        
        cancelButton.setVisible(visibleCancelButton);

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        
        this.setVisible(true);
    }

    private MessageForm(String message, Action action) {
        super();
        
        this.setModal(true);
        
        this.textLabel.setText(message);
        
        initComponents();
        
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        
        this.setVisible(true);
    }

    private MessageForm(String message, boolean visibleCancelButton, Action action) {
        super();
        
        this.action = action;
        
        this.setModal(true);
        
        initComponents();
        
        this.textLabel.setText(message);
        
        cancelButton.setVisible(visibleCancelButton);

        // Close the dialog when Esc is pressed
//        String cancelName = "cancel";
//        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
//        ActionMap actionMap = getRootPane().getActionMap();
//        actionMap.put(cancelName, new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                doClose(RET_CANCEL);
//                action.Ok();
//            }
//        });
        
//        this.setVisible(true);
    }
    public MessageForm(String message, String title, boolean visibleCancelButton, int messageType, Action action){
    
        int messageResult = JOptionPane.showConfirmDialog(null, 
                message,
                title,
                (visibleCancelButton)?JOptionPane.YES_NO_OPTION: JOptionPane.DEFAULT_OPTION, 
                messageType);
        
        if(messageResult == JOptionPane.YES_OPTION){
            if(action!=null) action.Ok();
        }else if(messageResult == JOptionPane.NO_OPTION){
            if(action!=null) action.Cancel();
        }
    }
    /**
     *
     * @param message
     * @param title
     * @param visibleCancelButton
     * @param action
     */
    public static void shows(String message, String title, boolean visibleCancelButton, Action action){
        
        MessageForm.messageForm = new MessageForm(message, title, visibleCancelButton, (visibleCancelButton)?JOptionPane.QUESTION_MESSAGE: JOptionPane.INFORMATION_MESSAGE, action);
    }
    
    public static void showsError(String message, String title, boolean visibleCancelButton, Action action){
        
        MessageForm.messageForm = new MessageForm(message, title, visibleCancelButton, JOptionPane.ERROR_MESSAGE, action);
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

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        textLabel = new javax.swing.JLabel();

        setTitle("Message");
        setLocationByPlatform(true);
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Annuler)); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        textLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        textLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textLabel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        textLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 183, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addComponent(textLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if(!this.cancelButton.isVisible())
            doClose(RET_CANCEL);
        else{
            doClose(RET_OK);
            action.Ok();
        }
        
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if(this.cancelButton.isVisible())
            action.Cancel();
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog
    
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel textLabel;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
