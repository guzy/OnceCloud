var group = "all";
initPage();
reloadList(1);

function initPage() {
    $.ajax({
        type: 'get',
        url: '/GroupAction/AllList',
        dataType: 'json',
        success: function (array) {
            $.each(array, function (index, json) {
                $("#select-group").append('<li><a id="group' + index + '" groupUuid="' + json.groupUuid + '"><sapn chalss="glyphicon glyphicon-tasks"></span>' + json.groupName + '</a></li>');
                $("#group" + index).on("click", function () {
                    $("#selectgroup").text(json.groupName);
                    group = json.groupUuid;
                    reloadList(1);
                });
            });
        }
    });
}

function reloadList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	getVMList(page, limit, search);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
	allDisable();
}

function removeAllCheck() {
	$('input[name="vmrow"]:checked').each(function() {
		$(this)[0].checked = false;
		$(this).change();
	});
}

function allDisable() {
	$("#startup").attr("disabled", true).addClass('btn-disable');
	$("#shutdown").attr("disabled", true).addClass('btn-disable');
	$("#restart").addClass('btn-forbidden');
	$("#destroy").addClass('btn-forbidden');
	$("#backup").addClass('btn-forbidden');
	$("#image").addClass('btn-forbidden');
	$('#adjust').addClass('btn-forbidden');
	$('#updateVMs').addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function(event) {
	event.preventDefault();
	allDisable();
	var running = 0;
	var process = 0;
	var stopped = 0;
	var total = 0;
	$('input[name="vmrow"]:checked').each(function() {
		var stateicon = $(this).parent().parent()
				.find('[name="stateicon"]');
		if (stateicon.hasClass('icon-running')) {
			running++;
		} else if (stateicon.hasClass('icon-stopped')) {
			stopped++;
		} else {
			process++;
		}
		total++;
	});
	if(total>0){
		$('#updateVMs').removeClass('btn-forbidden');
		
		if (process == 0) {
			$("#destroy").removeClass('btn-forbidden');
			if (running > 0 && stopped == 0) {
				$("#shutdown").attr('disabled', false).removeClass('btn-disable');
				$("#restart").removeClass('btn-forbidden');
			} else if (running == 0 && stopped > 0) {
				$("#startup").attr('disabled', false).removeClass('btn-disable');
			}
		}
		if (total == 1 && stopped == 1) {
			$("#backup").removeClass('btn-forbidden');
			$("#image").removeClass('btn-forbidden');
			$('#adjust').removeClass('btn-forbidden');
		}
	}
});

function getVMList(page, limit, search) {
	$('#tablebody').html("");
	$.ajax({
		type : 'get',
		url : '/VMAction/VMList',
		data : {
			page : page,
			limit : limit,
			search : search,
			groupUuid : group
		},
		dataType : 'json',
		success : function(array) {
			var totalnum = array[0];
			var totalp = 1;
			if (totalnum != 0) {
				totalp = Math.ceil(totalnum / limit);
			}
			options = {
				totalPages : totalp
			};
			$('#pageDivider').bootstrapPaginator(options);
			pageDisplayUpdate(page, totalp);
			var tableStr = "";
			for (var i = 1; i < array.length; i++) {
				var obj = array[i];
				var vmuuid = obj.vmid;
				var vmName = decodeURIComponent(obj.vmname);
				var state = obj.state;
				var showuuid = "i-" + vmuuid.substring(0, 8);
				var showstr = "<a class='id'>" + showuuid + '</a>';
				var iconStr = new Array("stopped", "running", "process",
						"process", "process", "process", "process");
				var nameStr = new Array("已关机", "正常运行", "创建中", "销毁中", "启动中",
						"关机中", "重启中");
				var stateStr = '<td><span class="icon-status icon-'
						+ iconStr[state] + '" name="stateicon">'
						+ '</span><span name="stateword">' + nameStr[state]
						+ '</span></td>';
				if (state == 1) {
					showstr = showstr + '<a class="console" data-uuid='
							+ vmuuid
							+ '><img src="../img/user/console.png"></a>';
				}
				var cpu = obj.cpu;
				cpu = cpu + "&nbsp;核";
				var memory = obj.memory;
				if (memory < 1024) {
					memory = memory + "&nbsp;MB";
				} else {
					memory = memory / 1024 + "&nbsp;GB";
				}
				var backupdate = obj.backupdate + "";
				var backupStr = decodeURIComponent(backupdate);
				if (backupdate == "") {
					backupStr = '<a class="glyphicon glyphicon-camera backup" url="'+ basePath + 'snapshot/create?rsid='
							+ vmuuid
							+ '&rstype=instance&rsname='
							+ vmName
							+ '"></a>';
				}
				var hosttype = obj.hosttype;//主机类型
				
				var thistr = '<tr rowid="'
						+ vmuuid +'" hosttype = "'+hosttype
						+ '" style="background-color:'+obj.groupColor+'"><td class="rcheck"><input type="checkbox" name="vmrow"></td><td name="console">'
						+ showstr + '</td><td name="vmname">' + vmName
						+ '</td>' + stateStr + '<td name="cpuCore">' + cpu
						+ '</td><td name="memoryCapacity">' + memory
						+ '</td><td name="mac">' + obj.vmMac
						+ '</td><td name="plateform">' + obj.vmplatform
						+ '</td><td name="groupName">'+ obj.groupName
						+ '</td><td name="backuptime" class="time">'
						+ backupStr
						+ '</td><td name="createtime" class="time">'
						+ decodeURIComponent(obj.createdate) + '</td></tr>';
				tableStr += thistr;
			}
			$('#tablebody').html(tableStr);
		}
	});
}

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
		url : '/VMAction/CheckVMStatus',
		data : {vmUuid : vmUuid},
		dataType : 'json',
		complete : function() {
			reloadList(1);
		}
	});
});

$("#groupall").on("click", function () {
    $("#selectgroup").text("全部");
    group = "all";
    reloadList(1);
});

$('#tablebody').on('click', '.console', function(event) {
	event.preventDefault();
	var uuid = $(this).data("uuid");
	var vnc = $('#platformcontent').attr("novnc");
	var token = uuid.substring(0, 8);
	var url = vnc + "console.html?id=" + token;
	window.open(url, "novnc", 'height=600, width=810, top=0, left=0');
});
		
$('#tablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var uuid = $(this).parent().parent().attr('rowid');
    var form = $("<form></form>");
    form.attr("action", "/instance/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="instanceUuid" value="' + uuid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='vmrow']").each(function(){
			this.checked=true;
		});	
		$('#updateVMs').removeClass('btn-forbidden');
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='vmrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});