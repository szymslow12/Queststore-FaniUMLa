
function includeHTML(title, first, second, third) {
    document.write('\<body>\
    \
        <header>\
            Welcome ' + title + '\
        \</header>\
        <nav class="menu">\
            <ul>\
                <li>\
                    <a href="index.html">' + first + '</a>\
                </li>\
                <li>\
                    <a href="index.html">' + second + '</a>\
                </li>\
                <li>\
                    <a href="index.html">' + third + '</a>\
                </li>\
            </ul>\
        </nav>\
    \
        <section>\
            <p>cos</p>\
        </section>\
    \
        <footer class="menu">\
            <p> &copy; FaniUMLa</p>\
        </footer>\
    \
    </body>\
    \
    ');
}