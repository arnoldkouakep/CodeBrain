/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.services;

import cm.codebrain.main.business.services.implement.CodeBrainServiceImplement;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainServiceAsync extends CodeBrainServiceImplement implements CodeBrainService {

    public CodeBrainServiceAsync() {
    }

    @Override
    public String authenticate(String login, String password) {
        return super.authenticate(login, password); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean connexion() {
        return super.connexion();
    }

    @Override
    public boolean disConnexion() {
        return super.disConnexion();
    }

    @Override
    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception {
        super.crud(entity, modelFinal, etatAction, etatActionList, listCreUp, listDel);
    }

    @Override
    public String MD5(String text) {
        return super.MD5(text);
    }

    @Override
    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException {
        try {
            return super.convertToObject(data, tmpEntity);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception {
        return super.getListEntity(entity, clause, toArray);
    }

    public List<HashMap> convertToListObject(List modelResult, Class<HashMap> aClass) {
        return super.convertToListObject(modelResult, aClass);
    }

    public Object getObjectInvoke(Object objectConverted, String string, String string0) {
        return super.getObjectInvoke(objectConverted, string, string0);
    }

    public Object convertToObject(Object objectReslut, Class<HashMap> aClass) {
        return super.convertToObject(objectReslut, aClass);
    }

}
