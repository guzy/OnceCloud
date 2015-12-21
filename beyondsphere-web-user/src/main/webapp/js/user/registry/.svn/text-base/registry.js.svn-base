/**
 * 仓库管理
 */
var group = "all";
reloadList(1);

function reloadList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	getRegistryList(page, limit, search);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
	allDisable();
}

function removeAllCheck() {
	$('input[name="regrow"]:checked').each(function() {
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
	$('input[name="regrow"]:checked').each(function() {
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
	$("#deleteReg").removeClass('btn-forbidden');
	$("#stop").removeClass('btn-forbidden');
});

//
$('#deleteReg').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="regrow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("rowid") + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除仓库&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="regrow"]:checked').each(function () {
                        deleteRegistry($(this).parent().parent().attr("rowid"));
                        
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
function deleteRegistry(uuid) {
    $.ajax({
        type: 'get',
        url: '/RegistryAction/deleteRegistry',
        data: {uuid: uuid},
        dataType: 'json',
        success: function () {
                reloadList(1);
        }
    });
}

//
function getRegistryList(page, limit, search) {
	$('#tablebody').html("");
	$.ajax({
		type : 'get',
		url : '/RegistryAction/registryList',
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
				var regid = obj.regid;
				var regname = obj.regname;
				var regip = obj.regip;
				var regport = obj.regport;
				if(obj.regstatus == 1){
					var regstatus ="运行";
				}else{
					var regstatus ="关机";
				}
				var regtime = obj.regtime;
				var thistr = '<tr rowid="'
						+ regid
						+ '"><td class="rcheck"><input type="checkbox" name="regrow"></td>'
						+ '<td name="regid"><a class="id">' + 'r-'+regid.substr(0, 8)+ '</a></td>'
						+ '<td name="regname">' + regname+'</td>'
						+ '<td name="regip">' + regip+'</td>'
						+ '<td name="regport">'+ regport+'</td>'
						+ '<td name="regstatus">' + regstatus+'</td>'
						+ '<td name="regtime">' + regtime+'</td>'
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
		$("input[name='regrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='regrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});