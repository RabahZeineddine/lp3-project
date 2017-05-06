<%-- 
    Document   : index
    Created on : Apr 30, 2017, 12:12:23 AM
    Author     : rabah
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        
        <p>${alert}</p>
        <p>${error}</p>

        <h2>REST request via js</h2>
        <h3>Create user</h3>
            <input type="text" name="nome" id="nome"  placholder="nome"/>
            <input type="text" name="sobrenome" id="sobrenome" placeholder="sobrenome"/></br>
            <input type="email" id="email" placeholder="email"/></br>
            <input type="password" id="senha" placeholder="senha"/></br>
            <input type="text" id="cpf" placeholder="cpf"/></br>
            <input type="text" id="endereco" placeholder="endereco"/></br>
            <input type="text" id="telefone" placeholder="telefone"/></br>
            <input type="radio" name="userType" value="cliente" onchange="radioChange(this)"/> Cliente
            <input type="radio" name="userType" value="medico" onchange="radioChange(this)"/> Medico
            <div id="newInput"></div>
            <input type="button" value="criar" id="login-btn" onclick="createUser()" disabled/>
            <br><br>
            <h3>Login form</h3>
            <input type="email" placeholder="Email" id="login_email"/><br>
            <input type="password" placeholder="password" id="login_password"/>
            <input type="button" value="Log in" onclick="loginUser()"/>
            
            
            <br><br>
            <h3>Update profile</h3>
            <div id="update_form"><h6> aparece quando loggar..</h6></div>
            
            <h3>Log out </h3>
            <div id="logout_button"><h6> aparece quando loggar..</h6></div>
            
            <h3>Delete user</h3>
            <div id="delete_button"><h6> aparece quando loggar..</h6></div>
            <script src="js/util.js"></script>
            <script src="js/custom.js"></script>
        
    </body>
</html>