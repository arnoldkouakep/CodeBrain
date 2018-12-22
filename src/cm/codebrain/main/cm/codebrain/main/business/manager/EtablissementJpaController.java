/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.main.business.manager;

import cm.codebrain.main.business.entitie.Etablissement;
import cm.codebrain.main.business.manager.exceptions.NonexistentEntityException;
import cm.codebrain.main.business.manager.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author KSA-INET
 */
public class EtablissementJpaController implements Serializable {

    public EtablissementJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Etablissement etablissement) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(etablissement);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEtablissement(etablissement.getEtablissementId()) != null) {
                throw new PreexistingEntityException("Etablissement " + etablissement + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Etablissement etablissement) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            etablissement = em.merge(etablissement);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = etablissement.getEtablissementId();
                if (findEtablissement(id) == null) {
                    throw new NonexistentEntityException("The etablissement with id " + id + " no longer exists.");
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
            Etablissement etablissement;
            try {
                etablissement = em.getReference(Etablissement.class, id);
                etablissement.getEtablissementId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The etablissement with id " + id + " no longer exists.", enfe);
            }
            em.remove(etablissement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Etablissement> findEtablissementEntities() {
        return findEtablissementEntities(true, -1, -1);
    }

    public List<Etablissement> findEtablissementEntities(int maxResults, int firstResult) {
        return findEtablissementEntities(false, maxResults, firstResult);
    }

    private List<Etablissement> findEtablissementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Etablissement.class));
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

    public Etablissement findEtablissement(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Etablissement.class, id);
        } finally {
            em.close();
        }
    }

    public int getEtablissementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Etablissement> rt = cq.from(Etablissement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
