var IP = "192.168.0.17";
var URI = "http://"+IP+":8080/ClinicWEB/UsersManager";
var CREATE_PATH, UPDATE_PATH, DELETE_PATH;
var USER_TYPE = "cliente";
var LOGGED_USER;




function createUser() {

    var user = {
        nome: document.getElementById('firstname').value,
        sobrenome: document.getElementById('last_name').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('password').value,
        cpf: document.getElementById('cpf').value,
        endereco: document.getElementById('address').value,
        telefone: document.getElementById('phone').value
    };

    if (USER_TYPE.localeCompare("medico") == 0) {
        var select = document.getElementById('especialidades');
        user.especialidade = {
            id: select.selectedIndex
        };
        user.crm = document.getElementById('crm').value;
        CREATE_PATH = "createUserDoctor";
    }
    else {
        user.rg = document.getElementById('rg').value;
        CREATE_PATH = "createUserClient";
    }




    xhrPut(URI + '/users/' + CREATE_PATH, JSON.stringify(user), function(data) {
        if (!data.error) {

            showAlert(data.msg, false);
            setSession('user', JSON.stringify(data.user));
            window.location = 'index.html';
        }
        else {
            showAlert(data.msg, true);
        }
    }, function(err) {
        console.log(err);


        showAlert('Ops.. ocorreu um erro! Tente novamente.', true)
    });
}






function radioChange(radio) {
    USER_TYPE = radio.value;
    // document.getElementById('login-btn').disabled = false;
    if (USER_TYPE.localeCompare("medico") == 0) {
        document.getElementById('newInput').innerHTML = '<img style="width:40px;" src="img/loading.gif"/>';
        xhrGet(URI + '/Especialidades/getAll', function(data) {


            var customHTML = '<div class="input-field col l6 s12">' +
                '<select id="especialidades"><option value="" disabled selected>Selecione uma especialidade</option>';

            for (var esp in data[0]) {

                customHTML += '<option value="' + data[0][esp]['id'] + '">' + data[0][esp]['nome'] + '</option>';
            }
            customHTML += '</select> <label>Esepecialidade</label></div> <div class="input-field col L6 s12"><input id="crm" type="text" class="validate" name="crm"><label for="crm">CRM</label>' +
                '</div></div>';
            document.getElementById('newInput').innerHTML = customHTML;
            $('select').material_select();
            maskInputs();
        }, function(err) {
            console.log(err);
            document.getElementById('newInput').innerHTML = '';
            showAlert('Ops.. ocorreu um erro! Tente novamente.');
        });
        //        document.getElementById('newInput').innerHTML = '<br><input type="text" id="crm" placeholder="CRM"/>';
    }
    else {
        document.getElementById('newInput').innerHTML = '<div class="input-field col l6 s12">' +
            '<input id="rg" type="text" class="validate" name="rg">' +
            '<label for="rg">RG</label></div>';
        maskInputs();
    }
}


function loginUser() {

    var user = {
        email: document.getElementById('email').value,
        senha: document.getElementById('password').value
    };
    xhrPost(URI + '/users/login', user, function(data) {
        if (!data.error && data.authorized) {

            // showAlert(data.msg);
            LOGGED_USER = data.user;
            // setCookie('user', JSON.stringify(LOGGED_USER), 1);
            setSession('user', JSON.stringify(LOGGED_USER));
            window.location = 'index.html';
        }
        else {
            showAlert(data.msg, true);
        }
    }, function(err) {
        console.log(err);
        showAlert('Ops.. ocorreu um erro! Tente novamente.', true);

    })

}

function logout() {
    // deleteCookie('user');
    deleteSession('user');
    window.location = 'index.html';
}




