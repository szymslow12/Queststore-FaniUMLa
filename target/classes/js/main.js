function createHeader(name) {

    var header = document.createElement("header");
    header.textContent = "Welcome " + name;
    document.body.appendChild(header);
}

function createMenu(array, activePage) {

    hideMenu();
    var menu = document.createElement("div");
    menu.setAttribute("class", "menu");
    menu.setAttribute("id", "menu");
    document.body.appendChild(menu);

    for (var i = 0; i < array.length; i++) {

        var a = document.createElement("a");
        //        a.setAttribute("href", array[i] + ".html");
        a.setAttribute("href", array[i]);
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
            a2.setAttribute("onclick", "seeProfile();");
        }
        dropdownContent.appendChild(a2);
    }
    div.appendChild(dropdownContent);
}

function seeProfile() {

    var div = document.createElement("div");
    div.setAttribute("class", "form-container");
    var form = document.createElement("form");
    div.appendChild(form);
    var formArray = ["First Name", "Last Name", "Phone", "Email", "Login", "Password", "Confirm Password"];
    document.body.appendChild(div);
    var container = document.createElement("div");
    container.setAttribute("class", "container");
    container.setAttribute("id", "profile-container");
    form.appendChild(container);

    for (var i = 0; i < formArray.length; i++) {
        var labelDiv = document.createElement("div");
        container.appendChild(labelDiv);
        var label = document.createElement("label");
        label.textContent = formArray[i];
        labelDiv.appendChild(label);
        var input = document.createElement("input");
        input.value = formArray[i];
        labelDiv.appendChild(input);

        if (i === 0 || i === 1 || i === 4) {
            input.setAttribute("readOnly", true);
            input.style.backgroundColor = "grey";
        } else {
            input.addEventListener("click", function () { this.setAttribute('required', true) });
            input.addEventListener("click", function () { this.value = '' });
        }
    }
    var button = createButton("Save", );
    form.appendChild(button);
    button.setAttribute("class", "button functionButton");
    fullfillInputs("profile-container", "/DAOUserController?method=Profile");
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
function hideMenu() {
    window.onscroll = function () {
        var navbar = document.getElementById("menu");
        var sticky = navbar.offsetTop;
        if (window.pageYOffset >= sticky) {
            navbar.classList.add("sticky");
        } else {
            navbar.classList.remove("sticky");
        }
    }
}

function createTable(array, view) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var imgDict = {
                "See Details": "fas fa-address-book",
                "Update": "fas fa-user-edit",
                "Delete": "fas fa-trash-alt",
                "Buy": "fas fa-shopping-cart",
                "Give": "fas fa-gift"
            };
            var entries = JSON.parse(this.response);
            var table = document.createElement("table");
            var rowH = document.createElement("tr");
            rowH.setAttribute("class", "tableHeader")

            //set header for table
            for (var i = 0; i < array.length; i++) {
                var col = document.createElement("td");
                col.textContent = array[i];
                rowH.appendChild(col);
            }
            table.appendChild(rowH);

            for (x in entries) {
                var row = document.createElement("tr");
                row.setAttribute("class", "tableRow");
                for (var i = 0; i < array.length; i++) {
                    var formArray = getArrayForForm(view);
                    var selectArray = getSelectArray(view);
                    if (array[i] in imgDict && array[i] != "Delete" && array[i] != "Buy" && array[i] != "Give") {
                        var data = document.createElement("td");
                        if (array[i] == "See Details") {
                            var button = createFormButton("", formArray, selectArray, true, view, x);
                        } else {
                            var button = createFormButton("", formArray, selectArray, false, view, x);
                        }
                        button.setAttribute("class", imgDict[array[i]] + " functionButton");
                        data.appendChild(button);
                    } else if (array[i] == "Delete") {
                        var data = document.createElement("td");
                        var button = createSubmitButton("delete", view, row.firstChild.textContent);
                        button.setAttribute("class", imgDict[array[i]] + " functionButton");

                        data.appendChild(button);

                    } else if (array[i] == "Buy") {
                        var data = document.createElement("td");
                        var button = createSubmitButton("buy");
                        button.setAttribute("class", imgDict[array[i]] + " functionButton");

                        data.appendChild(button);

                    } else if (array[i] == "Give") {
                        var data = document.createElement("td");
                        var button = createFormButton("", [], ["Class"]);
                        button.setAttribute("class", imgDict[array[i]] + " functionButton");
                        data.appendChild(button);

                    } else {
                        var data = document.createElement("td");
                        data.textContent = entries[x][array[i]];
                    }
                    row.appendChild(data);
                }
                table.appendChild(row);
            }
            document.getElementById("table_content").appendChild(table);
        };
    }
    var container = document.createElement("div");
    container.setAttribute("id", "table_content");
    document.body.appendChild(container);
    switch (view) {
        case "Mentors":
        case "Levels":
        case "Classes":
            xhttp.open("GET", "/daoAdminController?method=" + view, true);
            xhttp.send();
            break;
        case "Students":
        case "Quests":
        case "Artifacts":
            xhttp.open("GET", "/daoMentorController?method=" + view, true);
            xhttp.send();
            break;
        case "Inventory":
        case "Quests":
        case "Store":
        case "Discard":
            xhttp.open("GET", "/daoStudentController?method=" + view, true);
            xhttp.send();
            break;
    }
}

