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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rabah
 */
@Entity
@XmlRootElement
@NamedQueries({@NamedQuery(name="Medico.findAllByEspecialidade",query="SELECT m FROM Medico m WHERE m.especialidade.id= :especialidade_id"),
@NamedQuery(name="Medico.findByAgendaId",query="SELECT m from Medico m WHERE m.agenda.id = :agenda_id")})

public class Medico  extends Usuario implements Serializable{
    private String crm;
    
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Agenda agenda;
    
    @OneToOne
    private Especialidade especialidade;
    
    
    public String getCrm() {
        return crm;
    }

    @Override
    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    @Override
    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }
    
    @Override
    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "Medico{" + "crm=" + crm + ", agenda=" + agenda + ", especialidade=" + especialidade + '}';
    }
    
    
    
    
    
    
    
}
