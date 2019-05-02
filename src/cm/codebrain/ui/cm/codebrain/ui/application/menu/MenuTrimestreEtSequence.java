package cm.codebrain.ui.application.menu;

import cm.codebrain.ui.application.backoffice.TrimestreAndSequencesForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuTrimestreEtSequence extends JButton {

    private final JPanel mainPanel;
    private TrimestreAndSequencesForm form;

    public MenuTrimestreEtSequence(JPanel mainPanel, JToolBar menu) {
        this.mainPanel = mainPanel;
        
        setText(Dictionnaire.get(EnumLibelles.Business_Libelle_Trimestre.toString()).concat(" & ").concat(Dictionnaire.get(EnumLibelles.Business_Libelle_Sequence.toString())));
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
        form = new TrimestreAndSequencesForm(Dictionnaire.get(EnumLibelles.Business_Libelle_Trimestre.toString()).concat(" & ").concat(Dictionnaire.get(EnumLibelles.Business_Libelle_Sequence.toString())));
        form.setVisible(true);
    }
}
