package cm.codebrain.ui.application.menu;

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MenuHome extends JButton {

    private final JToolBar menu;

    public MenuHome(JPanel mainPanel, JToolBar menu) {
       
        this.menu = menu;
        
        /*
        *
        *Button Home
        *
         */
        setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/home.png")).getImage().getScaledInstance(18, 18, 0)));
        putClientProperty("JButton.buttonType", "bevel");
        addActionListener((ActionEvent e) -> {
            goToHere(mainPanel);
        });
        
        menu.add(this);
        
        addComponentPanel(mainPanel);
    }
    
    public void addComponentPanel(JPanel mainPanel){
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
        
        mainPanel.add(new MenuAdministration(mainPanel, menu));
    }
    
    private void goToHere(JPanel mainPanel){
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
        
        menu.removeAll();
        menu.revalidate();
        menu.repaint();
        
        new MenuHome(mainPanel, menu);
        
    }
}