function makeChandeOnLoggedIn() {

    var pageSplit = location.pathname.split('/');
    var pageName = pageSplit[pageSplit.length - 1];

    if (pageName == "cadastro.html") {
        maskInputs();
    }
    if (sessionCheck('user')) {

        if (pageName == 'login.html' || pageName == 'cadastro.html')
            window.location = 'index.html';

        LOGGED_USER = JSON.parse(getSession('user'));
        var right_nav = document.getElementById('right_nav');
        if (pageName == 'index.html') {

            right_nav.innerHTML = '<ul id="nav-mobile" class="right-nav hide-on-med-and-down" ><li class="cadastrar-li"><a href="perfil.html">Perfil</a></li>' +
                '<li><a href="javascript:logout();">Logout</a></li></ul>';
        }
        else if ((pageName == 'perfil.html' || pageName == 'consulta.html') && LOGGED_USER.dtype == "Cliente") {
            var aux = 'Consulta';
            if (pageName == 'consulta.html') {
                aux = "Perfil";
                loadConsulta()
            }
            right_nav.innerHTML = '<ul id="nav-mobile" class="right-nav hide-on-med-and-down" ><li class="cadastrar-li"><a href="' + aux.toLowerCase() + '.html">' + aux + '</a></li>' +
                '<li><a href="javascript:logout();">Logout</a></li></ul>';
            if (pageName == "perfil.html") putUserInfo();
            
        }
        else if (pageName == "perfil.html" && LOGGED_USER.dtype == "Medico") {
            right_nav.innerHTML = '<ul id="nav-mobile" class="right-nav hide-on-med-and-down" ><li class="cadastrar-li"><a href="agenda.html">Agenda</a></li>' +
                '<li><a href="javascript:logout();">Logout</a></li></ul>';
            putUserInfo();
        }
    }
    else {

        if (pageName != 'index.html' && pageName != 'cadastro.html' && pageName != 'login.html') {
            window.location = 'index.html';
        }
    }
}

function loadConsulta() {
    var div = document.getElementById('select_consulta');

    div.innerHTML = '<img style="width:40px;" src="img/loading.gif"/>';
    xhrGet(URI + '/Especialidades/getAll', function(data) {
        var customHTML = '<div class="input-field col l12 s12">' +
            '<select id="especialidades" style="z-index:99;"onchange="loadConsultaTable();"><option value="" disabled selected>Escolhe uma especialidade</option>';
        for (var esp in data[0]) {
            customHTML += '<option value="' + data[0][esp]['id'] + '">' + data[0][esp]['nome'] + '</option>';
        }
        customHTML += '</select> <label>Esepecialidade</label></div>';
        div.innerHTML = customHTML;
        $('select').material_select();


    }, function(err) {
        console.log(err);
        document.getElementById('newInput').innerHTML = '';
        showAlert('Ops.. ocorreu um erro! Tente novamente.', true);
    });

}
var CONSULTAS;

function loadConsultaTable() {
    // $('select').material_select();
    var div = document.getElementById('consultas_info');


    var select = document.getElementById('especialidades');
    var id = select.selectedIndex;

    // div.innerHTML += '<div><img style="width:40px;" src="img/loading.gif"/></div>';
    xhrGet(URI + '/Consultas/allByEspecialidade/' + id, function(data) {

        if (data[0].length == 0) {
            div.innerHTML = 'Não á horario ou medico parar essa especialidade';
            document.getElementById('consulta_form').style.overflow = 'visible';
        }
        else {
            var output = "";
            output += '<table class="centered highlight striped col s12 l12 consultas-table">' +
                '<thead><tr><th></th><th>Médico</th><th>Data</th><th>Hora</th></tr></thead>' +
                '<tbody>';
            CONSULTAS = data[0];
            
            for (var consulta in data[0]) {
                output += '<tr><td><input class="with-gap" name="consultaInfo" type="radio" onclick="saveConsulta(this)" id="' + consulta + '"/><label for="' + consulta + '"></label></td>' +
                    '<td>' + data[0][consulta]["medico"]["nome"] + ' ' + data[0][consulta]["medico"]["sobrenome"] + '</td>' +
                    '<td>' + data[0][consulta]["dia"] + '</td><td>' + data[0][consulta]["horario"] + '</td></tr>';
            }
            output += '</tbody></table><button class="btn waves-effect waves-light" type="button" onclick="confirmarConsulta()">Marcar consulta <i class="material-icons right">send</i></button>';
            div.innerHTML = output;

            document.getElementById('consulta_form').style.overflow = 'scroll';

            $('select').material_select();

        }


    }, function(err) {
        console.log(err);
        showAlert("Ops.. Ocorreu um erro! Tente novamente.", true);
        // div.innerHTML.replace('<div><img style="width:40px;" src="img/loading.gif"/></div>', '');
    })

}

