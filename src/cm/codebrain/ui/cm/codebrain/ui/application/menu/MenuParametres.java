package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuParametres extends JButton {

    private final JToolBar menu;
    private JButton menuButton;
    private final int menuIndex;

    public MenuParametres(JPanel mainPanel, JToolBar menu) {
        this.menu = menu;
        
        this.menuIndex=menu.getComponents().length;
        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Parametres));
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
        this.menuButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Parametres));
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
        
        mainPanel.add(new MenuEtablissement(mainPanel, menu));
        mainPanel.add(new MenuCategorie(mainPanel, menu));
        mainPanel.add(new MenuClasse(mainPanel, menu));
        mainPanel.add(new MenuSalle(mainPanel, menu));
        mainPanel.add(new MenuGroupSalle(mainPanel, menu));
        mainPanel.add(new MenuStudent(mainPanel, menu));
        
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
        
        new MenuParametres(mainPanel, menu).addComponentPanel(mainPanel, menu);
    }
}
