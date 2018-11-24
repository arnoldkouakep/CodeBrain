package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.ModelForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuUsersList extends JButton {

    private ModelForm modelForm;

    public MenuUsersList(JPanel mainPanel, JToolBar menu) {

        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Liste_Utilisateur));
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
        modelForm = new ModelForm(Dictionnaire.get(EnumLibelles.Business_Libelle_Liste_Utilisateur));
        modelForm.setVisible(true);
//        JSheet.showMessageSheet(SwingUtilities.windowForComponent(this), "Bouton Pas encore prêt.", JOptionPane.INFORMATION_MESSAGE);
    }
}
