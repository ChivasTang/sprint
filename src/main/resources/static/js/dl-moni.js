function deleteCookie(name) {
    var ex = new Date();
    ex.setTime(ex.getTime() - 1);
    document.cookie = name + "=;expires=" + ex.toGMTString();
}

deleteCookie("downloadToken");
console.log(document.cookie);

document.cookie = "downloadToken=downloadToken";
console.log(document.cookie);

var filePath = window.opener.document.form.filePath.value;
var fileName = window.opener.document.form.fileName.value;
var fileSize = window.opener.document.form.fileSize.value;
var fileSizeStr = window.opener.document.form.fileSizeStr.value;

console.log(filePath);
console.log(fileName);
console.log(fileSize);
console.log(fileSizeStr);

var formData = new FormData();
formData.append("filePath", filePath);
formData.append("fileName", fileName);
formData.append("fileSize", fileSize);
formData.append("fileSizeStr", fileSizeStr);

var xhr = new XMLHttpRequest();
var startTime, endTime, dlTime;

xhr.open("POST", "realDL", true);
xhr.responseType = 'blob';
xhr.timeout = 3000;

xhr.onloadstart = function (ev) {
    console.log(ev);
    startTime = new Date();
    console.log("onloadstart: " + startTime);
};

xhr.onloadend = function (ev) {
    console.log(ev);
    endTime = new Date();
    console.log("onloadend: " + endTime);

    dlTime = endTime.getTime() - startTime.getTime();
    console.log("Download Time: " + dlTime);
    console.log(document.cookie);
};

xhr.onload = function (ev) {
    console.log("onloading...");
    console.log(ev);
    var data = {
        dataURI: ev.target.response,
        fileName: getFileName()
    };
    saveAs(data);
};

xhr.send(formData);

function saveAs(data) {
    var a = document.createElement('a');
    a.style.display = 'none';
    document.body.appendChild(a);
    a.href = window.URL.createObjectURL(data.dataURI);
    a.download = data.fileName;
    document.body.appendChild(a);
    a.click();
}

function getFileName() {
    return new Date().toLocaleString().replace(/\/|[\u4e00-\u9fa5]|:|\s+/g, "") + ".zip";
}