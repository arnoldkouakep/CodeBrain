/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.main.business.controller.CodeBrainManager;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.controller.Locale;
import cm.codebrain.ui.application.controller.TableModelObject;
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.Enums;
import static cm.codebrain.ui.application.enumerations.Enums.CREATE;
import static cm.codebrain.ui.application.enumerations.Enums.DELETE;
import static cm.codebrain.ui.application.enumerations.Enums.DUPPLICATE;
import static cm.codebrain.ui.application.enumerations.Enums.Entity;
import static cm.codebrain.ui.application.enumerations.Enums.MODIF;
import static cm.codebrain.ui.application.enumerations.Enums.Type;
import static cm.codebrain.ui.application.enumerations.Enums.Model;
import static cm.codebrain.ui.application.enumerations.Enums.Value;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author KSA-INET
 */
public class ModelForm extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;

    public Enums etatAction = CREATE;

    private HashMap formDatas = new HashMap();

    protected HashMap fieldSearch = new HashMap();

    protected HashMap<String, Object> modelFinal;
    protected TableModelObject tableModelObject;

    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    public final int width = 18;
    public final int height = 18;
    private int wi = 394;
    private int he = 390;
    private JTextField inputRef;
    public String entity;
    private List modelResult;
    private Boolean createList = false;
    private String title;
    private boolean hideActionMenuTitle;

//    private String filter;
    CodeBrainManager cbManager = new CodeBrainManager();
    private InputSearchForm serachForm;
    private Object key;
    private List<HashMap> allComponents;

    public final String VALIDATE = "/images/accept.png";
    public final String ADD = "/images/add.png";
    public final String DEL = "/images/desactiver.png";
    public final String EDIT = "/images/cancel.png";
    public final String PRINT = "/images/printer.png";
    public final String CANCEL = "/images/cancel.png";
    public final String LOAD = "/images/load.png";
    public final String RESET = "/images/grid.png";
    public final String CLEAR = "/images/effacer.png";
    public final String EXIT = "/images/exit.png";

