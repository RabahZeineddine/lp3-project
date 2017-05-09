/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Agenda;
import ejb.entities.Cliente;
import ejb.entities.Consulta;
import ejb.entities.Medico;
import ejb.entities.Usuario;
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
public class UsuarioBean implements UsuarioBeanRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;

    @Override
    public Usuario criarUsuario(Usuario u) {
        em.persist(u);
        em.flush();
        em.refresh(u);
        return u;
    }

    @Override
    public Usuario findByEmail(String email) {
        Query query = em.createNamedQuery("Usuario.findByEmail").setParameter("email", email);
        Usuario u = null;
        if (query.getResultList().size() > 0) {
            u = (Usuario) query.getResultList().get(0);
            if (u instanceof Cliente) {
                Query query2 = em.createNamedQuery("Consulta.findAllByClienteID").setParameter("cliente_id", u.getId());
                List<Consulta> consultas = query2.getResultList();
                
                    for(Consulta c: consultas){
            Query q = em.createNamedQuery("Medico.findByAgendaId").setParameter("agenda_id", c.getAgenda().getId());
            c.setMedico((Medico) q.getResultList().get(0));
        }
                ((Cliente) u).setConsultas(consultas);
            } else if (u instanceof Medico) {
                Query query3 = em.createNamedQuery("Consulta.findAllByAgendaID").setParameter("agenda_id", ((Medico) u).getAgenda().getId()).setParameter("today", new Date());
                List<Consulta> consultas = query3.getResultList();
                Agenda a = new Agenda(((Medico) u).getAgenda().getId());
                a.setConsultas(consultas);
                u.setAgenda(a);
            }
            return u;
        } else {
            return null;
        }

    }

    @Override
    public Usuario findByCPF(String cpf) {
        Query query = em.createNamedQuery("Usuario.findByCPF").setParameter("cpf", cpf);
        Usuario u = null;
        u = (query.getResultList().size() > 0) ? (Usuario) query.getResultList().get(0) : null;
        return u;
    }

    @Override
    public Usuario findById(long id) {

        Usuario u = em.find(Usuario.class, id);

        if (u instanceof Cliente) {
            Query query2 = em.createNamedQuery("Consulta.findAllByClienteID").setParameter("cliente_id", u.getId());
            List<Consulta> consultas = query2.getResultList();
            for (Consulta c : consultas) {
                Query q = em.createNamedQuery("Medico.findByAgendaId").setParameter("agenda_id", c.getAgenda().getId());
                c.setMedico((Medico) q.getResultList().get(0));
            }

            ((Cliente) u).setConsultas(consultas);
            return u;
        } else if (u instanceof Medico) {
            Query query3 = em.createNamedQuery("Consulta.findAllByAgendaID").setParameter("agenda_id", ((Medico) u).getAgenda().getId());
            List<Consulta> consultas = query3.getResultList();
            Agenda a = new Agenda(((Medico) u).getAgenda().getId());
            a.setConsultas(consultas);
            u.setAgenda(a);
            return u;
        }
        return null;
    }

    @Override
    public Cliente findClienteById(long id) {
        try {
            Query query = em.createNamedQuery("Cliente.findById").setParameter("cliente_id", id);
            List<Cliente> clientes = query.getResultList();
            Cliente c = new Cliente();
            if (clientes.size() > 0) {
                c = clientes.get(0);
                Query query2 = em.createNamedQuery("Consulta.findAllByClienteID").setParameter("cliente_id", id);
                c.setConsultas(query2.getResultList());
            }

            return c;
        } catch (Exception e) {

            System.out.println(e);
        }
        return null;

    }

    @Override
    public Usuario atualizarUsuario(Usuario u) {
        Usuario aux = null;
        try {
            aux = em.merge(u);
            em.flush();
            return aux;
        } catch (Exception e) {
            System.out.println(e);
            return aux;
        }
    }

    @Override
    public boolean remove(long id) {

        try {
            Usuario u = em.find(Usuario.class,
                    id);
            em.remove(u);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Medico> findAllMedicosByEspecialidade(long id) {
        List<Medico> medicos = new ArrayList<>();
        Query query = em.createNamedQuery("Medico.findAllByEspecialidade").setParameter("especialidade_id", id);
        medicos = query.getResultList();
        return medicos;
    }

    @Override
    public Medico findByAgendaId(long id) {
        Medico medico = null;
        Query query = em.createNamedQuery("Medico.findByAgendaId").setParameter("agenda_id", id);
        medico = (query.getResultList().size() > 0) ? (Medico) query.getResultList().get(0) : null;
        return medico;
    }
}
