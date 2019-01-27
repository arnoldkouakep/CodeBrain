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
import cm.codebrain.main.business.entitie.Widget;
import cm.codebrain.main.business.manager.exceptions.IllegalOrphanException;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author KSA-INET
 */
public class WidgetJpaController implements Serializable {

    public WidgetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Widget widget) throws PreexistingEntityException, Exception {
        if (widget.getWidgetCollection() == null) {
            widget.setWidgetCollection(new ArrayList<Widget>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Levels levelsId = widget.getLevelsId();
            if (levelsId != null) {
                levelsId = em.getReference(levelsId.getClass(), levelsId.getLevelsId());
                widget.setLevelsId(levelsId);
            }
            Users userModified = widget.getUserModified();
            if (userModified != null) {
                userModified = em.getReference(userModified.getClass(), userModified.getUsersId());
                widget.setUserModified(userModified);
            }
            Users userCreated = widget.getUserCreated();
            if (userCreated != null) {
                userCreated = em.getReference(userCreated.getClass(), userCreated.getUsersId());
                widget.setUserCreated(userCreated);
            }
            Widget parent = widget.getParent();
            if (parent != null) {
                parent = em.getReference(parent.getClass(), parent.getWidgetId());
                widget.setParent(parent);
            }
            Collection<Widget> attachedWidgetCollection = new ArrayList<Widget>();
            for (Widget widgetCollectionWidgetToAttach : widget.getWidgetCollection()) {
                widgetCollectionWidgetToAttach = em.getReference(widgetCollectionWidgetToAttach.getClass(), widgetCollectionWidgetToAttach.getWidgetId());
                attachedWidgetCollection.add(widgetCollectionWidgetToAttach);
            }
            widget.setWidgetCollection(attachedWidgetCollection);
            em.persist(widget);
            if (levelsId != null) {
                levelsId.getWidgetCollection().add(widget);
                levelsId = em.merge(levelsId);
            }
            if (userModified != null) {
                userModified.getWidgetCollection().add(widget);
                userModified = em.merge(userModified);
            }
            if (userCreated != null) {
                userCreated.getWidgetCollection().add(widget);
                userCreated = em.merge(userCreated);
            }
            if (parent != null) {
                parent.getWidgetCollection().add(widget);
                parent = em.merge(parent);
            }
            for (Widget widgetCollectionWidget : widget.getWidgetCollection()) {
                Widget oldParentOfWidgetCollectionWidget = widgetCollectionWidget.getParent();
                widgetCollectionWidget.setParent(widget);
                widgetCollectionWidget = em.merge(widgetCollectionWidget);
                if (oldParentOfWidgetCollectionWidget != null) {
                    oldParentOfWidgetCollectionWidget.getWidgetCollection().remove(widgetCollectionWidget);
                    oldParentOfWidgetCollectionWidget = em.merge(oldParentOfWidgetCollectionWidget);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWidget(widget.getWidgetId()) != null) {
                throw new PreexistingEntityException("Widget " + widget + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Widget widget) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Widget persistentWidget = em.find(Widget.class, widget.getWidgetId());
            Levels levelsIdOld = persistentWidget.getLevelsId();
            Levels levelsIdNew = widget.getLevelsId();
            Users userModifiedOld = persistentWidget.getUserModified();
            Users userModifiedNew = widget.getUserModified();
            Users userCreatedOld = persistentWidget.getUserCreated();
            Users userCreatedNew = widget.getUserCreated();
            Widget parentOld = persistentWidget.getParent();
            Widget parentNew = widget.getParent();
            Collection<Widget> widgetCollectionOld = persistentWidget.getWidgetCollection();
            Collection<Widget> widgetCollectionNew = widget.getWidgetCollection();
            List<String> illegalOrphanMessages = null;
            for (Widget widgetCollectionOldWidget : widgetCollectionOld) {
                if (!widgetCollectionNew.contains(widgetCollectionOldWidget)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Widget " + widgetCollectionOldWidget + " since its parent field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (levelsIdNew != null) {
                levelsIdNew = em.getReference(levelsIdNew.getClass(), levelsIdNew.getLevelsId());
                widget.setLevelsId(levelsIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = em.getReference(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                widget.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = em.getReference(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                widget.setUserCreated(userCreatedNew);
            }
            if (parentNew != null) {
                parentNew = em.getReference(parentNew.getClass(), parentNew.getWidgetId());
                widget.setParent(parentNew);
            }
            Collection<Widget> attachedWidgetCollectionNew = new ArrayList<Widget>();
            for (Widget widgetCollectionNewWidgetToAttach : widgetCollectionNew) {
                widgetCollectionNewWidgetToAttach = em.getReference(widgetCollectionNewWidgetToAttach.getClass(), widgetCollectionNewWidgetToAttach.getWidgetId());
                attachedWidgetCollectionNew.add(widgetCollectionNewWidgetToAttach);
            }
            widgetCollectionNew = attachedWidgetCollectionNew;
            widget.setWidgetCollection(widgetCollectionNew);
            widget = em.merge(widget);
            if (levelsIdOld != null && !levelsIdOld.equals(levelsIdNew)) {
                levelsIdOld.getWidgetCollection().remove(widget);
                levelsIdOld = em.merge(levelsIdOld);
            }
            if (levelsIdNew != null && !levelsIdNew.equals(levelsIdOld)) {
                levelsIdNew.getWidgetCollection().add(widget);
                levelsIdNew = em.merge(levelsIdNew);
            }
            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
                userModifiedOld.getWidgetCollection().remove(widget);
                userModifiedOld = em.merge(userModifiedOld);
            }
            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
                userModifiedNew.getWidgetCollection().add(widget);
                userModifiedNew = em.merge(userModifiedNew);
            }
            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
                userCreatedOld.getWidgetCollection().remove(widget);
                userCreatedOld = em.merge(userCreatedOld);
            }
            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
                userCreatedNew.getWidgetCollection().add(widget);
                userCreatedNew = em.merge(userCreatedNew);
            }
            if (parentOld != null && !parentOld.equals(parentNew)) {
                parentOld.getWidgetCollection().remove(widget);
                parentOld = em.merge(parentOld);
            }
            if (parentNew != null && !parentNew.equals(parentOld)) {
                parentNew.getWidgetCollection().add(widget);
                parentNew = em.merge(parentNew);
            }
            for (Widget widgetCollectionNewWidget : widgetCollectionNew) {
                if (!widgetCollectionOld.contains(widgetCollectionNewWidget)) {
                    Widget oldParentOfWidgetCollectionNewWidget = widgetCollectionNewWidget.getParent();
                    widgetCollectionNewWidget.setParent(widget);
                    widgetCollectionNewWidget = em.merge(widgetCollectionNewWidget);
                    if (oldParentOfWidgetCollectionNewWidget != null && !oldParentOfWidgetCollectionNewWidget.equals(widget)) {
                        oldParentOfWidgetCollectionNewWidget.getWidgetCollection().remove(widgetCollectionNewWidget);
                        oldParentOfWidgetCollectionNewWidget = em.merge(oldParentOfWidgetCollectionNewWidget);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = widget.getWidgetId();
                if (findWidget(id) == null) {
                    throw new NonexistentEntityException("The widget with id " + id + " no longer exists.");
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
            Widget widget;
            try {
                widget = em.getReference(Widget.class, id);
                widget.getWidgetId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The widget with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Widget> widgetCollectionOrphanCheck = widget.getWidgetCollection();
            for (Widget widgetCollectionOrphanCheckWidget : widgetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Widget (" + widget + ") cannot be destroyed since the Widget " + widgetCollectionOrphanCheckWidget + " in its widgetCollection field has a non-nullable parent field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Levels levelsId = widget.getLevelsId();
            if (levelsId != null) {
                levelsId.getWidgetCollection().remove(widget);
                levelsId = em.merge(levelsId);
            }
            Users userModified = widget.getUserModified();
            if (userModified != null) {
                userModified.getWidgetCollection().remove(widget);
                userModified = em.merge(userModified);
            }
            Users userCreated = widget.getUserCreated();
            if (userCreated != null) {
                userCreated.getWidgetCollection().remove(widget);
                userCreated = em.merge(userCreated);
            }
            Widget parent = widget.getParent();
            if (parent != null) {
                parent.getWidgetCollection().remove(widget);
                parent = em.merge(parent);
            }
            em.remove(widget);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Widget> findWidgetEntities() {
        return findWidgetEntities(true, -1, -1);
    }

    public List<Widget> findWidgetEntities(int maxResults, int firstResult) {
        return findWidgetEntities(false, maxResults, firstResult);
    }

    private List<Widget> findWidgetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Widget.class));
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

    public Widget findWidget(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Widget.class, id);
        } finally {
            em.close();
        }
    }

    public int getWidgetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Widget> rt = cq.from(Widget.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