function getSelectArray(view) {
    var selectArray = [];
    if (view == "Mentors") {
        selectArray.push("Class");
    }

    else if (view == "Quests" || view == "Artifacts") {
        selectArray.push("Category");
    }

    return selectArray;
}

function getArrayForForm(view) {
    var formArray = ["First Name", "Last Name", "Phone", "Email"];
    if (view == "Students") {
        formArray.push("Wallet")
        formArray.push("Class");

    } else if (view == "Classes" || view == "Levels" || view == "Quests" || view == "Artifacts") {
        formArray = ["Name"];
    }
    if (view == "Levels") {
        formArray.push("Level Threshold");
    }
    if (view == "Quests") {
        formArray.push("Description");
        formArray.push("Award");
    }

    if (view == "Artifacts" || view == "Inventory") {
        formArray.push("Description");
        formArray.push("Price");
    }

    if (view == "Inventory") {
        formArray.push("Amount");
    }


    return formArray;
}

function createSubmitButton(actionLabel, view, index) {
    var button = createButton(name);
    button.addEventListener("click", function () { handleSubmit(actionLabel, view, index) });
    document.body.appendChild(button);

    return button;
}

function handleSubmit(actionLabel, view, index) {
    var div = document.createElement("div");
    div.setAttribute("class", "text-container");
    var textArea = document.createElement("div");
    textArea.setAttribute("class", "text-area");
    div.appendChild(textArea);
    var info = document.createElement("label");
    info.textContent = "Are you sure you want to " + actionLabel + "?";
    textArea.appendChild(info);

    var button1 = createButton("Yes");
    textArea.appendChild(button1);
    button1.setAttribute("class", "button functionButton");
    textArea.appendChild(button1);
    button1.addEventListener("click", function () { confirm(div, view, index) });

    var button2 = createButton("No");
    button2.setAttribute("class", "button functionButton");
    textArea.appendChild(button2);
    button2.addEventListener("click", function () { exit(div) });

    document.body.appendChild(div);
}

function createFormButton(name, inputsArray, optionsArray, boolean, view, index) {
    var button = createButton(name);
    button.addEventListener("click", function () { handleForm(name, inputsArray, optionsArray, boolean, view, index) })
    document.body.appendChild(button);
    return button;
}

function confirm(div, view, index) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function()  {
        if (this.readyState == 4 && this.status == 200) {
            document.body = this.response;
        }
    };
    xhttp.open("POST", "/DAOUserController?method=delete" + view + "?id=" + index, true);
    xhttp.send();
    exit(div);
    
}

function exit(div) {
    div.setAttribute("class", "confirm");
}

function handleForm(name, inputsArray, optionsArray, boolean, view, index) {
    createForm(name, inputsArray, optionsArray, boolean, view, index);
    displayForm();
}

function displayForm() {
    var div = document.createElement("div");
    div.setAttribute("class", "form-container");
    var form = document.getElementById("form");
    div.appendChild(form);
    document.body.appendChild(div);
}

