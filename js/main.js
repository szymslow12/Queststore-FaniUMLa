function createHeader(name) {

    var header = document.createElement("header");
    header.textContent = "Welcome " + name;
    document.body.appendChild(header);
}

function createMenu(array, activePage) {

    var menu = document.createElement("div");
    menu.setAttribute("class", "menu");
    document.body.appendChild(menu);

    for (var i = 0; i < array.length - 1; i++) {

        var a = document.createElement("a");
        a.setAttribute("href", array[i] + ".html");
        a.textContent = array[i];


        if (activePage == array[i]) {
            a.setAttribute("class", "active");
        }
        menu.appendChild(a);
    }
    createHiddenMenu(menu);
}

function createHiddenMenu(menu) {
    var options = ["Profile", "Log-out"];
    var div = document.createElement("div");
    div.setAttribute("class", "dropdown");
    menu.appendChild(div);
    var a = document.createElement("a");
    a.setAttribute("class", "fas fa-cog");
    a.setAttribute("id", "icon")
    div.appendChild(a);
    var dropdownContent = document.createElement("div");
    dropdownContent.setAttribute("class", "dropdown-content");

    for (var i = 0; i < options.length; i++) {
        var a2 = document.createElement("a");
        a2.textContent = options[i];
        if (options[i] == "Log-out") {
            a2.setAttribute("href", "../login.html");
        } else {
            a2.setAttribute("href", "../" + options[i] + ".html");
        }
        dropdownContent.appendChild(a2);
    }
    div.appendChild(dropdownContent);
    li.appendChild(div);
}

function includeFooter() {
    var footer = document.createElement("footer");
    var text = document.createTextNode("\u00A9 FaniUMLa");
    footer.appendChild(text);
    document.body.appendChild(footer);
}

function createButton(name) {
    var button = document.createElement("button");
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

    for (var i = 0; i < array.length; i++) {
        var col = document.createElement("td");
        col.textContent = array[i];
        rowH.appendChild(col);
    }
    table.appendChild(rowH);

    var imgDict = {
        "See Details": "fas fa-address-book",
        "Update": "fas fa-user-edit",
        "Delete": "fas fa-trash-alt",
        "Buy": "fas fa-shopping-cart"
    };

    for (var j = 0; j < 10; j++) {
        var row = document.createElement("tr");
        row.setAttribute("class", "tableRow")
        for (var i = 0; i < array.length; i++) {

            if (array[i] in imgDict) {
                var data = document.createElement("td");
                var button = createFormButton("", array, ["test"]);
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
        input.setAttribute("required", "");
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

function createStoreTable(array, id) {
    var tables = document.getElementsByTagName("table");
    if (tables.length == 0) {
        createTable(array);
        fillStoreTable(id);
    } else {
        var table = tables[0];
        table.remove();
        createTable(array);
        fillStoreTable(id);
    }
}


function fillStoreTable(id) {
    if (id == "basic-items") {
        var basicItems = [['Combat training', 'Private mentoring', '50 cc'],
                          ['Sanctuary', 'You can spend a day in home office', '300 cc'],
                          ['Time Travel', 'Extend SI week assignment deadline by one day', '500 cc']];
        fillRows(basicItems, basicItems[0].length);
    } else {
        var magicItems = [['Circle of Sorcery', '60 min workshop by a mentor(s) of the chosen topic', '1000 cc'],
                          ['Summon Code Elemental', "Mentor joins a students' team for a one hour", '1000 cc'],
                          ['Tome of knowledge', 'Extra material for the current topic', '500 cc'],
                          ['Transform mentors', 'All mentors should dress up as pirates (or just funny) for the day', '5000 cc'],
                          ['Teleport', 'The whole course goes to an off-school program instead for a day', '30000 cc']];
        fillRows(magicItems, magicItems[0].length);
    }
}

function fillRows(columnsData, columnsToFill) {
    var rows = document.getElementsByClassName("tableRow");
    for (i = 0; i < columnsData.length; i++) {
        var row = rows[i];
        var columns = row.getElementsByTagName("td");
        for (j = 0; j < columnsToFill; j++) {
            columns[j].textContent = columnsData[i][j];
        }
    }
}