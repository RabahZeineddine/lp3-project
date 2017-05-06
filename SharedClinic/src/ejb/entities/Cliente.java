/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rabah
 */
@Entity
@XmlRootElement
public class Cliente extends Usuario implements Serializable{
//    @XmlElement(name = "rg")
    private String rg;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENTE_ID")
    private List<Consulta> consultas;

    public Cliente() {
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void addConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

}
