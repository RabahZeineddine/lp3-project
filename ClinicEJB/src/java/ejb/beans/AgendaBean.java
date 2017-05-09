/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Agenda;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rabah
 */
@Stateless
public class AgendaBean implements AgendaBeanRemote {
    
    
     @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;

    @Override
    public Agenda findById(long id) {
        
        try{
        Agenda a = new Agenda();
        a = em.find(Agenda.class, id);
        em.flush();
        return a;
        }catch(Exception e){
                System.out.println("Erro ao buscar a agenda por id: "+e);
                return null;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
