/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans.rest;

import ejb.beans.EspecialidadeBeanRemote;
import ejb.entities.Especialidade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author rabah
 */
@Stateless
@LocalBean
@Path("/Especialidades")
public class EspecialidadeRestService implements EspecialidadeRestServiceLocal {

    @EJB
    private EspecialidadeBeanRemote especialidadeBean;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Path("/getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONArray findAll() {
        System.out.println("Find all especialidades method invoked..");
        JSONParser parser = new JSONParser();
        List<Especialidade> especialidades;
        especialidades = especialidadeBean.findAll();
        JSONArray esp = new JSONArray();
        esp.add(especialidades);
        return esp;
    }
}
