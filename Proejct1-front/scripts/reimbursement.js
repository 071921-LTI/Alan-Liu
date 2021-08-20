function myProfile() {
    let xhr = new XMLHttpRequest(); 
    let token = sessionStorage.getItem("token");
    let userId = token.split(':',2)[0];
    let url = 'http://localhost:8080/auth-demo/users?userId='+userId;
    let txt = '';
    xhr.open('GET', url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let user = JSON.parse(xhr.responseText);
            if(user.role === 1){
                user.role = 'Employee';
            }

            else{
                user.role = 'Manager';
            }
            txt += "<td>" + user.id + "</td>";
            txt += "<td>" + user.username + "</td>";
            txt += "<td>" + user.firstName + "</td>";
            txt += "<td>" + user.lastName + "</td>";
            txt += "<td>" + user.email + "</td>";
            txt += "<td>" + user.role + "</td>";
            txt += "</tr>"; 
            document.getElementById("table2").innerHTML = txt;
        }
    }
    xhr.send();
}

function AllUsers() {
    let xhr = new XMLHttpRequest(); 

    let url = 'http://localhost:8080/auth-demo/users/All';
    xhr.open('GET', url);
    let txt = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let user = JSON.parse(xhr.responseText);
            for (x in user) {
                if(user[x].role === 1){
                    user[x].role = 'Employee';
                }

                else{
                    user[x].role = 'Manager';
                }
                txt += "<td>" + user[x].id + "</td>";
                txt += "<td>" + user[x].username + "</td>";
                txt += "<td>" + user[x].firstName + "</td>";
                txt += "<td>" + user[x].lastName + "</td>";
                txt += "<td>" + user[x].email + "</td>";
                txt += "<td>" + user[x].role + "</td>";
                txt += "</tr>"; 
                document.getElementById("table2").innerHTML = txt;
            }
        }
    }
    xhr.send();
}

function submitReimbursement() {
    let type = document.getElementById('type').value;
    let typeId;
    if(type === 'Lodging'){
        typeId = 1;
    }
    else if(type === 'Travel'){
        typeId = 2;
    }
    else if(type === 'Food'){
        typeId = 3;
    }
    else{
        typeId = 4;
    }
    let token = sessionStorage.getItem("token");

    let status = 1;
    let resolver = null;
    let amount = document.getElementById('amount').value;
    let description = document.getElementById('description').value;
    let submitted = Date.now();
    let authorId = token.split(':',2)[0];

    let newReimbursement = {
        "amount": amount,
        "submitted": submitted,
        "resolved": null,
        "description": description,
        "authorId": authorId,
        "resolverId": resolver,
        "status": status,
        "type": typeId,
    }
    
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8080/auth-demo/reimbursement/add");
    xhr.onreadystatechange = function() {
        if(xhr.status >= 200 && xhr.status < 300){
            console.log('Added new reimbursement');
            window.location.reload(true);

        }
        else {
            console.log('Unable to add the reimbursement');
        }
    }
    
    xhr.send(JSON.stringify(newReimbursement)); 
}

