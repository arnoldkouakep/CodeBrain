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
public class CodeBrainServiceImplement implements CodeBrainService{

    private CodeBrainManager codeBrainManager;

    public CodeBrainServiceImplement() {
        this.codeBrainManager = new CodeBrainManager();
    }

    public CodeBrainServiceImplement(CodeBrainManager codeBrainManager) {
        this.codeBrainManager = codeBrainManager;
    }

    @Override
    public String authenticate(String login, String password) {
        return codeBrainManager.authenticate(login, password);
    }

    @Override
    public boolean connexion() {
        return codeBrainManager.connexion();
    }

    @Override
    public boolean disConnexion() {
        return codeBrainManager.disConnexion();
    }

    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception {
        codeBrainManager.crud(entity, modelFinal, etatAction, etatActionList, listCreUp, listDel);
    }

    public String MD5(String text) {
        return codeBrainManager.MD5(text);
    }

    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException {
        return codeBrainManager.convertToObject(data, tmpEntity);
    }

    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception {
        return codeBrainManager.getListEntity(entity, clause, toArray);
    }

    public Object getObjectInvoke(Object objectConverted, String string, String string0) {
        return codeBrainManager.getObjectInvoke(objectConverted, string, string0);
    }

    public List<HashMap> convertToListObject(List modelResult, Class<HashMap> aClass) {
        return codeBrainManager.convertToListObject(modelResult, aClass);
    }

    public Object convertToObject(Object objectReslut, Class<HashMap> aClass) {
        return codeBrainManager.convertToObject(objectReslut, aClass);
    }
    
}
