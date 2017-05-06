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
    public Usuario criarUsuario(Usuario u){
        em.persist(u);
        em.flush();
        em.refresh(u);
        return u;
    }

    @Override
    public Usuario findByEmail(String email) {
        Query query = em.createNamedQuery("Usuario.findByEmail").setParameter("email", email);
        Usuario u = null;
        u = (Usuario) query.getResultList().get(0);
        return u;
    }

    @Override
    public Usuario findByCPF(String cpf) {
        Query query = em.createNamedQuery("Usuario.findByCPF").setParameter("cpf", cpf);
        Usuario u = null;
        u = (Usuario) query.getResultList().get(0);
        return u;
    }
}
