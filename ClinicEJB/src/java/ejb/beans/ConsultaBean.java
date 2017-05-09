/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Consulta;
import ejb.entities.Medico;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rabah
 */
@Stateless
public class ConsultaBean implements ConsultaBeanRemote {

    @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;

    @Override
    public List<Consulta> findAllByEspecialidade(long id) {
        List<Consulta> consultas = new ArrayList<>();
        Query query = em.createNamedQuery("Consulta.findAllByEspecialidade").setParameter("especialidade_id", id);
        consultas = query.getResultList();
        return consultas;

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Consulta> findAllByAgenda(long id) {
        List<Consulta> consultas = new ArrayList<>();
        Query query = em.createNamedQuery("Consulta.findAllByAgenda").setParameter("agenda_id", id).setParameter("today", new Date());
        consultas = query.getResultList();
        return consultas;
    }

    @Override
    public boolean insert(Consulta consulta) {
        try {
            em.persist(consulta);
            em.flush();
            em.refresh(consulta);
            return true;
        } catch (Exception e) {
            System.out.println("Erro no metodo insert da consulta: "+e);
            return false;
        }
    }

    @Override
    public List<Consulta> findAllByClienteID(long id) {
        try{
        List<Consulta> consultas = new ArrayList<>();
        Query query = em.createNamedQuery("Consulta.findAllByClienteID").setParameter("cliente_id", id);
        consultas = query.getResultList();
        
        for(Consulta c: consultas){
            Query q = em.createNamedQuery("Medico.findByAgendaId").setParameter("agenda_id", c.getAgenda().getId());
            c.setMedico((Medico) q.getResultList().get(0));
        }
        return consultas;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
        
    }

    @Override
    public boolean removeById(long id) {
        try{
        Consulta c =  em.find(Consulta.class, id);
        em.remove(c);
        em.flush();
        return true;
        }catch(Exception e){
            System.out.println("Erro ao remover a consulta: "+e);
            return false;
        }
    }
}
