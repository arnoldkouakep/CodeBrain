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

    private static final HashMap<String, Object> variableGlobale = new HashMap<>();

//    public GlobalParameters(GlobalParameters globalParameters) {
//        super(globalParameters);
//    }
    public GlobalParameters() {
//        this.variableGlobale= new HashMap<>();
    }

    /**
     *
     * @param key
     * @param value
     */
    public static void addVar(String key, Object value) {
        variableGlobale.put(key, value);
    }

    public static Object getVar(String key) {
        return variableGlobale.get(key);
    }

    public static void reset() {
        variableGlobale.clear();
    }
}
