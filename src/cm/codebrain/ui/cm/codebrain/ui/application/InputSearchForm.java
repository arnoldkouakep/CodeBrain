/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author KSA-INET
 */
public class InputSearchForm extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private List<String> columnValues = new ArrayList<>();
    private List columnIndex = new ArrayList();
    private Vector<String> columnNames;
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private Vector<Vector<Object>> data;
    private Vector<Object> result;
    private Object[] imputResult;
    private List modelResult;
    private List modelComplet;
    private String entity;
    private Object filter;

    /**
     * Creates new form InputSearchForm
     */
    public InputSearchForm() {
        super();
        this.columnNames = new Vector<>();
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
    }

//    public InputSearchForm(String ejql, List args, Object... imputsResult) {
//        super();
//        this.columnNames = new Vector<>();
//
//        this.imputResult = imputsResult;
//
//        initComponents();
//
//        // Close the dialog when Esc is pressed
//        String cancelName = "cancel";
//        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
//        ActionMap actionMap = getRootPane().getActionMap();
//        actionMap.put(cancelName, new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                doClose(RET_CANCEL);
//            }
//        });
//    }

    public InputSearchForm(String entityName, List modelResult, List modelComplet, Object filter, String[][] parametresGrid, Object... imputsResult) {

        super();
        this.columnNames = new Vector<>();
        this.modelResult = modelResult;
        this.modelComplet = modelComplet;
        this.imputResult = imputsResult;
        this.entity = entityName;
        this.filter = filter;
        
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

        Loading.show(okButton, new Executable() {
            @Override
            public void execute() throws Exception {
                grid.setModel(setModelDataTable(modelResult, parametresGrid));
            }

            @Override
            public void error(Exception ex) {
                JOptionPane.showMessageDialog(rootPane, Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.OK_OPTION);
            }

        });
    }

    private TableModel setModelDataTable(List<Object[]> modelREsult, String[][] parametresGrid) {

        this.data = new Vector<>();
//        Integer[] columnSizes = new Integer[parametresGrid.length];

        for (String[] param : parametresGrid) {
            columnValues.add(param[0]);
        }

        for (String[] param : parametresGrid) {
            columnNames.add(param[1]);
        }

        for (String[] param : parametresGrid) {
            columnIndex.add(Integer.getInteger(param[2]));
        }

        modelREsult.forEach((Object[] object) -> {
            //            vector = new Vector();
            Object[] ob = (Object[]) object;
            Vector<Object> vector = new Vector();
            vector.addAll(Arrays.asList(ob));
            data.add(vector);
        });
//        formDatas.keySet().stream().map((ky) -> {
//            this.key = ky;
//            return ky;
//        }).filter((ky) -> (formDatas.get(ky).getClass() == JFormattedTextField.class)).map((ky) -> (JFormattedTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
//            modelFinal.put(this.key.toString(), ((JFormattedTextField) val).getText());
//        });
//        data.stream().map((ky)->{
//        return ky;}).filter((ky) ->(ky.get(0)))

        tableModel.setDataVector(data, columnNames);
        return this.tableModel;
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

        jPanel2 = new javax.swing.JPanel();
        labelSearch = new javax.swing.JLabel();
        searchImput = new javax.swing.JTextField();
        stripedTableScrollPane = new javax.swing.JScrollPane();
        grid = new javax.swing.JTable();
        footerGrid = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setLocationByPlatform(true);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setPreferredSize(new java.awt.Dimension(443, 268));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        labelSearch.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Filtre)); // NOI18N
        jPanel2.add(labelSearch);

        searchImput.setMaximumSize(new java.awt.Dimension(200, 28));
        searchImput.setMinimumSize(new java.awt.Dimension(200, 28));
        searchImput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel2.add(searchImput);

        getContentPane().add(jPanel2);

        grid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        grid.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        grid.setIntercellSpacing(new java.awt.Dimension(4, 1));
        grid.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        grid.setShowHorizontalLines(true);
        grid.setShowVerticalLines(true);
        grid.setSurrendersFocusOnKeystroke(true);
        stripedTableScrollPane.setViewportView(grid);
        grid.putClientProperty("Quaqua.Table.style", "striped");

        getContentPane().add(stripedTableScrollPane);

        footerGrid.setFloatable(false);
        footerGrid.setRollover(true);
        footerGrid.setFocusable(false);
        getContentPane().add(footerGrid);
        footerGrid.putClientProperty("Quaqua.ToolBar.style", "bottom");

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        jPanel1.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(cancelButton);

        getContentPane().add(jPanel1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed

        Loading.show(okButton, new Executable() {
            @Override
            public void execute() throws Exception {

                ListSelectionModel selectionModel = grid.getSelectionModel();

                result = (Vector) tableModel.getDataVector().elementAt(grid.getSelectedRow());

                Object resultEntity = modelComplet.get(grid.getSelectedRow());
                
                for (int i = 0; i < imputResult.length; i++) {
                    try {
                        if(imputResult[i].getClass().equals(JTextField.class)){
                            JTextField res =  (JTextField)imputResult[i];
                            res.setText(Dictionnaire.get(result.get(i).toString()));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                
                GlobalParameters.addVar(entity, resultEntity);

                doClose(RET_OK);
            }

            @Override
            public void error(Exception ex) {
                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(okButton), Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                
            }
        });

    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
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
    private javax.swing.JToolBar footerGrid;
    private javax.swing.JTable grid;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField searchImput;
    private javax.swing.JScrollPane stripedTableScrollPane;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
