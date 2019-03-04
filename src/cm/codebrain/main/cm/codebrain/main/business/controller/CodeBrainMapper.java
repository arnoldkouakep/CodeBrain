/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;

/**
 *
 * @author KSA-INET
 * @param <E>
 * @param <R>
 */
public class CodeBrainMapper<E, R> {

    public CodeBrainMapper() {
    }

    public R read(E entry, Class c) {
        TypeReference<R> typeRef = new TypeReference<R>() {
        };

        ObjectMapper oMapper = new ObjectMapper();
        R r = (R) oMapper.convertValue(entry, typeRef);
//        R r = (R) oMapper.convertValue(entry, c);

        return r;
    }

    public R mapper(E entry, Class c) {

        ObjectMapper oMapper = new ObjectMapper();
//        oMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        
//        String s = null;
//        try {
//            s = oMapper.writeValueAsString(entry);
//        }
//        catch (JsonProcessingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//        System.out.println(s);
        
//        oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        R r = (R) oMapper.convertValue(entry, c);

        return r;
    }

    public R deserialize(E entry, Class c) {

        ObjectMapper mapper = new ObjectMapper();

        SimpleModule idAsRefModule = new SimpleModule("ID-to-ref",
                new Version(1, 0, 0, null));

        idAsRefModule.addDeserializer(c,
                new JsonDeserializer<E>() {
            @Override
            public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

                ObjectCodec codec = p.getCodec();
                JsonNode node = codec.readTree(p);
                boolean isFullImpl = node.has("message");
                Class<? extends E> cls = isFullImpl ? c
                        : c;
                return codec.treeToValue(node, cls);

            }

        });
//            @Override
//            public TestEntity deserialize(JsonParser jp, DeserializationContext dc)
//                    throws IOException, JsonProcessingException {
//
//                ObjectCodec codec = jp.getCodec();
//                JsonNode node = codec.readTree(jp);
//                boolean isFullImpl = node.has("message");
//                Class<? extends TestEntity> cls = isFullImpl ? TestEntityImpl.class
//                        : TestEntityRef.class;
//                return codec.treeToValue(node, cls);
//            }
//        });

        mapper.registerModule(idAsRefModule);

//        return mapper;
//        TypeReference<R> typeRef = new TypeReference<R>() {
//        };
        ObjectMapper oMapper = new ObjectMapper();
        R r = (R) oMapper.convertValue(entry, c);
//        R r = (R) oMapper.convertValue(entry, c);

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