function displayByUser() {
    let xhr = new XMLHttpRequest(); 
    let token = sessionStorage.getItem("token");
    let userId = token.split(':',2)[0];
    let url = 'http://localhost:8080/auth-demo/reimbursement/user?userId='+userId;
    xhr.open('GET', url);
    let txt = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let reimbursement = JSON.parse(xhr.responseText);
            
            for (x in reimbursement) {
                let status = '';
                let d = new Date(parseInt(reimbursement[x].submitted));
                reimbursement[x].submitted = d.toLocaleDateString();
                
                if(reimbursement[x].resolved != null){
                    let rd = new Date(parseInt(reimbursement[x].resolved));
                    reimbursement[x].resolved = rd.toLocaleDateString();
                }
                else {
                    reimbursement[x].resolved = 'Unresolved';
                    reimbursement[x].resolverId = 'Unresolved';
                }
                
                if(reimbursement[x].status === 1){
                    reimbursement[x].status = 'Pending';
                }
                else if(reimbursement[x].status === 2){
                    reimbursement[x].status = 'Approved';
                    status = 'approved';
                }
                else{
                    reimbursement[x].status = 'Denied';
                    status = 'denied';
                }
                
                if(reimbursement[x].type === 1){
                    reimbursement[x].type = 'Lodging';
                }
                else if(reimbursement[x].type === 2){
                    reimbursement[x].type = 'Travel';
                }
                    else if(reimbursement[x].type === 3){
                    reimbursement[x].type = 'Food';
                }
                else{
                    reimbursement[x].type = 'Other';
                }
                txt += "<tr class= " + status +">";
                txt += "<td>" + reimbursement[x].id + "</td>";
                txt += "<td>" + "$" + reimbursement[x].amount + "</td>";
                txt += "<td>" + reimbursement[x].submitted + "</td>";
                txt += "<td>" + reimbursement[x].resolved + "</td>";
                txt += "<td>" + reimbursement[x].description + "</td>";
                txt += "<td>" + reimbursement[x].authorId + "</td>";
                txt += "<td>" + reimbursement[x].resolverId + "</td>";
                txt += "<td>" + reimbursement[x].status + "</td>";
                txt += "<td>" + reimbursement[x].type + "</td>";
                txt += "</tr>"; 
                document.getElementById("table").innerHTML = txt;
            }
        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send();
}

function displayByAll() {
    let xhr = new XMLHttpRequest(); 
    xhr.open('GET', 'http://localhost:8080/auth-demo/reimbursement/all');
    let txt = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let reimbursement = JSON.parse(xhr.responseText);
            
            for (x in reimbursement) {
                let status = '';
                let d = new Date(parseInt(reimbursement[x].submitted));
                reimbursement[x].submitted = d.toLocaleDateString();
                
                if(reimbursement[x].resolved != null){
                    let rd = new Date(parseInt(reimbursement[x].resolved));
                    reimbursement[x].resolved = rd.toLocaleDateString();
                }
                else {
                    reimbursement[x].resolved = 'Unresolved';
                    reimbursement[x].resolverId = 'Unresolved';
                }
                
                if(reimbursement[x].status === 1){
                    reimbursement[x].status = 'Pending';
                }
                else if(reimbursement[x].status === 2){
                    reimbursement[x].status = 'Approved';
                    status = 'approved';
                }
                else{
                    reimbursement[x].status = 'Denied';
                    status = 'denied';
                }
                
                if(reimbursement[x].type === 1){
                    reimbursement[x].type = 'Lodging';
                }
                else if(reimbursement[x].type === 2){
                    reimbursement[x].type = 'Travel';
                }
                    else if(reimbursement[x].type === 3){
                    reimbursement[x].type = 'Food';
                }
                else{
                    reimbursement[x].type = 'Other';
                }
                txt += "<tr class= " + status +">";
                txt += "<td>" + reimbursement[x].id + "</td>";
                txt += "<td>" + "$" + reimbursement[x].amount + "</td>";
                txt += "<td>" + reimbursement[x].submitted + "</td>";
                txt += "<td>" + reimbursement[x].resolved + "</td>";
                txt += "<td>" + reimbursement[x].description + "</td>";
                txt += "<td>" + reimbursement[x].authorId + "</td>";
                txt += "<td>" + reimbursement[x].resolverId + "</td>";
                txt += "<td>" + reimbursement[x].status + "</td>";
                txt += "<td>" + reimbursement[x].type + "</td>";
                txt += "</tr>"; 
                document.getElementById("table").innerHTML = txt;
            }
        }
    }
    xhr.send();
}

function approveOrDeny() {
    let choice = document.getElementById('statu').value;
    let reimbId = document.getElementById('reimbId').value;
    let statusId;
    let token = sessionStorage.getItem("token");
    let resolverId = token.split(':',2)[0];
    if(choice == 'Approve'){
        statusId = 2;
    }
    else if (choice == 'Deny') {
        statusId = 3;
    }
    
    let reimbursement = {
        "id": reimbId,
        "status": statusId,
        "resolverId":resolverId
    }
    let xhr = new XMLHttpRequest();
    if(statusId == 2){
        xhr.open('POST', 'http://localhost:8080/auth-demo/reimbursement/approve');
    }
    else {
        xhr.open('POST', 'http://localhost:8080/auth-demo/reimbursement/deny');
    }
    xhr.onreadystatechange = function()  {
        if(xhr.status >= 200 && xhr.status < 300) {
            window.location.reload(true);
        }
        else {
            alert('Unable to change the status. Please try again later.');
        }
    }

    xhr.send(JSON.stringify(reimbursement));

}


