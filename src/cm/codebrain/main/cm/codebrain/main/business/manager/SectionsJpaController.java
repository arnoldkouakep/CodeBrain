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
import cm.codebrain.main.business.entitie.Etablissement;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Classe;
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
public class SectionsJpaController implements Serializable {

    public SectionsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sections sections) throws PreexistingEntityException, Exception {
        if (sections.getClasseCollection() == null) {
            sections.setClasseCollection(new ArrayList<Classe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Etablissement etablissementId = sections.getEtablissementId();
            if (etablissementId != null) {
                etablissementId = em.getReference(etablissementId.getClass(), etablissementId.getEtablissementId());
                sections.setEtablissementId(etablissementId);
            }
            Users userModified = sections.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                sections.setUserModified(userModified);
            }
            Users userCreated = sections.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                sections.setUserCreated(userCreated);
            }
            Collection<Classe> attachedClasseCollection = new ArrayList<Classe>();
            for (Classe classeCollectionClasseToAttach : sections.getClasseCollection()) {
                classeCollectionClasseToAttach = em.getReference(classeCollectionClasseToAttach.getClass(), classeCollectionClasseToAttach.getClasseId());
                attachedClasseCollection.add(classeCollectionClasseToAttach);
            }
            sections.setClasseCollection(attachedClasseCollection);
            em.persist(sections);
            if (etablissementId != null) {
                etablissementId.getSectionsCollection().add(sections);
                etablissementId = em.merge(etablissementId);
            }
            if (userModified != null) {
                userModified.getSectionsCollection().add(sections);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getSectionsCollection().add(sections);
                userCreated = em.merge(userCreated);
            }
            for (Classe classeCollectionClasse : sections.getClasseCollection()) {
                Sections oldSectionsIdOfClasseCollectionClasse = classeCollectionClasse.getSectionsId();
                classeCollectionClasse.setSectionsId(sections);
                classeCollectionClasse = em.merge(classeCollectionClasse);
                if (oldSectionsIdOfClasseCollectionClasse != null) {
                    oldSectionsIdOfClasseCollectionClasse.getClasseCollection().remove(classeCollectionClasse);
                    oldSectionsIdOfClasseCollectionClasse = em.merge(oldSectionsIdOfClasseCollectionClasse);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSections(sections.getSectionsId()) != null) {
                throw new PreexistingEntityException("Sections " + sections + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sections sections) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sections persistentSections = em.find(Sections.class, sections.getSectionsId());
            Etablissement etablissementIdOld = persistentSections.getEtablissementId();
            Etablissement etablissementIdNew = sections.getEtablissementId();
            Users userModifiedOld = persistentSections.getUserModified();
            Users userModifiedNew = sections.getUserModified();
            Users userCreatedOld = persistentSections.getUserCreated();
            Users userCreatedNew = sections.getUserCreated();
            Collection<Classe> classeCollectionOld = persistentSections.getClasseCollection();
            Collection<Classe> classeCollectionNew = sections.getClasseCollection();
            List<String> illegalOrphanMessages = null;
            for (Classe classeCollectionOldClasse : classeCollectionOld) {
                if (!classeCollectionNew.contains(classeCollectionOldClasse)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Classe " + classeCollectionOldClasse + " since its sectionsId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (etablissementIdNew != null) {
                etablissementIdNew = em.getReference(etablissementIdNew.getClass(), etablissementIdNew.getEtablissementId());
                sections.setEtablissementId(etablissementIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                sections.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                sections.setUserCreated(userCreatedNew);
            }
            Collection<Classe> attachedClasseCollectionNew = new ArrayList<Classe>();
            for (Classe classeCollectionNewClasseToAttach : classeCollectionNew) {
                classeCollectionNewClasseToAttach = em.getReference(classeCollectionNewClasseToAttach.getClass(), classeCollectionNewClasseToAttach.getClasseId());
                attachedClasseCollectionNew.add(classeCollectionNewClasseToAttach);
            }
            classeCollectionNew = attachedClasseCollectionNew;
            sections.setClasseCollection(classeCollectionNew);
            sections = em.merge(sections);
            if (etablissementIdOld != null && !etablissementIdOld.equals(etablissementIdNew)) {
                etablissementIdOld.getSectionsCollection().remove(sections);
                etablissementIdOld = em.merge(etablissementIdOld);
            }
            if (etablissementIdNew != null && !etablissementIdNew.equals(etablissementIdOld)) {
                etablissementIdNew.getSectionsCollection().add(sections);
                etablissementIdNew = em.merge(etablissementIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getSectionsCollection().remove(sections);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getSectionsCollection().add(sections);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getSectionsCollection().remove(sections);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getSectionsCollection().add(sections);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Classe classeCollectionNewClasse : classeCollectionNew) {
                if (!classeCollectionOld.contains(classeCollectionNewClasse)) {
                    Sections oldSectionsIdOfClasseCollectionNewClasse = classeCollectionNewClasse.getSectionsId();
                    classeCollectionNewClasse.setSectionsId(sections);
                    classeCollectionNewClasse = em.merge(classeCollectionNewClasse);
                    if (oldSectionsIdOfClasseCollectionNewClasse != null && !oldSectionsIdOfClasseCollectionNewClasse.equals(sections)) {
                        oldSectionsIdOfClasseCollectionNewClasse.getClasseCollection().remove(classeCollectionNewClasse);
                        oldSectionsIdOfClasseCollectionNewClasse = em.merge(oldSectionsIdOfClasseCollectionNewClasse);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sections.getSectionsId();
                if (findSections(id) == null) {
                    throw new NonexistentEntityException("The sections with id " + id + " no longer exists.");
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
            Sections sections;
            try {
                sections = em.getReference(Sections.class, id);
                sections.getSectionsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sections with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Classe> classeCollectionOrphanCheck = sections.getClasseCollection();
            for (Classe classeCollectionOrphanCheckClasse : classeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sections (" + sections + ") cannot be destroyed since the Classe " + classeCollectionOrphanCheckClasse + " in its classeCollection field has a non-nullable sectionsId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Etablissement etablissementId = sections.getEtablissementId();
            if (etablissementId != null) {
                etablissementId.getSectionsCollection().remove(sections);
                etablissementId = em.merge(etablissementId);
            }
            Users userModified = sections.getUserModified();
            if (userModified != null) {
                userModified.getSectionsCollection().remove(sections);
                userModified = em.merge(userModified);
            }
            Users userCreated = sections.getUserCreated();
            if (userCreated != null) {
                userCreated.getSectionsCollection().remove(sections);
                userCreated = em.merge(userCreated);
            }
            em.remove(sections);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sections> findSectionsEntities() {
        return findSectionsEntities(true, -1, -1);
    }

    public List<Sections> findSectionsEntities(int maxResults, int firstResult) {
        return findSectionsEntities(false, maxResults, firstResult);
    }

    private List<Sections> findSectionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sections.class));
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

    public Sections findSections(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sections.class, id);
        } finally {
            em.close();
        }
    }

    public int getSectionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sections> rt = cq.from(Sections.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
