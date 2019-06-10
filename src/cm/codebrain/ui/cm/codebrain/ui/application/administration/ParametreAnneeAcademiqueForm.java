/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.administration;

import cm.codebrain.ui.application.ModelForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.FormParameters;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.EnumStatus;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import static cm.codebrain.ui.application.enumerations.EnumVariable.CREATE;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Entity;
import static cm.codebrain.ui.application.enumerations.EnumVariable.MODIF;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Model;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Type;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author KSA-INET
 */
public class ParametreAnneeAcademiqueForm extends ModelForm {

    private final String entityAnneeAcademic = "AnneeAcademic";
    private final String entityEtablissement = "Etablissement";
    private final String entityTrimestre = "Trimestre";
    private final String entitySequence = "Sequence";
    private List<HashMap> listModelsOriginal;
    private EnumVariable tmpEtatAction = MODIF;

    /**
     * Creates new form CategorieForm
     *
     * @param title
     */
    public ParametreAnneeAcademiqueForm(String title) {
        super(title, 480, 390);

        etatAction = MODIF;
        setActionMenu();
        this.showMenuBar();
    }

    public void createForm() {

        this.entity = "ParametreAnneeAcademic";
        initComponents();
        setAllComponents(nomAbregeInput, nomCompletInput, sessionInput, sessionDateOuvertureInput, trimestreInput, trimestreDateOuvertureInput, sequenceInput, sequenceDateOuvertureInput);
    }

    public void addActionSupplementaire() {
        eventAnneeAcademic();
        eventTrimestre();
        eventSequence();
    }

    public void makeModelData() {
        if (etatAction != CREATE) {
            super.makeModelData();
        }//etablissementId
        modelFinal.put(entityEtablissement.toLowerCase() + "Id", FormParameters.get(entityEtablissement.toLowerCase() + "Id"));
        modelFinal.put("session", FormParameters.get("anneeAcademicId"));
//        modelFinal.put("statut", (statusInput.getSelectedIndex() == 0) ? EnumStatus.Business_Statut_NonActif.toString() : EnumStatus.Business_Statut_Actif.toString());
        
        if(statusInput.getSelectedIndex() == 0){
            modelFinal.put(entityTrimestre.toLowerCase()+"Id", null);
            modelFinal.put(entitySequence.toLowerCase()+"Id", null);
            modelFinal.put("statut", EnumStatus.Business_Statut_NonActif.toString());
        }else{
            modelFinal.put(entityTrimestre.toLowerCase()+"Id", FormParameters.get(entityTrimestre.toLowerCase()+"Id"));
            modelFinal.put(entitySequence.toLowerCase()+"Id", FormParameters.get(entitySequence.toLowerCase()+"Id"));
            modelFinal.put("statut", EnumStatus.Business_Statut_Actif.toString());
        }
    }

    protected void eventActionRef() {
        eventEtablissement();
    }

    @Override
    public void actionBtnValider(ActionEvent evt) {
        etatAction = tmpEtatAction;
        super.actionBtnValider(evt); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public void eventParametrageAnneeAcademic() {
//        if (etatAction != CREATE) {

            HashMap[] args = new HashMap[2];

            String filter = "entity.etablissementId=:arg0 and entity.session=:arg1";

            HashMap arg = new HashMap();

            arg.put(Type, Entity);
            arg.put(Entity, entityEtablissement);
            arg.put(Model, "etablissementId");
            arg.put(Value, nomAbregeInput);

            args[0] = arg;

            arg = new HashMap();

            arg.put(Type, Entity);
            arg.put(Entity, entityAnneeAcademic);
            arg.put(Model, "anneeAcademicId");
            arg.put(Value, sessionInput);

            args[1] = arg;
            try {
                try {
                    listModelsOriginal = getListModelForSelect(null, entity, null, filter, args);
                } catch (Exception ex) {
                    listModelsOriginal = null;
                }

                if (listModelsOriginal != null && listModelsOriginal.size() > 0) {
                    tmpEtatAction = MODIF;
                    
                    getModelData(listModelsOriginal.get(0));
                    
                    FormParameters.add(Model, listModelsOriginal.get(0));

//                    if (listModelsOriginal.get(0).get("statut").equals(EnumStatus.Business_Statut_NonActif.toString())) {
//                        statusInput.setSelectedIndex(0);
//                    } else {
//                        statusInput.setSelectedIndex(1);
//                    }

                } else {
                    tmpEtatAction = CREATE;
                }
            } catch (Exception ex) {
            }

//        }
    }

    protected void eventEtablissement() {

        String[][] parametresGrid = {
            {"nameAbrege", Dictionnaire.get(EnumLibelles.Business_Libelle_Nom_Abrege)},
            {"fullName",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Nom_Complet)}};

        addAction(nomAbregeInput, entityEtablissement, parametresGrid, null, null, nomAbregeInput, nomCompletInput);
    }

