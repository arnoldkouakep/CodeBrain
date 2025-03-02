/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.main.business.controller.CodeBrainException;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.FormParameters;
import cm.codebrain.ui.application.controller.Locale;
import cm.codebrain.ui.application.controller.TableModelObject;
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import static cm.codebrain.ui.application.enumerations.EnumVariable.CREATE;
import static cm.codebrain.ui.application.enumerations.EnumVariable.DELETE;
import static cm.codebrain.ui.application.enumerations.EnumVariable.DUPPLICATE;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Default;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Entity;
import static cm.codebrain.ui.application.enumerations.EnumVariable.MODIF;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Type;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Model;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Panel;
import cm.codebrain.ui.application.implement.Action;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import cm.codebrain.ui.application.services.CodeBrainServiceAsync;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author KSA-INET
 */
public abstract class ModelForm extends javax.swing.JDialog {

// <editor-fold desc="Variables">
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;

    public EnumVariable etatAction = CREATE;
    private EnumVariable etatActionList = Default;

    private final HashMap formDatas = new HashMap();

    protected HashMap fieldSearch = new HashMap();

    protected HashMap<String, Object> modelFinal;
//    protected HashMap modelMaster = new HashMap<>();
    protected List fieldsRequired = new ArrayList();
    protected TableModelObject tableModelObject;

    public final int width = 18;
    public final int height = 18;
    private int wi = 394;
    private int he = 390;
    private JTextField inputRef;
    public String entity;
    private List modelResult;
    private Boolean createList = false;
    private final String title;
    private boolean hideActionMenuTitle = true;
    private boolean confirmExit = false;
    private final Color colorDefault = new Color(255, 255, 255);
    private final Color colorRef = new Color(200, 236, 255);//(200, 255, 255); 175, 215, 255
    private final Color colorSearch = new Color(223, 246, 238);
    private final Color colorResult = new Color(180, 183, 182);//255, 253, 235);

//    public CodeBrainManager cbManager = new CodeBrainManager();
    private SearchForm searchForm;
    private Object key;
    private List<HashMap> allComponents;

    public final String VALIDATE = "/images/accept.png";
    public final String ADD = "/images/add.png";
    public final String DEL = "/images/desactiver.png";
    public final String EDIT = "/images/cancel.png";
    public final String PRINT = "/images/printer.png";
    public final String CANCEL = "/images/cancel.png";
    public final String LOAD = "/images/load.png";
    public final String NEXT = "/images/next.png";
    public final String LEFT = "/images/left.png";
    public final String RIGHT = "/images/right.png";
    public final String PREVIEW = "/images/preview.png";
    public final String RESET = "/images/grid.png";
    public final String CLEAR = "/images/effacer.png";
    public final String EXIT = "/images/exit.png";
    public List<HashMap> modelFinals;

    public DefaultTableModel tableModel;
    private List<HashMap> listModelCreateUpdate;
    private List<HashMap> listModelDelete;
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
    private javax.swing.JToggleButton btnSearch;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPrint;
    public javax.swing.JButton btnValider;
    public javax.swing.JButton cancelButton;
    private javax.swing.JLabel labelCopyright;
    private javax.swing.JToolBar menuBar;
    private javax.swing.JPanel panelButtons;
    // End of variables declaration
    private int returnStatus = RET_CANCEL;
// </editor-fold>

// <editor-fold desc="Constructors">
    /**
     * Creates new form LoginForm
     *
     * @param title
     */
    public ModelForm(String title) {
        super();

        init();

        Locale.initBundle();

        setSize(wi, he);

        this.title = title;

        createList(false);

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnSearch.addActionListener(this::actionBtnSearch);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);
        btnCancel.addActionListener(this::actionBtnCancel);

        createForm();

        addActionSupplementaire();

