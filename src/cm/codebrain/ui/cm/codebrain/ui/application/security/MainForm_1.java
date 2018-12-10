/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.security;

import ch.randelshofer.quaqua.QuaquaManager;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author KSA-INET
 */
public class MainForm_1 extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainForm_1() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        centerPanel = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        logoPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        statusBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        statusBar = new javax.swing.JToolBar();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CodeBrain For School");
        setExtendedState(6);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName("mainFrame"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(51, 255, 153));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout(50, 10));

        centerPanel.setLayout(new javax.swing.OverlayLayout(centerPanel));

        mainPanel.setBackground(new java.awt.Color(204, 255, 102));
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 50, 25));
        centerPanel.add(mainPanel);

        logoPanel.setLayout(new java.awt.CardLayout());

        logoLabel.setFont(new java.awt.Font("sansserif", 0, 48)); // NOI18N
        logoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoLabel.setText("LOGO");
        logoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logoLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        logoPanel.add(logoLabel, "card3");

        centerPanel.add(logoPanel);

        jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);

        statusBar1.setFloatable(false);
        statusBar1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                statusBar1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                statusBar1AncestorRemoved(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        statusBar1.add(jButton1);

        jPanel1.add(statusBar1, java.awt.BorderLayout.NORTH);

        statusBar.setFloatable(false);
        statusBar.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                statusBarAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                statusBarAncestorRemoved(evt);
            }
        });

        statusLabel.setText("A status bar");
        statusBar.add(statusLabel);

        jPanel1.add(statusBar, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void statusBarAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_statusBarAncestorAdded
//        startTimer();
    }//GEN-LAST:event_statusBarAncestorAdded

    private void statusBarAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_statusBarAncestorRemoved
//        stopTimer();
    }//GEN-LAST:event_statusBarAncestorRemoved

    private void statusBar1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_statusBar1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_statusBar1AncestorAdded

    private void statusBar1AncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_statusBar1AncestorRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_statusBar1AncestorRemoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        final java.util.List argList = Arrays.asList(args);
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }

        // Add Quaqua to the lafs
        ArrayList<UIManager.LookAndFeelInfo> infos = new ArrayList<UIManager.LookAndFeelInfo>(Arrays.asList(UIManager.getInstalledLookAndFeels()));
        infos.add(new UIManager.LookAndFeelInfo("Quaqua", QuaquaManager.getLookAndFeelClassName()));
        UIManager.setInstalledLookAndFeels(infos.toArray(new UIManager.LookAndFeelInfo[infos.size()]));

        // Turn on look and feel decoration when not running on Mac OS X or Darwin.
        // This will still not look pretty, because we haven't got cast shadows
        // for the frame on other operating systems.
        boolean useDefaultLookAndFeelDecoration =
                !System.getProperty("os.name").toLowerCase().startsWith("mac")
                && !System.getProperty("os.name").toLowerCase().startsWith("darwin");
        int index = argList.indexOf("-decoration");
        if (index != -1 && index < argList.size() - 1) {
            useDefaultLookAndFeelDecoration = argList.get(index + 1).equals("true");
        }

        if (useDefaultLookAndFeelDecoration) {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        }

//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm_1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centerPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JToolBar statusBar;
    private javax.swing.JToolBar statusBar1;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
