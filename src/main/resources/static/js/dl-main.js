$.ajaxSetup({cache: false});

var dlMainLoader = (function () {
    var setFun = {

        getDLMenu:function(){
            $("#menu").kendoMenu({
                dataSource:[{
                    text: "普通ダウンロード"
                },{
                    text:"Cookieダウンロード",
                    select: function (e) {
                        console.log(e);
                    }
                }]
            });
        },

        pageReady: function () {
            setFun.getDLMenu();
        }
    };
    return {
        setFun: setFun
    };
})();

$(function () {
    dlMainLoader.setFun.pageReady();
});