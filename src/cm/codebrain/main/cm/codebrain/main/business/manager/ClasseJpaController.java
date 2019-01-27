/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.entitie.Classe;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.entitie.Sections;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Student;
import java.util.ArrayList;
import java.util.Collection;
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
public class ClasseJpaController implements Serializable {

    public ClasseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Classe classe) throws PreexistingEntityException, Exception {
        if (classe.getStudentCollection() == null) {
            classe.setStudentCollection(new ArrayList<Student>());
        }
        if (classe.getSalleCollection() == null) {
            classe.setSalleCollection(new ArrayList<Salle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupe groupeId = classe.getGroupeId();
            if (groupeId != null) {
                groupeId = em.getReference(groupeId.getClass(), groupeId.getGroupeId());
                classe.setGroupeId(groupeId);
            }
            Sections sectionsId = classe.getSectionsId();
            if (sectionsId != null) {
                sectionsId = em.getReference(sectionsId.getClass(), sectionsId.getSectionsId());
                classe.setSectionsId(sectionsId);
            }
            Users userModified = classe.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                classe.setUserModified(userModified);
            }
            Users userCreated = classe.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                classe.setUserCreated(userCreated);
            }
            Collection<Student> attachedStudentCollection = new ArrayList<Student>();
            for (Student studentCollectionStudentToAttach : classe.getStudentCollection()) {
                studentCollectionStudentToAttach = em.getReference(studentCollectionStudentToAttach.getClass(), studentCollectionStudentToAttach.getStudentId());
                attachedStudentCollection.add(studentCollectionStudentToAttach);
            }
            classe.setStudentCollection(attachedStudentCollection);
            Collection<Salle> attachedSalleCollection = new ArrayList<Salle>();
            for (Salle salleCollectionSalleToAttach : classe.getSalleCollection()) {
                salleCollectionSalleToAttach = em.getReference(salleCollectionSalleToAttach.getClass(), salleCollectionSalleToAttach.getSalleId());
                attachedSalleCollection.add(salleCollectionSalleToAttach);
            }
            classe.setSalleCollection(attachedSalleCollection);
            em.persist(classe);
            if (groupeId != null) {
                groupeId.getClasseCollection().add(classe);
                groupeId = em.merge(groupeId);
            }
            if (sectionsId != null) {
                sectionsId.getClasseCollection().add(classe);
                sectionsId = em.merge(sectionsId);
            }
            if (userModified != null) {
                userModified.getClasseCollection().add(classe);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getClasseCollection().add(classe);
                userCreated = em.merge(userCreated);
            }
            for (Student studentCollectionStudent : classe.getStudentCollection()) {
                Classe oldClasseIdOfStudentCollectionStudent = studentCollectionStudent.getClasseId();
                studentCollectionStudent.setClasseId(classe);
                studentCollectionStudent = em.merge(studentCollectionStudent);
                if (oldClasseIdOfStudentCollectionStudent != null) {
                    oldClasseIdOfStudentCollectionStudent.getStudentCollection().remove(studentCollectionStudent);
                    oldClasseIdOfStudentCollectionStudent = em.merge(oldClasseIdOfStudentCollectionStudent);
                }
            }
            for (Salle salleCollectionSalle : classe.getSalleCollection()) {
                Classe oldClasseIdOfSalleCollectionSalle = salleCollectionSalle.getClasseId();
                salleCollectionSalle.setClasseId(classe);
                salleCollectionSalle = em.merge(salleCollectionSalle);
                if (oldClasseIdOfSalleCollectionSalle != null) {
                    oldClasseIdOfSalleCollectionSalle.getSalleCollection().remove(salleCollectionSalle);
                    oldClasseIdOfSalleCollectionSalle = em.merge(oldClasseIdOfSalleCollectionSalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClasse(classe.getClasseId()) != null) {
                throw new PreexistingEntityException("Classe " + classe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Classe classe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Classe persistentClasse = em.find(Classe.class, classe.getClasseId());
            Groupe groupeIdOld = persistentClasse.getGroupeId();
            Groupe groupeIdNew = classe.getGroupeId();
            Sections sectionsIdOld = persistentClasse.getSectionsId();
            Sections sectionsIdNew = classe.getSectionsId();
            Users userModifiedOld = persistentClasse.getUserModified();
            Users userModifiedNew = classe.getUserModified();
            Users userCreatedOld = persistentClasse.getUserCreated();
            Users userCreatedNew = classe.getUserCreated();
            Collection<Student> studentCollectionOld = persistentClasse.getStudentCollection();
            Collection<Student> studentCollectionNew = classe.getStudentCollection();
            Collection<Salle> salleCollectionOld = persistentClasse.getSalleCollection();
            Collection<Salle> salleCollectionNew = classe.getSalleCollection();
            List<String> illegalOrphanMessages = null;
            for (Student studentCollectionOldStudent : studentCollectionOld) {
                if (!studentCollectionNew.contains(studentCollectionOldStudent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Student " + studentCollectionOldStudent + " since its classeId field is not nullable.");
                }
            }
            for (Salle salleCollectionOldSalle : salleCollectionOld) {
                if (!salleCollectionNew.contains(salleCollectionOldSalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Salle " + salleCollectionOldSalle + " since its classeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (groupeIdNew != null) {
                groupeIdNew = em.getReference(groupeIdNew.getClass(), groupeIdNew.getGroupeId());
                classe.setGroupeId(groupeIdNew);
            }
            if (sectionsIdNew != null) {
                sectionsIdNew = em.getReference(sectionsIdNew.getClass(), sectionsIdNew.getSectionsId());
                classe.setSectionsId(sectionsIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                classe.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                classe.setUserCreated(userCreatedNew);
            }
            Collection<Student> attachedStudentCollectionNew = new ArrayList<Student>();
            for (Student studentCollectionNewStudentToAttach : studentCollectionNew) {
                studentCollectionNewStudentToAttach = em.getReference(studentCollectionNewStudentToAttach.getClass(), studentCollectionNewStudentToAttach.getStudentId());
                attachedStudentCollectionNew.add(studentCollectionNewStudentToAttach);
            }
            studentCollectionNew = attachedStudentCollectionNew;
            classe.setStudentCollection(studentCollectionNew);
            Collection<Salle> attachedSalleCollectionNew = new ArrayList<Salle>();
            for (Salle salleCollectionNewSalleToAttach : salleCollectionNew) {
                salleCollectionNewSalleToAttach = em.getReference(salleCollectionNewSalleToAttach.getClass(), salleCollectionNewSalleToAttach.getSalleId());
                attachedSalleCollectionNew.add(salleCollectionNewSalleToAttach);
            }
            salleCollectionNew = attachedSalleCollectionNew;
            classe.setSalleCollection(salleCollectionNew);
            classe = em.merge(classe);
            if (groupeIdOld != null && !groupeIdOld.equals(groupeIdNew)) {
                groupeIdOld.getClasseCollection().remove(classe);
                groupeIdOld = em.merge(groupeIdOld);
            }
            if (groupeIdNew != null && !groupeIdNew.equals(groupeIdOld)) {
                groupeIdNew.getClasseCollection().add(classe);
                groupeIdNew = em.merge(groupeIdNew);
            }
            if (sectionsIdOld != null && !sectionsIdOld.equals(sectionsIdNew)) {
                sectionsIdOld.getClasseCollection().remove(classe);
                sectionsIdOld = em.merge(sectionsIdOld);
            }
            if (sectionsIdNew != null && !sectionsIdNew.equals(sectionsIdOld)) {
                sectionsIdNew.getClasseCollection().add(classe);
                sectionsIdNew = em.merge(sectionsIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getClasseCollection().remove(classe);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getClasseCollection().add(classe);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getClasseCollection().remove(classe);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getClasseCollection().add(classe);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Student studentCollectionNewStudent : studentCollectionNew) {
                if (!studentCollectionOld.contains(studentCollectionNewStudent)) {
                    Classe oldClasseIdOfStudentCollectionNewStudent = studentCollectionNewStudent.getClasseId();
                    studentCollectionNewStudent.setClasseId(classe);
                    studentCollectionNewStudent = em.merge(studentCollectionNewStudent);
                    if (oldClasseIdOfStudentCollectionNewStudent != null && !oldClasseIdOfStudentCollectionNewStudent.equals(classe)) {
                        oldClasseIdOfStudentCollectionNewStudent.getStudentCollection().remove(studentCollectionNewStudent);
                        oldClasseIdOfStudentCollectionNewStudent = em.merge(oldClasseIdOfStudentCollectionNewStudent);
                    }
                }
            }
            for (Salle salleCollectionNewSalle : salleCollectionNew) {
                if (!salleCollectionOld.contains(salleCollectionNewSalle)) {
                    Classe oldClasseIdOfSalleCollectionNewSalle = salleCollectionNewSalle.getClasseId();
                    salleCollectionNewSalle.setClasseId(classe);
                    salleCollectionNewSalle = em.merge(salleCollectionNewSalle);
                    if (oldClasseIdOfSalleCollectionNewSalle != null && !oldClasseIdOfSalleCollectionNewSalle.equals(classe)) {
                        oldClasseIdOfSalleCollectionNewSalle.getSalleCollection().remove(salleCollectionNewSalle);
                        oldClasseIdOfSalleCollectionNewSalle = em.merge(oldClasseIdOfSalleCollectionNewSalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = classe.getClasseId();
                if (findClasse(id) == null) {
                    throw new NonexistentEntityException("The classe with id " + id + " no longer exists.");
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
            Classe classe;
            try {
                classe = em.getReference(Classe.class, id);
                classe.getClasseId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The classe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Student> studentCollectionOrphanCheck = classe.getStudentCollection();
            for (Student studentCollectionOrphanCheckStudent : studentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Classe (" + classe + ") cannot be destroyed since the Student " + studentCollectionOrphanCheckStudent + " in its studentCollection field has a non-nullable classeId field.");
            }
            Collection<Salle> salleCollectionOrphanCheck = classe.getSalleCollection();
            for (Salle salleCollectionOrphanCheckSalle : salleCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Classe (" + classe + ") cannot be destroyed since the Salle " + salleCollectionOrphanCheckSalle + " in its salleCollection field has a non-nullable classeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Groupe groupeId = classe.getGroupeId();
            if (groupeId != null) {
                groupeId.getClasseCollection().remove(classe);
                groupeId = em.merge(groupeId);
            }
            Sections sectionsId = classe.getSectionsId();
            if (sectionsId != null) {
                sectionsId.getClasseCollection().remove(classe);
                sectionsId = em.merge(sectionsId);
            }
            Users userModified = classe.getUserModified();
            if (userModified != null) {
                userModified.getClasseCollection().remove(classe);
                userModified = em.merge(userModified);
            }
            Users userCreated = classe.getUserCreated();
            if (userCreated != null) {
                userCreated.getClasseCollection().remove(classe);
                userCreated = em.merge(userCreated);
            }
            em.remove(classe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
