/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.entitie.Classe;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Enseignant;
import cm.codebrain.main.business.entitie.Cours;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class CoursJpaController extends CodeBrainEntityManager implements Serializable {

    public CoursJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Cours cours) throws PreexistingEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Groupe groupeId = cours.getGroupeId();
            if (groupeId != null) {
                groupeId = (Groupe) refreshEntity(groupeId.getClass(), groupeId.getGroupeId());
                cours.setGroupeId(groupeId);
            }
            Classe classeId = cours.getClasseId();
            if (classeId != null) {
                classeId = (Classe) refreshEntity(classeId.getClass(), classeId.getClasseId());
                cours.setClasseId(classeId);
            }
            Users userModified = cours.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                cours.setUserModified(userModified);
            }
            Users userCreated = cours.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                cours.setUserCreated(userCreated);
            }
            persist(cours);
//            if (groupeId != null) {
//                groupeId.getCoursSet().add(cours);
//                groupeId = em.merge(groupeId);
//            }
//            if (classeId != null) {
//                classeId.getCoursSet().add(cours);
//                classeId = em.merge(classeId);
//            }
//            if (userModified != null) {
//                userModified.getCoursSet().add(cours);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getCoursSet().add(cours);
//                userCreated = em.merge(userCreated);
//            }
//            for (Enseignant enseignantSetEnseignant : cours.getEnseignantSet()) {
//                Cours oldCoursIdOfEnseignantSetEnseignant = enseignantSetEnseignant.getCoursId();
//                enseignantSetEnseignant.setCoursId(cours);
//                enseignantSetEnseignant = em.merge(enseignantSetEnseignant);
//                if (oldCoursIdOfEnseignantSetEnseignant != null) {
//                    oldCoursIdOfEnseignantSetEnseignant.getEnseignantSet().remove(enseignantSetEnseignant);
//                    oldCoursIdOfEnseignantSetEnseignant = em.merge(oldCoursIdOfEnseignantSetEnseignant);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCours(cours.getCoursId()) != null) {
                throw new PreexistingEntityException("Cours " + cours + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Cours cours) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Cours persistentCours = (Cours) find(Cours.class, cours.getCoursId());
            Groupe groupeIdOld = persistentCours.getGroupeId();
            Groupe groupeIdNew = cours.getGroupeId();
            Classe classeIdOld = persistentCours.getClasseId();
            Classe classeIdNew = cours.getClasseId();
            Users userModifiedOld = persistentCours.getUserModified();
            Users userModifiedNew = cours.getUserModified();
            Users userCreatedOld = persistentCours.getUserCreated();
            Users userCreatedNew = cours.getUserCreated();
//            List<String> illegalOrphanMessages = null;
//            for (Enseignant enseignantSetOldEnseignant : enseignantSetOld) {
//                if (!enseignantSetNew.contains(enseignantSetOldEnseignant)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Enseignant " + enseignantSetOldEnseignant + " since its coursId field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (groupeIdNew != null) {
                groupeIdNew = (Groupe) refreshEntity(groupeIdNew.getClass(), groupeIdNew.getGroupeId());
                cours.setGroupeId(groupeIdNew);
            }
            if (classeIdNew != null) {
                classeIdNew = (Classe) refreshEntity(classeIdNew.getClass(), classeIdNew.getClasseId());
                cours.setClasseId(classeIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                cours.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                cours.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                cours.setUserCreated(userCreatedOld);
            }
            cours = (Cours) merge(cours);
//            if (groupeIdOld != null && !groupeIdOld.equals(groupeIdNew)) {
//                groupeIdOld.getCoursSet().remove(cours);
//                groupeIdOld = em.merge(groupeIdOld);
//            }
//            if (groupeIdNew != null && !groupeIdNew.equals(groupeIdOld)) {
//                groupeIdNew.getCoursSet().add(cours);
//                groupeIdNew = em.merge(groupeIdNew);
//            }
//            if (classeIdOld != null && !classeIdOld.equals(classeIdNew)) {
//                classeIdOld.getCoursSet().remove(cours);
//                classeIdOld = em.merge(classeIdOld);
//            }
//            if (classeIdNew != null && !classeIdNew.equals(classeIdOld)) {
//                classeIdNew.getCoursSet().add(cours);
//                classeIdNew = em.merge(classeIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getCoursSet().remove(cours);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getCoursSet().add(cours);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getCoursSet().remove(cours);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getCoursSet().add(cours);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Enseignant enseignantSetNewEnseignant : enseignantSetNew) {
//                if (!enseignantSetOld.contains(enseignantSetNewEnseignant)) {
//                    Cours oldCoursIdOfEnseignantSetNewEnseignant = enseignantSetNewEnseignant.getCoursId();
//                    enseignantSetNewEnseignant.setCoursId(cours);
//                    enseignantSetNewEnseignant = em.merge(enseignantSetNewEnseignant);
//                    if (oldCoursIdOfEnseignantSetNewEnseignant != null && !oldCoursIdOfEnseignantSetNewEnseignant.equals(cours)) {
//                        oldCoursIdOfEnseignantSetNewEnseignant.getEnseignantSet().remove(enseignantSetNewEnseignant);
//                        oldCoursIdOfEnseignantSetNewEnseignant = em.merge(oldCoursIdOfEnseignantSetNewEnseignant);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cours.getCoursId();
                if (findCours(id) == null) {
                    throw new NonexistentEntityException("The cours with id " + id + " no longer exists.");
                }
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Cours cours;
//            try {
//                cours = em.getReference(Cours.class, id);
//                cours.getCoursId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The cours with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Enseignant> enseignantSetOrphanCheck = cours.getEnseignantSet();
//            for (Enseignant enseignantSetOrphanCheckEnseignant : enseignantSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Cours (" + cours + ") cannot be destroyed since the Enseignant " + enseignantSetOrphanCheckEnseignant + " in its enseignantSet field has a non-nullable coursId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Groupe groupeId = cours.getGroupeId();
//            if (groupeId != null) {
//                groupeId.getCoursSet().remove(cours);
//                groupeId = em.merge(groupeId);
//            }
//            Classe classeId = cours.getClasseId();
//            if (classeId != null) {
//                classeId.getCoursSet().remove(cours);
//                classeId = em.merge(classeId);
//            }
//            Users userModified = cours.getUserModified();
//            if (userModified != null) {
//                userModified.getCoursSet().remove(cours);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = cours.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getCoursSet().remove(cours);
//                userCreated = em.merge(userCreated);
//            }
            remove(Cours.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Cours> findCoursEntities() {
        return findCoursEntities(true, -1, -1);
    }

    public List<Cours> findCoursEntities(int maxResults, int firstResult) {
        return findCoursEntities(false, maxResults, firstResult);
    }

    private List<Cours> findCoursEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cours.class));
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

    public Cours findCours(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cours.class, id);
        } finally {
            em.close();
        }
    }

    public int getCoursCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cours> rt = cq.from(Cours.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
