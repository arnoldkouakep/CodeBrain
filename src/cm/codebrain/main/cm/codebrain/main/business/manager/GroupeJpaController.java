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
import cm.codebrain.main.business.entitie.Classe;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Salle;
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
public class GroupeJpaController extends CodeBrainEntityManager implements Serializable {

    public GroupeJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Groupe groupe) throws PreexistingEntityException, Exception {
        if (groupe.getSalleSet() == null) {
            groupe.setSalleSet(new HashSet<Salle>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Classe classeId = groupe.getClasseId();
            if (classeId != null) {
                classeId = (Classe) refreshEntity(classeId.getClass(), classeId.getClasseId());
                groupe.setClasseId(classeId);
            }
            Users userModified = groupe.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                groupe.setUserModified(userModified);
            }
            Users userCreated = groupe.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                groupe.setUserCreated(userCreated);
            }
            Set<Salle> attachedSalleSet = new HashSet<Salle>();
            for (Salle salleSetSalleToAttach : groupe.getSalleSet()) {
                salleSetSalleToAttach = (Salle) refreshEntity(salleSetSalleToAttach.getClass(), salleSetSalleToAttach.getSalleId());
                attachedSalleSet.add(salleSetSalleToAttach);
            }
            groupe.setSalleSet(attachedSalleSet);
            persist(groupe);
//            if (classeId != null) {
//                classeId.getGroupeSet().add(groupe);
//                classeId = em.merge(classeId);
//            }
//            if (userModified != null) {
//                userModified.getGroupeSet().add(groupe);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getGroupeSet().add(groupe);
//                userCreated = em.merge(userCreated);
//            }
//            for (Salle salleSetSalle : groupe.getSalleSet()) {
//                Groupe oldGroupeIdOfSalleSetSalle = salleSetSalle.getGroupeId();
//                salleSetSalle.setGroupeId(groupe);
//                salleSetSalle = em.merge(salleSetSalle);
//                if (oldGroupeIdOfSalleSetSalle != null) {
//                    oldGroupeIdOfSalleSetSalle.getSalleSet().remove(salleSetSalle);
//                    oldGroupeIdOfSalleSetSalle = em.merge(oldGroupeIdOfSalleSetSalle);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGroupe(groupe.getGroupeId()) != null) {
                throw new PreexistingEntityException("Groupe " + groupe + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Groupe groupe) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Groupe persistentGroupe = (Groupe) find(Groupe.class, groupe.getGroupeId());
//            Classe classeIdOld = persistentGroupe.getClasseId();
            Classe classeIdNew = groupe.getClasseId();
//            Users userModifiedOld = persistentGroupe.getUserModified();
            Users userModifiedNew = groupe.getUserModified();
            Users userCreatedOld = persistentGroupe.getUserCreated();
            Users userCreatedNew = groupe.getUserCreated();
//            Set<Salle> salleSetOld = persistentGroupe.getSalleSet();
            Set<Salle> salleSetNew = groupe.getSalleSet();
//            List<String> illegalOrphanMessages = null;
//            for (Salle salleSetOldSalle : salleSetOld) {
//                if (!salleSetNew.contains(salleSetOldSalle)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Salle " + salleSetOldSalle + " since its groupeId field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (classeIdNew != null) {
                classeIdNew = (Classe) refreshEntity(classeIdNew.getClass(), classeIdNew.getClasseId());
                groupe.setClasseId(classeIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                groupe.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                groupe.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                groupe.setUserCreated(userCreatedOld);
            }
            Set<Salle> attachedSalleSetNew = new HashSet<Salle>();
            for (Salle salleSetNewSalleToAttach : salleSetNew) {
                salleSetNewSalleToAttach = (Salle) refreshEntity(salleSetNewSalleToAttach.getClass(), salleSetNewSalleToAttach.getSalleId());
                attachedSalleSetNew.add(salleSetNewSalleToAttach);
            }
            salleSetNew = attachedSalleSetNew;
            groupe.setSalleSet(salleSetNew);
            groupe = (Groupe) merge(groupe);
//            if (classeIdOld != null && !classeIdOld.equals(classeIdNew)) {
//                classeIdOld.getGroupeSet().remove(groupe);
//                classeIdOld = em.merge(classeIdOld);
//            }
//            if (classeIdNew != null && !classeIdNew.equals(classeIdOld)) {
//                classeIdNew.getGroupeSet().add(groupe);
//                classeIdNew = em.merge(classeIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getGroupeSet().remove(groupe);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getGroupeSet().add(groupe);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getGroupeSet().remove(groupe);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getGroupeSet().add(groupe);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Salle salleSetNewSalle : salleSetNew) {
//                if (!salleSetOld.contains(salleSetNewSalle)) {
//                    Groupe oldGroupeIdOfSalleSetNewSalle = salleSetNewSalle.getGroupeId();
//                    salleSetNewSalle.setGroupeId(groupe);
//                    salleSetNewSalle = em.merge(salleSetNewSalle);
//                    if (oldGroupeIdOfSalleSetNewSalle != null && !oldGroupeIdOfSalleSetNewSalle.equals(groupe)) {
//                        oldGroupeIdOfSalleSetNewSalle.getSalleSet().remove(salleSetNewSalle);
//                        oldGroupeIdOfSalleSetNewSalle = em.merge(oldGroupeIdOfSalleSetNewSalle);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = groupe.getGroupeId();
                if (findGroupe(id) == null) {
                    throw new NonexistentEntityException("The groupe with id " + id + " no longer exists.");
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
//            Groupe groupe;
//            try {
//                groupe = (Groupe) refreshEntity(Groupe.class, id);
//                groupe.getGroupeId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The groupe with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Salle> salleSetOrphanCheck = groupe.getSalleSet();
//            for (Salle salleSetOrphanCheckSalle : salleSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Groupe (" + groupe + ") cannot be destroyed since the Salle " + salleSetOrphanCheckSalle + " in its salleSet field has a non-nullable groupeId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Classe classeId = groupe.getClasseId();
//            if (classeId != null) {
//                classeId.getGroupeSet().remove(groupe);
//                classeId = em.merge(classeId);
//            }
//            Users userModified = groupe.getUserModified();
//            if (userModified != null) {
//                userModified.getGroupeSet().remove(groupe);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = groupe.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getGroupeSet().remove(groupe);
//                userCreated = em.merge(userCreated);
//            }
            remove(Groupe.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Groupe> findGroupeEntities() {
        return findGroupeEntities(true, -1, -1);
    }

    public List<Groupe> findGroupeEntities(int maxResults, int firstResult) {
        return findGroupeEntities(false, maxResults, firstResult);
    }

    private List<Groupe> findGroupeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Groupe.class));
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

    public Groupe findGroupe(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Groupe.class, id);
        } finally {
            em.close();
        }
    }

    public int getGroupeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Groupe> rt = cq.from(Groupe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
