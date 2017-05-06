/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans.rest;

import ejb.beans.UsuarioBeanRemote;
import ejb.entities.Cliente;
import ejb.entities.Medico;
import ejb.entities.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    @Path("/createUserClient")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONObject createUserClient(Cliente usuarioJSON) {
        System.out.println("Criar cliente method invoked..");
        JSONObject response = new JSONObject();
        JSONParser parser = new JSONParser();
        boolean error = false;
        String msg = "";
        try {
            Usuario u = null;
            u = usuarioBean.findByEmail(usuarioJSON.getEmail());
            if (u != null) {
                error = true;
                msg = "Email já está cadastrado!";
            } else if ((u = usuarioBean.findByCPF(usuarioJSON.getCpf())) != null) {
                error = true;
                msg = "CPF já está cadastrado!";
            } else {
                usuarioBean.criarUsuario(usuarioJSON);
                msg = "Usuario criado com sucesso!";
            }
        } catch (Exception e) {

            error = true;
            msg = "Erro na criacao do usuario!";
        }

        response.put("error", new Boolean(error));
        response.put("msg", msg);
        return response;

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Path("/createUserDoctor")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONObject createUserDoctor(Medico usuarioJSON) {
        System.out.println("Received data: "+usuarioJSON);
        JSONObject response = new JSONObject();
        JSONParser parser = new JSONParser();
        boolean error = false;
        String msg = "";
        try {
            Usuario u = null;
            u = usuarioBean.findByEmail(usuarioJSON.getEmail());
            if (u != null) {
                error = true;
                msg = "Email já está cadastrado!";
            } else if ((u = usuarioBean.findByCPF(usuarioJSON.getCpf())) != null) {
                error = true;
                msg = "CPF já está cadastrado!";
            } else {
                usuarioBean.criarUsuario(usuarioJSON);
                msg = "Usuario criado com sucesso!";
            }
        } catch (Exception e) {
            error = true;
            msg = "Erro na criacao do usuario!";
        }

        response.put("error", new Boolean(error));
        response.put("msg", msg);
        return response;
    }

    @Path("/login")
    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONObject login(String jsonUser) {
        System.out.println("Login method invoked..");
        boolean error = false;
        String msg = "";
        JSONObject response = new JSONObject();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(jsonUser);
            JSONObject user = (JSONObject) obj;
            Usuario u = usuarioBean.findByEmail((String) user.get("email"));

            if (u != null) {
                if (u.getSenha().equals((String) user.get("senha"))) {
                    //Authenticated user
                    msg = "Usuário autorizado!";
                    response.put("authorized", new Boolean(true));
                    response.put("user", u);
                } else {
                    error = true;
                    msg = "Senha incorreta!";
                    response.put("authorized", new Boolean(false));

                }
            } else {
                error = true;
                msg = "Usuário não cadastrado! <a href='signup.html'>Cadastra-se aqui</a>";
            }

        } catch (Exception ex) {
            Logger.getLogger(UsuarioRestService.class.getName()).log(Level.SEVERE, null, ex);
            error = true;
            msg = "Um erro ocorreu durante o processo de login! Tente novamente!";
        }
        response.put("error", error);
        response.put("msg",msg);
        return response;
    }

   

}
