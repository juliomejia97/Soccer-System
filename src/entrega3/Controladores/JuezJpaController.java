/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.IllegalOrphanException;
import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Juez;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class JuezJpaController implements Serializable {

    public JuezJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juez juez) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = juez.getPersona();
        if (personaOrphanCheck != null) {
            Juez oldJuezOfPersona = personaOrphanCheck.getJuez();
            if (oldJuezOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Juez whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = juez.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdpersona());
                juez.setPersona(persona);
            }
            em.persist(juez);
            if (persona != null) {
                persona.setJuez(juez);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJuez(juez.getIdjuez()) != null) {
                throw new PreexistingEntityException("Juez " + juez + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juez juez) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juez persistentJuez = em.find(Juez.class, juez.getIdjuez());
            Persona personaOld = persistentJuez.getPersona();
            Persona personaNew = juez.getPersona();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Juez oldJuezOfPersona = personaNew.getJuez();
                if (oldJuezOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Juez whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdpersona());
                juez.setPersona(personaNew);
            }
            juez = em.merge(juez);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setJuez(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setJuez(juez);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = juez.getIdjuez();
                if (findJuez(id) == null) {
                    throw new NonexistentEntityException("The juez with id " + id + " no longer exists.");
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
            Juez juez;
            try {
                juez = em.getReference(Juez.class, id);
                juez.getIdjuez();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juez with id " + id + " no longer exists.", enfe);
            }
            Persona persona = juez.getPersona();
            if (persona != null) {
                persona.setJuez(null);
                persona = em.merge(persona);
            }
            em.remove(juez);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juez> findJuezEntities() {
        return findJuezEntities(true, -1, -1);
    }

    public List<Juez> findJuezEntities(int maxResults, int firstResult) {
        return findJuezEntities(false, maxResults, firstResult);
    }

    private List<Juez> findJuezEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juez.class));
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

    public Juez findJuez(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juez.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuezCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juez> rt = cq.from(Juez.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
