/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans.rest;

import javax.ejb.Local;
import org.json.simple.JSONArray;

/**
 *
 * @author rabah
 */
@Local
public interface EspecialidadeRestServiceLocal {
    
    JSONArray findAll();
}
