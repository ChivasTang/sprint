$.ajaxSetup({cache: false});

var dlMoniLoader = (function () {
    var setFun = {
        getDLForm: function(){
            var url=decodeURI(document.URL);
            var num=url.indexOf("?");
            return JSON.parse(url.substr(num + 1));
        },

        getDLProgress: function(data){
            var startTime, endTime;
            $("#filePath").val(data.filePath);
            $("#fileName").val(data.fileName);
            $("#fileSize").val(data.fileSize);
            $("#fileSizeStr").val(data.fileSizeStr);

            $("#progressbar").kendoProgressBar({
                min:0,
                max:100,
                type: "percent",
                complete:function (e) {
                    endTime=new Date().getTime();
                    $.ajax({
                        url:"afterDL",
                        type:"POST",
                        dataType:"json",
                        contentType:"application/json;charset=UTF-8",
                        data:JSON.stringify({filePath: data.filePath, fileName: data.fileName, fileSize: data.fileSize, dlTime: (endTime-startTime)})
                    }).done(function (data, status, xhr) {
                        if(data.completed){
                            window.close();
                        }
                    });
                }
            });

            var formData=new FormData();
            formData.append("filePath",data.filePath);
            formData.append("fileName",data.fileName);
            formData.append("fileSize",data.fileSize);
            formData.append("fileSizeStr",data.fileSizeStr);

            var xhr=new XMLHttpRequest();
            xhr.open("POST", "realDL");
            xhr.responseType = 'blob';
            xhr.onloadstart=function (ev) {
                startTime=new Date().getTime();
            };
            xhr.onprogress=function (ev) {
                $("#progressbar").data("kendoProgressBar").value(kendo.format("{0}",ev.loaded*100/ev.total));
            };
            xhr.onload=function(ev){
                var blob=this.response;
                kendo.saveAs({
                    dataURI:blob,
                    fileName:data.fileName
                });
            };
            xhr.send(formData);
        },

        pageReady: function () {
            setFun.getDLProgress(setFun.getDLForm());
        }
    };
    return {
        setFun: setFun
    };
})();

$(function () {
    dlMoniLoader.setFun.pageReady();
});