    private void eventAnneeAcademic() {

        HashMap[] args = null;

        String[][] parametresGrid = {
            {"session", Dictionnaire.get(EnumLibelles.Business_Libelle_Session)}};

        addAction(sessionInput, entityAnneeAcademic, parametresGrid, null, args, sessionInput, sessionDateOuvertureInput);
    }

    private void eventTrimestre() {

        HashMap[] args = new HashMap[1];

        String filter = "entity.anneeAcademicId=:arg0";

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entityAnneeAcademic);
        arg.put(Model, "anneeAcademicId");
        arg.put(Value, sessionInput);

        args[0] = arg;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)}};

        addAction(trimestreInput, entityTrimestre, parametresGrid, filter, args, trimestreInput, trimestreDateOuvertureInput);
    }

    private void eventSequence() {

        HashMap[] args = new HashMap[2];

        String filter = "entity.anneeAcademicId=:arg0 and entity.trimestreId=:arg1";

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entityAnneeAcademic);
        arg.put(Model, "anneeAcademicId");
        arg.put(Value, sessionInput);

        args[0] = arg;

        arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entityTrimestre);
        arg.put(Model, "trimestreId");
        arg.put(Value, trimestreInput);

        args[1] = arg;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)}};

        addAction(sequenceInput, entitySequence, parametresGrid, filter, args, sequenceInput, sequenceDateOuvertureInput);
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
        javax.swing.JPanel panelCategorie = new javax.swing.JPanel();
        javax.swing.JLabel labelLevel = new javax.swing.JLabel();
        nomAbregeInput = new javax.swing.JTextField();
        nomCompletInput = new javax.swing.JTextField();
        javax.swing.JLabel labelAnneeAcademic = new javax.swing.JLabel();
        sessionInput = new javax.swing.JTextField();
        statusInput = new javax.swing.JComboBox<>();
        javax.swing.JLabel labelAnneeAcademic1 = new javax.swing.JLabel();
        trimestreInput = new javax.swing.JTextField();
        javax.swing.JLabel labelAnneeAcademic2 = new javax.swing.JLabel();
        sequenceInput = new javax.swing.JTextField();
        sequenceDateOuvertureInput = new javax.swing.JFormattedTextField();
        trimestreDateOuvertureInput = new javax.swing.JFormattedTextField();
        sessionDateOuvertureInput = new javax.swing.JFormattedTextField();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelCategorie.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Etablissement))); // NOI18N
        panelCategorie.setOpaque(false);

        labelLevel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code, true)); // NOI18N
        labelLevel.setName("usernameLabel"); // NOI18N

        nomAbregeInput.setName("nameAbrege"); // NOI18N
        this.setRef(nomAbregeInput);
        fieldSearch("ParametreAnneeAcademic->etablissementId->nameAbrege", nomAbregeInput);
        fieldsRequired.add(nomAbregeInput);

        nomCompletInput.setEditable(false);
        nomCompletInput.setFocusable(false);
        nomCompletInput.setName("fullName"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->etablissementId->fullName", nomCompletInput);
        nomCompletInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomCompletInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCategorieLayout = new javax.swing.GroupLayout(panelCategorie);
        panelCategorie.setLayout(panelCategorieLayout);
        panelCategorieLayout.setHorizontalGroup(
            panelCategorieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategorieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomAbregeInput, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomCompletInput, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelCategorieLayout.setVerticalGroup(
            panelCategorieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategorieLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCategorieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomAbregeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomCompletInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nomAbregeInput.getAccessibleContext().setAccessibleName(entityEtablissement.toLowerCase() + "Id");

        labelAnneeAcademic.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_AnneeAcademique, true)); // NOI18N
        labelAnneeAcademic.setName("usernameLabel"); // NOI18N

        sessionInput.setName("session"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->session->session", sessionInput);
        fieldsRequired.add(sessionInput);

        statusInput.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        statusInput.setModel(setModelDataComboBox(Dictionnaire.get(EnumStatus.Business_Statut_NonActif), Dictionnaire.get(EnumStatus.Business_Statut_Actif))
        );
        statusInput.setActionCommand("status");
        statusInput.setName("status"); // NOI18N
        fieldSearch("statut", statusInput);
        statusInput.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                statusInputItemStateChanged(evt);
            }
        });

        labelAnneeAcademic1.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Trimestre)); // NOI18N
        labelAnneeAcademic1.setName("usernameLabel"); // NOI18N

        trimestreInput.setName("code"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->trimestreId->code", trimestreInput);

        labelAnneeAcademic2.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Sequence)); // NOI18N
        labelAnneeAcademic2.setName("usernameLabel"); // NOI18N

        sequenceInput.setName("code"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->sequenceId->code", sequenceInput);

        sequenceDateOuvertureInput.setEditable(false);
        sequenceDateOuvertureInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        sequenceDateOuvertureInput.setFocusable(false);
        sequenceDateOuvertureInput.setName("dateOuverture"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->sequenceId->dateOuverture", sequenceDateOuvertureInput);

        trimestreDateOuvertureInput.setEditable(false);
        trimestreDateOuvertureInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        trimestreDateOuvertureInput.setFocusable(false);
        trimestreDateOuvertureInput.setName("dateOuverture"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->trimestreId->dateOuverture", trimestreDateOuvertureInput);
        trimestreDateOuvertureInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trimestreDateOuvertureInputActionPerformed(evt);
            }
        });

        sessionDateOuvertureInput.setEditable(false);
        sessionDateOuvertureInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        sessionDateOuvertureInput.setFocusable(false);
        sessionDateOuvertureInput.setName("dateFermeture"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->session->dateOuverture", sessionDateOuvertureInput);
        fieldsRequired.add(sessionDateOuvertureInput);
        sessionDateOuvertureInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionDateOuvertureInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(panelContentLayout);
        panelContentLayout.setHorizontalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelCategorie, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContentLayout.createSequentialGroup()
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContentLayout.createSequentialGroup()
                                .addComponent(labelAnneeAcademic2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sequenceInput))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContentLayout.createSequentialGroup()
                                .addComponent(labelAnneeAcademic1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(trimestreInput, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sequenceDateOuvertureInput, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(trimestreDateOuvertureInput)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContentLayout.createSequentialGroup()
                        .addComponent(labelAnneeAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statusInput, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelContentLayout.createSequentialGroup()
                                .addComponent(sessionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sessionDateOuvertureInput, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelContentLayout.setVerticalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAnneeAcademic)
                    .addComponent(sessionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sessionDateOuvertureInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAnneeAcademic1)
                    .addComponent(trimestreInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trimestreDateOuvertureInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAnneeAcademic2)
                    .addComponent(sequenceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sequenceDateOuvertureInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sessionInput.getAccessibleContext().setAccessibleName("anneeAcademicId");
        trimestreInput.getAccessibleContext().setAccessibleName(entityTrimestre.toLowerCase() + "Id");
        sequenceInput.getAccessibleContext().setAccessibleName(entitySequence.toLowerCase() + "Id");

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

    private void nomCompletInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomCompletInputActionPerformed
        // TODO add your handling code here:
        if(nomCompletInput.getText().isEmpty()) reset(sessionInput, sessionDateOuvertureInput, trimestreInput, trimestreDateOuvertureInput, sequenceInput, sequenceDateOuvertureInput);
        else eventParametrageAnneeAcademic();
    }//GEN-LAST:event_nomCompletInputActionPerformed

    private void sessionDateOuvertureInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionDateOuvertureInputActionPerformed
        // TODO add your handling code here:
        if(sessionDateOuvertureInput.getText().isEmpty()) reset(sessionInput, sessionDateOuvertureInput, trimestreInput, trimestreDateOuvertureInput, sequenceInput, sequenceDateOuvertureInput);
        else eventParametrageAnneeAcademic();
    }//GEN-LAST:event_sessionDateOuvertureInputActionPerformed

    private void statusInputItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_statusInputItemStateChanged
        // TODO add your handling code here:
        if(statusInput.getSelectedIndex() == 0){
            reset(trimestreInput, trimestreDateOuvertureInput, sequenceInput, sequenceDateOuvertureInput);
            fieldsRequired.remove(trimestreInput);
            fieldsRequired.remove(sequenceInput);
        }else{
            fieldsRequired.add(trimestreInput);
            fieldsRequired.add(sequenceInput);
        }
    }//GEN-LAST:event_statusInputItemStateChanged

    private void trimestreDateOuvertureInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trimestreDateOuvertureInputActionPerformed
        // TODO add your handling code here:
        if(trimestreInput.getText().isEmpty()) reset(trimestreInput, trimestreDateOuvertureInput, sequenceInput, sequenceDateOuvertureInput);
        else eventParametrageAnneeAcademic();
    }//GEN-LAST:event_trimestreDateOuvertureInputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField nomAbregeInput;
    private javax.swing.JTextField nomCompletInput;
    private javax.swing.JFormattedTextField sequenceDateOuvertureInput;
    private javax.swing.JTextField sequenceInput;
    private javax.swing.JFormattedTextField sessionDateOuvertureInput;
    private javax.swing.JTextField sessionInput;
    private javax.swing.JComboBox<String> statusInput;
    private javax.swing.JFormattedTextField trimestreDateOuvertureInput;
    private javax.swing.JTextField trimestreInput;
    // End of variables declaration//GEN-END:variables

}
