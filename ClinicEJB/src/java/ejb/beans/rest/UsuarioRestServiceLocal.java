/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans.rest;

import ejb.entities.Cliente;
import javax.ejb.Local;
import org.json.simple.JSONObject;

/**
 *
 * @author rabah
 */
@Local
public interface UsuarioRestServiceLocal {

    JSONObject novoUsuario(Cliente usuarioJSON);
    
}
