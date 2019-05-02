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

    private final String entityEtablissement = "Etablissement";
    private final String entityAnneeAcademic = "AnneeAcademic";
    private List<HashMap> listModelsOriginal;
    private EnumVariable tmpEtatAction = MODIF;

    /**
     * Creates new form CategorieForm
     *
     * @param title
     */
    public ParametreAnneeAcademiqueForm(String title) {
        super(title, 400, 300);

        etatAction = MODIF;
        setActionMenu();
        this.showMenuBar();
    }

    public void createForm() {

        this.entity = "ParametreAnneeAcademic";
        initComponents();
        setAllComponents(nomAbregeInput, nomCompletInput, sessionInput);
    }

    public void addActionSupplementaire() {
        eventAnneeAcademic();
    }

    public void makeModelData() {
        if (etatAction != CREATE) {
            super.makeModelData();
        }//etablissementId
        modelFinal.put(entityEtablissement.toLowerCase() + "Id", FormParameters.get(entityEtablissement.toLowerCase() + "Id"));
        modelFinal.put("session", FormParameters.get("session"));
        modelFinal.put("statut", (statusInput.getSelectedIndex() == 0) ? EnumStatus.Business_Statut_NonActif.toString() : EnumStatus.Business_Statut_Actif.toString());
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
    public void addActionComplement() {
        if (etatAction != CREATE) {

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
            arg.put(Model, "session");
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
                    FormParameters.add(Model, listModelsOriginal.get(0));

                    if (listModelsOriginal.get(0).get("statut").equals(EnumStatus.Business_Statut_NonActif.toString())) {
                        statusInput.setSelectedIndex(0);
                    } else {
                        statusInput.setSelectedIndex(1);
                    }

                } else {
                    tmpEtatAction = CREATE;
                }
            } catch (Exception ex) {
            }

        }
    }

    protected void eventEtablissement() {

        String[][] parametresGrid = {
            {"nameAbrege", Dictionnaire.get(EnumLibelles.Business_Libelle_Nom_Abrege)},
            {"fullName",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Nom_Complet)}};

        addAction(nomAbregeInput, entityEtablissement, entityEtablissement.toLowerCase() + "Id", parametresGrid, null, null, nomAbregeInput, nomCompletInput);
    }

    private void eventAnneeAcademic() {

        HashMap[] args = null;

        String[][] parametresGrid = {
            {"session", Dictionnaire.get(EnumLibelles.Business_Libelle_Session)}};

        addAction(sessionInput, entityAnneeAcademic, "session", parametresGrid, null, args, sessionInput);
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

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelCategorie.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Etablissement))); // NOI18N
        panelCategorie.setOpaque(false);

        labelLevel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code, true)); // NOI18N
        labelLevel.setName("usernameLabel"); // NOI18N

        nomAbregeInput.setName("nameAbrege"); // NOI18N
        this.setRef(nomAbregeInput);
        fieldSearch("nameAbrege", nomAbregeInput);
        fieldsRequired.add(nomAbregeInput);

        nomCompletInput.setEditable(false);
        nomCompletInput.setName("fullName"); // NOI18N
        fieldSearch("fullName", nomCompletInput);
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
                .addComponent(nomAbregeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomCompletInput, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
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

        labelAnneeAcademic.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_AnneeAcademique, true)); // NOI18N
        labelAnneeAcademic.setName("usernameLabel"); // NOI18N

        sessionInput.setName("session"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->session->session", sessionInput);
        fieldsRequired.add(sessionInput);

        statusInput.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        statusInput.setModel(setModelDataComboBox(Dictionnaire.get(EnumStatus.Business_Statut_NonActif), Dictionnaire.get(EnumStatus.Business_Statut_Actif))
        );

        javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(panelContentLayout);
        panelContentLayout.setHorizontalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(statusInput, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelContentLayout.createSequentialGroup()
                            .addComponent(labelAnneeAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sessionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addComponent(sessionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(statusInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
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

    private void nomCompletInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomCompletInputActionPerformed
        // TODO add your handling code here:
        addActionComplement();
    }//GEN-LAST:event_nomCompletInputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField nomAbregeInput;
    private javax.swing.JTextField nomCompletInput;
    private javax.swing.JTextField sessionInput;
    private javax.swing.JComboBox<String> statusInput;
    // End of variables declaration//GEN-END:variables

}
