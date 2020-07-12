/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "PARTIDOXEQUIPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partidoxequipo.findAll", query = "SELECT p FROM Partidoxequipo p")
    , @NamedQuery(name = "Partidoxequipo.findByIdpartido", query = "SELECT p FROM Partidoxequipo p WHERE p.partidoxequipoPK.idpartido = :idpartido")
    , @NamedQuery(name = "Partidoxequipo.findByIdequipo", query = "SELECT p FROM Partidoxequipo p WHERE p.partidoxequipoPK.idequipo = :idequipo")
    , @NamedQuery(name = "Partidoxequipo.findByGoles", query = "SELECT p FROM Partidoxequipo p WHERE p.goles = :goles")
    , @NamedQuery(name = "Partidoxequipo.findByVisitante", query = "SELECT p FROM Partidoxequipo p WHERE p.visitante = :visitante")})
public class Partidoxequipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PartidoxequipoPK partidoxequipoPK;
    @Column(name = "GOLES")
    private Integer goles;
    @Column(name = "VISITANTE")
    private Short visitante;
    @JoinColumn(name = "IDEQUIPO", referencedColumnName = "IDEQUIPO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Equipo equipo;
    @JoinColumn(name = "IDPARTIDO", referencedColumnName = "IDPARTIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partido partido;

    public Partidoxequipo() {
    }

    public Partidoxequipo(PartidoxequipoPK partidoxequipoPK) {
        this.partidoxequipoPK = partidoxequipoPK;
    }

    public Partidoxequipo(int idpartido, int idequipo) {
        this.partidoxequipoPK = new PartidoxequipoPK(idpartido, idequipo);
    }

    public PartidoxequipoPK getPartidoxequipoPK() {
        return partidoxequipoPK;
    }

    public void setPartidoxequipoPK(PartidoxequipoPK partidoxequipoPK) {
        this.partidoxequipoPK = partidoxequipoPK;
    }

    public Integer getGoles() {
        return goles;
    }

    public void setGoles(Integer goles) {
        this.goles = goles;
    }

    public Short getVisitante() {
        return visitante;
    }

    public void setVisitante(Short visitante) {
        this.visitante = visitante;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (partidoxequipoPK != null ? partidoxequipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partidoxequipo)) {
            return false;
        }
        Partidoxequipo other = (Partidoxequipo) object;
        if ((this.partidoxequipoPK == null && other.partidoxequipoPK != null) || (this.partidoxequipoPK != null && !this.partidoxequipoPK.equals(other.partidoxequipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.Partidoxequipo[ partidoxequipoPK=" + partidoxequipoPK + " ]";
    }
    
}
