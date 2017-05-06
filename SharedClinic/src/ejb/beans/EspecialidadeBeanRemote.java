/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Especialidade;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rabah
 */
@Remote
public interface EspecialidadeBeanRemote {

    List<Especialidade> findAll();
    
}
