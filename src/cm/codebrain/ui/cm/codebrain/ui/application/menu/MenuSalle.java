package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.backoffice.SalleForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuSalle extends JButton {

    private SalleForm form;

    public MenuSalle(JPanel mainPanel, JToolBar menu) {

        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Salle));
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
        form = new SalleForm(Dictionnaire.get(EnumLibelles.Business_Libelle_Salle));
        form.setVisible(true);
    }
}
