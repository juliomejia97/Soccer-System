/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import Pesistencia.Conexion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "PARTIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partido.findAll", query = "SELECT p FROM Partido p")
    , @NamedQuery(name = "Partido.findByIdpartido", query = "SELECT p FROM Partido p WHERE p.idpartido = :idpartido")
    , @NamedQuery(name = "Partido.findByFecha", query = "SELECT p FROM Partido p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Partido.findByIdlocal", query = "SELECT p FROM Partido p WHERE p.idlocal = :idlocal")
    , @NamedQuery(name = "Partido.findByIdvisitante", query = "SELECT p FROM Partido p WHERE p.idvisitante = :idvisitante")})
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPARTIDO")
    private Integer idpartido;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "IDLOCAL")
    private Integer idlocal;
    @Column(name = "IDVISITANTE")
    private Integer idvisitante;
    @OneToMany(mappedBy = "idpartido")
    private Collection<Tiquete> tiqueteCollection;
    @JoinColumn(name = "IDESTADIO", referencedColumnName = "IDESTADIO")
    @ManyToOne
    private Estadio idestadio;
    @JoinColumn(name = "IDFASE", referencedColumnName = "IDFASE")
    @ManyToOne
    private Fase idfase;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partido")
    private Collection<Partidoxequipo> partidoxequipoCollection;
    @OneToMany(mappedBy = "idpartido")
    private Collection<Anotacion> anotacionCollection;
    @OneToMany(mappedBy = "idpartido")
    private Collection<Tarjeta> tarjetaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partido")
    private Collection<Alineacion> alineacionCollection;

    public Partido() {
    }

    public Partido(Integer idpartido) {
        this.idpartido = idpartido;
    }

    public Integer getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(Integer idpartido) {
        this.idpartido = idpartido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(Integer idlocal) {
        this.idlocal = idlocal;
    }

    public Integer getIdvisitante() {
        return idvisitante;
    }

    public void setIdvisitante(Integer idvisitante) {
        this.idvisitante = idvisitante;
    }

    @XmlTransient
    public Collection<Tiquete> getTiqueteCollection() {
        return tiqueteCollection;
    }

    public void setTiqueteCollection(Collection<Tiquete> tiqueteCollection) {
        this.tiqueteCollection = tiqueteCollection;
    }

    public Estadio getIdestadio() {
        return idestadio;
    }

    public void setIdestadio(Estadio idestadio) {
        this.idestadio = idestadio;
    }

    public Fase getIdfase() {
        return idfase;
    }

    public void setIdfase(Fase idfase) {
        this.idfase = idfase;
    }

    @XmlTransient
    public Collection<Partidoxequipo> getPartidoxequipoCollection() {
        return partidoxequipoCollection;
    }

    public void setPartidoxequipoCollection(Collection<Partidoxequipo> partidoxequipoCollection) {
        this.partidoxequipoCollection = partidoxequipoCollection;
    }

    @XmlTransient
    public Collection<Anotacion> getAnotacionCollection() {
        return anotacionCollection;
    }

    public void setAnotacionCollection(Collection<Anotacion> anotacionCollection) {
        this.anotacionCollection = anotacionCollection;
    }

    @XmlTransient
    public Collection<Tarjeta> getTarjetaCollection() {
        return tarjetaCollection;
    }

    public void setTarjetaCollection(Collection<Tarjeta> tarjetaCollection) {
        this.tarjetaCollection = tarjetaCollection;
    }

    @XmlTransient
    public Collection<Alineacion> getAlineacionCollection() {
        return alineacionCollection;
    }

    public void setAlineacionCollection(Collection<Alineacion> alineacionCollection) {
        this.alineacionCollection = alineacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpartido != null ? idpartido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partido)) {
            return false;
        }
        Partido other = (Partido) object;
        if ((this.idpartido == null && other.idpartido != null) || (this.idpartido != null && !this.idpartido.equals(other.idpartido))) {
            return false;
        }
        return true;
    }
    public List<Object[]> equiposCodigoPartido()
    {
        
        Conexion con =  new Conexion();
        EntityManager em = con.getEm();
        Query query = em.createNativeQuery("SELECT t2.IDPARTIDO, t2.NOMBREEQUIPO, t3.NOMBREEQUIPO FROM PARTIDO t4, EQUIPO t3, (SELECT t0.IDPARTIDO, t1.NOMBREEQUIPO FROM PARTIDO t0, EQUIPO t1 WHERE (t0.IDVISITANTE = t1.IDEQUIPO)) t2 WHERE ((t2.IDPARTIDO = t4.IDPARTIDO) AND (t4.IDLOCAL = t3.IDEQUIPO))");
        List<Object[]> results  = query.getResultList();
        return results;
    } 
    
    @Override
    public String toString() {
        return "entrega3.Entidades.Partido[ idpartido=" + idpartido + " ]";
    }
    
    public List<Object[]> anotacionPartido(Integer idPartido)
    {
        Conexion con = new Conexion();
        EntityManager em = con.getEm();
        Query consu = em.createQuery("select e.nombreequipo, concat(p.nombre, ' ',p.apellidos), a.minuto, j.idjugador \n" +
        "from Equipo e, Persona p, Jugador j, Anotacion a, Partido pa\n" +
        "where p.idpersona = j.idjugador and a.idpartido.idpartido = pa.idpartido and a.idjugador.idjugador = j.idjugador and j.idequipo.idequipo = e.idequipo and pa.idpartido = :idPartido").setParameter("idPartido", idPartido);
        List<Object[]> results = consu.getResultList();
        return results;
    }

    public List<Object[]> obetenerPArtidos() {
        Conexion con = new Conexion();
        EntityManager em = con.getEm();
        Query consu = em.createQuery("Select p.idpartido, p.fecha, e.nombreestadio, c.nombreciudad, concat(el.nombreequipo, '- ',ev.nombreequipo)\n " +
"from Partido p , Estadio e, Ciudad c, Equipo el, Equipo ev\n" +
"where p.idestadio.idestadio = e.idestadio \n" +
"and e.idciudad.idciudad = c.idciudad \n" +
"and el.idequipo = p.idlocal and ev.idequipo = p.idvisitante\n" +
"order by p.idpartido");
        List<Object[]> results = consu.getResultList();
        return results;
    }
    
    public List<Object[]> tarjetasPartido(Integer idPartido){
        Conexion con = new Conexion();
        EntityManager em = con.getEm();
        Query consu = em.createQuery("Select e.nombreequipo, concat(j.persona.nombre,' ',j.persona.apellidos), t.minuto, t.tipotarjeta\n" +
        "From Equipo e, Tarjeta t, Jugador j\n" +
        "where t.idpartido.idpartido=:idP and e.idequipo = j.idequipo.idequipo and t.idjugador.idjugador = j.idjugador");
        consu.setParameter("idP", idPartido);
        List<Object[]> resu = consu.getResultList();
        return resu;
                
    }
    
}
