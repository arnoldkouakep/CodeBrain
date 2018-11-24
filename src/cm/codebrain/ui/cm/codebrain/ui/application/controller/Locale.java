/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import java.util.ResourceBundle;

/**
 *
 * @author KSA-INET
 */
public class Locale {

    public static ResourceBundle i18n;

    public static Integer LANGUAGE = 0;

    public static void initBundle() {
        if (LANGUAGE == 0) {
            i18n = ResourceBundle.getBundle("fr_fr");
        } else if (LANGUAGE == 1) {
            i18n = ResourceBundle.getBundle("en_en");
        }
    }

}
