/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author rabah
 */

@Entity
@XmlRootElement
@NamedQuery(name = "Especialidade.findAll",query = "SELECT e FROM Especialidade e")
public class Especialidade implements Serializable{
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    
    private String nome;

    public Especialidade() {
    }

    public Especialidade(long id) {
        this.id = id;
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "{id:" + id + ", nome:" + nome + '}';
    }

    
    
    
}
