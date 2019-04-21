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
import static cm.codebrain.ui.application.enumerations.EnumVariable.CREATE;
import java.util.HashMap;

/**
 *
 * @author KSA-INET
 */
public class ProfessionForm extends ModelForm {
    
    /**
     * Creates new form CategorieForm
     *
     * @param title
     */
    public ProfessionForm(String title) {
        super(title, 520, 300);

        this.showActionBar();
        this.showMenuBar();
    }

    public void createForm() {
        this.entity = "Profession";

        initComponents();
        setAllComponents(codeInput, intituleInput);
    }

    public void addActionSupplementaire() {
    }

    protected void eventActionRef() {

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(codeInput, entity, entity.toLowerCase()+"Id", parametresGrid, null, null, codeInput);
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
        javax.swing.JLabel labelPrenom = new javax.swing.JLabel();
        codeInput = new javax.swing.JTextField();
        intituleInput = new javax.swing.JTextField();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelIdentifiant.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Identifiant))); // NOI18N
        panelIdentifiant.setOpaque(false);

        labelCode.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code, true)); // NOI18N
        labelCode.setName("usernameLabel"); // NOI18N

        labelPrenom.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule, true)); // NOI18N
        labelPrenom.setName("usernameLabel"); // NOI18N

        codeInput.setName("code"); // NOI18N
        this.addFormData("code", codeInput);
        this.setRef(codeInput);
        fieldSearch("code", codeInput);
        fieldsRequired.add(codeInput);

        intituleInput.setName("intitule"); // NOI18N
        this.addFormData("intitule", intituleInput);
        fieldSearch("intitule", intituleInput);
        fieldsRequired.add(intituleInput);

        javax.swing.GroupLayout panelIdentifiantLayout = new javax.swing.GroupLayout(panelIdentifiant);
        panelIdentifiant.setLayout(panelIdentifiantLayout);
        panelIdentifiantLayout.setHorizontalGroup(
            panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelPrenom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(labelCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codeInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
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
                    .addComponent(labelPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(panelContentLayout);
        panelContentLayout.setHorizontalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelIdentifiant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelContentLayout.setVerticalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelIdentifiant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codeInput;
    private javax.swing.JTextField intituleInput;
    // End of variables declaration//GEN-END:variables

}
