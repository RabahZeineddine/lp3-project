/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CREATE_PATH;
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

    if (USER_TYPE.localeCompare("medico")==0) {
        var select = document.getElementById('especialidades');
        alert(select.options.selectedIndex);
        user.especialidade =  {id:select.options[select.selectedIndex], nome: document.getElementById('especialidades').innerHTML};
        user.crm = document.getElementById('crm').value;
        CREATE_PATH = "createUserDoctor";
    } else {
        user.rg = document.getElementById('rg').value;
        CREATE_PATH = "createUserClient";
    }

    alert(JSON.stringify(user));
//        consultas : [{horario: new Date(),dataConsulta: new Date(),status: "Done"},{horario: new Date(),dataConsulta: new Date(),status: "Done"}]};

    xhrPut('/ClinicWEB/UsersManager/users/' + CREATE_PATH, JSON.stringify(user), function (data) {
        if(!data.error){
            alert(data.msg);
        }else{
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
        xhrGet('/ClinicWEB/UsersManager/Especialidades/getAll',function(data){
            
                    var select = '<select id="especialidades"><option value="">Selecione uma especialidade</option>';
            
            for(var esp in data[0]){
                
                select+='<option value="'+data[0][esp]['id']+'">'+data[0][esp]['nome']+'</option>';
            }
            select+='</select><br><input type="text" id="crm" placeholder="CRM"/>';
            document.getElementById('newInput').innerHTML = select;
        },function(err){
            console.log(err);
            alert('um erro ocorreu!');
        });
//        document.getElementById('newInput').innerHTML = '<br><input type="text" id="crm" placeholder="CRM"/>';
    } else {
        document.getElementById('newInput').innerHTML = '<br><input type="text" id="rg" placeholder="RG"/>';
    }
}


function loginUser() {

    var user = {email: document.getElementById('login_email').value,
        senha : document.getElementById('login_password').value};
    alert(JSON.stringify(user));
    xhrPost('/ClinicWEB/UsersManager/users/login',user,function(data){
        if(!data.error && data.authorized){
            alert(data.msg);
            LOGGED_USER = data.user;
            makeUserLogged();
        }else{
            alert(data.msg);
        }
    },function(err){
        console.log(err);
        
    })

}

function makeUserLogged(){
    alert(JSON.stringify(LOGGED_USER));
    var update_form = document.getElementById('update_form');
    update_form.innerHTML = '<input type="text" name="nome" id="nome"  placeholder="nome" value="'+LOGGED_USER.nome+'"/>'+
          '<input type="text" name="sobrenome" id="sobrenome" placeholder="sobrenome" value="'+LOGGED_USER.sobrenome+'"/></br>'+
            '<input type="email" id="email" placeholder="email" value="'+LOGGED_USER.email+'"/></br>'+
            '<input type="password" id="senha" placeholder="senha"/></br>'+
            '<input type="text" id="cpf" placeholder="cpf" value="'+LOGGED_USER.cpf+'"/></br>'+
            '<input type="text" id="endereco" placeholder="endereco" value="'+LOGGED_USER.endereco+'"/></br>'+
            '<input type="text" id="telefone" placeholder="telefone" value="'+LOGGED_USER.telefone+'"/></br>';
            if(user.dtype == "medico"){
                update_form.innerHTML +='<input type="text" id="crm" placeholder="CRM" value="'+LOGGED_USER.crm+'"/>'+
                        '<p>'+LOGGED_USER.especialidade.nome+'</p>';
            }else{
                update_form.innerHTML +='<input type="text" id="rg" placeholder="RG" value="'+LOGGED_USER.rg+'"/>';
            }
            update_form.innerHTML+='<br><input type="button" value="Atualizar" id="update-btn" onclick="updateUser()"/>';  
            
    document.getElementById('logout_button').innerHTML = '<button onclick="logout()">Logout</button>';
    document.getElementById('delete_button').innerHTML = '<button onclick="deleteUser()">Delete</button>';;
    
}

function logout(){
    alert('logout');
}
function deleteUser(){
    alert('delete');
    
}