function filerByPending() {
    let xhr = new XMLHttpRequest(); 
    xhr.open('GET', 'http://localhost:8080/auth-demo/reimbursement/pending');
    let txt = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let reimbursement = JSON.parse(xhr.responseText);
            if (reimbursement.length === 0){
                document.getElementById("table").innerHTML = txt;
            }
            for (x in reimbursement) {
                let status = '';
                let d = new Date(parseInt(reimbursement[x].submitted));
                reimbursement[x].submitted = d.toLocaleDateString();
                
                if(reimbursement[x].resolved != null){
                    let rd = new Date(parseInt(reimbursement[x].resolved));
                    reimbursement[x].resolved = rd.toLocaleDateString();
                }
                else {
                    reimbursement[x].resolved = 'Unresolved';
                    reimbursement[x].resolverId = 'Unresolved';
                }
                
                if(reimbursement[x].status === 1){
                    reimbursement[x].status = 'Pending';
                }
                
                if(reimbursement[x].type === 1){
                    reimbursement[x].type = 'Lodging';
                }
                else if(reimbursement[x].type === 2){
                    reimbursement[x].type = 'Travel';
                }
                    else if(reimbursement[x].type === 3){
                    reimbursement[x].type = 'Food';
                }
                else{
                    reimbursement[x].type = 'Other';
                }
                txt += "<tr class= " + status +">";
                txt += "<td>" + reimbursement[x].id + "</td>";
                txt += "<td>" + "$" + reimbursement[x].amount + "</td>";
                txt += "<td>" + reimbursement[x].submitted + "</td>";
                txt += "<td>" + reimbursement[x].resolved + "</td>";
                txt += "<td>" + reimbursement[x].description + "</td>";
                txt += "<td>" + reimbursement[x].authorId + "</td>";
                txt += "<td>" + reimbursement[x].resolverId + "</td>";
                txt += "<td>" + reimbursement[x].status + "</td>";
                txt += "<td>" + reimbursement[x].type + "</td>";
                txt += "</tr>"; 
                document.getElementById("table").innerHTML = txt;
            }
        }
    }
    xhr.send();
}


function filterByResolved() {
    let xhr = new XMLHttpRequest(); 
    xhr.open('GET', 'http://localhost:8080/auth-demo/reimbursement/resolved');
    let txt = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let reimbursement = JSON.parse(xhr.responseText);
            if (reimbursement.length === 0){
                document.getElementById("table").innerHTML = txt;
            }
            for (x in reimbursement) {
                let status = '';
                let d = new Date(parseInt(reimbursement[x].submitted));
                reimbursement[x].submitted = d.toLocaleDateString();
                
                if(reimbursement[x].resolved != null){
                    let rd = new Date(parseInt(reimbursement[x].resolved));
                    reimbursement[x].resolved = rd.toLocaleDateString();
                }
                else {
                    reimbursement[x].resolved = 'Unresolved';
                    reimbursement[x].resolverId = 'Unresolved';
                }
                
                if(reimbursement[x].status === 1){
                    reimbursement[x].status = 'Pending';
                }
                else if(reimbursement[x].status === 2){
                    reimbursement[x].status = 'Approved';
                    status = 'approved';
                }
                else{
                    reimbursement[x].status = 'Denied';
                    status = 'denied';
                }
                
                if(reimbursement[x].type === 1){
                    reimbursement[x].type = 'Lodging';
                }
                else if(reimbursement[x].type === 2){
                    reimbursement[x].type = 'Travel';
                }
                    else if(reimbursement[x].type === 3){
                    reimbursement[x].type = 'Food';
                }
                else{
                    reimbursement[x].type = 'Other';
                }
                txt += "<tr class= " + status +">";
                txt += "<td>" + reimbursement[x].id + "</td>";
                txt += "<td>" + "$" + reimbursement[x].amount + "</td>";
                txt += "<td>" + reimbursement[x].submitted + "</td>";
                txt += "<td>" + reimbursement[x].resolved + "</td>";
                txt += "<td>" + reimbursement[x].description + "</td>";
                txt += "<td>" + reimbursement[x].authorId + "</td>";
                txt += "<td>" + reimbursement[x].resolverId + "</td>";
                txt += "<td>" + reimbursement[x].status + "</td>";
                txt += "<td>" + reimbursement[x].type + "</td>";
                txt += "</tr>"; 
                document.getElementById("table").innerHTML = txt;
            }
        }
    }
    xhr.send();
}


