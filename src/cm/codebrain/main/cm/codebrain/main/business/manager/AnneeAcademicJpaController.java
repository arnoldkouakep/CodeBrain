/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import cm.codebrain.main.business.entitie.AnneeAcademic;
import cm.codebrain.main.business.entitie.Users;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class AnneeAcademicJpaController extends CodeBrainEntityManager implements Serializable {

    public AnneeAcademicJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(AnneeAcademic anneeAcademic) throws PreexistingEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Users userModified = anneeAcademic.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                anneeAcademic.setUserModified(userModified);
            }
            Users userCreated = anneeAcademic.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                anneeAcademic.setUserCreated(userCreated);
            }
            persist(anneeAcademic);
//            if (userModified != null) {
//                userModified.getAnneeAcademicSet().add(anneeAcademic);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getAnneeAcademicSet().add(anneeAcademic);
//                userCreated = em.merge(userCreated);
//            }
////            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnneeAcademic(anneeAcademic.getAnneeAcademicId()) != null) {
                throw new PreexistingEntityException("AnneeAcademic " + anneeAcademic + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(AnneeAcademic anneeAcademic) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            AnneeAcademic persistentAnneeAcademic = (AnneeAcademic) find(AnneeAcademic.class, anneeAcademic.getAnneeAcademicId());
            Users userModifiedOld = persistentAnneeAcademic.getUserModified();
            Users userModifiedNew = anneeAcademic.getUserModified();
            Users userCreatedOld = persistentAnneeAcademic.getUserCreated();
            Users userCreatedNew = anneeAcademic.getUserCreated();
            if (userModifiedNew != null) {
                userModifiedNew =(Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                anneeAcademic.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                anneeAcademic.setUserCreated(userCreatedNew);
            }
            anneeAcademic = (AnneeAcademic) merge(anneeAcademic);
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getAnneeAcademicSet().remove(anneeAcademic);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getAnneeAcademicSet().add(anneeAcademic);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getAnneeAcademicSet().remove(anneeAcademic);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getAnneeAcademicSet().add(anneeAcademic);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = anneeAcademic.getAnneeAcademicId();
                if (findAnneeAcademic(id) == null) {
                    throw new NonexistentEntityException("The anneeAcademic with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
//                    em = getEntityManager();
//            em.getTransaction().begin();
//            AnneeAcademic anneeAcademic;
//            try {
//                anneeAcademic = em.getReference(AnneeAcademic.class, id);
//                anneeAcademic.getAnneeAcademicId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The anneeAcademic with id " + id + " no longer exists.", enfe);
//            }
//            Users userModified = anneeAcademic.getUserModified();
//            if (userModified != null) {
//                userModified.getAnneeAcademicSet().remove(anneeAcademic);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = anneeAcademic.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getAnneeAcademicSet().remove(anneeAcademic);
//                userCreated = em.merge(userCreated);
//            }
        remove(AnneeAcademic.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
    }

    public List<AnneeAcademic> findAnneeAcademicEntities() {
        return findAnneeAcademicEntities(true, -1, -1);
    }

    public List<AnneeAcademic> findAnneeAcademicEntities(int maxResults, int firstResult) {
        return findAnneeAcademicEntities(false, maxResults, firstResult);
    }

    private List<AnneeAcademic> findAnneeAcademicEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AnneeAcademic.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AnneeAcademic findAnneeAcademic(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AnneeAcademic.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnneeAcademicCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AnneeAcademic> rt = cq.from(AnneeAcademic.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
