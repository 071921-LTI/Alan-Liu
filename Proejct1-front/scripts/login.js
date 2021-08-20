document.getElementById("submitButton").addEventListener("click", login);

function login(){

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let xhr = new XMLHttpRequest();
    
    xhr.open("POST", "http://localhost:8080/auth-demo/auth");

    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            let authToken = xhr.getResponseHeader("Authorization");
            sessionStorage.setItem("token", authToken);
            let role = authToken.split(':',2)[1];
            if(role == 1){
                 window.location.href="dashboard.html"; 
            }
            else{
                 window.location.href="ManagerDashboard.html";
            }

        } else if (xhr.readyState === 4){
            alert('Username or Password is Incorrect');
        }
    } 

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let requestBody = `username=${username}&password=${password}`;
    xhr.send(requestBody);
}