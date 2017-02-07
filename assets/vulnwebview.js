function go() {
    vulnerable = [];

    for (i in top) {
        el = top[i];
        if (el == null) { continue; };
        if (typeof(el) === 'function') { continue; }

        try {
            top[i].getClass().forName('java.lang.Runtime');
            vulnerable.push(i);
        } catch(e) {
            // do nothing...
        }
    }

    res = document.getElementById('result');
    res.innerHTML = 'Vulnerable status: ';
    if (vulnerable.length > 0) {
        res.innerHTML += '<font color=red>VULNERABLE: ' + vulnerable.join() + '</font>';
    } else {
        res.innerHTML += '<font color=green>NOT VULNERABLE</font>';
    }
}