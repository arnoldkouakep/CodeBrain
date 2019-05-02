/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.security;

import cm.codebrain.main.business.controller.CodeBrainManager;
import cm.codebrain.main.business.entitie.Levels;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.ui.application.MessageForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.controller.Locale;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import static cm.codebrain.ui.application.enumerations.EnumVariable.User;
import cm.codebrain.ui.application.implement.Action;
import cm.codebrain.ui.application.menu.MenuHome;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;

/**
 *
 * @author KSA-INET
 */
public class MainForm extends JFrame{

    private final String logoTxtImg = "/images/logo.png";
    private final String logoImg = "/images/logo2.png";
    private final String EngImg = "/images/en_en.png";
    private final String FrImg = "/images/fr_fr.png";
    private final String logoutImg = "/images/deconnexion.png";
    private final String exitImg = "/images/exit.png";
    private final String homeImg = "/images/home.png";
    private final int width = 18;
    private final int height = 18;

    private final ImageIcon image = new ImageIcon(getClass().getResource(logoImg));
    private final CodeBrainManager codeBrainManager;
    private JLabel hourStatusLabel;
    private JLabel userStatusLabel;
    private Users userConnected;
    private Levels levelGroup;
    private JLabel levelLabel;
    private JButton languageButton;
    private JButton logoutButton;
    private JButton exitButton;
    private JPanel statusBarLeft;
    private JPanel statusBarRight;
    private JLabel copyrightLabel;
    private JLabel logoLabel;

    private static JToolBar statusBar;
    private static JToolBar menuBar;
    private static Timer timer;
    private JPanel mainPanel;

    public MainForm(CodeBrainManager codeBrainManager) {

        this.codeBrainManager = codeBrainManager;

        userConnected = (GlobalParameters.get(User) == null ? null : ((Users) GlobalParameters.get(User)));

        levelGroup = (userConnected == null) ? null : userConnected.getLevelsId();

        Locale.initBundle();
        initComponents();
    }

    private void initComponents() {
        /*
        *
        *Parameters Form
        *
         */
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(Dictionnaire.get(EnumLibelles.Business_Libelle_Title));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setName("mainFrame"); // NOI18N
        setIconImage(image.getImage());
        setLayout(new BorderLayout());
        
        setLocationRelativeTo(null);
        /*
        *
        *CENTER Panel Container
        *
         */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new javax.swing.OverlayLayout(centerPanel));

        /*
         *
         *Logo Panel
         *
         */
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new CardLayout());
        
        /*
         *
         *Logo Label
         *
         */
        logoLabel = new JLabel();
        logoLabel.setSize(444, 152);

        logoLabel.setIcon(new ImageIcon(Dictionnaire.getResource(logoTxtImg).getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));//new ImageIcon(getClass().getResource(logoTxtImg)).getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), 0)));
        logoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        logoPanel.add(logoLabel);
        
        /*
         *
         *Main Container Panel
         *
         */
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 100, 50));

        centerPanel.add(mainPanel);

        centerPanel.add(logoPanel);

        add(centerPanel, BorderLayout.CENTER);
        
        /*
        *
        *MenuBar
        *
         */
        menuBar = new JToolBar();
        menuBar.setFloatable(false);
        menuBar.setMargin(new Insets(3, 10, 3, 10));
        
        menuBar.putClientProperty("Quaqua.ToolBar.style", "bottom");

        add(menuBar, BorderLayout.NORTH);

        /*
        *
        *StatusBar
        *
         */
        statusBar = new JToolBar();
        statusBar.setFloatable(false);
        statusBar.setLayout(new BorderLayout(50, 50));
        statusBar.putClientProperty("Quaqua.ToolBar.style", "bottom");

        statusBar.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }

            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                statusBarAncestorAdded(evt);
            }

            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                statusBarAncestorRemoved(evt);
            }
        });

        statusBarLeft = new JPanel();
        statusBarRight = new JPanel();
        statusBarRight.setVisible(false);

        /*
        *
        *User
        *
         */
        userStatusLabel = new JLabel();
        userStatusLabel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Identifiant) + ": " + (userConnected == null ? "" : userConnected.getLogin()));
        userStatusLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        statusBarLeft.add(userStatusLabel);

        /*
        *
        *Level
        *
         */
        levelLabel = new JLabel();
        levelLabel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Group) + ": " + (levelGroup == null ? "" : levelGroup.getIntitule()));
        levelLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
