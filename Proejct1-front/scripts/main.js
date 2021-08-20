let token = sessionStorage.getItem("token");
console.log(token);
if(!token){
    window.location.href="login.html";
}

function logout() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8080/auth-demo/auth/logout');
    xhr.onreadystatechange = function() {
        console.log(xhr.status);
        if(xhr.status == 200){
            window.location = 'login.html';
        }
        else {
            alert('failed to logout');
        }
    }
    xhr.send();
}


