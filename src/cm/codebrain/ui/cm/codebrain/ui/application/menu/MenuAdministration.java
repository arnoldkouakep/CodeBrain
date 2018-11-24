package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuAdministration extends JButton {

    private final JToolBar menu;
    private JButton menuButton;
    private final int menuIndex;

    public MenuAdministration(JPanel mainPanel, JToolBar menu) {
        this.menu = menu;
        
        this.menuIndex=menu.getComponents().length;
        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Administration));
//        setText(StringUtil.UTF8Encode(Locale.i18n.getString(EnumLibelles.Business_Libelle_Administration.toString())));
        putClientProperty("JButton.buttonType", "bevel");
        addActionListener((ActionEvent e) -> {
            addComponentPanel(mainPanel, menu);
        });
        
        setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        setMaximumSize(new java.awt.Dimension(250, 80));
        setMinimumSize(new java.awt.Dimension(250, 80));
        setPreferredSize(new java.awt.Dimension(250, 80));
        
        mainPanel.add(this);
    }
    
    public void addComponentPanel(JPanel mainPanel, JToolBar menu){
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
        
        this.menuButton = new JButton();
        this.menuButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Administration));
        this.menuButton.putClientProperty("JButton.buttonType", "bevel");
        this.menuButton.addActionListener((ActionEvent e) -> {
            goToHere(mainPanel);
        });
        menu.add(this.menuButton);

        /*
        *
        *Button Classe
        *
        */
        
        mainPanel.add(new MenuUser(mainPanel, menu));
        mainPanel.add(new MenuClasse(mainPanel, menu));
        
    }
    
    
    private void goToHere(JPanel mainPanel){
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
        
        int tabs = menu.getComponents().length;

        for(int i = tabs-1; i>= this.menuIndex; i--){
            menu.remove(i);
        }
        menu.revalidate();
        menu.repaint();
        
        new MenuAdministration(mainPanel, menu).addComponentPanel(mainPanel, menu);
    }
}
