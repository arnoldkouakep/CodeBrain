/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import cm.codebrain.main.business.entitie.Enseignant;
import cm.codebrain.main.business.entitie.Profession;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class EnseignantJpaController extends CodeBrainEntityManager implements Serializable {

    public EnseignantJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Enseignant enseignant) throws PreexistingEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Profession professionId = enseignant.getProfessionId();
            if (professionId != null) {
                professionId = (Profession) refreshEntity(professionId.getClass(), professionId.getProfessionId());
                enseignant.setProfessionId(professionId);
            }
            Users userModified = enseignant.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                enseignant.setUserModified(userModified);
            }
            Users userCreated = enseignant.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                enseignant.setUserCreated(userCreated);
            }
            persist(enseignant);
//            if (coursId != null) {
//                coursId.getEnseignantSet().add(enseignant);
//                coursId = em.merge(coursId);
//            }
//            if (userModified != null) {
//                userModified.getEnseignantSet().add(enseignant);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getEnseignantSet().add(enseignant);
//                userCreated = em.merge(userCreated);
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEnseignant(enseignant.getEnseignantId()) != null) {
                throw new PreexistingEntityException("Enseignant " + enseignant + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Enseignant enseignant) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Enseignant persistentEnseignant = (Enseignant) find(Enseignant.class, enseignant.getEnseignantId());
//            Profession professionIdOld = persistentEnseignant.getProfessionId();
            Profession professionIdNew = enseignant.getProfessionId();
//            Users userModifiedOld = persistentEnseignant.getUserModified();
            Users userModifiedNew = enseignant.getUserModified();
            Users userCreatedOld = persistentEnseignant.getUserCreated();
            Users userCreatedNew = enseignant.getUserCreated();
            if (professionIdNew != null) {
                professionIdNew = (Profession) refreshEntity(professionIdNew.getClass(), professionIdNew.getProfessionId());
                enseignant.setProfessionId(professionIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                enseignant.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                enseignant.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                enseignant.setUserCreated(userCreatedOld);
            }
            enseignant = (Enseignant) merge(enseignant);
//            if (coursIdOld != null && !coursIdOld.equals(coursIdNew)) {
//                coursIdOld.getEnseignantSet().remove(enseignant);
//                coursIdOld = em.merge(coursIdOld);
//            }
//            if (coursIdNew != null && !coursIdNew.equals(coursIdOld)) {
//                coursIdNew.getEnseignantSet().add(enseignant);
//                coursIdNew = em.merge(coursIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getEnseignantSet().remove(enseignant);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getEnseignantSet().add(enseignant);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getEnseignantSet().remove(enseignant);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getEnseignantSet().add(enseignant);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = enseignant.getEnseignantId();
                if (findEnseignant(id) == null) {
                    throw new NonexistentEntityException("The enseignant with id " + id + " no longer exists.");
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
//            Enseignant enseignant;
//            try {
//                enseignant = em.getReference(Enseignant.class, id);
//                enseignant.getEnseignantId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The enseignant with id " + id + " no longer exists.", enfe);
//            }
//            Cours coursId = enseignant.getCoursId();
//            if (coursId != null) {
//                coursId.getEnseignantSet().remove(enseignant);
//                coursId = em.merge(coursId);
//            }
//            Users userModified = enseignant.getUserModified();
//            if (userModified != null) {
//                userModified.getEnseignantSet().remove(enseignant);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = enseignant.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getEnseignantSet().remove(enseignant);
//                userCreated = em.merge(userCreated);
//            }
            remove(Enseignant.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Enseignant> findEnseignantEntities() {
        return findEnseignantEntities(true, -1, -1);
    }

    public List<Enseignant> findEnseignantEntities(int maxResults, int firstResult) {
        return findEnseignantEntities(false, maxResults, firstResult);
    }

    private List<Enseignant> findEnseignantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enseignant.class));
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

    public Enseignant findEnseignant(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enseignant.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnseignantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enseignant> rt = cq.from(Enseignant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