function createForm(name, inputsArray, optionsArray, boolean, view, index) {
    var form = document.createElement("form");
    form.setAttribute("id", "form");
    form.method='post';


    var container = document.createElement("div");
    container.setAttribute("class", "container");
    container.setAttribute("id", "container");

    if (!name.includes("Add")) {
        createInputElements(container, inputsArray, boolean, view);
        fullfillInputs("container", "/DAOUserController?method=" + view + "?id=" + index);
    } else {
        createInputElements(container, inputsArray, boolean);
    }
    createSelectElements(container, optionsArray);
    form.appendChild(container);

    var button = createButton("Save");
    button.setAttribute("class", "button save-button form-button");

    if (inputsArray.length == 0 && optionsArray.length > 0 && optionsArray[0] == "Class") {
        button.setAttribute("onclick", "setStudentList();return false");
    }
    form.appendChild(button);
    document.body.appendChild(form);

}

function setStudentList() {
    var div = document.createElement("div")
    div.setAttribute("class", "text-container");
    var form = document.createElement("form");
    div.appendChild(form);
    form.setAttribute("class", "text-area");

    var formArray = ["First Name Last Name"];
    for (var j = 0; j < 10; j++) {
        for (var i = 0; i < formArray.length; i++) {
            var label = document.createElement("label");
            label.textContent = formArray[i];
            form.appendChild(label);
            var checkbox = document.createElement("input");
            checkbox.setAttribute("type", "checkbox");
            form.appendChild(checkbox);
        }
    }
    var button = createButton("Save");
    button.setAttribute("class", "button functionButton");
    button.setAttribute("type", "submit");
    form.appendChild(button);
    document.body.appendChild(div);
}

function createInputElements(container, inputsArray, boolean) {

    for (var i = 0; i < inputsArray.length; i++) {
        var div = document.createElement("div");

        var label = document.createElement("label");
        div.appendChild(label);
        label.textContent = inputsArray[i];

        var input = document.createElement("input");
        input.setAttribute("required", "");
        input.setAttribute("name", inputsArray[i]);
        div.appendChild(input);

        container.appendChild(div);
        if (boolean) {
            input.setAttribute("readOnly", true);
            // input.style.backgroundColor = "grey";
        } else {
            input.addEventListener("click", function () { this.setAttribute('required', true) });
            input.addEventListener("click", function () { this.value = '' });
        }
    }
}

function fullfillInputs(divName, path) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var json = this.responseText;
            var obj = JSON.parse(json);
            var div = document.getElementById(divName);
            for (var i = 0; i < div.childNodes.length; i++) {
                var child = div.childNodes[i].lastChild;
                if (child.nodeName === "INPUT") {
                    child.value = obj[div.childNodes[i].firstChild.textContent];
                }
            }
        }
    };
    xhttp.open("GET", path, true);
    xhttp.send();

}

function createSelectElements(container, optionsArray) {
    for (var i = 0; i < optionsArray.length; i++) {

        var div = document.createElement("div");
        var label = document.createElement("label");
        div.appendChild(label);
        label.textContent = optionsArray[i];

        var pickList = document.createElement("select");
        pickList.multiple = true;
         var xhttp = new XMLHttpRequest();
         var optionList = [];

         xhttp.onreadystatechange = function() {

             if(this.readyState == 4 && this.status == 200) {
                 var entries = JSON.parse(this.response);
                 for(x in entries) {
                     for(y in entries[x]) {
                         optionList.push(entries[x][y]);
                     }
                }
                 for (var j = 0; j < optionList.length; j++) {
                     var option = document.createElement("option");
                     option.value = optionList[j];
                     option.textContent = optionList[j];
                     if(!Number.isInteger(optionList[j])) {
                        pickList.appendChild(option);
                     }


                 }
             }
         };
         xhttp.open("GET", "/daoAdminController?method=" + optionsArray[i], true);
         xhttp.send();

     div.appendChild(pickList);
     container.appendChild(div);
    }
}

function createStoreTable(array, id) {
    var tables = document.getElementsByTagName("table");
    if (tables.length == 0) {
        createTable(array, "Store");
        moveStoreTableBeforeFooter();
    } else {
        var table = tables[0];
        table.remove();
        createTable(array, "Store");
        moveStoreTableBeforeFooter();
    }
}


function moveStoreTableBeforeFooter() {
    var table = document.getElementById("table_content");
    var buttonContainer = document.getElementsByClassName("button-container")[0];
    buttonContainer.appendChild(table);
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