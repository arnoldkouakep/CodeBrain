/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.security;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.Task;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.implement.Executable;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author KSA-INET
 */
public class Loading extends javax.swing.JDialog implements
        PropertyChangeListener {

    private Task task;
    private Executable executable;
    private Exception exception;

    /**
     * Creates new form Loading
     * @param parent
     * @param modal
     */
    public Loading(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));
        setVisible(true);
    }
    
    public Loading(java.awt.Frame parent, Executable exe) {
        super(parent);
        initComponents();
        this.executable = exe;

        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));
        setVisible(true);
    }
    
    public Loading(Component parent, String message, Executable exe) {
        super(SwingUtilities.windowForComponent(parent));
        initComponents();
        this.executable = exe;

        progressBar.setString(message);
        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));
        setVisible(true);
    }
    
    public Loading(String message, Executable exe) {
        super();
        initComponents();
        this.executable = exe;

        progressBar.setString(message);
        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));
        setVisible(true);
    }

    /**
     * Creates new form Loading
     * @param parent
     * @param exe
     */
    private Loading(Component parent, Executable exe) {
        super(SwingUtilities.windowForComponent(parent));
        this.executable = exe;

        initComponents();
        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));
        setVisible(true);
    }
    
    /**
     * Creates new form Loading
     * @param parent
     * @param exe
     */
    private Loading(Executable exe) {
        super();
        this.executable = exe;

        initComponents();
        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/logo.png")).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));
        setVisible(true);
    }
    
    public static void show(Component parent, Executable executable){
        if(parent == null) new Loading(executable);
        else new Loading(parent, executable);
    }
    
    public static void show(Component parent, String message, Executable executable){
        if(parent == null) new Loading(message, executable);
        else new Loading(parent, message, executable);
    }

    /**
     * Invoked when task's progress property changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
                
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            setVisible(false);
            dispose();
            
            if(task.getException() == null){
                this.executable.success();
            }else{
                Toolkit.getDefaultToolkit().beep();
                this.executable.error(task.getException());
            }
        }
    }
    
    public Exception getExption(){
        return this.exception;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressBar = new javax.swing.JProgressBar();
        separateur = new javax.swing.JSeparator();
        logoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setModal(true);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        progressBar.setIndeterminate(true);
        progressBar.setString(Dictionnaire.get(EnumLibelles.Business_Libelle_Loading)); // NOI18N
        progressBar.setStringPainted(true);

        logoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separateur)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(separateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        setCursor(null);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task = new Task(this, this.executable);
        task.addPropertyChangeListener(this);
        task.execute();
    }//GEN-LAST:event_formWindowOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel logoLabel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JSeparator separateur;
    // End of variables declaration//GEN-END:variables

}
