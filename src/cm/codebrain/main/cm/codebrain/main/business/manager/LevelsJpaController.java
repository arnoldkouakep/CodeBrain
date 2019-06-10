/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
import cm.codebrain.main.business.entitie.Levels;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Users;
import java.util.HashSet;
import java.util.Set;
import cm.codebrain.main.business.entitie.Widget;
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
public class LevelsJpaController extends CodeBrainEntityManager implements Serializable {

    public LevelsJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Levels levels) throws PreexistingEntityException, Exception {
        if (levels.getUsersSet() == null) {
            levels.setUsersSet(new HashSet<Users>());
        }
        if (levels.getWidgetSet() == null) {
            levels.setWidgetSet(new HashSet<Widget>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Users userModified = levels.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                levels.setUserModified(userModified);
            }
            Users userCreated = levels.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                levels.setUserCreated(userCreated);
            }
            Set<Users> attachedUsersSet = new HashSet<Users>();
            for (Users usersSetUsersToAttach : levels.getUsersSet()) {
                usersSetUsersToAttach = (Users) refreshEntity(usersSetUsersToAttach.getClass(), usersSetUsersToAttach.getUsersId());
                attachedUsersSet.add(usersSetUsersToAttach);
            }
            levels.setUsersSet(attachedUsersSet);
            Set<Widget> attachedWidgetSet = new HashSet<Widget>();
            for (Widget widgetSetWidgetToAttach : levels.getWidgetSet()) {
                widgetSetWidgetToAttach = (Widget) refreshEntity(widgetSetWidgetToAttach.getClass(), widgetSetWidgetToAttach.getWidgetId());
                attachedWidgetSet.add(widgetSetWidgetToAttach);
            }
            levels.setWidgetSet(attachedWidgetSet);
            persist(levels);
//            if (userModified != null) {
//                userModified.getLevelsSet().add(levels);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getLevelsSet().add(levels);
//                userCreated = em.merge(userCreated);
//            }
//            for (Users usersSetUsers : levels.getUsersSet()) {
//                Levels oldLevelsIdOfUsersSetUsers = usersSetUsers.getLevelsId();
//                usersSetUsers.setLevelsId(levels);
//                usersSetUsers = em.merge(usersSetUsers);
//                if (oldLevelsIdOfUsersSetUsers != null) {
//                    oldLevelsIdOfUsersSetUsers.getUsersSet().remove(usersSetUsers);
//                    oldLevelsIdOfUsersSetUsers = em.merge(oldLevelsIdOfUsersSetUsers);
//                }
//            }
//            for (Widget widgetSetWidget : levels.getWidgetSet()) {
//                Levels oldLevelsIdOfWidgetSetWidget = widgetSetWidget.getLevelsId();
//                widgetSetWidget.setLevelsId(levels);
//                widgetSetWidget = em.merge(widgetSetWidget);
//                if (oldLevelsIdOfWidgetSetWidget != null) {
//                    oldLevelsIdOfWidgetSetWidget.getWidgetSet().remove(widgetSetWidget);
//                    oldLevelsIdOfWidgetSetWidget = em.merge(oldLevelsIdOfWidgetSetWidget);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLevels(levels.getLevelsId()) != null) {
                throw new PreexistingEntityException("Levels " + levels + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Levels levels) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Levels persistentLevels = (Levels) find(Levels.class, levels.getLevelsId());
//            Users userModifiedOld = persistentLevels.getUserModified();
            Users userModifiedNew = levels.getUserModified();
            Users userCreatedOld = persistentLevels.getUserCreated();
            Users userCreatedNew = levels.getUserCreated();
            Set<Users> usersSetOld = persistentLevels.getUsersSet();
            Set<Users> usersSetNew = levels.getUsersSet();
            Set<Widget> widgetSetOld = persistentLevels.getWidgetSet();
            Set<Widget> widgetSetNew = levels.getWidgetSet();
            List<String> illegalOrphanMessages = null;
            for (Users usersSetOldUsers : usersSetOld) {
                if (!usersSetNew.contains(usersSetOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersSetOldUsers + " since its levelsId field is not nullable.");
                }
            }
            for (Widget widgetSetOldWidget : widgetSetOld) {
                if (!widgetSetNew.contains(widgetSetOldWidget)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Widget " + widgetSetOldWidget + " since its levelsId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                levels.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                levels.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                levels.setUserCreated(userCreatedOld);
            }
            Set<Users> attachedUsersSetNew = new HashSet<Users>();
            for (Users usersSetNewUsersToAttach : usersSetNew) {
                usersSetNewUsersToAttach = (Users) refreshEntity(usersSetNewUsersToAttach.getClass(), usersSetNewUsersToAttach.getUsersId());
                attachedUsersSetNew.add(usersSetNewUsersToAttach);
            }
            usersSetNew = attachedUsersSetNew;
            levels.setUsersSet(usersSetNew);
            Set<Widget> attachedWidgetSetNew = new HashSet<Widget>();
            for (Widget widgetSetNewWidgetToAttach : widgetSetNew) {
                widgetSetNewWidgetToAttach = (Widget) refreshEntity(widgetSetNewWidgetToAttach.getClass(), widgetSetNewWidgetToAttach.getWidgetId());
                attachedWidgetSetNew.add(widgetSetNewWidgetToAttach);
            }
            widgetSetNew = attachedWidgetSetNew;
            levels.setWidgetSet(widgetSetNew);
            levels = (Levels) merge(levels);
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getLevelsSet().remove(levels);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getLevelsSet().add(levels);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getLevelsSet().remove(levels);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getLevelsSet().add(levels);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            for (Users usersSetNewUsers : usersSetNew) {
//                if (!usersSetOld.contains(usersSetNewUsers)) {
//                    Levels oldLevelsIdOfUsersSetNewUsers = usersSetNewUsers.getLevelsId();
//                    usersSetNewUsers.setLevelsId(levels);
//                    usersSetNewUsers = em.merge(usersSetNewUsers);
//                    if (oldLevelsIdOfUsersSetNewUsers != null && !oldLevelsIdOfUsersSetNewUsers.equals(levels)) {
//                        oldLevelsIdOfUsersSetNewUsers.getUsersSet().remove(usersSetNewUsers);
//                        oldLevelsIdOfUsersSetNewUsers = em.merge(oldLevelsIdOfUsersSetNewUsers);
//                    }
//                }
//            }
//            for (Widget widgetSetNewWidget : widgetSetNew) {
//                if (!widgetSetOld.contains(widgetSetNewWidget)) {
//                    Levels oldLevelsIdOfWidgetSetNewWidget = widgetSetNewWidget.getLevelsId();
//                    widgetSetNewWidget.setLevelsId(levels);
//                    widgetSetNewWidget = em.merge(widgetSetNewWidget);
//                    if (oldLevelsIdOfWidgetSetNewWidget != null && !oldLevelsIdOfWidgetSetNewWidget.equals(levels)) {
//                        oldLevelsIdOfWidgetSetNewWidget.getWidgetSet().remove(widgetSetNewWidget);
//                        oldLevelsIdOfWidgetSetNewWidget = em.merge(oldLevelsIdOfWidgetSetNewWidget);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = levels.getLevelsId();
                if (findLevels(id) == null) {
                    throw new NonexistentEntityException("The levels with id " + id + " no longer exists.");
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
//            Levels levels;
//            try {
//                levels = em.getReference(Levels.class, id);
//                levels.getLevelsId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The levels with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Users> usersSetOrphanCheck = levels.getUsersSet();
//            for (Users usersSetOrphanCheckUsers : usersSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Levels (" + levels + ") cannot be destroyed since the Users " + usersSetOrphanCheckUsers + " in its usersSet field has a non-nullable levelsId field.");
//            }
//            Set<Widget> widgetSetOrphanCheck = levels.getWidgetSet();
//            for (Widget widgetSetOrphanCheckWidget : widgetSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Levels (" + levels + ") cannot be destroyed since the Widget " + widgetSetOrphanCheckWidget + " in its widgetSet field has a non-nullable levelsId field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Users userModified = levels.getUserModified();
//            if (userModified != null) {
//                userModified.getLevelsSet().remove(levels);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = levels.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getLevelsSet().remove(levels);
//                userCreated = em.merge(userCreated);
//            }
            remove(Levels.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<Levels> findLevelsEntities() {
        return findLevelsEntities(true, -1, -1);
    }

    public List<Levels> findLevelsEntities(int maxResults, int firstResult) {
        return findLevelsEntities(false, maxResults, firstResult);
    }

    private List<Levels> findLevelsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Levels.class));
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

    public Levels findLevels(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Levels.class, id);
        } finally {
            em.close();
        }
    }

    public int getLevelsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Levels> rt = cq.from(Levels.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
