/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.backoffice;

import cm.codebrain.ui.application.ModelForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import static cm.codebrain.ui.application.enumerations.Enums.CREATE;
import static cm.codebrain.ui.application.enumerations.Enums.Type;
import static cm.codebrain.ui.application.enumerations.Enums.Entity;
import java.util.HashMap;
import static cm.codebrain.ui.application.enumerations.Enums.Value;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author KSA-INET
 */
public class GroupSalleForm extends ModelForm {

    private final String entitySalle = "Salle";
    private final String entityClasse = "Classe";
    private TableRowSorter<TableModel> sorter;
    private DefaultTableModel tableModel;

    /**
     * Creates new form UserForm
     *
     * @param title
     */
    public GroupSalleForm(String title) {
        super(title, 655, 600);

        this.showActionBar();

        this.setActionMenu(CREATE);
        this.showMenuBar();

//        this.addFormData("userCode", title);
//    addActionSupplementaire(
    }

    @Override
    public void createForm() {

        this.entity = "Groupe";

        initComponents();

        sorter = new TableRowSorter<>(grid.getModel());

        setAllComponents(salleInput, salleIntituleInput, salleInput, salleIntituleInput, codeInput, intituleInput);
    }

    @Override
    public void addActionSupplementaire() {
        super.addActionSupplementaire(); //To change body of generated methods, choose Tools | Templates.

        eventClasse();
        eventSalle();
    }

    private void eventSalle() {

        HashMap[] args = new HashMap[1];

        String filter = "entity.classeId=:arg0";

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entityClasse);
//        arg.put(clause, "categorie==");
        arg.put(Value, classeInput);

