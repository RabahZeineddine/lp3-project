/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var URI = "http://192.168.1.101:8080/ClinicWEB/UsersManager";
var CREATE_PATH, UPDATE_PATH,DELETE_PATH;
var USER_TYPE;
var LOGGED_USER;


function createUser() {

    var user = {nome: document.getElementById('nome').value,
        sobrenome: document.getElementById('sobrenome').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value,
        cpf: document.getElementById('cpf').value,
        endereco: document.getElementById('endereco').value,
        telefone: document.getElementById('telefone').value};

    if (USER_TYPE.localeCompare("medico") == 0) {
        var select = document.getElementById('especialidades');
        user.especialidade = {id: select.selectedIndex};
        user.crm = document.getElementById('crm').value;
        CREATE_PATH = "createUserDoctor";
    } else {
        user.rg = document.getElementById('rg').value;
        CREATE_PATH = "createUserClient";
    }

    alert(JSON.stringify(user));
//        consultas : [{horario: new Date(),dataConsulta: new Date(),status: "Done"},{horario: new Date(),dataConsulta: new Date(),status: "Done"}]};

    xhrPut(URI+'/users/'+ CREATE_PATH, JSON.stringify(user), function (data) {
        if (!data.error) {
            alert(data.msg);
        } else {
            alert(data.msg);
        }
    }, function (err) {
        console.log(err);
        alert('um erro ocorreu!');
    });
}

function radioChange(radio) {
    USER_TYPE = radio.value;
    document.getElementById('login-btn').disabled = false;
    if (USER_TYPE.localeCompare("medico") == 0) {
        document.getElementById('newInput').innerHTML = '<img style="width:40px;" src="img/loading.gif"/>';
        xhrGet(URI+'/Especialidades/getAll', function (data) {

            var select = '<select id="especialidades"><option value="">Selecione uma especialidade</option>';

            for (var esp in data[0]) {

                select += '<option value="' + data[0][esp]['id'] + '">' + data[0][esp]['nome'] + '</option>';
            }
            select += '</select><br><input type="text" id="crm" placeholder="CRM"/>';
            document.getElementById('newInput').innerHTML = select;
            maskInputs();
        }, function (err) {
            console.log(err);
            alert('um erro ocorreu!');
        });
//        document.getElementById('newInput').innerHTML = '<br><input type="text" id="crm" placeholder="CRM"/>';
    } else {
        document.getElementById('newInput').innerHTML = '<br><input type="text" id="rg" placeholder="RG"/>';
        maskInputs();
    }
}


function loginUser() {

    var user = {email: document.getElementById('login_email').value,
        senha: document.getElementById('login_password').value};
    xhrPost(URI+'/users/login', user, function (data) {
        if (!data.error && data.authorized) {
            alert(data.msg);
            LOGGED_USER = data.user;
            setCookie('user', JSON.stringify(LOGGED_USER), 1);
            makeUserLogged();
        } else {
            alert(data.msg);
        }
    }, function (err) {
        console.log(err);

    })

}

function makeUserLogged() {
    LOGGED_USER = JSON.parse(getCookie('user'));

    var update_form = document.getElementById('update_form');
    update_form.innerHTML = '<input type="text" name="nome" id="update_nome"  placeholder="nome" value="' + LOGGED_USER.nome + '"/>' +
            '<input type="text" name="sobrenome" id="update_sobrenome" placeholder="sobrenome" value="' + LOGGED_USER.sobrenome + '"/></br>' +
            '<input type="email" id="update_email" placeholder="email" value="' + LOGGED_USER.email + '"/></br>' +
            '<input type="password" id="update_senha" placeholder="senha"/></br>' +
            '<input type="text" id="update_cpf" placeholder="cpf" value="' + LOGGED_USER.cpf + '"/></br>' +
            '<input type="text" id="update_endereco" placeholder="endereco" value="' + LOGGED_USER.endereco + '"/></br>' +
            '<input type="text" id="update_telefone" placeholder="telefone" value="' + LOGGED_USER.telefone + '"/></br>';
    if (LOGGED_USER.dtype == "Medico") {
        xhrGet(URI+'/Especialidades/getAll', function (data) {

            var select = '<select id="update_especialidades"><option value="' + LOGGED_USER.especialidade.id + '">' + LOGGED_USER.especialidade.nome + '</option>';

            for (var esp in data[0]) {

                select += '<option value="' + data[0][esp]['id'] + '">' + data[0][esp]['nome'] + '</option>';
            }
            select += '</select><br><input type="text" id="update_crm" placeholder="CRM" value="' + LOGGED_USER.crm + '"/>';
            update_form.innerHTML += select;
             update_form.innerHTML += '<br><input type="button" value="Atualizar" id="update-btn" onclick="updateUser()"/>';
        }, function (err) {
            console.log(err);
            alert('um erro ocorreu!');
        });


    } else {
        update_form.innerHTML += '<input type="text" id="update_rg" placeholder="RG" value="' + LOGGED_USER.rg + '"/>';
        update_form.innerHTML += '<br><input type="button" value="Atualizar" id="update-btn" onclick="updateUser()"/>';
    }
    

    document.getElementById('logout_button').innerHTML = '<button onclick="logout()">Logout</button>';
    document.getElementById('delete_button').innerHTML = '<button onclick="deleteUser()">Delete</button>';
    ;

}

function updateUser() {
    var user = LOGGED_USER;
    user.nome = document.getElementById('update_nome').value;
    user.sobrenome = document.getElementById('update_sobrenome').value;
    user.email = document.getElementById('update_email').value;
    user.senha = document.getElementById('update_senha').value;
    user.cpf = document.getElementById('update_cpf').value;
    user.endereco = document.getElementById('update_endereco').value;
    user.telefone = document.getElementById('update_telefone').value;

    if (LOGGED_USER.dtype == "Medico") {
        var select = document.getElementById('update_especialidades');
        user.especialidade = {id: select.selectedIndex};
        user.crm = document.getElementById('update_crm').value;
        UPDATE_PATH = "updateUserDoctor";
    } else {
        user.rg = document.getElementById('update_rg').value;
        UPDATE_PATH = "updateUserClient";
    }
    xhrPut(URI+'/users/' + UPDATE_PATH, JSON.stringify(user), function (data) {
        if (!data.error) {
            alert(data.msg);
            setCookie('user', JSON.stringify(data.user), 1);
            makeUserLogged();
        } else {
            alert(data.msg);
        }
    }, function (err) {
        console.log(err);
        alert('um erro ocorreu!');
    });
}

function logout() {
    alert('logout');
}
function deleteUser() {
    LOGGED_USER = JSON.parse(getCookie('user'));
    
    xhrDelete(URI+'/users/removeAccount',LOGGED_USER,function(data){
        alert(data.msg);
    },function(err){
        console.log(err);
        alert('Ocorreu um erro!');
    })

}