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