//    private MainForm mainForm;
//    private Loading loading;
//    private MainForm mainForm;
//    private Loading loading;
    /**
     * Creates new form LoginForm
     *
     * @param title
     */
    public ModelForm(String title) {
        super();

        Locale.initBundle();

        setSize(wi, he);

//        setTitle(title);
        this.title = title;

        createList(false);

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);

        createForm();

        addActionSupplementaire();
    }

    public ModelForm(String title, int width, int height) {

        super();

        Locale.initBundle();

        setSize(width, height);

        this.title = title;

        this.wi = width;
        this.he = height;
        hideActionMenuTitle = false;

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);

        createForm();

        addActionSupplementaire();
        
        eventActionRef();
    }

    public ModelForm(String title, int width, int height, boolean hideActionMenuTitle) {

        super();

        Locale.initBundle();

        setSize(width, height);

        this.wi = width;
        this.he = height;
        this.hideActionMenuTitle = hideActionMenuTitle;

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);

        createForm();

        addActionSupplementaire();
        
        eventActionRef();
    }

    public void createForm() {

    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

//    public void addAction(Object input) {
//        /**
//         * TextFields
//         */
//        if (input.getClass().equals(JTextField.class)) {
//            ((JTextField) input).addFocusListener(new java.awt.event.FocusAdapter() {
//                @Override
//                public void focusLost(java.awt.event.FocusEvent evt) {
////                    levelCodeInputFocusLost(evt, null, null, null, null, null, null);
//                }
//            });
//        } /**
//         * ComboBox
//         */
//        else if (input.getClass().equals(JComboBox.class)) {
//            ((JComboBox) input).addFocusListener(new java.awt.event.FocusAdapter() {
//                @Override
//                public void focusLost(java.awt.event.FocusEvent evt) {
////                    levelCodeInputFocusLost(evt, null, null, null, null, null, (Object) null);
//                }
//            });
//        } else {
//            System.out.println("Nothing");
//        }
//
//    }
    public void addAction(Object input, String entity, String[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {
        /**
         * TextFields
         */
        if (input.getClass().equals(JTextField.class)) {
            ((JTextField) input).addActionListener((ActionEvent e) -> {
                if (!((JTextField) input).getText().isEmpty()) {
                    levelCodeInputFocusLost(e, input, entity, null, parametresGrid, filter, args, imputsResult);
                }
            });

        } /**
         * ComboBox
         */
        else if (input.getClass().equals(JComboBox.class)) {
            ((JComboBox) input).addActionListener((ActionEvent e) -> {
                if (!((JComboBox) input).getSelectedItem().toString().isEmpty()) {
                    levelCodeInputFocusLost(e, input, entity, null, parametresGrid, filter, args, imputsResult);
                }
            });
        } else {
            System.out.println("Nothing");
        }
    }

    public void addAction(Object input, String entity, String keyParam, String[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {
        /**
         * TextFields
         */
        if (input.getClass().equals(JTextField.class)) {
            ((JTextField) input).addActionListener((ActionEvent e) -> {
                if (!((JTextField) input).getText().isEmpty()) {
                    levelCodeInputFocusLost(e, input, entity, keyParam, parametresGrid, filter, args, imputsResult);
                }
            });

        } /**
         * ComboBox
         */
        else if (input.getClass().equals(JComboBox.class)) {
            ((JComboBox) input).addActionListener((ActionEvent e) -> {
                if (!((JComboBox) input).getSelectedItem().toString().isEmpty()) {
                    levelCodeInputFocusLost(e, input, entity, keyParam, parametresGrid, filter, args, imputsResult);
                }
            });
        } else {
            System.out.println("Nothing");
        }
    }

    private void levelCodeInputFocusLost(java.awt.event.ActionEvent evt, Object input, String entity, String keyParam, String[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {
        if (etatAction != CREATE || (etatAction == CREATE && !input.equals(getRef()))) {
            Loading.show(btnValider, new Executable<List<HashMap>>() {
                private Object key;
                private Boolean find;
                String filtre = getValueInputObject(input);

                @Override
                public List<HashMap> execute() throws Exception {

                    ArrayList parameterArgs = new ArrayList();

                    if (args != null) {

                        for (HashMap arg : args) {
                            if (arg.get(Type).equals(Entity)) {
                                String entitie = arg.get(Entity).toString();
                                HashMap data = (HashMap) GlobalParameters.getVar(arg.get(Model).toString());

                                Object dataObject = cbManager.convertToObject(data, entitie);
                                parameterArgs.add(dataObject);
                            } else {
                                parameterArgs.add(arg.get(Value));
                            }
                        }
                    }
                    modelResult = cbManager.getListEntity(entity, filter, parameterArgs.toArray());

                    List<HashMap> modelComplet = cbManager.convertToListObject(modelResult, HashMap.class);

                    if (!filtre.equals("*")) {
                        modelComplet = modelComplet.stream().map((mapper) -> {
                            find = false;
                            return mapper;
                        }).filter(((model) -> {
                            model.keySet().stream().map((ky) -> {
                                this.key = ky;
                                return ky;
                            }
                            ).forEachOrdered((Object val) -> {
                                Boolean b = model.get(key).toString().contains(filtre);
                                if (b) {
                                    find = true;
                                }
                            });
                            return find;
                        })).collect(Collectors.toList());
                    }
                    if (modelComplet.isEmpty()) {
                        throw new Exception(Dictionnaire.get(EnumError.Business_Libelle_No_Result_Found.toString()));
                    }
                    serachForm = new InputSearchForm(entity, keyParam, modelComplet, parametresGrid, imputsResult);

                    serachForm.setVisible(true);

//                    try {
//                        Robot robot = new Robot();
//                        robot.keyPress(KeyEvent.VK_TAB);
//                        robot.keyRelease(KeyEvent.VK_TAB);
//                    } catch (AWTException e) {
//                    }
//                     if(input.getClass().equals(JTextField.class)){
//                    ((JTextField) input).setFocusable(false);
//                    ((JTextField) input).setFocusable(true);
//                    }
//                    else if(input.getClass().equals(JComboBox.class)){
//                    ((JComboBox) input).setFocusable(false);
//                    ((JComboBox) input).setFocusable(true);
//                    }
//                    HashMap component;
//                    int index = 0;
//                    for (int i = 0; i < allComponents.size(); i++) {
//                        if (allComponents.get(i).get(Value).equals(input)) {
//                            index = i + 1;
//                            break;
//                        }
//                    }
//                    component = allComponents.get(index);
//
//                    Object obj = component.get(Value);
//
//                    if (obj.getClass().equals(JTextField.class)) {
//                        ((JTextField) obj).requestFocus();
//                    } else if (obj.getClass().equals(JComboBox.class)) {
//                        ((JComboBox) obj).requestFocus();
//                    }
                    if (etatAction != CREATE && input.equals(getRef())) {
                        try {
                            GlobalParameters.addVar(Model.toString(), serachForm.getResult());
                            getModelData(serachForm.getResult());
                        } catch (Exception e) {
                        }
                    }

                    return modelComplet;
                }

                @Override
                public void error(Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }

            });
        }

    }

    private String getValueInputObject(Object input) {
        String filter = null;
        /**
         * TextFields
         */
        if (input.getClass().equals(JTextField.class)) {
            filter = ((JTextField) input).getText();
        } /**
         * ComboBox
         */
        else if (input.getClass().equals(JComboBox.class)) {
            filter = ((JComboBox) input).getSelectedItem().toString();
        } else {
            System.out.println("Nothing");
        }

        return filter;
    }

    public void addFormData(String key, Object value) {
        this.formDatas.putIfAbsent(key, value);
    }

    public Object getData(String key) {
        return this.formDatas.get(key);
    }

    public void fieldSearch(String key, Object value) {
        this.fieldSearch.putIfAbsent(key, value);
    }

    public Object getFieldSearch(String key) {
        return this.fieldSearch.get(key);
    }

    public void showActionBar() {

        setTitle(stateMenuLabel(title, hideActionMenuTitle));

        setActionMenu();
        getContentPane().add(actionBar, java.awt.BorderLayout.EAST);
    }

    public void showMenuBar() {
        getContentPane().add(menuBar, java.awt.BorderLayout.NORTH);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        btnActionMenuGroup = new javax.swing.ButtonGroup();
        bottonPanel = new javax.swing.JPanel();
        labelCopyright = new javax.swing.JLabel();
        panelButtons = new javax.swing.JPanel();
        btnValider = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        menuBar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnNew.putClientProperty("JButton.buttonType", "segmented");
        btnClear = new javax.swing.JButton();
        btnClear.putClientProperty("JButton.buttonType", "segmented");
        btnPrint = new javax.swing.JButton();
        btnPrint.putClientProperty("JButton.buttonType", "segmented");
        btnCancel = new javax.swing.JButton();
        btnCancel.putClientProperty("JButton.buttonType", "segmented");
        actionBar = new javax.swing.JToolBar();
        btnCreate = new javax.swing.JToggleButton();
        btnModify = new javax.swing.JToggleButton();
        btnDupplicate = new javax.swing.JToggleButton();
        btnDelete = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(getSize());
        setModal(true);
        setResizable(false);

        bottonPanel.setLayout(new java.awt.BorderLayout());

        labelCopyright.setFont(new java.awt.Font("sansserif", 2, 8)); // NOI18N
        labelCopyright.setText("© CodeBrain@2018");
        labelCopyright.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        labelCopyright.setEnabled(false);
        bottonPanel.add(labelCopyright, java.awt.BorderLayout.WEST);

        btnValider.setIcon(new ImageIcon(Dictionnaire.getResource(VALIDATE).getScaledInstance(width, height, 0)));//new ImageIcon(getClass().getResource()).getImage().getScaledInstance(width, height, 0)));
        btnValider.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_valider)); // NOI18N
        btnValider.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        panelButtons.add(btnValider);
        getRootPane().setDefaultButton(btnValider);

        cancelButton.setIcon(new ImageIcon(Dictionnaire.getResource(CANCEL).getScaledInstance(width, height, 0)));//new ImageIcon(getClass().getResource()).getImage().getScaledInstance(width, height, 0)));//"/images/cancel.png"
        cancelButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Annuler)); // NOI18N
        cancelButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cancelButton.addActionListener(this::actionBtnCancel);
        panelButtons.add(cancelButton);

        bottonPanel.add(panelButtons, java.awt.BorderLayout.EAST);

        getContentPane().add(bottonPanel, java.awt.BorderLayout.SOUTH);

        menuBar.setFloatable(false);
        menuBar.setRollover(true);
        menuBar.putClientProperty("Quaqua.ToolBar.style", "bottom");

        btnNew.setIcon(new ImageIcon(Dictionnaire.getResource(ADD).getScaledInstance(width, height, 0)));//new ImageIcon(getClass().getResource()).getImage().getScaledInstance(width, height, 0)));//"/images/add.png"
        btnNew.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Nouveau)); // NOI18N
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuBar.add(btnNew);

        btnClear.setIcon(new ImageIcon(Dictionnaire.getResource(CLEAR).getScaledInstance(width, height, 0)));//new ImageIcon(getClass().getResource(CLEAR)).getImage().getScaledInstance(width, height, 0)));//"/images/effacer.png"
        btnClear.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Effacer)); // NOI18N
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuBar.add(btnClear);

        btnPrint.setIcon(new ImageIcon(Dictionnaire.getResource(PRINT).getScaledInstance(width, height, 0)));//new ImageIcon(getClass().getResource(PRINT)).getImage().getScaledInstance(width, height, 0)));//"/images/printer.png"
        btnPrint.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Print)); // NOI18N
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuBar.add(btnPrint);

        btnCancel.setIcon(new ImageIcon(Dictionnaire.getResource(CANCEL).getScaledInstance(width, height, 0)));//new ImageIcon(getClass().getResource(CANCEL)).getImage().getScaledInstance(width, height, 0)));//"/images/cancel.png"
        btnCancel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Annuler)); // NOI18N
        btnCancel.setFocusable(false);
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuBar.add(btnCancel);

