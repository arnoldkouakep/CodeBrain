/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

/**
 *
 * @author KSA-INET
 */
public class Dictionnaire {
//    public Dictionnaire(){}
    public static String get(String text){
        try{
            return StringUtils.UTF8Encode(Locale.i18n.getString(text));
        
        }catch(Exception e){
            return text;
        }
    }
    
    public static String get(Enum value){
        return StringUtils.UTF8Encode(Locale.i18n.getString(value.toString()));
    }
}
