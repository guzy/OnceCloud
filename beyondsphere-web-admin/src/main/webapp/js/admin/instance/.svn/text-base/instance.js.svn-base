initPage();
var host = "all";
var userid = $('#userid').val();
var importance = 6;
var type = "instance";
reloadList(1);
function reloadList(page) {
    var limit = $('#limit').val();
    getVMList(page, limit);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="vmrow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}

function allDisable() {
    $("#startup").addClass('btn-forbidden');
    $("#pause").addClass('btn-forbidden');
    $("#restart").addClass('btn-forbidden');
    $("#off_migration").addClass('btn-forbidden');
    $("#shutdown").addClass('btn-forbidden');
    $("#destroy").addClass('btn-forbidden');
    $("#editNetwork").addClass('btn-forbidden');
    $("#vm-to-template").addClass('btn-forbidden');
    $("#migration").addClass('btn-forbidden');
    $("#limitdisk").addClass('btn-forbidden');
    $("#clearlimit").addClass('btn-forbidden');
    $("#touser").addClass('btn-forbidden');
    
    $("#add2Router").addClass('btn-forbidden');
    $("#usbManage").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var running = 0;
    var process = 0;
    var stopped = 0;
    var total = 0;
    var level = 0;
    var paused=0;
    $('input[name="vmrow"]:checked').each(function () {
    	level += $(this).attr("level");
        var stateicon = $(this).parent().parent().find('[name="stateicon"]');
        if (stateicon.hasClass('icon-running')) {
            running++;
        } else if (stateicon.hasClass('icon-stopped')) {
            stopped++;
        }else if(stateicon.hasClass('icon-paused')){
        	paused++;
        } else {
        	process++;
        }
        total++;
    });
    
    if(running==0 && stopped == 0 && paused>0  ){
    	
		$("#restart").removeClass('btn-forbidden');
	}

    if (total != 0 && process == 0) {
    	
    	$("#clearlimit").removeClass('btn-forbidden');
    	$("#touser").removeClass('btn-forbidden');
    	if (type == "instance" && level == 0) {
	        $("#destroy").removeClass('btn-forbidden');
    	}
        if (running > 0 && stopped == 0) {
            $("#shutdown").removeClass('btn-forbidden');
            $("#pause").removeClass('btn-forbidden');
            
            if (total == 1) {
            	$("#editNetwork").removeClass('btn-forbidden');
            	$("#limitdisk").removeClass('btn-forbidden');
            	$("#add2Router").removeClass('btn-forbidden');
            	
            	$("#usbManage").removeClass('btn-forbidden');
            }
        } else if (running == 0 && stopped > 0) {
        	$("#startup").removeClass('btn-forbidden');
        	
            if (total == 1) {
            	$("#off_migration").removeClass('btn-forbidden');
            	$("#editNetwork").removeClass('btn-forbidden');
            	$("#limitdisk").removeClass('btn-forbidden');
         
            	$("#usbManage").removeClass('btn-forbidden');
            }
        }
    }
     
    
    if (total == 1 && stopped == 1) {
    	$("#vm-to-template").removeClass('btn-forbidden');
    	$("#off_migration").removeClass('btn-forbidden');
    }
    if (total == 1 && running == 1) {
    	$("#migration").removeClass('btn-forbidden');
    	$("#joinNetwork").removeClass('btn-forbidden');
    	
    	$("#add2Router").removeClass('btn-forbidden');
    	$("#usbManage").removeClass('btn-forbidden');
    }
});

