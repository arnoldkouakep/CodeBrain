/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.frontoffice;

import cm.codebrain.ui.application.MessageForm;
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
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
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
public class NoteEtudiantForm extends ModelForm {

    private final String entityParamAnneeAcademic = "ParametreAnneeAcademic";
    private final String entitySalle = "Salle";
    private final String entityClasse = "Classe";
    private final String entityGroupe = "Groupe";
    private final String entitySection = "Section";
    private final String entityStudent = "Student";
    private final String entityCours = "Cours";
    private TableRowSorter<TableModel> sorter;

    private List<HashMap> listModelsOriginal;
    private List<HashMap> listModelsAdd;
    private List<HashMap> listModelsSub;
    private List<HashMap> listCours;
    private HashMap<Object, Object> modelSalle;
    private boolean gridIsEmpty;

    /**
     * Creates new form UserForm
     *
     * @param title
     */
    public NoteEtudiantForm(String title) {
        super(title, 906, 750, false, true);

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

        setAllComponents(anneeAcademicInput, trimestreInput, sequenceInput, sectionCodeInput, sectionLibelleInput, classeInput, classeIntituleInput, groupCodeInput, groupLibelleInput, salleInput, salleIntituleInput, studentInput, studentFirstnameInput, studentLastNameInput, grid);
    }

