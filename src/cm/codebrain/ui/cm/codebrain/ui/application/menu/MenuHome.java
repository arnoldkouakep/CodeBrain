package cm.codebrain.ui.application.menu;

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuHome extends JButton {

    private final int menuIndex;
    private final JPanel mainPanel;
    private final JToolBar menu;
    private JButton menuButton;

    public MenuHome(JPanel mainPanel, JToolBar menu) {
        this.menu = menu;
        this.mainPanel = mainPanel;
        this.menuIndex = menu.getComponents().length;
        
        addComponentPanel();
    }

    public void addComponentPanel() {
        this.mainPanel.removeAll();
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
        /**
         * Menu Bouton
         */
        this.menuButton = new JButton();
        this.menuButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/home.png")).getImage().getScaledInstance(18, 18, 0)));
        this.menuButton.putClientProperty("JButton.buttonType", "bevel");
        this.menuButton.addActionListener((ActionEvent e) -> {
            goToHere();
        });
        this.menu.add(this.menuButton);
        /**
         * Panel Home
         */
        addPanel(mainPanel,
                new MenuAdministration(mainPanel, menu),
                new MenuBackOffice(mainPanel, menu),
                new MenuFrontOffice(mainPanel, menu),
                new MenuEdition(mainPanel, menu)
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
