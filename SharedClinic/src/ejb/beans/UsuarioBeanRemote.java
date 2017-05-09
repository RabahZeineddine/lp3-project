/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Cliente;
import ejb.entities.Medico;
import ejb.entities.Usuario;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rabah
 */
@Remote
public interface UsuarioBeanRemote {
    Usuario criarUsuario(Usuario u);
    Usuario findByEmail(String email);
    Usuario findByCPF(String cpf);
    Usuario findById(long id);
    Usuario atualizarUsuario(Usuario u);
    boolean remove(long id);
    List<Medico> findAllMedicosByEspecialidade(long id);
    Cliente findClienteById(long id);
    Medico findByAgendaId(long id);
    
}