var CONFIRMED_CONSULTA;

function saveConsulta(radio) {
    var id = radio.getAttribute('id');
    CONFIRMED_CONSULTA = CONSULTAS[id];

}

function confirmarConsulta() {
    if (CONFIRMED_CONSULTA) {
        var data = CONFIRMED_CONSULTA;
        data.user = LOGGED_USER;
        xhrPost(URI + '/Consultas/createConsulta', data, function(data) {
            
            if (data.error == false) {
                showAlert(data.msg, false);
                LOGGED_USER = data.user;
                
                setSession('user',JSON.stringify(LOGGED_USER));
                setTimeout(function() {
                    
                    window.location = 'perfil.html'
                }, 1000);
            }else{
                showAlert(data.msg,true);
            }

        }, function(err) {
            showAlert('Um erro ocorreu! Tente novamente.',true)
        })


    }
    else {
        showAlert('Selecione um horario!', true);
    }
}

function putUserInfo() {
    document.getElementById('firstname').value = LOGGED_USER.nome;
    document.getElementById('last_name').value = LOGGED_USER.sobrenome;
    document.getElementById('email').value = LOGGED_USER.email;
    document.getElementById('address').value = LOGGED_USER.endereco;
    document.getElementById('phone').value = LOGGED_USER.telefone;
    document.getElementById('cpf').value = LOGGED_USER.cpf;
    var div = document.getElementById('newInput');
    if (LOGGED_USER.dtype == "Cliente") {
        div.innerHTML = ' <div class="input-field col l6 s12"><input id="rg" type="text" class="validate" name="rg" value=' + LOGGED_USER.rg + '><label for="rg">RG</label></div>';
        maskInputs();
        
        
        //Show Agenda..
        
        var div = document.getElementById('consultas_show');
        if(LOGGED_USER.consultas.length ==0){
            div.innerHTML = "<p>Não há nenhuma consulta marcada!</p>";
        }else{
            var str = '<table class="centered highlight striped"><thead><tr><th>Médico</th><th>Data</th><th>Hora</th><th></th></tr></thead><tbody>';
            
            for(var consulta in LOGGED_USER.consultas){
                
                str += '<tr><td>'+LOGGED_USER.consultas[consulta].medico.nome+' '+LOGGED_USER.consultas[consulta].medico.sobrenome+'</td>'+
                    '<td>'+LOGGED_USER.consultas[consulta].dia+'</td><td>'+LOGGED_USER.consultas[consulta].horario+'</td>'+
                '<td><a style="color:red;" href="javascript:deleteConsulta('+LOGGED_USER.consultas[consulta].id+');"><i class="material-icons">delete</i></a></td></tr>';
                
            }
            str+= '</tbody></table>';
            div.innerHTML = str;
        }
        
        
        
        
    }
    else {
        div.innerHTML = '<img style="width:40px;" src="img/loading.gif"/>';
        xhrGet(URI + '/Especialidades/getAll', function(data) {
            var customHTML = '<div class="input-field col l6 s12">' +
                '<select id="especialidades"><option value="' + LOGGED_USER.especialidade.id + '"  selected>' + LOGGED_USER.especialidade.nome + '</option>';
            for (var esp in data[0]) {
                customHTML += '<option value="' + data[0][esp]['id'] + '">' + data[0][esp]['nome'] + '</option>';
            }
            customHTML += '</select> <label>Esepecialidade</label></div> <div class="input-field col l6 s12"><input id="crm" type="text" class="validate" value="' + LOGGED_USER.crm + '" name="crm"><label for="crm">CRM</label>' +
                '</div></div>';
            document.getElementById('newInput').innerHTML = customHTML;
            $('select').material_select();
            maskInputs();

        }, function(err) {
            console.log(err);
            document.getElementById('newInput').innerHTML = '';
            showAlert('Ops.. ocorreu um erro! Tente novamente.', true);
        });
    }

}



