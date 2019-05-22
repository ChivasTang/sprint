$.ajaxSetup({cache: false});

var commFunc = (function () {
    var setFun = {
        isEmpty: function (str) {
            return undefined === str || null === str || !str || "" === str;
        }
    };
    return {
        setFun: setFun
    };
})();