        args[0] = arg;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(salleInput, entitySalle, parametresGrid, filter, args, salleInput, salleIntituleInput);
    }

    private void eventClasse() {

        HashMap[] args = null;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(classeInput, entityClasse, parametresGrid, null, args, classeInput, classeIntituleInput);
    }

    @Override
    public void actionBtnValider(java.awt.event.ActionEvent evt) {
        super.actionBtnValider(evt);
    }

    @Override
    public void makeModelData() {
        super.makeModelData();
//        modelFinal.put(entityCategorie.toLowerCase(), GlobalParameters.getVar(entityCategorie));
//        modelFinal.put(entityClasse.toLowerCase(), GlobalParameters.getVar(entityClasse));

    }

    private DefaultTableModel setModelDataTable(List<String> columnsName) {
//List<HashMap> modelFinal, 
//        for (String[] param : parametresGrid) {
//            columnValues.add(param[0]);
//            columnNames.add(param[1]);
//        }

        tableModel =  new DefaultTableModel() {
            @Override
            public int getRowCount() {
                return super.getRowCount();
//                return modelFinal.size();
            }

            @Override
            public int getColumnCount() {
                return columnsName.size();
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnsName.get(columnIndex);
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return super.getColumnClass(columnIndex);
//                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return super.getValueAt(row, column);
//                return modelFinal.get(row).get(columnValues.get(column));
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                super.setValueAt(aValue, rowIndex, columnIndex);//modelFinal.get(rowIndex).get(columnValues.get(columnIndex)), rowIndex, columnIndex);
            }
        };

        return tableModel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        javax.swing.JPanel panelContent = new javax.swing.JPanel();
        javax.swing.JPanel panelIdentifiant = new javax.swing.JPanel();
        javax.swing.JLabel labelCode = new javax.swing.JLabel();
        javax.swing.JLabel labelIntitule = new javax.swing.JLabel();
        codeInput = new javax.swing.JTextField();
        intituleInput = new javax.swing.JTextField();
        javax.swing.JPanel panelSalle = new javax.swing.JPanel();
        javax.swing.JLabel labelSalle = new javax.swing.JLabel();
        salleInput = new javax.swing.JTextField();
        salleIntituleInput = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid = new javax.swing.JTable();
        javax.swing.JPanel jPanelButtons = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        javax.swing.JPanel panelClasse = new javax.swing.JPanel();
        javax.swing.JLabel labelClasse = new javax.swing.JLabel();
        classeInput = new javax.swing.JTextField();
        classeIntituleInput = new javax.swing.JTextField();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelIdentifiant.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Identifiant))); // NOI18N
        panelIdentifiant.setOpaque(false);

        labelCode.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelCode.setName("usernameLabel"); // NOI18N

        labelIntitule.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)); // NOI18N
        labelIntitule.setName("usernameLabel"); // NOI18N

        codeInput.setName("codeInput"); // NOI18N
        this.addFormData("code", codeInput);
        this.setRef(codeInput);

        intituleInput.setName("intituleInput"); // NOI18N
        this.addFormData("intitule", intituleInput);

        javax.swing.GroupLayout panelIdentifiantLayout = new javax.swing.GroupLayout(panelIdentifiant);
        panelIdentifiant.setLayout(panelIdentifiantLayout);
        panelIdentifiantLayout.setHorizontalGroup(
            panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelIntitule, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(labelCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codeInput, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(intituleInput))
                .addContainerGap())
        );
        panelIdentifiantLayout.setVerticalGroup(
            panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIntitule, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSalle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Salle))); // NOI18N
        panelSalle.setOpaque(false);

        labelSalle.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelSalle.setName("usernameLabel"); // NOI18N

        salleInput.setName("code"); // NOI18N
        this.addFormData("salle", salleInput);

        salleIntituleInput.setName("intitule"); // NOI18N

        javax.swing.GroupLayout panelSalleLayout = new javax.swing.GroupLayout(panelSalle);
        panelSalle.setLayout(panelSalleLayout);
        panelSalleLayout.setHorizontalGroup(
            panelSalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salleIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSalleLayout.setVerticalGroup(
            panelSalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salleIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        grid.setModel(setModelDataTable(Arrays.asList(
            Dictionnaire.get(EnumLibelles.Business_Libelle_code).concat(" " + Dictionnaire.get(EnumLibelles.Business_Libelle_Classe)),
            Dictionnaire.get(EnumLibelles.Business_Libelle_GrouperSalles),
            Dictionnaire.get(EnumLibelles.Business_Libelle_code).concat(" " + Dictionnaire.get(EnumLibelles.Business_Libelle_Salle)),
            Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule).concat(" " + Dictionnaire.get(EnumLibelles.Business_Libelle_Salle))
        )));
        grid.setShowHorizontalLines(true);
        grid.setShowVerticalLines(true);
        grid.getTableHeader().setReorderingAllowed(false);
        grid.setUpdateSelectionOnSort(false);
        grid.putClientProperty("Quaqua.Table.style", "striped");
        jScrollPane1.setViewportView(grid);

        jPanelButtons.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        addButton.setIcon(new ImageIcon(Dictionnaire.getResource(ADD).getScaledInstance(width, height, 0)));
        addButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Add)); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanelButtons.add(addButton);

        jButton2.setIcon(new ImageIcon(Dictionnaire.getResource(DEL).getScaledInstance(width, height, 0)));
        jButton2.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Sub)); // NOI18N
        jPanelButtons.add(jButton2);

        resetButton.setIcon(new ImageIcon(Dictionnaire.getResource(RESET).getScaledInstance(width, height, 0)));
        resetButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Reset)); // NOI18N
        jPanelButtons.add(resetButton);

        panelClasse.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Classe))); // NOI18N
        panelClasse.setOpaque(false);

        labelClasse.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelClasse.setName("usernameLabel"); // NOI18N

        classeInput.setName("code"); // NOI18N
        this.addFormData("classe", classeInput);

        classeIntituleInput.setName("intitule"); // NOI18N

        javax.swing.GroupLayout panelClasseLayout = new javax.swing.GroupLayout(panelClasse);
        panelClasse.setLayout(panelClasseLayout);
        panelClasseLayout.setHorizontalGroup(
            panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(classeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(classeIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelClasseLayout.setVerticalGroup(
            panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classeIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(panelContentLayout);
        panelContentLayout.setHorizontalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContentLayout.createSequentialGroup()
                                .addComponent(panelIdentifiant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(137, 137, 137))
                            .addComponent(panelSalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addGroup(panelContentLayout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelClasse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelContentLayout.setVerticalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelIdentifiant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
//        Ab m grid.getModel();
        Object[] newRow = {classeInput.getText(), codeInput.getText(), intituleInput.getText(), salleInput.getText()};
        
        tableModel.addRow(newRow);
        reset(classeInput, classeIntituleInput, salleInput, salleIntituleInput);
    }//GEN-LAST:event_addButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField classeInput;
    private javax.swing.JTextField classeIntituleInput;
    private javax.swing.JTextField codeInput;
    private javax.swing.JTable grid;
    private javax.swing.JTextField intituleInput;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton resetButton;
    private javax.swing.JTextField salleInput;
    private javax.swing.JTextField salleIntituleInput;
    // End of variables declaration//GEN-END:variables

}
