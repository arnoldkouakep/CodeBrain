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
import cm.codebrain.main.business.entitie.Trimestre;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.ParametreAnneeAcademic;
import cm.codebrain.main.business.entitie.Sequence;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class SequenceJpaController extends CodeBrainEntityManager implements Serializable {

    public SequenceJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Sequence sequence) throws PreexistingEntityException, Exception {
        if (sequence.getParametreAnneeAcademicSet() == null) {
            sequence.setParametreAnneeAcademicSet(new HashSet<ParametreAnneeAcademic>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Trimestre trimestreId = sequence.getTrimestreId();
            if (trimestreId != null) {
                trimestreId = (Trimestre) refreshEntity(trimestreId.getClass(), trimestreId.getTrimestreId());
                sequence.setTrimestreId(trimestreId);
            }
            AnneeAcademic anneeAcademicId = sequence.getAnneeAcademicId();
            if (anneeAcademicId != null) {
                anneeAcademicId = (AnneeAcademic) refreshEntity(anneeAcademicId.getClass(), anneeAcademicId.getAnneeAcademicId());
                sequence.setAnneeAcademicId(anneeAcademicId);
            }else{
//                anneeAcademicId = (AnneeAcademic) refreshEntity(anneeAcademicId.getClass(), trimestreId.getAnneeAcademicId().getAnneeAcademicId());
                sequence.setAnneeAcademicId(trimestreId.getAnneeAcademicId());
            }
            Users userModified = sequence.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                sequence.setUserModified(userModified);
            }
            Users userCreated = sequence.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                sequence.setUserCreated(userCreated);
            }
            Set<ParametreAnneeAcademic> attachedParametreAnneeAcademicSet = new HashSet<ParametreAnneeAcademic>();
            for (ParametreAnneeAcademic parametreAnneeAcademicSetParametreAnneeAcademicToAttach : sequence.getParametreAnneeAcademicSet()) {
                parametreAnneeAcademicSetParametreAnneeAcademicToAttach = (ParametreAnneeAcademic) refreshEntity(parametreAnneeAcademicSetParametreAnneeAcademicToAttach.getClass(), parametreAnneeAcademicSetParametreAnneeAcademicToAttach.getParametreAnneeAcademicId());
                attachedParametreAnneeAcademicSet.add(parametreAnneeAcademicSetParametreAnneeAcademicToAttach);
            }
            sequence.setParametreAnneeAcademicSet(attachedParametreAnneeAcademicSet);
            persist(sequence);
//            if (anneeAcademicId != null) {
//                anneeAcademicId.getSequenceSet().add(sequence);
//                anneeAcademicId = em.merge(anneeAcademicId);
//            }
//            if (trimestreId != null) {
//                trimestreId.getSequenceSet().add(sequence);
//                trimestreId = em.merge(trimestreId);
//            }
//            if (userModified != null) {
//                userModified.getSequenceSet().add(sequence);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getSequenceSet().add(sequence);
//                userCreated = em.merge(userCreated);
//            }
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetParametreAnneeAcademic : sequence.getParametreAnneeAcademicSet()) {
//                Sequence oldSequenceIdOfParametreAnneeAcademicSetParametreAnneeAcademic = parametreAnneeAcademicSetParametreAnneeAcademic.getSequenceId();
//                parametreAnneeAcademicSetParametreAnneeAcademic.setSequenceId(sequence);
//                parametreAnneeAcademicSetParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetParametreAnneeAcademic);
//                if (oldSequenceIdOfParametreAnneeAcademicSetParametreAnneeAcademic != null) {
//                    oldSequenceIdOfParametreAnneeAcademicSetParametreAnneeAcademic.getParametreAnneeAcademicSet().remove(parametreAnneeAcademicSetParametreAnneeAcademic);
//                    oldSequenceIdOfParametreAnneeAcademicSetParametreAnneeAcademic = em.merge(oldSequenceIdOfParametreAnneeAcademicSetParametreAnneeAcademic);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSequence(sequence.getSequenceId()) != null) {
                throw new PreexistingEntityException("Sequence " + sequence + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Sequence sequence) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Sequence persistentSequence = (Sequence) find(Sequence.class, sequence.getSequenceId());
//            AnneeAcademic anneeAcademicIdOld = persistentSequence.getAnneeAcademicId();
            AnneeAcademic anneeAcademicIdNew = sequence.getAnneeAcademicId();
//            Trimestre trimestreIdOld = persistentSequence.getTrimestreId();
            Trimestre trimestreIdNew = sequence.getTrimestreId();
//            Users userModifiedOld = persistentSequence.getUserModified();
            Users userModifiedNew = sequence.getUserModified();
            Users userCreatedOld = persistentSequence.getUserCreated();
            Users userCreatedNew = sequence.getUserCreated();
//            Set<ParametreAnneeAcademic> parametreAnneeAcademicSetOld = persistentSequence.getParametreAnneeAcademicSet();
            Set<ParametreAnneeAcademic> parametreAnneeAcademicSetNew = sequence.getParametreAnneeAcademicSet();
            if (anneeAcademicIdNew != null) {
                anneeAcademicIdNew = (AnneeAcademic) refreshEntity(anneeAcademicIdNew.getClass(), anneeAcademicIdNew.getAnneeAcademicId());
                sequence.setAnneeAcademicId(anneeAcademicIdNew);
            }
            if (trimestreIdNew != null) {
                trimestreIdNew = (Trimestre) refreshEntity(trimestreIdNew.getClass(), trimestreIdNew.getTrimestreId());
                sequence.setTrimestreId(trimestreIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                sequence.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                sequence.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                sequence.setUserCreated(userCreatedOld);
            }
            Set<ParametreAnneeAcademic> attachedParametreAnneeAcademicSetNew = new HashSet<ParametreAnneeAcademic>();
            for (ParametreAnneeAcademic parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach : parametreAnneeAcademicSetNew) {
                parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach = (ParametreAnneeAcademic) refreshEntity(parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach.getClass(), parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach.getParametreAnneeAcademicId());
                attachedParametreAnneeAcademicSetNew.add(parametreAnneeAcademicSetNewParametreAnneeAcademicToAttach);
            }
            parametreAnneeAcademicSetNew = attachedParametreAnneeAcademicSetNew;
            sequence.setParametreAnneeAcademicSet(parametreAnneeAcademicSetNew);
            sequence = (Sequence) merge(sequence);
//            if (anneeAcademicIdOld != null && !anneeAcademicIdOld.equals(anneeAcademicIdNew)) {
//                anneeAcademicIdOld.getSequenceSet().remove(sequence);
//                anneeAcademicIdOld = em.merge(anneeAcademicIdOld);
//            }
//            if (anneeAcademicIdNew != null && !anneeAcademicIdNew.equals(anneeAcademicIdOld)) {
//                anneeAcademicIdNew.getSequenceSet().add(sequence);
//                anneeAcademicIdNew = em.merge(anneeAcademicIdNew);
//            }
//            if (trimestreIdOld != null && !trimestreIdOld.equals(trimestreIdNew)) {
//                trimestreIdOld.getSequenceSet().remove(sequence);
//                trimestreIdOld = em.merge(trimestreIdOld);
//            }
//            if (trimestreIdNew != null && !trimestreIdNew.equals(trimestreIdOld)) {
//                trimestreIdNew.getSequenceSet().add(sequence);
//                trimestreIdNew = em.merge(trimestreIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getSequenceSet().remove(sequence);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getSequenceSet().add(sequence);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getSequenceSet().remove(sequence);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getSequenceSet().add(sequence);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetOldParametreAnneeAcademic : parametreAnneeAcademicSetOld) {
//                if (!parametreAnneeAcademicSetNew.contains(parametreAnneeAcademicSetOldParametreAnneeAcademic)) {
//                    parametreAnneeAcademicSetOldParametreAnneeAcademic.setSequenceId(null);
//                    parametreAnneeAcademicSetOldParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetOldParametreAnneeAcademic);
//                }
//            }
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetNewParametreAnneeAcademic : parametreAnneeAcademicSetNew) {
//                if (!parametreAnneeAcademicSetOld.contains(parametreAnneeAcademicSetNewParametreAnneeAcademic)) {
//                    Sequence oldSequenceIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic = parametreAnneeAcademicSetNewParametreAnneeAcademic.getSequenceId();
//                    parametreAnneeAcademicSetNewParametreAnneeAcademic.setSequenceId(sequence);
//                    parametreAnneeAcademicSetNewParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetNewParametreAnneeAcademic);
//                    if (oldSequenceIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic != null && !oldSequenceIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic.equals(sequence)) {
//                        oldSequenceIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic.getParametreAnneeAcademicSet().remove(parametreAnneeAcademicSetNewParametreAnneeAcademic);
//                        oldSequenceIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic = em.merge(oldSequenceIdOfParametreAnneeAcademicSetNewParametreAnneeAcademic);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sequence.getSequenceId();
                if (findSequence(id) == null) {
                    throw new NonexistentEntityException("The sequence with id " + id + " no longer exists.");
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
//            Sequence sequence;
//            try {
//                sequence = em.getReference(Sequence.class, id);
//                sequence.getSequenceId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The sequence with id " + id + " no longer exists.", enfe);
//            }
//            AnneeAcademic anneeAcademicId = sequence.getAnneeAcademicId();
//            if (anneeAcademicId != null) {
//                anneeAcademicId.getSequenceSet().remove(sequence);
//                anneeAcademicId = em.merge(anneeAcademicId);
//            }
//            Trimestre trimestreId = sequence.getTrimestreId();
//            if (trimestreId != null) {
//                trimestreId.getSequenceSet().remove(sequence);
//                trimestreId = em.merge(trimestreId);
//            }
//            Users userModified = sequence.getUserModified();
//            if (userModified != null) {
//                userModified.getSequenceSet().remove(sequence);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = sequence.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getSequenceSet().remove(sequence);
//                userCreated = em.merge(userCreated);
//            }
//            Set<ParametreAnneeAcademic> parametreAnneeAcademicSet = sequence.getParametreAnneeAcademicSet();
//            for (ParametreAnneeAcademic parametreAnneeAcademicSetParametreAnneeAcademic : parametreAnneeAcademicSet) {
//                parametreAnneeAcademicSetParametreAnneeAcademic.setSequenceId(null);
//                parametreAnneeAcademicSetParametreAnneeAcademic = em.merge(parametreAnneeAcademicSetParametreAnneeAcademic);
//            }
            remove(Sequence.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Sequence> findSequenceEntities() {
        return findSequenceEntities(true, -1, -1);
    }

    public List<Sequence> findSequenceEntities(int maxResults, int firstResult) {
        return findSequenceEntities(false, maxResults, firstResult);
    }

    private List<Sequence> findSequenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sequence.class));
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

    public Sequence findSequence(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sequence.class, id);
        } finally {
            em.close();
        }
    }

    public int getSequenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sequence> rt = cq.from(Sequence.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
