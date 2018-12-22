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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
    private Object key;
    private final CodeBrainMapper cbMapper = new CodeBrainMapper();
    private String entity;

    public CodeBrainManager() {
    }
    
    public static void CodeBrainManager(){
//        new CodeBrainManager();
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

        widgetSecurity.forEach((w) -> {
            System.out.println(w.toString());
        });

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

    public static String generateUIDPrimaryKey() {
        String id;
        if (GlobalParameters.getVar("user") == null) {

            id = "CB-" + Date.from(Instant.MIN).toString();
        } else {
            id = ((Users) GlobalParameters.getVar("user")).getLogin();
        }

        return id + new UID().toString();
    }

    public void createEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formDatas);
    }

    public void executeMethod(String className, String methodName, HashMap modelFinal) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        java.lang.reflect.Method entityMethod = null;

        Class<?> controllerClass = Class.forName(className); // convert string classname to class

        Constructor<?> constructor = controllerClass.getConstructor(EntityManagerFactory.class);
        Object instance = constructor.newInstance(emfManager);

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

//            System.out.println("User Value Form : " + ((Users) entityObject).getFirstName());
        }

        Class[] paramsClass = new Class[params.size()];
        for (int i = 0; i < params.size(); i++) {
            paramsClass[i] = params.get(i).getClass();
        }

        entityMethod.invoke(instance, params.toArray()); // pass args

    }

    public List getList(String ejbql, List args) throws Exception {

        Query query = null;
        int nbreArgs;
        HashMap<String, Object> arg;
        String sqlPlus;
        List<Map> lstMap = new ArrayList<>();

        int j = 0;
        if (args != null) {
            for (Object arg1 : args) {
                if (arg1 == null) {
                    ejbql = ejbql.replaceFirst("<>:arg" + j, " is not null");
                    ejbql = ejbql.replaceFirst("=:arg" + j, " is null");
                    j++;
                } else {
                    if (arg1 instanceof Object[]) {
                        for (Object argt : (Object[]) arg1) {
                            arg = new HashMap();
                            arg.put("key", j);
                            arg.put("value", argt);
                            lstMap.add(arg);
                            j++;
                        }
                    } else {
                        arg = new HashMap();
                        arg.put("key", j);
                        arg.put("value", arg1);
                        lstMap.add(arg);
                        j++;
                    }
                }
            }
        }
        nbreArgs = j;

        String stateDbString = "stateDb !=:arg" + nbreArgs;

        if (ejbql.contains("entity")) {
            stateDbString = "entity." + stateDbString;
        }

        if (!ejbql.contains("where")) {
            sqlPlus = " where " + stateDbString;
        } else {
            sqlPlus = " and " + stateDbString;
        }
        String finEjb = "";

        if (ejbql.contains("group by")) {
            finEjb = "group by";
        } else {
            if (ejbql.contains("order by")) {
                finEjb = "order by";
            }
        }

        //sqlPlus="";
        if (finEjb.length() > 0) {
            ejbql = ejbql.replace(finEjb, sqlPlus + " " + finEjb);
        } else {
            ejbql = ejbql + sqlPlus;
        }

        try {

            query = emfManager.createEntityManager().createQuery(ejbql);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List list;
        String argsEvent = "";

        if (lstMap.size() > 0) {
            for (Map map : lstMap) {
                query.setParameter("arg" + map.get("key"), map.get("value"));
            }
        }
        query.setParameter("arg" + nbreArgs,
                EnumLibelles.Business_Status_StateDb_Delete.toString());
        try {
            list = query.getResultList();

            return list;
        } catch (Exception e) {
            return null;
        }

    }
    
    public Object convertToObject(Object obj, Class cl){
        return cbMapper.mapper(obj, cl);
    }

    public String getUpperValue(String value) {
		return value.substring(0, 1).toUpperCase()
				+ value.substring(1, value.length());
	}

	public String getLowerValue(String value) {
		return value.substring(0, 1).toLowerCase()
				+ value.substring(1, value.length());
	}
}
