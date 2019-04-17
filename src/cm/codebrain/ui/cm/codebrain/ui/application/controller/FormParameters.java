/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import java.util.HashMap;

/**
 *
 * @author KSA-INET
 */
public class FormParameters {

    private static HashMap<Object, Object> variableGlobale = new HashMap<>();

    public FormParameters() {
        FormParameters.variableGlobale= new HashMap<>();
    }

    public static void init() {
        FormParameters.variableGlobale= new HashMap<>();
    }
    /**
     *
     * @param key
     * @param value
     */
    public static void add(Object key, Object value) {
        variableGlobale.put(key, value);
    }

    public static Object get(Object key) {
        return variableGlobale.get(key);
    }
    
    public static void remove(Object key) {
        variableGlobale.remove(key);
    }

    public static void reset() {
        variableGlobale.clear();
    }
}
