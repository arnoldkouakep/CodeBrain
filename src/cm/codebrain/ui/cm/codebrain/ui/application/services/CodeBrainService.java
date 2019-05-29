/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.services;

import cm.codebrain.ui.application.enumerations.EnumVariable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author KSA-INET
 */
public interface CodeBrainService {

    public String authenticate(String login, String password);

    public boolean connexion();

    public boolean disConnexion();

    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception;

    public String MD5(String text);

    public Object convertToObject(Object data, String tmpEntity) throws ClassNotFoundException;

    public List getListEntity(String entity, String clause, Object[] toArray) throws Exception;

    public Object getObjectInvoke(Object objectConverted, String string, String string0);

    public List<HashMap> convertToListObject(List modelResult, Class<HashMap> aClass);

    public Object convertToObject(Object objectReslut, Class<HashMap> aClass);

}