$("#creatVMISO").on("click", function (event) {
	event.preventDefault();
    $('#InstanceModalContainer').load('instanceiso/create', '', function () {
        $('#InstanceModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});


//网络配置
$("#editNetwork").on("click", function (event) {
	event.preventDefault();
	var uuid;
	var isrun;
	$('input[name="vmrow"]:checked').each(function () {
    	uuid = $(this).parent().parent().attr("rowid");
    	var stateicon = $(this).parent().parent().find('[name="stateicon"]');
    	if (stateicon.hasClass('icon-running')) {
            isrun = "run";
        } else if (stateicon.hasClass('icon-stopped')) {
            isrun = "stop";
        }
	});
    $('#InstanceModalContainer').load('admin/modal/modifynetwork', {"type":type, "uuid":uuid, "isrun":isrun} , function () {
        $('#InstanceModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

//usb接口设备
$("#usbManage").on("click", function (event) {
	event.preventDefault();
	var uuid;
	$('input[name="vmrow"]:checked').each(function () {
    	uuid = $(this).parent().parent().attr("rowid");
	});
    $('#usbManageContainer').load('admin/modal/modifyUSB', {"type":type, "uuid":uuid}, function () {
        $('#usbManageContainer').modal({
            backdrop: false,
            show: true
        });
    });
});


$("#touser").on("click", function (event) {
	event.preventDefault();
	var userName;
	$('input[name="vmrow"]:checked').each(function () {
		userName = $(this).parent().parent().attr("userName");
	});
    var total = 0;
    var vmuuid = null;
    var vmUid = null;
    $('input[name="vmrow"]:checked').each(function () {
        total++;
        vmuuid = $(this).parent().parent().attr("rowid");
        vmUid = $(this).parent().parent().attr("vmuid");
    });
    if(total>1){
    	 $("#touser").addClass('btn-forbidden');
    }
    
	var showMessage ='<div class="alert alert-info" style="margin:5px 5px 5px">选择的虚拟机已被用户占用！如更改，原用户将无法继续使用！</div>';
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: '提示',
        buttons: {
            main: {
                label: "继续",
                className: "btn-primary",
                callback: function () {
                	$('#InstanceModalContainer').load('admin/modal/distributeVm2User', {"vmuuid":vmuuid, "userName":userName,"vmUid":vmUid} , function () {
                        $('#InstanceModalContainer').modal({
                            backdrop: false,
                            show: true
                        });
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
});
$("#vm-to-template").on("click", function(event) {
	event.preventDefault();
	$('#InstanceModalContainer').load('admin/modal/vmtoimage','', function () {
        $('#InstanceModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});


$("#limitdisk").on("click", function () {
	 $('#InstanceModalContainer').load('admin/modal/limitdisk','', function () {
	        $('#InstanceModalContainer').modal({
	            backdrop: false,
	            show: true
	        });
	    });
});

$("#clearlimit").on("click", function () {
	showbox(3);
});


$('#add2Router').on('click', function (event) {
    event.preventDefault();
    $('#add2RouterModalContainer').load('admin/modal/instanceAdd2Router', '', function () {
        $('#add2RouterModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});
/*$("#all-star").on("click", function () {
    $("#selectimportant").text("全部");
    importance = 6;
    reloadList(1);
});

$("#none-star").on("click", function () {
    $("#selectimportant").text("未标星");
    importance = 0;
    reloadList(1);
});

$("#one-star").on("click", function () {
    $("#selectimportant").text("一颗星");
    importance = 1;
    reloadList(1);
});

$("#two-star").on("click", function () {
    $("#selectimportant").text("二颗星");
    importance = 2;
    reloadList(1);
});

$("#three-star").on("click", function () {
    $("#selectimportant").text("三颗星");
    importance = 3;
    reloadList(1);
});

$("#four-star").on("click", function () {
    $("#selectimportant").text("四颗星");
    importance = 4;
    reloadList(1);
});

$("#five-star").on("click", function () {
    $("#selectimportant").text("五颗星");
    importance = 5;
    reloadList(1);
});*/

$("#select-instance").on("click", function () {
    $("#selecttype").text("主机");
    type = "instance";
});

$("#hostall").on("click", function () {
    $("#selecthost").text("全部");
    host = "all";
    reloadList(1);
});

function getInfoList() {
    var infoList = "";
    var typeStr = "i";
    $('input[name="vmrow"]:checked').each(function () {
        infoList += "[" + typeStr + "-" + $(this).parent().parent().attr("rowid").substring(0, 8) + "]&nbsp;";
    });
    return infoList;
}

function showbox(type) {
    var infoList = getInfoList();
    var infoArray = new Array("启动主机","暂停主机","恢复主机", "关闭主机", "销毁主机","取消硬盘限速");
    var showMessage = '';
    var showTitle = '';
    if (type==3) {
        showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">1. 强制关机会丢失内存中的数据<br/>'
            + '2. 为保证数据的完整性，请在强制关机前暂停所有文件的写操作，或进行正常关机。</div>'
            + '<div class="item" style="margin:0"><div class="controls" style="margin-left:100px">'
            + '<label class="inline"><input type="checkbox" id="force">&nbsp;强制关机</label></div></div>';
        showTitle = infoArray[type] + '&nbsp;' + infoList + '?';
    } else if(type==1){
    	 showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">1. 暂停主机导致相关应用停止运行<br/>'
             + '2. 请你谨慎操作。</div>'
             + '<div class="item" style="margin:0"><div class="controls" style="margin-left:100px">'
             + '<label class="inline"><input type="checkbox" id="force">&nbsp;暂停主机</label></div></div>';
         showTitle = infoArray[type] + '&nbsp;' + infoList + '?';
    	
    } else if(type==2){
    	
    	showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">1.开启主机运行相关应用<br/>'
            + '2. 你确定操作么？</div>'
            + '<div class="item" style="margin:0"><div class="controls" style="margin-left:100px">'
            + '<label class="inline"><input type="checkbox" id="force">&nbsp;恢复主机</label></div></div>';
        showTitle = infoArray[type] + '&nbsp;' + infoList + '?';
    }else{
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
                        if (type == 0) {
                            startVM(uuid);
                        }else if(type == 1){
                        	 var force = $('#force')[0].checked;
                             pauseVM(uuid, force);
                        	
                        } else if (type == 2) {
                        	var force = $('#force')[0].checked;
                            restartVM(uuid, force);
                        	
                        }else if (type == 3) {
                            var force = $('#force')[0].checked;
                            shutdownVM(uuid, force);
                        } else if (type == 4) {
                            destroyVM(uuid);
                        } else if (type == 5) {
                        	clearLimit(uuid);
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

$('#off_migration').on('click', function (event) {
    event.preventDefault();
    $('#InstanceModalContainer').load('admin/modal/migration', {"type":"off"}, function () {
        $('#InstanceModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#migration').on('click', function (event) {
    event.preventDefault();
    $('#InstanceModalContainer').load('admin/modal/migration',{"type":"on"}, function () {
        $('#InstanceModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});


$('#startup').on('click', function (event) {
    event.preventDefault();
    showbox(0);
});

$('#pause').on('click', function (event) {
    event.preventDefault();
    showbox(1);
});

$('#restart').on('click', function (event) {
    event.preventDefault();
    showbox(2);
});
$('#shutdown').on('click', function (event) {
    event.preventDefault();
    showbox(3);
});

$('#destroy').on('click', function (event) {
    event.preventDefault();
    showbox(4);
});

$('#export').on('click', function (event){
	event.preventDefault();
	/*var vmuuid = '';
	$('input[name="vmrow"]:checked').each(function () {
       vmuuid += $(this).parent().parent().attr("rowid")+',';
    });*/
	$.ajax({
		type: 'post',
		async: false,
        url: 'VMAction/Export',
        dataType: 'json',
        success: function (obj) {
        	$('#filedownload').prop('href',$("#base_url").val()+'/file/'+obj.filename);
        	$('#download').click();
        }
	});
	//alert('导出');
});

function loadList(action, page, limit, str) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: action,
        data: {page: page, limit: limit, host: host, importance: importance, userid: userid, type: type},
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
                var vmuuid = obj.vmid;
                var vmName = decodeURIComponent(obj.vmname);
                var userName = decodeURIComponent(obj.userName);
                var state = obj.state;
                var vmUid = obj.vmUid;
                var showuuid = str + vmuuid.substring(0, 8);
                var showstr = "<a class='id'>" + showuuid + '</a>';
                if (obj.level == 0) {
                	showstr = showstr + '<a class="console" data-uuid=' + vmuuid + '><img src="'+$("#base_url").val()+'img/user/console.png"></a>';
                }
                var iconStr = new Array("stopped", "running", "process", "process", "process", "process", "process","paused");
                var nameStr = new Array("已关机", "正常运行", "创建中", "销毁中", "启动中", "关机中", "重启中","已暂停");
                var stateStr = '<td><span class="icon-status icon-' + iconStr[state] + '" name="stateicon">'
                    + '</span><span name="stateword">' + nameStr[state] + '</span></td>';
                var cpu = obj.cpu;
                cpu = cpu + "&nbsp;核";
                var memory = obj.memory;
                if (memory < 1024) {
                    memory = memory + "&nbsp;MB";
                } else {
                    memory = memory / 1024 + "&nbsp;GB";
                }
                var ip = obj.ip;
                var vlan = obj.vlan;
                var starArray = "";
                var importanceTem = obj.importance;
                starArray += '<div id="star"><ul>';
                for (var j = 0; j < 5; j++) {
                    if (importanceTem > 0) {
                        starArray += '<li><a href="javascript:;"><span class="glyphicon glyphicon-star"></span></a></li>';
                        importanceTem--;
                    } else {
                        starArray += '<li><a href="javascript:;"><span class="glyphicon glyphicon-star-empty"></span></a></li>';
                    }

                }
                starArray += '</ul></div>';
                var thistr = '<tr rowid="' + vmuuid + '" userName="'+userName+'" vmuid='+vmUid+' vmName='+vmName+'><td class="rcheck"><input type="checkbox" name="vmrow" level="'+obj.level+'"></td><td name="console">' + showstr 
                	+ '</td><td name="userName">' + userName + '</td><td name="vmname">'
                    + vmName + '</td>' + stateStr + /*'<td id="vmimportance" star="' + obj.importance + '">' + starArray + '</td>*/'<td name="cpuCore">'
                    + cpu + '</td><td name="memoryCapacity">'
                    + memory + '</td><td name="sip">' + vlan.substr(0, vlan.length-1) + '</td><td name="createtime" class="time">' + decodeURIComponent(obj.createdate) + '</td></tr>';
                tableStr += thistr;
            }
            $('#tablebody').html(tableStr);
        }
    });
}

function getVMList(page, limit) {
    $('#tablebody').html("");
    if (type == "instance") {
        loadList('VMAction/AdminList', page, limit, "i-");
    }
}

function adminstart(action, uuid) {
	$('#status-check').prop('disabled', true);
    $.ajax({
        type: 'get',
        url: action,
        data: {uuid: uuid},
        dataType: 'json',
        success: function (operationtype) {
        	$('#status-check').prop('disabled', false);
        	var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
        	if(operationtype){
        		thistr.find('[name="stateicon"]').removeClass("icon-process");
        	    thistr.find('[name="stateicon"]').addClass('icon-running');
        	    thistr.find('[name="stateword"]').text('正常运行');
        	}else{
        		thistr.find('[name="stateword"]').text('已关机');
        	}
        }
    });
}

function startVM(uuid) {
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-stopped");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('启动中');
    if (type == "instance") {
        adminstart('VMAction/AdminStartUp', uuid);
    }
}

function clearLimit(uuid) {
    $.ajax({
        type: 'post',
        url: 'VMAction/ClearLimit',
        data: {vmUuid: uuid},
        dataType: 'json',
        success: function (data) {
        }
    });
}

function adminshut(action, uuid, force) {
	$('#status-check').prop('disabled', true);
    $.ajax({
        type: 'get',
        url: action,
        data: {uuid: uuid, force: force},
        dataType: 'text',
        success: function (operationtype) {
        	$('#status-check').prop('disabled', false);
        	var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
        	if(operationtype=='shutdownVM'){
        		thistr.find('[name="stateicon"]').removeClass("icon-process");
        	    thistr.find('[name="stateicon"]').addClass('icon-stopped');
        	    thistr.find('[name="stateword"]').text('已关机');
        	}
        }
    });
}

function restartAction(action, uuid, force) {
	$('#status-check').prop('disabled', true);
    $.ajax({
        type: 'get',
        url: action,
        data: {uuid: uuid, force: force},
        dataType: 'text',
        success: function (operationtype) {
        	$('#status-check').prop('disabled', false);
        	var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
        	if(operationtype=='restartVM'){
        		thistr.find('[name="stateicon"]').removeClass("icon-process");
        	    thistr.find('[name="stateicon"]').addClass('icon-running');
        	    thistr.find('[name="stateword"]').text('正常运行');
        	}
        }
    });
}
function pauseAction(action, uuid, force) {
	$('#status-check').prop('disabled', true);
    $.ajax({
        type: 'get',
        url: action,
        data: {uuid: uuid, force: force},
        dataType: 'text',
        success: function (operationtype) {
        	$('#status-check').prop('disabled', false);
        	var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
        	if(operationtype=='pauseVM'){
        	    thistr.find('[name="stateicon"]').removeClass('icon-process');
        	    thistr.find('[name="stateicon"]').addClass("icon-paused");
        	    thistr.find('[name="stateword"]').text('已暂停');
        	}
        }
    });
}

function shutdownVM(uuid, force) {
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-running");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('关机中');
    thistr.find('.console').remove();
    if (type == "instance") {
        adminshut('VMAction/AdminShutDown', uuid, force);
    }
}

function pauseVM(uuid, force) {
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-running");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('暂停中');
    thistr.find('.console').remove();
    if (type == "instance") {
        pauseAction('VMAction/AdminStopHost', uuid, force);
    }
}

function restartVM(uuid, force) {
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-stopped");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('恢复中');
    thistr.find('.console').remove();
    if (type == "instance") {
        restartAction('VMAction/RestartHostVm', uuid, force);
    }
}
function initPage() {
    $.ajax({
        type: 'get',
        url: 'HostAction/ALLList',
        dataType: 'json',
        success: function (array) {
            $.each(array, function (index, json) {
                $("#select-server").append('<li><a id="host' + index + '" hostUuid="' + json.hostUuid + '"><sapn chalss="glyphicon glyphicon-tasks"></span>' + json.hostName + '</a></li>');
                $("#host" + index).on("click", function () {
                    $("#selecthost").text(json.hostName);
                    host = json.hostUuid;
                    reloadList(1);
                });
            });
        }
    });
}

$(function () {

    $(document).on('mouseover', '#star li', function () {
        var iScore = ($(this).index() + 1);
        for (i = 0; i < 5; i++) {
            if (i < iScore) {
                $(this).parent("ul").find("li").eq(i).find("span").removeClass("glyphicon-star-empty");
                $(this).parent("ul").find("li").eq(i).find("span").addClass("glyphicon-star");
            } else {
                $(this).parent("ul").find("li").eq(i).find("span").removeClass("glyphicon-star");
                $(this).parent("ul").find("li").eq(i).find("span").addClass("glyphicon-star-empty");
            }
        }
    });

    $(document).on('mouseout', '#star li', function () {
        var iScore = $(this).parents("#vmimportance").attr("star");
        for (i = 0; i < 5; i++) {
            if (i < iScore) {
                $(this).parent("ul").find("li").eq(i).find("span").removeClass("glyphicon-star-empty");
                $(this).parent("ul").find("li").eq(i).find("span").addClass("glyphicon-star");
            } else {
                $(this).parent("ul").find("li").eq(i).find("span").removeClass("glyphicon-star");
                $(this).parent("ul").find("li").eq(i).find("span").addClass("glyphicon-star-empty");
            }
        }
    });

    $(document).on('click', '#star li', function () {
        $(this).parents("#vmimportance").attr("star", $(this).index() + 1);
        var uuid = $(this).parents("tr").attr("rowid");
        bootbox.alert($(this).index());
        return;
        if (type == 'instance') {
            updateStar('VMAction', $(this).index() + 1, uuid);
        }
    });

    function updateStar(action, num, uuid) {
        $.ajax({
            type: 'post',
            url: action + '/UpdateStar',
            data: {uuid: uuid, num: num},
            dataType: 'text',
            complete: function () {

            }
        });
    }
});

$('#tablebody').on('click', '.console', function (event) {
    event.preventDefault();
    var uuid = $(this).data("uuid");
    var vnc = $('#platformcontent').attr("novnc");
    var token = uuid.substring(0, 8);
    var url = vnc + "console.html?id=" + token;
    window.open(url, "novnc", 'height=600, width=810, top=0, left=0');
});

$('#status-check').on('click', function(){
	var vmUuid = '';
	$('input[name="vmrow"]:checked').each(function() {
		vmUuid = $(this).parent().parent().attr("rowid");
	});
	if(vmUuid == ''){
		bootbox.alert('请选中要校验的虚拟机！');
		return;
	}
	$.ajax({
		type : 'post',
		url : 'VMAction/CheckVMStatus',
		data : {vmUuid : vmUuid},
		dataType : 'json',
		complete : function() {
			reloadList(1);
		}
	});
});

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='vmrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='vmrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});

function destroyVM(uuid) {
	$('#status-check').prop('disabled', true);
    var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
    var thisicon = thistr.find('[name="stateicon"]');
    thisicon.removeClass("icon-stopped");
    thisicon.removeClass("icon-running");
    thisicon.addClass('icon-process');
    thistr.find('[name="stateword"]').text('销毁中');
    console.log(uuid);
    var total=0;
    $.ajax({
        type: 'get',
        url: 'VMAction/AdminDestoryVM',
        data: {uuid: uuid},
        dataType: 'text',
        complete: function(){
        	$('#status-check').prop('disabled', false);
        	thistr.remove();
        }
    });
}
