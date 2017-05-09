/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.beans.rest;

import javax.ejb.Local;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author rabah
 */
@Local
public interface ConsultaRestServiceLocal {

    JSONArray findAllByEspecialidade(long id);

    JSONObject createConsulta(String dataJSON);

    JSONObject delete(long id,long user_id);
}
