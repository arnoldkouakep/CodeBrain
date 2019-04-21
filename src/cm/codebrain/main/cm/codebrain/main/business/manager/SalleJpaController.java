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
import cm.codebrain.main.business.entitie.Classe;
import cm.codebrain.main.business.entitie.Groupe;
import cm.codebrain.main.business.entitie.Salle;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.entitie.Student;
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
public class SalleJpaController extends CodeBrainEntityManager implements Serializable {

    public SalleJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Salle salle) throws PreexistingEntityException, Exception {
        if (salle.getStudentSet() == null) {
            salle.setStudentSet(new HashSet<Student>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            getTransaction().begin();
            Classe classeId = salle.getClasseId();
            if (classeId != null) {
                classeId = (Classe) refreshEntity(classeId.getClass(), classeId.getClasseId());
                salle.setClasseId(classeId);
            }
            Groupe groupeId = salle.getGroupeId();
            if (groupeId != null) {
                groupeId = (Groupe) refreshEntity(groupeId.getClass(), groupeId.getGroupeId());
                salle.setGroupeId(groupeId);
            }
            Users userModified = salle.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                salle.setUserModified(userModified);
            }
            Users userCreated = salle.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                salle.setUserCreated(userCreated);
            }
            Set<Student> attachedStudentSet = new HashSet<Student>();
            for (Student studentSetStudentToAttach : salle.getStudentSet()) {
                studentSetStudentToAttach = (Student) refreshEntity(studentSetStudentToAttach.getClass(), studentSetStudentToAttach.getStudentId());
                attachedStudentSet.add(studentSetStudentToAttach);
            }
            salle.setStudentSet(attachedStudentSet);
            persist(salle);
//            if (classeId != null) {
//                classeId.getSalleSet().add(salle);
//                classeId = merge(classeId);
//            }
//            if (groupeId != null) {
//                groupeId.getSalleSet().add(salle);
//                groupeId = merge(groupeId);
//            }
//            if (userModified != null) {
//                userModified.getSalleSet().add(salle);
//                userModified = merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getSalleSet().add(salle);
//                userCreated = merge(userCreated);
//            }
//            for (Student studentSetStudent : salle.getStudentSet()) {
//                Salle oldSalleIdOfStudentSetStudent = studentSetStudent.getSalleId();
//                studentSetStudent.setSalleId(salle);
//                studentSetStudent = merge(studentSetStudent);
//                if (oldSalleIdOfStudentSetStudent != null) {
//                    oldSalleIdOfStudentSetStudent.getStudentSet().remove(studentSetStudent);
//                    oldSalleIdOfStudentSetStudent = merge(oldSalleIdOfStudentSetStudent);
//                }
//            }
//            getTransaction().commit();
        } catch (Exception ex) {
            if (findSalle(salle.getSalleId()) != null) {
                throw new PreexistingEntityException("Salle " + salle + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                close();
//            }
        }
    }

    public void edit(Salle salle) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            getTransaction().begin();
            Salle persistentSalle = (Salle) find(Salle.class, salle.getSalleId());
            Classe classeIdOld = persistentSalle.getClasseId();
            Classe classeIdNew = salle.getClasseId();
            Groupe groupeIdOld = persistentSalle.getGroupeId();
            Groupe groupeIdNew = salle.getGroupeId();
            Users userModifiedOld = persistentSalle.getUserModified();
            Users userModifiedNew = salle.getUserModified();
            Users userCreatedOld = persistentSalle.getUserCreated();
            Users userCreatedNew = salle.getUserCreated();
            Set<Student> studentSetOld = persistentSalle.getStudentSet();
            Set<Student> studentSetNew = salle.getStudentSet();
            List<String> illegalOrphanMessages = null;
            for (Student studentSetOldStudent : studentSetOld) {
                if (!studentSetNew.contains(studentSetOldStudent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Student " + studentSetOldStudent + " since its salleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (classeIdNew != null) {
                classeIdNew = (Classe) refreshEntity(classeIdNew.getClass(), classeIdNew.getClasseId());
                salle.setClasseId(classeIdNew);
            }
            if (groupeIdNew != null) {
                try{
                    groupeIdNew = (Groupe) refreshEntity(groupeIdNew.getClass(), groupeIdNew.getGroupeId());
                }catch(Exception e){}
                salle.setGroupeId(groupeIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                salle.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                salle.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                salle.setUserCreated(userCreatedOld);
            }
            Set<Student> attachedStudentSetNew = new HashSet<Student>();
            for (Student studentSetNewStudentToAttach : studentSetNew) {
                studentSetNewStudentToAttach = (Student) refreshEntity(studentSetNewStudentToAttach.getClass(), studentSetNewStudentToAttach.getStudentId());
                attachedStudentSetNew.add(studentSetNewStudentToAttach);
            }
            studentSetNew = attachedStudentSetNew;
            salle.setStudentSet(studentSetNew);
            salle = (Salle) merge(salle);
//            if (classeIdOld != null && !classeIdOld.equals(classeIdNew)) {
//                classeIdOld.getSalleSet().remove(salle);
//                classeIdOld = merge(classeIdOld);
//            }
//            if (classeIdNew != null && !classeIdNew.equals(classeIdOld)) {
//                classeIdNew.getSalleSet().add(salle);
//                classeIdNew = merge(classeIdNew);
//            }
//            if (groupeIdOld != null && !groupeIdOld.equals(groupeIdNew)) {
//                groupeIdOld.getSalleSet().remove(salle);
//                groupeIdOld = merge(groupeIdOld);
//            }
//            if (groupeIdNew != null && !groupeIdNew.equals(groupeIdOld)) {
//                groupeIdNew.getSalleSet().add(salle);
//                groupeIdNew = merge(groupeIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getSalleSet().remove(salle);
//                userModifiedOld = merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getSalleSet().add(salle);
//                userModifiedNew = merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getSalleSet().remove(salle);
//                userCreatedOld = merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getSalleSet().add(salle);
//                userCreatedNew = merge(userCreatedNew);
//            }
//            for (Student studentSetNewStudent : studentSetNew) {
//                if (!studentSetOld.contains(studentSetNewStudent)) {
//                    Salle oldSalleIdOfStudentSetNewStudent = studentSetNewStudent.getSalleId();
//                    studentSetNewStudent.setSalleId(salle);
//                    studentSetNewStudent = merge(studentSetNewStudent);
//                    if (oldSalleIdOfStudentSetNewStudent != null && !oldSalleIdOfStudentSetNewStudent.equals(salle)) {
//                        oldSalleIdOfStudentSetNewStudent.getStudentSet().remove(studentSetNewStudent);
//                        oldSalleIdOfStudentSetNewStudent = merge(oldSalleIdOfStudentSetNewStudent);
//                    }
//                }
//            }
//            getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = salle.getSalleId();
                if (findSalle(id) == null) {
                    throw new NonexistentEntityException("The salle with id " + id + " no longer exists.");
                }
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                close();
//            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            getTransaction().begin();
//            Salle salle;
//            try {
//                salle = () refreshEntity(Salle.class, id);
//                salle.getSalleId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The salle with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Student> studentSetOrphanCheck = salle.getStudentSet();
//            for (Student studentSetOrphanCheckStudent : studentSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Salle (" + salle + ") cannot be destroyed since the Student " + studentSetOrphanCheckStudent + " in its studentSet field has a non-nullable salleId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Classe classeId = salle.getClasseId();
//            if (classeId != null) {
//                classeId.getSalleSet().remove(salle);
//                classeId = merge(classeId);
//            }
//            Groupe groupeId = salle.getGroupeId();
//            if (groupeId != null) {
//                groupeId.getSalleSet().remove(salle);
//                groupeId = merge(groupeId);
//            }
//            Users userModified = salle.getUserModified();
//            if (userModified != null) {
//                userModified.getSalleSet().remove(salle);
//                userModified = merge(userModified);
//            }
//            Users userCreated = salle.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getSalleSet().remove(salle);
//                userCreated = merge(userCreated);
//            }
            remove(Salle.class, id);
//            getTransaction().commit();
//        } finally {
//            if (em != null) {
//                close();
//            }
//        }
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
