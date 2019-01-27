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
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.Enums;
import static cm.codebrain.ui.application.enumerations.Enums.CREATE;
import static cm.codebrain.ui.application.enumerations.Enums.DELETE;
import static cm.codebrain.ui.application.enumerations.Enums.DUPPLICATE;
import static cm.codebrain.ui.application.enumerations.Enums.Entity;
import static cm.codebrain.ui.application.enumerations.Enums.MODIF;
import static cm.codebrain.ui.application.enumerations.Enums.Type;
import static cm.codebrain.ui.application.enumerations.Enums.Value;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    public Enum etatAction = CREATE;

    private HashMap formDatas = new HashMap();

    protected HashMap<String, Object> modelFinal;
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
//    private List modelComplet;

//    private String filter;
    CodeBrainManager cbManager = new CodeBrainManager();
    private InputSearchForm serachForm;
    private Object key;
    private List<HashMap> allComponents;
    
    
    public final String VALIDATE="/images/accept.png";
    public final String ADD="/images/add.png";
    public final String DEL="/images/desactiver.png";
    public final String EDIT="/images/cancel.png";
    public final String PRINT="/images/printer.png";
    public final String CANCEL="/images/cancel.png";
    public final String LOAD="/images/load.png";
    public final String RESET="/images/grid.png";
    public final String CLEAR="/images/effacer.png";
    public final String EXIT="/images/exit.png";

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

        setTitle(title);

        initComponents();

        btnCreate.addActionListener(this::actionBtnCreate);
        btnModify.addActionListener(this::actionBtnModify);
        btnDupplicate.addActionListener(this::actionBtnDupplicate);
        btnDelete.addActionListener(this::actionBtnDelete);

        btnValider.addActionListener(this::actionBtnValider);

        btnNew.addActionListener(this::actionBtnReset);

    }

    public ModelForm(String title, int width, int height) {

        super();

        Locale.initBundle();

        setSize(width, height);

        setTitle(title);

        this.wi = width;
        this.he = height;

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
            ((JTextField) input).addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (!((JTextField) input).getText().isEmpty())
                        levelCodeInputFocusLost(evt, input, entity, parametresGrid, filter, args, imputsResult);
                }
            });
        } /**
         * ComboBox
         */
        else if (input.getClass().equals(JComboBox.class)) {
            ((JComboBox) input).addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (!((JComboBox) input).getSelectedItem().toString().isEmpty())
                        levelCodeInputFocusLost(evt, input, entity, parametresGrid, filter, args, imputsResult);
                }
            });
        } else {
            System.out.println("Nothing");
        }
    }

    private void levelCodeInputFocusLost(java.awt.event.FocusEvent evt, Object input, String entity, String[][] parametresGrid, String filter, HashMap[] args, Object... imputsResult) {

//        this.ejql = null;
        Loading.show(btnValider, new Executable<List<HashMap>>() {
            private Object key;
            private Boolean find;
            String filtre = getValueInputObject(input);

            @Override
            public List<HashMap> execute() throws Exception {

//                HashMap filterArg = new HashMap();
                ArrayList parameterArgs = new ArrayList();

                if (args != null) {

                    for (HashMap arg : args) {
                        if (arg.get(Type).equals(Entity)) {
//                            if (arg.get(value).getClass().equals(JTextField.class)) {
                            String entitie = arg.get(Entity).toString();
                            HashMap data = (HashMap) GlobalParameters.getVar(arg.get(Entity).toString());

                            Object dataObject = cbManager.convertToObject(data, entitie);
//                            filterArg.put(value, dataObject);//GlobalParameters.getVar(arg.get(Entity).toString()));
                            parameterArgs.add(dataObject);
//                            }
                        } else {
//                            filterArg.put(arg.get(clause), arg.get(value));
                            parameterArgs.add(arg.get(Value));
                        }
                    }
                }
//                modelResult = cbManager.getList(entity, filterArg);
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
                serachForm = new InputSearchForm(entity, modelComplet, parametresGrid, imputsResult);

                serachForm.setVisible(true);
                return modelComplet;
            }

            @Override
            public void error(Exception ex) {
                //System.out.println(ex);
                ex.printStackTrace();
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }

        });

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

    public void showActionBar() {
        getContentPane().add(actionBar, java.awt.BorderLayout.EAST);
    }

    public void showMenuBar() {
        getContentPane().add(menuBar, java.awt.BorderLayout.NORTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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

    public void actionBtnCreate(java.awt.event.ActionEvent evt) {
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnCreate), "Bonjour", JOptionPane.INFORMATION_MESSAGE);
        setActionMenu(CREATE);
        setDefaultActionRef();
    }

    public void actionBtnModify(java.awt.event.ActionEvent evt) {
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnCreate), "Bonjour", JOptionPane.INFORMATION_MESSAGE);
        setActionMenu(MODIF);
        setActionRef();
    }

    public void actionBtnDupplicate(java.awt.event.ActionEvent evt) {
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnCreate), "Bonjour", JOptionPane.INFORMATION_MESSAGE);
        setActionMenu(DUPPLICATE);
        setActionRef();
    }

    public void actionBtnDelete(java.awt.event.ActionEvent evt) {
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnCreate), "Bonjour", JOptionPane.INFORMATION_MESSAGE);
        setActionMenu(DELETE);
        setActionRef();
    }

    public void actionBtnValider(java.awt.event.ActionEvent evt) {

        Loading.show(btnValider, new Executable<HashMap>() {

            @Override
            public HashMap execute() throws Exception {
                makeModelData();
                if (etatAction == CREATE) {

                    cbManager.createEntity(entity, modelFinal);

                } else {

                    cbManager.updateEntity(entity, modelFinal);

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

    public void makeModelData() {

        modelFinal = new HashMap<>();

        modelFinal.put(entity.toLowerCase() + "Id", CodeBrainManager.generateUIDPrimaryKey());
        modelFinal.put("stateDb", EnumLibelles.Business_Status_StateDb_Create.toString());
        modelFinal.put("dtCreated", new Date());
        modelFinal.put("dtModified", new Date());
        modelFinal.put("userCreated", GlobalParameters.getVar("user"));
        modelFinal.put("userModified", GlobalParameters.getVar("user"));
//        modelFinal.put("levelsId", ((Users) GlobalParameters.getVar("user")).getLevelsId());

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

        GlobalParameters.addVar("model", modelFinal);
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

    public void setActionMenu(Enums etatAction) {
        if (null == etatAction) {
            btnCreate.setSelected(true);
            this.etatAction = CREATE;
        } else//        this.etatAction = Enums.CREATE;
        {
            this.etatAction = etatAction;
            switch (etatAction) {
                case CREATE:
                    btnCreate.setSelected(true);
                    break;
                case MODIF:
                    btnModify.setSelected(true);
                    break;
                case DUPPLICATE:
                    btnDupplicate.setSelected(true);
                    break;
                case DELETE:
                    btnDelete.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    }

    public void setActionRef() {
//        this.inputRef = fieldActionRef;
        this.inputRef.setBackground(new Color(223, 246, 238));//68, 127, 255));
        this.inputRef.requestFocus();
    }

    public void setDefaultActionRef() {
//        this.inputRef = fieldActionRef;
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
                    ((JTextField) component.get(Value)).setText("");
                    ((JTextField) component.get(Value)).setFocusable(true);
                    
                    /**
                     * JPasswordField.class
                     */
                } else if (component.get(Type) == JPasswordField.class) {
                    ((JPasswordField) component.get(Value)).setText("");
                    /**
                     * JTextPane.class
                     */
                } else if (component.get(Type) == JTextPane.class) {
                    ((JTextPane) component.get(Value)).setText("");
                    /**
                     * JFormattedTextField.class
                     */
                } else if (component.get(Type) == JFormattedTextField.class) {
                    ((JFormattedTextField) component.get(Value)).setText("");
                } else {
                    System.out.println(component.get(Type).toString() + " : Type non géré.");
                }
            });
        }
    }

    public void reset(Object... components) {
        if (components != null || components.length>0) {
            for(Object component : components){
                if (component.getClass() == JTextField.class) {
                    ((JTextField) component).setText("");
                    ((JTextField) component).setFocusable(true);

                    /**
                     * JPasswordField.class
                     */
                } else if (component.getClass() == JPasswordField.class) {
                    ((JPasswordField) component).setText("");
                    /**
                     * JTextPane.class
                     */
                } else if (component.getClass() == JTextPane.class) {
                    ((JTextPane) component).setText("");
                    /**
                     * JFormattedTextField.class
                     */
                } else if (component.getClass() == JFormattedTextField.class) {
                    ((JFormattedTextField) component).setText("");
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
            ((JTextField) formDatas.get(this.key.toString())).setText("");
        });
        /**
         * JPasswordField.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JPasswordField.class)).map((ky) -> (JPasswordField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JPasswordField) formDatas.get(this.key.toString())).setText("");
        });
        /**
         * JTextPane.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JTextPane.class)).map((ky) -> (JTextPane) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JTextPane) formDatas.get(this.key.toString())).setText("");
        });
        /**
         * JFormattedTextField.class
         */
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JFormattedTextField.class)).map((ky) -> (JFormattedTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            ((JFormattedTextField) formDatas.get(this.key.toString())).setText("");
        });

    }
    
    public void setRadiBottun(ButtonGroup group, JRadioButton... rdButtons){
       ActionListener action = (ActionEvent e) -> {
           
           for(JRadioButton rdBtn : rdButtons){
               if(rdBtn.isSelected() && rdBtn == group.getSelection()){
                   group.setSelected(rdBtn.getModel(), true);
                   break;
               }
           }
       };
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                masculinRadioButtonActionPerformed(evt);
//            }
//        });
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
