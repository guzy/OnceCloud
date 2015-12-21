/**
 * 容器管理
 */
var group = "all";
reloadList(1);

function reloadList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	getContainerList(page, limit, search);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
	allDisable();
}

function removeAllCheck() {
	$('input[name="containerrow"]:checked').each(function() {
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
}

$('#tablebody').on('change', 'input:checkbox', function(event) {
	event.preventDefault();
	allDisable();
	var running = 0;
	var process = 0;
	var stopped = 0;
	var total = 0;
	$('input[name="containerrow"]:checked').each(function() {
		var stateicon = $(this).parent().parent().find('[name="stateicon"]');
		if (stateicon.hasClass('icon-running')) {
			running++;
		} else if (stateicon.hasClass('icon-stopped')) {
			stopped++;
		} else {
			process++;
		}
		total++;
	});
	$("#image").removeClass('btn-forbidden');
	$("#restart").removeClass('btn-forbidden');
	$("#delete").removeClass('btn-forbidden');
	$("#stop").removeClass('btn-forbidden');
});

//
$('#stop').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="containerrow"]:checked').each(function () {
        infoList += "[c-" + $(this).parent().parent().attr("rowid").substr(0,8) + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;停止容器&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="containerrow"]:checked').each(function () {
                    	stopContainer($(this).parent().parent().attr("rowid"));
                        
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

//
function stopContainer(containerId) {
    $.ajax({
        type: 'get',
        url: 'ContainerAction/Stop',
        data: {containerId: containerId},
        dataType: 'text',
        success: function () {
            reloadList(1);
        }
    });
}
//
$('#delete').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="containerrow"]:checked').each(function () {
        infoList += "[c-" + $(this).parent().parent().attr("rowid").substr(0, 8) + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除容器&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="containerrow"]:checked').each(function () {
                        deleteContainer($(this).parent().parent().attr("rowid"));
                        
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

//
function deleteContainer(containerId) {
    $.ajax({
        type: 'get',
        url: 'ContainerAction/Delete',
        data: {containerId: containerId},
        dataType: 'text',
        success: function () {
            reloadList(1);
        }
    });
}

//
$('#restart').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="containerrow"]:checked').each(function () {
        infoList += "[c-" + $(this).parent().parent().attr("rowid").substr(0, 8) + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;重启容器&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="containerrow"]:checked').each(function () {
                        restartContainer($(this).parent().parent().attr("rowid"));
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

//
function restartContainer(containerId) {
    $.ajax({
        type: 'get',
        url: 'ContainerAction/Restart',
        data: {containerId: containerId},
        dataType: 'text',
        success: function () {
            reloadList(1);
        }
    });
}

//
function getContainerList(page, limit, search) {
	$('#tablebody').html("");
	$.ajax({
		type : 'get',
		url : '/ContainerAction/ContainerList',
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
				var containerid = obj.containerid;
				var containername = obj.containername;
				
				var iconStr = new Array("stopped", "running");
				var nameStr = new Array("已关机", "正常运行");
				var stateStr = '<td><span class="icon-status icon-'
						+ iconStr[obj.containerstatus] + '" name="stateicon">'
						+ '</span><span name="stateword">' + nameStr[obj.containerstatus]
						+ '</span></td>';
				var convlan = obj.containervlan;
				var containercpu = obj.containercpu;
				var containermem = obj.containermem;
				var containerimage = obj.containerimage;
				var containerport = obj.containerport;
				var containertime =obj.containertime;
				var thistr = '<tr rowid="'
						+ containerid
						+ '"><td class="rcheck"><input type="checkbox" name="containerrow"></td>'
						+ '<td name="conid"><a class="id">' + 'c-'+containerid.substr(0, 8)+ '</a></td>'
						+ '<td name="conname">' + containername+'</td>'
						+ stateStr
						+ '<td name="containerimage">' + containerimage+'</td>'
						/*+ '<td name="containercpu">' + containercpu+"核"+'</td>'
						+ '<td name="containermem">' + containermem+"G"+'</td>'*/
						/*+ '<td name="containerimage"'> + containerimage +'</td>'*/
						+ '<td name="containerport">' + containerport+'</td>'
						+ '<td name="containertime">' + containertime+'</td>'
						+ '</tr>';
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
    form.attr("action", "/container/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="conUuid" value="' + uuid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='containerrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='containerrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});