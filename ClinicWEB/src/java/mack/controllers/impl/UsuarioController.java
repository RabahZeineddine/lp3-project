/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mack.controllers.impl;

import ejb.beans.UsuarioBean;
import ejb.beans.UsuarioBeanRemote;
import ejb.entities.Cliente;
import ejb.entities.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import mack.controllers.AbstractController;

/**
 *
 * @author rabah
 */
public class UsuarioController extends AbstractController {

    UsuarioBeanRemote usuarioBean = lookupUsuarioBeanRemote();

    @Override
    public void execute() {
        String function = this.getRequest().getParameter("fn");
        switch (function) {
            case "criar":
                createUser();
                break;
        }
    }

    private void createUser() {
        HttpServletRequest request = this.getRequest();
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");

        //            Context context = new InitialContext();
//            UsuarioBean usuarioBean =  (UsuarioBean) context.lookup("java:global/ClinicAPP/ClinicEJB/UsuarioBean!ejb.beans.UsuarioBeanRemote");
        Cliente u = new Cliente();
        u.setNome(nome);
        u.setSobrenome(sobrenome);
        u.setRg("123.123.123");
        u = (Cliente) usuarioBean.criarUsuario(u);
        if (u != null) {
            request.getSession().setAttribute("alert", "Criado com sucesso!");
        } else {
            request.getSession().setAttribute("error", "Erro na criacao do novo usuario!");
        }
        this.setReturnPage("/index.jsp");

    }

    private UsuarioBeanRemote lookupUsuarioBeanRemote() {
        try {
            Context c = new InitialContext();
            return (UsuarioBeanRemote) c.lookup("java:global/ClinicAPP/ClinicEJB/UsuarioBean!ejb.beans.UsuarioBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
