/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Equipo;
import entrega3.Entidades.Partido;
import entrega3.Entidades.Partidoxequipo;
import entrega3.Entidades.PartidoxequipoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class PartidoxequipoJpaController implements Serializable {

    public PartidoxequipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partidoxequipo partidoxequipo) throws PreexistingEntityException, Exception {
        if (partidoxequipo.getPartidoxequipoPK() == null) {
            partidoxequipo.setPartidoxequipoPK(new PartidoxequipoPK());
        }
        partidoxequipo.getPartidoxequipoPK().setIdpartido(partidoxequipo.getPartido().getIdpartido());
        partidoxequipo.getPartidoxequipoPK().setIdequipo(partidoxequipo.getEquipo().getIdequipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo equipo = partidoxequipo.getEquipo();
            if (equipo != null) {
                equipo = em.getReference(equipo.getClass(), equipo.getIdequipo());
                partidoxequipo.setEquipo(equipo);
            }
            Partido partido = partidoxequipo.getPartido();
            if (partido != null) {
                partido = em.getReference(partido.getClass(), partido.getIdpartido());
                partidoxequipo.setPartido(partido);
            }
            em.persist(partidoxequipo);
            if (equipo != null) {
                equipo.getPartidoxequipoCollection().add(partidoxequipo);
                equipo = em.merge(equipo);
            }
            if (partido != null) {
                partido.getPartidoxequipoCollection().add(partidoxequipo);
                partido = em.merge(partido);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartidoxequipo(partidoxequipo.getPartidoxequipoPK()) != null) {
                throw new PreexistingEntityException("Partidoxequipo " + partidoxequipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partidoxequipo partidoxequipo) throws NonexistentEntityException, Exception {
        partidoxequipo.getPartidoxequipoPK().setIdpartido(partidoxequipo.getPartido().getIdpartido());
        partidoxequipo.getPartidoxequipoPK().setIdequipo(partidoxequipo.getEquipo().getIdequipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partidoxequipo persistentPartidoxequipo = em.find(Partidoxequipo.class, partidoxequipo.getPartidoxequipoPK());
            Equipo equipoOld = persistentPartidoxequipo.getEquipo();
            Equipo equipoNew = partidoxequipo.getEquipo();
            Partido partidoOld = persistentPartidoxequipo.getPartido();
            Partido partidoNew = partidoxequipo.getPartido();
            if (equipoNew != null) {
                equipoNew = em.getReference(equipoNew.getClass(), equipoNew.getIdequipo());
                partidoxequipo.setEquipo(equipoNew);
            }
            if (partidoNew != null) {
                partidoNew = em.getReference(partidoNew.getClass(), partidoNew.getIdpartido());
                partidoxequipo.setPartido(partidoNew);
            }
            partidoxequipo = em.merge(partidoxequipo);
            if (equipoOld != null && !equipoOld.equals(equipoNew)) {
                equipoOld.getPartidoxequipoCollection().remove(partidoxequipo);
                equipoOld = em.merge(equipoOld);
            }
            if (equipoNew != null && !equipoNew.equals(equipoOld)) {
                equipoNew.getPartidoxequipoCollection().add(partidoxequipo);
                equipoNew = em.merge(equipoNew);
            }
            if (partidoOld != null && !partidoOld.equals(partidoNew)) {
                partidoOld.getPartidoxequipoCollection().remove(partidoxequipo);
                partidoOld = em.merge(partidoOld);
            }
            if (partidoNew != null && !partidoNew.equals(partidoOld)) {
                partidoNew.getPartidoxequipoCollection().add(partidoxequipo);
                partidoNew = em.merge(partidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PartidoxequipoPK id = partidoxequipo.getPartidoxequipoPK();
                if (findPartidoxequipo(id) == null) {
                    throw new NonexistentEntityException("The partidoxequipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PartidoxequipoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partidoxequipo partidoxequipo;
            try {
                partidoxequipo = em.getReference(Partidoxequipo.class, id);
                partidoxequipo.getPartidoxequipoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partidoxequipo with id " + id + " no longer exists.", enfe);
            }
            Equipo equipo = partidoxequipo.getEquipo();
            if (equipo != null) {
                equipo.getPartidoxequipoCollection().remove(partidoxequipo);
                equipo = em.merge(equipo);
            }
            Partido partido = partidoxequipo.getPartido();
            if (partido != null) {
                partido.getPartidoxequipoCollection().remove(partidoxequipo);
                partido = em.merge(partido);
            }
            em.remove(partidoxequipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partidoxequipo> findPartidoxequipoEntities() {
        return findPartidoxequipoEntities(true, -1, -1);
    }

    public List<Partidoxequipo> findPartidoxequipoEntities(int maxResults, int firstResult) {
        return findPartidoxequipoEntities(false, maxResults, firstResult);
    }

    private List<Partidoxequipo> findPartidoxequipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partidoxequipo.class));
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

    public Partidoxequipo findPartidoxequipo(PartidoxequipoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partidoxequipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidoxequipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partidoxequipo> rt = cq.from(Partidoxequipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
