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
import entrega3.Entidades.Equipo;
import entrega3.Entidades.Jugador;
import entrega3.Entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class JugadorJpaController implements Serializable {

    public JugadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugador jugador) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = jugador.getPersona();
        if (personaOrphanCheck != null) {
            Jugador oldJugadorOfPersona = personaOrphanCheck.getJugador();
            if (oldJugadorOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Jugador whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo idequipo = jugador.getIdequipo();
            if (idequipo != null) {
                idequipo = em.getReference(idequipo.getClass(), idequipo.getIdequipo());
                jugador.setIdequipo(idequipo);
            }
            Persona persona = jugador.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdpersona());
                jugador.setPersona(persona);
            }
            em.persist(jugador);
            if (idequipo != null) {
                idequipo.getJugadorCollection().add(jugador);
                idequipo = em.merge(idequipo);
            }
            if (persona != null) {
                persona.setJugador(jugador);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJugador(jugador.getIdjugador()) != null) {
                throw new PreexistingEntityException("Jugador " + jugador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jugador jugador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador persistentJugador = em.find(Jugador.class, jugador.getIdjugador());
            Equipo idequipoOld = persistentJugador.getIdequipo();
            Equipo idequipoNew = jugador.getIdequipo();
            Persona personaOld = persistentJugador.getPersona();
            Persona personaNew = jugador.getPersona();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Jugador oldJugadorOfPersona = personaNew.getJugador();
                if (oldJugadorOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Jugador whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idequipoNew != null) {
                idequipoNew = em.getReference(idequipoNew.getClass(), idequipoNew.getIdequipo());
                jugador.setIdequipo(idequipoNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdpersona());
                jugador.setPersona(personaNew);
            }
            jugador = em.merge(jugador);
            if (idequipoOld != null && !idequipoOld.equals(idequipoNew)) {
                idequipoOld.getJugadorCollection().remove(jugador);
                idequipoOld = em.merge(idequipoOld);
            }
            if (idequipoNew != null && !idequipoNew.equals(idequipoOld)) {
                idequipoNew.getJugadorCollection().add(jugador);
                idequipoNew = em.merge(idequipoNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setJugador(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setJugador(jugador);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jugador.getIdjugador();
                if (findJugador(id) == null) {
                    throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.");
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
            Jugador jugador;
            try {
                jugador = em.getReference(Jugador.class, id);
                jugador.getIdjugador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.", enfe);
            }
            Equipo idequipo = jugador.getIdequipo();
            if (idequipo != null) {
                idequipo.getJugadorCollection().remove(jugador);
                idequipo = em.merge(idequipo);
            }
            Persona persona = jugador.getPersona();
            if (persona != null) {
                persona.setJugador(null);
                persona = em.merge(persona);
            }
            em.remove(jugador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jugador> findJugadorEntities() {
        return findJugadorEntities(true, -1, -1);
    }

    public List<Jugador> findJugadorEntities(int maxResults, int firstResult) {
        return findJugadorEntities(false, maxResults, firstResult);
    }

    private List<Jugador> findJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugador.class));
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

    public Jugador findJugador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugador> rt = cq.from(Jugador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
