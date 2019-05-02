/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.services;

/**
 *
 * @author KSA-INET
 */
public interface CodeBrainService extends CodeBrainServiceAsync{

//    public CodeBrainManager cbManager = new CodeBrainManager();

    public String authenticate(String login, String password) throws Exception;

    public Boolean connexion() throws Exception;
    
//
//    public CodeBrainService(CodeBrainManager cbManager) {
//        this.cbManager = cbManager;
//    }
//
//    @Override
//    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception {
//        cbManager.crud(entity, modelFinal, etatAction, etatActionList, listCreUp, listDel);
//    }
//
//    @Override
//    public String MD5(String text) {
//        return cbManager.MD5(text);
//    }
//
//    @Override
//    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException {
//        return cbManager.convertToObject(data, tmpEntity);
//    }
//
//    @Override
//    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception {
//        return cbManager.getListEntity(entity, clause, toArray);
//    }
//
//    @Override
//    public List<HashMap> convertToListObject(List modelResult, Class aClass) {
//        return cbManager.convertToListObject(modelResult, aClass);
//    }
//
//    @Override
//    public Object getObjectInvoke(Object objectConverted, String string, String string0) {
//        return cbManager.getObjectInvoke(objectConverted, string, string0);
//    }
//
//    @Override
//    public Object convertToObject(Object objectReslut, Class aClass) {
//        return cbManager.convertToObject(objectReslut, aClass);
//    }
//
//    @Override
//    public String authenticate(String login, String password) {
//        try {
//            asynchronousExe.execute();
//            return cbManager.authenticate(login, password);
//        } catch (Exception ex) {
//            asynchronousExe.error(ex);
//            return null;
//        }
//    }
//
//    @Override
//    public void load() {
//        cbManager.load();
//    }
//
//    @Override
//    public void restart() {
//        cbManager.restart();
//    }
//
//    public void start() {
//        cbManager.start();
//    }

}
