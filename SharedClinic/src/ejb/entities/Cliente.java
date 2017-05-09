/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rabah
 */
@Entity
@XmlRootElement
@NamedQuery(name="Cliente.findById",query="SELECT c FROM Cliente c WHERE c.id= :cliente_id")
public class Cliente extends Usuario implements Serializable{
//    @XmlElement(name = "rg")
    private String rg;

//    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true, cascade = CascadeType.MERGE)
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "CLIENTE_ID")  
    
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JoinColumn(name = "CLIENTE_ID")  
    @Transient
    private List<Consulta> consultas;

    public Cliente() {
        consultas = new ArrayList<>();
    }

    public Cliente(long id){
        super.setId(id);
        consultas = new ArrayList<>();
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

    @Override
    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void addConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

}
