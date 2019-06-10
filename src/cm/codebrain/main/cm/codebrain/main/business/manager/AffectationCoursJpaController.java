/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import cm.codebrain.main.business.entitie.AffectationCours;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Cours;
import cm.codebrain.main.business.entitie.Enseignant;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class AffectationCoursJpaController extends CodeBrainEntityManager implements Serializable {

    public AffectationCoursJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(AffectationCours affectationCours) throws PreexistingEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Cours coursId = affectationCours.getCoursId();
            if (coursId != null) {
                coursId = (Cours) refreshEntity(coursId.getClass(), coursId.getCoursId());
                affectationCours.setCoursId(coursId);
            }
            Enseignant enseignantId = affectationCours.getEnseignantId();
            if (enseignantId != null) {
                enseignantId = (Enseignant) refreshEntity(enseignantId.getClass(), enseignantId.getEnseignantId());
                affectationCours.setEnseignantId(enseignantId);
            }
            Users userModified = affectationCours.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                affectationCours.setUserModified(userModified);
            }
            Users userCreated = affectationCours.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                affectationCours.setUserCreated(userCreated);
            }
            persist(affectationCours);
//            if (coursId != null) {
//                coursId.getAffectationCoursSet().add(affectationCours);
//                coursId = em.merge(coursId);
//            }
//            if (enseignantId != null) {
//                enseignantId.getAffectationCoursSet().add(affectationCours);
//                enseignantId = em.merge(enseignantId);
//            }
//            if (userModified != null) {
//                userModified.getAffectationCoursSet().add(affectationCours);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getAffectationCoursSet().add(affectationCours);
//                userCreated = em.merge(userCreated);
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAffectationCours(affectationCours.getAffectationCoursId()) != null) {
                throw new PreexistingEntityException("AffectationCours " + affectationCours + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(AffectationCours affectationCours) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            AffectationCours persistentAffectationCours = (AffectationCours) find(AffectationCours.class, affectationCours.getAffectationCoursId());
//            Cours coursIdOld = persistentAffectationCours.getCoursId();
            Cours coursIdNew = affectationCours.getCoursId();
//            Enseignant enseignantIdOld = persistentAffectationCours.getEnseignantId();
            Enseignant enseignantIdNew = affectationCours.getEnseignantId();
//            Users userModifiedOld = persistentAffectationCours.getUserModified();
            Users userModifiedNew = affectationCours.getUserModified();
            Users userCreatedOld = persistentAffectationCours.getUserCreated();
            Users userCreatedNew = affectationCours.getUserCreated();
            if (coursIdNew != null) {
                coursIdNew = (Cours) refreshEntity(coursIdNew.getClass(), coursIdNew.getCoursId());
                affectationCours.setCoursId(coursIdNew);
            }
            if (enseignantIdNew != null) {
                enseignantIdNew = (Enseignant) refreshEntity(enseignantIdNew.getClass(), enseignantIdNew.getEnseignantId());
                affectationCours.setEnseignantId(enseignantIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                affectationCours.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                affectationCours.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                affectationCours.setUserCreated(userCreatedOld);
            }
            affectationCours = (AffectationCours) merge(affectationCours);
//            if (coursIdOld != null && !coursIdOld.equals(coursIdNew)) {
//                coursIdOld.getAffectationCoursSet().remove(affectationCours);
//                coursIdOld = em.merge(coursIdOld);
//            }
//            if (coursIdNew != null && !coursIdNew.equals(coursIdOld)) {
//                coursIdNew.getAffectationCoursSet().add(affectationCours);
//                coursIdNew = em.merge(coursIdNew);
//            }
//            if (enseignantIdOld != null && !enseignantIdOld.equals(enseignantIdNew)) {
//                enseignantIdOld.getAffectationCoursSet().remove(affectationCours);
//                enseignantIdOld = em.merge(enseignantIdOld);
//            }
//            if (enseignantIdNew != null && !enseignantIdNew.equals(enseignantIdOld)) {
//                enseignantIdNew.getAffectationCoursSet().add(affectationCours);
//                enseignantIdNew = em.merge(enseignantIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getAffectationCoursSet().remove(affectationCours);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getAffectationCoursSet().add(affectationCours);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getAffectationCoursSet().remove(affectationCours);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getAffectationCoursSet().add(affectationCours);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = affectationCours.getAffectationCoursId();
                if (findAffectationCours(id) == null) {
                    throw new NonexistentEntityException("The affectationCours with id " + id + " no longer exists.");
                }
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            AffectationCours affectationCours;
//            try {
//                affectationCours = em.getReference(AffectationCours.class, id);
//                affectationCours.getAffectationCoursId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The affectationCours with id " + id + " no longer exists.", enfe);
//            }
//            Cours coursId = affectationCours.getCoursId();
//            if (coursId != null) {
//                coursId.getAffectationCoursSet().remove(affectationCours);
//                coursId = em.merge(coursId);
//            }
//            Enseignant enseignantId = affectationCours.getEnseignantId();
//            if (enseignantId != null) {
//                enseignantId.getAffectationCoursSet().remove(affectationCours);
//                enseignantId = em.merge(enseignantId);
//            }
//            Users userModified = affectationCours.getUserModified();
//            if (userModified != null) {
//                userModified.getAffectationCoursSet().remove(affectationCours);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = affectationCours.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getAffectationCoursSet().remove(affectationCours);
//                userCreated = em.merge(userCreated);
//            }
            remove(AffectationCours.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<AffectationCours> findAffectationCoursEntities() {
        return findAffectationCoursEntities(true, -1, -1);
    }

    public List<AffectationCours> findAffectationCoursEntities(int maxResults, int firstResult) {
        return findAffectationCoursEntities(false, maxResults, firstResult);
    }

    private List<AffectationCours> findAffectationCoursEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AffectationCours.class));
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

    public AffectationCours findAffectationCours(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AffectationCours.class, id);
        } finally {
            em.close();
        }
    }

    public int getAffectationCoursCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AffectationCours> rt = cq.from(AffectationCours.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
