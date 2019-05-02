/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.backoffice;

import cm.codebrain.ui.application.MessageForm;
import cm.codebrain.ui.application.ModelForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.FormParameters;
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import static cm.codebrain.ui.application.enumerations.EnumVariable.CREATE;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Entity;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Field;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Model;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Type;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
public class TrimestreAndSequencesForm extends ModelForm {

    private final String entityAnneeAcademic = "AnneeAcademic";
    private final String entitySequence = "Sequence";
    private TableRowSorter<TableModel> sorter;

    private List<HashMap> listModelsOriginal;
    private List<HashMap> listModelsAdd;
    private List<HashMap> listModelsSub;
    private HashMap<Object, Object> modelSequence;
    private boolean gridIsEmpty;
    

    /**
     * Creates new form UserForm
     *
     * @param title
     */
    public TrimestreAndSequencesForm(String title) {
        super(title, 730, 680, false, true);

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

        this.entity = "Trimestre";

        listModelsOriginal = new ArrayList<>();
        listModelsAdd = new ArrayList<>();
        listModelsSub = new ArrayList<>();

        initComponents();

        sorter = new TableRowSorter<>(grid.getModel());

        setAllComponents(sessionInput, sessionDebutInput, sessionFinInput, codeTrimestreInput, trimestreDebutInput, trimestreFinInput, codeSequenceInput, sequenceDebutInput, sequenceFinInput, grid);

    }

