/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.administration;

import cm.codebrain.ui.application.MessageForm;
import cm.codebrain.ui.application.ModelForm;
import static cm.codebrain.ui.application.ModelForm.RET_CANCEL;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.FormParameters;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Entity;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Model;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Type;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KSA-INET
 */
public class AffectationCoursForm extends ModelForm {

    private final String entityEnseignant = "Enseignant";
    private final String entityCours = "Cours";
    private List<HashMap> listCours;
    private List<HashMap> listAffectationOriginal;
    private DefaultTableModel modelList;
    private final List<HashMap> listAffectionCours = new ArrayList<>();

    /**
     * Creates new form CategorieForm
     *
     * @param title
     */
    public AffectationCoursForm(String title) {
        super(title, 950, 500, true, true);

        createList(false);
        setAction(EnumVariable.List);
        this.showMenuBar();
    }

    public void createForm() {

        this.entity = "AffectationCours";
        initComponents();
        setAllComponents(matriculeInput, firstNameInput, lastNameInput, coursInputsTable);
    }

    public void addActionSupplementaire() {
        eventEnseignant();
        loadGridCoursDisponible();
    }

    @Override
    public void makeModelData() {
//        super.makeModelData();
        final Object modelEnseignement = FormParameters.get(entityEnseignant.toLowerCase() + "Id");
        
//        List<HashMap> listModelsAdd = new ArrayList<>();
        
        List<HashMap> listModelsAdd = filterModel(this.listAffectationOriginal, "AffectationCours->coursId->code", this.listAffectionCours, "AffectationCours->coursId->code", false);
        List<HashMap> listModelsSub = filterModel(this.listAffectionCours, "AffectationCours->coursId->code", this.listAffectationOriginal, "AffectationCours->coursId->code", false);
        
        if(listModelsAdd != null && listModelsAdd.size() > 0){
            listModelsAdd.forEach((model) -> {
                model.put(entityEnseignant.toLowerCase() + "Id", modelEnseignement);
//                modelFinal.put(entityCours.toLowerCase()+"Id", model);
//                listModelsAdd.add(modelFinal);
            });
        }
        
        setActionModel(listModelsAdd, listModelsSub);
    }

    protected void eventActionRef() {
    }

//    @Override
    public void loadGridCoursOfEnseignant() {
        
//        if (etatAction != CREATE) {

        HashMap[] args = new HashMap[1];

        String filter = "entity.enseignantId=:arg0";

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entityEnseignant);
        arg.put(Model, "enseignantId");
        arg.put(Value, matriculeInput);

        args[0] = arg;

