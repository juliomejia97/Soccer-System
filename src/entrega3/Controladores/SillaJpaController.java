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
import entrega3.Entidades.Categoria;
import entrega3.Entidades.Estadio;
import entrega3.Entidades.Entrada;
import entrega3.Entidades.Silla;
import entrega3.Entidades.SillaPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class SillaJpaController implements Serializable {

    public SillaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Silla silla) throws PreexistingEntityException, Exception {
        if (silla.getSillaPK() == null) {
            silla.setSillaPK(new SillaPK());
        }
        if (silla.getEntradaCollection() == null) {
            silla.setEntradaCollection(new ArrayList<Entrada>());
        }
        silla.getSillaPK().setIdestadio(silla.getEstadio().getIdestadio());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idcategoria = silla.getIdcategoria();
            if (idcategoria != null) {
                idcategoria = em.getReference(idcategoria.getClass(), idcategoria.getIdcategoria());
                silla.setIdcategoria(idcategoria);
            }
            Estadio estadio = silla.getEstadio();
            if (estadio != null) {
                estadio = em.getReference(estadio.getClass(), estadio.getIdestadio());
                silla.setEstadio(estadio);
            }
            Collection<Entrada> attachedEntradaCollection = new ArrayList<Entrada>();
            for (Entrada entradaCollectionEntradaToAttach : silla.getEntradaCollection()) {
                entradaCollectionEntradaToAttach = em.getReference(entradaCollectionEntradaToAttach.getClass(), entradaCollectionEntradaToAttach.getIdentrada());
                attachedEntradaCollection.add(entradaCollectionEntradaToAttach);
            }
            silla.setEntradaCollection(attachedEntradaCollection);
            em.persist(silla);
            if (idcategoria != null) {
                idcategoria.getSillaCollection().add(silla);
                idcategoria = em.merge(idcategoria);
            }
            if (estadio != null) {
                estadio.getSillaCollection().add(silla);
                estadio = em.merge(estadio);
            }
            for (Entrada entradaCollectionEntrada : silla.getEntradaCollection()) {
                Silla oldSillaOfEntradaCollectionEntrada = entradaCollectionEntrada.getSilla();
                entradaCollectionEntrada.setSilla(silla);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
                if (oldSillaOfEntradaCollectionEntrada != null) {
                    oldSillaOfEntradaCollectionEntrada.getEntradaCollection().remove(entradaCollectionEntrada);
                    oldSillaOfEntradaCollectionEntrada = em.merge(oldSillaOfEntradaCollectionEntrada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSilla(silla.getSillaPK()) != null) {
                throw new PreexistingEntityException("Silla " + silla + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Silla silla) throws NonexistentEntityException, Exception {
        silla.getSillaPK().setIdestadio(silla.getEstadio().getIdestadio());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Silla persistentSilla = em.find(Silla.class, silla.getSillaPK());
            Categoria idcategoriaOld = persistentSilla.getIdcategoria();
            Categoria idcategoriaNew = silla.getIdcategoria();
            Estadio estadioOld = persistentSilla.getEstadio();
            Estadio estadioNew = silla.getEstadio();
            Collection<Entrada> entradaCollectionOld = persistentSilla.getEntradaCollection();
            Collection<Entrada> entradaCollectionNew = silla.getEntradaCollection();
            if (idcategoriaNew != null) {
                idcategoriaNew = em.getReference(idcategoriaNew.getClass(), idcategoriaNew.getIdcategoria());
                silla.setIdcategoria(idcategoriaNew);
            }
            if (estadioNew != null) {
                estadioNew = em.getReference(estadioNew.getClass(), estadioNew.getIdestadio());
                silla.setEstadio(estadioNew);
            }
            Collection<Entrada> attachedEntradaCollectionNew = new ArrayList<Entrada>();
            for (Entrada entradaCollectionNewEntradaToAttach : entradaCollectionNew) {
                entradaCollectionNewEntradaToAttach = em.getReference(entradaCollectionNewEntradaToAttach.getClass(), entradaCollectionNewEntradaToAttach.getIdentrada());
                attachedEntradaCollectionNew.add(entradaCollectionNewEntradaToAttach);
            }
            entradaCollectionNew = attachedEntradaCollectionNew;
            silla.setEntradaCollection(entradaCollectionNew);
            silla = em.merge(silla);
            if (idcategoriaOld != null && !idcategoriaOld.equals(idcategoriaNew)) {
                idcategoriaOld.getSillaCollection().remove(silla);
                idcategoriaOld = em.merge(idcategoriaOld);
            }
            if (idcategoriaNew != null && !idcategoriaNew.equals(idcategoriaOld)) {
                idcategoriaNew.getSillaCollection().add(silla);
                idcategoriaNew = em.merge(idcategoriaNew);
            }
            if (estadioOld != null && !estadioOld.equals(estadioNew)) {
                estadioOld.getSillaCollection().remove(silla);
                estadioOld = em.merge(estadioOld);
            }
            if (estadioNew != null && !estadioNew.equals(estadioOld)) {
                estadioNew.getSillaCollection().add(silla);
                estadioNew = em.merge(estadioNew);
            }
            for (Entrada entradaCollectionOldEntrada : entradaCollectionOld) {
                if (!entradaCollectionNew.contains(entradaCollectionOldEntrada)) {
                    entradaCollectionOldEntrada.setSilla(null);
                    entradaCollectionOldEntrada = em.merge(entradaCollectionOldEntrada);
                }
            }
            for (Entrada entradaCollectionNewEntrada : entradaCollectionNew) {
                if (!entradaCollectionOld.contains(entradaCollectionNewEntrada)) {
                    Silla oldSillaOfEntradaCollectionNewEntrada = entradaCollectionNewEntrada.getSilla();
                    entradaCollectionNewEntrada.setSilla(silla);
                    entradaCollectionNewEntrada = em.merge(entradaCollectionNewEntrada);
                    if (oldSillaOfEntradaCollectionNewEntrada != null && !oldSillaOfEntradaCollectionNewEntrada.equals(silla)) {
                        oldSillaOfEntradaCollectionNewEntrada.getEntradaCollection().remove(entradaCollectionNewEntrada);
                        oldSillaOfEntradaCollectionNewEntrada = em.merge(oldSillaOfEntradaCollectionNewEntrada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SillaPK id = silla.getSillaPK();
                if (findSilla(id) == null) {
                    throw new NonexistentEntityException("The silla with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SillaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Silla silla;
            try {
                silla = em.getReference(Silla.class, id);
                silla.getSillaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The silla with id " + id + " no longer exists.", enfe);
            }
            Categoria idcategoria = silla.getIdcategoria();
            if (idcategoria != null) {
                idcategoria.getSillaCollection().remove(silla);
                idcategoria = em.merge(idcategoria);
            }
            Estadio estadio = silla.getEstadio();
            if (estadio != null) {
                estadio.getSillaCollection().remove(silla);
                estadio = em.merge(estadio);
            }
            Collection<Entrada> entradaCollection = silla.getEntradaCollection();
            for (Entrada entradaCollectionEntrada : entradaCollection) {
                entradaCollectionEntrada.setSilla(null);
                entradaCollectionEntrada = em.merge(entradaCollectionEntrada);
            }
            em.remove(silla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Silla> findSillaEntities() {
        return findSillaEntities(true, -1, -1);
    }

    public List<Silla> findSillaEntities(int maxResults, int firstResult) {
        return findSillaEntities(false, maxResults, firstResult);
    }

    private List<Silla> findSillaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Silla.class));
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

    public Silla findSilla(SillaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Silla.class, id);
        } finally {
            em.close();
        }
    }

    public int getSillaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Silla> rt = cq.from(Silla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
