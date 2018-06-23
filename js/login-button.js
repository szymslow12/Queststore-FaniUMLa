function checkIfLoginIsEmpty() {
    var htmlLoginValue;
    htmlLoginValue = document.getElementById("user-login").value;
    if (htmlLoginValue.length == 0 || htmlLoginValue.includes(" ")) {
        document.getElementById("password-label-container").style.top = "-50px";
        document.getElementById("user-login").style.border = "1px solid #33CCCC";
        setBadInputMessage("login-label", "Login cannot contain spaces!");
        return true;
    } else {
        document.getElementById("password-label-container").style.top = "10px";
        setBadInputMessage("login-label", "");
        document.getElementById("user-login").style.border = "none";
        return false;
    }
}


function checkIfPasswordIsEmpty() {
    var htmlLoginValue;
    htmlLoginValue = document.getElementById("user-password").value;
    if (htmlLoginValue.length == 0) {
        setBadInputMessage("password-label", "Password cannot be empty!");
        document.getElementById("user-password").style.border = "1px solid #33CCCC";
        return true;
    } else {
        setBadInputMessage("password-label", "");
        document.getElementById("user-password").style.border = "none";
        return false;
    }
}


function setBadInputMessage(labelName, textToDisplay) {
    var loginLabel = document.getElementById(labelName);
    loginLabel.getElementsByTagName("h5")[0].innerText = textToDisplay;
}


function activateSubmitButton() {
    var a = checkIfLoginIsEmpty();
    var b = checkIfPasswordIsEmpty();
    if (!a && !b) {
        window.alert(a);
        document.getElementById("login-button").setAttribute("disabled", false);
    } else {
        document.getElementById("login-button").disabled = true;
    }
    adjustPositionOfSubmitButton();
}


function loginToQuestStore() {
    var login, password, lol;
    var form = document.getElementById("login");
    login = document.forms["login"]["login"].value;
    password = document.forms["login"]["password"].value;
    // if (login != "test" && password != "test") {
    //     document.getElementById("input-container").style.top = "0px";
    //     document.getElementById("login-head").getElementsByTagName("h5")[0].innerText = "Bad login or password!";
    //     return false;
    // } else
    if (login == "admin" && password == "admin") {
        form.setAttribute("action", "../html/admin/mentors.html");
        return true;
    }
    else if (login == "mentor" && password == "mentor") {
        form.setAttribute("action", "../html/mentor/Students.html");
        return true;
    } else if (login == "student" && password == "student") {
        form.setAttribute("action", "../html/student/Store.html");
        return true;
    } else {
        document.getElementById("input-container").style.top = "0px";
        document.getElementById("login-head").getElementsByTagName("h5")[0].innerText = "Bad login or password!";
        return false;
    }
}


function adjustPositionOfSubmitButton() {
    var login, password;
    login = document.getElementById("login-label").getElementsByTagName("h5")[0].innerText;
    password = document.getElementById("password-label").getElementsByTagName("h5")[0].innerText;
    if (login != "" && password != "") {
        document.getElementById("login-button-container").style.top = "-121px";
    } else if (login != "" || password != "") {
        document.getElementById("login-button-container").style.top = "-60px";
    } else {
        document.getElementById("login-button-container").style.top = "0px";
    }
}