$.ajaxSetup({cache: false});

var dlFormLoader = (function () {
    var setFun = {
        pageReady: function () {
        }
    };
    return {
        setFun: setFun
    };
})();

$(function () {
    dlFormLoader.setFun.pageReady();
});