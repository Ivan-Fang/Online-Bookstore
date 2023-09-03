function $(id) {
    return document.getElementById(id);
}

function checkRegisterInfo() {
    // check username
    var nameNode = $("nameNode");
    var nameValue = nameNode.value;
    var nameReg = /[0-9a-zA-Z]{6,16}/;
    var nameSpan = $("nameSpan");
    if (!nameReg.test(nameValue)) {
        nameSpan.style.visibility = "visible";
        return false;
    } else {
        nameSpan.style.visibility = "hidden";
    }

    // check password
    var passwordNode = $("passwordNode");
    var passwordValue = passwordNode.value;
    var passwordReg = /[\w]{8,}/;
    var passwordSpan = $("passwordSpan");
    if (!passwordReg.test(passwordValue)) {
        passwordSpan.style.visibility = "visible";
        return false;
    } else {
        passwordSpan.style.visibility = "hidden";
    }

    // check password2
    var password2Node = $("password2Node");
    var password2Value = password2Node.value;
    var password2Span = $("password2Span");
    if (password2Value !== passwordValue) {
        password2Span.style.visibility = "visible";
        return false;
    } else {
        password2Span.style.visibility = "hidden";
    }

    // check email
    var emailNode = $("emailNode");
    var emailValue = emailNode.value;
    var emailReg = /^[a-zA-Z0-9_\.-]+@([a-zA-Z0-9-]+[\.]{1})+[a-zA-Z]+$/;
    var emailSpan = $("emailSpan");
    if (!emailReg.test(emailValue)) {
        emailSpan.style.visibility = "visible";
        return false;
    } else {
        emailSpan.style.visibility = "hidden";
    }

    return true;
}

var xmlHttpRequest;

// not need to memorize, copy the code when needed
function createXmlHttpRequest() {
    if (window.XMLHttpRequest) {        // for browsers that meet DOM2 standard
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {  // for IE browser
        try {
            return new ActiveXObject("Microsoft.XMLHTTP");
        } catch (e) {
            return new ActiveXObject("Msxml2.XMLHTTP");
        }
    }
}

function checkDuplicateName(name) {
    /*
        ajax: asynchronous javascript and xml
            - send the request asynchronously
            - renew part of the page, not the whole page
     */
    xmlHttpRequest = createXmlHttpRequest();
    var url = "user.do?operate=checkDuplicateName&name=" + name;
    xmlHttpRequest.open("GET", url, true);  // true: use ajax (asynchronous javascript and xml)
    xmlHttpRequest.onreadystatechange = alertDuplicate;     // 回調函數
    xmlHttpRequest.send();
}

function alertDuplicate() {
    if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
        alert(xmlHttpRequest.responseText);
    }
}