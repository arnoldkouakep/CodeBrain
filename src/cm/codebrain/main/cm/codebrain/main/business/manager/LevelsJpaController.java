/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.entitie.Levels;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cm.codebrain.main.business.entitie.Users;
import java.util.ArrayList;
import java.util.Collection;
import cm.codebrain.main.business.entitie.Widget;
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
public class LevelsJpaController implements Serializable {

    public LevelsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Levels levels) throws PreexistingEntityException, Exception {
        if (levels.getUsersCollection() == null) {
            levels.setUsersCollection(new ArrayList<Users>());
        }
        if (levels.getWidgetCollection() == null) {
            levels.setWidgetCollection(new ArrayList<Widget>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userModified = levels.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                levels.setUserModified(userModified);
            }
            Users userCreated = levels.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                levels.setUserCreated(userCreated);
            }
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : levels.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getUsersId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            levels.setUsersCollection(attachedUsersCollection);
            Collection<Widget> attachedWidgetCollection = new ArrayList<Widget>();
            for (Widget widgetCollectionWidgetToAttach : levels.getWidgetCollection()) {
                widgetCollectionWidgetToAttach = em.getReference(widgetCollectionWidgetToAttach.getClass(), widgetCollectionWidgetToAttach.getWidgetId());
                attachedWidgetCollection.add(widgetCollectionWidgetToAttach);
            }
            levels.setWidgetCollection(attachedWidgetCollection);
            em.persist(levels);
            if (userModified != null) {
                userModified.getLevelsCollection().add(levels);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getLevelsCollection().add(levels);
                userCreated = em.merge(userCreated);
            }
            for (Users usersCollectionUsers : levels.getUsersCollection()) {
                Levels oldLevelsIdOfUsersCollectionUsers = usersCollectionUsers.getLevelsId();
                usersCollectionUsers.setLevelsId(levels);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldLevelsIdOfUsersCollectionUsers != null) {
                    oldLevelsIdOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldLevelsIdOfUsersCollectionUsers = em.merge(oldLevelsIdOfUsersCollectionUsers);
                }
            }
            for (Widget widgetCollectionWidget : levels.getWidgetCollection()) {
                Levels oldLevelsIdOfWidgetCollectionWidget = widgetCollectionWidget.getLevelsId();
                widgetCollectionWidget.setLevelsId(levels);
                widgetCollectionWidget = em.merge(widgetCollectionWidget);
                if (oldLevelsIdOfWidgetCollectionWidget != null) {
                    oldLevelsIdOfWidgetCollectionWidget.getWidgetCollection().remove(widgetCollectionWidget);
                    oldLevelsIdOfWidgetCollectionWidget = em.merge(oldLevelsIdOfWidgetCollectionWidget);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLevels(levels.getLevelsId()) != null) {
                throw new PreexistingEntityException("Levels " + levels + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Levels levels) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Levels persistentLevels = em.find(Levels.class, levels.getLevelsId());
            Users userModifiedOld = persistentLevels.getUserModified();
            Users userModifiedNew = levels.getUserModified();
            Users userCreatedOld = persistentLevels.getUserCreated();
            Users userCreatedNew = levels.getUserCreated();
            Collection<Users> usersCollectionOld = persistentLevels.getUsersCollection();
            Collection<Users> usersCollectionNew = levels.getUsersCollection();
            Collection<Widget> widgetCollectionOld = persistentLevels.getWidgetCollection();
            Collection<Widget> widgetCollectionNew = levels.getWidgetCollection();
            List<String> illegalOrphanMessages = null;
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersCollectionOldUsers + " since its levelsId field is not nullable.");
                }
            }
            for (Widget widgetCollectionOldWidget : widgetCollectionOld) {
                if (!widgetCollectionNew.contains(widgetCollectionOldWidget)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Widget " + widgetCollectionOldWidget + " since its levelsId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                levels.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                levels.setUserCreated(userCreatedNew);
            }
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getUsersId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            levels.setUsersCollection(usersCollectionNew);
            Collection<Widget> attachedWidgetCollectionNew = new ArrayList<Widget>();
            for (Widget widgetCollectionNewWidgetToAttach : widgetCollectionNew) {
                widgetCollectionNewWidgetToAttach = em.getReference(widgetCollectionNewWidgetToAttach.getClass(), widgetCollectionNewWidgetToAttach.getWidgetId());
                attachedWidgetCollectionNew.add(widgetCollectionNewWidgetToAttach);
            }
            widgetCollectionNew = attachedWidgetCollectionNew;
            levels.setWidgetCollection(widgetCollectionNew);
            levels = em.merge(levels);
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getLevelsCollection().remove(levels);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getLevelsCollection().add(levels);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getLevelsCollection().remove(levels);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getLevelsCollection().add(levels);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Levels oldLevelsIdOfUsersCollectionNewUsers = usersCollectionNewUsers.getLevelsId();
                    usersCollectionNewUsers.setLevelsId(levels);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldLevelsIdOfUsersCollectionNewUsers != null && !oldLevelsIdOfUsersCollectionNewUsers.equals(levels)) {
                        oldLevelsIdOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldLevelsIdOfUsersCollectionNewUsers = em.merge(oldLevelsIdOfUsersCollectionNewUsers);
                    }
                }
            }
            for (Widget widgetCollectionNewWidget : widgetCollectionNew) {
                if (!widgetCollectionOld.contains(widgetCollectionNewWidget)) {
                    Levels oldLevelsIdOfWidgetCollectionNewWidget = widgetCollectionNewWidget.getLevelsId();
                    widgetCollectionNewWidget.setLevelsId(levels);
                    widgetCollectionNewWidget = em.merge(widgetCollectionNewWidget);
                    if (oldLevelsIdOfWidgetCollectionNewWidget != null && !oldLevelsIdOfWidgetCollectionNewWidget.equals(levels)) {
                        oldLevelsIdOfWidgetCollectionNewWidget.getWidgetCollection().remove(widgetCollectionNewWidget);
                        oldLevelsIdOfWidgetCollectionNewWidget = em.merge(oldLevelsIdOfWidgetCollectionNewWidget);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = levels.getLevelsId();
                if (findLevels(id) == null) {
                    throw new NonexistentEntityException("The levels with id " + id + " no longer exists.");
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
            Levels levels;
            try {
                levels = em.getReference(Levels.class, id);
                levels.getLevelsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The levels with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Users> usersCollectionOrphanCheck = levels.getUsersCollection();
            for (Users usersCollectionOrphanCheckUsers : usersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Levels (" + levels + ") cannot be destroyed since the Users " + usersCollectionOrphanCheckUsers + " in its usersCollection field has a non-nullable levelsId field.");
            }
            Collection<Widget> widgetCollectionOrphanCheck = levels.getWidgetCollection();
            for (Widget widgetCollectionOrphanCheckWidget : widgetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Levels (" + levels + ") cannot be destroyed since the Widget " + widgetCollectionOrphanCheckWidget + " in its widgetCollection field has a non-nullable levelsId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users userModified = levels.getUserModified();
            if (userModified != null) {
                userModified.getLevelsCollection().remove(levels);
                userModified = em.merge(userModified);
            }
            Users userCreated = levels.getUserCreated();
            if (userCreated != null) {
                userCreated.getLevelsCollection().remove(levels);
                userCreated = em.merge(userCreated);
            }
            em.remove(levels);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
