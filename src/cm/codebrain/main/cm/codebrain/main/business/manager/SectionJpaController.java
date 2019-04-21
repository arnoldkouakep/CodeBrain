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
import cm.codebrain.main.business.entitie.Etablissement;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Classe;
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
public class SectionJpaController extends CodeBrainEntityManager implements Serializable {

    public SectionJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Section section) throws PreexistingEntityException, Exception {
        if (section.getClasseSet() == null) {
            section.setClasseSet(new HashSet<Classe>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Etablissement etablissementId = section.getEtablissementId();
            if (etablissementId != null) {
                etablissementId = (Etablissement) refreshEntity(etablissementId.getClass(), etablissementId.getEtablissementId());
                section.setEtablissementId(etablissementId);
            }
            Users userModified = section.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                section.setUserModified(userModified);
            }
            Users userCreated = section.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                section.setUserCreated(userCreated);
            }
            Set<Classe> attachedClasseSet = new HashSet<Classe>();
            for (Classe classeSetClasseToAttach : section.getClasseSet()) {
                classeSetClasseToAttach = (Classe) refreshEntity(classeSetClasseToAttach.getClass(), classeSetClasseToAttach.getClasseId());
                attachedClasseSet.add(classeSetClasseToAttach);
            }
            section.setClasseSet(attachedClasseSet);
            persist(section);
//            if (etablissementId != null) {
//                etablissementId.getSectionSet().add(section);
//                etablissementId = em.merge(etablissementId);
//            }
//            if (userModified != null) {
//                userModified.getSectionSet().add(section);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getSectionSet().add(section);
//                userCreated = em.merge(userCreated);
//            }
//            for (Classe classeSetClasse : section.getClasseSet()) {
//                Section oldSectionIdOfClasseSetClasse = classeSetClasse.getSectionId();
//                classeSetClasse.setSectionId(section);
//                classeSetClasse = em.merge(classeSetClasse);
//                if (oldSectionIdOfClasseSetClasse != null) {
//                    oldSectionIdOfClasseSetClasse.getClasseSet().remove(classeSetClasse);
//                    oldSectionIdOfClasseSetClasse = em.merge(oldSectionIdOfClasseSetClasse);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSection(section.getSectionId()) != null) {
                throw new PreexistingEntityException("Section " + section + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Section section) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Section persistentSection = (Section) find(Section.class, section.getSectionId());
            Etablissement etablissementIdOld = persistentSection.getEtablissementId();
            Etablissement etablissementIdNew = section.getEtablissementId();
            Users userModifiedOld = persistentSection.getUserModified();
            Users userModifiedNew = section.getUserModified();
            Users userCreatedOld = persistentSection.getUserCreated();
            Users userCreatedNew = section.getUserCreated();
            Set<Classe> classeSetOld = persistentSection.getClasseSet();
            Set<Classe> classeSetNew = section.getClasseSet();
            List<String> illegalOrphanMessages = null;
//            for (Classe classeSetOldClasse : classeSetOld) {
//                if (!classeSetNew.contains(classeSetOldClasse)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Classe " + classeSetOldClasse + " since its sectionId field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (etablissementIdNew != null) {
                etablissementIdNew = (Etablissement) refreshEntity(etablissementIdNew.getClass(), etablissementIdNew.getEtablissementId());
                section.setEtablissementId(etablissementIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                section.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                section.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                section.setUserCreated(userCreatedOld);
            }
            Set<Classe> attachedClasseSetNew = new HashSet<Classe>();
            for (Classe classeSetNewClasseToAttach : classeSetNew) {
                classeSetNewClasseToAttach = (Classe) refreshEntity(classeSetNewClasseToAttach.getClass(), classeSetNewClasseToAttach.getClasseId());
                attachedClasseSetNew.add(classeSetNewClasseToAttach);
            }
            classeSetNew = attachedClasseSetNew;
            section.setClasseSet(classeSetNew);
            section = (Section) merge(section);
//            if (etablissementIdOld != null && !etablissementIdOld.equals(etablissementIdNew)) {
//                etablissementIdOld.getSectionSet().remove(section);
//                etablissementIdOld = em.merge(etablissementIdOld);
//            }
//            if (etablissementIdNew != null && !etablissementIdNew.equals(etablissementIdOld)) {
//                etablissementIdNew.getSectionSet().add(section);
//                etablissementIdNew = em.merge(etablissementIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getSectionSet().remove(section);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getSectionSet().add(section);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getSectionSet().remove(section);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getSectionSet().add(section);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Classe classeSetNewClasse : classeSetNew) {
//                if (!classeSetOld.contains(classeSetNewClasse)) {
//                    Section oldSectionIdOfClasseSetNewClasse = classeSetNewClasse.getSectionId();
//                    classeSetNewClasse.setSectionId(section);
//                    classeSetNewClasse = em.merge(classeSetNewClasse);
//                    if (oldSectionIdOfClasseSetNewClasse != null && !oldSectionIdOfClasseSetNewClasse.equals(section)) {
//                        oldSectionIdOfClasseSetNewClasse.getClasseSet().remove(classeSetNewClasse);
//                        oldSectionIdOfClasseSetNewClasse = em.merge(oldSectionIdOfClasseSetNewClasse);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = section.getSectionId();
                if (findSection(id) == null) {
                    throw new NonexistentEntityException("The section with id " + id + " no longer exists.");
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
//            Section section;
//            try {
//                section = () refreshEntity(Section.class, id);
//                section.getSectionId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The section with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Classe> classeSetOrphanCheck = section.getClasseSet();
//            for (Classe classeSetOrphanCheckClasse : classeSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Section (" + section + ") cannot be destroyed since the Classe " + classeSetOrphanCheckClasse + " in its classeSet field has a non-nullable sectionId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Etablissement etablissementId = section.getEtablissementId();
//            if (etablissementId != null) {
//                etablissementId.getSectionSet().remove(section);
//                etablissementId = em.merge(etablissementId);
//            }
//            Users userModified = section.getUserModified();
//            if (userModified != null) {
//                userModified.getSectionSet().remove(section);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = section.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getSectionSet().remove(section);
//                userCreated = em.merge(userCreated);
//            }
            remove(Section.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Section> findSectionEntities() {
        return findSectionEntities(true, -1, -1);
    }

    public List<Section> findSectionEntities(int maxResults, int firstResult) {
        return findSectionEntities(false, maxResults, firstResult);
    }

    private List<Section> findSectionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Section.class));
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

    public Section findSection(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Section.class, id);
        } finally {
            em.close();
        }
    }

    public int getSectionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Section> rt = cq.from(Section.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
