/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pesistencia;

import Pesistencia.exceptions.IllegalOrphanException;
import Pesistencia.exceptions.NonexistentEntityException;
import Pesistencia.exceptions.PreexistingEntityException;
import entrega3.Entidades.DirectorTecnico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Equipo;
import entrega3.Entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class DirectorTecnicoJpaController implements Serializable {

    public DirectorTecnicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DirectorTecnico directorTecnico) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = directorTecnico.getPersona();
        if (personaOrphanCheck != null) {
            DirectorTecnico oldDirectorTecnicoOfPersona = personaOrphanCheck.getDirectorTecnico();
            if (oldDirectorTecnicoOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type DirectorTecnico whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipo idequipo = directorTecnico.getIdequipo();
            if (idequipo != null) {
                idequipo = em.getReference(idequipo.getClass(), idequipo.getIdequipo());
                directorTecnico.setIdequipo(idequipo);
            }
            Persona persona = directorTecnico.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdpersona());
                directorTecnico.setPersona(persona);
            }
            em.persist(directorTecnico);
            if (idequipo != null) {
                idequipo.getDirectorTecnicoCollection().add(directorTecnico);
                idequipo = em.merge(idequipo);
            }
            if (persona != null) {
                persona.setDirectorTecnico(directorTecnico);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDirectorTecnico(directorTecnico.getIdDirector()) != null) {
                throw new PreexistingEntityException("DirectorTecnico " + directorTecnico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DirectorTecnico directorTecnico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DirectorTecnico persistentDirectorTecnico = em.find(DirectorTecnico.class, directorTecnico.getIdDirector());
            Equipo idequipoOld = persistentDirectorTecnico.getIdequipo();
            Equipo idequipoNew = directorTecnico.getIdequipo();
            Persona personaOld = persistentDirectorTecnico.getPersona();
            Persona personaNew = directorTecnico.getPersona();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                DirectorTecnico oldDirectorTecnicoOfPersona = personaNew.getDirectorTecnico();
                if (oldDirectorTecnicoOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type DirectorTecnico whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idequipoNew != null) {
                idequipoNew = em.getReference(idequipoNew.getClass(), idequipoNew.getIdequipo());
                directorTecnico.setIdequipo(idequipoNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdpersona());
                directorTecnico.setPersona(personaNew);
            }
            directorTecnico = em.merge(directorTecnico);
            if (idequipoOld != null && !idequipoOld.equals(idequipoNew)) {
                idequipoOld.getDirectorTecnicoCollection().remove(directorTecnico);
                idequipoOld = em.merge(idequipoOld);
            }
            if (idequipoNew != null && !idequipoNew.equals(idequipoOld)) {
                idequipoNew.getDirectorTecnicoCollection().add(directorTecnico);
                idequipoNew = em.merge(idequipoNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setDirectorTecnico(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setDirectorTecnico(directorTecnico);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = directorTecnico.getIdDirector();
                if (findDirectorTecnico(id) == null) {
                    throw new NonexistentEntityException("The directorTecnico with id " + id + " no longer exists.");
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
            DirectorTecnico directorTecnico;
            try {
                directorTecnico = em.getReference(DirectorTecnico.class, id);
                directorTecnico.getIdDirector();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The directorTecnico with id " + id + " no longer exists.", enfe);
            }
            Equipo idequipo = directorTecnico.getIdequipo();
            if (idequipo != null) {
                idequipo.getDirectorTecnicoCollection().remove(directorTecnico);
                idequipo = em.merge(idequipo);
            }
            Persona persona = directorTecnico.getPersona();
            if (persona != null) {
                persona.setDirectorTecnico(null);
                persona = em.merge(persona);
            }
            em.remove(directorTecnico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DirectorTecnico> findDirectorTecnicoEntities() {
        return findDirectorTecnicoEntities(true, -1, -1);
    }

    public List<DirectorTecnico> findDirectorTecnicoEntities(int maxResults, int firstResult) {
        return findDirectorTecnicoEntities(false, maxResults, firstResult);
    }

    private List<DirectorTecnico> findDirectorTecnicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DirectorTecnico.class));
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

    public DirectorTecnico findDirectorTecnico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DirectorTecnico.class, id);
        } finally {
            em.close();
        }
    }

    public int getDirectorTecnicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DirectorTecnico> rt = cq.from(DirectorTecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
