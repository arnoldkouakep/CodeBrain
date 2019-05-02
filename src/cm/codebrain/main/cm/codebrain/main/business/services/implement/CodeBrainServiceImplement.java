/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.services.implement;

import cm.codebrain.main.business.controller.CodeBrainManager;
import cm.codebrain.ui.application.services.CodeBrainService;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainServiceImplement implements CodeBrainService{

    private CodeBrainManager codeBrainManager;

    @Override
    public Boolean connexion() throws Exception {
        codeBrainManager = new CodeBrainManager();
        return codeBrainManager.connexion();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String authenticate(String login, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    
//    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception;
//
//    public String MD5(String text);
//
//    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException;
//
//    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception;
//
//    public List<HashMap> convertToListObject(List modelResult, Class aClass);
//
//    public Object getObjectInvoke(Object objectConverted, String string, String string0);
//
//    public Object convertToObject(Object objectReslut, Class aClass);
//
//    public String authenticate(String login, String password, Executable asynchronousExe);
//
//    public void load();
//
//    public void restart();

}
