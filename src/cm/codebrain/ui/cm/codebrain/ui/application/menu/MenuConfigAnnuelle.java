package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuConfigAnnuelle extends JButton {

    private final int menuIndex;
    private final JPanel mainPanel;
    private final JToolBar menu;
    private JButton menuButton;

    public MenuConfigAnnuelle(JPanel mainPanel, JToolBar menu) {
        this.menu = menu;
        this.mainPanel = mainPanel;
        this.menuIndex = menu.getComponents().length;

        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_ConfigurationAnnuelle.toString(), true));
        putClientProperty("JButton.buttonType", "bevel");

        setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        setMaximumSize(new java.awt.Dimension(250, 80));
        setMinimumSize(new java.awt.Dimension(250, 80));
        setPreferredSize(new java.awt.Dimension(250, 80));

        addActionListener((ActionEvent e) -> {
            addComponentPanel();
        });
        this.mainPanel.add(this);
    }

    public void addComponentPanel() {
        this.mainPanel.removeAll();
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
        /**
         * Menu Bouton
         */
        this.menuButton = new JButton();
        this.menuButton.setText(Dictionnaire.get(EnumLibelles.Business_Libelle_ConfigurationAnnuelle));
        this.menuButton.putClientProperty("JButton.buttonType", "bevel");
        this.menuButton.addActionListener((ActionEvent e) -> {
            goToHere();
        });
        this.menu.add(this.menuButton);
        /**
         * Panel Home
         */
        addPanel(this.mainPanel,
                new MenuCreateAnneeAcademique(this.mainPanel, this.menu),
                new MenuTrimestreEtSequence(this.mainPanel, this.menu),
                new MenuEtablissement(this.mainPanel, this.menu),
                new MenuCategorie(this.mainPanel, this.menu),
                new MenuClasse(this.mainPanel, this.menu),
                new MenuSalle(this.mainPanel, this.menu),
                new MenuGroupSalle(this.mainPanel, this.menu)
        );
    }

    private void goToHere() {
        int tabs = this.menu.getComponents().length;

        for (int i = tabs - 1; i >= this.menuIndex; i--) {
            this.menu.remove(i);
        }
        this.menu.revalidate();
        this.menu.repaint();

        addComponentPanel();
    }

    private void addPanel(JPanel mainPanel, JButton... buttons) {
        for (JButton btn : buttons) {
            mainPanel.add(btn);
        }
    }
}
