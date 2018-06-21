

function createStoreButtons(array) {
    var buttonContainer = document.createElement("DIV");
    buttonContainer.setAttribute("class", "button-container");
    for (i = 0; i < array.length; i++) {
        buttonContainer.appendChild(createButton(array[i]));
    }

    document.body.appendChild(buttonContainer);
}

function createButton(name) {
    var button = document.createElement("BUTTON");
    var text = document.createTextNode(name);
    button.appendChild(text);
    button.setAttribute("class", "button");
    button.setAttribute("onclick", "createStoreTable(['Name', 'Description', 'Price', 'Buy'])");
    document.body.appendChild(button);
    return button;
}