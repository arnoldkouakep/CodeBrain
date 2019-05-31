/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.services;

import cm.codebrain.main.business.controller.CodeBrainManager;
import cm.codebrain.main.business.services.implement.CodeBrainServiceImplement;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainServiceAsync extends CodeBrainServiceImplement {

    public CodeBrainServiceAsync() {
    }

    @Override
    public Object convertToObject(Object objectReslut, Class<HashMap> aClass) {
        return super.convertToObject(objectReslut, aClass); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HashMap> convertToListObject(List modelResult, Class<HashMap> aClass) {
        return super.convertToListObject(modelResult, aClass); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObjectInvoke(Object objectConverted, String string, String string0) {
        return super.getObjectInvoke(objectConverted, string, string0); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception {
        return super.getListEntity(entity, clause, toArray); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException {
        return super.convertToObject(data, tmpEntity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String MD5(String text) {
        return super.MD5(text); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception {
        super.crud(entity, modelFinal, etatAction, etatActionList, listCreUp, listDel); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean disConnexion() {
        return super.disConnexion(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean connexion() {
        return super.connexion(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String authenticate(String login, String password) {
        return super.authenticate(login, password); //To change body of generated methods, choose Tools | Templates.
    }

}
