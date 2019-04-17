/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.controller;

import cm.codebrain.main.business.enumerations.EnumStatus;
import cm.codebrain.ui.application.controller.GlobalParameters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Value;
import static cm.codebrain.ui.application.enumerations.EnumVariable.Indice;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author KSA-INET
 */
public class CodeBrainEntityManager {

    private String key;

    private final String entityPackage = "cm.codebrain.main.business.entitie.";
    private CriteriaQuery criteriaQuery;

    private final String inf = "<";
    private final String infEqual = "<=";
    private final String supEqual = ">=";
    private final String sup = ">";
    private final String equal = "=";
    private final String equalDouble = "==";
    private final String diff = "=!";
    private final String isNull = "--";
    private final String like = "%";
    private final String btwn = "[]";
    private EntityManager em;
    private EntityManagerFactory emf;

    public CodeBrainEntityManager() {
    }

    public CodeBrainEntityManager(EntityManager em) {
        this.em = em;
    }
    
    public EntityTransaction getTransaction(){
        return this.em.getTransaction();
    }
    
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return this.emf;
    }

    public EntityManager getEntityManager() {
//        if(this.emf==null){
//            this.emf = (EntityManagerFactory) GlobalParameters.get("emf");
//        }
        
        return this.em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void persist(Object entityObject) {
//        EntityManager em = null;

        try {
//            em = getEntityManager();
            
//            EntityTransaction tx = getTransaction();
            if(!getTransaction().isActive())
                getTransaction().begin();

            getEntityManager().persist(entityObject);

            getEntityManager().flush();
//          tx.commit();
        } catch (Exception ex) {
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public Object merge(Object entityObject) {

        try {
//            em = getEntityManager();
            
//            EntityTransaction tx = getEntityManager().getTransaction();
            if(!getTransaction().isActive())
                getTransaction().begin();

            entityObject = getEntityManager().merge(entityObject);

            getEntityManager().flush();
//            tx.commit();
        } catch (Exception ex) {
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }

        return entityObject;
    }

    public void remove(Class classValue, String id) {
//        EntityManager em = null;

        try {
//            em = getEntityManager();
            
//            EntityTransaction tx = em.getTransaction();
            if(!getTransaction().isActive())
                getTransaction().begin();

            Object entityObject;
//            try {
            entityObject = getEntityManager().getReference(classValue, id);
//                anneeAcademic.getAnneeAcademicId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The anneeAcademic with id " + id + " no longer exists.", enfe);
//            }
            getEntityManager().remove(entityObject);

            getEntityManager().flush();
//            tx.commit();
        } catch (Exception ex) {
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }

    }

    public Object refreshEntity(Class classValue, String id) {
//        EntityManager em = null;
        Object entityObject;
        try {
//            em = getEntityManager();
            
            if(!getTransaction().isActive())
                getTransaction().begin();
            
            entityObject = getEntityManager().getReference(classValue, id);
        } catch (Exception ex) {
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
        return entityObject;
    }

    public List getList(String entity, HashMap args) throws Exception {

        Class<?> classe = Class.forName(entityPackage.concat(entity));

//        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            criteriaQuery = cb.createQuery();

            Root rt = criteriaQuery.from(classe);

            criteriaQuery = criteriaQuery.select(rt);

            if (args != null && args.size() > 0) {
                args.keySet().stream().map((ky) -> {
                    key = ky.toString();
                    return ky;
                }).forEachOrdered((value) -> {
                    if (key.endsWith(inf)) {
                        key = key.substring(0, (key.length() - inf.length()));
                        criteriaQuery = criteriaQuery.where(cb.lessThan(rt.get(key), (Comparable) args.get(value)));
                    } else if (key.endsWith(infEqual)) {
                        key = key.substring(0, (key.length() - infEqual.length()));
                        criteriaQuery = criteriaQuery.where(cb.lessThanOrEqualTo(rt.get(key), (Comparable) args.get(value)));
                    } else if (key.endsWith(sup)) {
                        key = key.substring(0, (key.length() - sup.length()));
                        criteriaQuery = criteriaQuery.where(cb.greaterThan(rt.get(key), (Comparable) args.get(value)));
                    } else if (key.endsWith(supEqual)) {
                        key = key.substring(0, (key.length() - supEqual.length()));
                        criteriaQuery = criteriaQuery.where(cb.greaterThanOrEqualTo(rt.get(key), (Comparable) args.get(value)));
                    } else if (key.endsWith(equalDouble)) {
                        key = key.substring(0, (key.length() - equalDouble.length()));
                        criteriaQuery = criteriaQuery.where(cb.equal(rt.get(key), args.get(value)));
                    } else if (key.endsWith(equal)) {
                        key = key.substring(0, (key.length() - equal.length()));
                        criteriaQuery = criteriaQuery.where(cb.equal(rt.get(key), args.get(value)));
                    } else if (key.endsWith(like)) {
                        key = key.substring(0, (key.length() - like.length()));
                        criteriaQuery = criteriaQuery.where(cb.like((Expression<String>) rt.get(key), args.get(value).toString()));
                    } else if (key.endsWith(isNull) || args.get(value) == null) {
                        key = key.substring(0, (key.length() - isNull.length()));
                        criteriaQuery = criteriaQuery.where(cb.isNull(rt.get(key)), cb.isEmpty(rt.get(key)));
                    } else if (key.endsWith(diff)) {
                        key = key.substring(0, (key.length() - diff.length()));
                        criteriaQuery = criteriaQuery.where(cb.notEqual(rt.get(key), args.get(value)));
                    } else if (key.endsWith(btwn)) {
                        key = key.substring(0, (key.length() - btwn.length()));
                        Object[] btwValue = (Object[]) args.get(value);
                        criteriaQuery = criteriaQuery.where(cb.between(rt.get(key), (Comparable) btwValue[0], (Comparable) btwValue[1]));
                    }
                });
            }
            Query query = em.createQuery(criteriaQuery);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Object find(Class classValue, String id) {
//        EntityManager em = null;
        Object entityObject;
        try {
//            em = getEntityManager();
            
            if(!getTransaction().isActive())
                getTransaction().begin();
            
            entityObject = getEntityManager().find(classValue, id);
        } catch (Exception ex) {
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
        return entityObject;
    }

    public List getList(String entity, String filter, Object... paramsArgs) throws Exception {

        Class<?> classe = Class.forName(entityPackage.concat(entity));

        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            criteriaQuery = cb.createQuery();

            Root rt = criteriaQuery.from(classe);

            criteriaQuery = criteriaQuery.select(rt);

            Query query = em.createQuery(criteriaQuery);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List getList(String ejbql, Object... args) throws Exception {
        EntityManager em = getEntityManagerFactory().createEntityManager();

        Query query = null;
        int nbreArgs;
        Map arg;
        String sqlPlus = "";
        List<Map> lstMap = new ArrayList();

        int j = 0;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    ejbql = ejbql.replaceFirst("<>:arg" + j, " is not null");
                    ejbql = ejbql.replaceFirst("=:arg" + j, " is null");
                    j++;
                } else {
                    if (args[i] instanceof HashMap) {
                        arg = (HashMap) args[i];
                        arg.put(Indice, j);
                        lstMap.add(arg);
                        j++;
                    } else {
                        arg = new HashMap();
                        arg.put(Indice, j);
                        arg.put(Value, args[i]);
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

        if (ejbql.indexOf("group by") > 0) {
            finEjb = "group by";
        } else if (ejbql.indexOf("order by") > 0) {
            finEjb = "order by";
        }

        //sqlPlus="";
        if (finEjb.length() > 0) {
            ejbql = ejbql.replace(finEjb, sqlPlus + " " + finEjb);
        } else {
            ejbql = ejbql + sqlPlus;
        }

        try {

            query = em.createQuery(ejbql);

        } catch (Exception e) {
        }
        List list = null;
        //ejbql=ejbql;
        String argsEvent = "";

        if (lstMap.size() > 0) {
            for (Map map : lstMap) {
                query.setParameter("arg" + map.get(Indice), map.get(Value));
            }
        }
        query.setParameter("arg" + nbreArgs,
                EnumStatus.Business_Status_StateDb_Delete.toString());
        try {
            list = query.getResultList();

            return list;

        } catch (Exception e) {
            return null;
        }

    }

}
