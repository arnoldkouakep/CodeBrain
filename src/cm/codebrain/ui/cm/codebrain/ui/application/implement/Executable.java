/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.implement;

/**
 *
 * @author KSA-INET
 * @param <T>
 */
public interface Executable<T> {
    /*
     *
     * 
     */
    public abstract void execute() throws Exception;
    
    public T success();
    
    public void error(Exception ex);
}