        loadRequiredFields();
        FormParameters.add(Panel.toString() + this.getClass().getSimpleName(), this);
    }

    public ModelForm(String title, int width, int height) {

        super();

        init();

        Locale.initBundle();

        setSize(width, height);

        this.title = title;
        hideActionMenuTitle = false;

        showTitle();

        this.wi = width;
        this.he = height;

        createList(false);

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnSearch.addActionListener(this::actionBtnSearch);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);
        btnCancel.addActionListener(this::actionBtnCancel);

        createForm();

        addActionSupplementaire();

        eventActionRef();

        loadRequiredFields();
        FormParameters.add(Panel.toString() + this.getClass().getSimpleName(), this);
    }

    public ModelForm(String title, int width, int height, boolean hideActionMenuTitle) {

        super();
        init();

        Locale.initBundle();

        setSize(width, height);

        this.title = title;
        this.hideActionMenuTitle = hideActionMenuTitle;

        showTitle();
        this.wi = width;
        this.he = height;

        createList(false);

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnSearch.addActionListener(this::actionBtnSearch);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);
        btnCancel.addActionListener(this::actionBtnCancel);

        createForm();

        addActionSupplementaire();

        eventActionRef();

        loadRequiredFields();
        FormParameters.add(Panel.toString() + this.getClass().getSimpleName(), this);
    }

    public ModelForm(String title, int width, int height, boolean hideActionMenuTitle, boolean confirmExit) {

        super();
        init();

        Locale.initBundle();

        setSize(width, height);

        this.title = title;
        this.hideActionMenuTitle = hideActionMenuTitle;

        showTitle();

        this.wi = width;
        this.he = height;

        this.confirmExit = confirmExit;

        createList(false);

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnSearch.addActionListener(this::actionBtnSearch);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);
        btnCancel.addActionListener(this::actionBtnCancel);

        createForm();

        addActionSupplementaire();

        eventActionRef();

        loadRequiredFields();
        FormParameters.add(Panel.toString() + this.getClass().getSimpleName(), this);
    }

    public ModelForm(String title, int width, int height, boolean hideActionMenuTitle, boolean confirmExit, Object... variablesInit) {

        super();
        init(variablesInit);

        Locale.initBundle();

        setSize(width, height);

        this.title = title;
        this.hideActionMenuTitle = hideActionMenuTitle;

        showTitle();

        this.wi = width;
        this.he = height;

        this.confirmExit = confirmExit;

        createList(false);

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnSearch.addActionListener(this::actionBtnSearch);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);
        btnCancel.addActionListener(this::actionBtnCancel);

        createForm();

        addActionSupplementaire();

        eventActionRef();

        loadRequiredFields();
        FormParameters.add(Panel.toString() + this.getClass().getSimpleName(), this);
    }
// </editor-fold>

// <editor-fold desc="Abstract Methods">
    /**
     *
     */
    protected abstract void addActionSupplementaire();

    protected abstract void createForm();

    protected abstract void eventActionRef();

