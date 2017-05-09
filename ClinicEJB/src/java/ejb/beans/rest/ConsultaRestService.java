/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans.rest;

import ejb.beans.AgendaBeanRemote;
import ejb.beans.ConsultaBeanRemote;
import ejb.beans.UsuarioBeanRemote;
import ejb.entities.Agenda;
import ejb.entities.Cliente;
import ejb.entities.Consulta;
import ejb.entities.Medico;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rabah
 */
@Path("/Consultas")
@Stateless
public class ConsultaRestService implements ConsultaRestServiceLocal {

    @EJB
    private AgendaBeanRemote agendaBean;

    @EJB
    private UsuarioBeanRemote usuarioBean;

    @EJB
    private ConsultaBeanRemote consultaBean;

    @Path("/allByEspecialidade/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONArray findAllByEspecialidade(@PathParam("id") long id) {
        System.out.println("Find all consultas by especialidade method invoked .. id: " + id);

        List<Medico> medicos = usuarioBean.findAllMedicosByEspecialidade(id);

        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String dates = "";

        List<Consulta> consultas = new ArrayList<>();

        for (Medico m : medicos) {
            List<Consulta> aux_cons = consultaBean.findAllByAgenda(m.getAgenda().getId());

            for (Consulta c : aux_cons) {
                dates += ft.format(c.getDataConsulta()) + " ,";
            }

            //Loop for 3 days
            for (int j = 1; j <= 3; j++) {

                Calendar aux_cal = Calendar.getInstance();
                aux_cal.setTime(today);
                aux_cal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                if (j != 1) {
                    aux_cal.add(Calendar.DATE, j - 1);
                }

                if ((j == 1 && (calendar.get(Calendar.HOUR_OF_DAY) >= 8 && calendar.get(Calendar.HOUR_OF_DAY) <= 18)) || j > 1) {
                    //Loop from current hour to 18 or from 8 to 18 (Clinic open time)
                    for (int i = (j == 1) ? calendar.get(Calendar.HOUR_OF_DAY) : 8; i < 18; i++) {

                        Consulta c = new Consulta();

                        aux_cal.set(Calendar.HOUR_OF_DAY, i);
                        aux_cal.set(Calendar.MINUTE, 0);
                        aux_cal.set(Calendar.SECOND, 0);

                        Date aux_date = aux_cal.getTime();

                        if (dates.indexOf(ft.format(aux_date)) == -1) {
                            c.setDataConsulta(aux_date);
                            c.setMarcada(false);
                            Medico aux_medico = new Medico();
                            aux_medico.setCrm(m.getCrm());
                            aux_medico.setNome(m.getNome());
                            aux_medico.setSobrenome(m.getSobrenome());
                            aux_medico.setAgenda(m.getAgenda());
                            aux_medico.setId(m.getId());

                            Agenda a = new Agenda(m.getAgenda().getId());
                            c.setAgenda(a);
                            c.setMedico(aux_medico);
                            c.setDia(dayFormat.format(aux_date));
                            c.setHorario(hourFormat.format(aux_date));
                            consultas.add(c);

                        }
                    }
                }
            }

        }

        JSONArray consl = new JSONArray();
        consl.add(consultas);
        return consl;

    }

    @Path("/createConsulta")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONObject createConsulta(String dataJSON) {
        System.out.println("********************  Rceieved data: " + dataJSON);
        JSONObject response = new JSONObject();
        JSONParser parser = new JSONParser();
        boolean error = false;
        String msg = "";
        try {
            JSONObject obj = (JSONObject) parser.parse(dataJSON);

            JSONObject agendaJSON = new JSONObject();
            agendaJSON = (JSONObject) ((JSONObject) obj.get("medico")).get("agenda");
            System.out.println("********************  Parsed agenda: " + agendaJSON);
            long id_agenda = (Long) agendaJSON.get("id");

            Consulta consulta = new Consulta();

            Agenda a = agendaBean.findById(id_agenda);

            consulta.setAgenda(a);

            JSONObject clienteJSON = new JSONObject();
            clienteJSON = (JSONObject) obj.get("user");
            System.out.println("********************   Parsed cliente: " + clienteJSON);

            consulta.setMarcada(true);

            consulta.setDataConsulta(new Date((long) obj.get("dataConsulta")));

            Cliente cliente = (Cliente) usuarioBean.findById((long) clienteJSON.get("id"));
            consulta.setCliente(cliente);

            if (consultaBean.insert(consulta)) {

//                cliente.addConsulta(consulta);
                cliente = (Cliente) usuarioBean.findById(cliente.getId());
                if (cliente != null) {
                    error = false;
                    msg = "Consulta confirmada com sucesso!";

                    for (Consulta c : cliente.getConsultas()) {
                        c.setFormattedDia();
                        c.setFormattedHorario();
                    }

                    response.put("user", cliente);
                } else {
                    error = true;
                    msg = "Erro ao registrar a consulta! Tente novamente.";
                }
            } else {
                error = true;
                msg = "Erro ao registrar a consulta! Tente novamente.";
            }

        } catch (ParseException ex) {
            error = true;
            msg = "Erro ao registrar a consulta! Tente novamente.";
        }

        response.put("error", new Boolean(error));
        response.put("msg", msg);
        return response;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Path("/delete/{id}/{user_id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public JSONObject delete(@PathParam("id") long id,@PathParam("user_id") long user_id) {
        JSONObject response = new JSONObject();

        if (consultaBean.removeById(id)) {
            response.put("error", new Boolean(false));
            response.put("msg", "Consulta removida com sucesso!");
            response.put("user", usuarioBean.findById(user_id));
        } else {
            response.put("error", new Boolean(true));
            response.put("msg", "Erro ao remover a consulta! Tente novamente.");
        }

        return response;
    }
}
