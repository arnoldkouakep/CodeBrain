package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.backoffice.ClasseForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuClasse extends JButton {

    private final JPanel mainPanel;
    private ClasseForm form;

    public MenuClasse(JPanel mainPanel, JToolBar menu) {
        this.mainPanel = mainPanel;
        
        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Classe.toString(), true));
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
        form = new ClasseForm(Dictionnaire.get(EnumLibelles.Business_Libelle_Classe));
        form.setVisible(true);
    }

    public JButton getButton(){
        return this;
    }
}
