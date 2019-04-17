package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.backoffice.SalleForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuSalle extends JButton {

    private final JPanel mainPanel;
    private SalleForm form;

    public MenuSalle(JPanel mainPanel, JToolBar menu) {
        this.mainPanel = mainPanel;
        
        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Salle.toString(), true));
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
        form = new SalleForm(Dictionnaire.get(EnumLibelles.Business_Libelle_Salle));
        form.setVisible(true);
    }

    public JButton getButton(){
        return this;
    }
}
