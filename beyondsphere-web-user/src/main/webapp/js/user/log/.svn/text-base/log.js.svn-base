reloadList();
function reloadList(){
	var start = $('#load-log').attr("start");
    var startInt = parseInt(start);
    var status = $('.active', $('.once-tab')).data('status');
    getLogList(status, 1, startInt);
}

function getLogList(status, append, start) {
    $.ajax({
        type: 'get',
        url: '/LogAction/LogList',
        data: {status: status, start: start, num: "10"},
        async: false,
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
            	logArea.innerHTML = '<li class="log-item"><span style="float:left; margin:10px 5px 0px 10px"><a id="selectAll">全选</a></span>'
            		+ '<div class="alert alert-success alert-item" id="operation-title">'
            		+ '<span class="log-name">操作</span>'
            		+ '<span class="log-time" style="text-align:left;width:158px;">操作时间</span>'
            		+ '<span class="log-elapse">响应时间</span>' 
            		+ '</div></li>';
                var iconStr = new Array("cloud", "inbox", "camera", "globe",
                    "globe", "flash", "record", "barcode", "fullscreen",
                    "random", "camera", "flash", "user", "tint", "hdd",
                    "road", "tasks", "fullscreen", "globe", "indent-left",
                    "thumbs-up", "flag", "inbox");
                var statusStr = new Array("danger", "success", "warning",
                    "info");
                var statusIconStr = new Array("remove", "ok");
                var search = $('#search').val();
                for (var i = 0; i < jsonArray.length; i++) {
            		var jsonObj = jsonArray[i];
            		//alert(decodeURIComponent(jsonObj.logObjectStr)==search);
            		//alert(decodeURIComponent(jsonObj.logObjectStr).indexOf(search) > 0);
            		if(search==""|| decodeURIComponent(jsonObj.logObjectStr).indexOf(search) >= 0){
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
                        
                        var alertItem = '<li class="log-item">'
                        	+ '<input type="checkbox" name="loglists" style="float:left; margin:13px 5px 0px 10px" value="'+logId+'">'
                        	+ '<div class="alert alert-'
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
    var logtype = $('.once-tab').find('.active').attr("type");
    if(logtype=="operation-log"){
    	$('#operation-log').show();
    	//$('#system-log').hide();
    	$('#application-log').hide();
    }else if(logtype=="system-log"){
    	$('#operation-log').hide();
    	//$('#system-log').show();
    	$('#application-log').hide();
    }else{
    	$('#operation-log').hide();
    	//$('#system-log').hide();
    	$('#application-log').show();
    	listContainers();
    }
});

$("#dockerList").on('change', function(){
	var containerId = $(this).val();
	var applicationLogType = $("input[name='applicationRadio']:checked").val();
	listContainerlogs(containerId,applicationLogType);
});


$(".operationRadio").change(function(){
	var operationTag = $("input[name='operationRadio']:checked").val();
	getLogList(operationTag, 0, 0);
    $('#load-log').attr("start", 10);
    $('#load-log').attr("has-log", false);
    if(operationTag == 0){
    	$('#operation-title').removeClass('alert-success');
    	$('#operation-title').addClass('alert-danger');
    }else{
    	$('#operation-title').removeClass('alert-danger');
    	$('#operation-title').addClass('alert-success');
    }
});

$(".applicationRadio").change(function(){
	var applicationLogType = $("input[name='applicationRadio']:checked").val();
	var containerId = $('#dockerList').val();
	listContainerlogs(containerId,applicationLogType);
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

$('#selectAll').on('click', function(){
	var tag = $('#selectAll').text();
	if('全选'== tag){
		$("input[name='loglists']").prop("checked",true);
		$('#selectAll').text('取消');
	}else{
		$("input[name='loglists']").prop("checked",false);
		$('#selectAll').text('全选');
	}
});

$('#delete').on('click', function(event){
	event.preventDefault();
	var remove_success = false;
	$("input[name='loglists']:checkbox:checked").each(function(){ //由于复选框一般选中的是多个,所以可以循环输出 
		var logId = $(this).attr('value');
		$.ajax({
			type: 'post',
			async: false,
	        url: '/LogAction/Remove',
	        data: {logId : logId},
	        dataType: 'json',
	        success: function (object) {
	        	var status = $('.active', $('.once-tab')).data('status');
	    	    getLogList(status, 0, 0);
	        }
		});
	}); 
});

$('#export').on('click', function(){
	$.ajax({
		type: 'post',
		async: false,
        url: '/LogAction/LogToFile',
        dataType: 'json',
        success: function (obj) {
        	$('#filedownload').prop('href','/file/'+obj.filename);
        	$('#download').click();
        }
	});
});

$('#config-save-time').on('click', function(){
    $('#LogModalContainer').load('user/modal/logConfig', '',
        function () {
            $('#LogModalContainer').modal({
                backdrop: false,
                show: true
            });
    });
});

$('#config-cancel-time').on('click', function(){
	$.ajax({
		type: 'post',
        url: '/LogAction/cancelSaveTime',
        dataType: 'json',
        success: function (flag) {
        	bootbox.alert('取消日志配置成功');
        }
	});
});

function listContainers(){
	var containers = "";
	$.ajax({
		type: 'get',
        url: '/ContainerAction/listAllContainer',
        async: false,
        dataType: 'json',
        success: function (array) {
        	if(array.length > 0){
        		for(var i=0;i<array.length;i++){
        			var container = array[i];
        			var conname = container.containername;
            		$('#dockerList').append('<option value="'+container.containerid+'">c-'+conname[conname.length-1]+'</option>');
            	}
        	}
        }
	});
}

function listContainerlogs(containerId, logtype){
	$.ajax({
		type: 'get',
        url: '/LogAction/applicationLogList',
        data: {containerId : containerId, logtype : logtype},
        async: false,
        dataType: 'json',
        success: function (result) {
        	$('#containerLog').html( '<span id="logresult">'+result.log+'</span>');
        }
	});
}
