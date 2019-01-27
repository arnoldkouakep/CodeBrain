/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
        R r = (R) oMapper.convertValue(entry, c);

        return r;
    }

    public R read(E entry, Class c) {

        ObjectMapper oMapper = new ObjectMapper();
        R r = (R) oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(entry, c);

        return r;
    }
    /*
    public R deserialize(E entry, Class c){
        
        TypeReference<R> typeRef = new TypeReference<R>(){};
        
        ObjectMapper oMapper = new ObjectMapper();
        
        R r = (R) oMapper.readValue(entry, typeRef);
        return null;
    }
*/
}
