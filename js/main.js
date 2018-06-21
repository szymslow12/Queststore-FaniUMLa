function createHeader(name) {

    var header = document.createElement("HEADER");
    header.textContent = "Welcome " + name;
    document.body.appendChild(header);
}



function createMenu(array, activePage) {

    var nav = document.createElement("NAV");
    nav.setAttribute("class", "menu");

    var ul = document.createElement("ul");
    nav.appendChild(ul);

    for (var i = 0; i < array.length; i++) {
        var li = document.createElement("li");

        var a = document.createElement("a");
        a.setAttribute("href", array[i] + ".html");
        a.textContent = array[i];

        if(activePage == array[i]) {
            a.setAttribute("class", "active");
        }

        li.appendChild(a);

        ul.appendChild(li);
    }

    document.body.appendChild(nav);
}

function includeFooter() {
    var footer = document.createElement("footer");
    var text = document.createTextNode("\u00A9 FaniUMLa");
    footer.appendChild(text);
    document.body.appendChild(footer);
}

function createButton(name) {
    var button = document.createElement("BUTTON");
    var text = document.createTextNode(name);
    button.appendChild(text);
    button.setAttribute("class", "button");
    document.body.appendChild(button);
    return button;
}

function createTable(array) {
    var table = document.createElement("table");
    var rowH = document.createElement("tr");
    rowH.setAttribute("class", "tableHeader")
    
    for (var i=0; i<array.length; i++) {
        var col = document.createElement("td");
        col.textContent = array[i];
        rowH.appendChild(col);
    }
    table.appendChild(rowH);

    var imgDict = {
        "See Details" : "fas fa-address-book",
        "Update" : "fas fa-user-edit",
        "Delete" : "fas fa-trash-alt",
        "Buy" : "fas fa-shopping-cart"
    };

    for(var j = 0; j<10; j++) {
        var row = document.createElement("tr");
        row.setAttribute("class", "tableRow")
        for (var i=0; i<array.length; i++) {
        var formArray = getArrayForForm(view);
            
            if(array[i] in imgDict) {
                var data = document.createElement("td");
                var button = createFormButton("", formArray, []);
                button.setAttribute("class", imgDict[array[i]]);
                data.appendChild(button);

            } else {
                var data = document.createElement("td");
                data.textContent = "-----------------";
            }
            row.appendChild(data);
        } 
        table.appendChild(row);
    } 
    document.body.appendChild(table);
}

function getArrayForForm(view) {
    var formArray = ["First Name", "Last Name", "Phone", "Class", "Email"];
        if(view == "Mentor") {
            formArray.push("Wallet")

        } else if(view =="Classes" || view =="Levels") {
            formArray=["Name"];
        }
        if(view=="Levels") {
            formArray.push("Level Threshold");
        }

    return formArray;
}

function createFormButton(name, inputsArray, optionsArray) {
    
    var button = createButton(name);

    button.addEventListener("click", function () { handleForm(inputsArray, optionsArray) })
    document.body.appendChild(button);

    return button;
}

function handleForm(inputsArray, optionsArray) {
    createForm(inputsArray, optionsArray);
    displayForm();
}

function displayForm() {
    var div = document.createElement("div");
    div.setAttribute("class", "form-container");
    var form = document.getElementById("form");
    div.appendChild(form);
    document.body.appendChild(div);
}

function createForm(inputsArray, optionsArray) {
    var form = document.createElement("form");
    form.setAttribute("id", "form");

    var container = document.createElement("div");
    container.setAttribute("class", "container");
    createInputElements(container, inputsArray);
    createSelectElements(container, optionsArray);

    form.appendChild(container);

    var button = createButton("Save");
    button.setAttribute("class", "button save-button");
    form.appendChild(button);
    document.body.appendChild(form);
}

function createInputElements(container, inputsArray) {
    for (var i = 0; i < inputsArray.length; i++) {
        var div = document.createElement("div");

        var label = document.createElement("label");
        div.appendChild(label);
        label.textContent = inputsArray[i];

        var input = document.createElement("input");
        div.appendChild(input);

        container.appendChild(div);
    }
}

function createSelectElements(container, optionsArray) {
    for (var i = 0; i < optionsArray.length; i++) {

        var div = document.createElement("div");
        var label = document.createElement("label");
        div.appendChild(label);
        label.textContent = optionsArray[i];

        var select = document.createElement("select");

        var option = document.createElement("option");
        option.value = optionsArray[i];
        option.text = optionsArray[i];
        select.appendChild(option);
        div.appendChild(select);
        container.appendChild(div);
    }
}