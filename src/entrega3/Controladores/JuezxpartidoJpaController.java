/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Juezxpartido;
import entrega3.Entidades.JuezxpartidoPK;
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
 * @author DELL
 */
public class JuezxpartidoJpaController implements Serializable {

    public JuezxpartidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juezxpartido juezxpartido) throws PreexistingEntityException, Exception {
        if (juezxpartido.getJuezxpartidoPK() == null) {
            juezxpartido.setJuezxpartidoPK(new JuezxpartidoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(juezxpartido);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJuezxpartido(juezxpartido.getJuezxpartidoPK()) != null) {
                throw new PreexistingEntityException("Juezxpartido " + juezxpartido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juezxpartido juezxpartido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            juezxpartido = em.merge(juezxpartido);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JuezxpartidoPK id = juezxpartido.getJuezxpartidoPK();
                if (findJuezxpartido(id) == null) {
                    throw new NonexistentEntityException("The juezxpartido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JuezxpartidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juezxpartido juezxpartido;
            try {
                juezxpartido = em.getReference(Juezxpartido.class, id);
                juezxpartido.getJuezxpartidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juezxpartido with id " + id + " no longer exists.", enfe);
            }
            em.remove(juezxpartido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juezxpartido> findJuezxpartidoEntities() {
        return findJuezxpartidoEntities(true, -1, -1);
    }

    public List<Juezxpartido> findJuezxpartidoEntities(int maxResults, int firstResult) {
        return findJuezxpartidoEntities(false, maxResults, firstResult);
    }

    private List<Juezxpartido> findJuezxpartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juezxpartido.class));
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

    public Juezxpartido findJuezxpartido(JuezxpartidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juezxpartido.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuezxpartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juezxpartido> rt = cq.from(Juezxpartido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
