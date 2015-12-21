reloadList();

$('#modify').on('click', function (event) {
    event.preventDefault();
    var url = $("#platformcontent").attr('basePath') + 'user/modal/modify';
    var groupUuid = $("#platformcontent").attr("groupUuid");
    var groupName = $("#groupName").text();
    var groupDes = $("#groupDes").text();
    $('#GroupModalContainer').load(url, {"modifyType": "group", "modifyUuid": groupUuid, "modifyName": groupName, "modifyDesc": groupDes}, function () {
        $('#GroupModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#basic-unbind').on('click', function (event) {
    event.preventDefault();
    var showMessage = '';
    var showTitle = '';
    showMessage = '<div class="alert alert-info" style="margin:10px">'
        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;确定要解除所有资源&nbsp;?</div>';
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
                	removeVMFromGroup();
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

$('.rule-refresh').on('click', function (event) {
    event.preventDefault();
    reloadList();
});

$('#addVMtoGroup').on('click', function(){
	event.preventDefault();
    var uuid =  $('#platformcontent').attr("groupUuid");
    $('#GroupModalContainer').load($(this).attr('url'), {"uuid": uuid}, function () {
        $('#GroupModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#deleteVMfromGroup').on('click', function(){
	$('input[name="vmrow"]:checked').each(function () {
		var vmUuid = $(this).parent().parent().attr("rowid");
		unbindVMFromGroup(vmUuid);
    });
});

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    var count = 0;
    $('input[name="vmrow"]:checked').each(function () {
        count++;
    });
    if (count == 0) {
        $('#deleteVMfromGroup').addClass('btn-disable').attr('disabled', true);
    } else {
        $('#deleteVMfromGroup').removeClass('btn-disable').attr('disabled', false);
    }
});

function reloadList() {
	getGroupBasic();
    var limit = $('#limit').val();
    var groupUuid = $('#platformcontent').attr("groupUuid");
    getVMOfGroupList(1, limit, groupUuid);
    options = {
        currentPage: 1
    }
    $('#pageDivider').bootstrapPaginator(options);
}

function getGroupBasic() {
    var groupUuid = $('#platformcontent').attr("groupUuid");
    $('#basic-list').html("");
    $.ajax({
        type: 'get',
        url: '/GroupAction/GetGroup',
        data: {groupUuid: groupUuid},
        dataType: 'json',
        success: function (obj) {
            var showid = "gl-" + groupUuid.substr(0, 8);
            var groupName = decodeURIComponent(obj.groupName);
            var createTime = obj.createTime;
            var rsTable = '';
            var vmTotal = obj.vmTotal;
            var groupDes = decodeURIComponent(obj.groupDes);
            $('#basic-list').html('<dt>ID</dt><dd><a href="javascript:void(0)">'
                + showid + '</a></dd><dt>名称</dt><dd id="groupName">'
                + groupName + '</dd><dt>描述</dt><dd id="groupDes">' + groupDes 
                + '</dd><dt>资源个数</dt><dd id="vmTotal">' + vmTotal
                + '</dd><dt>创建时间</dt><dd class="none">' + createTime + '</dd>');
        },
        error: function () {
        }
    });
}

function getVMOfGroupList(page, limit, groupUuid){
	 $('#tablebody').html("");
     $.ajax({
         type: 'get',
         url: '/GroupAction/VMListOfGroup',
         data: {uuid: groupUuid, page: page, limit: limit},
         dataType: 'json',
         success: function (array) {
             var totalnum = array[0];
             var totalp = 1;
             if (totalnum != 0) {
                 totalp = Math.ceil(totalnum / limit);
             }
             options = {
                 totalPages: totalp
             }
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
 				var thistr = '<tr rowid="'
 						+ vmuuid
 						+ '"><td class="rcheck"><input type="checkbox" name="vmrow"></td><td name="console">'
 						+ showstr + '</td><td name="vmname">' + vmName
 						+ '</td>' + stateStr + '<td name="timeUsed" class="time">'
 						+ decodeURIComponent(obj.timeUsed) + '</td></tr>';
 				tableStr += thistr;
 			}
 			$('#tablebody').html(tableStr);
         },
         error: function () {
         }
     });
}

function removeVMFromGroup(){
	var groupUuid = $('#platformcontent').attr("groupUuid");
	$.ajax({
	   type: 'post',
	   url: '/GroupAction/RemoveFromGroup',
	   data: {groupUuid: groupUuid},
	   dataType: 'text',
	   success: function (obj) {
		   reloadList();
	   },
	   error: function () {
	   }
    });
}

function unbindVMFromGroup(vmUuid){
	$.ajax({
	   type: 'post',
	   url: '/GroupAction/UnBindGroup',
	   data: {vmUuid: vmUuid},
	   dataType: 'text',
	   success: function (obj) {
		   reloadList();
	   },
	   error: function () {
	   }
    });
}

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='grouprow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='grouprow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});