/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.implement;

import cm.codebrain.main.business.controller.CodeBrainExceptions;

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
    public abstract T execute() throws Exception;
    
    public void error(CodeBrainExceptions ex);
}
