/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Controladores;

import entrega3.Controladores.exceptions.IllegalOrphanException;
import entrega3.Controladores.exceptions.NonexistentEntityException;
import entrega3.Controladores.exceptions.PreexistingEntityException;
import entrega3.Entidades.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entrega3.Entidades.Silla;
import java.util.ArrayList;
import java.util.Collection;
import entrega3.Entidades.Precio;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) throws PreexistingEntityException, Exception {
        if (categoria.getSillaCollection() == null) {
            categoria.setSillaCollection(new ArrayList<Silla>());
        }
        if (categoria.getPrecioCollection() == null) {
            categoria.setPrecioCollection(new ArrayList<Precio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Silla> attachedSillaCollection = new ArrayList<Silla>();
            for (Silla sillaCollectionSillaToAttach : categoria.getSillaCollection()) {
                sillaCollectionSillaToAttach = em.getReference(sillaCollectionSillaToAttach.getClass(), sillaCollectionSillaToAttach.getSillaPK());
                attachedSillaCollection.add(sillaCollectionSillaToAttach);
            }
            categoria.setSillaCollection(attachedSillaCollection);
            Collection<Precio> attachedPrecioCollection = new ArrayList<Precio>();
            for (Precio precioCollectionPrecioToAttach : categoria.getPrecioCollection()) {
                precioCollectionPrecioToAttach = em.getReference(precioCollectionPrecioToAttach.getClass(), precioCollectionPrecioToAttach.getPrecioPK());
                attachedPrecioCollection.add(precioCollectionPrecioToAttach);
            }
            categoria.setPrecioCollection(attachedPrecioCollection);
            em.persist(categoria);
            for (Silla sillaCollectionSilla : categoria.getSillaCollection()) {
                Categoria oldIdcategoriaOfSillaCollectionSilla = sillaCollectionSilla.getIdcategoria();
                sillaCollectionSilla.setIdcategoria(categoria);
                sillaCollectionSilla = em.merge(sillaCollectionSilla);
                if (oldIdcategoriaOfSillaCollectionSilla != null) {
                    oldIdcategoriaOfSillaCollectionSilla.getSillaCollection().remove(sillaCollectionSilla);
                    oldIdcategoriaOfSillaCollectionSilla = em.merge(oldIdcategoriaOfSillaCollectionSilla);
                }
            }
            for (Precio precioCollectionPrecio : categoria.getPrecioCollection()) {
                Categoria oldCategoriaOfPrecioCollectionPrecio = precioCollectionPrecio.getCategoria();
                precioCollectionPrecio.setCategoria(categoria);
                precioCollectionPrecio = em.merge(precioCollectionPrecio);
                if (oldCategoriaOfPrecioCollectionPrecio != null) {
                    oldCategoriaOfPrecioCollectionPrecio.getPrecioCollection().remove(precioCollectionPrecio);
                    oldCategoriaOfPrecioCollectionPrecio = em.merge(oldCategoriaOfPrecioCollectionPrecio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoria(categoria.getIdcategoria()) != null) {
                throw new PreexistingEntityException("Categoria " + categoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdcategoria());
            Collection<Silla> sillaCollectionOld = persistentCategoria.getSillaCollection();
            Collection<Silla> sillaCollectionNew = categoria.getSillaCollection();
            Collection<Precio> precioCollectionOld = persistentCategoria.getPrecioCollection();
            Collection<Precio> precioCollectionNew = categoria.getPrecioCollection();
            List<String> illegalOrphanMessages = null;
            for (Precio precioCollectionOldPrecio : precioCollectionOld) {
                if (!precioCollectionNew.contains(precioCollectionOldPrecio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Precio " + precioCollectionOldPrecio + " since its categoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Silla> attachedSillaCollectionNew = new ArrayList<Silla>();
            for (Silla sillaCollectionNewSillaToAttach : sillaCollectionNew) {
                sillaCollectionNewSillaToAttach = em.getReference(sillaCollectionNewSillaToAttach.getClass(), sillaCollectionNewSillaToAttach.getSillaPK());
                attachedSillaCollectionNew.add(sillaCollectionNewSillaToAttach);
            }
            sillaCollectionNew = attachedSillaCollectionNew;
            categoria.setSillaCollection(sillaCollectionNew);
            Collection<Precio> attachedPrecioCollectionNew = new ArrayList<Precio>();
            for (Precio precioCollectionNewPrecioToAttach : precioCollectionNew) {
                precioCollectionNewPrecioToAttach = em.getReference(precioCollectionNewPrecioToAttach.getClass(), precioCollectionNewPrecioToAttach.getPrecioPK());
                attachedPrecioCollectionNew.add(precioCollectionNewPrecioToAttach);
            }
            precioCollectionNew = attachedPrecioCollectionNew;
            categoria.setPrecioCollection(precioCollectionNew);
            categoria = em.merge(categoria);
            for (Silla sillaCollectionOldSilla : sillaCollectionOld) {
                if (!sillaCollectionNew.contains(sillaCollectionOldSilla)) {
                    sillaCollectionOldSilla.setIdcategoria(null);
                    sillaCollectionOldSilla = em.merge(sillaCollectionOldSilla);
                }
            }
            for (Silla sillaCollectionNewSilla : sillaCollectionNew) {
                if (!sillaCollectionOld.contains(sillaCollectionNewSilla)) {
                    Categoria oldIdcategoriaOfSillaCollectionNewSilla = sillaCollectionNewSilla.getIdcategoria();
                    sillaCollectionNewSilla.setIdcategoria(categoria);
                    sillaCollectionNewSilla = em.merge(sillaCollectionNewSilla);
                    if (oldIdcategoriaOfSillaCollectionNewSilla != null && !oldIdcategoriaOfSillaCollectionNewSilla.equals(categoria)) {
                        oldIdcategoriaOfSillaCollectionNewSilla.getSillaCollection().remove(sillaCollectionNewSilla);
                        oldIdcategoriaOfSillaCollectionNewSilla = em.merge(oldIdcategoriaOfSillaCollectionNewSilla);
                    }
                }
            }
            for (Precio precioCollectionNewPrecio : precioCollectionNew) {
                if (!precioCollectionOld.contains(precioCollectionNewPrecio)) {
                    Categoria oldCategoriaOfPrecioCollectionNewPrecio = precioCollectionNewPrecio.getCategoria();
                    precioCollectionNewPrecio.setCategoria(categoria);
                    precioCollectionNewPrecio = em.merge(precioCollectionNewPrecio);
                    if (oldCategoriaOfPrecioCollectionNewPrecio != null && !oldCategoriaOfPrecioCollectionNewPrecio.equals(categoria)) {
                        oldCategoriaOfPrecioCollectionNewPrecio.getPrecioCollection().remove(precioCollectionNewPrecio);
                        oldCategoriaOfPrecioCollectionNewPrecio = em.merge(oldCategoriaOfPrecioCollectionNewPrecio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getIdcategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Precio> precioCollectionOrphanCheck = categoria.getPrecioCollection();
            for (Precio precioCollectionOrphanCheckPrecio : precioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Precio " + precioCollectionOrphanCheckPrecio + " in its precioCollection field has a non-nullable categoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Silla> sillaCollection = categoria.getSillaCollection();
            for (Silla sillaCollectionSilla : sillaCollection) {
                sillaCollectionSilla.setIdcategoria(null);
                sillaCollectionSilla = em.merge(sillaCollectionSilla);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public int getIdCategoriaN(String nCategoria){
        Categoria retornar;
        EntityManager em = getEntityManager();
        Query consu = em.createNamedQuery("Categoria.findByNombrecategoria");
        consu.setParameter("nombrecategoria", nCategoria);
        retornar = (Categoria) consu.getSingleResult();
        return retornar.getIdcategoria();
    }
    
    public BigDecimal precioCategoria(String nCategoria){
        BigDecimal precio;
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p.precio\n" +
    "from Precio p, Categoria c\n" +
    "where c.nombrecategoria = :nombrec and p.precioPK.idcategoria = c.idcategoria \n" +
    "and p.precioPK.idfase = 1");
        query.setParameter("nombrec", nCategoria);
        precio = (BigDecimal) query.getSingleResult();
        return precio;
    }
}
