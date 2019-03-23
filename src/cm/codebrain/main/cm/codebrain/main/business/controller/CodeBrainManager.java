/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import cm.codebrain.main.business.manager.*;
import cm.codebrain.main.business.entitie.*;
import cm.codebrain.main.business.enumerations.EnumStatus;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.controller.StringUtils;
import static cm.codebrain.ui.application.enumerations.Enums.Entity;
import static cm.codebrain.ui.application.enumerations.Enums.User;
import cm.codebrain.ui.application.security.LoginForm;
import cm.codebrain.ui.application.security.MainForm;
import cm.codebrain.ui.application.security.ReLoginForm;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.server.UID;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    private final CodeBrainMapper cbMapper = new CodeBrainMapper();
    private final CodeBrainEntityManager entityManager = new CodeBrainEntityManager(emfManager);
    private String entity;
//    private Boolean security;

    public CodeBrainManager() {
    }

    public static void CodeBrainManager() {
//        new CodeBrainManager();
    }

    public Users authenticate(String login, String password) throws SQLException {
        EntityManager em = entityManager.getEntityManager();
        try {

//            UsersJpaController userCtrl = new UsersJpaController(emfManager);
//        Users user = userCtrl.authentificate(login, MD5(password));
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery cq = cb.createQuery();

            Root<Users> rt = cq.from(Users.class);

            cq = cq.select(rt).where(cb.equal(rt.get("login"), login),
                    cb.equal(rt.get("password"), MD5(password)));

            Query q = em.createQuery(cq);
            Users user = (Users) q.getSingleResult();

            GlobalParameters.addVar(User.toString(), user);

            getWidgetSecurity(user);

            mainForm.loadMenu();
            mainForm.initStatus(Boolean.TRUE);

            return user;

        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public void getWidgetSecurity(Users userConnected) throws SQLException {
        WidgetJpaController widgetCtrl = new WidgetJpaController(emfManager);

//        widgetSecurity = widgetCtrl.findWidgetFromLevel(userConnected.getLevelsId());
//    public List<Widget> findWidgetFromLevel(Levels level) {
        EntityManager em = entityManager.getEntityManager();
        try {
            Levels level = userConnected.getLevelsId();
            if (level != null) {
                CriteriaBuilder cb = em.getCriteriaBuilder();

                CriteriaQuery cq = cb.createQuery();

                Root<Widget> rt = cq.from(Widget.class);

//            cq = cq.select(rt).where(cb.equal(rt.get("login"), login));
                Query q = em.createQuery(cq);

                List<Widget> listTmp = q.getResultList();

                widgetSecurity = listTmp.stream().filter(w -> w.getLevelsId().getCode() >= level.getCode()).collect(Collectors.toList());

//            List<Person> beerDrinkers = persons.stream()
//                    .filter(p -> p.getAge() > 16).collect(Collectors.toList());
//            return list;
//            }
//            else
//                return null;
                widgetSecurity.forEach((w) -> {
                    System.out.println(w.toString());
                });

                GlobalParameters.addVar("widgets", widgetSecurity);
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
//            return null;
        } finally {
            em.close();
        }
//    }
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
        if (GlobalParameters.getVar(User.toString()) == null) {
            login = new LoginForm(this, mainForm, true);
            login.setVisible(true);
        } else {
            reLogin = new ReLoginForm(this, mainForm, true, ((Users) GlobalParameters.getVar(User.toString())).getLogin());
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
        if (GlobalParameters.getVar(User.toString()) == null) {

            id = "CB-" + Date.from(Instant.MIN).toString();
        } else {
            id = ((Users) GlobalParameters.getVar(User.toString())).getLogin();
        }

        return id + new UID().toString();
    }

    public void createEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        formDatas.put(entity.toLowerCase() + "Id", generateUIDPrimaryKey());

        formDatas.put("userCreated", GlobalParameters.getVar(User.toString()));
        formDatas.put("dtCreated", new Date());
        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.getVar(User.toString()));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formDatas);
    }

    public void dupplicateEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        formDatas.put(entity.toLowerCase() + "Id", generateUIDPrimaryKey());

        formDatas.put("userCreated", GlobalParameters.getVar(User.toString()));
        formDatas.put("dtCreated", new Date());
        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.getVar(User.toString()));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formDatas);
    }

    public void updateEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Update.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.getVar(User.toString()));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "edit", formDatas);
    }

    public void deleteEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Delete.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.getVar(User.toString()));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "edit", formDatas);
    }

    public void getEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "find" + entity, formDatas);
    }

    public void getListEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formDatas);
    }

    public void executeMethod(String className, String methodName, Object modelFinal) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {

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

        }

        Class[] paramsClass = new Class[params.size()];
        for (int i = 0; i < params.size(); i++) {
            paramsClass[i] = params.get(i).getClass();
        }

        entityMethod.invoke(instance, params.toArray()); // pass args

    }

    public Object getObjectByExecuteMethod(String className, String methodName, final Object objectEntity) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        java.lang.reflect.Method entityMethod = null;

        for (java.lang.reflect.Method m : objectEntity.getClass().getMethods()) {
            if (m.getName().equals(methodName)) {
                entityMethod = m;
                break;
            }
        }

        Object value = entityMethod.invoke(objectEntity);

        return value;

    }

    public List getList(String entity, HashMap args) throws Exception {
        args.put("stateDb=!", EnumStatus.Business_Status_StateDb_Delete);
        return entityManager.getList(entity, args);
    }

    public List getList(String entity, String filter, HashMap args) throws Exception {
        args.put("stateDb=!", EnumStatus.Business_Status_StateDb_Delete);
        return entityManager.getList(entity, args);
    }

    public Object getEntity(String entity, String entityId) throws Exception {
        String criteria = "entity." + entity.toLowerCase() + "Id=:arg0";
        return getListEntity(entity, criteria, entityId).get(0);
    }

    public Object convertToObject(Object obj, String entity) throws ClassNotFoundException {

        String className = "cm.codebrain.main.business.entitie." + entity;

        Class<?> cl = Class.forName(className); // convert string classname to class
        return cbMapper.mapper(obj, cl);
    }

    public Object convertToObject(Object obj, Class cl) {
        return cbMapper.mapper(obj, cl);
    }

    public List convertToListObject(List list, Class cl) {
        List result = new ArrayList();
        list.forEach((obj) -> {
            result.add(cbMapper.mapper(obj, cl));
        });
        return result;
    }

    public String getUpperValue(String value) {
        return value.substring(0, 1).toUpperCase()
                + value.substring(1, value.length());
    }

    public String getLowerValue(String value) {
        return value.substring(0, 1).toLowerCase()
                + value.substring(1, value.length());
    }

    public List getListEntity(String entity, String criteria,
            Object... args) throws Exception {

        HashMap mod;
        String ejbql = criteria == null ? "from " + getUpperValue(entity) + " entity" : "from " + getUpperValue(entity) + " entity where " + criteria;

        Object[] objects = new Object[args.length];

        try {
            for (int i = 0; i <= args.length - 1; i++) {
                if (args[i] instanceof HashMap) {
                    mod = (HashMap) args[i];
                    objects[i] = convertToObject(mod.get(Entity).toString(), entity);
                } else {
                    objects[i] = args[i];
                }
            }

            List<Object> lstObjects = (List<Object>) entityManager
                    .getList(ejbql, objects);

            return lstObjects;
        } catch (Exception e) {
            throw new CodeBrainExceptions(e);
        }
    }

    public Object getObjectInvoke(Object objectConverted, String entity, String methodName) {
        Object value = null;
        try {
            if (!methodName.startsWith("get")) {
                methodName = "get" + getUpperValue(methodName);
            }
            value = getObjectByExecuteMethod("cm.codebrain.main.business.entitie." + entity, methodName, objectConverted);
        } catch (ClassNotFoundException | NullPointerException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | InstantiationException ex) {
            Logger.getLogger(CodeBrainManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return value;
    }
}
