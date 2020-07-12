/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pesistencia;

import Pesistencia.exceptions.IllegalOrphanException;
import Pesistencia.exceptions.NonexistentEntityException;
import Pesistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Jugador;
import java.util.ArrayList;
import java.util.Collection;
import entrega3.Entidades.Partidoxequipo;
import entrega3.Entidades.DirectorTecnico;
import entrega3.Entidades.Equipo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class EquipoJpaController implements Serializable {

    public EquipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipo equipo) throws PreexistingEntityException, Exception {
        if (equipo.getJugadorCollection() == null) {
            equipo.setJugadorCollection(new ArrayList<Jugador>());
        }
        if (equipo.getPartidoxequipoCollection() == null) {
            equipo.setPartidoxequipoCollection(new ArrayList<Partidoxequipo>());
        }
        if (equipo.getDirectorTecnicoCollection() == null) {
            equipo.setDirectorTecnicoCollection(new ArrayList<DirectorTecnico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Jugador> attachedJugadorCollection = new ArrayList<Jugador>();
            for (Jugador jugadorCollectionJugadorToAttach : equipo.getJugadorCollection()) {
                jugadorCollectionJugadorToAttach = em.getReference(jugadorCollectionJugadorToAttach.getClass(), jugadorCollectionJugadorToAttach.getIdjugador());
                attachedJugadorCollection.add(jugadorCollectionJugadorToAttach);
            }
            equipo.setJugadorCollection(attachedJugadorCollection);
            Collection<Partidoxequipo> attachedPartidoxequipoCollection = new ArrayList<Partidoxequipo>();
            for (Partidoxequipo partidoxequipoCollectionPartidoxequipoToAttach : equipo.getPartidoxequipoCollection()) {
                partidoxequipoCollectionPartidoxequipoToAttach = em.getReference(partidoxequipoCollectionPartidoxequipoToAttach.getClass(), partidoxequipoCollectionPartidoxequipoToAttach.getPartidoxequipoPK());
                attachedPartidoxequipoCollection.add(partidoxequipoCollectionPartidoxequipoToAttach);
            }
            equipo.setPartidoxequipoCollection(attachedPartidoxequipoCollection);
            Collection<DirectorTecnico> attachedDirectorTecnicoCollection = new ArrayList<DirectorTecnico>();
            for (DirectorTecnico directorTecnicoCollectionDirectorTecnicoToAttach : equipo.getDirectorTecnicoCollection()) {
                directorTecnicoCollectionDirectorTecnicoToAttach = em.getReference(directorTecnicoCollectionDirectorTecnicoToAttach.getClass(), directorTecnicoCollectionDirectorTecnicoToAttach.getIdDirector());
                attachedDirectorTecnicoCollection.add(directorTecnicoCollectionDirectorTecnicoToAttach);
            }
            equipo.setDirectorTecnicoCollection(attachedDirectorTecnicoCollection);
            em.persist(equipo);
            for (Jugador jugadorCollectionJugador : equipo.getJugadorCollection()) {
                Equipo oldIdequipoOfJugadorCollectionJugador = jugadorCollectionJugador.getIdequipo();
                jugadorCollectionJugador.setIdequipo(equipo);
                jugadorCollectionJugador = em.merge(jugadorCollectionJugador);
                if (oldIdequipoOfJugadorCollectionJugador != null) {
                    oldIdequipoOfJugadorCollectionJugador.getJugadorCollection().remove(jugadorCollectionJugador);
                    oldIdequipoOfJugadorCollectionJugador = em.merge(oldIdequipoOfJugadorCollectionJugador);
                }
            }
            for (Partidoxequipo partidoxequipoCollectionPartidoxequipo : equipo.getPartidoxequipoCollection()) {
                Equipo oldEquipoOfPartidoxequipoCollectionPartidoxequipo = partidoxequipoCollectionPartidoxequipo.getEquipo();
                partidoxequipoCollectionPartidoxequipo.setEquipo(equipo);
                partidoxequipoCollectionPartidoxequipo = em.merge(partidoxequipoCollectionPartidoxequipo);
                if (oldEquipoOfPartidoxequipoCollectionPartidoxequipo != null) {
                    oldEquipoOfPartidoxequipoCollectionPartidoxequipo.getPartidoxequipoCollection().remove(partidoxequipoCollectionPartidoxequipo);
                    oldEquipoOfPartidoxequipoCollectionPartidoxequipo = em.merge(oldEquipoOfPartidoxequipoCollectionPartidoxequipo);
                }
            }
            for (DirectorTecnico directorTecnicoCollectionDirectorTecnico : equipo.getDirectorTecnicoCollection()) {
                Equipo oldIdequipoOfDirectorTecnicoCollectionDirectorTecnico = directorTecnicoCollectionDirectorTecnico.getIdequipo();
                directorTecnicoCollectionDirectorTecnico.setIdequipo(equipo);
                directorTecnicoCollectionDirectorTecnico = em.merge(directorTecnicoCollectionDirectorTecnico);
                if (oldIdequipoOfDirectorTecnicoCollectionDirectorTecnico != null) {
                    oldIdequipoOfDirectorTecnicoCollectionDirectorTecnico.getDirectorTecnicoCollection().remove(directorTecnicoCollectionDirectorTecnico);
                    oldIdequipoOfDirectorTecnicoCollectionDirectorTecnico = em.merge(oldIdequipoOfDirectorTecnicoCollectionDirectorTecnico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipo(equipo.getIdequipo()) != null) {
                throw new PreexistingEntityException("Equipo " + equipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipo equipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo persistentEquipo = em.find(Equipo.class, equipo.getIdequipo());
            Collection<Jugador> jugadorCollectionOld = persistentEquipo.getJugadorCollection();
            Collection<Jugador> jugadorCollectionNew = equipo.getJugadorCollection();
            Collection<Partidoxequipo> partidoxequipoCollectionOld = persistentEquipo.getPartidoxequipoCollection();
            Collection<Partidoxequipo> partidoxequipoCollectionNew = equipo.getPartidoxequipoCollection();
            Collection<DirectorTecnico> directorTecnicoCollectionOld = persistentEquipo.getDirectorTecnicoCollection();
            Collection<DirectorTecnico> directorTecnicoCollectionNew = equipo.getDirectorTecnicoCollection();
            List<String> illegalOrphanMessages = null;
            for (Partidoxequipo partidoxequipoCollectionOldPartidoxequipo : partidoxequipoCollectionOld) {
                if (!partidoxequipoCollectionNew.contains(partidoxequipoCollectionOldPartidoxequipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partidoxequipo " + partidoxequipoCollectionOldPartidoxequipo + " since its equipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Jugador> attachedJugadorCollectionNew = new ArrayList<Jugador>();
            for (Jugador jugadorCollectionNewJugadorToAttach : jugadorCollectionNew) {
                jugadorCollectionNewJugadorToAttach = em.getReference(jugadorCollectionNewJugadorToAttach.getClass(), jugadorCollectionNewJugadorToAttach.getIdjugador());
                attachedJugadorCollectionNew.add(jugadorCollectionNewJugadorToAttach);
            }
            jugadorCollectionNew = attachedJugadorCollectionNew;
            equipo.setJugadorCollection(jugadorCollectionNew);
            Collection<Partidoxequipo> attachedPartidoxequipoCollectionNew = new ArrayList<Partidoxequipo>();
            for (Partidoxequipo partidoxequipoCollectionNewPartidoxequipoToAttach : partidoxequipoCollectionNew) {
                partidoxequipoCollectionNewPartidoxequipoToAttach = em.getReference(partidoxequipoCollectionNewPartidoxequipoToAttach.getClass(), partidoxequipoCollectionNewPartidoxequipoToAttach.getPartidoxequipoPK());
                attachedPartidoxequipoCollectionNew.add(partidoxequipoCollectionNewPartidoxequipoToAttach);
            }
            partidoxequipoCollectionNew = attachedPartidoxequipoCollectionNew;
            equipo.setPartidoxequipoCollection(partidoxequipoCollectionNew);
            Collection<DirectorTecnico> attachedDirectorTecnicoCollectionNew = new ArrayList<DirectorTecnico>();
            for (DirectorTecnico directorTecnicoCollectionNewDirectorTecnicoToAttach : directorTecnicoCollectionNew) {
                directorTecnicoCollectionNewDirectorTecnicoToAttach = em.getReference(directorTecnicoCollectionNewDirectorTecnicoToAttach.getClass(), directorTecnicoCollectionNewDirectorTecnicoToAttach.getIdDirector());
                attachedDirectorTecnicoCollectionNew.add(directorTecnicoCollectionNewDirectorTecnicoToAttach);
            }
            directorTecnicoCollectionNew = attachedDirectorTecnicoCollectionNew;
            equipo.setDirectorTecnicoCollection(directorTecnicoCollectionNew);
            equipo = em.merge(equipo);
            for (Jugador jugadorCollectionOldJugador : jugadorCollectionOld) {
                if (!jugadorCollectionNew.contains(jugadorCollectionOldJugador)) {
                    jugadorCollectionOldJugador.setIdequipo(null);
                    jugadorCollectionOldJugador = em.merge(jugadorCollectionOldJugador);
                }
            }
            for (Jugador jugadorCollectionNewJugador : jugadorCollectionNew) {
                if (!jugadorCollectionOld.contains(jugadorCollectionNewJugador)) {
                    Equipo oldIdequipoOfJugadorCollectionNewJugador = jugadorCollectionNewJugador.getIdequipo();
                    jugadorCollectionNewJugador.setIdequipo(equipo);
                    jugadorCollectionNewJugador = em.merge(jugadorCollectionNewJugador);
                    if (oldIdequipoOfJugadorCollectionNewJugador != null && !oldIdequipoOfJugadorCollectionNewJugador.equals(equipo)) {
                        oldIdequipoOfJugadorCollectionNewJugador.getJugadorCollection().remove(jugadorCollectionNewJugador);
                        oldIdequipoOfJugadorCollectionNewJugador = em.merge(oldIdequipoOfJugadorCollectionNewJugador);
                    }
                }
            }
            for (Partidoxequipo partidoxequipoCollectionNewPartidoxequipo : partidoxequipoCollectionNew) {
                if (!partidoxequipoCollectionOld.contains(partidoxequipoCollectionNewPartidoxequipo)) {
                    Equipo oldEquipoOfPartidoxequipoCollectionNewPartidoxequipo = partidoxequipoCollectionNewPartidoxequipo.getEquipo();
                    partidoxequipoCollectionNewPartidoxequipo.setEquipo(equipo);
                    partidoxequipoCollectionNewPartidoxequipo = em.merge(partidoxequipoCollectionNewPartidoxequipo);
                    if (oldEquipoOfPartidoxequipoCollectionNewPartidoxequipo != null && !oldEquipoOfPartidoxequipoCollectionNewPartidoxequipo.equals(equipo)) {
                        oldEquipoOfPartidoxequipoCollectionNewPartidoxequipo.getPartidoxequipoCollection().remove(partidoxequipoCollectionNewPartidoxequipo);
                        oldEquipoOfPartidoxequipoCollectionNewPartidoxequipo = em.merge(oldEquipoOfPartidoxequipoCollectionNewPartidoxequipo);
                    }
                }
            }
            for (DirectorTecnico directorTecnicoCollectionOldDirectorTecnico : directorTecnicoCollectionOld) {
                if (!directorTecnicoCollectionNew.contains(directorTecnicoCollectionOldDirectorTecnico)) {
                    directorTecnicoCollectionOldDirectorTecnico.setIdequipo(null);
                    directorTecnicoCollectionOldDirectorTecnico = em.merge(directorTecnicoCollectionOldDirectorTecnico);
                }
            }
            for (DirectorTecnico directorTecnicoCollectionNewDirectorTecnico : directorTecnicoCollectionNew) {
                if (!directorTecnicoCollectionOld.contains(directorTecnicoCollectionNewDirectorTecnico)) {
                    Equipo oldIdequipoOfDirectorTecnicoCollectionNewDirectorTecnico = directorTecnicoCollectionNewDirectorTecnico.getIdequipo();
                    directorTecnicoCollectionNewDirectorTecnico.setIdequipo(equipo);
                    directorTecnicoCollectionNewDirectorTecnico = em.merge(directorTecnicoCollectionNewDirectorTecnico);
                    if (oldIdequipoOfDirectorTecnicoCollectionNewDirectorTecnico != null && !oldIdequipoOfDirectorTecnicoCollectionNewDirectorTecnico.equals(equipo)) {
                        oldIdequipoOfDirectorTecnicoCollectionNewDirectorTecnico.getDirectorTecnicoCollection().remove(directorTecnicoCollectionNewDirectorTecnico);
                        oldIdequipoOfDirectorTecnicoCollectionNewDirectorTecnico = em.merge(oldIdequipoOfDirectorTecnicoCollectionNewDirectorTecnico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipo.getIdequipo();
                if (findEquipo(id) == null) {
                    throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo equipo;
            try {
                equipo = em.getReference(Equipo.class, id);
                equipo.getIdequipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Partidoxequipo> partidoxequipoCollectionOrphanCheck = equipo.getPartidoxequipoCollection();
            for (Partidoxequipo partidoxequipoCollectionOrphanCheckPartidoxequipo : partidoxequipoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipo (" + equipo + ") cannot be destroyed since the Partidoxequipo " + partidoxequipoCollectionOrphanCheckPartidoxequipo + " in its partidoxequipoCollection field has a non-nullable equipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Jugador> jugadorCollection = equipo.getJugadorCollection();
            for (Jugador jugadorCollectionJugador : jugadorCollection) {
                jugadorCollectionJugador.setIdequipo(null);
                jugadorCollectionJugador = em.merge(jugadorCollectionJugador);
            }
            Collection<DirectorTecnico> directorTecnicoCollection = equipo.getDirectorTecnicoCollection();
            for (DirectorTecnico directorTecnicoCollectionDirectorTecnico : directorTecnicoCollection) {
                directorTecnicoCollectionDirectorTecnico.setIdequipo(null);
                directorTecnicoCollectionDirectorTecnico = em.merge(directorTecnicoCollectionDirectorTecnico);
            }
            em.remove(equipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipo> findEquipoEntities() {
        return findEquipoEntities(true, -1, -1);
    }

    public List<Equipo> findEquipoEntities(int maxResults, int firstResult) {
        return findEquipoEntities(false, maxResults, firstResult);
    }

    private List<Equipo> findEquipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipo.class));
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

    public Equipo findEquipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipo> rt = cq.from(Equipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
