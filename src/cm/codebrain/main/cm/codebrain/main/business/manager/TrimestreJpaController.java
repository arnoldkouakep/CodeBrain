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
import cm.codebrain.main.business.entitie.AnneeAcademic;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Sequence;
import java.util.HashSet;
import java.util.Set;
import cm.codebrain.main.business.entitie.ParametreAnneeAcademic;
import cm.codebrain.main.business.entitie.Trimestre;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class TrimestreJpaController extends CodeBrainEntityManager implements Serializable {

    public TrimestreJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Trimestre trimestre) throws PreexistingEntityException, Exception {
        if (trimestre.getSequenceSet() == null) {
            trimestre.setSequenceSet(new HashSet<Sequence>());
        }
        if (trimestre.getParametreAnneeAcademicSet() == null) {
            trimestre.setParametreAnneeAcademicSet(new HashSet<ParametreAnneeAcademic>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            AnneeAcademic anneeAcademicId = trimestre.getAnneeAcademicId();
            if (anneeAcademicId != null) {
                anneeAcademicId = (AnneeAcademic) refreshEntity(anneeAcademicId.getClass(), anneeAcademicId.getAnneeAcademicId());
                trimestre.setAnneeAcademicId(anneeAcademicId);
            }
            Users userModified = trimestre.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                trimestre.setUserModified(userModified);
            }
            Users userCreated = trimestre.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                trimestre.setUserCreated(userCreated);
            }
            Set<Sequence> attachedSequenceSet = new HashSet<Sequence>();
            for (Sequence sequenceSetSequenceToAttach : trimestre.getSequenceSet()) {
                sequenceSetSequenceToAttach = (Sequence) refreshEntity(sequenceSetSequenceToAttach.getClass(), sequenceSetSequenceToAttach.getSequenceId());
                attachedSequenceSet.add(sequenceSetSequenceToAttach);
            }
            trimestre.setSequenceSet(attachedSequenceSet);
            Set<ParametreAnneeAcademic> attachedParametreAnneeAcademicSet = new HashSet<ParametreAnneeAcademic>();
            for (ParametreAnneeAcademic parametreAnneeAcademicSetParametreAnneeAcademicToAttach : trimestre.getParametreAnneeAcademicSet()) {
                parametreAnneeAcademicSetParametreAnneeAcademicToAttach = (ParametreAnneeAcademic) refreshEntity(parametreAnneeAcademicSetParametreAnneeAcademicToAttach.getClass(), parametreAnneeAcademicSetParametreAnneeAcademicToAttach.getParametreAnneeAcademicId());
                attachedParametreAnneeAcademicSet.add(parametreAnneeAcademicSetParametreAnneeAcademicToAttach);
            }
            trimestre.setParametreAnneeAcademicSet(attachedParametreAnneeAcademicSet);
            persist(trimestre);
//            if (anneeAcademicId != null) {
//                anneeAcademicId.getTrimestreSet().add(trimestre);
//                anneeAcademicId = em.merge(anneeAcademicId);
//            }
//            if (userModified != null) {
//                userModified.getTrimestreSet().add(trimestre);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getTrimestreSet().add(trimestre);
//                userCreated = em.merge(userCreated);
//            }
//            for (Sequence sequenceSetSequence : trimestre.getSequenceSet()) {
//                Trimestre oldTrimestreIdOfSequenceSetSequence = sequenceSetSequence.getTrimestreId();
//                sequenceSetSequence.setTrimestreId(trimestre);
//                sequenceSetSequence = em.merge(sequenceSetSequence);
//                if (oldTrimestreIdOfSequenceSetSequence != null) {
//                    oldTrimestreIdOfSequenceSetSequence.getSequenceSet().remove(sequenceSetSequence);
//                    oldTrimestreIdOfSequenceSetSequence = em.merge(oldTrimestreIdOfSequenceSetSequence);
//                }
//            }
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetParametreAnneeAcademic : trimestre.getParametreAnneeAcademicSet()) {
//                Trimestre oldTrimestreIdOfParametreAnneeAcademicSetParametreAnneeAcademic = parametreAnneeAcademicSetParametreAnneeAcademic.getTrimestreId();
//                parametreAnneeAcademicSetParametreAnneeAcademic.setTrimestreId(trimestre);
//                parametreAnneeAcademicSetParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetParametreAnneeAcademic);
//                if (oldTrimestreIdOfParametreAnneeAcademicSetParametreAnneeAcademic != null) {
//                    oldTrimestreIdOfParametreAnneeAcademicSetParametreAnneeAcademic.getParametreAnneeAcademicSet().remove(parametreAnneeAcademicSetParametreAnneeAcademic);
//                    oldTrimestreIdOfParametreAnneeAcademicSetParametreAnneeAcademic = em.merge(oldTrimestreIdOfParametreAnneeAcademicSetParametreAnneeAcademic);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrimestre(trimestre.getTrimestreId()) != null) {
                throw new PreexistingEntityException("Trimestre " + trimestre + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Trimestre trimestre) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Trimestre persistentTrimestre = (Trimestre) find(Trimestre.class, trimestre.getTrimestreId());
            AnneeAcademic anneeAcademicIdOld = persistentTrimestre.getAnneeAcademicId();
            AnneeAcademic anneeAcademicIdNew = trimestre.getAnneeAcademicId();
            Users userModifiedOld = persistentTrimestre.getUserModified();
            Users userModifiedNew = trimestre.getUserModified();
            Users userCreatedOld = persistentTrimestre.getUserCreated();
            Users userCreatedNew = trimestre.getUserCreated();
            Set<Sequence> sequenceSetOld = persistentTrimestre.getSequenceSet();
            Set<Sequence> sequenceSetNew = trimestre.getSequenceSet();
            Set<ParametreAnneeAcademic> parametreAnneeAcademicSetOld = persistentTrimestre.getParametreAnneeAcademicSet();
            Set<ParametreAnneeAcademic> parametreAnneeAcademicSetNew = trimestre.getParametreAnneeAcademicSet();
            List<String> illegalOrphanMessages = null;
            for (Sequence sequenceSetOldSequence : sequenceSetOld) {
                if (!sequenceSetNew.contains(sequenceSetOldSequence)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sequence " + sequenceSetOldSequence + " since its trimestreId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (anneeAcademicIdNew != null) {
                anneeAcademicIdNew = (AnneeAcademic) refreshEntity(anneeAcademicIdNew.getClass(), anneeAcademicIdNew.getAnneeAcademicId());
                trimestre.setAnneeAcademicId(anneeAcademicIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                trimestre.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                trimestre.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                trimestre.setUserCreated(userCreatedOld);
            }
            Set<Sequence> attachedSequenceSetNew = new HashSet<Sequence>();
            for (Sequence sequenceSetNewSequenceToAttach : sequenceSetNew) {
                sequenceSetNewSequenceToAttach = (Sequence) refreshEntity(sequenceSetNewSequenceToAttach.getClass(), sequenceSetNewSequenceToAttach.getSequenceId());
                attachedSequenceSetNew.add(sequenceSetNewSequenceToAttach);
            }
            sequenceSetNew = attachedSequenceSetNew;
            trimestre.setSequenceSet(sequenceSetNew);
            Set<ParametreAnneeAcademic> attachedParametreAnneeAcademicSetNew = new HashSet<ParametreAnneeAcademic>();
            for (ParametreAnneeAcademic parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach : parametreAnneeAcademicSetNew) {
                parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach = (ParametreAnneeAcademic) refreshEntity(parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach.getClass(), parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach.getParametreAnneeAcademicId());
                attachedParametreAnneeAcademicSetNew.add(parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach);
            }
            parametreAnneeAcademicSetNew = attachedParametreAnneeAcademicSetNew;
            trimestre.setParametreAnneeAcademicSet(parametreAnneeAcademicSetNew);
            trimestre = (Trimestre) merge(trimestre);
//            if (anneeAcademicIdOld != null && !anneeAcademicIdOld.equals(anneeAcademicIdNew)) {
//                anneeAcademicIdOld.getTrimestreSet().remove(trimestre);
//                anneeAcademicIdOld = em.merge(anneeAcademicIdOld);
//            }
//            if (anneeAcademicIdNew != null && !anneeAcademicIdNew.equals(anneeAcademicIdOld)) {
//                anneeAcademicIdNew.getTrimestreSet().add(trimestre);
//                anneeAcademicIdNew = em.merge(anneeAcademicIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getTrimestreSet().remove(trimestre);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getTrimestreSet().add(trimestre);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getTrimestreSet().remove(trimestre);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getTrimestreSet().add(trimestre);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Sequence sequenceSetNewSequence : sequenceSetNew) {
//                if (!sequenceSetOld.contains(sequenceSetNewSequence)) {
//                    Trimestre oldTrimestreIdOfSequenceSetNewSequence = sequenceSetNewSequence.getTrimestreId();
//                    sequenceSetNewSequence.setTrimestreId(trimestre);
//                    sequenceSetNewSequence = em.merge(sequenceSetNewSequence);
//                    if (oldTrimestreIdOfSequenceSetNewSequence != null && !oldTrimestreIdOfSequenceSetNewSequence.equals(trimestre)) {
//                        oldTrimestreIdOfSequenceSetNewSequence.getSequenceSet().remove(sequenceSetNewSequence);
//                        oldTrimestreIdOfSequenceSetNewSequence = em.merge(oldTrimestreIdOfSequenceSetNewSequence);
//                    }
//                }
//            }
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetOldParametreAnneeAcademic : parametreAnneeAcademicSetOld) {
//                if (!parametreAnneeAcademicSetNew.contains(parametreAnneeAcademicSetOldParametreAnneeAcademic)) {
//                    parametreAnneeAcademicSetOldParametreAnneeAcademic.setTrimestreId(null);
//                    parametreAnneeAcademicSetOldParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetOldParametreAnneeAcademic);
//                }
//            }
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetNewParametreAnneeAcademic : parametreAnneeAcademicSetNew) {
//                if (!parametreAnneeAcademicSetOld.contains(parametreAnneeAcademicSetNewParametreAnneeAcademic)) {
//                    Trimestre oldTrimestreIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic = parametreAnneeAcademicSetNewParametreAnneeAcademic.getTrimestreId();
//                    parametreAnneeAcademicSetNewParametreAnneeAcademic.setTrimestreId(trimestre);
//                    parametreAnneeAcademicSetNewParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetNewParametreAnneeAcademic);
//                    if (oldTrimestreIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic != null && !oldTrimestreIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic.equals(trimestre)) {
//                        oldTrimestreIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic.getParametreAnneeAcademicSet().remove(parametreAnneeAcademicSetNewParametreAnneeAcademic);
//                        oldTrimestreIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic = em.merge(oldTrimestreIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = trimestre.getTrimestreId();
                if (findTrimestre(id) == null) {
                    throw new NonexistentEntityException("The trimestre with id " + id + " no longer exists.");
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
//            Trimestre trimestre;
//            try {
//                trimestre = em.getReference(Trimestre.class, id);
//                trimestre.getTrimestreId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The trimestre with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Sequence> sequenceSetOrphanCheck = trimestre.getSequenceSet();
//            for (Sequence sequenceSetOrphanCheckSequence : sequenceSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Trimestre (" + trimestre + ") cannot be destroyed since the Sequence " + sequenceSetOrphanCheckSequence + " in its sequenceSet field has a non-nullable trimestreId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            AnneeAcademic anneeAcademicId = trimestre.getAnneeAcademicId();
//            if (anneeAcademicId != null) {
//                anneeAcademicId.getTrimestreSet().remove(trimestre);
//                anneeAcademicId = em.merge(anneeAcademicId);
//            }
//            Users userModified = trimestre.getUserModified();
//            if (userModified != null) {
//                userModified.getTrimestreSet().remove(trimestre);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = trimestre.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getTrimestreSet().remove(trimestre);
//                userCreated = em.merge(userCreated);
//            }
//            Set<ParametreAnneeAcademic> parametreAnneeAcademicSet = trimestre.getParametreAnneeAcademicSet();
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetParametreAnneeAcademic : parametreAnneeAcademicSet) {
//                parametreAnneeAcademicSetParametreAnneeAcademic.setTrimestreId(null);
//                parametreAnneeAcademicSetParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetParametreAnneeAcademic);
//            }
            remove(Trimestre.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Trimestre> findTrimestreEntities() {
        return findTrimestreEntities(true, -1, -1);
    }

    public List<Trimestre> findTrimestreEntities(int maxResults, int firstResult) {
        return findTrimestreEntities(false, maxResults, firstResult);
    }

    private List<Trimestre> findTrimestreEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trimestre.class));
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

    public Trimestre findTrimestre(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trimestre.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrimestreCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trimestre> rt = cq.from(Trimestre.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