//        getContentPane().add(menuBar, java.awt.BorderLayout.NORTH);
        actionBar.setFloatable(false);
        actionBar.setOrientation(javax.swing.SwingConstants.VERTICAL);
        actionBar.setMargin(new java.awt.Insets(30, 10, 20, 10));
        actionBar.putClientProperty("Quaqua.ToolBar.style", "bottom");

        btnActionMenuGroup.add(btnCreate);
        btnCreate.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnCreate.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_BtnCreation)); // NOI18N
        btnCreate.setFocusable(false);
        btnCreate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCreate.setMargin(new java.awt.Insets(10, 0, 10, 0));
        btnCreate.setMaximumSize(new java.awt.Dimension(100, 40));
        btnCreate.setMinimumSize(new java.awt.Dimension(100, 40));
        btnCreate.setPreferredSize(new java.awt.Dimension(100, 40));
        btnCreate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCreate.putClientProperty("JButton.buttonType", "segmented");
        actionBar.add(btnCreate);

        btnActionMenuGroup.add(btnModify);
        btnModify.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnModify.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_BtnModification)); // NOI18N
        btnModify.setFocusable(false);
        btnModify.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModify.setMargin(new java.awt.Insets(10, 0, 10, 0));
        btnModify.setMaximumSize(new java.awt.Dimension(100, 40));
        btnModify.setMinimumSize(new java.awt.Dimension(100, 40));
        btnModify.setPreferredSize(new java.awt.Dimension(100, 40));
        btnModify.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModify.putClientProperty("JButton.buttonType", "segmented");
        actionBar.add(btnModify);

        btnActionMenuGroup.add(btnDupplicate);
        btnDupplicate.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnDupplicate.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_BtnDupplication)); // NOI18N
        btnDupplicate.setFocusable(false);
        btnDupplicate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDupplicate.setMargin(new java.awt.Insets(10, 0, 10, 0));
        btnDupplicate.setMaximumSize(new java.awt.Dimension(100, 40));
        btnDupplicate.setMinimumSize(new java.awt.Dimension(100, 40));
        btnDupplicate.setPreferredSize(new java.awt.Dimension(100, 40));
        btnDupplicate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDupplicate.putClientProperty("JButton.buttonType", "segmented");
        actionBar.add(btnDupplicate);

        btnActionMenuGroup.add(btnDelete);
        btnDelete.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnDelete.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_BtnSupprimer)); // NOI18N
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setMargin(new java.awt.Insets(10, 0, 10, 0));
        btnDelete.setMaximumSize(new java.awt.Dimension(100, 40));
        btnDelete.setMinimumSize(new java.awt.Dimension(100, 40));
        btnDelete.setPreferredSize(new java.awt.Dimension(100, 40));
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.putClientProperty("JButton.buttonType", "segmented");
        actionBar.add(btnDelete);

