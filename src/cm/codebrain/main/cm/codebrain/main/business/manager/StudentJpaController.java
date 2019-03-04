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
import cm.codebrain.main.business.entitie.Salle;
import cm.codebrain.main.business.entitie.Student;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author KSA-INET
 */
public class StudentJpaController implements Serializable {

    public StudentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Student student) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salle salleId = student.getSalleId();
            if (salleId != null) {
                salleId = em.getReference(salleId.getClass(), salleId.getSalleId());
                student.setSalleId(salleId);
            }
            Users userModified = student.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                student.setUserModified(userModified);
            }
            Users userCreated = student.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                student.setUserCreated(userCreated);
            }
            em.persist(student);
            if (salleId != null) {
                salleId.getStudentCollection().add(student);
                salleId = em.merge(salleId);
            }
            if (userModified != null) {
                userModified.getStudentCollection().add(student);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getStudentCollection().add(student);
                userCreated = em.merge(userCreated);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStudent(student.getStudentId()) != null) {
                throw new PreexistingEntityException("Student " + student + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Student student) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student persistentStudent = em.find(Student.class, student.getStudentId());
            Salle salleIdOld = persistentStudent.getSalleId();
            Salle salleIdNew = student.getSalleId();
            Users userModifiedOld = persistentStudent.getUserModified();
            Users userModifiedNew = student.getUserModified();
            Users userCreatedOld = persistentStudent.getUserCreated();
            Users userCreatedNew = student.getUserCreated();
            if (salleIdNew != null) {
                salleIdNew = em.getReference(salleIdNew.getClass(), salleIdNew.getSalleId());
                student.setSalleId(salleIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                student.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                student.setUserCreated(userCreatedNew);
            }
            student = em.merge(student);
            if (salleIdOld != null && !salleIdOld.equals(salleIdNew)) {
                salleIdOld.getStudentCollection().remove(student);
                salleIdOld = em.merge(salleIdOld);
            }
            if (salleIdNew != null && !salleIdNew.equals(salleIdOld)) {
                salleIdNew.getStudentCollection().add(student);
                salleIdNew = em.merge(salleIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getStudentCollection().remove(student);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getStudentCollection().add(student);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getStudentCollection().remove(student);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getStudentCollection().add(student);
                userCreatedNew = em.merge(userCreatedNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = student.getStudentId();
                if (findStudent(id) == null) {
                    throw new NonexistentEntityException("The student with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student student;
            try {
                student = em.getReference(Student.class, id);
                student.getStudentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The student with id " + id + " no longer exists.", enfe);
            }
            Salle salleId = student.getSalleId();
            if (salleId != null) {
                salleId.getStudentCollection().remove(student);
                salleId = em.merge(salleId);
            }
            Users userModified = student.getUserModified();
            if (userModified != null) {
                userModified.getStudentCollection().remove(student);
                userModified = em.merge(userModified);
            }
            Users userCreated = student.getUserCreated();
            if (userCreated != null) {
                userCreated.getStudentCollection().remove(student);
                userCreated = em.merge(userCreated);
            }
            em.remove(student);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Student> findStudentEntities() {
        return findStudentEntities(true, -1, -1);
    }

    public List<Student> findStudentEntities(int maxResults, int firstResult) {
        return findStudentEntities(false, maxResults, firstResult);
    }

    private List<Student> findStudentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Student.class));
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

    public Student findStudent(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    public int getStudentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Student> rt = cq.from(Student.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