function deleteConsulta(id){
    
    xhrDelete(URI+'/Consultas/delete/'+id+'/'+LOGGED_USER.id,function(data){
    if(!data.error){
        showAlert(data.msg,false);
        LOGGED_USER = data.user;
        setSession('user',JSON.stringify(LOGGED_USER));
        putUserInfo();
    }
    },function(err){
        showAlert('Um erro ocorreu! Tente novamente!',true);
    })
}

function showAlert(msg, error) {
    var div = document.getElementById('alert');
    var classMsg = '';
    if (error) classMsg = 'red darken-2';
    else classMsg = 'teal';
    div.innerHTML = '<div class="card-panel ' + classMsg + ' alert"><span class="white-text">' + msg + '</span>' +
        '<span class="dissmiss-alert" onclick="dissmissAlert(this)"><i class="material-icons">close</i></span></div>';
}





function changePic(element, name) {
    element.setAttribute('src', 'img/social_media/' + name + '.png');
}





function updateUser() {
    var user = LOGGED_USER;
    user.nome = document.getElementById('firstname').value;
    user.sobrenome = document.getElementById('last_name').value;
    user.email = document.getElementById('email').value;
    user.cpf = document.getElementById('cpf').value;
    user.endereco = document.getElementById('address').value;
    user.telefone = document.getElementById('phone').value;
    
    var current_password = document.getElementById('password').value;
    var new_pass = document.getElementById('password2').value;
    var confirm = document.getElementById('password3').value;
    
    if(current_password == LOGGED_USER.senha && new_pass == confirm){
        user.senha = new_pass;
    }

    if (LOGGED_USER.dtype == "Medico") {
        var select = document.getElementById('especialidades');
        user.especialidade = {
            id: select.selectedIndex
        };
        user.crm = document.getElementById('crm').value;
        UPDATE_PATH = "updateUserDoctor";
    }
    else {
        user.rg = document.getElementById('rg').value;
        UPDATE_PATH = "updateUserClient";
    }
    xhrPut(URI + '/users/' + UPDATE_PATH, JSON.stringify(user), function(data) {
        if (!data.error) {
            showAlert(data.msg, false);
            setSession('user', JSON.stringify(data.user));
            makeUserLogged();
        }
        else {
            showAlert(data.msg, true);
        }
    }, function(err) {
        console.log(err);
        showAlert('um erro ocorreu!', true);
    });
}

function deleteUser() {
    LOGGED_USER = JSON.parse(getSession('user'));

    xhrDelete(URI + '/users/removeAccount/'+LOGGED_USER['id'], function(data) {
        deleteSession('user');
        window.location = 'index.html';
    }, function(err) {
        console.log(err);
        showAlert('Ocorreu um erro!', true);
    })

}





function dissmissAlert(span) {

    $(span).parent().fadeOut();
}

function maskInputs() {

    $("#rg").mask("99.999.999-9");
    $("#crm").mask("99999999-9");
    $("#cpf").mask("999.999.999-99");
    $('#phone').mask("(99)9999?9-9999");


}





$(document).ready(function() {
    $('.parallax').parallax();
    $('select').material_select();

    makeChandeOnLoggedIn();

});
