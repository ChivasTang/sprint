$.ajaxSetup({cache: false});

var dlLoader = (function () {
    var setFun = {
        pageReady: function () {
            setFun.getDLBtn();
        },

        getDLBtn: function () {
            $("#dl_btn").kendoButton({
                icon:"download",
                click:function (e) {
                    $.ajax({
                        url:"beforeDL",
                        type:"POST",
                        dataType:"json",
                        contentType: "application/json;charset=UTF-8",
                        data:kendo.stringify({fileName:null}),
                        success:function (data) {
                            console.log(data);
                            setFun.getDialog(data);
                        }
                    });
                }
            });
        },

        getDialog: function (data) {
            $("#dialog").kendoDialog({
                width: 470,
                title: "ファイルダイアログ",
                closable: false,
                modal: true,
                content: "<p>下記のファイルをダウンロードしてよろしいですか？<p>"+
                        "<div><label>ファイル名：</label><label>"+data.fileName+"</label></div>"+
                        "<div><label>ファイルサイズ：</label><label>"+data.fileSize+"Bytes</label></div>"+
                        "<div id='progressbar'></div>",
                actions: [{
                    text: 'キャンセル',
                    action: function (e) {
                        //TODO
                    }
                },{
                    text: '確定',
                    primary: true,
                    action: function (e) {
                        $.ajax({
                            url:"realDL",
                            type:"POST",
                            dataType:"json",
                            contentType: "application/json;charset=UTF-8",
                            data:kendo.stringify({fileName:data.fileName, filePath:data.filePath, fileSize:data.fileSize}),
                            success:function (res) {
                                console.log(res);
                            }
                        });
                        return false;
                    }
                }],
                initOpen: function (e) {
                    $("#progressbar").kendoProgressBar({
                        min: 0,
                        max: 100,
                        type: "percent",
                        change: function (e) {

                        },
                        complete: function (e) {

                        }
                    });
                },
                close: function (e) {
                    $("#progressbar").data("kendoProgressBar").destroy();
                    $("#dialog").data("kendoDialog").destroy();
                    $("#dialog").remove();
                    $("body").append("<div id='dialog'></div>");
                }
            });
        }
    };
    return {
        setFun: setFun
    };
})();

$(function () {
    dlLoader.setFun.pageReady();
});