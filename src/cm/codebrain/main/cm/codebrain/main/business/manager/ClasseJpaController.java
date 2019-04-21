/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import cm.codebrain.main.business.entitie.Classe;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Section;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Groupe;
import java.util.HashSet;
import java.util.Set;
import cm.codebrain.main.business.entitie.Salle;
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
public class ClasseJpaController extends CodeBrainEntityManager implements Serializable {

    public ClasseJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Classe classe) throws PreexistingEntityException, Exception {
        if (classe.getGroupeSet() == null) {
            classe.setGroupeSet(new HashSet<Groupe>());
        }
        if (classe.getSalleSet() == null) {
            classe.setSalleSet(new HashSet<Salle>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Section sectionId = classe.getSectionId();
            if (sectionId != null) {
                sectionId = (Section) refreshEntity(sectionId.getClass(), sectionId.getSectionId());
                classe.setSectionId(sectionId);
            }
            Users userModified = classe.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                classe.setUserModified(userModified);
            }
            Users userCreated = classe.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                classe.setUserCreated(userCreated);
            }
            Set<Groupe> attachedGroupeSet = new HashSet<Groupe>();
            for (Groupe groupeSetGroupeToAttach : classe.getGroupeSet()) {
                groupeSetGroupeToAttach = (Groupe) refreshEntity(groupeSetGroupeToAttach.getClass(), groupeSetGroupeToAttach.getGroupeId());
                attachedGroupeSet.add(groupeSetGroupeToAttach);
            }
            classe.setGroupeSet(attachedGroupeSet);
            Set<Salle> attachedSalleSet = new HashSet<Salle>();
            for (Salle salleSetSalleToAttach : classe.getSalleSet()) {
                salleSetSalleToAttach = (Salle) refreshEntity(salleSetSalleToAttach.getClass(), salleSetSalleToAttach.getSalleId());
                attachedSalleSet.add(salleSetSalleToAttach);
            }
            classe.setSalleSet(attachedSalleSet);
            persist(classe);
//            if (sectionId != null) {
//                sectionId.getClasseSet().add(classe);
//                sectionId = em.merge(sectionId);
//            }
//            if (userModified != null) {
//                userModified.getClasseSet().add(classe);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getClasseSet().add(classe);
//                userCreated = em.merge(userCreated);
//            }
//            for (Groupe groupeSetGroupe : classe.getGroupeSet()) {
//                Classe oldClasseIdOfGroupeSetGroupe = groupeSetGroupe.getClasseId();
//                groupeSetGroupe.setClasseId(classe);
//                groupeSetGroupe = em.merge(groupeSetGroupe);
//                if (oldClasseIdOfGroupeSetGroupe != null) {
//                    oldClasseIdOfGroupeSetGroupe.getGroupeSet().remove(groupeSetGroupe);
//                    oldClasseIdOfGroupeSetGroupe = em.merge(oldClasseIdOfGroupeSetGroupe);
//                }
//            }
//            for (Salle salleSetSalle : classe.getSalleSet()) {
//                Classe oldClasseIdOfSalleSetSalle = salleSetSalle.getClasseId();
//                salleSetSalle.setClasseId(classe);
//                salleSetSalle = em.merge(salleSetSalle);
//                if (oldClasseIdOfSalleSetSalle != null) {
//                    oldClasseIdOfSalleSetSalle.getSalleSet().remove(salleSetSalle);
//                    oldClasseIdOfSalleSetSalle = em.merge(oldClasseIdOfSalleSetSalle);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClasse(classe.getClasseId()) != null) {
                throw new PreexistingEntityException("Classe " + classe + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Classe classe) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Classe persistentClasse = (Classe) find(Classe.class, classe.getClasseId());
            Section sectionIdOld = persistentClasse.getSectionId();
            Section sectionIdNew = classe.getSectionId();
            Users userModifiedOld = persistentClasse.getUserModified();
            Users userModifiedNew = classe.getUserModified();
            Users userCreatedOld = persistentClasse.getUserCreated();
            Users userCreatedNew = classe.getUserCreated();
            Set<Groupe> groupeSetOld = persistentClasse.getGroupeSet();
            Set<Groupe> groupeSetNew = classe.getGroupeSet();
            Set<Salle> salleSetOld = persistentClasse.getSalleSet();
            Set<Salle> salleSetNew = classe.getSalleSet();
            List<String> illegalOrphanMessages = null;
            for (Groupe groupeSetOldGroupe : groupeSetOld) {
                if (!groupeSetNew.contains(groupeSetOldGroupe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Groupe " + groupeSetOldGroupe + " since its classeId field is not nullable.");
                }
            }
            for (Salle salleSetOldSalle : salleSetOld) {
                if (!salleSetNew.contains(salleSetOldSalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Salle " + salleSetOldSalle + " since its classeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sectionIdNew != null) {
                sectionIdNew = (Section) refreshEntity(sectionIdNew.getClass(), sectionIdNew.getSectionId());
                classe.setSectionId(sectionIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                classe.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                classe.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                classe.setUserCreated(userCreatedOld);
            }
            Set<Groupe> attachedGroupeSetNew = new HashSet<Groupe>();
            for (Groupe groupeSetNewGroupeToAttach : groupeSetNew) {
                groupeSetNewGroupeToAttach = (Groupe) refreshEntity(groupeSetNewGroupeToAttach.getClass(), groupeSetNewGroupeToAttach.getGroupeId());
                attachedGroupeSetNew.add(groupeSetNewGroupeToAttach);
            }
            groupeSetNew = attachedGroupeSetNew;
            classe.setGroupeSet(groupeSetNew);
            Set<Salle> attachedSalleSetNew = new HashSet<Salle>();
            for (Salle salleSetNewSalleToAttach : salleSetNew) {
                salleSetNewSalleToAttach = (Salle) refreshEntity(salleSetNewSalleToAttach.getClass(), salleSetNewSalleToAttach.getSalleId());
                attachedSalleSetNew.add(salleSetNewSalleToAttach);
            }
            salleSetNew = attachedSalleSetNew;
            classe.setSalleSet(salleSetNew);
            classe = (Classe) merge(classe);
//            if (sectionIdOld != null && !sectionIdOld.equals(sectionIdNew)) {
//                sectionIdOld.getClasseSet().remove(classe);
//                sectionIdOld = (Section) merge(sectionIdOld);
//            }
//            if (sectionIdNew != null && !sectionIdNew.equals(sectionIdOld)) {
//                sectionIdNew.getClasseSet().add(classe);
//                sectionIdNew = (Section) merge(sectionIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getClasseSet().remove(classe);
//                userModifiedOld = (Users) merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getClasseSet().add(classe);
//                userModifiedNew = (Users) merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getClasseSet().remove(classe);
//                userCreatedOld = (Users) merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getClasseSet().add(classe);
//                userCreatedNew = (Users) merge(userCreatedNew);
//            }
//            for (Groupe groupeSetNewGroupe : groupeSetNew) {
//                if (!groupeSetOld.contains(groupeSetNewGroupe)) {
//                    Classe oldClasseIdOfGroupeSetNewGroupe = groupeSetNewGroupe.getClasseId();
//                    groupeSetNewGroupe.setClasseId(classe);
//                    groupeSetNewGroupe = (Groupe) merge(groupeSetNewGroupe);
//                    if (oldClasseIdOfGroupeSetNewGroupe != null && !oldClasseIdOfGroupeSetNewGroupe.equals(classe)) {
//                        oldClasseIdOfGroupeSetNewGroupe.getGroupeSet().remove(groupeSetNewGroupe);
//                        oldClasseIdOfGroupeSetNewGroupe = (Classe) merge(oldClasseIdOfGroupeSetNewGroupe);
//                    }
//                }
//            }
//            for (Salle salleSetNewSalle : salleSetNew) {
//                if (!salleSetOld.contains(salleSetNewSalle)) {
//                    Classe oldClasseIdOfSalleSetNewSalle = salleSetNewSalle.getClasseId();
//                    salleSetNewSalle.setClasseId(classe);
//                    salleSetNewSalle = (Salle) merge(salleSetNewSalle);
//                    if (oldClasseIdOfSalleSetNewSalle != null && !oldClasseIdOfSalleSetNewSalle.equals(classe)) {
//                        oldClasseIdOfSalleSetNewSalle.getSalleSet().remove(salleSetNewSalle);
//                        oldClasseIdOfSalleSetNewSalle = (Classe) merge(oldClasseIdOfSalleSetNewSalle);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = classe.getClasseId();
                if (findClasse(id) == null) {
                    throw new NonexistentEntityException("The classe with id " + id + " no longer exists.");
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
//            Classe classe;
//            try {
//                classe = (Classe) refreshEntity(Classe.class, id);
//                classe.getClasseId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The classe with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Groupe> groupeSetOrphanCheck = classe.getGroupeSet();
//            for (Groupe groupeSetOrphanCheckGroupe : groupeSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Classe (" + classe + ") cannot be destroyed since the Groupe " + groupeSetOrphanCheckGroupe + " in its groupeSet field has a non-nullable classeId field.");
//            }
//            Set<Salle> salleSetOrphanCheck = classe.getSalleSet();
//            for (Salle salleSetOrphanCheckSalle : salleSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Classe (" + classe + ") cannot be destroyed since the Salle " + salleSetOrphanCheckSalle + " in its salleSet field has a non-nullable classeId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Section sectionId = classe.getSectionId();
//            if (sectionId != null) {
//                sectionId.getClasseSet().remove(classe);
//                sectionId = (Section) merge(sectionId);
//            }
//            Users userModified = classe.getUserModified();
//            if (userModified != null) {
//                userModified.getClasseSet().remove(classe);
//                userModified = (Users) merge(userModified);
//            }
//            Users userCreated = classe.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getClasseSet().remove(classe);
//                userCreated = (Users) merge(userCreated);
//            }
            remove(Classe.class, id);
//            getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Classe> findClasseEntities() {
        return findClasseEntities(true, -1, -1);
    }

    public List<Classe> findClasseEntities(int maxResults, int firstResult) {
        return findClasseEntities(false, maxResults, firstResult);
    }

    private List<Classe> findClasseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Classe.class));
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

    public Classe findClasse(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Classe.class, id);
        } finally {
            em.close();
        }
    }

    public int getClasseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Classe> rt = cq.from(Classe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
