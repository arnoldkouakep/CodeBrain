package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.backoffice.SectionsForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuCategorie extends JButton {

    private SectionsForm form;

    public MenuCategorie(JPanel mainPanel, JToolBar menu) {

        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Sections));
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

    public void addComponentPanel(JPanel mainPanel, JToolBar menu) {
        form = new SectionsForm(Dictionnaire.get(EnumLibelles.Business_Libelle_Sections));
        form.setVisible(true);
    }
}
