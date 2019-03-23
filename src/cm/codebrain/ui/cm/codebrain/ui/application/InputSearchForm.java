/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.main.business.controller.CodeBrainExceptions;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
    private static final long serialVersionUID = 1L;
    private List<String> columnValues = new ArrayList<>();
    private List columnIndex = new ArrayList();
    private List<String> columnNames;
    private DefaultTableModel tableModel;
//    private Vector<Vector<Object>> data;
//    private Vector<Object> result;
    private Object[] imputsResultFields;
    private List<HashMap> modelFinal;
    private HashMap modelResult;
    private String entity;
    private String keyParam;
    private Object filter;
    private TableRowSorter<TableModel> sorter;

    /**
     * Creates new form InputSearchForm
     */
    public InputSearchForm() {
        super();
//        this.columnNames = new Vector<>();
        initComponents();
        columnNames = new ArrayList<>();
        tableModel = new DefaultTableModel();

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

    public InputSearchForm(String entityName, String keyParam, List<HashMap> modelFinal, String[][] parametresGrid, Object... imputsResultFields) {

        super();
        
        this.modelFinal = modelFinal;
        this.imputsResultFields = imputsResultFields;
        this.entity = entityName;
        
        this.keyParam = keyParam;

        initComponents();

        columnNames = new ArrayList<>();
        tableModel = new DefaultTableModel();

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

        Loading.show(okButton, new Executable<List<HashMap>>() {
            @Override
            public List<HashMap> execute() throws Exception {
                grid.setModel(setModelDataTable(modelFinal, parametresGrid));
                grid.setRowSorter(sorter);
                
                return modelFinal;
            }

            @Override
            public void error(CodeBrainExceptions ex) {
                JOptionPane.showMessageDialog(rootPane, Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.OK_OPTION);
            }

        });
    }

    private void newFilter() {
        RowFilter<TableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(searchImput.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    private TableModel setModelDataTable(List<HashMap> modelFinal, String[][] parametresGrid) {

        for (String[] param : parametresGrid) {
            columnValues.add(param[0]);
            columnNames.add(param[1]);
        }

        tableModel = new DefaultTableModel() {
            @Override
            public int getRowCount() {
                return modelFinal.size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.size();
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnNames.get(columnIndex);
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return modelFinal.get(0).get(columnValues.get(columnIndex)).getClass();
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return modelFinal.get(row).get(columnValues.get(column));
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                super.setValueAt(modelFinal.get(rowIndex).get(columnValues.get(columnIndex)), rowIndex, columnIndex);
            }
        };

        sorter = new TableRowSorter<>(tableModel);

        return tableModel;
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

        javax.swing.JPanel panelSearch = new javax.swing.JPanel();
        labelSearch = new javax.swing.JLabel();
        searchImput = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        stripedTableScrollPane = new javax.swing.JScrollPane();
        grid = new javax.swing.JTable();
        footerGrid = new javax.swing.JToolBar();
        javax.swing.JPanel panelButtons = new javax.swing.JPanel();
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
        panelSearch.add(labelSearch);

        searchImput.setMaximumSize(new java.awt.Dimension(200, 28));
        searchImput.setMinimumSize(new java.awt.Dimension(200, 28));
        searchImput.setPreferredSize(new java.awt.Dimension(200, 28));
        panelSearch.add(searchImput);

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        panelSearch.add(btnSearch);

        getContentPane().add(panelSearch);

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
        grid.setUpdateSelectionOnSort(false);
        stripedTableScrollPane.setViewportView(grid);
        grid.putClientProperty("Quaqua.Table.style", "striped");

        getContentPane().add(stripedTableScrollPane);

        footerGrid.setFloatable(false);
        footerGrid.setRollover(true);
        footerGrid.setFocusable(false);
        getContentPane().add(footerGrid);
        footerGrid.putClientProperty("Quaqua.ToolBar.style", "bottom");

        panelButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        panelButtons.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        panelButtons.add(cancelButton);

        getContentPane().add(panelButtons);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed

        Loading.show(okButton, new Executable<HashMap>() {
            @Override
            public HashMap execute() throws Exception {

                ListSelectionModel selectionModel = grid.getSelectionModel();

                TableModel model = grid.getModel();
                
                
                int rowIndex = grid.convertRowIndexToModel(grid.getSelectedRow());
                int columnIndex = grid.getSelectedColumn();
//                int x = grid.convertRowIndexToModel(grid.getSelectedRow());
//                int y = grid.convertColumnIndexToModel(columnIndex);
                
//                grid.getModel().
                
//                int columnIndex = grid.getSelectedRow();
//                Object obj = model.getValueAt(x, columnIndex);
//                System.out.println(String.valueOf(obj));
                HashMap result = modelFinal.get(rowIndex);// tableModel.getDataVector().elementAt(grid.getSelectedRow());

//                Object resultEntity = modelComplet.get(grid.getSelectedRow());
                for (Object field : imputsResultFields) {
                    try {
                        if (field.getClass().equals(JTextField.class)) {
                            JTextField res = (JTextField) field;
                            res.setText(Dictionnaire.get(result.get(res.getName()).toString()));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                
                setResult(result);

                if(keyParam == null)
                    GlobalParameters.addVar(entity, result);
                else
                    GlobalParameters.addVar(keyParam, result);
                
                doClose(RET_OK);
                return result;
            }

            @Override
            public void error(CodeBrainExceptions ex) {
                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(okButton), Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.ERROR_MESSAGE);

            }

        });

    }//GEN-LAST:event_okButtonActionPerformed

    private void setResult(HashMap result) {
        this.modelResult = result;
    }
    
    public HashMap getResult(){
        return this.modelResult;
    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        System.out.println("2: " + searchImput.getText());
        RowFilter<TableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(searchImput.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton cancelButton;
    private javax.swing.JToolBar footerGrid;
    private javax.swing.JTable grid;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField searchImput;
    private javax.swing.JScrollPane stripedTableScrollPane;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