    public void addActionSupplementaire() {
        eventParamAnneeAcademic();
        eventSections();
        eventClasse();
        eventGroupe();
        eventStudent();
        eventSalle();
//        loadGrid();

        grid.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            int rowNumber = grid.getSelectedRow();
            HashMap model = null;
            try {
                model = listModelsOriginal.get(rowNumber);
            } catch (Exception ex) {
            }
            if (model == null) {
                model = listModelsAdd.get(rowNumber - listModelsOriginal.size());
//                    getModelData(model, salleInput, salleIntituleInput);
//                    modifyButton.setEnabled(true);
//                    addButton.setEnabled(false);
            } else {
//                    modifyButton.setEnabled(false);
//                    addButton.setEnabled(true);
//                    reset(salleInput, salleIntituleInput);
            }
        });
    }

    private void eventParamAnneeAcademic() {

        HashMap[] args = null;

        String[][] parametresGrid = {
            {"ParametreAnneeAcademic->session->session", Dictionnaire.get(EnumLibelles.Business_Libelle_Session)}};

        addAction(anneeAcademicInput, entityParamAnneeAcademic, "parametreAnneeAcademicId", parametresGrid, null, args, anneeAcademicInput, trimestreInput, sequenceInput);
    }

    private void eventSalle() {
        int nbre = 1;

        String filter = "entity.classeId=:arg0";

        HashMap arg1 = null;

        HashMap arg0 = new HashMap();
        arg0.put(Type, Entity);
        arg0.put(Entity, entityClasse);
        arg0.put(Model, "classeId");
        arg0.put(Value, classeInput);

        if (!groupCodeInput.getText().isEmpty()) {
            nbre++;

            filter += " and entity.groupeId=:arg1";

            arg1 = new HashMap();
            arg1.put(Type, Entity);
            arg1.put(Entity, entityGroupe);
            arg1.put(Model, "groupeId");
            arg1.put(Value, groupCodeInput);

        }

        HashMap[] args = new HashMap[nbre];
        args[0] = arg0;

        if (!groupCodeInput.getText().isEmpty()) {
            args[1] = arg1;
        }

//
//        HashMap[] args = new HashMap[1];
//
//        String filter = "entity.classeId=:arg0";
//
//        HashMap arg = new HashMap();
//
//        arg.put(Type, Entity);
//        arg.put(Entity, entityClasse);
//        arg.put(Model, "classeId");
//        arg.put(Value, classeInput);
//
//        args[0] = arg;
        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(salleInput, entitySalle, entitySalle.toLowerCase() + "Id", parametresGrid, filter, args, salleInput, salleIntituleInput);
    }

    private void eventGroupe() {

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

        addAction(groupCodeInput, entityGroupe, entityGroupe.toLowerCase() + "Id", parametresGrid, filter, args, groupCodeInput, groupLibelleInput);
    }

    private void eventClasse() {

        HashMap[] args = new HashMap[1];

        String filter = "entity.sectionId=:arg0";

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entitySection);
        arg.put(Model, "sectionId");
        arg.put(Value, sectionCodeInput);

        args[0] = arg;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(classeInput, entityClasse, entityClasse.toLowerCase() + "Id", parametresGrid, filter, args, classeInput, classeIntituleInput);
    }

    private void eventSections() {

        HashMap[] args = null;

        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};

        addAction(sectionCodeInput, entitySection, entitySection.toLowerCase() + "Id", parametresGrid, null, args, sectionCodeInput, sectionLibelleInput);
    }

    private void eventStudent() {
        HashMap[] args = new HashMap[1];

        String filter = "entity.salleId=:arg0";

        HashMap arg = new HashMap();

        arg.put(Type, Entity);
        arg.put(Entity, entitySalle);
        arg.put(Model, "salleId");
        arg.put(Value, salleInput);

        args[0] = arg;

        String[][] parametresGrid = {
            {"matricule", Dictionnaire.get(EnumLibelles.Business_Libelle_Matricule)},
            {"firstName",
                Dictionnaire.get(EnumLibelles.Business_Libelle_nom)},
            {"lastName",
                Dictionnaire.get(EnumLibelles.Business_Libelle_prenom)}};

        addAction(studentInput, entityStudent, entityStudent.toLowerCase() + "Id", parametresGrid, filter, args, studentInput, studentFirstnameInput, studentLastNameInput);
    }

    protected void eventActionRef() {
        /*
        String[][] parametresGrid = {
            {"code", Dictionnaire.get(EnumLibelles.Business_Libelle_code)},
            {"intitule",
                Dictionnaire.get(EnumLibelles.Business_Libelle_Intitule)}};
        addAction(codeInput, entity, entity.toLowerCase() + "Id", parametresGrid, null, null, codeInput, salleIntituleInput);
         */
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

//    public void addActionComplement() {
//    }
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

//    private void loadSalles() {
//        Loading.show(null, new Executable() {
//            
//            @Override
//            public void execute() throws Exception{
//
//                HashMap[] args = new HashMap[3];
//
//                String filter = "entity.classeId=:arg0 and (:arg2='' or (:arg2!='' and entity.groupeId=:arg1))";
//
//                HashMap arg = new HashMap();
//                arg.put(Type, Entity);
//                arg.put(Entity, entityClasse);
//                arg.put(Model, "classeId");
//                arg.put(Value, classeInput);
//                args[0] = arg;
//
//                arg = new HashMap();
//                arg.put(Type, Entity);
//                arg.put(Entity, entityGroupe);
//                arg.put(Model, "groupeId");
//                arg.put(Value, groupCodeInput);
//                args[1] = arg;
//
//                arg = new HashMap();
//                arg.put(Type, "String");
//                arg.put(Value, groupCodeInput.getText());
//                args[2] = arg;
//
//                listSalles = null;
////                try {
//                    listSalles = getListModelForSelect(null, entitySalle, null, filter, args);
////                    new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" })
////                } catch (Exception ex) {
////                    Logger.getLogger(GroupSalleForm.class.getName()).log(Level.SEVERE, null, ex);
////                }
//
//            }
//
//            @Override
//            public List<HashMap> success(){
////                listSalles.stream().map((model) -> {
////                    Object[] newRow = {false, model.get("code"), model.get("libelleFr"), model.get("libelleEn")};
////                    return newRow;
////                }).forEachOrdered((newRow) -> {
////                    ((DefaultTableModel) ((JTable) listSalles).getModel()).addRow(newRow);
////                });
//                SalleInput.setModel(setModelDataComboBox(listSalles, "code"));
//                return listSalles;
//            }
//
//            @Override
//            public void error(Exception ex) {
//                System.out.println(ex.getMessage());
////                MessageForm.showsError(ex.getMessage(), "Message", false, null);
////                doClose(RET_CANCEL);
////                dispose();
//            }
//        });
//    }
    private void loadGrid() {
        Loading.show(null, new Executable() {
            @Override
            public void execute() throws Exception{

                HashMap[] args = null;

                listCours = null;
//                try {
                    listCours = getListModelForSelect(null, entityCours, null, null, args);
//                } catch (Exception ex) {
//                    Logger.getLogger(GroupSalleForm.class.getName()).log(Level.SEVERE, null, ex);
//                }

            }

            @Override
            public List<HashMap> success(){
//                setModelDataTable(
//    Dictionnaire.get(EnumLibelles.Business_Libelle_code),
//    Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleFr),
//    Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleEn),
//    Dictionnaire.get(EnumLibelles.Business_Libelle_Note),
//    Dictionnaire.get(EnumLibelles.Business_Libelle_Credits),
//    Dictionnaire.get(EnumLibelles.Business_Libelle_Total)
//    )
                listCours.stream().map((model) -> {
                    Object[] newRow = {model.get("code"), model.get("libelleFr"), model.get("libelleEn")};
                    return newRow;
                }).forEachOrdered((newRow) -> {
                    ((DefaultTableModel) ((JTable) grid).getModel()).addRow(newRow);
                });

                return listCours;
            }

            @Override
            public void error(Exception ex) {
                MessageForm.showsError(ex.getMessage(), "Message", false, null);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionButtonGroup = new javax.swing.ButtonGroup();
        javax.swing.JPanel mainPanel = new javax.swing.JPanel();
        javax.swing.JPanel panelContent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid = new javax.swing.JTable();
        javax.swing.JPanel panelClasse = new javax.swing.JPanel();
        javax.swing.JLabel labelClasse = new javax.swing.JLabel();
        classeInput = new javax.swing.JTextField();
        classeIntituleInput = new javax.swing.JTextField();
        javax.swing.JLabel labelAneeAcademic = new javax.swing.JLabel();
        anneeAcademicInput = new javax.swing.JTextField();
        javax.swing.JLabel labelTrimestre = new javax.swing.JLabel();
        trimestreInput = new javax.swing.JTextField();
        sequenceInput = new javax.swing.JTextField();
        javax.swing.JLabel labelSequence = new javax.swing.JLabel();
        javax.swing.JPanel panelSection = new javax.swing.JPanel();
        javax.swing.JLabel labelClasse1 = new javax.swing.JLabel();
        sectionCodeInput = new javax.swing.JTextField();
        sectionLibelleInput = new javax.swing.JTextField();
        javax.swing.JPanel panelStudent = new javax.swing.JPanel();
        javax.swing.JLabel labelSalle1 = new javax.swing.JLabel();
        studentFirstnameInput = new javax.swing.JTextField();
        studentInput = new javax.swing.JTextField();
        javax.swing.JLabel labelSalle2 = new javax.swing.JLabel();
        studentLastNameInput = new javax.swing.JTextField();
        javax.swing.JPanel panelGroup = new javax.swing.JPanel();
        javax.swing.JLabel labelGroup = new javax.swing.JLabel();
        groupCodeInput = new javax.swing.JTextField();
        groupLibelleInput = new javax.swing.JTextField();
        javax.swing.JPanel panelSalle = new javax.swing.JPanel();
        javax.swing.JLabel labelSalle = new javax.swing.JLabel();
        salleInput = new javax.swing.JTextField();
        salleIntituleInput = new javax.swing.JTextField();
        javax.swing.JPanel jPanelButtons = new javax.swing.JPanel();
        javax.swing.JLabel labelOption = new javax.swing.JLabel();
        studentOptionRadio = new javax.swing.JRadioButton();
        coursOptionRadio = new javax.swing.JRadioButton();
        btnLoad = new javax.swing.JButton();

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        panelContent.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        grid.setModel(setModelDataTable(
            Dictionnaire.get(EnumLibelles.Business_Libelle_code),
            Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleFr),
            Dictionnaire.get(EnumLibelles.Business_Libelle_IntituleEn),
            Dictionnaire.get(EnumLibelles.Business_Libelle_Note),
            Dictionnaire.get(EnumLibelles.Business_Libelle_Credits),
            Dictionnaire.get(EnumLibelles.Business_Libelle_Total)
        ));
        grid.setFocusable(false);
        grid.setRequestFocusEnabled(false);
        grid.setShowHorizontalLines(true);
        grid.setShowVerticalLines(true);
        grid.getTableHeader().setReorderingAllowed(false);
        grid.setUpdateSelectionOnSort(false);
        grid.putClientProperty("Quaqua.Table.style", "striped");
        jScrollPane1.setViewportView(grid);

        panelClasse.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Classe))); // NOI18N
        panelClasse.setOpaque(false);

        labelClasse.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelClasse.setName("usernameLabel"); // NOI18N

        classeInput.setName("code"); // NOI18N

        classeIntituleInput.setEditable(false);
        classeIntituleInput.setFocusable(false);
        classeIntituleInput.setName("intitule"); // NOI18N
        classeIntituleInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classeIntituleInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelClasseLayout = new javax.swing.GroupLayout(panelClasse);
        panelClasse.setLayout(panelClasseLayout);
        panelClasseLayout.setHorizontalGroup(
            panelClasseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClasseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(classeIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        labelAneeAcademic.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_AnneeAcademique, true)); // NOI18N

        anneeAcademicInput.setName("ParametreAnneeAcademic->session->session"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->session->code", anneeAcademicInput);

        labelTrimestre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelTrimestre.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Trimestre, true)); // NOI18N

        trimestreInput.setName("ParametreAnneeAcademic->trimestreId->code"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->trimestreId->code", trimestreInput);

        sequenceInput.setName("ParametreAnneeAcademic->sequenceId->code"); // NOI18N
        fieldSearch("ParametreAnneeAcademic->sequenceId->code", sequenceInput);

        labelSequence.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelSequence.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Sequence, true)); // NOI18N

        panelSection.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Sections))); // NOI18N
        panelSection.setOpaque(false);

        labelClasse1.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelClasse1.setName("usernameLabel"); // NOI18N

        sectionCodeInput.setName("code"); // NOI18N

        sectionLibelleInput.setEditable(false);
        sectionLibelleInput.setFocusable(false);
        sectionLibelleInput.setName("intitule"); // NOI18N

        javax.swing.GroupLayout panelSectionLayout = new javax.swing.GroupLayout(panelSection);
        panelSection.setLayout(panelSectionLayout);
        panelSectionLayout.setHorizontalGroup(
            panelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelClasse1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sectionCodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sectionLibelleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSectionLayout.setVerticalGroup(
            panelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelClasse1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectionCodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sectionLibelleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelStudent.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Student))); // NOI18N
        panelStudent.setOpaque(false);

        labelSalle1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelSalle1.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_nom)); // NOI18N
        labelSalle1.setName("usernameLabel"); // NOI18N

        studentFirstnameInput.setEditable(false);
        studentFirstnameInput.setName("firstName"); // NOI18N
        fieldSearch("Salle->intitule", studentFirstnameInput);

        studentInput.setName("matricule"); // NOI18N
        fieldSearch("Groupe->classeId->code", studentInput);
        fieldsRequired.add(studentInput);

        labelSalle2.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code, true)); // NOI18N
        labelSalle2.setName("usernameLabel"); // NOI18N

        studentLastNameInput.setEditable(false);
        studentLastNameInput.setName("lastName"); // NOI18N
        fieldSearch("Salle->intitule", studentLastNameInput);

        javax.swing.GroupLayout panelStudentLayout = new javax.swing.GroupLayout(panelStudent);
        panelStudent.setLayout(panelStudentLayout);
        panelStudentLayout.setHorizontalGroup(
            panelStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSalle2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(studentInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelSalle1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(studentFirstnameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(studentLastNameInput)
                .addContainerGap())
        );
        panelStudentLayout.setVerticalGroup(
            panelStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSalle1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentFirstnameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentLastNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSalle2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelGroup.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_GrouperSalles))); // NOI18N
        panelGroup.setOpaque(false);

        labelGroup.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelGroup.setName("usernameLabel"); // NOI18N

        groupCodeInput.setName("code"); // NOI18N

        groupLibelleInput.setEditable(false);
        groupLibelleInput.setFocusable(false);
        groupLibelleInput.setName("intitule"); // NOI18N
        groupLibelleInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupLibelleInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGroupLayout = new javax.swing.GroupLayout(panelGroup);
        panelGroup.setLayout(panelGroupLayout);
        panelGroupLayout.setHorizontalGroup(
            panelGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(groupCodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(groupLibelleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGroupLayout.setVerticalGroup(
            panelGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupCodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupLibelleInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSalle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), Dictionnaire.get(EnumLibelles.Business_Libelle_Salle))); // NOI18N
        panelSalle.setOpaque(false);

        labelSalle.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_code)); // NOI18N
        labelSalle.setName("usernameLabel"); // NOI18N

        salleInput.setName("code"); // NOI18N

        salleIntituleInput.setEditable(false);
        salleIntituleInput.setFocusable(false);
        salleIntituleInput.setName("intitule"); // NOI18N

        javax.swing.GroupLayout panelSalleLayout = new javax.swing.GroupLayout(panelSalle);
        panelSalle.setLayout(panelSalleLayout);
        panelSalleLayout.setHorizontalGroup(
            panelSalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salleIntituleInput, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanelButtons.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5);
        flowLayout1.setAlignOnBaseline(true);
        jPanelButtons.setLayout(flowLayout1);

        labelOption.setText("Option");
        jPanelButtons.add(labelOption);

        optionButtonGroup.add(studentOptionRadio);
        studentOptionRadio.setSelected(true);
        studentOptionRadio.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Student)); // NOI18N
        jPanelButtons.add(studentOptionRadio);

        optionButtonGroup.add(coursOptionRadio);
        coursOptionRadio.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Cours)); // NOI18N
        jPanelButtons.add(coursOptionRadio);

        btnLoad.setIcon(new ImageIcon(Dictionnaire.getResource(LOAD).getScaledInstance(width, height, 0)));
        btnLoad.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Loading)); // NOI18N
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });
        jPanelButtons.add(btnLoad);

        javax.swing.GroupLayout panelContentLayout = new javax.swing.GroupLayout(panelContent);
        panelContent.setLayout(panelContentLayout);
        panelContentLayout.setHorizontalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContentLayout.createSequentialGroup()
                        .addComponent(labelAneeAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(anneeAcademicInput, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(152, 152, 152)
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelSequence, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTrimestre, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sequenceInput)
                            .addComponent(trimestreInput))
                        .addGap(18, 18, 18))
                    .addGroup(panelContentLayout.createSequentialGroup()
                        .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(panelStudent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelContentLayout.createSequentialGroup()
                                    .addComponent(panelSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(panelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelContentLayout.createSequentialGroup()
                                .addComponent(panelGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(7, Short.MAX_VALUE))))
            .addGroup(panelContentLayout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelContentLayout.setVerticalGroup(
            panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAneeAcademic)
                    .addComponent(anneeAcademicInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTrimestre)
                    .addComponent(trimestreInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSequence)
                    .addComponent(sequenceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContentLayout.createSequentialGroup()
                        .addComponent(panelSalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
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

    private void groupLibelleInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupLibelleInputActionPerformed
        // TODO add your handling code here:
//        salleInput.removeActionListener(null);
        eventSalle();
    }//GEN-LAST:event_groupLibelleInputActionPerformed

    private void classeIntituleInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classeIntituleInputActionPerformed
        // TODO add your handling code here:
//        salleInput.removeActionListener(null);
        eventSalle();
    }//GEN-LAST:event_classeIntituleInputActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
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
    }//GEN-LAST:event_btnLoadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anneeAcademicInput;
    private javax.swing.JButton btnLoad;
    private javax.swing.JTextField classeInput;
    private javax.swing.JTextField classeIntituleInput;
    private javax.swing.JRadioButton coursOptionRadio;
    private javax.swing.JTable grid;
    private javax.swing.JTextField groupCodeInput;
    private javax.swing.JTextField groupLibelleInput;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.ButtonGroup optionButtonGroup;
    private javax.swing.JTextField salleInput;
    private javax.swing.JTextField salleIntituleInput;
    private javax.swing.JTextField sectionCodeInput;
    private javax.swing.JTextField sectionLibelleInput;
    private javax.swing.JTextField sequenceInput;
    private javax.swing.JTextField studentFirstnameInput;
    private javax.swing.JTextField studentInput;
    private javax.swing.JTextField studentLastNameInput;
    private javax.swing.JRadioButton studentOptionRadio;
    private javax.swing.JTextField trimestreInput;
    // End of variables declaration//GEN-END:variables

}
