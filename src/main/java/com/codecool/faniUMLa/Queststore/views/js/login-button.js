
function checkIfLoginIsEmpty() {
    var htmlLoginValue;
    htmlLoginValue = document.getElementById("user-login").value;
    if (htmlLoginValue.length <= 0 || htmlLoginValue.includes(" ")) {
        setBadInputMessage("login-label", "Login cannot contain spaces!")
    } else {
        setBadInputMessage("login-label", "");
    }
}


function setBadInputMessage(labelName, textToDisplay) {
    var loginLabel = document.getElementById(labelName);
    loginLabel.getElementsByTagName("h5")[0].innerText = textToDisplay;
}