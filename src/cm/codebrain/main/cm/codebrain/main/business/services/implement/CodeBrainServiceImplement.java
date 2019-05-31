/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.services.implement;

import cm.codebrain.main.business.controller.CodeBrainManager;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import cm.codebrain.ui.application.services.CodeBrainService;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainServiceImplement implements CodeBrainService {

    public CodeBrainServiceImplement() {
    }

    @Override
    public String authenticate(String login, String password) {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.authenticate(login, password);
    }

    @Override
    public boolean connexion(){
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.connexion();
    }

    @Override
    public boolean disConnexion() {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.disConnexion();
    }

    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        codeBrainManager.crud(entity, modelFinal, etatAction, etatActionList, listCreUp, listDel);
    }

    public String MD5(String text) {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.MD5(text);
    }

    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.convertToObject(data, tmpEntity);
    }

    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.getListEntity(entity, clause, toArray);
    }

    public Object getObjectInvoke(Object objectConverted, String string, String string0) {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.getObjectInvoke(objectConverted, string, string0);
    }

    public List<HashMap> convertToListObject(List modelResult, Class<HashMap> aClass) {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.convertToListObject(modelResult, aClass);
    }

    public Object convertToObject(Object objectReslut, Class<HashMap> aClass) {
        CodeBrainManager codeBrainManager = new CodeBrainManager();
        return codeBrainManager.convertToObject(objectReslut, aClass);
    }

}
