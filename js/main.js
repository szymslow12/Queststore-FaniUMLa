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
        ul.appendChild(li);
        var a = document.createElement("a");
        a.setAttribute("href", array[i] + ".html");
        a.textContent = array[i];
        li.appendChild(a);
    }

    document.body.appendChild(nav);
}

function includeFooter() {
        document.write('<footer class="menu">\
            <p> &copy; FaniUMLa</p>\
        </footer>\
    \
    </body>\
    \
    ');
}

function createButton(name) {
    var button = document.createElement("BUTTON");
    var text = document.createTextNode(name);
    button.appendChild(text);
    document.body.appendChild(button);
}