/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author KSA-INET
 */
public class Dictionnaire {
    

    public static String get(String text) {
        return get(text, false, false);
    }

    public static String get(String text, Boolean isHtml) {
        return get(text, isHtml, false);
    }

    public static String get(Enum value) {
        return get(value.toString(), false, false);
    }
    
    public static String get(Enum value, Boolean Required) {
        return get(value.toString(), true, Required);
    }

    public static String get(String value, Boolean isHtml, boolean Required) {
        try {
            if (isHtml) {
                if(Required)
                    return "<html>" + StringUtils.UTF8Encode(Locale.i18n.getString(value)).replaceAll("\n", "<br/>") + "<font color='red' size=5>*</font></html>";
                else
                    return "<html>" + StringUtils.UTF8Encode(Locale.i18n.getString(value)).replaceAll("\n", "<br/>");
            } else {
                return StringUtils.UTF8Encode(Locale.i18n.getString(value));
            }
        } catch (Exception e) {
            return value;
        }
    }

    public static Image getResource(Enum value) {
        try {
            String resourcePath = StringUtils.UTF8Encode(Locale.i18n.getString(value.toString()));
            return ImageIO.read(Dictionnaire.class.getResource(resourcePath));
        } catch (IOException e) {
            return null;
        }
    }

    public static Image getResource(String resourcePath) {
        try {
            return ImageIO.read(Dictionnaire.class.getResource(resourcePath));
        } catch (IOException e) {
            return null;
        }
    }
}
