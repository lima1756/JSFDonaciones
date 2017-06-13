/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis Iván Morett
 */
@Entity
@Table(name = "objeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objeto.findAll", query = "SELECT o FROM Objeto o")
    , @NamedQuery(name = "Objeto.findByIdobjeto", query = "SELECT o FROM Objeto o WHERE o.idobjeto = :idobjeto")
    , @NamedQuery(name = "Objeto.findByNombre", query = "SELECT o FROM Objeto o WHERE o.nombre = :nombre")
    , @NamedQuery(name = "Objeto.findByTipo", query = "SELECT o FROM Objeto o WHERE o.tipo = :tipo")
    , @NamedQuery(name = "Objeto.findByDonadoA", query = "SELECT o FROM Objeto o WHERE o.donadoA = :donadoA")
    , @NamedQuery(name = "Objeto.findByDonadoPor", query = "SELECT o FROM Objeto o WHERE o.donadoPor = :donadoPor")})
public class Objeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idobjeto")
    private Integer idobjeto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "donado_a")
    private Integer donadoA;
    @Basic(optional = false)
    @NotNull
    @Column(name = "donado_por")
    private int donadoPor;

    public Objeto() {
    }

    public Objeto(Integer idobjeto) {
        this.idobjeto = idobjeto;
    }

    public Objeto(Integer idobjeto, String nombre, String descripcion, String tipo, int donadoPor) {
        this.idobjeto = idobjeto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.donadoPor = donadoPor;
    }

    public Integer getIdobjeto() {
        return idobjeto;
    }

    public void setIdobjeto(Integer idobjeto) {
        this.idobjeto = idobjeto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getDonadoA() {
        return donadoA;
    }

    public void setDonadoA(Integer donadoA) {
        this.donadoA = donadoA;
    }

    public int getDonadoPor() {
        return donadoPor;
    }

    public void setDonadoPor(int donadoPor) {
        this.donadoPor = donadoPor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idobjeto != null ? idobjeto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objeto)) {
            return false;
        }
        Objeto other = (Objeto) object;
        if ((this.idobjeto == null && other.idobjeto != null) || (this.idobjeto != null && !this.idobjeto.equals(other.idobjeto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.jsfdonaciones.Objeto[ idobjeto=" + idobjeto + " ]";
    }
    
}
