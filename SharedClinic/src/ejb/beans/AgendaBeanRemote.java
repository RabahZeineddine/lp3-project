/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans;

import ejb.entities.Agenda;
import javax.ejb.Remote;

/**
 *
 * @author rabah
 */
@Remote
public interface AgendaBeanRemote {
    
    Agenda findById(long id);
}
