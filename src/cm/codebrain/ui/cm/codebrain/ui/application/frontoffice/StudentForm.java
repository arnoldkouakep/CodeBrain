/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.frontoffice;

import cm.codebrain.ui.application.ModelForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.FormParameters;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import static cm.codebrain.ui.application.enumerations.EnumVariable.CREATE;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Type;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Entity;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Model;
import java.util.HashMap;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;

/**
 *
 * @author KSA-INET
 */
public class StudentForm extends ModelForm {

    private final String entitySections = "Section";
    private final String entityClasse = "Classe";
    private final String entitySalle = "Salle";

    /**
     * Creates new form UserForm
     *
     * @param title
     */
    public StudentForm(String title){
//            , int width, int height, boolean hideActionMenuTitle, boolean confirmExit) {
        super(title, 960, 520, false, true);

        this.showActionBar();
        etatAction = CREATE;
        this.showMenuBar();

//        this.addFormData("userCode", title);
//    addActionSupplementaire(
    }

    public void createForm() {
        this.entity = "Student";

        initComponents();
        
        setAllComponents(bornLocationInput, sectionsInput, sectionsIntituleInput, chooseButton, classeInput, classeIntituleInput, codeInput, birthdayInput, femininRadioButton, imageLabel, lastNameInput, masculinRadioButton, matriculeInput, nameInput, salleInput, salleIntituleInput, sexeButtonGroup);
    }

    protected void addActionSupplementaire() {
        eventSections();
        eventClasse();
        eventSalle();
        
    }

    private void eventSections() {

        HashMap[] args = null;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(sectionsInput, entitySections, entitySections.toLowerCase()+"Id", parametresGrid, null, args, sectionsInput, sectionsIntituleInput);
    }

    private void eventClasse() {

        String filter = "entity.sectionId=:arg0";

        HashMap[] args = new HashMap[1];

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entitySections);
        arg.put(Model, "sectionId");
        arg.put(Value, sectionsInput);

