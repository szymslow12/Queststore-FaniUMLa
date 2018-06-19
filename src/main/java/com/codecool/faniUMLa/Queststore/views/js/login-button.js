function checkIfLoginIsEmpty() {
    var htmlLoginValue;
    htmlLoginValue = document.getElementById("user-login").value;
    if (htmlLoginValue.length <= 0 || htmlLoginValue.includes(" ")) {
        setBadInputMessage("login-label", "Login cannot contain spaces!");
        return true;
    } else {
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