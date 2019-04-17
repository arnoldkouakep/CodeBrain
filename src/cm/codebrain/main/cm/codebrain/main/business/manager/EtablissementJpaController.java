/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import cm.codebrain.main.business.entitie.Etablissement;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Section;
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
public class EtablissementJpaController extends CodeBrainEntityManager implements Serializable {

    public EtablissementJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Etablissement etablissement) throws PreexistingEntityException, Exception {
        if (etablissement.getSectionSet() == null) {
            etablissement.setSectionSet(new HashSet<Section>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Users userModified = etablissement.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                etablissement.setUserModified(userModified);
            }
            Users userCreated = etablissement.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                etablissement.setUserCreated(userCreated);
            }
            Set<Section> attachedSectionSet = new HashSet<Section>();
            for (Section sectionSetSectionToAttach : etablissement.getSectionSet()) {
                sectionSetSectionToAttach = (Section) refreshEntity(sectionSetSectionToAttach.getClass(), sectionSetSectionToAttach.getSectionId());
                attachedSectionSet.add(sectionSetSectionToAttach);
            }
            etablissement.setSectionSet(attachedSectionSet);
            persist(etablissement);
//            if (userModified != null) {
//                userModified.getEtablissementSet().add(etablissement);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getEtablissementSet().add(etablissement);
//                userCreated = em.merge(userCreated);
//            }
//            for (Section sectionSetSection : etablissement.getSectionSet()) {
//                Etablissement oldEtablissementIdOfSectionSetSection = sectionSetSection.getEtablissementId();
//                sectionSetSection.setEtablissementId(etablissement);
//                sectionSetSection = em.merge(sectionSetSection);
//                if (oldEtablissementIdOfSectionSetSection != null) {
//                    oldEtablissementIdOfSectionSetSection.getSectionSet().remove(sectionSetSection);
//                    oldEtablissementIdOfSectionSetSection = em.merge(oldEtablissementIdOfSectionSetSection);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEtablissement(etablissement.getEtablissementId()) != null) {
                throw new PreexistingEntityException("Etablissement " + etablissement + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Etablissement etablissement) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Etablissement persistentEtablissement = (Etablissement) find(Etablissement.class, etablissement.getEtablissementId());
//            Users userModifiedOld = persistentEtablissement.getUserModified();
            Users userModifiedNew = etablissement.getUserModified();
//            Users userCreatedOld = persistentEtablissement.getUserCreated();
            Users userCreatedNew = etablissement.getUserCreated();
//            Set<Section> sectionSetOld = persistentEtablissement.getSectionSet();
            Set<Section> sectionSetNew = etablissement.getSectionSet();
//            List<String> illegalOrphanMessages = null;
//            for (Section sectionSetOldSection : sectionSetOld) {
//                if (!sectionSetNew.contains(sectionSetOldSection)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Section " + sectionSetOldSection + " since its etablissementId field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                etablissement.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                etablissement.setUserCreated(userCreatedNew);
            }
            Set<Section> attachedSectionSetNew = new HashSet<Section>();
            for (Section sectionSetNewSectionToAttach : sectionSetNew) {
                sectionSetNewSectionToAttach = (Section) refreshEntity(sectionSetNewSectionToAttach.getClass(), sectionSetNewSectionToAttach.getSectionId());
                attachedSectionSetNew.add(sectionSetNewSectionToAttach);
            }
            sectionSetNew = attachedSectionSetNew;
            etablissement.setSectionSet(sectionSetNew);
            etablissement = (Etablissement) merge(etablissement);
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getEtablissementSet().remove(etablissement);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getEtablissementSet().add(etablissement);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getEtablissementSet().remove(etablissement);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getEtablissementSet().add(etablissement);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Section sectionSetNewSection : sectionSetNew) {
//                if (!sectionSetOld.contains(sectionSetNewSection)) {
//                    Etablissement oldEtablissementIdOfSectionSetNewSection = sectionSetNewSection.getEtablissementId();
//                    sectionSetNewSection.setEtablissementId(etablissement);
//                    sectionSetNewSection = em.merge(sectionSetNewSection);
//                    if (oldEtablissementIdOfSectionSetNewSection != null && !oldEtablissementIdOfSectionSetNewSection.equals(etablissement)) {
//                        oldEtablissementIdOfSectionSetNewSection.getSectionSet().remove(sectionSetNewSection);
//                        oldEtablissementIdOfSectionSetNewSection = em.merge(oldEtablissementIdOfSectionSetNewSection);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = etablissement.getEtablissementId();
                if (findEtablissement(id) == null) {
                    throw new NonexistentEntityException("The etablissement with id " + id + " no longer exists.");
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
//            Etablissement etablissement;
//            try {
//                etablissement = (Etablissement) refreshEntity(Etablissement.class, id);
//                etablissement.getEtablissementId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The etablissement with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Section> sectionSetOrphanCheck = etablissement.getSectionSet();
//            for (Section sectionSetOrphanCheckSection : sectionSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Etablissement (" + etablissement + ") cannot be destroyed since the Section " + sectionSetOrphanCheckSection + " in its sectionSet field has a non-nullable etablissementId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Users userModified = etablissement.getUserModified();
//            if (userModified != null) {
//                userModified.getEtablissementSet().remove(etablissement);
//                userModified = (Users) merge(userModified);
//            }
//            Users userCreated = etablissement.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getEtablissementSet().remove(etablissement);
//                userCreated = em.merge(userCreated);
//            }
            remove(Etablissement.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
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
