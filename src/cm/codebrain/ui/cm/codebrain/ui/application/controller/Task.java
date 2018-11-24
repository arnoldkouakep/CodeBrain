/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
import java.awt.Container;
import java.awt.Toolkit;
import javax.swing.SwingWorker;

/**
 *
 * @author KSA-INET
 */
public class Task extends SwingWorker<Void, Void> {

    private final Container parent;
    private final Executable executable;
    private Exception exception;

    public Task(Loading parent, Executable exe) {
        this.parent = parent;
        executable = exe;
    }

    /*
     * Main task. Executed in background thread.
     */
    @Override
    public Void doInBackground() throws Exception {

//        int progress = 0;
        try {
            executable.execute();
        } catch (Exception ex) {
            this.exception = ex;
        }
       int progress = 100;
        setProgress(progress);//Math.min(progress, 100));
        return null;
    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        this.parent.setCursor(null); // turn off the wait cursor
    }

    public Exception getException() {
        return exception;
    }
}
