/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans.rest;

import ejb.beans.UsuarioBeanRemote;
import ejb.entities.Cliente;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 *
 * @author rabah
 */
@Stateless
@LocalBean
@Path("/users")
public class UsuarioRestService implements UsuarioRestServiceLocal {

    @EJB
    private UsuarioBeanRemote usuarioBean;

    
    @Path("/createUser")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONObject novoUsuario(Cliente usuarioJSON) {  
        System.out.println("Received data: "+usuarioJSON);
        JSONObject response = new JSONObject();
        JSONParser parser = new JSONParser();
        
        System.out.println("tamanho das consultas : "+usuarioJSON.getConsultas().size());
//        try {
//            Object obj = parser.parse(usuarioJSON);
//            JSONObject user = (JSONObject) obj;
//            
//            System.out.println(user.get("nome"));
//            
//            Usuario u = new Cliente();
//            u.setNome((String) user.get("nome"));
//            u.setSobrenome((String) user.get("sobrenome"));
//            
            usuarioBean.criarUsuario(usuarioJSON);
//            
//            
            response.put("error", new Boolean(false));
            response.put("msg", "Usuario criado com sucesso!");
            
            
            
//        } catch (ParseException ex) {
//            Logger.getLogger(UsuarioRestService.class.getName()).log(Level.SEVERE, null, ex);
//            response.put("error", new Boolean(true));
//            response.put("msg", "Erro na criacao do usuario!");
////        }
        return response;
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
    
}
