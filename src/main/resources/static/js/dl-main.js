$.ajaxSetup({cache: false});

var dlMainLoader = (function () {
    var setFun = {
        getDailog: function (data) {
            $("#dialog").kendoDialog({
                width: 400,
                title: "Cookieダウンロード",
                closable: false,
                modal: true,
                content: "<p>指定されているファイルをダウンロードしますか？</p><p>ファイル名：</p><p>" + data.fileName + "</p><p>ファイルサイズ：</p><p>" + data.fileSizeStr + "</p>",
                actions: [{
                    text: "キャンセル"
                }, {
                    text: "確認",
                    primary: true,
                    action: function (e) {
                        setFun.getNewWin(data);
                    }
                }],
                initOpen: function (e) {
                    $("#filePath").val(data.filePath);
                    $("#fileName").val(data.fileName);
                    $("#fileSize").val(data.fileSize);
                    $("#fileSizeStr").val(data.fileSizeStr);
                },
                close: function (e) {
                    $("#dialog").data("kendoDialog").destroy();
                    $("#dialog").remove();
                    $("body").append("<div id='dialog'></div>");
                }
            });
        },

        getNewWin: function (data) {
            //var link=JSON.stringify({filePath:data.filePath,fileName:data.fileName,fileSize:data.fileSize,fileSizeStr:data.fileSizeStr});
            var winLink="dlMoni?";
            //var link = "dlMoni?" + encodeURI("filePath=" + data.filePath + "&fileName=" + data.fileName + "&fileSize=" + data.fileSize + "&fileSizeStr=" + data.fileSizeStr);
            var target = "_blank";
            var height = 400;
            var width = 500;
            var top = window.screen.availHeight - height;
            var left = window.screen.availWidth - width;
            var features = "titlebar=0, toolbar=0, location=0, status=0, menubar=0, scrollbars=0, resizable=0" +
                ", top=" + top +
                ", left=" + left +
                ", height=" + height +
                ", width=" + width;
            window.open(winLink, target, features, true);
        },


        getDLMenu: function () {
            $("#menu").kendoMenu({
                dataSource: [{
                    text: "普通ダウンロード"
                }, {
                    text: "Cookieダウンロード",
                    select: function (e) {
                        $.ajax({
                            url: "beforeDL",
                            type: "POST",
                            dataType: "json",
                            contentType: "application/json;charset=UTF-8",
                            data: JSON.stringify({filePath: null})
                        }).done(function (result, status, xhr) {
                            console.log(result);
                            console.log(status);
                            console.log(xhr);
                            if (status === "success") {
                                setFun.getDailog(result);
                            }
                        });
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