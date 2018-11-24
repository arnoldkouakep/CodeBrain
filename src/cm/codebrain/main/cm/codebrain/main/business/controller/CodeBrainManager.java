/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import cm.codebrain.main.business.manager.*;
import cm.codebrain.main.business.entitie.*;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.controller.StringUtils;
import cm.codebrain.ui.application.enumerations.EnumLibelles;
import cm.codebrain.ui.application.security.LoginForm;
import cm.codebrain.ui.application.security.MainForm;
import cm.codebrain.ui.application.security.ReLoginForm;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.server.UID;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainManager {

    private MainForm mainForm;
    private LoginForm login;
    private ReLoginForm reLogin;
    private final EntityManagerFactory emfManager = Persistence.createEntityManagerFactory("Brain");
    private List<Widget> widgetSecurity;
    private HashMap<String, Object> modelFinal;
    private Object key;
    private final CodeBrainMapper cbMapper = new CodeBrainMapper();
    private String entity;

    public CodeBrainManager() {
    }

    public Users authenticate(String login, String password) throws SQLException {

        UsersJpaController userCtrl = new UsersJpaController(emfManager);

        Users user = userCtrl.authentificate(login, MD5(password));

        GlobalParameters.addVar("user", user);

        getWidgetSecurity(user);

        mainForm.loadMenu();
        mainForm.initStatus(Boolean.TRUE);

        return user;
    }

    public void getWidgetSecurity(Users userConnected) throws SQLException {
        WidgetJpaController widgetCtrl = new WidgetJpaController(emfManager);

        widgetSecurity = widgetCtrl.findWidgetFromLevel(userConnected.getLevelsId());

        for (Widget w : widgetSecurity) {
            System.out.println(w.toString());
        }

        GlobalParameters.addVar("widgets", widgetSecurity);
    }

    public void logout() {
        GlobalParameters.reset();
        restart();
    }

    public String MD5(String md5) {
        return StringUtils.MD5encode(md5);
    }

    public void start() {

        mainForm = new MainForm(this);

        mainForm.setVisible(true);
        if (GlobalParameters.getVar("user") == null) {
            login = new LoginForm(this, mainForm, true);
            login.setVisible(true);
        } else {
            reLogin = new ReLoginForm(this, mainForm, true, ((Users) GlobalParameters.getVar("user")).getLogin());
            reLogin.setVisible(true);
        }
    }

    public void restart() {
        if (mainForm == null) {
            start();
        } else {
            reset();
            start();
        }
    }

    public void reset() {
        mainForm.dispose();
        mainForm.revalidate();
    }

    public void load() {
        mainForm.refresh();
    }

    public String generateUIDPrimaryKey() {
        String id;
        if (GlobalParameters.getVar("user") == null) {

            id = "CB-" + "1212";///+ Date.from(Instant.MIN).toString();
        } else {
            id = ((Users) GlobalParameters.getVar("user")).getLogin();
        }

        return id + new UID().toString();
    }

    public void createEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        makeModelData(formDatas);

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formDatas);
    }

    public void executeMethod(String className, String methodName, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        java.lang.reflect.Method entityMethod = null;

        Class<?> controllerClass = Class.forName(className); // convert string classname to class

        Constructor<?> dogConstructor = controllerClass.getConstructor(EntityManagerFactory.class);
        Object instance = dogConstructor.newInstance(emfManager);

        java.lang.reflect.Method[] methods = controllerClass.getMethods();

        for (java.lang.reflect.Method m : methods) {
            if (m.getName().equals(methodName)) {
                entityMethod = m;
                break;
            }
        }

        java.lang.reflect.Parameter[] parameters = entityMethod.getParameters();

        List<Object> params = new ArrayList<>();

        for (java.lang.reflect.Parameter p : parameters) {

            String paramClassName = p.getType().getTypeName();

            Class entityClass = Class.forName(paramClassName);

            Object entityObject = cbMapper.mapper(modelFinal, entityClass);

            params.add(entityObject);

            System.out.println("User Value Form : " + ((Users) entityObject).getFirstName());
        }

        Class[] paramsClass = new Class[params.size()];
        for (int i = 0; i < params.size(); i++) {
            paramsClass[i] = params.get(i).getClass();
        }

        entityMethod.invoke(instance, params.toArray()); // pass args
        
    }

    private void makeModelData(HashMap formDatas) {

        System.out.println(((Users) GlobalParameters.getVar("user")).getLevelsId().getCode());

        modelFinal = new HashMap<>();

        modelFinal.put(entity.toLowerCase() + "Id", generateUIDPrimaryKey());
        modelFinal.put("stateDb", EnumLibelles.Business_Status_StateDb_Create.toString());
        modelFinal.put("dtCreated", new Date());
        modelFinal.put("dtModified", new Date());
        modelFinal.put("userCreated", GlobalParameters.getVar("user"));
        modelFinal.put("userModified", GlobalParameters.getVar("user"));
        modelFinal.put("levelsId", ((Users) GlobalParameters.getVar("user")).getLevelsId());

        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JTextField.class)).map((ky) -> (JTextField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            modelFinal.put(this.key.toString(), ((JTextField) val).getText());
        });
        
        formDatas.keySet().stream().map((ky) -> {
            this.key = ky;
            return ky;
        }).filter((ky) -> (formDatas.get(ky).getClass() == JPasswordField.class)).map((ky) -> (JPasswordField) formDatas.get(ky)).forEachOrdered((Object val) -> {
            modelFinal.put(this.key.toString(), ((JPasswordField) val).getText());
        });
        
        GlobalParameters.addVar("model", modelFinal);
    }
    
}
