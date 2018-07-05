

function createButtons(array, onClick, isShop) {
    var buttonContainer = document.createElement("DIV");
    buttonContainer.setAttribute("class", "button-container");
    var buttonIDs = ['basic-items', 'magic-items'];
    if (isShop) {
        for (i = 0; i < array.length; i++) {
            buttonContainer.appendChild(createButton(array[i], buttonIDs[i], onClick));
        }
    } else {
        for (i = 0; i < array.length; i++) {
            buttonContainer.appendChild(createButton(array[i], 'discard' + i, onClick));
        }
    }

    document.body.appendChild(buttonContainer);
}

function createButton(name, id, onClick) {
    var button = document.createElement("BUTTON");
    var text = document.createTextNode(name);
    button.appendChild(text);
    button.setAttribute("class", "button category-button");
    button.setAttribute("id", id);
    button.setAttribute("onclick", onClick);
    document.body.appendChild(button);
    return button;
}

function createStoreHeader(coolcoins) {
    var storeHeader = document.createElement("h3");
    storeHeader.setAttribute("class", "store-header")
    var coinSymbol = document.createElement("i");
    coinSymbol.setAttribute('class', "fas fa-dollar-sign");
    var headerText = document.createTextNode("You have " + coolcoins + " ");
    storeHeader.appendChild(headerText);
    storeHeader.appendChild(coinSymbol);
    storeHeader.appendChild(document.createTextNode(" , you can buy following items:"));
    document.body.appendChild(storeHeader);
}
