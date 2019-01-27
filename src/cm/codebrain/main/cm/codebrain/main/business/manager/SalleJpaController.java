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
import cm.codebrain.main.business.entitie.Classe;
import cm.codebrain.main.business.entitie.Sections;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Student;
import java.util.ArrayList;
import java.util.Collection;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.entitie.Salle;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author KSA-INET
 */
public class SalleJpaController implements Serializable {

    public SalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salle salle) throws PreexistingEntityException, Exception {
        if (salle.getStudentCollection() == null) {
            salle.setStudentCollection(new ArrayList<Student>());
        }
        if (salle.getGroupeCollection() == null) {
            salle.setGroupeCollection(new ArrayList<Groupe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Classe classeId = salle.getClasseId();
            if (classeId != null) {
                classeId = em.getReference(classeId.getClass(), classeId.getClasseId());
                salle.setClasseId(classeId);
            }
            Sections sectionsId = salle.getSectionsId();
            if (sectionsId != null) {
                sectionsId = em.getReference(sectionsId.getClass(), sectionsId.getSectionsId());
                salle.setSectionsId(sectionsId);
            }
            Users userModified = salle.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                salle.setUserModified(userModified);
            }
            Users userCreated = salle.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                salle.setUserCreated(userCreated);
            }
            Collection<Student> attachedStudentCollection = new ArrayList<Student>();
            for (Student studentCollectionStudentToAttach : salle.getStudentCollection()) {
                studentCollectionStudentToAttach = em.getReference(studentCollectionStudentToAttach.getClass(), studentCollectionStudentToAttach.getStudentId());
                attachedStudentCollection.add(studentCollectionStudentToAttach);
            }
            salle.setStudentCollection(attachedStudentCollection);
            Collection<Groupe> attachedGroupeCollection = new ArrayList<Groupe>();
            for (Groupe groupeCollectionGroupeToAttach : salle.getGroupeCollection()) {
                groupeCollectionGroupeToAttach = em.getReference(groupeCollectionGroupeToAttach.getClass(), groupeCollectionGroupeToAttach.getGroupeId());
                attachedGroupeCollection.add(groupeCollectionGroupeToAttach);
            }
            salle.setGroupeCollection(attachedGroupeCollection);
            em.persist(salle);
            if (classeId != null) {
                classeId.getSalleCollection().add(salle);
                classeId = em.merge(classeId);
            }
            if (sectionsId != null) {
                sectionsId.getSalleCollection().add(salle);
                sectionsId = em.merge(sectionsId);
            }
            if (userModified != null) {
                userModified.getSalleCollection().add(salle);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getSalleCollection().add(salle);
                userCreated = em.merge(userCreated);
            }
            for (Student studentCollectionStudent : salle.getStudentCollection()) {
                Salle oldSalleIdOfStudentCollectionStudent = studentCollectionStudent.getSalleId();
                studentCollectionStudent.setSalleId(salle);
                studentCollectionStudent = em.merge(studentCollectionStudent);
                if (oldSalleIdOfStudentCollectionStudent != null) {
                    oldSalleIdOfStudentCollectionStudent.getStudentCollection().remove(studentCollectionStudent);
                    oldSalleIdOfStudentCollectionStudent = em.merge(oldSalleIdOfStudentCollectionStudent);
                }
            }
            for (Groupe groupeCollectionGroupe : salle.getGroupeCollection()) {
                Salle oldSalleIdOfGroupeCollectionGroupe = groupeCollectionGroupe.getSalleId();
                groupeCollectionGroupe.setSalleId(salle);
                groupeCollectionGroupe = em.merge(groupeCollectionGroupe);
                if (oldSalleIdOfGroupeCollectionGroupe != null) {
                    oldSalleIdOfGroupeCollectionGroupe.getGroupeCollection().remove(groupeCollectionGroupe);
                    oldSalleIdOfGroupeCollectionGroupe = em.merge(oldSalleIdOfGroupeCollectionGroupe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSalle(salle.getSalleId()) != null) {
                throw new PreexistingEntityException("Salle " + salle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salle salle) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salle persistentSalle = em.find(Salle.class, salle.getSalleId());
            Classe classeIdOld = persistentSalle.getClasseId();
            Classe classeIdNew = salle.getClasseId();
            Sections sectionsIdOld = persistentSalle.getSectionsId();
            Sections sectionsIdNew = salle.getSectionsId();
            Users userModifiedOld = persistentSalle.getUserModified();
            Users userModifiedNew = salle.getUserModified();
            Users userCreatedOld = persistentSalle.getUserCreated();
            Users userCreatedNew = salle.getUserCreated();
            Collection<Student> studentCollectionOld = persistentSalle.getStudentCollection();
            Collection<Student> studentCollectionNew = salle.getStudentCollection();
            Collection<Groupe> groupeCollectionOld = persistentSalle.getGroupeCollection();
            Collection<Groupe> groupeCollectionNew = salle.getGroupeCollection();
            List<String> illegalOrphanMessages = null;
            for (Student studentCollectionOldStudent : studentCollectionOld) {
                if (!studentCollectionNew.contains(studentCollectionOldStudent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Student " + studentCollectionOldStudent + " since its salleId field is not nullable.");
                }
            }
            for (Groupe groupeCollectionOldGroupe : groupeCollectionOld) {
                if (!groupeCollectionNew.contains(groupeCollectionOldGroupe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Groupe " + groupeCollectionOldGroupe + " since its salleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (classeIdNew != null) {
                classeIdNew = em.getReference(classeIdNew.getClass(), classeIdNew.getClasseId());
                salle.setClasseId(classeIdNew);
            }
            if (sectionsIdNew != null) {
                sectionsIdNew = em.getReference(sectionsIdNew.getClass(), sectionsIdNew.getSectionsId());
                salle.setSectionsId(sectionsIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                salle.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                salle.setUserCreated(userCreatedNew);
            }
            Collection<Student> attachedStudentCollectionNew = new ArrayList<Student>();
            for (Student studentCollectionNewStudentToAttach : studentCollectionNew) {
                studentCollectionNewStudentToAttach = em.getReference(studentCollectionNewStudentToAttach.getClass(), studentCollectionNewStudentToAttach.getStudentId());
                attachedStudentCollectionNew.add(studentCollectionNewStudentToAttach);
            }
            studentCollectionNew = attachedStudentCollectionNew;
            salle.setStudentCollection(studentCollectionNew);
            Collection<Groupe> attachedGroupeCollectionNew = new ArrayList<Groupe>();
            for (Groupe groupeCollectionNewGroupeToAttach : groupeCollectionNew) {
                groupeCollectionNewGroupeToAttach = em.getReference(groupeCollectionNewGroupeToAttach.getClass(), groupeCollectionNewGroupeToAttach.getGroupeId());
                attachedGroupeCollectionNew.add(groupeCollectionNewGroupeToAttach);
            }
            groupeCollectionNew = attachedGroupeCollectionNew;
            salle.setGroupeCollection(groupeCollectionNew);
            salle = em.merge(salle);
            if (classeIdOld != null && !classeIdOld.equals(classeIdNew)) {
                classeIdOld.getSalleCollection().remove(salle);
                classeIdOld = em.merge(classeIdOld);
            }
            if (classeIdNew != null && !classeIdNew.equals(classeIdOld)) {
                classeIdNew.getSalleCollection().add(salle);
                classeIdNew = em.merge(classeIdNew);
            }
            if (sectionsIdOld != null && !sectionsIdOld.equals(sectionsIdNew)) {
                sectionsIdOld.getSalleCollection().remove(salle);
                sectionsIdOld = em.merge(sectionsIdOld);
            }
            if (sectionsIdNew != null && !sectionsIdNew.equals(sectionsIdOld)) {
                sectionsIdNew.getSalleCollection().add(salle);
                sectionsIdNew = em.merge(sectionsIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getSalleCollection().remove(salle);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getSalleCollection().add(salle);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getSalleCollection().remove(salle);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getSalleCollection().add(salle);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Student studentCollectionNewStudent : studentCollectionNew) {
                if (!studentCollectionOld.contains(studentCollectionNewStudent)) {
                    Salle oldSalleIdOfStudentCollectionNewStudent = studentCollectionNewStudent.getSalleId();
                    studentCollectionNewStudent.setSalleId(salle);
                    studentCollectionNewStudent = em.merge(studentCollectionNewStudent);
                    if (oldSalleIdOfStudentCollectionNewStudent != null && !oldSalleIdOfStudentCollectionNewStudent.equals(salle)) {
                        oldSalleIdOfStudentCollectionNewStudent.getStudentCollection().remove(studentCollectionNewStudent);
                        oldSalleIdOfStudentCollectionNewStudent = em.merge(oldSalleIdOfStudentCollectionNewStudent);
                    }
                }
            }
            for (Groupe groupeCollectionNewGroupe : groupeCollectionNew) {
                if (!groupeCollectionOld.contains(groupeCollectionNewGroupe)) {
                    Salle oldSalleIdOfGroupeCollectionNewGroupe = groupeCollectionNewGroupe.getSalleId();
                    groupeCollectionNewGroupe.setSalleId(salle);
                    groupeCollectionNewGroupe = em.merge(groupeCollectionNewGroupe);
                    if (oldSalleIdOfGroupeCollectionNewGroupe != null && !oldSalleIdOfGroupeCollectionNewGroupe.equals(salle)) {
                        oldSalleIdOfGroupeCollectionNewGroupe.getGroupeCollection().remove(groupeCollectionNewGroupe);
                        oldSalleIdOfGroupeCollectionNewGroupe = em.merge(oldSalleIdOfGroupeCollectionNewGroupe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = salle.getSalleId();
                if (findSalle(id) == null) {
                    throw new NonexistentEntityException("The salle with id " + id + " no longer exists.");
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
            Salle salle;
            try {
                salle = em.getReference(Salle.class, id);
                salle.getSalleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salle with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Student> studentCollectionOrphanCheck = salle.getStudentCollection();
            for (Student studentCollectionOrphanCheckStudent : studentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Salle (" + salle + ") cannot be destroyed since the Student " + studentCollectionOrphanCheckStudent + " in its studentCollection field has a non-nullable salleId field.");
            }
            Collection<Groupe> groupeCollectionOrphanCheck = salle.getGroupeCollection();
            for (Groupe groupeCollectionOrphanCheckGroupe : groupeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Salle (" + salle + ") cannot be destroyed since the Groupe " + groupeCollectionOrphanCheckGroupe + " in its groupeCollection field has a non-nullable salleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Classe classeId = salle.getClasseId();
            if (classeId != null) {
                classeId.getSalleCollection().remove(salle);
                classeId = em.merge(classeId);
            }
            Sections sectionsId = salle.getSectionsId();
            if (sectionsId != null) {
                sectionsId.getSalleCollection().remove(salle);
                sectionsId = em.merge(sectionsId);
            }
            Users userModified = salle.getUserModified();
            if (userModified != null) {
                userModified.getSalleCollection().remove(salle);
                userModified = em.merge(userModified);
            }
            Users userCreated = salle.getUserCreated();
            if (userCreated != null) {
                userCreated.getSalleCollection().remove(salle);
                userCreated = em.merge(userCreated);
            }
            em.remove(salle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Salle> findSalleEntities() {
        return findSalleEntities(true, -1, -1);
    }

    public List<Salle> findSalleEntities(int maxResults, int firstResult) {
        return findSalleEntities(false, maxResults, firstResult);
    }

    private List<Salle> findSalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salle.class));
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

    public Salle findSalle(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salle.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salle> rt = cq.from(Salle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
