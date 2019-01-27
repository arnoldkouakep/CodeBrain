/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.entitie.Etablissement;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Sections;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
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
public class EtablissementJpaController implements Serializable {

    public EtablissementJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Etablissement etablissement) throws PreexistingEntityException, Exception {
        if (etablissement.getSectionsCollection() == null) {
            etablissement.setSectionsCollection(new ArrayList<Sections>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userModified = etablissement.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                etablissement.setUserModified(userModified);
            }
            Users userCreated = etablissement.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                etablissement.setUserCreated(userCreated);
            }
            Collection<Sections> attachedSectionsCollection = new ArrayList<Sections>();
            for (Sections sectionsCollectionSectionsToAttach : etablissement.getSectionsCollection()) {
                sectionsCollectionSectionsToAttach = em.getReference(sectionsCollectionSectionsToAttach.getClass(), sectionsCollectionSectionsToAttach.getSectionsId());
                attachedSectionsCollection.add(sectionsCollectionSectionsToAttach);
            }
            etablissement.setSectionsCollection(attachedSectionsCollection);
            em.persist(etablissement);
            if (userModified != null) {
                userModified.getEtablissementCollection().add(etablissement);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getEtablissementCollection().add(etablissement);
                userCreated = em.merge(userCreated);
            }
            for (Sections sectionsCollectionSections : etablissement.getSectionsCollection()) {
                Etablissement oldEtablissementIdOfSectionsCollectionSections = sectionsCollectionSections.getEtablissementId();
                sectionsCollectionSections.setEtablissementId(etablissement);
                sectionsCollectionSections = em.merge(sectionsCollectionSections);
                if (oldEtablissementIdOfSectionsCollectionSections != null) {
                    oldEtablissementIdOfSectionsCollectionSections.getSectionsCollection().remove(sectionsCollectionSections);
                    oldEtablissementIdOfSectionsCollectionSections = em.merge(oldEtablissementIdOfSectionsCollectionSections);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEtablissement(etablissement.getEtablissementId()) != null) {
                throw new PreexistingEntityException("Etablissement " + etablissement + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Etablissement etablissement) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etablissement persistentEtablissement = em.find(Etablissement.class, etablissement.getEtablissementId());
            Users userModifiedOld = persistentEtablissement.getUserModified();
            Users userModifiedNew = etablissement.getUserModified();
            Users userCreatedOld = persistentEtablissement.getUserCreated();
            Users userCreatedNew = etablissement.getUserCreated();
            Collection<Sections> sectionsCollectionOld = persistentEtablissement.getSectionsCollection();
            Collection<Sections> sectionsCollectionNew = etablissement.getSectionsCollection();
            List<String> illegalOrphanMessages = null;
            for (Sections sectionsCollectionOldSections : sectionsCollectionOld) {
                if (!sectionsCollectionNew.contains(sectionsCollectionOldSections)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sections " + sectionsCollectionOldSections + " since its etablissementId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                etablissement.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                etablissement.setUserCreated(userCreatedNew);
            }
            Collection<Sections> attachedSectionsCollectionNew = new ArrayList<Sections>();
            for (Sections sectionsCollectionNewSectionsToAttach : sectionsCollectionNew) {
                sectionsCollectionNewSectionsToAttach = em.getReference(sectionsCollectionNewSectionsToAttach.getClass(), sectionsCollectionNewSectionsToAttach.getSectionsId());
                attachedSectionsCollectionNew.add(sectionsCollectionNewSectionsToAttach);
            }
            sectionsCollectionNew = attachedSectionsCollectionNew;
            etablissement.setSectionsCollection(sectionsCollectionNew);
            etablissement = em.merge(etablissement);
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getEtablissementCollection().remove(etablissement);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getEtablissementCollection().add(etablissement);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getEtablissementCollection().remove(etablissement);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getEtablissementCollection().add(etablissement);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Sections sectionsCollectionNewSections : sectionsCollectionNew) {
                if (!sectionsCollectionOld.contains(sectionsCollectionNewSections)) {
                    Etablissement oldEtablissementIdOfSectionsCollectionNewSections = sectionsCollectionNewSections.getEtablissementId();
                    sectionsCollectionNewSections.setEtablissementId(etablissement);
                    sectionsCollectionNewSections = em.merge(sectionsCollectionNewSections);
                    if (oldEtablissementIdOfSectionsCollectionNewSections != null && !oldEtablissementIdOfSectionsCollectionNewSections.equals(etablissement)) {
                        oldEtablissementIdOfSectionsCollectionNewSections.getSectionsCollection().remove(sectionsCollectionNewSections);
                        oldEtablissementIdOfSectionsCollectionNewSections = em.merge(oldEtablissementIdOfSectionsCollectionNewSections);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = etablissement.getEtablissementId();
                if (findEtablissement(id) == null) {
                    throw new NonexistentEntityException("The etablissement with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etablissement etablissement;
            try {
                etablissement = em.getReference(Etablissement.class, id);
                etablissement.getEtablissementId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etablissement with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sections> sectionsCollectionOrphanCheck = etablissement.getSectionsCollection();
            for (Sections sectionsCollectionOrphanCheckSections : sectionsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Etablissement (" + etablissement + ") cannot be destroyed since the Sections " + sectionsCollectionOrphanCheckSections + " in its sectionsCollection field has a non-nullable etablissementId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users userModified = etablissement.getUserModified();
            if (userModified != null) {
                userModified.getEtablissementCollection().remove(etablissement);
                userModified = em.merge(userModified);
            }
            Users userCreated = etablissement.getUserCreated();
            if (userCreated != null) {
                userCreated.getEtablissementCollection().remove(etablissement);
                userCreated = em.merge(userCreated);
            }
            em.remove(etablissement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Etablissement> findEtablissementEntities() {
        return findEtablissementEntities(true, -1, -1);
    }

    public List<Etablissement> findEtablissementEntities(int maxResults, int firstResult) {
        return findEtablissementEntities(false, maxResults, firstResult);
    }

    private List<Etablissement> findEtablissementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Etablissement.class));
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

    public Etablissement findEtablissement(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Etablissement.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtablissementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Etablissement> rt = cq.from(Etablissement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
