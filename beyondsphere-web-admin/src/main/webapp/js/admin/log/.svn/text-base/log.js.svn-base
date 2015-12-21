reloadList(1);
function reloadList(a){
	getLogList(4, 0, 0);
}

function getLogList(status, append, start, num) {
	
	var search=$.trim($('#search').val());
    $.ajax({
        type: 'get',
        url: 'LogAction/LogList',
        data: {status: status, start: start, num: "10",search:search},
        dataType: 'json',
        success: function (jsonArray) {
            var logArea = document.getElementById("log-area");
            if (append == 0) {
                logArea.innerHTML = "";
            }
            if (jsonArray.length == 0) {
                var hasLog = $('#load-log').attr("has-log");
                if (hasLog == "true") {
                    $('#load-log').hide();
                } else {
                    logArea.innerHTML = '<div><span class="unit">没有相关的操作<span></div>';
                    $('#load-log').hide();
                }
            } else {
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
                    var logUName = decodeURIComponent(jsonObj.logUName);
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
                    //该条记录的操作用户
                    
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
                    alertItem+='<dt class="log-dt">操作用户&nbsp;:</dt><dd><a>'+logUName+'</a></dd>';
                    alertItem += '</dl></li>';
                    $(logArea).append(alertItem);
                }
                $('#load-log').attr("has-log", "true");
                if (jsonArray.length == 10) {
                    $('#load-log').show();
                } else {
                    $('#load-log').hide();
                }
            }
        },
        error: function () {

        }
    });
}

$('.once-tab').on('click', '.tab-filter', function (event) {
    event.preventDefault();
    $('li', $('.once-tab')).removeClass('active');
    $(this).addClass('active');
    var status = $(this).data('status');
    getLogList(status, 0, 0);
    $('#load-log').attr("start", 10);
    $('#load-log').attr("has-log", false);
});

$('#load-log').on('click', function (event) {
    event.preventDefault();
    var start = $(this).attr("start");
    var startInt = parseInt(start);
    var status = $('.active', $('.once-tab')).data('status');
    getLogList(status, 1, startInt);
    $(this).attr("start", startInt + 10);
});

$('#log-area').delegate('.alert-item', 'click', function (event) {
    event.preventDefault();
    $("dl", $(this).parent()).toggle();
});

//$('#search').on('focusout', function () {
//	getLogList(4, 0, 0);
//});
//
//$('#search').keypress(function (e) {
//    var key = e.which;
//    if (key == 13) {
//    	getLogList(4, 0, 0);
//    }
//});
