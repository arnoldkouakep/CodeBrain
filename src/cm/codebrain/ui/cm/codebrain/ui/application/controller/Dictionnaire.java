/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import java.awt.Image;
import javax.imageio.ImageIO;

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
        try{
        return StringUtils.UTF8Encode(Locale.i18n.getString(value.toString()));
        }catch(Exception e){
            return value.toString();
        }
    }
    
    public static Image getResource(Enum value){
        try{
            String resourcePath = StringUtils.UTF8Encode(Locale.i18n.getString(value.toString()));
            
            return ImageIO.read(Dictionnaire.class.getResource(resourcePath));
            
//            return ImageIcon(Dictionnaire.class.getResource(resourcePath));//.getImage().getScaledInstance(width, height, 0);
        
        }catch(Exception e){
            return null;
        }
    }
    
    public static Image getResource(String resourcePath){
        try{
//            String resourcePath = StringUtils.UTF8Encode(Locale.i18n.getString(value.toString()));
            
            return ImageIO.read(Dictionnaire.class.getResource(resourcePath));
            
//            return ImageIcon(Dictionnaire.class.getResource(resourcePath));//.getImage().getScaledInstance(width, height, 0);
        
        }catch(Exception e){
            return null;
        }
    }
}
