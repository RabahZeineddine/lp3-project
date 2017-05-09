/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author rabah
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consulta.findAllByEspecialidade", query = "SELECT c FROM Consulta c JOIN c.agenda a JOIN a.medico m WHERE m.especialidade.id = :especialidade_id ORDER BY m.id ASC, c.dataConsulta ASC")
    ,
@NamedQuery(name = "Consulta.findAllByAgendaID", query = "SELECT c FROM Consulta c WHERE c.agenda.id = :agenda_id AND c.dataConsulta >= :today ORDER BY c.dataConsulta ASC")
    ,
@NamedQuery(name = "Consulta.findAllByClienteID", query = "SELECT c FROM Consulta c JOIN c.agenda.medico m WHERE c.cliente.id = :cliente_id")})
public class Consulta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Transient
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
    @Transient
    SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
//    @Temporal(TemporalType.TIMESTAMP)
    @Transient
    private String horario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataConsulta;

    private boolean marcada;

//     @JoinColumn(name = "CLIENTE_ID")
     
//    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JoinColumn(nullable = false, updatable = false)
      @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, updatable = false)
    @JsonIgnore
    private Cliente cliente;

    //    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, updatable = false)
@JsonIgnore
    private Agenda agenda;

    @Transient
    private Medico medico;

    @Transient
    private String dia;

    public Consulta() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setFormattedHorario() {
        this.horario = hourFormat.format(dataConsulta);
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setFormattedDia() {
        this.dia = dayFormat.format(dataConsulta);
    }

}
