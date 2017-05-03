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
        <h1>Hello World!</h1>
        
        <p>${alert}</p>
        <p>${error}</p>
         
        <form action="${pageContext.request.contextPath}/FrontControllerServlet?control=Usuario&fn=criar" method="POST">
            
            <input type="text" name="nome" />
            <input type="text" name="sobrenome"/>
            <input type="submit" value="criar"/>
        </form>
        
        <h2>REST request via js</h2>
            <input type="text" name="nome" id="nome"  placholder="nome"/>
            <input type="text" name="sobrenome" id="sobrenome" placeholder="sobrenome"/></br>
            <input type="email" id="email" placeholder="email"/></br>
            <input type="password" id="senha" placeholder="senha"/></br>
            <input type="text" id="cpf" placeholder="cpf"/></br>
            <input type="text" id="usuario" placeholder="usuario"/></br>
            <input type="text" id="endereco" placeholder="endereco"/></br>
            <input type="text" id="telefone" placeholder="telefone"/></br>
            <input type="text" id="rg" placeholder="rg"/></br>
            
            <input type="button" value="criar" onclick="createUser()"/>
            
            <script src="js/util.js"></script>
            <script src="js/custom.js"></script>
        
    </body>
</html>