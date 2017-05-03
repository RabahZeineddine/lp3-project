/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public Usuario criarUsuario(Usuario u){
        em.persist(u);
        em.flush();
        em.refresh(u);
        return u;
    }
}
