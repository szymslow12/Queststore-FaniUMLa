function createHeader(name) {

    var header = document.createElement("HEADER");
    header.textContent = "Welcome " + name;
    document.body.appendChild(header);
}



function createMenu(array) {

    var nav = document.createElement("NAV");
    nav.setAttribute("class", "menu");
    
    var ul = document.createElement("ul");
    nav.appendChild(ul);

    for (var i = 0; i < array.length; i++) {
        var li = document.createElement("li");
        
        var a = document.createElement("a");
        a.setAttribute("href", array[i] + ".html");
        a.textContent = array[i];
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
    button.setAttribute("class", "button");
    button.appendChild(text);
    document.body.appendChild(button);
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
        "Delete" : "fas fa-trash-alt"
    };

    for(var j = 0; j<10; j++) {
        var row = document.createElement("tr");
        row.setAttribute("class", "tableRow")
        for (var i=0; i<array.length; i++) {
            
            if(array[i] in imgDict) {
                var data = document.createElement("td");
                var a = document.createElement("a");
                data.appendChild(a);
                a.setAttribute("class", imgDict[array[i]]);
                a.setAttribute("href", "sth.html");
            } else {
                var data = document.createElement("td");
                data.textContent = "Elzbieta Krzych";
            }
            row.appendChild(data);
        } 
        table.appendChild(row);
    } 
    document.body.appendChild(table);
}