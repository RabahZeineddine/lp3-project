/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function createUser() {

    var user = {nome: document.getElementById('nome').value,
        sobrenome: document.getElementById('sobrenome').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value,
        cpf: document.getElementById('cpf').value,
        usuario: document.getElementById('usuario').value,
        endereco: document.getElementById('endereco').value,
        telefone: document.getElementById('telefone').value,
        rg: document.getElementById('rg').value,
        consultas : [{horario: new Date(),dataConsulta: new Date(),status: "Done"},{horario: new Date(),dataConsulta: new Date(),status: "Done"}]};

    xhrPut('/ClinicWEB/UsersManager/users/createUser', JSON.stringify(user), function (data) {
        alert(data.error + ' msg: ' + data.msg);
    }, function (err) {
        console.log(err);
    });
}