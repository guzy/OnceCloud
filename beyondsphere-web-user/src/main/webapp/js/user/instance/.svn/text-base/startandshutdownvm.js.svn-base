function getInfoList() {
    var infoList = "";
    $('input[name="vmrow"]:checked').each(function () {
        infoList += "[i-" + $(this).parent().parent().attr("rowid").substring(0, 8) + "]&nbsp;";
    });
    return infoList;
}

function showbox(type) {
    var infoList = getInfoList();
    var infoArray = new Array("启动主机", "重启主机", "关闭主机");
    var showMessage = '';
    var showTitle = '';
    if (type == 2) {
        showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">1. 强制关机会丢失内存中的数据<br/>'
            + '2. 为保证数据的完整性，请在强制关机前暂停所有文件的写操作，或进行正常关机。</div>'
            + '<div class="item" style="margin:0"><div class="controls" style="margin-left:100px">'
            + '<label class="inline"><input type="checkbox" id="force">&nbsp;强制关机</label></div></div>';
        showTitle = infoArray[type] + '&nbsp;' + infoList + '?';
    } else {
        showMessage = '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;'
            + infoArray[type] + '&nbsp;' + infoList + '?</div>';
        showTitle = '提示';
    }
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="vmrow"]:checked').each(function () {
                        var uuid = $(this).parent().parent().attr("rowid");
                        var hosttype = $(this).parent().parent().attr("hosttype");
                        if (type == 0) {
                            startVM(uuid,hosttype);
                        } else if (type == 1) {
                            restartVM(uuid,hosttype);
                        } else if (type == 2) {
                            var force = $('#force')[0].checked;
                            shutdownVM(uuid, force,hosttype);
                        }
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

$('#startup').on('click', function (event) {
    event.preventDefault();
    showbox(0);
});
$('#restart').on('click', function (event) {
    event.preventDefault();
    showbox(1);
});
$('#shutdown').on('click', function (event) {
    event.preventDefault();
    showbox(2);
});

function startVM(uuid,hosttype) {
	$('#status-check').prop('disabled', true);
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-stopped");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('启动中');
    var action ='';
    if(hosttype=="beyondCloud"){
    	action = '/VMAction/StartVM';
    }
    if(hosttype=="vSphere"){
    	action = '/VSphereAction/StartVM';
    }
    $.ajax({
        type: 'get',
        url:action,
        data: {uuid: uuid},
        dataType: 'json',
        complete: function(){
        	$('#status-check').prop('disabled', false);
        }
    });
}

function restartVM(uuid,hosttype) {
	$('#status-check').prop('disabled', true);
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-running");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('重启中');
    thistr.find('.console').remove();
    
    var action ='';
    if(hosttype=="beyondCloud"){
    	action = '/VMAction/RestartVM';
    }
    if(hosttype=="vSphere"){
    	action = '/VSphereAction/RestartVM';
    }
    $.ajax({
        type: 'get',
        url: action,
        data: {uuid: uuid},
        dataType: 'json',
        complete: function(){
        	$('#status-check').prop('disabled', false);
        }
    });
}

function shutdownVM(uuid, force,hosttype) {
	$('#status-check').prop('disabled', true);
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-running");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('关机中');
    thistr.find('.console').remove();
    
    var action ='';
    if(hosttype=="beyondCloud"){
    	action = '/VMAction/ShutdownVM';
    }
    if(hosttype=="vSphere"){
    	action = '/VSphereAction/ShutdownVM';
    }
    $.ajax({
        type: 'get',
        url: action,
        data: {uuid: uuid, force: force},
        dataType: 'json',
        complete: function(){
        	$('#status-check').prop('disabled', false);
        }
    });
}

$('#tablebody').on('click', '.console', function (event) {
    event.preventDefault();
    var uuid = $(this).data("uuid");
    var vnc = $('#platformcontent').attr("novnc");
    var token = uuid.substring(0, 8);
    var url = vnc + "console.html?id=" + token;
    window.open(url, "novnc", 'height=600, width=810, top=0, left=0');
});