// </editor-fold>
// <editor-fold desc="Void Methods">
    public void init(Object... var) {

        FormParameters.init();
    }

    public void addAction(Object input, String entity, Object[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {
        /**
         * TextFields
         */
        if (input.getClass().equals(JTextField.class)) {
            //((JTextField) input).setBackground(colorSearch);//68, 127, 255));
            setColor(input, imputsResult);
            ActionListener action = addActionEvent(input, entity, ((JTextField) input).getAccessibleContext().getAccessibleName(), parametresGrid, filter, args, imputsResult);
//            ((JTextField) input).removeActionListener(action);

            ActionListener[] axcc = ((JTextField) input).getActionListeners();
            for (ActionListener a : axcc) {
                ((JTextField) input).removeActionListener(a);
            }

            ((JTextField) input).addActionListener(action);
//                    (ActionEvent e) -> {
//                if (!((JTextField) input).getText().isEmpty()) {
//                    inputActionListener(e, input, entity, ((JTextField) input).getAccessibleContext().getAccessibleName(), parametresGrid, filter, args, imputsResult);
//                }else if(((JTextField) input).getText().isEmpty()){
//                    reset(imputsResult);
//                }
//            });

        } /**
         * ComboBox
         */
        else if (input.getClass().equals(JComboBox.class)) {
            ActionListener action = addActionEvent(input, entity, ((JComboBox) input).getAccessibleContext().getAccessibleName(), parametresGrid, filter, args, imputsResult);
            ((JComboBox) input).removeActionListener(action);

            ActionListener[] axcc = ((JComboBox) input).getActionListeners();
            for (ActionListener a : axcc) {
                ((JComboBox) input).removeActionListener(a);
            }

            ((JComboBox) input).addActionListener(action);
//                    (ActionEvent e) -> {
//                if (!((JComboBox) input).getSelectedItem().toString().isEmpty()) {
//                    inputActionListener(input, entity, ((JComboBox) input).getAccessibleContext().getAccessibleName(), parametresGrid, filter, args, imputsResult);
//                }else if(((JTextField) input).getText().isEmpty()){
//                    reset(imputsResult);
//                }
//            });
        } else {
            System.out.println("Nothing");
        }
    }

    public void addAction(Object input, String entity, String keyParam, Object[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {
        /**
         * TextFields
         */
        if (input.getClass().equals(JTextField.class)) {
//            ((JTextField) input).setBackground(colorSearch);//68, 127, 255));
            setColor(input, imputsResult);
            ActionListener action = addActionEvent(input, entity, keyParam, parametresGrid, filter, args, imputsResult);
//            ((JTextField) input).removeActionListener(action);

            ActionListener[] axcc = ((JTextField) input).getActionListeners();
            for (ActionListener a : axcc) {
                ((JTextField) input).removeActionListener(a);
            }

            ((JTextField) input).addActionListener(action);
//                    (ActionEvent e) -> {
//                if (!((JTextField) input).getText().isEmpty()) {
//                    inputActionListener(e, input, entity, keyParam, parametresGrid, filter, args, imputsResult);
//                }else if(((JTextField) input).getText().isEmpty()){
//                    reset(imputsResult);
//                }
//            });

        } /**
         * ComboBox
         */
        else if (input.getClass().equals(JComboBox.class)) {
            ActionListener action = addActionEvent(input, entity, keyParam, parametresGrid, filter, args, imputsResult);
            ((JComboBox) input).removeActionListener(action);

            ActionListener[] axcc = ((JComboBox) input).getActionListeners();
            for (ActionListener a : axcc) {
                ((JComboBox) input).removeActionListener(a);
            }
            ((JComboBox) input).addActionListener(action);
//                    (ActionEvent e) -> {
//                if (!((JComboBox) input).getSelectedItem().toString().isEmpty()) {
//                    inputActionListener(e, input, entity, keyParam, parametresGrid, filter, args, imputsResult);
//                }else if(((JTextField) input).getText().isEmpty()){
//                    reset(imputsResult);
//                }
//            });
        } else {
            System.out.println("Nothing");
        }
    }

    private ActionListener addActionEvent(Object input, String entity, String keyParam, Object[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {
        return (ActionEvent e) -> {
            /**
             * TextFields
             */
            if (input.getClass().equals(JTextField.class)) {
                //            ((JTextField) input).setBackground(colorSearch);//68, 127, 255));
//                setColor(input, imputsResult);
                if (!((JTextField) input).getText().isEmpty()) {
                    inputActionListener(input, entity, keyParam, parametresGrid, filter, args, imputsResult);
                } else if (((JTextField) input).getText().isEmpty()) {
                    reset(imputsResult);
                    addActionSecondaire(imputsResult);
                }
            } /**
             * ComboBox
             */
            else if (input.getClass().equals(JComboBox.class)) {
                if (!((JComboBox) input).getSelectedItem().toString().isEmpty()) {
                    inputActionListener(input, entity, keyParam, parametresGrid, filter, args, imputsResult);
                } else if (((JTextField) input).getText().isEmpty()) {
                    reset(imputsResult);
                    addActionSecondaire(imputsResult);
                }
            } else {
                System.out.println("Nothing");
            }
        };
    }

    private void inputActionListener(Object input, String entity, String keyParam, Object[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {
        if (etatAction != CREATE || (etatAction == CREATE && !input.equals(getRef()))) {

            Loading.show(btnValider, new Executable<List<HashMap>>() {
                List<HashMap> modelComplet;

                @Override
                public void execute() throws Exception {

                    modelComplet = getListModelForSelect(input, entity, parametresGrid, filter, args);

                    searchForm = new SearchForm(entity, keyParam, modelComplet, parametresGrid, imputsResult);
                    
//                    if(modelComplet.size()==1) searchForm.setResult(modelComplet.get(0));
//                    else 
                        searchForm.setVisible(true);
                    
                    if (etatAction != CREATE && input.equals(getRef())) {
//                        try {
                        FormParameters.add(Model, searchForm.getResult());
                        getModelData(searchForm.getResult());

//                        } catch (Exception e) {
//                            throw e;
//                        }
                    }
                }

                @Override
                public List<HashMap> success() {
                    addActionSecondaire(imputsResult);
//                    if (imputsResult.length > 1) {
//                        if (imputsResult[1].getClass() == JTextField.class) {
//                            ((JTextField) imputsResult[1]).postActionEvent();
//                        } else if (imputsResult[1].getClass() == JFormattedTextField.class) {
//                            ((JFormattedTextField) imputsResult[1]).postActionEvent();
//                        } else if (imputsResult[1].getClass() == JComboBox.class) {
//                            ((JComboBox) imputsResult[1]).postEvent(new Event(imputsResult, Event.ENTER, null));//actionPerformed(new ActionEvent(imputsResult, 0, ((JComboBox) imputsResult[1]).getActionCommand()));
//                        }
//                    }

                    return modelComplet;
                }

                @Override
                public void error(Exception ex) {
                    MessageForm.showsError(ex.getMessage(), "Message", false, null);
                }

            });
        }

    }

    private void addActionSecondaire(Object... imputsResult) {
        if (imputsResult.length > 1) {
            if (imputsResult[1].getClass() == JTextField.class) {
                ((JTextField) imputsResult[1]).postActionEvent();
            } else if (imputsResult[1].getClass() == JFormattedTextField.class) {
                ((JFormattedTextField) imputsResult[1]).postActionEvent();
            } else if (imputsResult[1].getClass() == JComboBox.class) {
                ((JComboBox) imputsResult[1]).postEvent(new Event(imputsResult, Event.ENTER, null));//actionPerformed(new ActionEvent(imputsResult, 0, ((JComboBox) imputsResult[1]).getActionCommand()));
            }
        }

    }

    public void addFormData(String key, Object value) {
        this.formDatas.putIfAbsent(key, value);
    }

    public void fieldSearch(String key, Object value) {
        this.fieldSearch.putIfAbsent(key, value);
    }

    public void showTitle() {
        setTitle(stateMenuLabel(title, hideActionMenuTitle));
    }

    public void showActionBar() {

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
        btnSearch = new javax.swing.JToggleButton();
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

        btnActionMenuGroup.add(btnSearch);
        btnSearch.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnSearch.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_BtnSearch)); // NOI18N
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setMargin(new java.awt.Insets(10, 0, 10, 0));
        btnSearch.setMaximumSize(new java.awt.Dimension(100, 40));
        btnSearch.setMinimumSize(new java.awt.Dimension(100, 40));
        btnSearch.setPreferredSize(new java.awt.Dimension(100, 40));
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.putClientProperty("JButton.buttonType", "segmented");
        actionBar.add(btnSearch);

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
    }

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

    public void actionBtnSearch(java.awt.event.ActionEvent evt) {
        etatAction = Default;
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
        if (requiredFieldsValidation()) {

            Loading.show(btnValider, new Executable<HashMap>() {
                @Override
                public void execute() throws Exception {
                    if (etatAction == Default) {
                        reset();
                        doClose(RET_CANCEL);
                    } else {
                        if (createList) {

                        } else {
                            if (etatAction == CREATE) {
                                modelFinal = new HashMap<>();
                            } else {
                                modelFinal = (HashMap) FormParameters.get(Model);
                            }

                            makeModelData();
                            getAdministrationService().crud(entity, modelFinal, etatAction, etatActionList, listModelCreateUpdate, listModelDelete);
//                            cbManager.crud(entity, modelFinal, etatAction, etatActionList, listModelCreateUpdate, listModelDelete);
                        }
                    }
                }

                @Override
                public HashMap success() {
                    if (etatAction != Default) {
                        MessageForm.shows(Dictionnaire.get(EnumLibelles.Business_Libelle_Message_Sucess.toString()), "Message", false, null);

                        reset();
                    }

                    return modelFinal;
                }

                @Override
                public void error(Exception ex) {
                    MessageForm.showsError(CodeBrainException.analyse(ex.getCause()).getMessage(), "Message", false, null);
                }
            });
        }
    }

    public void setActionModel(List<HashMap> modelCreateUpdate, List<HashMap> modelDelete) {
        this.listModelCreateUpdate = modelCreateUpdate;
        this.listModelDelete = modelDelete;
    }

    public void actionBtnReset(java.awt.event.ActionEvent evt) {
        reset();
    }

    public void setAction(EnumVariable action) {
        this.etatActionList = action;
    }

//    public void makeModelDatas() {
    /*
        modelFinal = new HashMap<>();
        makeModelData();

        model.keySet().stream().map((ky) -> {
            this.key = ky.toString();
            return ky;
        }).forEachOrdered((Object val) -> {
            modelFinal.putIfAbsent(this.key.toString(), model.get(val));
        });
     */
//    }
    public void makeModelData() {
//        if (!createList) {
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
            if (((JPasswordField) val).getText().isEmpty()) {
                modelFinal.put(this.key.toString(), FormParameters.get(this.key.toString()));
            } else {
                String valueMD5 = getAdministrationService().MD5(((JPasswordField) val).getText());
                modelFinal.put(this.key.toString(), valueMD5);//cbManager.MD5(((JPasswordField) val).getText()));
            }
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
            modelFinal.put(this.key.toString(), ((JFormattedTextField) val).getValue());
        });

//        }
    }

    public void getModelData(HashMap model) {
        /**
         * JTextField.class
         */
        fieldSearch.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (fieldSearch.get(ky).getClass() == JTextField.class)).map((ky) -> (JTextField) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
            try {
                ((JTextField) val).setText(Dictionnaire.get(getValueModelFromKey(this.key.toString(), ((JTextField) val).getAccessibleContext().getAccessibleName(), model).toString()));
            } catch (Exception e) {
            }
        });
        /**
         * JPasswordField.class
         */
        fieldSearch.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (fieldSearch.get(ky).getClass() == JPasswordField.class)).map((ky) -> (JPasswordField) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
            try {
                getValueModelFromKey(this.key.toString(), ((JPasswordField) val).getAccessibleContext().getAccessibleName(), model);
                ((JPasswordField) val).setText(null);
            } catch (Exception e) {
            }
        });
        /**
         * JTextPane.class
         */
        fieldSearch.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (fieldSearch.get(ky).getClass() == JTextPane.class)).map((ky) -> (JTextPane) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
            try {
                ((JTextPane) val).setText(model.get(this.key.toString()).toString());
            } catch (Exception e) {
            }
        });
        /**
         * ButtonGroup.class
         */
        fieldSearch.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (fieldSearch.get(ky).getClass() == ButtonGroup.class)).map((ky) -> (ButtonGroup) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
            try {
                Enumeration<AbstractButton> buttons = ((ButtonGroup) val).getElements();
                while (buttons.hasMoreElements()) {
                    JRadioButton rdButton = (JRadioButton) buttons.nextElement();
                    if (((JRadioButton) rdButton).getActionCommand() == null ? model.get(this.key.toString()).toString() == null : ((JRadioButton) rdButton).getActionCommand().equals(model.get(this.key.toString()).toString())) {
                        ((JRadioButton) rdButton).setSelected(true);
                    }
                }
            } catch (Exception e) {
            }
        });
        /**
         * JFormattedTextField.class
         */
        fieldSearch.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (fieldSearch.get(ky).getClass() == JFormattedTextField.class)).map((ky) -> (JFormattedTextField) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
            try {
//                String strTime = new SimpleDateFormat("").format(model.get(this.key.toString()));
                ((JFormattedTextField) val).setValue(getValueModelFromKey(this.key.toString(), ((JFormattedTextField) val).getAccessibleContext().getAccessibleName(), model));//model.get(this.key.toString()));
            } catch (Exception e) {
            }
        });
        /**
         * JComboBox.class
         */
        fieldSearch.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (fieldSearch.get(ky).getClass() == JComboBox.class)).map((ky) -> (JComboBox) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
            try {
//                String strTime = new SimpleDateFormat("").format(model.get(this.key.toString()));
//                ((JComboBox) val).setValue();//model.get(this.key.toString()));
                
                Object valeur = getValueModelFromKey(this.key.toString(), ((JComboBox) val).getAccessibleContext().getAccessibleName(), model);
                
                int nber = ((JComboBox) val).getItemCount();
                
                for(int i=0; i<nber ; i++){
                    if(Dictionnaire.get(valeur).equals(((JComboBox) val).getItemAt(i))){
                        ((JComboBox) val).setSelectedIndex(i);
                    }
                }
//                    if (listModelsOriginal.get(0).get("statut").equals(EnumStatus.Business_Statut_NonActif.toString())) {
//                        statusInput.setSelectedIndex(0);
//                    } else {
//                        statusInput.setSelectedIndex(1);
//                    }
            } catch (Exception e) {
            }
        });
    }

    public void getModelData(HashMap model, Object... inputs) {

        for (Object input : inputs) {
            /**
             * JTextField.class
             */
            if (input.getClass() == JTextField.class) {
//                ((JTextField) input).setText(Dictionnaire.get(getValueModelFromKey(this.key.toString(), model).toString()));

                fieldSearch.keySet().stream().map((ky) -> {
                    this.key = ky;
                    return ky;
                }).filter((ky) -> (fieldSearch.get(ky) == input)).map((ky) -> (JTextField) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
                    try {
                        ((JTextField) input).setText(Dictionnaire.get(getValueModelFromKey(this.key.toString(), ((JTextField) input).getAccessibleContext().getAccessibleName(), model).toString()));
                    } catch (Exception e) {
                    }
                });
            }
        }
    }

    
    public void getDataFromModel(HashMap model, Object... inputs) {

        for (Object input : inputs) {
            /**
             * JTextField.class
             */
            if (input.getClass() == JTextField.class) {
//                ((JTextField) input).setText(Dictionnaire.get(getValueModelFromKey(this.key.toString(), model).toString()));

//                fieldSearch.keySet().stream().map((ky) -> {
//                    this.key = ky;
//                    return ky;
//                }).filter((ky) -> (fieldSearch.get(ky) == input)).map((ky) -> (JTextField) fieldSearch.get(ky)).forEachOrdered((Object val) -> {
                try {
                    ((JTextField) input).setText(Dictionnaire.get(getValueModelFromKey(((JTextField) input).getName(), ((JTextField) input).getAccessibleContext().getAccessibleName(), model).toString()));
                } catch (Exception e) {
                }
//                });
            }else if(input.getClass() == JFormattedTextField.class){
                ((JFormattedTextField) input).setValue(getValueModelFromKey(((JFormattedTextField) input).getName(), ((JFormattedTextField) input).getAccessibleContext().getAccessibleName(), model));
            }
        }
    }
    
    private void actionBtnCancel(java.awt.event.ActionEvent evt) {
        if (confirmExit) {

            MessageForm.shows(Dictionnaire.get(EnumLibelles.Business_ConfirmExit), "Message", true, new Action() {
                @Override
                public void Ok() {
                    doClose(RET_CANCEL);
                    dispose();
                }

                @Override
                public void Cancel() {
                }
            });
        } else {
            doClose(RET_CANCEL);
            dispose();
        }
    }

    protected void doClose(int retStatus) {
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
                btnSearch.setSelected(true);
                setActionRef();
                break;
        }
    }

    public void setActionRef() {
        this.inputRef.setBackground(colorRef);//new Color(200, 255, 255));//68, 127, 255));
        this.inputRef.requestFocus();
    }

    public void setDefaultActionRef() {
        this.inputRef.setBackground(colorDefault);//new Color(255, 255, 255));
        this.inputRef.setFocusable(true);
    }

    public void setRef(JTextField inputRef) {
        this.inputRef = inputRef;
    }

    public void keyTypeEvent(KeyEvent ev) {
        JTextField obj = (JTextField) ev.getSource();

        if (obj.getText().equals("")) {
            obj.setText(obj.getAccessibleContext().getAccessibleName());
//        }else{
//            obj.setText(null);
        }
    }

    public void loadRequiredFields() {
        fieldsRequired.forEach((field) -> {
            if (field.getClass() == JTextField.class) {
                ((JTextField) field).setToolTipText(Dictionnaire.get(EnumLibelles.Business_Libelle_RequiredField));
            } else if (field.getClass() == JTextPane.class) {
                ((JTextPane) field).setToolTipText(Dictionnaire.get(EnumLibelles.Business_Libelle_RequiredField));
            } else if (field.getClass() == JFormattedTextField.class) {
                ((JFormattedTextField) field).setToolTipText(Dictionnaire.get(EnumLibelles.Business_Libelle_RequiredField));
            }
        });
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

        FormParameters.reset();

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
                    ((JFormattedTextField) component.get(Value)).setValue(null);
                    /**
                     * JTable.class
                     */
                } else if (component.get(Type) == JTable.class) {
                    try {
                        (((DefaultTableModel) ((JTable) component.get(Value)).getModel())).setRowCount(0);
                        ((JTable) component.get(Value)).repaint();
                    } catch (Exception e) {
                    }
                } else {
//                    System.out.println(component.get(Type).toString() + " : Type non géré.");
                }
            });
        }
    }

    public void reset(Object... components) {
        if (components != null || components.length > 0) {
            for (Object component : components) {

                this.key = formDatas.get(component);

                if (this.key != null) {
                    FormParameters.remove(this.key);
                }

                resetModel(component);

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
                    ((JFormattedTextField) component).setValue(null);
                } else if (component.getClass() == JTable.class) {
                    ((DefaultTableModel) ((JTable) component).getModel()).setRowCount(0);
                } else {
                    System.out.println(component.toString() + " : Type non géré.");
                }
            }
        }
    }

    public void resetModel(Object component) {
        if (component.getClass() == JTextField.class) {
            String name = ((JTextField) component).getAccessibleContext().getAccessibleName();
            if (name != null) {
//                String[] indentKey = name.split("->");

//                if (indentKey.length > 2) {
//                    for (int i = 1; i <= indentKey.length; i = i + 3) {
//                        FormParameters.remove(indentKey[indentKey.length - 2]);
//                    }
//                } else {
                    FormParameters.remove(name);
//                    FormParameters.remove(indentKey[indentKey.length - 1]);
//                }
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

    public void createList(boolean state) {
        this.createList = state;

//        if (this.createList) {
        tableModelObject = new TableModelObject(this.formDatas);
//        } else {
//            tableModelObject = null;
//        }
    }

    public void setTableModel(Object... inputs) {
        tableModelObject.setModel(inputs);
    }

    protected void setSelectedCheckbox(JTable table, boolean selectedCheckbox) {
        JCheckBox cb = new JCheckBox();
        cb.setHorizontalAlignment(SwingConstants.CENTER);
        cb.putClientProperty("JComponent.sizeVariant", "small");
        table.setDefaultEditor(Boolean.class, new DefaultCellEditor(cb));
    }

// </editor-fold>
// <editor-fold desc="Methods Return Values">
    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    public List<HashMap> getListModelForSelect(Object input, String entity, Object[][] parametresGrid, String clause, HashMap[] args) throws ClassNotFoundException, Exception {
        String filtre = getValueInputObject(input);
        ArrayList parameterArgs = new ArrayList();

        if (args != null) {
            for (HashMap arg : args) {
                if (arg.get(Type).equals(Entity)) {
                    String tmpEntity = arg.get(Entity).toString();
                    HashMap data = (HashMap) FormParameters.get(arg.get(Model).toString());

                    Object dataObject = getAdministrationService().convertToObject(data, tmpEntity);
                    parameterArgs.add(dataObject);
                } else {
                    parameterArgs.add(arg.get(Value));
                }
            }
        }
        modelResult = getAdministrationService().getListEntity(entity, clause, parameterArgs.toArray());

        List<HashMap> modelComplet = getAdministrationService().convertToListObject(modelResult, HashMap.class);

        if (filtre != null && !filtre.equals("*")) {
            modelComplet = modelComplet.stream().map((mapper) -> {
                return mapper;
            }).filter(((model) -> {
                Boolean find = false;
                for (Object[] keys : parametresGrid) {
                    if (model.get(keys[0]).toString().toLowerCase().contains(filtre.toLowerCase())) {
                        find = true;
                        break;
                    }
                }
                return find;
            })).collect(Collectors.toList());
        }
        if (modelComplet.isEmpty()) {
            throw new Exception(Dictionnaire.get(EnumError.Business_Libelle_No_Result_Found.toString()));
        }

        return modelComplet;
    }

    private String getValueInputObject(Object input) {
        String filter = null;
        if (input != null) /**
         * TextFields
         */
        {
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
        }

        return filter;
    }

    public Object getData(String key) {
        return this.formDatas.get(key);
    }

    public Object getFieldSearch(String key) {
        return this.fieldSearch.get(key);
    }

    public JTextField getRef() {
        return this.inputRef;
    }

    /*
    public void setRadiBottun(ButtonGroup group) {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (AbstractButton rdBtn : group.getElements().nextElement()) {
                    if (rdBtn.isSelected() && rdBtn == group.getSelection()) {
                        group.setSelected(rdBtn.getModel(), true);
                        break;
                    }
                }
            }
        };
    }
     */
//    protected void eventActionRef(){
//
//    }
    private String stateMenuLabel(String title, boolean hideActionMenuTitle) {
        if (null != etatAction && hideActionMenuTitle) {
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

        if (indentKey.length > 2) {
            for (int i = 1; i <= indentKey.length; i = i + 3) {
                Object object = modelTmp.get(indentKey[i]);

                if (object.getClass().equals(HashMap.class) || object.getClass().equals(LinkedHashMap.class)) {

                    FormParameters.add(indentKey[indentKey.length - 2], object);

                    modelTmp = (HashMap) object;
                    value = modelTmp.get(indentKey[i + 1]);
                } else if (object.getClass().equals(String.class)) {
                    Object objectConverted = null;
                    try {
                        objectConverted = getAdministrationService().convertToObject(modelTmp, indentKey[i - 1]);
                    } catch (ClassNotFoundException ex) {
                    }

                    if (objectConverted != null) {
                        Object objectReslut = getAdministrationService().getObjectInvoke(objectConverted, indentKey[i - 1], indentKey[i]);

                        if (objectReslut != null) {

                            object = getAdministrationService().convertToObject(objectReslut, HashMap.class);

                            FormParameters.add(indentKey[indentKey.length - 2], object);

                            modelTmp = (HashMap) object;

                            value = modelTmp.get(indentKey[i + 1]);

                        } else {
                            FormParameters.remove(indentKey[indentKey.length - 2]);
                            value = null;
                        }
                    } else {
                        value = null;
                    }
                } else {
                    FormParameters.add(indentKey[indentKey.length - 2], object);

                    value = modelTmp.get(indentKey[i]);
                }
            }
        } else {
            value = modelTmp.get(indentKey[indentKey.length - 1]);
            FormParameters.add(indentKey[indentKey.length - 1], value);
        }

        return value;
    }

    public Object getValueModelFromKey(String key, String keyUnik, HashMap model) {
        Object value = null;
        String[] indentKey = key.split("->");
        HashMap modelTmp = model;

        if (indentKey.length > 2) {
            for (int i = 1; i <= indentKey.length; i = i + 3) {
                Object object = modelTmp.get(indentKey[i]);

                if (object.getClass().equals(HashMap.class) || object.getClass().equals(LinkedHashMap.class)) {


                    if(keyUnik==null) FormParameters.add(indentKey[indentKey.length - 2], object);
                    else FormParameters.add(keyUnik, object);

                    modelTmp = (HashMap) object;
                    value = modelTmp.get(indentKey[i + 1]);
                } else if (object.getClass().equals(String.class)) {
                    Object objectConverted = null;
                    try {
                        objectConverted = getAdministrationService().convertToObject(modelTmp, indentKey[i - 1]);
                    } catch (ClassNotFoundException ex) {
                    }

                    if (objectConverted != null) {
                        Object objectReslut = getAdministrationService().getObjectInvoke(objectConverted, indentKey[i - 1], indentKey[i]);

                        if (objectReslut != null) {

                            object = getAdministrationService().convertToObject(objectReslut, HashMap.class);

                            if(keyUnik==null) FormParameters.add(indentKey[indentKey.length - 2], object);
                            else FormParameters.add(keyUnik, object);


                            modelTmp = (HashMap) object;

                            value = modelTmp.get(indentKey[i + 1]);

                        } else {
                            if(keyUnik==null) FormParameters.remove(indentKey[indentKey.length - 2]);
                            else FormParameters.remove(keyUnik);
                            value = null;
                        }
                    } else {
                        value = null;
                    }
                } else {
//                    if(keyUnik==null) 
                    FormParameters.add(indentKey[indentKey.length - 2], object);
//                    else FormParameters.add(keyUnik, object);

                    value = modelTmp.get(indentKey[i]);
                }
            }
        } else {
            value = modelTmp.get(indentKey[indentKey.length - 1]);
            if(keyUnik==null) FormParameters.add(indentKey[indentKey.length - 1], value);
            else FormParameters.add(keyUnik, value);
        }

        return value;
    }

    public DefaultTableModel setModelDataTable(List<Class> classArray, String... columnsName) {
//        List<Class> classArray = new ArrayList<>();
//        for (Object c : typeArray) {
//            if (c == null) {
//                classArray.add(java.lang.Object.class);
//            } else {
//                if (c instanceof Boolean) {
//                    classArray.add(java.lang.Boolean.class);
//                } else {
//                    classArray.add(java.lang.Object.class);
//                }
//            }
//        }

        this.tableModel = new javax.swing.table.DefaultTableModel(
                null,
                columnsName
        ) {
            public Class getColumnClass(int columnIndex) {
                return (classArray.get(columnIndex) == null) ? Object.class : classArray.get(columnIndex);
            }
        };

        return this.tableModel;
    }

    public DefaultTableModel setModelDataTable(Object[]... parameters) {

        Class[] columnClasses = new Class[parameters.length];
        String[] columnNames = new String[parameters.length];
//        int[] columnSizes = new int[parameters.length];
        boolean[] columnEditables = new boolean[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            columnNames[i] = (String) parameters[i][0];
            columnClasses[i] = (Class) parameters[i][1];
//            columnSizes[i] = (int) parameters[i][2];
            columnEditables[i] = (boolean) parameters[i][2];
        }

        this.tableModel = new DefaultTableModel() {
            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClasses[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnEditables[columnIndex];
            }

            @Override
            public Object getValueAt(int row, int column) {
                return super.getValueAt(row, column);
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                super.setValueAt(aValue, rowIndex, columnIndex);
            }
        };

        return this.tableModel;

    }

    public DefaultTableModel setModelDataTable(String... columnsName) {

        this.tableModel = new DefaultTableModel() {
            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public int getColumnCount() {
                return columnsName.length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnsName[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return super.getValueAt(row, column);
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                super.setValueAt(aValue, rowIndex, columnIndex);
            }
        };

        return tableModel;
    }

    protected void resizeTableColumnModel(JTable table, int... size) {
        final TableColumnModel colmunModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            colmunModel.getColumn(column).setPreferredWidth(size[column]);
        }
    }

    public DefaultComboBoxModel setModelDataComboBox(Object... rowValue) {
        return new DefaultComboBoxModel(rowValue);
    }

    public DefaultComboBoxModel setModelDataComboBox(List<HashMap> listModel, String keyParam) {
        List<Object> obj = new ArrayList<>();
        listModel.forEach((model) -> {
            obj.add(Dictionnaire.get(getValueModelFromKey(keyParam, model)));
        });

        return setModelDataComboBox(obj.toArray());
    }

    public boolean requiredFieldsValidation() {
        boolean valid = false;
        if (fieldsRequired.size() > 0) {
            for (Object field : fieldsRequired) {
                if (field.getClass() == JTextField.class) {
                    this.key = null;
                    formDatas.keySet().stream().map((ky) -> {
                        this.key = ky;
                        return ky;
                    }).filter((ky) -> (formDatas.get(ky) == field));

                    if (this.key != null) {
                        valid = !(FormParameters.get(this.key) != null);
                        break;
                    } else {
                        valid = !((JTextField) field).getText().isEmpty();
                    }

                } else if (field.getClass() == JTextPane.class) {
                    valid = !((JTextPane) field).getText().isEmpty();
                } else if (field.getClass() == JFormattedTextField.class) {
                    valid = !((JFormattedTextField) field).getText().isEmpty();
                } else  if (field.getClass() == JPasswordField.class) {
                    valid = !(FormParameters.get(((JFormattedTextField) field).getAccessibleContext().getAccessibleName())==null);
                } else {
                    valid = true;
                }

                if (!valid) {
                    MessageForm.showsError(Dictionnaire.get(EnumError.Business_Error_find_required), "Message", false, null);
                    break;
                }
            }
        } else {
            valid = true;
        }
        return valid;
    }

    protected CodeBrainServiceAsync getAdministrationService() {
        CodeBrainServiceAsync svc = null;
        try {
            svc = CodeBrainServiceAsync.class.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
        return svc;
    }
// </editor-fold>

    protected void setColor(Object input, Object... imputsResult) {
//        ((JTextField) input).setBackground(colorSearch);//68, 127, 255));
        for (Object obj : imputsResult) {
            if (obj.equals(input)) {
                setColor(colorSearch, true, input);
            } else {
                setColor(colorResult, false, obj);
            }
        }
    }

    private void setColor(Color color, boolean editable, Object input) {
        if (input.getClass() == JTextField.class) {
            if (editable || (!editable && !((JTextField) input).isEditable())) {
                ((JTextField) input).setBackground(color);
            }
        } else if (input.getClass() == JFormattedTextField.class) {
            if (editable || (!editable && !((JTextField) input).isEditable())) {
                ((JFormattedTextField) input).setBackground(color);
            }
        }
    }

    private void setColor(Color color, Object input0, Object... inputs) {
        for (Object input : inputs) {
            if (!input0.equals(input)) {
                if (input.getClass() == JTextField.class) {
                    ((JTextField) input).setBackground(color);
                } else if (input.getClass() == JFormattedTextField.class) {
                    ((JFormattedTextField) input).setBackground(color);
                }
            }
        }
    }
}
