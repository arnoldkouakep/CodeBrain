/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import cm.codebrain.main.business.controller.CodeBrainManager;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.Locale;
import cm.codebrain.ui.application.enumerations.EnumError;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.enumerations.Enums;
import static cm.codebrain.ui.application.enumerations.Enums.CREATE;
import static cm.codebrain.ui.application.enumerations.Enums.DELETE;
import static cm.codebrain.ui.application.enumerations.Enums.DUPPLICATE;
import static cm.codebrain.ui.application.enumerations.Enums.MODIF;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

    public int etatAction = 1;

    private HashMap formDatas = new HashMap();

    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private final int width = 18;
    private final int height = 18;
    private int wi = 394;
    private int he = 390;
    private Object inputRef;
    public String entity;

    CodeBrainManager cbManager = new CodeBrainManager();
    private InputSearchForm serachForm;

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

    public void addAction(Object input) {
        /**
         * TextFields
         */
        if (input.getClass().equals(JTextField.class)) {
            ((JTextField) input).addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    levelCodeInputFocusLost(evt);
                }
            });
        } 
        /**
         * ComboBox
         */
        else if (input.getClass().equals(JComboBox.class)) {
            ((JComboBox) input).addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    levelCodeInputFocusLost(evt);
                }
            });
        }
        else{
            System.out.println("Nothing");
        }

    }

    private void addAction(Object field, String entity, String[] fields, String clause, List args, Object... imputsResult) {

    }

    private void levelCodeInputFocusLost(java.awt.event.FocusEvent evt) {
        System.out.println(evt.getClass());
        serachForm = new InputSearchForm();
        serachForm.setVisible(true);
    }

    public void addFormData(String key, Object value) {
        this.formDatas.putIfAbsent(key, value);
    }

    public Object getData(String key) {
        return this.formDatas.get(key);
    }

    public void setImputRef(Object input) {
        this.inputRef = input;
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

        btnValider.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/accept.png")).getImage().getScaledInstance(width, height, 0)));
        btnValider.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_valider)); // NOI18N
        btnValider.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        panelButtons.add(btnValider);
        getRootPane().setDefaultButton(btnValider);

        cancelButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/cancel.png")).getImage().getScaledInstance(width, height, 0)));
        cancelButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Annuler)); // NOI18N
        cancelButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cancelButton.addActionListener(this::actionBtnCancel);
        panelButtons.add(cancelButton);

        bottonPanel.add(panelButtons, java.awt.BorderLayout.EAST);

        getContentPane().add(bottonPanel, java.awt.BorderLayout.SOUTH);

        menuBar.setFloatable(false);
        menuBar.setRollover(true);
        menuBar.putClientProperty("Quaqua.ToolBar.style", "bottom");

        btnNew.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/add.png")).getImage().getScaledInstance(width, height, 0)));
        btnNew.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Nouveau)); // NOI18N
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuBar.add(btnNew);

        btnClear.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/effacer.png")).getImage().getScaledInstance(width, height, 0)));
        btnClear.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Effacer)); // NOI18N
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuBar.add(btnClear);

        btnPrint.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/printer.png")).getImage().getScaledInstance(width, height, 0)));
        btnPrint.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Print)); // NOI18N
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        menuBar.add(btnPrint);

        btnCancel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/cancel.png")).getImage().getScaledInstance(width, height, 0)));
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
    }

    public void actionBtnModify(java.awt.event.ActionEvent evt) {
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnCreate), "Bonjour", JOptionPane.INFORMATION_MESSAGE);
        setActionMenu(MODIF);
    }

    public void actionBtnDupplicate(java.awt.event.ActionEvent evt) {
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnCreate), "Bonjour", JOptionPane.INFORMATION_MESSAGE);
        setActionMenu(DUPPLICATE);
    }

    public void actionBtnDelete(java.awt.event.ActionEvent evt) {
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(btnCreate), "Bonjour", JOptionPane.INFORMATION_MESSAGE);
        setActionMenu(DELETE);
    }

    public void actionBtnValider(java.awt.event.ActionEvent evt) {

        new Loading(btnValider, new Executable() {

            @Override
            public void execute() throws Exception {

                cbManager.createEntity(entity, formDatas);

                doClose(RET_OK);
            }

            @Override
            public void error(Exception ex) {
//                    System.out.println("Error : "+ex.getLocalizedMessage());
                JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(btnValider), Dictionnaire.get(EnumError.BusinessLibelleError) + ": " + ex.getLocalizedMessage(), "Message", JOptionPane.ERROR_MESSAGE);
            }

        });

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
        } else//        this.etatAction = Enums.CREATE;
        {
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

    public void setActionRef(Object fieldActionRef) {
        this.inputRef = fieldActionRef;
    }

    public void addActionSupplementaire() {

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
