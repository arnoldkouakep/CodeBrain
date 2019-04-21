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
import cm.codebrain.main.business.entitie.AnneeAcademic;
import cm.codebrain.main.business.entitie.Etablissement;
import cm.codebrain.main.business.entitie.ParametreAnneeAcademic;
import cm.codebrain.main.business.entitie.Users;
import cm.codebrain.main.business.enumerations.EnumStatus;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author KSA-INET
 */
public class ParametreAnneeAcademicJpaController  extends CodeBrainEntityManager implements Serializable {

    public ParametreAnneeAcademicJpaController(EntityManager em) {
        setEntityManager(em);
    }

    public void create(ParametreAnneeAcademic parametreAnneeAcademic) throws PreexistingEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            AnneeAcademic session = parametreAnneeAcademic.getSession();
            if (session != null) {
                session = (AnneeAcademic) refreshEntity(session.getClass(), session.getAnneeAcademicId());
                parametreAnneeAcademic.setSession(session);
            }
            Etablissement etablissementId = parametreAnneeAcademic.getEtablissementId();
            if (etablissementId != null) {
                etablissementId = (Etablissement) refreshEntity(etablissementId.getClass(), etablissementId.getEtablissementId());
                
                if(parametreAnneeAcademic.getStatut() != null && (parametreAnneeAcademic.getStatut() == null ? EnumStatus.Business_Statut_Actif.toString() == null : parametreAnneeAcademic.getStatut().equals(EnumStatus.Business_Statut_Actif.toString()))){
                    etablissementId.setSession(session);
                }else{
                    etablissementId.setSession(null);
                }
                
                parametreAnneeAcademic.setEtablissementId(etablissementId);
            }
            Users userModified = parametreAnneeAcademic.getUserModified();
            if (userModified != null) {
                userModified = (Users) refreshEntity(userModified.getClass(), userModified.getUsersId());
                parametreAnneeAcademic.setUserModified(userModified);
            }
            Users userCreated = parametreAnneeAcademic.getUserCreated();
            if (userCreated != null) {
                userCreated = (Users) refreshEntity(userCreated.getClass(), userCreated.getUsersId());
                parametreAnneeAcademic.setUserCreated(userCreated);
            }
            persist(parametreAnneeAcademic);
//            if (session != null) {
//                session.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
//                session = em.merge(session);
//            }
            if (etablissementId != null) {
                etablissementId.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
                etablissementId = (Etablissement) merge(etablissementId);
            }
//            if (userModified != null) {
//                userModified.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
//                userModified = em.merge(userModified);
//            }
//            if (userCreated != null) {
//                userCreated.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
//                userCreated = em.merge(userCreated);
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametreAnneeAcademic(parametreAnneeAcademic.getParametreAnneeAcademicId()) != null) {
                throw new PreexistingEntityException("ParametreAnneeAcademic " + parametreAnneeAcademic + " already exists.", ex);
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void edit(ParametreAnneeAcademic parametreAnneeAcademic) throws NonexistentEntityException, Exception {
//        EntityManager em = null;
        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
            ParametreAnneeAcademic persistentParametreAnneeAcademic = (ParametreAnneeAcademic) find(ParametreAnneeAcademic.class, parametreAnneeAcademic.getParametreAnneeAcademicId());
//            AnneeAcademic sessionOld = persistentParametreAnneeAcademic.getSession();
            AnneeAcademic sessionNew = parametreAnneeAcademic.getSession();
//            Etablissement etablissementIdOld = persistentParametreAnneeAcademic.getEtablissementId();
            Etablissement etablissementIdNew = parametreAnneeAcademic.getEtablissementId();
//            Users userModifiedOld = persistentParametreAnneeAcademic.getUserModified();
            Users userModifiedNew = parametreAnneeAcademic.getUserModified();
            Users userCreatedOld = persistentParametreAnneeAcademic.getUserCreated();
            Users userCreatedNew = parametreAnneeAcademic.getUserCreated();
            if (sessionNew != null) {
                sessionNew = (AnneeAcademic) refreshEntity(sessionNew.getClass(), sessionNew.getAnneeAcademicId());
                parametreAnneeAcademic.setSession(sessionNew);
            }
            if (etablissementIdNew != null) {
                etablissementIdNew = (Etablissement) refreshEntity(etablissementIdNew.getClass(), etablissementIdNew.getEtablissementId());
                
                if(parametreAnneeAcademic.getStatut() != null && (parametreAnneeAcademic.getStatut() == null ? EnumStatus.Business_Statut_Actif.toString() == null : parametreAnneeAcademic.getStatut().equals(EnumStatus.Business_Statut_Actif.toString()))){
                    etablissementIdNew.setSession(sessionNew);
                }else{
                    etablissementIdNew.setSession(null);
                }
                
                parametreAnneeAcademic.setEtablissementId(etablissementIdNew);
            }
            if (userModifiedNew != null) {
                userModifiedNew = (Users) refreshEntity(userModifiedNew.getClass(), userModifiedNew.getUsersId());
                parametreAnneeAcademic.setUserModified(userModifiedNew);
            }
            if (userCreatedNew != null) {
                userCreatedNew = (Users) refreshEntity(userCreatedNew.getClass(), userCreatedOld.getUsersId());
                parametreAnneeAcademic.setUserCreated(userCreatedNew);
            }else{
                userCreatedOld = (Users) refreshEntity(userCreatedOld.getClass(), userCreatedOld.getUsersId());
                parametreAnneeAcademic.setUserCreated(userCreatedOld);
            }
            parametreAnneeAcademic = (ParametreAnneeAcademic) merge(parametreAnneeAcademic);
//            if (sessionOld != null && !sessionOld.equals(sessionNew)) {
//                sessionOld.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                sessionOld = em.merge(sessionOld);
//            }
//            if (sessionNew != null && !sessionNew.equals(sessionOld)) {
//                sessionNew.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
//                sessionNew = em.merge(sessionNew);
//            }
//            if (etablissementIdOld != null && !etablissementIdOld.equals(etablissementIdNew)) {
//                etablissementIdOld.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                etablissementIdOld = em.merge(etablissementIdOld);
//            }
            if (etablissementIdNew != null) {
                etablissementIdNew.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
                etablissementIdNew = (Etablissement) merge(etablissementIdNew);
            }
//            if (userModifiedOld != null && !userModifiedOld.equals(userModifiedNew)) {
//                userModifiedOld.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                userModifiedOld = em.merge(userModifiedOld);
//            }
//            if (userModifiedNew != null && !userModifiedNew.equals(userModifiedOld)) {
//                userModifiedNew.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
//                userModifiedNew = em.merge(userModifiedNew);
//            }
//            if (userCreatedOld != null && !userCreatedOld.equals(userCreatedNew)) {
//                userCreatedOld.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                userCreatedOld = em.merge(userCreatedOld);
//            }
//            if (userCreatedNew != null && !userCreatedNew.equals(userCreatedOld)) {
//                userCreatedNew.getParametreAnneeAcademicSet().add(parametreAnneeAcademic);
//                userCreatedNew = em.merge(userCreatedNew);
//            }
//            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = parametreAnneeAcademic.getParametreAnneeAcademicId();
                if (findParametreAnneeAcademic(id) == null) {
                    throw new NonexistentEntityException("The parametreAnneeAcademic with id " + id + " no longer exists.");
                }
            }
            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            ParametreAnneeAcademic parametreAnneeAcademic;
//            try {
//                parametreAnneeAcademic = em.getReference(ParametreAnneeAcademic.class, id);
//                parametreAnneeAcademic.getParametreAnneeAcademicId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The parametreAnneeAcademic with id " + id + " no longer exists.", enfe);
//            }
//            AnneeAcademic session = parametreAnneeAcademic.getSession();
//            if (session != null) {
//                session.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                session = em.merge(session);
//            }
//            Etablissement etablissementId = parametreAnneeAcademic.getEtablissementId();
//            if (etablissementId != null) {
//                etablissementId.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                etablissementId = em.merge(etablissementId);
//            }
//            Users userModified = parametreAnneeAcademic.getUserModified();
//            if (userModified != null) {
//                userModified.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                userModified = em.merge(userModified);
//            }
//            Users userCreated = parametreAnneeAcademic.getUserCreated();
//            if (userCreated != null) {
//                userCreated.getParametreAnneeAcademicSet().remove(parametreAnneeAcademic);
//                userCreated = em.merge(userCreated);
//            }
            remove(ParametreAnneeAcademic.class, id);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<ParametreAnneeAcademic> findParametreAnneeAcademicEntities() {
        return findParametreAnneeAcademicEntities(true, -1, -1);
    }

    public List<ParametreAnneeAcademic> findParametreAnneeAcademicEntities(int maxResults, int firstResult) {
        return findParametreAnneeAcademicEntities(false, maxResults, firstResult);
    }

    private List<ParametreAnneeAcademic> findParametreAnneeAcademicEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametreAnneeAcademic.class));
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

    public ParametreAnneeAcademic findParametreAnneeAcademic(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametreAnneeAcademic.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametreAnneeAcademicCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametreAnneeAcademic> rt = cq.from(ParametreAnneeAcademic.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
