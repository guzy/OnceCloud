//alarmlist
reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getAlarmList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="alrow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}

function allDisable() {
	$("#startup").attr("disabled", true).addClass('btn-disable');
	$("#shutdown").attr("disabled", true).addClass('btn-disable');
    $("#destroy").addClass('btn-forbidden');
    $("#bindalarm").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var total = 0;
	var running = 0;
    $('input[name="alrow"]:checked').each(function () {
        var stateicon = $(this).parent().parent()
		.find('[name="stateicon"]');
        if (stateicon.hasClass('icon-running')) {
			running++;
        } else {
        	
		} 
        total++;
    });
    if (total == 1) {
        $("#destroy").removeClass('btn-forbidden');
        $("#bindalarm").removeClass('btn-forbidden');
        if (running == 0) {
        	$("#startup").attr('disabled', false).removeClass('btn-disable');
        }
        else {
        	$("#shutdown").attr('disabled', false).removeClass('btn-disable');
        }
        
    }
});

function getAlarmList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: '/AlarmAction/AlarmList',
        data: {page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            var totalnum = array[0];
            var totalp = 1;
            if (totalnum != 0) {
                totalp = Math.ceil(totalnum / limit);
            }
            options = {
                totalPages: totalp
            };
            $('#pageDivider').bootstrapPaginator(options);
            pageDisplayUpdate(page, totalp);
            var tableStr = "";
            for (var i = 1; i < array.length; i++) {
                var obj = array[i];
                var alarmUuid = obj.alarmUuid;
                var alarmName = decodeURIComponent(obj.alarmName);
                var showuuid = "al-" + alarmUuid.substring(0, 8);
                var showstr = "<a class='id'>" + showuuid + '</a>';
                var alarmCount = obj.alarmCount;
                var alarmTime = decodeURIComponent(obj.alarmTime);
                alarmTime = alarmTime.substring(0, alarmTime.length - 2);
                alarmTime = alarmTime.replace(/%3A/g, ":");
                var thistr = '<tr rowid="' + alarmUuid + '"><td class="rcheck"><input type="checkbox" name="alrow"></td><td name="console">' + showstr + '</td><td name="alarmName">'
                    + alarmName + '</td><td name="alarmCount">'+alarmCount+'<td name="createtime" class="time">' + alarmTime + '</td></tr>';
                tableStr += thistr;
            }
            $('#tablebody').html(tableStr);
        }
    });
}

//alarmdetail
$('#tablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var uuid = $(this).parent().parent().attr('rowid');
    var form = $("<form></form>");
    form.attr("action", "/alarm/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="alarmUuid" value="' + uuid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

//createalarm
$('#create').on('click', function (event) {
    event.preventDefault();
    $('#AlarmModalContainer').load($(this).attr('url'), '', function () {
        $('#AlarmModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});


//destoryalarm
function getInfoList() {
    var infoList = "";
    $('input[name="alrow"]:checked').each(function () {
        infoList += "[al-" + $(this).parent().parent().attr("rowid").substring(0, 8) + "]&nbsp;";
    });
    return infoList;
}

function showbox() {
    var infoList = getInfoList();
    var showMessage = '';
    var showTitle = '';
    showMessage = '<div class="alert alert-info" style="margin:10px">'
        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;确定要删除警告策略&nbsp;' + infoList + '?</div>';
    showTitle = '提示';

    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="alrow"]:checked').each(function () {
                        var alarmUuid = $(this).parent().parent().attr("rowid");
                        destroyAlarm(alarmUuid);
                    });
                    removeAllCheck();
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
                callback: function () {
                    removeAllCheck();
                }
            }
        }
    });
}

$('#destroy').on('click', function (event) {
    event.preventDefault();
    showbox();
});

function destroyAlarm(alarmUuid) {
    $.ajax({
        type: 'get',
        url: '/AlarmAction/Destory',
        data: {alarmUuid: alarmUuid},
        dataType: 'text',
        success: function (result) {
            if (result === "true") {
                $("#tablebody").find('[rowid="' + alarmUuid + '"]').remove();
            } else {
                bootbox.dialog({
                    message: '<div class="alert alert-danger" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;该策略已绑定资源，请先卸载资源</div>',
                    title: "提示",
                    buttons: {
                        main: {
                            label: "确定",
                            className: "btn-primary",
                            callback: function () {
                            }
                        }
                    }
                });
            }
        }
    });
}


$('#bindalarm').on('click', function (event) {
    event.preventDefault();
    var uuid = "";
    $('input[name="alrow"]:checked').each(function () {
        uuid = $(this).parent().parent().attr("rowid");
    });
    $('#AlarmModalContainer').load($(this).attr('url'), {"uuid": uuid}, function () {
        $('#AlarmModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='alrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='alrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});