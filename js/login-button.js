function checkIfLoginIsEmpty() {
    var htmlLoginValue;
    htmlLoginValue = document.getElementById("user-login").value;
    if (htmlLoginValue.length <= 0 || htmlLoginValue.includes(" ")) {
        document.getElementById("password-label-container").style.top = "-40px";
        setBadInputMessage("login-label", "Login cannot contain spaces!");
        return true;
    } else {
        document.getElementById("password-label-container").style.top = "-20px";
        setBadInputMessage("login-label", "");
        return false;
    }
}


function checkIfPasswordIsEmpty() {
    var htmlLoginValue;
    htmlLoginValue = document.getElementById("user-password").value;
    if (htmlLoginValue.length <= 0) {
        setBadInputMessage("password-label", "Password cannot be empty");
        return true;
    } else {
        setBadInputMessage("password-label", "");
        return false;
    }
}


function setBadInputMessage(labelName, textToDisplay) {
    var loginLabel = document.getElementById(labelName);
    loginLabel.getElementsByTagName("h5")[0].innerText = textToDisplay;
}


function activateSubmitButton() {
    if (!checkIfLoginIsEmpty() && !checkIfPasswordIsEmpty()) {
        document.getElementById("login-button").disabled = false;
    } else {
        document.getElementById("login-button").disabled = true;
    }
}


function loginToQuestStore() {
    var login, password, lol;
    login = document.forms["login"]["login"].value;
    password = document.forms["login"]["password"].value;
    if (login != "test" && password != "test") {
        document.getElementById("input-container").style.top = "0px";
        document.getElementById("login-head").getElementsByTagName("h5")[0].innerText = "Bad login or password!";
        return false;
    } else {
        return true;
    }
}


function adjustPositionOfSubmitButton() {
    var login, password;
    login = document.getElementById("login-label").getElementsByTagName("h5")[0].innerText;
    password = document.getElementById("password-label").getElementsByTagName("h5")[0].innerText;
    if (login != "" && password != "") {
        document.getElementById("submit-button").style.top = "-30%";
    } else {
        document.getElementById("submit-button").style.top = "-30px";
    }
}