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
public class GlobalParameters {

    private static HashMap<Object, Object> variableGlobale = new HashMap<Object, Object>();

    public GlobalParameters() {
        GlobalParameters.variableGlobale= new HashMap<>();
    }
    
    public static void init() {
        GlobalParameters.variableGlobale= new HashMap<>();
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

    public static void reset() {
        variableGlobale.clear();
    }
}