        args[0] = arg;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(classeInput, entityClasse, entityClasse.toLowerCase()+"Id", parametresGrid, filter, args, classeInput, classeIntituleInput);
    }

    private void eventSalle() {

        String filter = "entity.classeId=:arg0";

        HashMap[] args = new HashMap[1];

        HashMap arg;
//        arg.put(Type, Entity);
//        arg.put(Entity, entitySections);
//        arg.put(Model, "sectionsId");
//        arg.put(Value, sectionsInput);

//        args[0] = arg;

        arg = new HashMap();
        arg.put(Type, Entity);
        arg.put(Entity, entityClasse);
        arg.put(Model, "classeId");
        arg.put(Value, classeInput);

        args[0] = arg;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(salleInput, entitySalle, entitySalle.toLowerCase()+"Id", parametresGrid, filter, args, salleInput, salleIntituleInput);
    }

    @Override
    protected void eventActionRef() {
        
        String[][] parametresGrid = {
            {"matricule", Dictionnaire.get(EnumLibelles.Business_Libelle_Matricule)},
            {"firstName",
                Dictionnaire.get(EnumLibelles.Business_Libelle_nom)},
            {"lastName",
                Dictionnaire.get(EnumLibelles.Business_Libelle_prenom)}};

        addAction(codeInput, entity, entity.toLowerCase()+"Id", parametresGrid, null, null, codeInput);
    }

    
    

    @Override
    public void makeModelData() {

        super.makeModelData();
//        modelFinal.put(entitySections.toLowerCase()+"Id", FormParameters.get(entitySections));
//        modelFinal.put(entityClasse.toLowerCase()+"Id", FormParameters.get(entityClasse));
        if(masculinRadioButton.isSelected())modelFinal.put("sexe", masculinRadioButton.getActionCommand());
        else if(femininRadioButton.isSelected())modelFinal.put("sexe", femininRadioButton.getActionCommand());
        
        modelFinal.put(entitySalle.toLowerCase()+"Id", FormParameters.get(entitySalle.toLowerCase()+"Id"));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sexeButtonGroup = new javax.swing.ButtonGroup();
        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        javax.swing.JPanel panelContent = new javax.swing.JPanel();
        javax.swing.JPanel panelIdentifiant = new javax.swing.JPanel();
        javax.swing.JLabel labelCode = new javax.swing.JLabel();
        javax.swing.JLabel labelMatricule = new javax.swing.JLabel();
        codeInput = new javax.swing.JTextField();
        matriculeInput = new javax.swing.JTextField();
        nameInput = new javax.swing.JTextField();
        lastNameInput = new javax.swing.JTextField();
        javax.swing.JLabel labelfirstName = new javax.swing.JLabel();
        javax.swing.JLabel labelLastName = new javax.swing.JLabel();
        javax.swing.JLabel labelbornLocation = new javax.swing.JLabel();
        javax.swing.JLabel labelMatricule4 = new javax.swing.JLabel();
        bornLocationInput = new javax.swing.JTextField();
        javax.swing.JLabel labelSexe = new javax.swing.JLabel();
        femininRadioButton = new javax.swing.JRadioButton();
        masculinRadioButton = new javax.swing.JRadioButton();
        birthdayInput = new javax.swing.JFormattedTextField();
        javax.swing.JPanel panelSections = new javax.swing.JPanel();
        javax.swing.JLabel labelSections = new javax.swing.JLabel();
        sectionsInput = new javax.swing.JTextField();
        sectionsIntituleInput = new javax.swing.JTextField();
        javax.swing.JPanel panelProfil = new javax.swing.JPanel();
        chooseButton = new javax.swing.JButton();
        javax.swing.JPanel panelImage = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        javax.swing.JLabel labelImage = new javax.swing.JLabel();
        javax.swing.JPanel panelSalle = new javax.swing.JPanel();
        javax.swing.JLabel labelSalle = new javax.swing.JLabel();
        salleInput = new javax.swing.JTextField();
        salleIntituleInput = new javax.swing.JTextField();
        javax.swing.JPanel panelClasse = new javax.swing.JPanel();
        javax.swing.JLabel labelClasse = new javax.swing.JLabel();
        classeInput = new javax.swing.JTextField();
        classeIntituleInput = new javax.swing.JTextField();

        setRadiBottun(sexeButtonGroup, masculinRadioButton, femininRadioButton);
        fieldSearch("sexe", sexeButtonGroup);
        this.addFormData("sexe", sexeButtonGroup);

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelIdentifiant.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Identifiant))); // NOI18N
        panelIdentifiant.setOpaque(false);

        labelCode.setLabelFor(codeInput);
        labelCode.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code, true)); // NOI18N
        labelCode.setName("usernameLabel"); // NOI18N

        labelMatricule.setLabelFor(matriculeInput);
        labelMatricule.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Matricule, true)); // NOI18N
        labelMatricule.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        labelMatricule.setName("usernameLabel"); // NOI18N

        codeInput.setName("codeInput"); // NOI18N
        this.addFormData("code", codeInput);
        this.setRef(codeInput);
        fieldSearch("code", codeInput);
        fieldsRequired.add(codeInput);

        matriculeInput.setName("matriculeInput"); // NOI18N
        this.addFormData("matricule", matriculeInput);
        fieldSearch("matricule", matriculeInput);
        fieldsRequired.add(matriculeInput);

        nameInput.setName("matriculeInput"); // NOI18N
        this.addFormData("firstName", nameInput);
        fieldSearch("firstName", nameInput);
        fieldsRequired.add(nameInput);

        lastNameInput.setName("matriculeInput"); // NOI18N
        this.addFormData("lastName", lastNameInput);
        fieldSearch("lastName", lastNameInput);

        labelfirstName.setLabelFor(nameInput);
        labelfirstName.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_nom, true)); // NOI18N
        labelfirstName.setName("usernameLabel"); // NOI18N

        labelLastName.setLabelFor(lastNameInput);
        labelLastName.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_prenom)); // NOI18N
        labelLastName.setName("usernameLabel"); // NOI18N

        labelbornLocation.setLabelFor(bornLocationInput);
        labelbornLocation.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_BornLocation, true)); // NOI18N
        labelbornLocation.setName("usernameLabel"); // NOI18N

        labelMatricule4.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_BirthDay, true)); // NOI18N
        labelMatricule4.setName("usernameLabel"); // NOI18N

        bornLocationInput.setName("matriculeInput"); // NOI18N
        bornLocationInput.setPreferredSize(new java.awt.Dimension(199, 28));
        this.addFormData("bornLocation", bornLocationInput);
        fieldSearch("bornLocation", bornLocationInput);
        fieldsRequired.add(bornLocationInput);

        labelSexe.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Sexe, true)); // NOI18N
        labelSexe.setName("usernameLabel"); // NOI18N

        sexeButtonGroup.add(femininRadioButton);
        femininRadioButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Feminin)); // NOI18N
        femininRadioButton.setActionCommand(EnumLibelles.Business_Libelle_Feminin.toString()); // NOI18N

        sexeButtonGroup.add(masculinRadioButton);
        masculinRadioButton.setSelected(true);
        masculinRadioButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Masculin)); // NOI18N
        masculinRadioButton.setActionCommand(EnumLibelles.Business_Libelle_Masculin.toString()); // NOI18N

        birthdayInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        birthdayInput.setName("birthday"); // NOI18N
        this.addFormData("birthday", birthdayInput);
        fieldSearch("birthday", birthdayInput);
        fieldsRequired.add(birthdayInput);

        javax.swing.GroupLayout panelIdentifiantLayout = new javax.swing.GroupLayout(panelIdentifiant);
        panelIdentifiant.setLayout(panelIdentifiantLayout);
        panelIdentifiantLayout.setHorizontalGroup(
            panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIdentifiantLayout.createSequentialGroup()
                        .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(labelMatricule4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(labelSexe, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelIdentifiantLayout.createSequentialGroup()
                                        .addComponent(masculinRadioButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(femininRadioButton))
                                    .addGroup(panelIdentifiantLayout.createSequentialGroup()
                                        .addComponent(birthdayInput, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelbornLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bornLocationInput, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
                                .addContainerGap(15, Short.MAX_VALUE))
                            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                                .addComponent(codeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelMatricule, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(matriculeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panelIdentifiantLayout.createSequentialGroup()
                        .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelfirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                                .addComponent(labelLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                                .addComponent(lastNameInput)
                                .addGap(12, 12, 12))))))
        );
        panelIdentifiantLayout.setVerticalGroup(
            panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIdentifiantLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelIdentifiantLayout.createSequentialGroup()
                        .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(codeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMatricule, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(matriculeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCode, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelfirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelbornLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bornLocationInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelMatricule4)
                        .addComponent(birthdayInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIdentifiantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSexe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(femininRadioButton)
                    .addComponent(masculinRadioButton))
                .addGap(10, 10, 10))
        );

        panelSections.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Sections))); // NOI18N
        panelSections.setOpaque(false);
        panelSections.setPreferredSize(new java.awt.Dimension(399, 66));

        labelSections.setLabelFor(sectionsInput);
        labelSections.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_level)); // NOI18N
        labelSections.setName("usernameLabel"); // NOI18N

        sectionsInput.setName("code"); // NOI18N
        fieldSearch("Student->salleId->code->Salle->classeId->code->Classe->sectionId->code", sectionsInput);

        sectionsIntituleInput.setEditable(false);
        sectionsIntituleInput.setName("intitule"); // NOI18N
        fieldSearch("Student->salleId->code->Salle->classeId->code->Classe->sectionId->intitule", sectionsIntituleInput);

        javax.swing.GroupLayout panelSectionsLayout = new javax.swing.GroupLayout(panelSections);
        panelSections.setLayout(panelSectionsLayout);
        panelSectionsLayout.setHorizontalGroup(
            panelSectionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSectionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSections, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sectionsInput, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sectionsIntituleInput, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelSectionsLayout.setVerticalGroup(
            panelSectionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSectionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSectionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSections, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectionsInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectionsIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelProfil.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Profil))); // NOI18N
        panelProfil.setVisible(false);
        panelProfil.setLayout(new java.awt.BorderLayout());

        chooseButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Choose)); // NOI18N
        panelProfil.add(chooseButton, java.awt.BorderLayout.SOUTH);

        panelImage.setLayout(new javax.swing.OverlayLayout(panelImage));

        imageLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        imageLabel.setMaximumSize(new java.awt.Dimension(140, 140));
        imageLabel.setMinimumSize(new java.awt.Dimension(140, 140));
        imageLabel.setPreferredSize(new java.awt.Dimension(135, 135));
        panelImage.add(imageLabel);

        labelImage.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        labelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelImage.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Image)); // NOI18N
        labelImage.setMaximumSize(new java.awt.Dimension(141, 166));
        labelImage.setPreferredSize(new java.awt.Dimension(135, 135));
        panelImage.add(labelImage);

        panelProfil.add(panelImage, java.awt.BorderLayout.CENTER);

        panelSalle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Salle))); // NOI18N
        panelSalle.setOpaque(false);

        labelSalle.setLabelFor(salleInput);
        labelSalle.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_level, true)); // NOI18N
        labelSalle.setName("usernameLabel"); // NOI18N

        salleInput.setName("code"); // NOI18N
        this.addFormData("salleId", salleInput);
        fieldSearch("Student->salleId->code", salleInput);
        fieldsRequired.add(salleInput);

        salleIntituleInput.setEditable(false);
        salleIntituleInput.setName("intitule"); // NOI18N
        fieldSearch("Student->salleId->intitule", salleIntituleInput);

        javax.swing.GroupLayout panelSalleLayout = new javax.swing.GroupLayout(panelSalle);
        panelSalle.setLayout(panelSalleLayout);
        panelSalleLayout.setHorizontalGroup(
            panelSalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salleInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salleIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        panelClasse.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Classe))); // NOI18N
        panelClasse.setOpaque(false);

        labelClasse.setLabelFor(classeInput);
        labelClasse.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_level)); // NOI18N
        labelClasse.setName("usernameLabel"); // NOI18N

        classeInput.setName("code"); // NOI18N
        fieldSearch("Student->salleId->code->Salle->classeId->code", classeInput);

        classeIntituleInput.setEditable(false);
        classeIntituleInput.setName("intitule"); // NOI18N
        fieldSearch("Student->salleId->code->Salle->classeId->intitule", classeIntituleInput);

        javax.swing.GroupLayout panelClasseLayout = new javax.swing.GroupLayout(panelClasse);
        panelClasse.setLayout(panelClasseLayout);
        panelClasseLayout.setHorizontalGroup(
            panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(classeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classeIntituleInput, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
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
                .addContainerGap()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSalle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContentLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContentLayout.createSequentialGroup()
                                .addComponent(panelSections, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelContentLayout.createSequentialGroup()
                                .addComponent(panelIdentifiant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        panelContentLayout.setVerticalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelIdentifiant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelSections, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        panelProfil.getAccessibleContext().setAccessibleName("Classe");

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
    private javax.swing.JFormattedTextField birthdayInput;
    private javax.swing.JTextField bornLocationInput;
    private javax.swing.JButton chooseButton;
    private javax.swing.JTextField classeInput;
    private javax.swing.JTextField classeIntituleInput;
    private javax.swing.JTextField codeInput;
    private javax.swing.JRadioButton femininRadioButton;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JTextField lastNameInput;
    private javax.swing.JRadioButton masculinRadioButton;
    private javax.swing.JTextField matriculeInput;
    private javax.swing.JTextField nameInput;
    private javax.swing.JTextField salleInput;
    private javax.swing.JTextField salleIntituleInput;
    private javax.swing.JTextField sectionsInput;
    private javax.swing.JTextField sectionsIntituleInput;
    private javax.swing.ButtonGroup sexeButtonGroup;
    // End of variables declaration//GEN-END:variables

}
