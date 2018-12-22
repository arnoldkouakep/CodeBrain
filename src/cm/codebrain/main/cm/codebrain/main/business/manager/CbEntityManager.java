/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author KSA-INET
 */
public class CbEntityManager {

    private String key;
    private CriteriaQuery criteriaQuery;
    
    private final String inf = "<";
    private final String infEqual = "<=";
    private final String supEqual = ">=";
    private final String sup = ">";
    private final String equal = "=";
    private final String equalDouble = "==";
    private final String diff = "<>";
    private final String isNull = "--";
    private final String like = "%";
    private final String btwn = "[]";
    
    
    public CbEntityManager(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List getList(String entity, HashMap args) throws Exception{
        
        Class<?> classe = Class.forName(entity);
        
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            criteriaQuery = cb.createQuery();

            Root rt = criteriaQuery.from(classe);

            criteriaQuery = criteriaQuery.select(rt);
            
            args.keySet().stream().map((ky) -> {
            key = ky.toString();
                return ky;
            }).forEachOrdered((value)->{
                if(key.startsWith(inf)){
                    criteriaQuery = criteriaQuery.where(cb.lessThan(rt.get(key), (Comparable) value));
                }else if(key.startsWith(infEqual)){
                    criteriaQuery = criteriaQuery.where(cb.lessThanOrEqualTo(rt.get(key), (Comparable) value));
                }else if(key.startsWith(sup)){
                    criteriaQuery = criteriaQuery.where(cb.greaterThan(rt.get(key), (Comparable) value));
                }else if(key.startsWith(supEqual)){
                    criteriaQuery = criteriaQuery.where(cb.greaterThanOrEqualTo(rt.get(key), (Comparable) value));
                }else if(key.startsWith(equal) || key.startsWith(equalDouble)){
                    criteriaQuery = criteriaQuery.where(cb.equal(rt.get(key), value));
                }else if(key.startsWith(like)){
                    criteriaQuery = criteriaQuery.where(cb.like((Expression<String>) rt.get(key), value.toString()));
                }else if(key.startsWith(isNull) || value == null){
                    criteriaQuery = criteriaQuery.where(cb.isNull(rt.get(key)), cb.isEmpty(rt.get(key)));
                }else if(key.startsWith(diff)){
                    criteriaQuery = criteriaQuery.where(cb.notEqual(rt.get(key), value));
                }else if(key.startsWith(btwn)){
                    Object[] btwValue = (Object[])value;
                    criteriaQuery = criteriaQuery.where(cb.between(rt.get(key), (Comparable) btwValue[0], (Comparable) btwValue[1]));
                }
                
            });

            Query query = em.createQuery(criteriaQuery);
            return query.getResultList();
        }finally {
            em.close();
        }
    }
}
