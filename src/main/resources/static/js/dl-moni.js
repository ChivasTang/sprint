$.ajaxSetup({cache: false});

var dlMoniLoader = (function () {
    var setFun = {
        pageReady: function () {
        }
    };
    return {
        setFun: setFun
    };
})();

$(function () {
    dlMoniLoader.setFun.pageReady();
});