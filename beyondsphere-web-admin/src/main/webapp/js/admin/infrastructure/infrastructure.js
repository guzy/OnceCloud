getLogList(4, 0, 0);
function getLogList(status, append, start, num) {
    $.ajax({
        type: 'get',
        url: 'LogAction/LogList',
        data: {status: status, start: start, num: "10"},
        dataType: 'json',
        success: function (jsonArray) {
            var logArea = document.getElementById("log-area");
            if (append == 0) {
                logArea.innerHTML = "";
            }
            if (jsonArray.length == 0) {
                logArea.innerHTML = '<div><span class="unit">没有相关的操作<span></div>';
            } 
            else {
                var iconStr = new Array("cloud", "inbox", "camera", "globe",
                    "globe", "flash", "record", "barcode", "fullscreen",
                    "random", "camera", "flash", "user", "tint", "hdd",
                    "road", "tasks", "fullscreen", "globe", "indent-left",
                    "thumbs-up", "flag", "inbox");
                var statusStr = new Array("danger", "success", "warning",
                    "info");
                var statusIconStr = new Array("remove", "ok");
                for (var i = 0; i < jsonArray.length; i++) {
                    var jsonObj = jsonArray[i];
                    var logId = jsonObj.logId;
                    var logObject = jsonObj.logObject;
                    var logObjectStr = decodeURIComponent(jsonObj.logObjectStr);
                    var logActionStr = decodeURIComponent(jsonObj.logActionStr);
                    var logStatus = jsonObj.logStatus;
                    var logTime = jsonObj.logTime;
                    var logElapse = jsonObj.logElapse;
                    var logElapseSpan = "";
                    if (logStatus < 2) {
                        var logElapseStr = logElapse + "秒";
                        if (logElapse == 0) {
                            logElapseStr = "< 1秒";
                        }
                        logElapseSpan = '<span class="log-elapse"><span class="glyphicon glyphicon-'
                            + statusIconStr[logStatus]
                            + '"></span>'
                            + logElapseStr + '</span></div>';
                    }
                    var logIcon = '<span class="glyphicon glyphicon-'
                        + iconStr[logObject] + '"></span>';
                    var alertItem = '<li class="log-item"><div class="alert alert-'
                        + statusStr[logStatus]
                        + ' alert-item">'
                        + logIcon
                        + '<span class="log-name">'
                        + logActionStr
                        + logObjectStr
                        + '</span>'
                        + '<span class="log-time"><span class="glyphicon glyphicon-time"></span>'
                        + logTime + '</span>' + logElapseSpan;
                    var logInfo = eval(jsonObj.logInfo);
                    alertItem += '<dl class="my-horizontal">';
                    for (var j = 0; j < logInfo.length; j++) {
                        var logInfoObj = logInfo[j];
                        var logValueStr;
                        if (logInfoObj.value == "") {
                            logValueStr = '<dd><a>不存在</a></dd>';
                        } else {
                            logValueStr = '<dd><a>'
                                + decodeURIComponent(logInfoObj.value) + '</a></dd>';
                        }
                        var logInfoStr = '<dt class="log-dt">'
                            + decodeURIComponent(logInfoObj.key) + '&nbsp;:</dt>'
                            + logValueStr;
                        alertItem += logInfoStr;
                    }
                    alertItem += '</dl></li>';
                    $(logArea).append(alertItem);
                }
            }
        },
        error: function () {

        }
    });
}

$('#log-area').delegate('.alert-item', 'click', function (event) {
    event.preventDefault();
    $("dl", $(this).parent()).toggle();
});
