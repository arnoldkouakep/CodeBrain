/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author KSA-INET
 * @param <E>
 * @param <R>
 */
public class CodeBrainMapper<E, R> {

    public CodeBrainMapper() {
    }

    public R mapper(E entry, Class c) {
        
        ObjectMapper oMapper = new ObjectMapper();
//        oMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        oMapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, true);
        
        // object -> Map
        
        R r = (R) oMapper.convertValue(entry, c);
        
        return r;
    }
}
