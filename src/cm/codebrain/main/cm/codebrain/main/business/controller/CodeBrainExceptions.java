/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import cm.codebrain.main.business.enumerations.EnumError;
import cm.codebrain.ui.application.controller.Dictionnaire;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainExceptions extends Exception{
    
    private static final long serialVersionUID = -8642262827833529009L;
        
    public CodeBrainExceptions() {
    }

    public CodeBrainExceptions(String message) {
        super(Dictionnaire.get(EnumError.WorkFlowException.toString()));
    }

    public CodeBrainExceptions(String message, Throwable cause) {
        super(Dictionnaire.get(EnumError.WorkFlowException.toString()), cause);
    }

    public CodeBrainExceptions(Throwable cause) {
        super(cause);
    }

    public CodeBrainExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(Dictionnaire.get(EnumError.WorkFlowException.toString()), cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
