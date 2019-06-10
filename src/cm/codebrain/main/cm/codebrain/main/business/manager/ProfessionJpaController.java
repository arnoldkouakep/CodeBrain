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
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Enseignant;
import cm.codebrain.main.business.entitie.Profession;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class ProfessionJpaController  extends CodeBrainEntityManager implements Serializable {

    public ProfessionJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Profession profession) throws PreexistingEntityException, Exception {
        if (profession.getEnseignantSet() == null) {
            profession.setEnseignantSet(new HashSet<Enseignant>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Users userModified = profession.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                profession.setUserModified(userModified);
            }
            Users userCreated = profession.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                profession.setUserCreated(userCreated);
            }
            Set<Enseignant> attachedEnseignantSet = new HashSet<>();
            for (Enseignant enseignantSetEnseignantToAttach : profession.getEnseignantSet()) {
                enseignantSetEnseignantToAttach = (Enseignant) refreshEntity(enseignantSetEnseignantToAttach.getClass(), enseignantSetEnseignantToAttach.getEnseignantId());
                attachedEnseignantSet.add(enseignantSetEnseignantToAttach);
            }
            profession.setEnseignantSet(attachedEnseignantSet);
            persist(profession);
//            if (userModified != null) {
//                userModified.getProfessionSet().add(profession);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getProfessionSet().add(profession);
//                userCreated = em.merge(userCreated);
//            }
//            for (Enseignant enseignantSetEnseignant : profession.getEnseignantSet()) {
//                Profession oldProfessionIdOfEnseignantSetEnseignant = enseignantSetEnseignant.getProfessionId();
//                enseignantSetEnseignant.setProfessionId(profession);
//                enseignantSetEnseignant = em.merge(enseignantSetEnseignant);
//                if (oldProfessionIdOfEnseignantSetEnseignant != null) {
//                    oldProfessionIdOfEnseignantSetEnseignant.getEnseignantSet().remove(enseignantSetEnseignant);
//                    oldProfessionIdOfEnseignantSetEnseignant = em.merge(oldProfessionIdOfEnseignantSetEnseignant);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProfession(profession.getProfessionId()) != null) {
                throw new PreexistingEntityException("Profession " + profession + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Profession profession) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Profession persistentProfession = (Profession) find(Profession.class, profession.getProfessionId());
//            Users userModifiedOld = persistentProfession.getUserModified();
            Users userModifiedNew = profession.getUserModified();
            Users userCreatedOld = persistentProfession.getUserCreated();
            Users userCreatedNew = profession.getUserCreated();
//            Set<Enseignant> enseignantSetOld = persistentProfession.getEnseignantSet();
            Set<Enseignant> enseignantSetNew = profession.getEnseignantSet();
//            List<String> illegalOrphanMessages = null;
//            for (Enseignant enseignantSetOldEnseignant : enseignantSetOld) {
//                if (!enseignantSetNew.contains(enseignantSetOldEnseignant)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Enseignant " + enseignantSetOldEnseignant + " since its professionId field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                profession.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                profession.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                profession.setUserCreated(userCreatedOld);
            }
            Set<Enseignant> attachedEnseignantSetNew = new HashSet<Enseignant>();
            for (Enseignant enseignantSetNewEnseignantToAttach : enseignantSetNew) {
                enseignantSetNewEnseignantToAttach = (Enseignant) refreshEntity(enseignantSetNewEnseignantToAttach.getClass(), enseignantSetNewEnseignantToAttach.getEnseignantId());
                attachedEnseignantSetNew.add(enseignantSetNewEnseignantToAttach);
            }
            enseignantSetNew = attachedEnseignantSetNew;
            profession.setEnseignantSet(enseignantSetNew);
            profession = (Profession) merge(profession);
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getProfessionSet().remove(profession);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getProfessionSet().add(profession);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getProfessionSet().remove(profession);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getProfessionSet().add(profession);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Enseignant enseignantSetNewEnseignant : enseignantSetNew) {
//                if (!enseignantSetOld.contains(enseignantSetNewEnseignant)) {
//                    Profession oldProfessionIdOfEnseignantSetNewEnseignant = enseignantSetNewEnseignant.getProfessionId();
//                    enseignantSetNewEnseignant.setProfessionId(profession);
//                    enseignantSetNewEnseignant = em.merge(enseignantSetNewEnseignant);
//                    if (oldProfessionIdOfEnseignantSetNewEnseignant != null && !oldProfessionIdOfEnseignantSetNewEnseignant.equals(profession)) {
//                        oldProfessionIdOfEnseignantSetNewEnseignant.getEnseignantSet().remove(enseignantSetNewEnseignant);
//                        oldProfessionIdOfEnseignantSetNewEnseignant = em.merge(oldProfessionIdOfEnseignantSetNewEnseignant);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = profession.getProfessionId();
                if (findProfession(id) == null) {
                    throw new NonexistentEntityException("The profession with id " + id + " no longer exists.");
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
//            Profession profession;
//            try {
//                profession = em.getReference(Profession.class, id);
//                profession.getProfessionId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The profession with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Enseignant> enseignantSetOrphanCheck = profession.getEnseignantSet();
//            for (Enseignant enseignantSetOrphanCheckEnseignant : enseignantSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Profession (" + profession + ") cannot be destroyed since the Enseignant " + enseignantSetOrphanCheckEnseignant + " in its enseignantSet field has a non-nullable professionId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Users userModified = profession.getUserModified();
//            if (userModified != null) {
//                userModified.getProfessionSet().remove(profession);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = profession.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getProfessionSet().remove(profession);
//                userCreated = em.merge(userCreated);
//            }
            remove(Profession.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Profession> findProfessionEntities() {
        return findProfessionEntities(true, -1, -1);
    }

    public List<Profession> findProfessionEntities(int maxResults, int firstResult) {
        return findProfessionEntities(false, maxResults, firstResult);
    }

    private List<Profession> findProfessionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profession.class));
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

    public Profession findProfession(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profession.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfessionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profession> rt = cq.from(Profession.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
