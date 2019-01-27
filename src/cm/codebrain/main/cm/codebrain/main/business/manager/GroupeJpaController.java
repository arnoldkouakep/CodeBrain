/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Salle;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Classe;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author KSA-INET
 */
public class GroupeJpaController implements Serializable {

    public GroupeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Groupe groupe) throws PreexistingEntityException, Exception {
        if (groupe.getClasseCollection() == null) {
            groupe.setClasseCollection(new ArrayList<Classe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salle salleId = groupe.getSalleId();
            if (salleId != null) {
                salleId = em.getReference(salleId.getClass(), salleId.getSalleId());
                groupe.setSalleId(salleId);
            }
            Users userModified = groupe.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                groupe.setUserModified(userModified);
            }
            Users userCreated = groupe.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                groupe.setUserCreated(userCreated);
            }
            Collection<Classe> attachedClasseCollection = new ArrayList<Classe>();
            for (Classe classeCollectionClasseToAttach : groupe.getClasseCollection()) {
                classeCollectionClasseToAttach = em.getReference(classeCollectionClasseToAttach.getClass(), classeCollectionClasseToAttach.getClasseId());
                attachedClasseCollection.add(classeCollectionClasseToAttach);
            }
            groupe.setClasseCollection(attachedClasseCollection);
            em.persist(groupe);
            if (salleId != null) {
                salleId.getGroupeCollection().add(groupe);
                salleId = em.merge(salleId);
            }
            if (userModified != null) {
                userModified.getGroupeCollection().add(groupe);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getGroupeCollection().add(groupe);
                userCreated = em.merge(userCreated);
            }
            for (Classe classeCollectionClasse : groupe.getClasseCollection()) {
                Groupe oldGroupeIdOfClasseCollectionClasse = classeCollectionClasse.getGroupeId();
                classeCollectionClasse.setGroupeId(groupe);
                classeCollectionClasse = em.merge(classeCollectionClasse);
                if (oldGroupeIdOfClasseCollectionClasse != null) {
                    oldGroupeIdOfClasseCollectionClasse.getClasseCollection().remove(classeCollectionClasse);
                    oldGroupeIdOfClasseCollectionClasse = em.merge(oldGroupeIdOfClasseCollectionClasse);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGroupe(groupe.getGroupeId()) != null) {
                throw new PreexistingEntityException("Groupe " + groupe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Groupe groupe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupe persistentGroupe = em.find(Groupe.class, groupe.getGroupeId());
            Salle salleIdOld = persistentGroupe.getSalleId();
            Salle salleIdNew = groupe.getSalleId();
            Users userModifiedOld = persistentGroupe.getUserModified();
            Users userModifiedNew = groupe.getUserModified();
            Users userCreatedOld = persistentGroupe.getUserCreated();
            Users userCreatedNew = groupe.getUserCreated();
            Collection<Classe> classeCollectionOld = persistentGroupe.getClasseCollection();
            Collection<Classe> classeCollectionNew = groupe.getClasseCollection();
            if (salleIdNew != null) {
                salleIdNew = em.getReference(salleIdNew.getClass(), salleIdNew.getSalleId());
                groupe.setSalleId(salleIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                groupe.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                groupe.setUserCreated(userCreatedNew);
            }
            Collection<Classe> attachedClasseCollectionNew = new ArrayList<Classe>();
            for (Classe classeCollectionNewClasseToAttach : classeCollectionNew) {
                classeCollectionNewClasseToAttach = em.getReference(classeCollectionNewClasseToAttach.getClass(), classeCollectionNewClasseToAttach.getClasseId());
                attachedClasseCollectionNew.add(classeCollectionNewClasseToAttach);
            }
            classeCollectionNew = attachedClasseCollectionNew;
            groupe.setClasseCollection(classeCollectionNew);
            groupe = em.merge(groupe);
            if (salleIdOld != null && !salleIdOld.equals(salleIdNew)) {
                salleIdOld.getGroupeCollection().remove(groupe);
                salleIdOld = em.merge(salleIdOld);
            }
            if (salleIdNew != null && !salleIdNew.equals(salleIdOld)) {
                salleIdNew.getGroupeCollection().add(groupe);
                salleIdNew = em.merge(salleIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getGroupeCollection().remove(groupe);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getGroupeCollection().add(groupe);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getGroupeCollection().remove(groupe);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getGroupeCollection().add(groupe);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Classe classeCollectionOldClasse : classeCollectionOld) {
                if (!classeCollectionNew.contains(classeCollectionOldClasse)) {
                    classeCollectionOldClasse.setGroupeId(null);
                    classeCollectionOldClasse = em.merge(classeCollectionOldClasse);
                }
            }
            for (Classe classeCollectionNewClasse : classeCollectionNew) {
                if (!classeCollectionOld.contains(classeCollectionNewClasse)) {
                    Groupe oldGroupeIdOfClasseCollectionNewClasse = classeCollectionNewClasse.getGroupeId();
                    classeCollectionNewClasse.setGroupeId(groupe);
                    classeCollectionNewClasse = em.merge(classeCollectionNewClasse);
                    if (oldGroupeIdOfClasseCollectionNewClasse != null && !oldGroupeIdOfClasseCollectionNewClasse.equals(groupe)) {
                        oldGroupeIdOfClasseCollectionNewClasse.getClasseCollection().remove(classeCollectionNewClasse);
                        oldGroupeIdOfClasseCollectionNewClasse = em.merge(oldGroupeIdOfClasseCollectionNewClasse);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = groupe.getGroupeId();
                if (findGroupe(id) == null) {
                    throw new NonexistentEntityException("The groupe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupe groupe;
            try {
                groupe = em.getReference(Groupe.class, id);
                groupe.getGroupeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The groupe with id " + id + " no longer exists.", enfe);
            }
            Salle salleId = groupe.getSalleId();
            if (salleId != null) {
                salleId.getGroupeCollection().remove(groupe);
                salleId = em.merge(salleId);
            }
            Users userModified = groupe.getUserModified();
            if (userModified != null) {
                userModified.getGroupeCollection().remove(groupe);
                userModified = em.merge(userModified);
            }
            Users userCreated = groupe.getUserCreated();
            if (userCreated != null) {
                userCreated.getGroupeCollection().remove(groupe);
                userCreated = em.merge(userCreated);
            }
            Collection<Classe> classeCollection = groupe.getClasseCollection();
            for (Classe classeCollectionClasse : classeCollection) {
                classeCollectionClasse.setGroupeId(null);
                classeCollectionClasse = em.merge(classeCollectionClasse);
            }
            em.remove(groupe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