//        getContentPane().add(actionBar, java.awt.BorderLayout.EAST);
        getAccessibleContext().setAccessibleName("FORMULAIRE");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    public void actionMenu(java.awt.event.ActionEvent evt) {
        setActionMenu();
    }

    public void actionBtnCreate(java.awt.event.ActionEvent evt) {
        etatAction = CREATE;
        setActionMenu();
    }

    public void actionBtnModify(java.awt.event.ActionEvent evt) {
        etatAction = MODIF;
        setActionMenu();
    }

    public void actionBtnDupplicate(java.awt.event.ActionEvent evt) {
        etatAction = DUPPLICATE;
        setActionMenu();
    }

    public void actionBtnDelete(java.awt.event.ActionEvent evt) {
        etatAction = DELETE;
        setActionMenu();
    }

    public void actionBtnValider(java.awt.event.ActionEvent evt) {

        Loading.show(btnValider, new Executable<HashMap>() {

            @Override
            public HashMap execute() throws Exception {

                if (createList) {

                    List<HashMap> modelDataFinal = tableModelObject.getModel();

                    if (modelDataFinal != null) {
                        for (HashMap model : modelDataFinal) {
                            makeModelDatas(model);

                            if (null != etatAction) switch (etatAction) {
                                case CREATE:
                                    cbManager.createEntity(entity, modelFinal);
                                    break;
                                case MODIF:
                                    cbManager.updateEntity(entity, modelFinal);
                                    break;
                                case DUPPLICATE:
                                    cbManager.dupplicateEntity(entity, modelFinal);
                                    break;
                                case DELETE:
                                    cbManager.deleteEntity(entity, modelFinal);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    /**
                     *
                     * To Do
                     *
                     *
                     */
                } else {
                    if(etatAction == CREATE)
                        modelFinal = new HashMap<>();
                    else
                        modelFinal = (HashMap) GlobalParameters.getVar(Model.toString());
                        
                    makeModelData();
                        
                    if (null != etatAction) switch (etatAction) {
                        case CREATE:
                            cbManager.createEntity(entity, modelFinal);
                            break;
                        case MODIF:
                            cbManager.updateEntity(entity, modelFinal);
                            break;
                        case DUPPLICATE:
                            cbManager.dupplicateEntity(entity, modelFinal);
                            break;
                        case DELETE:
                            cbManager.deleteEntity(entity, modelFinal);
                            break;
                        default:
                            break;
                    }
                }

                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(btnValider), Dictionnaire.get(EnumLibelles.Business_Libelle_Message_Sucess.toString()), "Message", JOptionPane.INFORMATION_MESSAGE);
                
                reset();

                return modelFinal;
            }

            @Override
            public void error(Exception ex) {
                System.out.println("Error : " + ex.getLocalizedMessage());
                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(btnValider), Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void actionBtnReset(java.awt.event.ActionEvent evt) {
        reset();
    }

    public void makeModelDatas(HashMap model) {
        modelFinal = new HashMap<>();
        makeModelData();

        model.keySet().stream().map((ky) -> {
            this.key = ky.toString();
            return ky;
        }).forEachOrdered((Object val) -> {
            modelFinal.putIfAbsent(this.key.toString(), model.get(val));
        });
    }

    public void makeModelData() {
//        modelFinal = new HashMap<>();
//        if(entity.toLowerCase() + "Id")
//        modelFinal.put(entity.toLowerCase() + "Id", CodeBrainManager.generateUIDPrimaryKey());

//        modelFinal.put("levelsId", ((Users) GlobalParameters.getVar("user")).getLevelsId());

        if (!createList) {
            /**
             * JTextField.class
             */
            formDatas.keySet().stream().map((ky) -> {
                this.key = ky;
                return ky;
            }).filter((ky) -> (formDatas.get(ky).getClass() == JTextField.class)).map((ky) -> (JTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
                modelFinal.put(this.key.toString(), ((JTextField) val).getText());
            });
            /**
             * JPasswordField.class
             */
            formDatas.keySet().stream().map((ky) -> {
                this.key = ky;
                return ky;
            }).filter((ky) -> (formDatas.get(ky).getClass() == JPasswordField.class)).map((ky) -> (JPasswordField) formDatas.get(ky)).forEachOrdered((Object val) -> {
                modelFinal.put(this.key.toString(), ((JPasswordField) val).getText());
            });
            /**
             * JTextPane.class
             */
            formDatas.keySet().stream().map((ky) -> {
                this.key = ky;
                return ky;
            }).filter((ky) -> (formDatas.get(ky).getClass() == JTextPane.class)).map((ky) -> (JTextPane) formDatas.get(ky)).forEachOrdered((Object val) -> {
                modelFinal.put(this.key.toString(), ((JTextPane) val).getText());
            });
            /**
             * JFormattedTextField.class
             */
            formDatas.keySet().stream().map((ky) -> {
                this.key = ky;
                return ky;
            }).filter((ky) -> (formDatas.get(ky).getClass() == JFormattedTextField.class)).map((ky) -> (JFormattedTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
                modelFinal.put(this.key.toString(), ((JFormattedTextField) val).getText());
            });

            GlobalParameters.addVar(Model.toString(), modelFinal);
        }
    }

    public void getModelData(HashMap model) {
        /**
         * JTextField.class
         */
//        formDatas.keySet().stream().map((ky) -> {
//            this.key = ky;
//            return ky;
//        }).filter((ky) -> (formDatas.get(ky).getClass() == JTextField.class)).map((ky) -> (JTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
//            ((JTextField) val).setText(getValueModelFromKey(this.key.toString(), model).toString());//model.get(this.key.toString()).toString());
//        });

        fieldSearch.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (fieldSearch.get(ky).getClass() == JTextField.class)).map((ky) -> (JTextField) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
            ((JTextField) val).setText(getValueModelFromKey(this.key.toString(), model).toString());//model.get(this.key.toString()).toString());
        });
        /**
         * JPasswordField.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JPasswordField.class)).map((ky) -> (JPasswordField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JPasswordField) val).setText(model.get(this.key.toString()).toString());
        });
        /**
         * JTextPane.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JTextPane.class)).map((ky) -> (JTextPane) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JTextPane) val).setText(model.get(this.key.toString()).toString());
        });
        /**
         * JFormattedTextField.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JFormattedTextField.class)).map((ky) -> (JFormattedTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JFormattedTextField) val).setText(model.get(this.key.toString()).toString());
        });

//            GlobalParameters.addVar("model", modelFinal);
    }

    private void actionBtnCancel(java.awt.event.ActionEvent evt) {
        doClose(RET_CANCEL);
        dispose();
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    public void setActionMenu() {
        reset();
        setTitle(stateMenuLabel(title, hideActionMenuTitle));
        switch (etatAction) {
            case CREATE:
                btnCreate.setSelected(true);
                setDefaultActionRef();
                break;
            case MODIF:
                btnModify.setSelected(true);
                setActionRef();
                break;
            case DUPPLICATE:
                btnDupplicate.setSelected(true);
                setActionRef();
                break;
            case DELETE:
                btnDelete.setSelected(true);
                setActionRef();
                break;
            default:
                break;
        }
    }

    public void setActionRef() {
        this.inputRef.setBackground(new Color(223, 246, 238));//68, 127, 255));
        this.inputRef.requestFocus();
    }

    public void setDefaultActionRef() {
        this.inputRef.setBackground(new Color(255, 255, 255));
        this.inputRef.setFocusable(true);
    }

    public void setRef(JTextField inputRef) {
        this.inputRef = inputRef;
    }

    public JTextField getRef() {
        return this.inputRef;
    }

    public void addActionSupplementaire() {
    }

    public void setAllComponents(Object... components) {
        allComponents = new ArrayList<>();
        HashMap component;
        for (Object com : components) {
            component = new HashMap();
            component.put(Type, com.getClass());
            component.put(Value, com);

            allComponents.add(component);
        }
    }

    public void reset() {
        if (allComponents == null || allComponents.isEmpty()) {
            resetFormData();
        } else {
            allComponents.forEach((HashMap component) -> {
                /**
                 * JTextField.class
                 */
                if (component.get(Type) == JTextField.class) {
                    ((JTextField) component.get(Value)).setText(null);
                    ((JTextField) component.get(Value)).setFocusable(true);

                    /**
                     * JPasswordField.class
                     */
                } else if (component.get(Type) == JPasswordField.class) {
                    ((JPasswordField) component.get(Value)).setText(null);
                    /**
                     * JTextPane.class
                     */
                } else if (component.get(Type) == JTextPane.class) {
                    ((JTextPane) component.get(Value)).setText(null);
                    /**
                     * JFormattedTextField.class
                     */
                } else if (component.get(Type) == JFormattedTextField.class) {
                    ((JFormattedTextField) component.get(Value)).setText(null);
                } else {
                    System.out.println(component.get(Type).toString() + " : Type non géré.");
                }
            });
        }
    }

    public void reset(Object... components) {
        if (components != null || components.length > 0) {
            for (Object component : components) {
                if (component.getClass() == JTextField.class) {
                    ((JTextField) component).setText(null);
                    ((JTextField) component).setFocusable(true);

                    /**
                     * JPasswordField.class
                     */
                } else if (component.getClass() == JPasswordField.class) {
                    ((JPasswordField) component).setText(null);
                    /**
                     * JTextPane.class
                     */
                } else if (component.getClass() == JTextPane.class) {
                    ((JTextPane) component).setText(null);
                    /**
                     * JFormattedTextField.class
                     */
                } else if (component.getClass() == JFormattedTextField.class) {
                    ((JFormattedTextField) component).setText(null);
                } else {
                    System.out.println(component.toString() + " : Type non géré.");
                }
            }
        }
    }

    public void resetFormData() {

        /**
         * JTextField.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JTextField.class)).map((ky) -> (JTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JTextField) formDatas.get(this.key.toString())).setText(null);
        });
        /**
         * JPasswordField.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JPasswordField.class)).map((ky) -> (JPasswordField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JPasswordField) formDatas.get(this.key.toString())).setText(null);
        });
        /**
         * JTextPane.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JTextPane.class)).map((ky) -> (JTextPane) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JTextPane) formDatas.get(this.key.toString())).setText(null);
        });
        /**
         * JFormattedTextField.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JFormattedTextField.class)).map((ky) -> (JFormattedTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JFormattedTextField) formDatas.get(this.key.toString())).setText(null);
        });
    }

    public void setRadiBottun(ButtonGroup group, JRadioButton... rdButtons) {
        ActionListener action = (ActionEvent e) -> {
            for (JRadioButton rdBtn : rdButtons) {
                if (rdBtn.isSelected() && rdBtn == group.getSelection()) {
                    group.setSelected(rdBtn.getModel(), true);
                    break;
                }
            }
        };
    }
    
//    protected void eventActionRef(){
//    
//    }

    public void createList(boolean state) {
        this.createList = state;

        if (this.createList) {
            tableModelObject = new TableModelObject(this.formDatas);
        } else {
            tableModelObject = null;
        }
    }

    public void setTableModel(Object... inputs) {
        tableModelObject.setModel(inputs);
    }

    private String stateMenuLabel(String title, boolean hideActionMenuTitle) {
        if (null == etatAction || hideActionMenuTitle) {
            return title;
        } else {
            switch (etatAction) {
                case CREATE:
                    return title.concat(" - ").concat(Dictionnaire.get(EnumLibelles.Business_Libelle_Creation));
                case MODIF:
                    return title.concat(" - ").concat(Dictionnaire.get(EnumLibelles.Business_Libelle_Modification));
                case DUPPLICATE:
                    return title.concat(" - ").concat(Dictionnaire.get(EnumLibelles.Business_Libelle_Dupplicate));
                case DELETE:
                    return title.concat(" - ").concat(Dictionnaire.get(EnumLibelles.Business_Libelle_Suppression));
                default:
                    return title;
            }
        }
    }

    public Object getValueModelFromKey(String key, HashMap model) {
        Object value = null;
        String[] indentKey = key.split("->");
        HashMap modelTmp = model;

        if (indentKey.length > 1) {
            for (int i=0; i < indentKey.length; i++) {
                Object object = modelTmp.get(indentKey[i]);
                
                if (object.getClass().equals(HashMap.class) || object.getClass().equals(LinkedHashMap.class)) {
                    
                    GlobalParameters.addVar(indentKey[i], object);

                    modelTmp = (HashMap) object;
//                } else if(object.getClass().equals(String.class)){
                    
//                    HashMap mapResult = null;
//                    try{
//                        Object dataObject = cbManager.getEntity(indentKey[i], object.toString());
//                        mapResult = (HashMap) cbManager.convertToObject(dataObject, HashMap.class);
//                        
//                        GlobalParameters.addVar(indentKey[i+1], mapResult);
//
//                        modelTmp = (HashMap) mapResult;
//                        value = modelTmp.get(indentKey[i]);
//                    }catch(Exception e){
//                    }
                }else{
                    
//                    GlobalParameters.addVar(indentKey[i+1], modelTmp);

                    value = modelTmp.get(indentKey[i]);
                }
            }
        } else {
            value = modelTmp.get(key);
        }

        return value;
    }

    protected void eventActionRef() {

    }

    // Variables declaration - do not modify                     
    private javax.swing.JToolBar actionBar;
    private javax.swing.JPanel bottonPanel;
    private javax.swing.ButtonGroup btnActionMenuGroup;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClear;
    public javax.swing.JToggleButton btnCreate;
    private javax.swing.JToggleButton btnDelete;
    private javax.swing.JToggleButton btnDupplicate;
    private javax.swing.JToggleButton btnModify;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPrint;
    public javax.swing.JButton btnValider;
    public javax.swing.JButton cancelButton;
    private javax.swing.JLabel labelCopyright;
    private javax.swing.JToolBar menuBar;
    private javax.swing.JPanel panelButtons;
    // End of variables declaration                   

    private int returnStatus = RET_CANCEL;

}
