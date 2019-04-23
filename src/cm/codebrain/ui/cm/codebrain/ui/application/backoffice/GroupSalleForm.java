/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.backoffice;

import cm.codebrain.ui.application.ModelForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.FormParameters;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import static cm.codebrain.ui.application.enumerations.EnumVariable.CREATE;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Entity;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Field;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Model;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Type;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
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

    private List<HashMap> listModelsOriginal;
    private List<HashMap> listModelsAdd;
    private List<HashMap> listModelsSub;
    private HashMap<Object, Object> modelSalle;
    private boolean gridIsEmpty;

    /**
     * Creates new form UserForm
     *
     * @param title
     */
    public GroupSalleForm(String title) {
        super(title, 655, 600, false, true);

        this.showActionBar();

        createList(false);
        setAction(EnumVariable.Master_Detail);
        
        etatAction = CREATE;
        this.showMenuBar();
    }

    /**
     *
     */
    public void createForm() {

        this.entity = "Groupe";

        listModelsOriginal = new ArrayList<>();
        listModelsAdd = new ArrayList<>();
        listModelsSub = new ArrayList<>();

        initComponents();

        sorter = new TableRowSorter<>(grid.getModel());

        setAllComponents(salleInput, salleIntituleInput, classeInput, classeIntituleInput, codeInput, intituleInput, grid);
    }

    public void addActionSupplementaire() {
        eventClasse();
        eventSalle();
        
        grid.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int rowNumber = grid.getSelectedRow();
            HashMap model = null;
            try {
                model = listModelsOriginal.get(rowNumber);
            } catch (Exception ex) {
            }
                if(model == null){
                    model = listModelsAdd.get(rowNumber-listModelsOriginal.size());
                    getModelData(model, salleInput, salleIntituleInput);
                    modifyButton.setEnabled(true);
                    addButton.setEnabled(false);
                }else{
                    modifyButton.setEnabled(false);
                    addButton.setEnabled(true);
                    reset(salleInput, salleIntituleInput);
                }
        });
    }

    private void eventSalle() {

        HashMap[] args = new HashMap[1];

        String filter = "entity.classeId=:arg0";

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entityClasse);
        arg.put(Model, "classeId");
        arg.put(Value, classeInput);

        args[0] = arg;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(salleInput, entitySalle, entitySalle.toLowerCase() + "Id", parametresGrid, filter, args, salleInput, salleIntituleInput);
    }

    private void eventClasse() {

        HashMap[] args = null;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(classeInput, entityClasse, entityClasse.toLowerCase() + "Id", parametresGrid, null, args, classeInput, classeIntituleInput);
    }

    protected void eventActionRef() {

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(codeInput, entity, entity.toLowerCase() + "Id", parametresGrid, null, null, codeInput, salleIntituleInput);
    }

    @Override
    public void makeModelData() {
        super.makeModelData(); //To change body of generated methods, choose Tools | Templates.

        modelFinal.put(entityClasse.toLowerCase() + "Id", FormParameters.get(entityClasse.toLowerCase() + "Id"));

        HashMap modelAdd = new HashMap();
        modelAdd.put(Entity, entitySalle);
        modelAdd.put(Field, entity.toLowerCase() + "Id");
        modelAdd.put(Model, listModelsAdd);

        HashMap modelSub = new HashMap();
        modelSub.put(Entity, entitySalle);
        modelSub.put(Field, entity.toLowerCase() + "Id");
        modelSub.put(Model, listModelsSub);

        setActionModel(Arrays.asList(modelAdd), Arrays.asList(modelSub));
    }

    @Override
    public void addActionComplement() {
        if (gridIsEmpty) {
            if (etatAction != CREATE) {

                HashMap[] args = new HashMap[1];

                String filter = "entity.groupeId=:arg0";

                HashMap arg = new HashMap();

                arg.put(Type, Entity);
                arg.put(Entity, entity);
                arg.put(Model, "groupeId");
                arg.put(Value, codeInput);

                args[0] = arg;

                try {
                    listModelsOriginal = getListModelForSelect(null, entitySalle, null, filter, args);
                } catch (Exception ex) {
                    Logger.getLogger(GroupSalleForm.class.getName()).log(Level.SEVERE, null, ex);
                }

                listModelsOriginal.stream().map((model) -> {
                    Object[] newRow = {model.get("code"), model.get("intitule")};
                    return newRow;
                }).forEachOrdered((newRow) -> {
                    ((DefaultTableModel) ((JTable) grid).getModel()).addRow(newRow);
                });
                reset(salleInput, salleIntituleInput);
                gridIsEmpty = false;
            }
        }
    }

    @Override
    public void setActionMenu() {
        super.setActionMenu();
        gridIsEmpty = true;
        listModelsOriginal.clear();
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
        listModelsOriginal.clear();
    }

    private HashMap findModel(HashMap model, String filter, List<HashMap> listModelsOriginal) {
        HashMap modelFind = null;
        try {
            if (listModelsOriginal.size() > 0) {
                for (HashMap tmpModel : listModelsOriginal) {
                    if (tmpModel.get(filter).equals(model.get(filter))) {
                        modelFind = tmpModel;
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return modelFind;
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
        modifyButton = new javax.swing.JButton();
        subButton = new javax.swing.JButton();
        javax.swing.JPanel panelClasse = new javax.swing.JPanel();
        javax.swing.JLabel labelClasse = new javax.swing.JLabel();
        classeInput = new javax.swing.JTextField();
        classeIntituleInput = new javax.swing.JTextField();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelIdentifiant.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Identifiant))); // NOI18N
        panelIdentifiant.setOpaque(false);

        labelCode.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code, true)); // NOI18N
        labelCode.setName("usernameLabel"); // NOI18N

        labelIntitule.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule, true)); // NOI18N
        labelIntitule.setName("usernameLabel"); // NOI18N

        codeInput.setName("codeInput"); // NOI18N
        this.addFormData("code", codeInput);
        this.setRef(codeInput);
        fieldSearch.put("code", codeInput);
        fieldsRequired.add(codeInput);

        intituleInput.setName("intituleInput"); // NOI18N
        this.addFormData("intitule", intituleInput);
        fieldSearch.put("intitule", intituleInput);
        fieldsRequired.add(intituleInput);

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
        this.addFormData("salleId", salleInput);
        fieldSearch("Salle->code", salleInput);

        salleIntituleInput.setName("intitule"); // NOI18N
        fieldSearch("Salle->intitule", salleIntituleInput);

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

        grid.setModel(setModelDataTable(
            Dictionnaire.get(EnumLibelles.Business_Libelle_code).concat(" " + Dictionnaire.get(EnumLibelles.Business_Libelle_Salle)),
            Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule).concat(" " + Dictionnaire.get(EnumLibelles.Business_Libelle_Salle))
        ));
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

        modifyButton.setIcon(new ImageIcon(Dictionnaire.getResource(RESET).getScaledInstance(width, height, 0)));
        modifyButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Modify)); // NOI18N
        modifyButton.setEnabled(false);
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });
        jPanelButtons.add(modifyButton);

        subButton.setIcon(new ImageIcon(Dictionnaire.getResource(DEL).getScaledInstance(width, height, 0)));
        subButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Sub)); // NOI18N
        subButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subButtonActionPerformed(evt);
            }
        });
        jPanelButtons.add(subButton);

        panelClasse.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Classe))); // NOI18N
        panelClasse.setOpaque(false);

        labelClasse.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code, true)); // NOI18N
        labelClasse.setName("usernameLabel"); // NOI18N

        classeInput.setName("code"); // NOI18N
        fieldSearch("Groupe->classeId->code", classeInput);
        fieldsRequired.add(classeInput);

        classeIntituleInput.setName("intitule"); // NOI18N
        fieldSearch("Groupe->classeId->intitule", classeIntituleInput);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        modelSalle = new HashMap<>();
        modelSalle = (HashMap) FormParameters.get(entitySalle.toLowerCase() + "Id");

        if (findModel(modelSalle, "code", listModelsOriginal) == null) {
            Object[] newRow = {salleInput.getText(), salleIntituleInput.getText()};

            setTableModel(salleInput, salleIntituleInput);

            ((DefaultTableModel) ((JTable) grid).getModel()).addRow(newRow);

            listModelsAdd.add(modelSalle);

            reset(salleInput, salleIntituleInput);
            
            modifyButton.setEnabled(false);
            addButton.setEnabled(true);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void subButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subButtonActionPerformed
        // TODO add your handling code here:

        int rowIndex = grid.convertRowIndexToModel(grid.getSelectedRow());
        try {
            ((DefaultTableModel) ((JTable) grid).getModel()).removeRow(rowIndex);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        grid.repaint();

        if (etatAction != CREATE) {
            modelSalle = listModelsOriginal.get(rowIndex);
            if (modelSalle != null) {
                listModelsSub.add(modelSalle);
            }else{
                listModelsAdd.remove(rowIndex);
            }
        }else{
            listModelsAdd.remove(rowIndex);
        }

        reset(salleInput, salleIntituleInput);
    }//GEN-LAST:event_subButtonActionPerformed

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        // TODO add your handling code here:
        int rowIndex = grid.convertRowIndexToModel(grid.getSelectedRow());

        modelSalle = new HashMap<>();
        modelSalle.put(entitySalle.toLowerCase() + "Id", FormParameters.get(entitySalle.toLowerCase() + "Id"));

        if (findModel(modelSalle, "code", listModelsOriginal) == null) {
            try {
                ((DefaultTableModel) ((JTable) grid).getModel()).removeRow(rowIndex);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            grid.repaint();

            listModelsAdd.remove(rowIndex);
            listModelsAdd.add(modelSalle);

            Object[] newRow = {salleInput.getText(), salleIntituleInput.getText()};

            setTableModel(salleInput, salleIntituleInput);

            ((DefaultTableModel) ((JTable) grid).getModel()).addRow(newRow);
        }
        reset(salleInput, salleIntituleInput);
        
        modifyButton.setEnabled(false);
        addButton.setEnabled(true);
    }//GEN-LAST:event_modifyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField classeInput;
    private javax.swing.JTextField classeIntituleInput;
    private javax.swing.JTextField codeInput;
    private javax.swing.JTable grid;
    private javax.swing.JTextField intituleInput;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modifyButton;
    private javax.swing.JTextField salleInput;
    private javax.swing.JTextField salleIntituleInput;
    private javax.swing.JButton subButton;
    // End of variables declaration//GEN-END:variables

}