function filterByUser() {
    let xhr = new XMLHttpRequest(); 
    let token = sessionStorage.getItem("token");
    let id = document.getElementById("Eid").value;
    let url = 'http://localhost:8080/auth-demo/reimbursement/user?userId='+id;
    xhr.open('GET', url);
    let txt = '';
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let reimbursement = JSON.parse(xhr.responseText);
            if (reimbursement.length === 0){
                document.getElementById("table").innerHTML = txt;
            }
            for (x in reimbursement) {
                let status = '';
                let d = new Date(parseInt(reimbursement[x].submitted));
                reimbursement[x].submitted = d.toLocaleDateString();
                
                if(reimbursement[x].resolved != null){
                    let rd = new Date(parseInt(reimbursement[x].resolved));
                    reimbursement[x].resolved = rd.toLocaleDateString();
                }
                else {
                    reimbursement[x].resolved = 'Unresolved';
                    reimbursement[x].resolverId = 'Unresolved';
                }
                
                if(reimbursement[x].status === 1){
                    reimbursement[x].status = 'Pending';
                }
                else if(reimbursement[x].status === 2){
                    reimbursement[x].status = 'Approved';
                    status = 'approved';
                }
                else{
                    reimbursement[x].status = 'Denied';
                    status = 'denied';
                }
                
                if(reimbursement[x].type === 1){
                    reimbursement[x].type = 'Lodging';
                }
                else if(reimbursement[x].type === 2){
                    reimbursement[x].type = 'Travel';
                }
                    else if(reimbursement[x].type === 3){
                    reimbursement[x].type = 'Food';
                }
                else{
                    reimbursement[x].type = 'Other';
                }
                txt += "<tr class= " + status +">";
                txt += "<td>" + reimbursement[x].id + "</td>";
                txt += "<td>" + "$" + reimbursement[x].amount + "</td>";
                txt += "<td>" + reimbursement[x].submitted + "</td>";
                txt += "<td>" + reimbursement[x].resolved + "</td>";
                txt += "<td>" + reimbursement[x].description + "</td>";
                txt += "<td>" + reimbursement[x].authorId + "</td>";
                txt += "<td>" + reimbursement[x].resolverId + "</td>";
                txt += "<td>" + reimbursement[x].status + "</td>";
                txt += "<td>" + reimbursement[x].type + "</td>";
                txt += "</tr>"; 
                document.getElementById("table").innerHTML = txt;
            }
        }
    }
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send();
}


function updateEmail() {
    let xhr = new XMLHttpRequest(); 
    let token = sessionStorage.getItem("token");
    let userId = token.split(':',2)[0];
    let email = document.getElementById("Email").value;
    let url = 'http://localhost:8080/auth-demo/users/update/email?userId='+userId+'&Email='+email;
    let txt = '';
    xhr.open('POST', url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            window.location.reload(true);
        }
    }
    xhr.send();
}

function updatePassword() {
    let xhr = new XMLHttpRequest(); 
    let token = sessionStorage.getItem("token");
    let userId = token.split(':',2)[0];
    let password = document.getElementById("pw").value;
    let url = 'http://localhost:8080/auth-demo/users/update/password?userId='+userId+'&password='+password;
    let txt = '';
    xhr.open('POST', url);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            window.location.reload(true);
        }
    }
    xhr.send();
}