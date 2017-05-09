/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Especialidade;
import java.util.ArrayList;
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
public class EspecialidadeBean implements EspecialidadeBeanRemote {

    @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;
    
    
    @Override
    public List<Especialidade> findAll() {
        List<Especialidade> especialidades = new ArrayList<>();
        Query query = em.createNamedQuery("Especialidade.findAll");
        especialidades = (query.getResultList().size()>0)?query.getResultList():null;
        return especialidades;
    }

  

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
