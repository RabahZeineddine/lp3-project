/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author rabah
 */
@Entity
public class Medico  extends Usuario implements Serializable{
    private String crm;
    
    @OneToOne
    private Agenda agenda;

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
    
    
}
