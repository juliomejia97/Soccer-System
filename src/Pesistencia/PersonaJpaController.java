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
import entrega3.Entidades.Cliente;
import entrega3.Entidades.Pais;
import entrega3.Entidades.DirectorTecnico;
import entrega3.Entidades.Juez;
import entrega3.Entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador jugador = persona.getJugador();
            if (jugador != null) {
                jugador = em.getReference(jugador.getClass(), jugador.getIdjugador());
                persona.setJugador(jugador);
            }
            Cliente cliente = persona.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdcliente());
                persona.setCliente(cliente);
            }
            Pais idpais = persona.getIdpais();
            if (idpais != null) {
                idpais = em.getReference(idpais.getClass(), idpais.getIdpais());
                persona.setIdpais(idpais);
            }
            DirectorTecnico directorTecnico = persona.getDirectorTecnico();
            if (directorTecnico != null) {
                directorTecnico = em.getReference(directorTecnico.getClass(), directorTecnico.getIdDirector());
                persona.setDirectorTecnico(directorTecnico);
            }
            Juez juez = persona.getJuez();
            if (juez != null) {
                juez = em.getReference(juez.getClass(), juez.getIdjuez());
                persona.setJuez(juez);
            }
            em.persist(persona);
            if (jugador != null) {
                Persona oldPersonaOfJugador = jugador.getPersona();
                if (oldPersonaOfJugador != null) {
                    oldPersonaOfJugador.setJugador(null);
                    oldPersonaOfJugador = em.merge(oldPersonaOfJugador);
                }
                jugador.setPersona(persona);
                jugador = em.merge(jugador);
            }
            if (cliente != null) {
                Persona oldPersonaOfCliente = cliente.getPersona();
                if (oldPersonaOfCliente != null) {
                    oldPersonaOfCliente.setCliente(null);
                    oldPersonaOfCliente = em.merge(oldPersonaOfCliente);
                }
                cliente.setPersona(persona);
                cliente = em.merge(cliente);
            }
            if (idpais != null) {
                idpais.getPersonaCollection().add(persona);
                idpais = em.merge(idpais);
            }
            if (directorTecnico != null) {
                Persona oldPersonaOfDirectorTecnico = directorTecnico.getPersona();
                if (oldPersonaOfDirectorTecnico != null) {
                    oldPersonaOfDirectorTecnico.setDirectorTecnico(null);
                    oldPersonaOfDirectorTecnico = em.merge(oldPersonaOfDirectorTecnico);
                }
                directorTecnico.setPersona(persona);
                directorTecnico = em.merge(directorTecnico);
            }
            if (juez != null) {
                Persona oldPersonaOfJuez = juez.getPersona();
                if (oldPersonaOfJuez != null) {
                    oldPersonaOfJuez.setJuez(null);
                    oldPersonaOfJuez = em.merge(oldPersonaOfJuez);
                }
                juez.setPersona(persona);
                juez = em.merge(juez);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getIdpersona()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdpersona());
            Jugador jugadorOld = persistentPersona.getJugador();
            Jugador jugadorNew = persona.getJugador();
            Cliente clienteOld = persistentPersona.getCliente();
            Cliente clienteNew = persona.getCliente();
            Pais idpaisOld = persistentPersona.getIdpais();
            Pais idpaisNew = persona.getIdpais();
            DirectorTecnico directorTecnicoOld = persistentPersona.getDirectorTecnico();
            DirectorTecnico directorTecnicoNew = persona.getDirectorTecnico();
            Juez juezOld = persistentPersona.getJuez();
            Juez juezNew = persona.getJuez();
            List<String> illegalOrphanMessages = null;
            if (jugadorOld != null && !jugadorOld.equals(jugadorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Jugador " + jugadorOld + " since its persona field is not nullable.");
            }
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cliente " + clienteOld + " since its persona field is not nullable.");
            }
            if (directorTecnicoOld != null && !directorTecnicoOld.equals(directorTecnicoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DirectorTecnico " + directorTecnicoOld + " since its persona field is not nullable.");
            }
            if (juezOld != null && !juezOld.equals(juezNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Juez " + juezOld + " since its persona field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (jugadorNew != null) {
                jugadorNew = em.getReference(jugadorNew.getClass(), jugadorNew.getIdjugador());
                persona.setJugador(jugadorNew);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdcliente());
                persona.setCliente(clienteNew);
            }
            if (idpaisNew != null) {
                idpaisNew = em.getReference(idpaisNew.getClass(), idpaisNew.getIdpais());
                persona.setIdpais(idpaisNew);
            }
            if (directorTecnicoNew != null) {
                directorTecnicoNew = em.getReference(directorTecnicoNew.getClass(), directorTecnicoNew.getIdDirector());
                persona.setDirectorTecnico(directorTecnicoNew);
            }
            if (juezNew != null) {
                juezNew = em.getReference(juezNew.getClass(), juezNew.getIdjuez());
                persona.setJuez(juezNew);
            }
            persona = em.merge(persona);
            if (jugadorNew != null && !jugadorNew.equals(jugadorOld)) {
                Persona oldPersonaOfJugador = jugadorNew.getPersona();
                if (oldPersonaOfJugador != null) {
                    oldPersonaOfJugador.setJugador(null);
                    oldPersonaOfJugador = em.merge(oldPersonaOfJugador);
                }
                jugadorNew.setPersona(persona);
                jugadorNew = em.merge(jugadorNew);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                Persona oldPersonaOfCliente = clienteNew.getPersona();
                if (oldPersonaOfCliente != null) {
                    oldPersonaOfCliente.setCliente(null);
                    oldPersonaOfCliente = em.merge(oldPersonaOfCliente);
                }
                clienteNew.setPersona(persona);
                clienteNew = em.merge(clienteNew);
            }
            if (idpaisOld != null && !idpaisOld.equals(idpaisNew)) {
                idpaisOld.getPersonaCollection().remove(persona);
                idpaisOld = em.merge(idpaisOld);
            }
            if (idpaisNew != null && !idpaisNew.equals(idpaisOld)) {
                idpaisNew.getPersonaCollection().add(persona);
                idpaisNew = em.merge(idpaisNew);
            }
            if (directorTecnicoNew != null && !directorTecnicoNew.equals(directorTecnicoOld)) {
                Persona oldPersonaOfDirectorTecnico = directorTecnicoNew.getPersona();
                if (oldPersonaOfDirectorTecnico != null) {
                    oldPersonaOfDirectorTecnico.setDirectorTecnico(null);
                    oldPersonaOfDirectorTecnico = em.merge(oldPersonaOfDirectorTecnico);
                }
                directorTecnicoNew.setPersona(persona);
                directorTecnicoNew = em.merge(directorTecnicoNew);
            }
            if (juezNew != null && !juezNew.equals(juezOld)) {
                Persona oldPersonaOfJuez = juezNew.getPersona();
                if (oldPersonaOfJuez != null) {
                    oldPersonaOfJuez.setJuez(null);
                    oldPersonaOfJuez = em.merge(oldPersonaOfJuez);
                }
                juezNew.setPersona(persona);
                juezNew = em.merge(juezNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdpersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdpersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Jugador jugadorOrphanCheck = persona.getJugador();
            if (jugadorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Jugador " + jugadorOrphanCheck + " in its jugador field has a non-nullable persona field.");
            }
            Cliente clienteOrphanCheck = persona.getCliente();
            if (clienteOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Cliente " + clienteOrphanCheck + " in its cliente field has a non-nullable persona field.");
            }
            DirectorTecnico directorTecnicoOrphanCheck = persona.getDirectorTecnico();
            if (directorTecnicoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the DirectorTecnico " + directorTecnicoOrphanCheck + " in its directorTecnico field has a non-nullable persona field.");
            }
            Juez juezOrphanCheck = persona.getJuez();
            if (juezOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Juez " + juezOrphanCheck + " in its juez field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais idpais = persona.getIdpais();
            if (idpais != null) {
                idpais.getPersonaCollection().remove(persona);
                idpais = em.merge(idpais);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
