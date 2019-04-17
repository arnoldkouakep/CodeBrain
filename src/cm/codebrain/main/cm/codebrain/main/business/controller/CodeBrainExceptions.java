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
    
    public CodeBrainExceptions() {
    }

    public CodeBrainExceptions(String message) {
        super(Dictionnaire.get(message));
    }

    public CodeBrainExceptions(String message, Throwable cause) {
        super(Dictionnaire.get(message), cause);
    }

    public CodeBrainExceptions(Throwable cause) {
        super(cause);
    }

    public CodeBrainExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(Dictionnaire.get(EnumError.WorkFlowException.toString()), cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMessage() {
        return manageError(super.getCause()); //To change body of generated methods, choose Tools | Templates.
    }

    private String manageError(Throwable cause) {
        if(cause instanceof NullPointerException){
            return Dictionnaire.get(EnumError.NullValueException);
        }else if(cause instanceof ArrayIndexOutOfBoundsException){
            return Dictionnaire.get(EnumError.WorkFlowException);
        } else{
            return cause.getMessage();
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
