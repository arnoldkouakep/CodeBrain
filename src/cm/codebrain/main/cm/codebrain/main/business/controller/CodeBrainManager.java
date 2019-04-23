/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import cm.codebrain.main.business.manager.*;
import cm.codebrain.main.business.entitie.*;
import cm.codebrain.main.business.enumerations.EnumError;
import cm.codebrain.main.business.enumerations.EnumLibelle;
import cm.codebrain.main.business.enumerations.EnumStatus;
import cm.codebrain.ui.application.MessageForm;
import cm.codebrain.ui.application.controller.Dictionnaire;
import cm.codebrain.ui.application.controller.GlobalParameters;
import cm.codebrain.ui.application.controller.Locale;
import cm.codebrain.ui.application.controller.StringUtils;
import cm.codebrain.ui.application.enumerations.EnumVariable;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Action;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Entity;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Master_Detail;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Detail_Master;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Field;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Model;
import static cm.codebrain.ui.application.enumerations.EnumVariable.User;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;
import cm.codebrain.ui.application.implement.Executable;
import cm.codebrain.ui.application.security.Loading;
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
    private final CodeBrainEntityManager entityManager;
    private String entity;

    public CodeBrainManager() {
        this.entityManager = new CodeBrainEntityManager();
        this.entityManager.setEntityManagerFactory(emfManager);
    }

    public static void CodeBrainManager() {
    }

    public Users authenticate(String login, String password) {
//        try {
            CriteriaBuilder cb = entityManager.getEntityManager().getCriteriaBuilder();

            CriteriaQuery cq = cb.createQuery();

            Root<Users> rt = cq.from(Users.class);

            cq = cq.select(rt).where(cb.equal(rt.get("login"), login),
                    cb.equal(rt.get("password"), MD5(password)));

            Query q = entityManager.getEntityManager().createQuery(cq);
            Users user = (Users) q.getSingleResult();

            GlobalParameters.add(User, user);

            getWidgetSecurity(user);

            mainForm.loadMenu();
            mainForm.initStatus(Boolean.TRUE);

                System.out.println("User : " + user.getLogin() + " Connecté.");
//            em.close();
            return user;

//        } catch (CodeBrainExceptions ex) {
            
//            throw CodeBrainExceptions.fromTypeExceptions(EnumError.UserLoginException); //CodeBrainExceptions(Dictionnaire.get(EnumError.UserLoginException));
//            System.out.println(ex.getLocalizedMessage());
//            return null;
//        } finally {
//        }
    }

    public void getWidgetSecurity(Users userConnected) {

        EntityManager em = entityManager.getEntityManagerFactory().createEntityManager();
//        try {
            Levels level = userConnected.getLevelsId();
            if (level != null) {
                CriteriaBuilder cb = em.getCriteriaBuilder();

                CriteriaQuery cq = cb.createQuery();

                Root<Widget> rt = cq.from(Widget.class);

                Query q = em.createQuery(cq);

                List<Widget> listTmp = q.getResultList();

                widgetSecurity = listTmp.stream().filter(w -> w.getLevelsId().getCode() >= level.getCode()).collect(Collectors.toList());

                widgetSecurity.forEach((w) -> {
                    System.out.println(w.toString());
                });

                GlobalParameters.add("widgets", widgetSecurity);
            }
//        } catch (Exception ex) {
//            System.out.println(ex.getLocalizedMessage());
//        } finally {
            em.close();
//        }
    }

    public void logout() {
        if(!disConnexion()){
            GlobalParameters.init();
            restart();
        }
    }

    public Boolean disConnexion(){
        Loading.show(null, Dictionnaire.get(EnumLibelle.Business_Libelle_Deconnexion), new Executable() {
                @Override
                public Object execute() throws Exception {
                    connexion = true;
                    if(entityManager.getEntityManager().isOpen()) entityManager.getEntityManager().close();

                    Users user = (Users) GlobalParameters.get(User);

                    System.out.println("User : " + user.getLogin() + " Deconnecté.");
                    connexion = false;
                    return connexion;
                }

                @Override
                public void error(CodeBrainExceptions ex) {
                    MessageForm.showsError(Dictionnaire.get(EnumError.UserLoginException), "Message", false, null);
                }
            });
        return connexion;
    }
    
    public void quit() {
        if(!disConnexion()){
            GlobalParameters.init();
            System.exit(0);
        }
    }

    public String MD5(String md5) {
        return StringUtils.MD5encode(md5);
    }

    private boolean connexion;
    public void start() {
        
        Locale.initBundle();

        Loading.show(null, Dictionnaire.get(EnumLibelle.Business_Libelle_Connexion), new Executable() {
            @Override
            public Boolean execute() throws Exception {
                connexion = false;
                entityManager.setEntityManager(entityManager.getEntityManagerFactory().createEntityManager());
                connexion = true;
                return connexion;
            }

            @Override
            public void error(CodeBrainExceptions ex) {
                MessageForm.showsError(Dictionnaire.get(EnumError.UserLoginException), "Message", false, null);
            }
        });
        
        if(connexion){
            mainForm = new MainForm(this);

            mainForm.setVisible(true);
            if (GlobalParameters.get(User) == null) {
                login = new LoginForm(this, mainForm, true);
                login.setVisible(true);
            } else {
                reLogin = new ReLoginForm(this, mainForm, true, ((Users) GlobalParameters.get(User)).getLogin());
                reLogin.setVisible(true);
            }
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
        if (GlobalParameters.get(User) == null) {

            id = "CB-" + Date.from(Instant.MIN).toString();
        } else {
            id = ((Users) GlobalParameters.get(User)).getLogin();
        }

        return id + new UID().toString();
    }

    public void createEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        formDatas.put(getLowerValue(entity) + "Id", generateUIDPrimaryKey());

        formDatas.put("userCreated", GlobalParameters.get(User));
        formDatas.put("dtCreated", new Date());
        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.get(User));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formDatas);
    }

    public void createListEntity(String entity, List<HashMap> formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        for (HashMap formData : formDatas) {
            formData.put(getLowerValue(entity) + "Id", generateUIDPrimaryKey());

            formData.put("userCreated", GlobalParameters.get(User));
            formData.put("dtCreated", new Date());
            formData.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
            formData.put("dtModified", new Date());
            formData.put("userModified", GlobalParameters.get(User));

            executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formData);
        }
    }

    public void dupplicateEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        formDatas.put(getLowerValue(entity) + "Id", generateUIDPrimaryKey());

        formDatas.put("userCreated", GlobalParameters.get(User));
        formDatas.put("dtCreated", new Date());
        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.get(User));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formDatas);
    }

    public void dupplicateListEntity(String entity, List<HashMap> formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        for (HashMap formData : formDatas) {
            formData.put(getLowerValue(entity) + "Id", generateUIDPrimaryKey());

            formData.put("userCreated", GlobalParameters.get(User));
            formData.put("dtCreated", new Date());
            formData.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
            formData.put("dtModified", new Date());
            formData.put("userModified", GlobalParameters.get(User));

            executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "create", formData);
        }
    }

    public void updateEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Update.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.get(User));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "edit", formDatas);
    }

    public void updateListEntity(String entity, List<HashMap> formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        for (HashMap formData : formDatas) {
            formData.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Update.toString());
            formData.put("dtModified", new Date());
            formData.put("userModified", GlobalParameters.get(User));

            executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "edit", formData);
        }
    }

    public void deleteEntity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        formDatas.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Delete.toString());
        formDatas.put("dtModified", new Date());
        formDatas.put("userModified", GlobalParameters.get(User));

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "edit", formDatas);
    }

    public void deleteListEntity(String entity, List<HashMap> formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;
        for (HashMap formData : formDatas) {
            formData.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Delete.toString());
            formData.put("dtModified", new Date());
            formData.put("userModified", GlobalParameters.get(User));

            executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "edit", formData);
        }
    }

    public void delete2Entity(String entity, HashMap formDatas) throws ClassNotFoundException, NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        this.entity = entity;

        executeMethod("cm.codebrain.main.business.manager." + entity + "JpaController", "destroy", getIdValueObject(formDatas, entity));
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

        Constructor<?> constructor = controllerClass.getConstructor(EntityManager.class);
        
        Object instance = constructor.newInstance(entityManager.getEntityManager());

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
            Logger.getLogger(CodeBrainManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return value;
    }

    public void createMasterDetailEntity(String entity, HashMap formData, String detailEntity, List<HashMap> createListModels) throws Exception {
        /**
         *
         * Master
         *
         */

        this.entity = entity;
//        for (HashMap formData : formDatas) {
        formData.put(entity.toLowerCase() + "Id", generateUIDPrimaryKey());

        formData.put("userCreated", GlobalParameters.get(User));
        formData.put("dtCreated", new Date());
        formData.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
        formData.put("dtModified", new Date());
        formData.put("userModified", GlobalParameters.get(User));

        executeMethod("cm.codebrain.main.business.manager." + this.entity + "JpaController", "create", formData);
//        }
        /**
         *
         * Details
         *
         */

        this.entity = detailEntity;
        for (HashMap model : createListModels) {
            model.put(entity.toLowerCase() + "Id", generateUIDPrimaryKey());

            model.put("userCreated", GlobalParameters.get(User));
            model.put("dtCreated", new Date());
            model.put("stateDb", cm.codebrain.ui.application.enumerations.EnumLibelles.Business_Status_StateDb_Create.toString());
            model.put("dtModified", new Date());
            model.put("userModified", GlobalParameters.get(User));

            executeMethod("cm.codebrain.main.business.manager." + this.entity + "JpaController", "create", model);
        }
    }

    public void crud(String entity, HashMap modelFinal, EnumVariable etatAction, EnumVariable etatActionList, List<HashMap> listCreUp, List<HashMap> listDel) throws Exception {
        if (null != etatActionList) {
            
            entityManager.setEntityManager(entityManager.getEntityManagerFactory().createEntityManager());
            entityManager.getTransaction().begin();
            
            switch (etatActionList) {
                case List:
                    if (null != etatAction) {
                        switch (etatAction) {
                            case CREATE:
                                createEntity(entity, modelFinal);
                                break;
                            case MODIF:
                                updateEntity(entity, modelFinal);
                                break;
                            case DUPPLICATE:
                                dupplicateEntity(entity, modelFinal);
                                break;
                            case DELETE:
                                deleteEntity(entity, modelFinal);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case Master_Detail:
                    if (null != etatAction) {
                        switch (etatAction) {
                            case CREATE:
                                createEntity(entity, modelFinal);

                                for(HashMap modelMD2 : listCreUp){
                                    String tmpEntity = modelMD2.get(Entity).toString();
                                    String field = modelMD2.get(Field).toString();
                                    List<HashMap> CrUpList = (List<HashMap>) modelMD2.get(Model);
                                    
                                    for(HashMap modelCU : CrUpList){
                                        modelCU.put(field, modelFinal);
                                        if (getIdValueObject(modelCU, tmpEntity) == null) {
                                            try{createEntity(tmpEntity, modelCU);}
                                            catch(Exception ee){}
                                        } else {
                                            try{updateEntity(tmpEntity, modelCU);}
                                            catch(Exception ee){}
                                        }
                                    }
                                }
                                
                                for(HashMap modelMD2 : listDel){
                                    String tmpEntity = modelMD2.get(Entity).toString();
                                    String field = modelMD2.get(Field).toString();
                                    List<HashMap> DelList = (List<HashMap>) modelMD2.get(Model);
                                    
                                    for(HashMap modelDel : DelList){
                                        modelDel.put(field, null);
                                        
                                        try{updateEntity(tmpEntity, modelDel);}
                                        catch(Exception ee){}
                                    }
                                }
                                
                                break;
                            case MODIF:
                                updateEntity(entity, modelFinal);

                                for(HashMap modelMD2 : listCreUp){
                                    String tmpEntity = modelMD2.get(Entity).toString();
                                    String field = modelMD2.get(Field).toString();
                                    List<HashMap> CrUpList = (List<HashMap>) modelMD2.get(Model);
                                    
                                    for(HashMap modelCU : CrUpList){
                                        modelCU.put(field, modelFinal);
                                        if (getIdValueObject(modelCU, tmpEntity) == null) {
                                            try{createEntity(tmpEntity, modelCU);}
                                            catch(Exception ee){}
                                        } else {
                                            try{updateEntity(tmpEntity, modelCU);}
                                            catch(Exception ee){}
                                        }
                                    }
                                }
                                
                                for(HashMap modelMD2 : listDel){
                                    String tmpEntity = modelMD2.get(Entity).toString();
                                    String field = modelMD2.get(Field).toString();
                                    List<HashMap> DelList = (List<HashMap>) modelMD2.get(Model);
                                    
                                    for(HashMap modelDel : DelList){
                                        modelDel.put(field, null);
                                        
                                        try{updateEntity(tmpEntity, modelDel);}
                                        catch(Exception ee){}
                                    }
                                }
                                
                                break;
                            case DUPPLICATE:
                                dupplicateEntity(entity, modelFinal);

                                break;
                            case DELETE:
                                deleteEntity(entity, modelFinal);

                                for(HashMap modelMD2 : listDel){
                                    String tmpEntity = modelMD2.get(Entity).toString();
                                    String field = modelMD2.get(Field).toString();
                                    List<HashMap> DelList = (List<HashMap>) modelMD2.get(Model);
                                    
                                    for(HashMap modelDel : DelList){
                                        modelDel.put(field, null);
                                        
                                        try{deleteEntity(tmpEntity, modelDel);}
                                        catch(Exception ee){}
                                    }
                                }
                                
                                break;
                            default:
                                break;
                        }
                    }
                    
                    break;
//                if (createList) {
//                    makeModelDatas();
//
////                    modelFinals = tableModelObject.getModel();
//                    if (modelFinals == null) {
//                        modelFinals = (List<HashMap>) FormParameters.get(Model);
//                    }
////                    if (modelFinals == null) {
////                        modelFinals = new ArrayList<>();
////                        for (HashMap model : modelDataFinal) {
////                            makeModelDatas(model);
////                            modelFinals.add(modelFinal);
////                        }
////                }
//                    FormParameters.add(Model, modelFinals);
//                    if (null != etatAction) {
//                        switch (etatAction) {
//                            case CREATE:
//                                cbManager.createListEntity(entity, modelFinals);
//                                break;
//                            case MODIF:
//                                cbManager.updateListEntity(entity, modelFinals);
//                                break;
//                            case DUPPLICATE:
//                                cbManager.dupplicateListEntity(entity, modelFinals);
//                                break;
//                            case DELETE:
//                                cbManager.deleteListEntity(entity, modelFinals);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//
//                    /**
//                     *
//                     * To Do
//                     *
//                     *
//                     */
//                } else {
//                    if (etatAction == CREATE) {
//                        modelFinal = new HashMap<>();
//                    } else {
//                        modelFinal = (HashMap) FormParameters.get(Model);
//                    }
//
//                    makeModelData();
//
//                    if (null != etatAction) {
//                        switch (etatAction) {
//                            case CREATE:
//                                cbManager.createEntity(entity, modelFinal);
//                                break;
//                            case MODIF:
//                                cbManager.updateEntity(entity, modelFinal);
//                                break;
//                            case DUPPLICATE:
//                                cbManager.dupplicateEntity(entity, modelFinal);
//                                break;
//                            case DELETE:
//                                cbManager.deleteEntity(entity, modelFinal);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                }
//        }
                case Detail_Master:
                    break;
                default:
                    if (null != etatAction) {
                        switch (etatAction) {
                            case CREATE:
                                createEntity(entity, modelFinal);
                                break;
                            case MODIF:
                                updateEntity(entity, modelFinal);
                                break;
                            case DUPPLICATE:
                                dupplicateEntity(entity, modelFinal);
                                break;
                            case DELETE:
                                deleteEntity(entity, modelFinal);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
            }
            entityManager.getTransaction().commit();
            
            entityManager.getEntityManager().close();
        }
    }

    private Object getIdValueObject(HashMap model, String entity) {
        Object idValue = model.get(entity.toLowerCase() + "Id");
        return idValue;
    }
}