    public void addActionSupplementaire() {
        eventAnneeAcademic();
        
        
        grid.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int rowNumber = grid.getSelectedRow();
            HashMap model = null;
            try {
                model = listModelsOriginal.get(rowNumber);
            } catch (Exception ex) {
            }
                if(model == null){
                    model = listModelsAdd.get(rowNumber-listModelsOriginal.size());
                    getModelData(model, codeSequenceInput, sequenceDebutInput, sequenceFinInput);
                    modifyButton.setEnabled(true);
                    addButton.setEnabled(false);
                }else{
                    modifyButton.setEnabled(false);
                    addButton.setEnabled(true);
                    reset(codeSequenceInput, sequenceDebutInput, sequenceFinInput);
                }
        });
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

        Object[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"dateOuverture",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule), Date.class},
            {"dateFermeture",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule), Date.class}};

        addAction(codeTrimestreInput, entity, entity.toLowerCase() + "Id", parametresGrid, filter, args, codeTrimestreInput, codeSequenceInput, trimestreDebutInput, trimestreFinInput);
    }

    private void eventAnneeAcademic() {

        HashMap[] args = null;

        Object[][] parametresGrid1 = {
            {"session", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"dateOuverture",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule), Date.class},
            {"dateFermeture",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule), Date.class}};

        addAction(sessionInput, entityAnneeAcademic, "anneeAcademicId", parametresGrid1, null, args, sessionInput, sessionDebutInput, sessionFinInput);
    }

    protected void eventActionRef() {
        eventTrimestre();
    }

    @Override
    public void makeModelData() {
        super.makeModelData(); //To change body of generated methods, choose Tools | Templates.

        modelFinal.put("anneeAcademicId", FormParameters.get("anneeAcademicId"));
        
        listModelsAdd.forEach((model)->{
            model.put("anneeAcademicId", FormParameters.get("anneeAcademicId"));
        });

        HashMap modelAdd = new HashMap();
        modelAdd.put(Entity, entitySequence);
        modelAdd.put(Field, entity.toLowerCase() + "Id");
        modelAdd.put(Model, listModelsAdd);

        HashMap modelSub = new HashMap();
        modelSub.put(Entity, entitySequence);
        modelSub.put(Field, entity.toLowerCase() + "Id");
        modelSub.put(Model, listModelsSub);

        setActionModel(Arrays.asList(modelAdd), Arrays.asList(modelSub));
    }

    public void addActionComplement() {
        if (gridIsEmpty) {
            if (etatAction != CREATE) {

                HashMap[] args = new HashMap[1];

                String filter = "entity.trimestreId=:arg0";

                HashMap arg = new HashMap();

                arg.put(Type, Entity);
                arg.put(Entity, entity);
                arg.put(Model, "trimestreId");
                arg.put(Value, codeTrimestreInput);

                args[0] = arg;

                try {
                    listModelsOriginal = getListModelForSelect(null, entitySequence, null, filter, args);
                } catch (Exception ex) {
                    Logger.getLogger(GroupSalleForm.class.getName()).log(Level.SEVERE, null, ex);
                }

                listModelsOriginal.stream().map((model) -> {
                    Object[] newRow = {model.get("code"), model.get("dateOuverture"), model.get("dateFermeture")};
                    return newRow;
                }).forEachOrdered((newRow) -> {
                    ((DefaultTableModel) ((JTable) grid).getModel()).addRow(newRow);
                });
                reset(codeSequenceInput, sequenceDebutInput, sequenceFinInput);
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
        javax.swing.JPanel panelAnneeAcademic = new javax.swing.JPanel();
        javax.swing.JLabel labelSession = new javax.swing.JLabel();
        javax.swing.JLabel labelSessionDebut = new javax.swing.JLabel();
        sessionInput = new javax.swing.JTextField();
        javax.swing.JLabel labelSessionFin = new javax.swing.JLabel();
        sessionDebutInput = new javax.swing.JFormattedTextField();
        sessionFinInput = new javax.swing.JFormattedTextField();
        javax.swing.JPanel panelClasse = new javax.swing.JPanel();
        javax.swing.JLabel labelTrimestreDebut = new javax.swing.JLabel();
        trimestreDebutInput = new javax.swing.JFormattedTextField();
        javax.swing.JLabel labelTrimestreFin = new javax.swing.JLabel();
        trimestreFinInput = new javax.swing.JFormattedTextField();
        javax.swing.JLabel labelTrimestreCode = new javax.swing.JLabel();
        codeTrimestreInput = new javax.swing.JTextField();
        javax.swing.JPanel jPanelButtons = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        modifyButton = new javax.swing.JButton();
        subButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid = new javax.swing.JTable();
        javax.swing.JPanel panelSequence = new javax.swing.JPanel();
        javax.swing.JLabel labelSequenceDebut = new javax.swing.JLabel();
        sequenceDebutInput = new javax.swing.JFormattedTextField();
        javax.swing.JLabel labelSequenceFin = new javax.swing.JLabel();
        sequenceFinInput = new javax.swing.JFormattedTextField();
        javax.swing.JLabel labelSequenceCode = new javax.swing.JLabel();
        codeSequenceInput = new javax.swing.JTextField();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelAnneeAcademic.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_AnneeAcademique))); // NOI18N
        panelAnneeAcademic.setOpaque(false);

        labelSession.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Session)); // NOI18N
        labelSession.setName("usernameLabel"); // NOI18N

        labelSessionDebut.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_DateDebut)); // NOI18N
        labelSessionDebut.setName("usernameLabel"); // NOI18N

        sessionInput.setName("session"); // NOI18N
        fieldSearch.put("Trimestre->anneeAcademicId->code", codeTrimestreInput);

        labelSessionFin.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_DateFin)); // NOI18N
        labelSessionFin.setName("usernameLabel"); // NOI18N

        sessionDebutInput.setEditable(false);
        sessionDebutInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        sessionDebutInput.setName("dateOuverture"); // NOI18N
        fieldSearch.put("Trimestre->anneeAcademicId->dateOuverture", trimestreDebutInput);

        sessionFinInput.setEditable(false);
        sessionFinInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        sessionFinInput.setName("dateFermeture"); // NOI18N
        fieldSearch.put("Trimestre->anneeAcademicId->dateFermeture", trimestreFinInput);

        javax.swing.GroupLayout panelAnneeAcademicLayout = new javax.swing.GroupLayout(panelAnneeAcademic);
        panelAnneeAcademic.setLayout(panelAnneeAcademicLayout);
        panelAnneeAcademicLayout.setHorizontalGroup(
            panelAnneeAcademicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnneeAcademicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAnneeAcademicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelSessionDebut, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(labelSession, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAnneeAcademicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAnneeAcademicLayout.createSequentialGroup()
                        .addComponent(sessionDebutInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(labelSessionFin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sessionFinInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAnneeAcademicLayout.createSequentialGroup()
                        .addComponent(sessionInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelAnneeAcademicLayout.setVerticalGroup(
            panelAnneeAcademicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnneeAcademicLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAnneeAcademicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSession, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sessionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAnneeAcademicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSessionDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSessionFin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sessionDebutInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sessionFinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelClasse.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Trimestre))); // NOI18N
        panelClasse.setOpaque(false);

        labelTrimestreDebut.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_DateDebut)); // NOI18N
        labelTrimestreDebut.setName("usernameLabel"); // NOI18N

        trimestreDebutInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        trimestreDebutInput.setName("dateOuverture"); // NOI18N
        this.addFormData("dateOuverture", trimestreDebutInput);
        fieldSearch.put("dateOuverture", trimestreDebutInput);
        fieldsRequired.add(trimestreDebutInput);

        labelTrimestreFin.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_DateFin)); // NOI18N
        labelTrimestreFin.setName("usernameLabel"); // NOI18N

        trimestreFinInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        trimestreFinInput.setName("dateFermeture"); // NOI18N
        this.addFormData("dateFermeture", trimestreFinInput);
        fieldSearch.put("dateFermeture", trimestreFinInput);
        fieldsRequired.add(trimestreFinInput);

        labelTrimestreCode.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelTrimestreCode.setName("usernameLabel"); // NOI18N

        codeTrimestreInput.setName("code"); // NOI18N
        this.addFormData("code", codeTrimestreInput);
        this.setRef(codeTrimestreInput);
        fieldSearch.put("code", codeTrimestreInput);
        fieldsRequired.add(codeTrimestreInput);

        javax.swing.GroupLayout panelClasseLayout = new javax.swing.GroupLayout(panelClasse);
        panelClasse.setLayout(panelClasseLayout);
        panelClasseLayout.setHorizontalGroup(
            panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClasseLayout.createSequentialGroup()
                .addGroup(panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelClasseLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelTrimestreCode, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addComponent(labelTrimestreDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClasseLayout.createSequentialGroup()
                        .addComponent(trimestreDebutInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(labelTrimestreFin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(trimestreFinInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelClasseLayout.createSequentialGroup()
                        .addComponent(codeTrimestreInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelClasseLayout.setVerticalGroup(
            panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClasseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTrimestreCode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeTrimestreInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTrimestreDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTrimestreFin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trimestreDebutInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trimestreFinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        grid.setModel(setModelDataTable(
            new Object[] {Dictionnaire.get(EnumLibelles.Business_Libelle_code), String.class, false},
            new Object[] {Dictionnaire.get(EnumLibelles.Business_Libelle_DateDebut), Date.class, true},
            new Object[] {Dictionnaire.get(EnumLibelles.Business_Libelle_DateFin), Date.class, true}
        ));
        grid.setShowHorizontalLines(true);
        grid.setShowVerticalLines(true);
        grid.getTableHeader().setReorderingAllowed(false);
        grid.setUpdateSelectionOnSort(false);
        grid.putClientProperty("Quaqua.Table.style", "striped");
        jScrollPane1.setViewportView(grid);

        panelSequence.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Sequence))); // NOI18N
        panelSequence.setOpaque(false);

        labelSequenceDebut.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_DateDebut)); // NOI18N
        labelSequenceDebut.setName("usernameLabel"); // NOI18N

        sequenceDebutInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        sequenceDebutInput.setName("dateOuverture"); // NOI18N
        fieldSearch.put("Trimestre->sequenceId->dateOuverture", trimestreDebutInput);

        labelSequenceFin.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_DateFin)); // NOI18N
        labelSequenceFin.setName("usernameLabel"); // NOI18N

        sequenceFinInput.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        sequenceFinInput.setName("dateFermeture"); // NOI18N
        fieldSearch.put("Trimestre->sequenceId->dateFermeture", trimestreFinInput);

        labelSequenceCode.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelSequenceCode.setName("usernameLabel"); // NOI18N

        codeSequenceInput.setName("code"); // NOI18N
        fieldSearch.put("Trimestre->sequenceId->code", codeTrimestreInput);

        javax.swing.GroupLayout panelSequenceLayout = new javax.swing.GroupLayout(panelSequence);
        panelSequence.setLayout(panelSequenceLayout);
        panelSequenceLayout.setHorizontalGroup(
            panelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSequenceLayout.createSequentialGroup()
                .addGroup(panelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelSequenceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelSequenceCode, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addComponent(labelSequenceDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSequenceLayout.createSequentialGroup()
                        .addComponent(sequenceDebutInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(labelSequenceFin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sequenceFinInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSequenceLayout.createSequentialGroup()
                        .addComponent(codeSequenceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelSequenceLayout.setVerticalGroup(
            panelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSequenceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSequenceCode, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeSequenceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSequenceDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSequenceFin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sequenceDebutInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sequenceFinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(panelContentLayout);
        panelContentLayout.setHorizontalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAnneeAcademic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelClasse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(panelContentLayout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelSequence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelContentLayout.setVerticalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAnneeAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
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

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        
        if(!codeSequenceInput.getText().isEmpty() && sequenceDebutInput.getValue() != null && sequenceFinInput.getValue() != null){
            modelSequence = new HashMap<>();
            modelSequence.put("code", codeSequenceInput.getText());
            modelSequence.put("dateOuverture", sequenceDebutInput.getValue());
            modelSequence.put("dateFermeture", sequenceFinInput.getValue());
        
            if (findModel(modelSequence, "code", listModelsOriginal) == null) {
                Object[] newRow = {codeSequenceInput.getText(), sequenceDebutInput.getValue(), sequenceFinInput.getValue()};

                setTableModel(codeSequenceInput, sequenceDebutInput, sequenceFinInput);

                ((DefaultTableModel) ((JTable) grid).getModel()).addRow(newRow);

                listModelsAdd.add(modelSequence);

                reset(codeSequenceInput, sequenceDebutInput, sequenceFinInput);

                modifyButton.setEnabled(false);
                addButton.setEnabled(true);
                
                codeSequenceInput.requestFocus();
            }
        }else{
            MessageForm.showsError(Dictionnaire.get(EnumError.Business_Error_find_required), "Message", false, null);
        }
        
    }//GEN-LAST:event_addButtonActionPerformed

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        // TODO add your handling code here:
        int rowIndex = grid.convertRowIndexToModel(grid.getSelectedRow());

//        modelSequence = new HashMap<>();
        modelSequence = (HashMap) FormParameters.get(entitySequence.toLowerCase() + "Id");
        
        if (findModel(modelSequence, "code", listModelsOriginal) == null) {
            
            listModelsAdd.set(rowIndex, modelSequence);
            
            Object[] newRow = {codeSequenceInput.getText(), sequenceDebutInput.getValue(), sequenceFinInput.getValue()};

            setTableModel(codeSequenceInput, sequenceDebutInput, sequenceFinInput);

            try {
                ((DefaultTableModel) ((JTable) grid).getModel()).setValueAt(newRow[0], rowIndex, 0);
                ((DefaultTableModel) ((JTable) grid).getModel()).setValueAt(newRow[1], rowIndex, 1);
                ((DefaultTableModel) ((JTable) grid).getModel()).setValueAt(newRow[2], rowIndex, 2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            grid.repaint();
        }
        
        reset(codeSequenceInput, sequenceDebutInput, sequenceFinInput);

        modifyButton.setEnabled(false);
        addButton.setEnabled(true);
        
        codeSequenceInput.requestFocus();
    }//GEN-LAST:event_modifyButtonActionPerformed

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
            modelSequence = listModelsOriginal.get(rowIndex);
            if (modelSequence != null) {
                listModelsSub.add(modelSequence);
            }else{
                listModelsAdd.remove(rowIndex);
            }
        }else{
            listModelsAdd.remove(rowIndex);
        }
        
        reset(codeSequenceInput, sequenceDebutInput, sequenceFinInput);
        
        codeSequenceInput.requestFocus();
    }//GEN-LAST:event_subButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField codeSequenceInput;
    private javax.swing.JTextField codeTrimestreInput;
    private javax.swing.JTable grid;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modifyButton;
    private javax.swing.JFormattedTextField sequenceDebutInput;
    private javax.swing.JFormattedTextField sequenceFinInput;
    private javax.swing.JFormattedTextField sessionDebutInput;
    private javax.swing.JFormattedTextField sessionFinInput;
    private javax.swing.JTextField sessionInput;
    private javax.swing.JButton subButton;
    private javax.swing.JFormattedTextField trimestreDebutInput;
    private javax.swing.JFormattedTextField trimestreFinInput;
    // End of variables declaration//GEN-END:variables

}
