function register() {
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    let firstname = document.getElementById('firstname').value;
    let lastname = document.getElementById('lastname').value;
    let email = document.getElementById('email').value;
    let role = document.getElementById('role').value;
    let roleId;
    
    if(role === 'Employee'){
        roleId = 1;
    }
    else{
        roleId = 2;
    }
    
    let newUser = {
        "username": username,
        "password": password,
        "firstName": firstname,
        "lastName": lastname,
        "email": email,
        "role": roleId
    }
    
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8080/auth-demo/users/register");
    xhr.onreadystatechange = function() {
        if(xhr.status >= 200 && xhr.status < 300){
            alert('Registed!');
            window.location.href = 'login.html';
        }
        else{
            alert('Please try again');
        }
    }
    xhr.send(JSON.stringify(newUser));
}