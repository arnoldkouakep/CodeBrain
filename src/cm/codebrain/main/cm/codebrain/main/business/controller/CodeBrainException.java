/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.enumerations.EnumError;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainException extends Exception{

    private static Exception cbException = new Exception();

    private static Exception controllerException(Exception cbException) {
        
        if(cbException.getCause() instanceof NullPointerException){
            cbException.initCause(new NullPointerException(Dictionnaire.get(EnumError.Business_Libelle_No_Result_Found)));
        }else if(cbException.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException){
            cbException = new Exception(Dictionnaire.get(EnumError.NumberArgumentException), cbException.getCause());
        }else{
            cbException = new Exception(Dictionnaire.get(EnumError.WorkFlowException), cbException.getCause());
        }
        
        return cbException;
    }

    public CodeBrainException() {
    }

    public CodeBrainException(String message) {
        super(message);
    }

    public CodeBrainException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeBrainException(Throwable cause) {
        super(cause);
    }

    public CodeBrainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public static Exception analyse() {
        CodeBrainException.cbException = new Exception();
        return CodeBrainException.cbException;
    }
    
    public static Exception analyse(String message) {
        CodeBrainException.cbException = new Exception(message);
        return CodeBrainException.cbException;
    }

    public static Exception analyse(Throwable cause) {
        CodeBrainException.cbException = new Exception(cause);
        return controllerException(CodeBrainException.cbException);
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