//        levelLabel.setSize(levelLabel.getHeight(), 100);
        statusBarLeft.add(levelLabel);

        /**
         * Heure
         */
        hourStatusLabel = new JLabel();
        hourStatusLabel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Hour));
        hourStatusLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        statusBarLeft.add(hourStatusLabel);

        /**
         * Copyright
         */
        copyrightLabel = new JLabel();
        copyrightLabel.setText(Dictionnaire.get(EnumLibelles.Business_Copyright_Author));
        copyrightLabel.setFont(new java.awt.Font("sansserif", 3, 8));
        copyrightLabel.setEnabled(false);
        statusBarLeft.add(copyrightLabel);
        
        /*
        *
        *Footer Menu : Language
        * 
         */
        languageButton = new JButton();
        languageButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Language));
//        languageButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
//        languageButton.setSize(width, height);
        languageButton.setIcon(
                (Locale.LANGUAGE == 0)
                        ? new ImageIcon(new ImageIcon(getClass().getResource(EngImg)).getImage().getScaledInstance(width, height, 0))
                        : new ImageIcon(new ImageIcon(getClass().getResource(FrImg)).getImage().getScaledInstance(width, height, 0))
        );
        languageButton.putClientProperty("JButton.buttonType", "bevel");
        languageButton.addActionListener((ActionEvent e) -> {
            if (Locale.LANGUAGE == 0) {
                Locale.LANGUAGE = 1;
            } else if (Locale.LANGUAGE == 1) {
                Locale.LANGUAGE = 0;
            }
            /*
            *Restart Application
            *Lock user Identification
            */
            codeBrainManager.restart();
        });
        statusBarRight.add(languageButton);

        /*
        *
        *Footer Menu : Deconnexion
        * 
         */
        logoutButton = new JButton();
        logoutButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Logout));
        logoutButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(logoutImg)).getImage().getScaledInstance(width, height, 0)));        
        logoutButton.putClientProperty("JButton.buttonType", "bevel");
        logoutButton.addActionListener((ActionEvent e) -> {
            codeBrainManager.logout();
        });
        statusBarRight.add(logoutButton);

        /*
        *
        *Footer Menu : Exit
        * 
         */
        exitButton = new JButton();
        exitButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Exit));
        exitButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(exitImg)).getImage().getScaledInstance(width, height, 0)));        
        exitButton.setFont(new java.awt.Font("sansserif", 1, 12));
        exitButton.putClientProperty("JButton.buttonType", "bevel");
        exitButton.addActionListener((ActionEvent e) -> {
            
            MessageForm.shows(Dictionnaire.get(EnumLibelles.Business_ConfirmExit), "Message", true, new Action() {
                @Override
                public void Ok() {
                    codeBrainManager.quit();
                }

                @Override
                public void Cancel() {
                }
            });
        });
        statusBarRight.add(exitButton);

        statusBar.add(statusBarLeft, BorderLayout.WEST);
        statusBar.add(statusBarRight, BorderLayout.EAST);

        add(statusBar, BorderLayout.SOUTH);

//        add(mainPanel);
        refresh();
        pack();
        
    }

    private void statusBarAncestorAdded(javax.swing.event.AncestorEvent evt) {
        startTimer();
    }

    private void statusBarAncestorRemoved(javax.swing.event.AncestorEvent evt) {
        stopTimer();
    }

    private void updateStatusLabel() {
        DateFormat tf = new SimpleDateFormat("HH:mm:ss - dd MMM yyyy", Locale.getLocale());
        hourStatusLabel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Hour) + ": " + tf.format(new Date()));
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer(500, (ActionEvent e) -> {
                updateStatusLabel();
            });
            timer.setRepeats(true);
            timer.start();
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    public void refresh() {

        userConnected = (GlobalParameters.get(User) == null ? null : ((Users) GlobalParameters.get(User)));

        levelGroup = (userConnected == null) ? null : userConnected.getLevelsId();

        userStatusLabel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Identifiant) + ": " + (userConnected == null ? "" : userConnected.getLogin()));
        levelLabel.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Group) + ": " + (levelGroup == null ? "" : Dictionnaire.get(levelGroup.getIntitule())));
        statusBar.revalidate();
        statusBar.repaint();
    }
    
    public void  loadMenu(){
        new MenuHome(mainPanel, menuBar);
    }
    
    public void  initStatus(Boolean state){
        statusBarRight.setVisible(state);
    }
}