        try {
            listAffectationOriginal = getListModelForSelect(null, entity, null, filter, args);

            if (this.listAffectationOriginal != null && this.listAffectationOriginal.size() > 0) {
                
                List<HashMap> tmp = new ArrayList<>();
                
                ((DefaultTableModel) coursInputsTable.getModel()).setRowCount(0);
                coursInputsTable.repaint();
                
                listAffectionCours.clear();
                listAffectationOriginal.forEach((model)->{
                    getValueModelFromKey("AffectationCours->coursId->code", model);
                    tmp.add((HashMap) FormParameters.get("coursId"));
                });
                
                addModelToTable(coursInputsTable, tmp);
                listAffectionCours.addAll(listAffectationOriginal);
            }
        } catch (Exception ex) {
            listAffectationOriginal = null;
        }
    }

    private void loadGridCoursDisponible() {
        Loading.show(null, new Executable() {
            @Override
            public void execute() throws Exception{

                HashMap[] args = null;

                listCours = null;
                    listCours = getListModelForSelect(null, entityCours, null, null, args);
            }

            @Override
            public List<HashMap> success(){
                listCours.stream().map((model) -> {
                    Object[] newRow = {false, model.get("code"), model.get("libelleFr"), model.get("libelleEn")};
                    return newRow;
                }).forEachOrdered((newRow) -> {
                    ((DefaultTableModel) ((JTable) coursListTable).getModel()).addRow(newRow);
                });

                return listCours;
            }

            @Override
            public void error(Exception ex) {
                MessageForm.showsError(ex.getMessage(), "Message", false, null);
                doClose(RET_CANCEL);
                dispose();
            }
        });
    }

    protected void eventEnseignant() {

        String[][] parametresGrid = {
            {"matricule", Dictionnaire.get(EnumLibelles.Business_Libelle_Matricule)},
            {"firstName", Dictionnaire.get(EnumLibelles.Business_Libelle_nom)},
            {"lastName",
                Dictionnaire.get(EnumLibelles.Business_Libelle_prenom)}};

        addAction(matriculeInput, entityEnseignant, parametresGrid, null, null, matriculeInput, firstNameInput, lastNameInput);
    }

    private List<HashMap> filterModel(List<HashMap> listModelsOriginal, String value1, List<HashMap> listTmp, String value2, boolean equal) {
        List<HashMap> lst = new ArrayList<>();
        if(listTmp != null && listTmp.size()>0)
            for (HashMap modelFind : listTmp) {
                Boolean find = false;
                if(listModelsOriginal != null && listModelsOriginal.size()>0)
                    for (HashMap model : listModelsOriginal) {
                        
                        if (getValueModelFromKey(value2, modelFind).equals(getValueModelFromKey(value1, model))) {
                            find = true;
                            break;
                        }
                    }

                if (find == equal) {
                    lst.add(modelFind);
                }
            }

        return lst;
    }

    private void addModelToTable(JTable grid, List<HashMap> list) {

        this.modelList = ((DefaultTableModel) ((JTable) grid).getModel());

        for (HashMap model : list) {
            Object[] newRow = {false, model.get("code"), model.get("libelleFr"), model.get("libelleEn")};
            this.modelList.addRow(newRow);
        }
        grid.repaint();
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
        javax.swing.JPanel panelCategorie1 = new javax.swing.JPanel();
        javax.swing.JLabel labelLevel1 = new javax.swing.JLabel();
        matriculeInput = new javax.swing.JTextField();
        firstNameInput = new javax.swing.JTextField();
        lastNameInput = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        coursListTable = new javax.swing.JTable();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
        coursInputsTable = new javax.swing.JTable();
        inButton = new javax.swing.JButton();
        outButton = new javax.swing.JButton();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelCategorie1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Enseignant))); // NOI18N
        panelCategorie1.setOpaque(false);

        labelLevel1.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Matricule, true)); // NOI18N
        labelLevel1.setName("usernameLabel"); // NOI18N

        matriculeInput.setName("matricule"); // NOI18N
        fieldSearch("AffectationCours->enseignantId->matricule", matriculeInput);
        fieldsRequired.add(matriculeInput);

        firstNameInput.setEditable(false);
        firstNameInput.setFocusable(false);
        firstNameInput.setName("firstName"); // NOI18N
        this.setRef(firstNameInput);
        fieldSearch("AffectationCours->EnseignantId->firstName", firstNameInput);
        firstNameInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameInputActionPerformed(evt);
            }
        });

        lastNameInput.setEditable(false);
        lastNameInput.setFocusable(false);
        lastNameInput.setName("lastName"); // NOI18N
        fieldSearch("AffectationCours->EnseignantId->lastName", lastNameInput);

        javax.swing.GroupLayout panelCategorie1Layout = new javax.swing.GroupLayout(panelCategorie1);
        panelCategorie1.setLayout(panelCategorie1Layout);
        panelCategorie1Layout.setHorizontalGroup(
            panelCategorie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategorie1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelLevel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matriculeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelCategorie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lastNameInput)
                    .addComponent(firstNameInput))
                .addContainerGap())
        );
        panelCategorie1Layout.setVerticalGroup(
            panelCategorie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategorie1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCategorie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLevel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matriculeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        matriculeInput.getAccessibleContext().setAccessibleName(entityEnseignant.toLowerCase() + "Id");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_ListeCours), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 14))); // NOI18N

        jScrollPane3.setAutoscrolls(true);

        coursListTable.setModel(setModelDataTable(Arrays.asList(Boolean.class, null, null, null),
            "",
            Dictionnaire.get(EnumLibelles.Business_Libelle_code),
            Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleFr),
            Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleEn)));
    coursListTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    coursListTable.setIntercellSpacing(new java.awt.Dimension(4, 1));
    coursListTable.setShowHorizontalLines(true);
    coursListTable.setShowVerticalLines(true);
    coursListTable.putClientProperty("Quaqua.Table.style", "striped");
    setSelectedCheckbox(coursListTable, true);
    resizeTableColumnModel(coursListTable, 30, 90, 140, 140);
    jScrollPane3.setViewportView(coursListTable);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
    );

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), Dictionnaire.get(EnumLibelles.Business_Libelle_Cours, true), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 14))); // NOI18N

    jScrollPane4.setAutoscrolls(true);

    coursInputsTable.setModel(setModelDataTable(Arrays.asList(Boolean.class, null, null, null),
        "",
        Dictionnaire.get(EnumLibelles.Business_Libelle_code),
        Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleFr),
        Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleEn)));
coursInputsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
coursInputsTable.setIntercellSpacing(new java.awt.Dimension(4, 1));
coursInputsTable.setShowHorizontalLines(true);
coursInputsTable.setShowVerticalLines(true);
coursInputsTable.getTableHeader().setResizingAllowed(false);
coursInputsTable.getTableHeader().setReorderingAllowed(false);
coursInputsTable.putClientProperty("Quaqua.Table.style", "striped");
setSelectedCheckbox(coursInputsTable, true);
resizeTableColumnModel(coursInputsTable, 30, 90, 140, 140);
jScrollPane4.setViewportView(coursInputsTable);

javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
jPanel2.setLayout(jPanel2Layout);
jPanel2Layout.setHorizontalGroup(
    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
    );

    inButton.setIcon(new ImageIcon(Dictionnaire.getResource(NEXT).getScaledInstance(width, height, 0)));
    inButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            inButtonActionPerformed(evt);
        }
    });

    outButton.setIcon(new ImageIcon(Dictionnaire.getResource(PREVIEW).getScaledInstance(width, height, 0)));
    outButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            outButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
    panelContent.setLayout(panelContentLayout);
    panelContentLayout.setHorizontalGroup(
        panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelContentLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCategorie1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelContentLayout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(outButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap())
    );
    panelContentLayout.setVerticalGroup(
        panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(panelContentLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(panelCategorie1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelContentLayout.createSequentialGroup()
                    .addGap(106, 106, 106)
                    .addComponent(inButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(outButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 93, Short.MAX_VALUE))
                .addGroup(panelContentLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addContainerGap())
    );

    javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
        mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(mainPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
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

    private void inButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inButtonActionPerformed
        // TODO add your handling code here:
        if (this.listCours != null && this.listCours.size() > 0) {
            this.modelList = (DefaultTableModel) coursListTable.getModel();
            List<HashMap> listTmp = new ArrayList<>();
            for (int row = 0; row < this.modelList.getRowCount(); row++) {
                Boolean selected = (Boolean) this.modelList.getValueAt(row, 0);

                if (selected) {
                    listTmp.add(this.listCours.get(row));
                }
            }
            if (listTmp.size() > 0) {
                listTmp = filterModel(this.listAffectionCours, "AffectationCours->coursId->code", listTmp, "code", false);
            }
            if (listTmp.size() > 0) {
                try {
                    addModelToTable(coursInputsTable, listTmp);

                    listTmp.forEach((model)->{
                        HashMap m = new HashMap();
                        m.put(entityCours.toLowerCase()+"Id", model);
                        this.listAffectionCours.add(m);
                    });
                } catch (Exception ex) {
                }
            }
        }
    }//GEN-LAST:event_inButtonActionPerformed

    private void outButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outButtonActionPerformed
        // TODO add your handling code here:
        if (this.listAffectionCours.size() > 0) {
            this.modelList = (DefaultTableModel) coursInputsTable.getModel();
            for (int row = 0; row < this.modelList.getRowCount(); row++) {
                Boolean selected = (Boolean) this.modelList.getValueAt(row, 0);
                if (selected) {
                    try {
                        ((DefaultTableModel) coursInputsTable.getModel()).removeRow(row);
                        this.listAffectionCours.remove(row);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }//GEN-LAST:event_outButtonActionPerformed

    private void firstNameInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameInputActionPerformed
        // TODO add your handling code here:
        if(firstNameInput.getText().isEmpty()) reset(coursInputsTable);
        else loadGridCoursOfEnseignant();
    }//GEN-LAST:event_firstNameInputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable coursInputsTable;
    private javax.swing.JTable coursListTable;
    private javax.swing.JTextField firstNameInput;
    private javax.swing.JButton inButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lastNameInput;
    private javax.swing.JTextField matriculeInput;
    private javax.swing.JButton outButton;
    // End of variables declaration//GEN-END:variables

}
