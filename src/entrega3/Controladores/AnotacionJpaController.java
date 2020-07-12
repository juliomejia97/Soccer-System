/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import Pesistencia.Conexion;
import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Anotacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Partido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class AnotacionJpaController implements Serializable {

    public AnotacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anotacion anotacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partido idpartido = anotacion.getIdpartido();
            if (idpartido != null) {
                idpartido = em.getReference(idpartido.getClass(), idpartido.getIdpartido());
                anotacion.setIdpartido(idpartido);
            }
            em.persist(anotacion);
            if (idpartido != null) {
                idpartido.getAnotacionCollection().add(anotacion);
                idpartido = em.merge(idpartido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnotacion(anotacion.getIdanotacion()) != null) {
                throw new PreexistingEntityException("Anotacion " + anotacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anotacion anotacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anotacion persistentAnotacion = em.find(Anotacion.class, anotacion.getIdanotacion());
            Partido idpartidoOld = persistentAnotacion.getIdpartido();
            Partido idpartidoNew = anotacion.getIdpartido();
            if (idpartidoNew != null) {
                idpartidoNew = em.getReference(idpartidoNew.getClass(), idpartidoNew.getIdpartido());
                anotacion.setIdpartido(idpartidoNew);
            }
            anotacion = em.merge(anotacion);
            if (idpartidoOld != null && !idpartidoOld.equals(idpartidoNew)) {
                idpartidoOld.getAnotacionCollection().remove(anotacion);
                idpartidoOld = em.merge(idpartidoOld);
            }
            if (idpartidoNew != null && !idpartidoNew.equals(idpartidoOld)) {
                idpartidoNew.getAnotacionCollection().add(anotacion);
                idpartidoNew = em.merge(idpartidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = anotacion.getIdanotacion();
                if (findAnotacion(id) == null) {
                    throw new NonexistentEntityException("The anotacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anotacion anotacion;
            try {
                anotacion = em.getReference(Anotacion.class, id);
                anotacion.getIdanotacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anotacion with id " + id + " no longer exists.", enfe);
            }
            Partido idpartido = anotacion.getIdpartido();
            if (idpartido != null) {
                idpartido.getAnotacionCollection().remove(anotacion);
                idpartido = em.merge(idpartido);
            }
            em.remove(anotacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anotacion> findAnotacionEntities() {
        return findAnotacionEntities(true, -1, -1);
    }

    public List<Anotacion> findAnotacionEntities(int maxResults, int firstResult) {
        return findAnotacionEntities(false, maxResults, firstResult);
    }

    private List<Anotacion> findAnotacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anotacion.class));
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

    public Anotacion findAnotacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anotacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnotacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anotacion> rt = cq.from(Anotacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void agregarAnotacion(Integer idAnotacion, Integer idJugador, Short min, Short tiempo, String tipoGol, String var, Integer idPartido, Integer idJugador0) throws Exception {
        Conexion con = new Conexion();
        Anotacion an= new Anotacion(idAnotacion);
        JugadorJpaController controladorJugador= new JugadorJpaController(con.getEmf());
        PartidoJpaController controladorPartido= new PartidoJpaController(con.getEmf());
        an.setIdjugador(controladorJugador.findJugador(idJugador));
        an.setIdpartido(controladorPartido.findPartido(idPartido));
        an.setMinuto(min);
        an.setTiempo(tiempo);
        an.setTipogol(tipoGol);
        an.setUsovar(var);
        create(an);
        
    }
    
}
