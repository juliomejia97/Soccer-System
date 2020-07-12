/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega3.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "DIRECTOR_TECNICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DirectorTecnico.findAll", query = "SELECT d FROM DirectorTecnico d")
    , @NamedQuery(name = "DirectorTecnico.findByIdDirector", query = "SELECT d FROM DirectorTecnico d WHERE d.idDirector = :idDirector")
    , @NamedQuery(name = "DirectorTecnico.findByTipo", query = "SELECT d FROM DirectorTecnico d WHERE d.tipo = :tipo")})
public class DirectorTecnico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DIRECTOR")
    private Integer idDirector;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @JoinColumn(name = "IDEQUIPO", referencedColumnName = "IDEQUIPO")
    @ManyToOne
    private Equipo idequipo;
    @JoinColumn(name = "ID_DIRECTOR", referencedColumnName = "IDPERSONA", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public DirectorTecnico() {
    }

    public DirectorTecnico(Integer idDirector) {
        this.idDirector = idDirector;
    }

    public DirectorTecnico(Integer idDirector, String tipo) {
        this.idDirector = idDirector;
        this.tipo = tipo;
    }

    public Integer getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Integer idDirector) {
        this.idDirector = idDirector;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Equipo getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(Equipo idequipo) {
        this.idequipo = idequipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDirector != null ? idDirector.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DirectorTecnico)) {
            return false;
        }
        DirectorTecnico other = (DirectorTecnico) object;
        if ((this.idDirector == null && other.idDirector != null) || (this.idDirector != null && !this.idDirector.equals(other.idDirector))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entrega3.Entidades.DirectorTecnico[ idDirector=" + idDirector + " ]";
    }
    
}
