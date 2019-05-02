/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.FormParameters;
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
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author KSA-INET
 */
public class SearchForm extends ModelForm {

    private String keyParam;
    private Object[] imputsResultFields;
    private List<String> columnNames;
    private List<Class> columnClasses;
    private List<Integer> columnSizes;
    private List<HashMap> listModelResult;
    private Object[][] parametresGrid;
    private TableRowSorter<DefaultTableModel> sorter;
    private List<String> columnValues;
    
    public SearchForm() {
        super(Dictionnaire.get(EnumLibelles.Business_Libelle_Search), 520, 320, true, false);
    }
    
    public SearchForm(String entityName, String keyParam, List<HashMap> listModelResult, Object[][] parametresGrid, Object... imputsResultFields) {
        super(Dictionnaire.get(EnumLibelles.Business_Libelle_Search), 520, 320, true, false, listModelResult, imputsResultFields, entityName, keyParam, parametresGrid);
        btnValider.setText("Ok");
    }

    @Override
    public void init(Object... var) {
        this.listModelResult = (List<HashMap>) var[0];
        this.imputsResultFields = (Object[]) var[1];
        this.entity = (String) var[2];
        this.keyParam = (String) var[3];
        this.parametresGrid = (Object[][]) var[4];
    }
    
    /**
     * Creates new form UserForm
     *
     * @param title
     */
    public SearchForm(String title) {
        super(title, 400, 320);
    }

    public void createForm() {
        initComponents();
        
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose();
            }
        });
    }

    public void addActionSupplementaire() {
        
        columnNames = new ArrayList<>();
        columnValues = new ArrayList<>();
        columnClasses = new ArrayList<>();
        tableModel = new DefaultTableModel();

        Loading.show(btnValider, new Executable<List<HashMap>>() {
            @Override
            public void execute() throws Exception {
                grid.setModel(setModelDataTable());
                grid.setRowSorter(sorter);
            }

            @Override
            public List<HashMap> success() {
                return listModelResult;
            }

            @Override
            public void error(Exception ex) {
                JOptionPane.showMessageDialog(rootPane, Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.OK_OPTION);
            }

        });
    }

    @Override
    protected void eventActionRef() {
    }

    @Override
    public void makeModelData(){
        super.makeModelData();
    }

    private TableModel setModelDataTable() {

        for (Object[] param : parametresGrid) {
            columnValues.add((String) param[0]);
            columnNames.add((String) param[1]);
            if(param.length > 2) columnClasses.add((Class) param[2]);
            else columnClasses.add(null);
        }

        tableModel = new DefaultTableModel() {
            
            @Override
            public int getRowCount() {
                return listModelResult.size();
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
                return (columnClasses.get(columnIndex) == null) ? listModelResult.get(0).get(columnValues.get(columnIndex)).getClass()
                        : columnClasses.get(columnIndex);
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return listModelResult.get(row).get(columnValues.get(column));
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                super.setValueAt(listModelResult.get(rowIndex).get(columnValues.get(columnIndex)), rowIndex, columnIndex);
            }
        };

        sorter = new TableRowSorter<>(tableModel);

        return tableModel;
    }

    private int[] getIndices(int count) {
        int[] result = new int[count];
        
        for(int i = 0; i < count; i++){
            result[i] = i;
        }
        
        return result;
    }
    
    private void setResult(HashMap result) {
        this.modelFinal = result;
    }
    
    public HashMap getResult(){
        return this.modelFinal;
    }

    @Override
    public void actionBtnValider(ActionEvent evt) {
        
        Loading.show(btnValider, new Executable<HashMap>() {
            HashMap result;
            @Override
            public void execute() throws Exception {

                ListSelectionModel selectionModel = grid.getSelectionModel();

                TableModel model = grid.getModel();
                
                
                int rowIndex = grid.convertRowIndexToModel(grid.getSelectedRow());
                
                result = listModelResult.get(rowIndex);

                for (Object field : imputsResultFields) {
                    try {
                        if (field.getClass().equals(JTextField.class)) {
                            JTextField res = (JTextField) field;
                            res.setText(Dictionnaire.get(result.get(res.getName()).toString()));
                        }else if(field.getClass().equals(JFormattedTextField.class)){
                            JFormattedTextField res = (JFormattedTextField) field;
                                
//                            JFormattedTextField.AbstractFormatterFactory r = res.getFormatterFactory();
//                            
//                            JFormattedTextField.AbstractFormatter o = r.getFormatter(res);
//                            
//                            if(r.getFormatter(res) instanceof DateFormatter){
//                                SimpleDateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
//                                res.setValue(df.format(result.get(res.getName())));
//                            }else{
                                res.setValue(result.get(res.getName()));
//                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                
                setResult(result);

                if(keyParam == null)
                    FormParameters.add(entity, result);
                else
                    FormParameters.add(keyParam, result);
                
            }

            @Override
            public HashMap success() {
                doClose();
                return result;
            }

            @Override
            public void error(Exception ex) {
                MessageForm.showsError(Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", false, null);
            }
        });
    }
    
    
    private void doClose() {
        setVisible(false);
        dispose();
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
        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        stripedTableScrollPane = new javax.swing.JScrollPane();
        grid = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(400, 234));
        setPreferredSize(new java.awt.Dimension(400, 234));

        labelSearch.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        labelSearch.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Filtre)); // NOI18N
        panelSearch.add(labelSearch);

        searchImput.setMaximumSize(new java.awt.Dimension(200, 28));
        searchImput.setMinimumSize(new java.awt.Dimension(200, 28));
        searchImput.setPreferredSize(new java.awt.Dimension(200, 28));
        searchImput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchImputActionPerformed(evt);
            }
        });
        panelSearch.add(searchImput);

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        panelSearch.add(btnSearch);

        getContentPane().add(panelSearch, java.awt.BorderLayout.PAGE_START);

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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
        grid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gridMouseClicked(evt);
            }
        });
        stripedTableScrollPane.setViewportView(grid);
        grid.putClientProperty("Quaqua.Table.style", "striped");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stripedTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(stripedTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        RowFilter<TableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(searchImput.getText(), getIndices(grid.getColumnCount()));
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void searchImputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchImputActionPerformed
        // TODO add your handling code here:
        RowFilter<TableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(searchImput.getText(), getIndices(grid.getColumnCount()));
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }//GEN-LAST:event_searchImputActionPerformed

    private void gridMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gridMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            btnValider.doClick();
        } else {
        }
    }//GEN-LAST:event_gridMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JTable grid;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JTextField searchImput;
    private javax.swing.JScrollPane stripedTableScrollPane;
    // End of variables declaration//GEN-END:variables

}
