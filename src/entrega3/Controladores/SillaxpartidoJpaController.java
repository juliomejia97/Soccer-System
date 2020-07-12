/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Sillaxpartido;
import entrega3.Entidades.SillaxpartidoPK;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SillaxpartidoJpaController implements Serializable {

    public SillaxpartidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sillaxpartido sillaxpartido) throws PreexistingEntityException, Exception {
        if (sillaxpartido.getSillaxpartidoPK() == null) {
            sillaxpartido.setSillaxpartidoPK(new SillaxpartidoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sillaxpartido);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSillaxpartido(sillaxpartido.getSillaxpartidoPK()) != null) {
                throw new PreexistingEntityException("Sillaxpartido " + sillaxpartido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sillaxpartido sillaxpartido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            sillaxpartido = em.merge(sillaxpartido);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SillaxpartidoPK id = sillaxpartido.getSillaxpartidoPK();
                if (findSillaxpartido(id) == null) {
                    throw new NonexistentEntityException("The sillaxpartido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SillaxpartidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sillaxpartido sillaxpartido;
            try {
                sillaxpartido = em.getReference(Sillaxpartido.class, id);
                sillaxpartido.getSillaxpartidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sillaxpartido with id " + id + " no longer exists.", enfe);
            }
            em.remove(sillaxpartido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sillaxpartido> findSillaxpartidoEntities() {
        return findSillaxpartidoEntities(true, -1, -1);
    }

    public List<Sillaxpartido> findSillaxpartidoEntities(int maxResults, int firstResult) {
        return findSillaxpartidoEntities(false, maxResults, firstResult);
    }

    private List<Sillaxpartido> findSillaxpartidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sillaxpartido.class));
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

    public Sillaxpartido findSillaxpartido(SillaxpartidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sillaxpartido.class, id);
        } finally {
            em.close();
        }
    }

    public int getSillaxpartidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sillaxpartido> rt = cq.from(Sillaxpartido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Object[]> mostrarSillasXPartido(Integer idPartido, String nombreEstadio) {
        Sillaxpartido sillaPart = new Sillaxpartido();
        List<Object[]> resultados = sillaPart.mostrarSillasXPartido(idPartido, nombreEstadio);
         return resultados;
    }
     public List<Integer> mostrarSillasXPartidoC(Integer idPartido, String nombreEstadio, Integer idCategoria) {
        Sillaxpartido sillaPart = new Sillaxpartido();
        List<Integer> resultados = sillaPart.mostrarSillasXPartidoC(idPartido, nombreEstadio, idCategoria);
         return resultados;
    }
     
     
    public SillaxpartidoPK construirSPK(int idEstadio, int idPartido, int idSilla){
          SillaxpartidoPK devolver = new SillaxpartidoPK(idPartido, idEstadio, idSilla);
          return devolver;
    }
    
    public void actualizarSilla(int idEstadio, int idPartido, int idSilla){
        try {
            SillaxpartidoPK llave = construirSPK(idEstadio, idPartido, idSilla);
            Sillaxpartido sillita = findSillaxpartido(llave);
            Integer estado = 1;
            Short cambiar = Short.parseShort("1");
            sillita.setOcupado(cambiar);
            edit(sillita);
        } catch (Exception ex) {
            Logger.getLogger(SillaxpartidoJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
