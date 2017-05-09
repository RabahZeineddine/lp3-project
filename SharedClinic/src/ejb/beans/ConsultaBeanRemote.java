/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Consulta;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rabah
 */
@Remote
public interface ConsultaBeanRemote {

    List<Consulta> findAllByEspecialidade(long id);
    List<Consulta> findAllByAgenda(long id);
    boolean insert(Consulta consulta);
    List<Consulta> findAllByClienteID(long id);
    
    boolean removeById(long id);
}
