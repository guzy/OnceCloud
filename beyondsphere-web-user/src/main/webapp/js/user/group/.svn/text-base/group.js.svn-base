
reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    getGroupList(page, limit);
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
	$("#modify").addClass('btn-forbidden');
    $("#delete").addClass('btn-forbidden');
    $('#bindgroup').addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var total = 0;
	var running = 0;
    $('input[name="grouprow"]:checked').each(function () {
        var stateicon = $(this).parent().parent()
        total++;
    });
    if (total == 1) {
        $("#modify").removeClass('btn-forbidden');
        $("#delete").removeClass('btn-forbidden');
        $('#bindgroup').removeClass('btn-forbidden');
    }
});

function getGroupList(page, limit) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: '/GroupAction/GroupList',
        data: {page: page, limit: limit},
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
                var groupUuid = obj.groupUuid;
                var groupName = decodeURIComponent(obj.groupName);
                var groupColor = obj.groupColor;
                var groupDes = decodeURIComponent(obj.groupDes);
                var createTime = decodeURIComponent(obj.createTime);
                var showuuid = "gl-" + groupUuid.substr(0, 8);
                var showstr = "<a class='id'>" + showuuid + '</a>';
                var thistr = '<tr rowid="' + groupUuid + '">'
                		   + '<td class="rcheck"><input type="checkbox" name="grouprow"></td><td name="console">' 
                		   + showstr 
                		   + '</td><td name="groupName">'
                		   + groupName 
                		   + '</td><td name="groupColor">'
                		   + '<input type = "color" value = "'+ groupColor
                		   + '" style="border:solid 0px; padding:0px 0px; width:80%; background-color:#fff" disabled>'
                		   + '</td><td name="groupDes">'
                		   + groupDes
                		   + '</td><td name="createtime" class="time">' 
                		   + createTime 
                		   + '</td></tr>';
                tableStr += thistr;
            }
            $('#tablebody').html(tableStr);
        }
    });
}

$('#tablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var uuid = $(this).parent().parent().attr('rowid');
    var form = $("<form></form>");
    form.attr("action", "/group/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="groupUuid" value="' + uuid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#create').on('click', function (event) {
    event.preventDefault();
    $('#GroupModalContainer').load($(this).attr('url'), '', function () {
        $('#GroupModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#modify').on('click', function (event) {
    event.preventDefault();
    var groupUuid = "";
    $('input[name="grouprow"]:checked').each(function () {
    	groupUuid = $(this).parent().parent().attr("rowid")
    });
    $('#GroupModalContainer').load($(this).attr('url'), {groupUuid:groupUuid}, function () {
        $('#GroupModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#delete').on('click', function(event){
	event.preventDefault();
    showbox();
});

$('#bindgroup').on('click', function (event) {
    event.preventDefault();
    var uuid = "";
    $('input[name="grouprow"]:checked').each(function () {
        uuid = $(this).parent().parent().attr("rowid");
    });
    $('#GroupModalContainer').load($(this).attr('url'), {"uuid": uuid}, function () {
        $('#GroupModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

function showbox() {
    var infoList = getInfoList();
    var showMessage = '';
    var showTitle = '';
    showMessage = '<div class="alert alert-info" style="margin:10px">'
        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;确定要删除分组&nbsp;' + infoList + '?</div>';
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
                    $('input[name="grouprow"]:checked').each(function () {
                        var groupUuid = $(this).parent().parent().attr("rowid");
                        deleteGroup(groupUuid);
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

function getInfoList() {
    var infoList = "";
    $('input[name="grouprow"]:checked').each(function () {
        infoList += "[gl-" + $(this).parent().parent().attr("rowid").substr(0, 8) + "]&nbsp;";
    });
    return infoList;
}

function deleteGroup(groupUuid){
	$.ajax({
        type: 'get',
        url: '/GroupAction/Destory',
        data: {groupUuid: groupUuid},
        dataType: 'text',
        success: function (result) {
            if (result === "true") {
                $("#tablebody").find('[rowid="' + groupUuid + '"]').remove();
            } 
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

function stripscript(s){ 
	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]") 
	var rs = ""; 
	for (var i = 0; i < s.length; i++) { 
	rs = rs+s.substr(i, 1).replace(pattern, ''); 
} 
	if(s.length != rs.length){
		alert('分组类别不能包含特殊字符');
	}
} 
