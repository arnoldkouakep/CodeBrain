/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.services;

import cm.codebrain.ui.application.implement.Executable;

/**
 *
 * @author KSA-INET
 */
public interface CodeBrainServiceAsync{

    void authenticate(String login, String password, Executable<String> callback);
    
    void connexion(Executable<String> callback);
    
    
//
//    @Override
//    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception {
//        super.crud(entity, modelFinal, etatAction, etatActionList, listCreUp, listDel);
//    }
//
//    @Override
//    public String MD5(String text) {
//        return super.MD5(text);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException {
//        return super.convertToObject(data, tmpEntity);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception {
//        return super.getListEntity(entity, clause, toArray);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<HashMap> convertToListObject(List modelResult, Class aClass) {
//        return super.convertToListObject(modelResult, aClass);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Object getObjectInvoke(Object objectConverted, String string, String string0) {
//        return super.getObjectInvoke(objectConverted, string, string0);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Object convertToObject(Object objectReslut, Class aClass) {
//        return super.convertToObject(objectReslut, aClass);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void authenticate(String login, String password, Executable<String> asynchronousExe) {
//        super.authenticate(login, password, asynchronousExe);
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void load() {
//        super.load();
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void restart() {
//        super.restart();
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public void start() {
//        super.start();
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    

    
    
}
