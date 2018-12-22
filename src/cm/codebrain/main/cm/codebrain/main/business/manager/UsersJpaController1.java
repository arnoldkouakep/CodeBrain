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
import cm.codebrain.main.business.entitie.Levels;
import cm.codebrain.main.business.entitie.Users;
import java.util.ArrayList;
import java.util.Collection;
import cm.codebrain.main.business.entitie.Widget;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author KSA-INET
 */
public class UsersJpaController1 implements Serializable {

    public UsersJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws PreexistingEntityException, Exception {
        if (users.getUsersCollection() == null) {
            users.setUsersCollection(new ArrayList<Users>());
        }
        if (users.getUsersCollection1() == null) {
            users.setUsersCollection1(new ArrayList<Users>());
        }
        if (users.getWidgetCollection() == null) {
            users.setWidgetCollection(new ArrayList<Widget>());
        }
        if (users.getWidgetCollection1() == null) {
            users.setWidgetCollection1(new ArrayList<Widget>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Levels levelsId = users.getLevelsId();
            if (levelsId != null) {
                levelsId = em.getReference(levelsId.getClass(), levelsId.getLevelsId());
                users.setLevelsId(levelsId);
            }
            Users userModified = users.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                users.setUserModified(userModified);
            }
            Users userCreated = users.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                users.setUserCreated(userCreated);
            }
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : users.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getUsersId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            users.setUsersCollection(attachedUsersCollection);
            Collection<Users> attachedUsersCollection1 = new ArrayList<Users>();
            for (Users usersCollection1UsersToAttach : users.getUsersCollection1()) {
                usersCollection1UsersToAttach = em.getReference(usersCollection1UsersToAttach.getClass(), usersCollection1UsersToAttach.getUsersId());
                attachedUsersCollection1.add(usersCollection1UsersToAttach);
            }
            users.setUsersCollection1(attachedUsersCollection1);
            Collection<Widget> attachedWidgetCollection = new ArrayList<Widget>();
            for (Widget widgetCollectionWidgetToAttach : users.getWidgetCollection()) {
                widgetCollectionWidgetToAttach = em.getReference(widgetCollectionWidgetToAttach.getClass(), widgetCollectionWidgetToAttach.getWidgetId());
                attachedWidgetCollection.add(widgetCollectionWidgetToAttach);
            }
            users.setWidgetCollection(attachedWidgetCollection);
            Collection<Widget> attachedWidgetCollection1 = new ArrayList<Widget>();
            for (Widget widgetCollection1WidgetToAttach : users.getWidgetCollection1()) {
                widgetCollection1WidgetToAttach = em.getReference(widgetCollection1WidgetToAttach.getClass(), widgetCollection1WidgetToAttach.getWidgetId());
                attachedWidgetCollection1.add(widgetCollection1WidgetToAttach);
            }
            users.setWidgetCollection1(attachedWidgetCollection1);
            em.persist(users);
            if (levelsId != null) {
                levelsId.getUsersCollection().add(users);
                levelsId = em.merge(levelsId);
            }
            if (userModified != null) {
                userModified.getUsersCollection().add(users);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getUsersCollection().add(users);
                userCreated = em.merge(userCreated);
            }
            for (Users usersCollectionUsers : users.getUsersCollection()) {
                Users oldUserModifiedOfUsersCollectionUsers = usersCollectionUsers.getUserModified();
                usersCollectionUsers.setUserModified(users);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldUserModifiedOfUsersCollectionUsers != null) {
                    oldUserModifiedOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldUserModifiedOfUsersCollectionUsers = em.merge(oldUserModifiedOfUsersCollectionUsers);
                }
            }
            for (Users usersCollection1Users : users.getUsersCollection1()) {
                Users oldUserCreatedOfUsersCollection1Users = usersCollection1Users.getUserCreated();
                usersCollection1Users.setUserCreated(users);
                usersCollection1Users = em.merge(usersCollection1Users);
                if (oldUserCreatedOfUsersCollection1Users != null) {
                    oldUserCreatedOfUsersCollection1Users.getUsersCollection1().remove(usersCollection1Users);
                    oldUserCreatedOfUsersCollection1Users = em.merge(oldUserCreatedOfUsersCollection1Users);
                }
            }
            for (Widget widgetCollectionWidget : users.getWidgetCollection()) {
                Users oldUserModifiedOfWidgetCollectionWidget = widgetCollectionWidget.getUserModified();
                widgetCollectionWidget.setUserModified(users);
                widgetCollectionWidget = em.merge(widgetCollectionWidget);
                if (oldUserModifiedOfWidgetCollectionWidget != null) {
                    oldUserModifiedOfWidgetCollectionWidget.getWidgetCollection().remove(widgetCollectionWidget);
                    oldUserModifiedOfWidgetCollectionWidget = em.merge(oldUserModifiedOfWidgetCollectionWidget);
                }
            }
            for (Widget widgetCollection1Widget : users.getWidgetCollection1()) {
                Users oldUserCreatedOfWidgetCollection1Widget = widgetCollection1Widget.getUserCreated();
                widgetCollection1Widget.setUserCreated(users);
                widgetCollection1Widget = em.merge(widgetCollection1Widget);
                if (oldUserCreatedOfWidgetCollection1Widget != null) {
                    oldUserCreatedOfWidgetCollection1Widget.getWidgetCollection1().remove(widgetCollection1Widget);
                    oldUserCreatedOfWidgetCollection1Widget = em.merge(oldUserCreatedOfWidgetCollection1Widget);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsers(users.getUsersId()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getUsersId());
            Levels levelsIdOld = persistentUsers.getLevelsId();
            Levels levelsIdNew = users.getLevelsId();
            Users userModifiedOld = persistentUsers.getUserModified();
            Users userModifiedNew = users.getUserModified();
            Users userCreatedOld = persistentUsers.getUserCreated();
            Users userCreatedNew = users.getUserCreated();
            Collection<Users> usersCollectionOld = persistentUsers.getUsersCollection();
            Collection<Users> usersCollectionNew = users.getUsersCollection();
            Collection<Users> usersCollection1Old = persistentUsers.getUsersCollection1();
            Collection<Users> usersCollection1New = users.getUsersCollection1();
            Collection<Widget> widgetCollectionOld = persistentUsers.getWidgetCollection();
            Collection<Widget> widgetCollectionNew = users.getWidgetCollection();
            Collection<Widget> widgetCollection1Old = persistentUsers.getWidgetCollection1();
            Collection<Widget> widgetCollection1New = users.getWidgetCollection1();
            if (levelsIdNew != null) {
                levelsIdNew = em.getReference(levelsIdNew.getClass(), levelsIdNew.getLevelsId());
                users.setLevelsId(levelsIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                users.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                users.setUserCreated(userCreatedNew);
            }
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getUsersId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            users.setUsersCollection(usersCollectionNew);
            Collection<Users> attachedUsersCollection1New = new ArrayList<Users>();
            for (Users usersCollection1NewUsersToAttach : usersCollection1New) {
                usersCollection1NewUsersToAttach = em.getReference(usersCollection1NewUsersToAttach.getClass(), usersCollection1NewUsersToAttach.getUsersId());
                attachedUsersCollection1New.add(usersCollection1NewUsersToAttach);
            }
            usersCollection1New = attachedUsersCollection1New;
            users.setUsersCollection1(usersCollection1New);
            Collection<Widget> attachedWidgetCollectionNew = new ArrayList<Widget>();
            for (Widget widgetCollectionNewWidgetToAttach : widgetCollectionNew) {
                widgetCollectionNewWidgetToAttach = em.getReference(widgetCollectionNewWidgetToAttach.getClass(), widgetCollectionNewWidgetToAttach.getWidgetId());
                attachedWidgetCollectionNew.add(widgetCollectionNewWidgetToAttach);
            }
            widgetCollectionNew = attachedWidgetCollectionNew;
            users.setWidgetCollection(widgetCollectionNew);
            Collection<Widget> attachedWidgetCollection1New = new ArrayList<Widget>();
            for (Widget widgetCollection1NewWidgetToAttach : widgetCollection1New) {
                widgetCollection1NewWidgetToAttach = em.getReference(widgetCollection1NewWidgetToAttach.getClass(), widgetCollection1NewWidgetToAttach.getWidgetId());
                attachedWidgetCollection1New.add(widgetCollection1NewWidgetToAttach);
            }
            widgetCollection1New = attachedWidgetCollection1New;
            users.setWidgetCollection1(widgetCollection1New);
            users = em.merge(users);
            if (levelsIdOld != null && !levelsIdOld.equals(levelsIdNew)) {
                levelsIdOld.getUsersCollection().remove(users);
                levelsIdOld = em.merge(levelsIdOld);
            }
            if (levelsIdNew != null && !levelsIdNew.equals(levelsIdOld)) {
                levelsIdNew.getUsersCollection().add(users);
                levelsIdNew = em.merge(levelsIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getUsersCollection().remove(users);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getUsersCollection().add(users);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getUsersCollection().remove(users);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getUsersCollection().add(users);
                userCreatedNew = em.merge(userCreatedNew);
            }
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    usersCollectionOldUsers.setUserModified(null);
                    usersCollectionOldUsers = em.merge(usersCollectionOldUsers);
                }
            }
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Users oldUserModifiedOfUsersCollectionNewUsers = usersCollectionNewUsers.getUserModified();
                    usersCollectionNewUsers.setUserModified(users);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldUserModifiedOfUsersCollectionNewUsers != null && !oldUserModifiedOfUsersCollectionNewUsers.equals(users)) {
                        oldUserModifiedOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldUserModifiedOfUsersCollectionNewUsers = em.merge(oldUserModifiedOfUsersCollectionNewUsers);
                    }
                }
            }
            for (Users usersCollection1OldUsers : usersCollection1Old) {
                if (!usersCollection1New.contains(usersCollection1OldUsers)) {
                    usersCollection1OldUsers.setUserCreated(null);
                    usersCollection1OldUsers = em.merge(usersCollection1OldUsers);
                }
            }
            for (Users usersCollection1NewUsers : usersCollection1New) {
                if (!usersCollection1Old.contains(usersCollection1NewUsers)) {
                    Users oldUserCreatedOfUsersCollection1NewUsers = usersCollection1NewUsers.getUserCreated();
                    usersCollection1NewUsers.setUserCreated(users);
                    usersCollection1NewUsers = em.merge(usersCollection1NewUsers);
                    if (oldUserCreatedOfUsersCollection1NewUsers != null && !oldUserCreatedOfUsersCollection1NewUsers.equals(users)) {
                        oldUserCreatedOfUsersCollection1NewUsers.getUsersCollection1().remove(usersCollection1NewUsers);
                        oldUserCreatedOfUsersCollection1NewUsers = em.merge(oldUserCreatedOfUsersCollection1NewUsers);
                    }
                }
            }
            for (Widget widgetCollectionOldWidget : widgetCollectionOld) {
                if (!widgetCollectionNew.contains(widgetCollectionOldWidget)) {
                    widgetCollectionOldWidget.setUserModified(null);
                    widgetCollectionOldWidget = em.merge(widgetCollectionOldWidget);
                }
            }
            for (Widget widgetCollectionNewWidget : widgetCollectionNew) {
                if (!widgetCollectionOld.contains(widgetCollectionNewWidget)) {
                    Users oldUserModifiedOfWidgetCollectionNewWidget = widgetCollectionNewWidget.getUserModified();
                    widgetCollectionNewWidget.setUserModified(users);
                    widgetCollectionNewWidget = em.merge(widgetCollectionNewWidget);
                    if (oldUserModifiedOfWidgetCollectionNewWidget != null && !oldUserModifiedOfWidgetCollectionNewWidget.equals(users)) {
                        oldUserModifiedOfWidgetCollectionNewWidget.getWidgetCollection().remove(widgetCollectionNewWidget);
                        oldUserModifiedOfWidgetCollectionNewWidget = em.merge(oldUserModifiedOfWidgetCollectionNewWidget);
                    }
                }
            }
            for (Widget widgetCollection1OldWidget : widgetCollection1Old) {
                if (!widgetCollection1New.contains(widgetCollection1OldWidget)) {
                    widgetCollection1OldWidget.setUserCreated(null);
                    widgetCollection1OldWidget = em.merge(widgetCollection1OldWidget);
                }
            }
            for (Widget widgetCollection1NewWidget : widgetCollection1New) {
                if (!widgetCollection1Old.contains(widgetCollection1NewWidget)) {
                    Users oldUserCreatedOfWidgetCollection1NewWidget = widgetCollection1NewWidget.getUserCreated();
                    widgetCollection1NewWidget.setUserCreated(users);
                    widgetCollection1NewWidget = em.merge(widgetCollection1NewWidget);
                    if (oldUserCreatedOfWidgetCollection1NewWidget != null && !oldUserCreatedOfWidgetCollection1NewWidget.equals(users)) {
                        oldUserCreatedOfWidgetCollection1NewWidget.getWidgetCollection1().remove(widgetCollection1NewWidget);
                        oldUserCreatedOfWidgetCollection1NewWidget = em.merge(oldUserCreatedOfWidgetCollection1NewWidget);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = users.getUsersId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUsersId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            Levels levelsId = users.getLevelsId();
            if (levelsId != null) {
                levelsId.getUsersCollection().remove(users);
                levelsId = em.merge(levelsId);
            }
            Users userModified = users.getUserModified();
            if (userModified != null) {
                userModified.getUsersCollection().remove(users);
                userModified = em.merge(userModified);
            }
            Users userCreated = users.getUserCreated();
            if (userCreated != null) {
                userCreated.getUsersCollection().remove(users);
                userCreated = em.merge(userCreated);
            }
            Collection<Users> usersCollection = users.getUsersCollection();
            for (Users usersCollectionUsers : usersCollection) {
                usersCollectionUsers.setUserModified(null);
                usersCollectionUsers = em.merge(usersCollectionUsers);
            }
            Collection<Users> usersCollection1 = users.getUsersCollection1();
            for (Users usersCollection1Users : usersCollection1) {
                usersCollection1Users.setUserCreated(null);
                usersCollection1Users = em.merge(usersCollection1Users);
            }
            Collection<Widget> widgetCollection = users.getWidgetCollection();
            for (Widget widgetCollectionWidget : widgetCollection) {
                widgetCollectionWidget.setUserModified(null);
                widgetCollectionWidget = em.merge(widgetCollectionWidget);
            }
            Collection<Widget> widgetCollection1 = users.getWidgetCollection1();
            for (Widget widgetCollection1Widget : widgetCollection1) {
                widgetCollection1Widget.setUserCreated(null);
                widgetCollection1Widget = em.merge(widgetCollection1Widget);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
