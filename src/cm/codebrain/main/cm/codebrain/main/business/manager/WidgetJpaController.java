/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.controller.CodeBrainEntityManager;
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
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author KSA-INET
 */
public class WidgetJpaController extends CodeBrainEntityManager implements Serializable {

    public WidgetJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(Widget widget) throws PreexistingEntityException, Exception {
        if (widget.getWidgetSet() == null) {
            widget.setWidgetSet(new HashSet<Widget>());
        }
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Levels levelsId = widget.getLevelsId();
            if (levelsId != null) {
                levelsId = (Levels) refreshEntity(levelsId.getClass(), levelsId.getLevelsId());
                widget.setLevelsId(levelsId);
            }
            Users userModified = widget.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                widget.setUserModified(userModified);
            }
            Users userCreated = widget.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                widget.setUserCreated(userCreated);
            }
            Widget parent = widget.getParent();
            if (parent != null) {
                parent = (Widget) refreshEntity(parent.getClass(), parent.getWidgetId());
                widget.setParent(parent);
            }
            Set<Widget> attachedWidgetSet = new HashSet<Widget>();
            for (Widget widgetSetWidgetToAttach : widget.getWidgetSet()) {
                widgetSetWidgetToAttach = (Widget) refreshEntity(widgetSetWidgetToAttach.getClass(), widgetSetWidgetToAttach.getWidgetId());
                attachedWidgetSet.add(widgetSetWidgetToAttach);
            }
            widget.setWidgetSet(attachedWidgetSet);
            persist(widget);
//            if (levelsId != null) {
//                levelsId.getWidgetSet().add(widget);
//                levelsId = em.merge(levelsId);
//            }
//            if (userModified != null) {
//                userModified.getWidgetSet().add(widget);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getWidgetSet().add(widget);
//                userCreated = em.merge(userCreated);
//            }
//            if (parent != null) {
//                parent.getWidgetSet().add(widget);
//                parent = em.merge(parent);
//            }
//            for (Widget widgetSetWidget : widget.getWidgetSet()) {
//                Widget oldParentOfWidgetSetWidget = widgetSetWidget.getParent();
//                widgetSetWidget.setParent(widget);
//                widgetSetWidget = em.merge(widgetSetWidget);
//                if (oldParentOfWidgetSetWidget != null) {
//                    oldParentOfWidgetSetWidget.getWidgetSet().remove(widgetSetWidget);
//                    oldParentOfWidgetSetWidget = em.merge(oldParentOfWidgetSetWidget);
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWidget(widget.getWidgetId()) != null) {
                throw new PreexistingEntityException("Widget " + widget + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(Widget widget) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            Widget persistentWidget = (Widget) find(Widget.class, widget.getWidgetId());
            Levels levelsIdOld = persistentWidget.getLevelsId();
            Levels levelsIdNew = widget.getLevelsId();
            Users userModifiedOld = persistentWidget.getUserModified();
            Users userModifiedNew = widget.getUserModified();
            Users userCreatedOld = persistentWidget.getUserCreated();
            Users userCreatedNew = widget.getUserCreated();
            Widget parentOld = persistentWidget.getParent();
            Widget parentNew = widget.getParent();
            Set<Widget> widgetSetOld = persistentWidget.getWidgetSet();
            Set<Widget> widgetSetNew = widget.getWidgetSet();
            List<String> illegalOrphanMessages = null;
//            for (Widget widgetSetOldWidget : widgetSetOld) {
//                if (!widgetSetNew.contains(widgetSetOldWidget)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Widget " + widgetSetOldWidget + " since its parent field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (levelsIdNew != null) {
                levelsIdNew = (Levels) refreshEntity(levelsIdNew.getClass(), levelsIdNew.getLevelsId());
                widget.setLevelsId(levelsIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                widget.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedNew.getUsersId());
                widget.setUserCreated(userCreatedNew);
            }
            if (parentNew != null) {
                parentNew = (Widget) refreshEntity(parentNew.getClass(), parentNew.getWidgetId());
                widget.setParent(parentNew);
            }
            Set<Widget> attachedWidgetSetNew = new HashSet<Widget>();
            for (Widget widgetSetNewWidgetToAttach : widgetSetNew) {
                widgetSetNewWidgetToAttach = (Widget) refreshEntity(widgetSetNewWidgetToAttach.getClass(), widgetSetNewWidgetToAttach.getWidgetId());
                attachedWidgetSetNew.add(widgetSetNewWidgetToAttach);
            }
            widgetSetNew = attachedWidgetSetNew;
            widget.setWidgetSet(widgetSetNew);
            widget = (Widget) merge(widget);
//            if (levelsIdOld != null && !levelsIdOld.equals(levelsIdNew)) {
//                levelsIdOld.getWidgetSet().remove(widget);
//                levelsIdOld = em.merge(levelsIdOld);
//            }
//            if (levelsIdNew != null && !levelsIdNew.equals(levelsIdOld)) {
//                levelsIdNew.getWidgetSet().add(widget);
//                levelsIdNew = em.merge(levelsIdNew);
//            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getWidgetSet().remove(widget);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getWidgetSet().add(widget);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getWidgetSet().remove(widget);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getWidgetSet().add(widget);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            if (parentOld != null && !parentOld.equals(parentNew)) {
//                parentOld.getWidgetSet().remove(widget);
//                parentOld = em.merge(parentOld);
//            }
//            if (parentNew != null && !parentNew.equals(parentOld)) {
//                parentNew.getWidgetSet().add(widget);
//                parentNew = em.merge(parentNew);
//            }
//            for (Widget widgetSetNewWidget : widgetSetNew) {
//                if (!widgetSetOld.contains(widgetSetNewWidget)) {
//                    Widget oldParentOfWidgetSetNewWidget = widgetSetNewWidget.getParent();
//                    widgetSetNewWidget.setParent(widget);
//                    widgetSetNewWidget = em.merge(widgetSetNewWidget);
//                    if (oldParentOfWidgetSetNewWidget != null && !oldParentOfWidgetSetNewWidget.equals(widget)) {
//                        oldParentOfWidgetSetNewWidget.getWidgetSet().remove(widgetSetNewWidget);
//                        oldParentOfWidgetSetNewWidget = em.merge(oldParentOfWidgetSetNewWidget);
//                    }
//                }
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = widget.getWidgetId();
                if (findWidget(id) == null) {
                    throw new NonexistentEntityException("The widget with id " + id + " no longer exists.");
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
//            Widget widget;
//            try {
//                widget = () refreshEntity(Widget.class, id);
//                widget.getWidgetId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The widget with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Set<Widget> widgetSetOrphanCheck = widget.getWidgetSet();
//            for (Widget widgetSetOrphanCheckWidget : widgetSetOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Widget (" + widget + ") cannot be destroyed since the Widget " + widgetSetOrphanCheckWidget + " in its widgetSet field has a non-nullable parent field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Levels levelsId = widget.getLevelsId();
//            if (levelsId != null) {
//                levelsId.getWidgetSet().remove(widget);
//                levelsId = em.merge(levelsId);
//            }
//            Users userModified = widget.getUserModified();
//            if (userModified != null) {
//                userModified.getWidgetSet().remove(widget);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = widget.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getWidgetSet().remove(widget);
//                userCreated = em.merge(userCreated);
//            }
//            Widget parent = widget.getParent();
//            if (parent != null) {
//                parent.getWidgetSet().remove(widget);
//                parent = em.merge(parent);
//            }
            remove(Widget.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
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
