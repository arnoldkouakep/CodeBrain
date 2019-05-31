/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application;

import ch.randelshofer.quaqua.QuaquaManager;
import cm.codebrain.ui.application.controller.CodeBrainAcces;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author KSA-INET
 */
public class CodeBrain {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        final java.util.List argList = Arrays.asList(args);

        // Add Quaqua to the lafs
        ArrayList<UIManager.LookAndFeelInfo> infos = new ArrayList<UIManager.LookAndFeelInfo>(Arrays.asList(UIManager.getInstalledLookAndFeels()));
        infos.add(new UIManager.LookAndFeelInfo("Quaqua", QuaquaManager.getLookAndFeelClassName()));
        UIManager.setInstalledLookAndFeels(infos.toArray(new UIManager.LookAndFeelInfo[infos.size()]));

        // Turn on look and feel decoration when not running on Mac OS X or Darwin.
        // This will still not look pretty, because we haven't got cast shadows
        // for the frame on other operating systems.
        boolean useDefaultLookAndFeelDecoration
                = !System.getProperty("os.name").toLowerCase().startsWith("mac")
                && !System.getProperty("os.name").toLowerCase().startsWith("darwin");
        int index = argList.indexOf("-decoration");
        if (index != -1 && index < argList.size() - 1) {
            useDefaultLookAndFeelDecoration = argList.get(index + 1).equals("true");
        }

        if (useDefaultLookAndFeelDecoration) {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        }

        // Launch the Main program
        SwingUtilities.invokeLater(new Runnable() {
//            private MainForm mainForm;
//            private LoginForm login;
            private CodeBrainAcces codeBrainAcces;

            public void run() {
                int index;
                index = argList.indexOf("-include");
                if (index != -1 && index < argList.size() - 1) {
                    HashSet includes = new HashSet();
                    includes.addAll(Arrays.asList(((String) argList.get(index + 1)).split(",")));

                    QuaquaManager.setIncludedUIs(includes);
                }
                index = argList.indexOf("-exclude");
                if (index != -1 && index < argList.size() - 1) {
                    HashSet excludes = new HashSet();
                    excludes.addAll(Arrays.asList(((String) argList.get(index + 1)).split(",")));

                    QuaquaManager.setExcludedUIs(excludes);
                }
                index = argList.indexOf("-laf");
                String lafName;
                if (index != -1 && index < argList.size() - 1) {
                    lafName = (String) argList.get(index + 1);
                } else {
                    lafName = QuaquaManager.getLookAndFeelClassName();
                }
                if (!lafName.equals("default")) {

                    if (lafName.equals("system")) {
                        lafName = UIManager.getSystemLookAndFeelClassName();
                    } else if (lafName.equals("crossplatform")) {
                        lafName = UIManager.getCrossPlatformLookAndFeelClassName();
                    }

                    try {
                        LookAndFeel laf = (LookAndFeel) Class.forName(lafName).newInstance();
                        UIManager.setLookAndFeel(laf);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                codeBrainAcces = new CodeBrainAcces();
                codeBrainAcces.start();
            }
        });
    }

}
