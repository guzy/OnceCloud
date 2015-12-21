reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getRouterList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="rtrow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}

function allDisable() {
    $("#startup").addClass('btn-forbidden');
    $("#shutdown").addClass('btn-forbidden');
    $("#destroy").addClass('btn-forbidden');
}

$('#router_tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var running = 0;
    var process = 0;
    var stopped = 0;
    var total = 0;
    $('input[name="rtrow"]:checked').each(function () {
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
    if (total > 0 && process == 0) {
        $("#destroy").removeClass('btn-forbidden');
        if (running > 0 && stopped == 0) {
            $("#shutdown").removeClass('btn-forbidden');
        } else if (running == 0 && stopped > 0) {
            $("#startup").removeClass('btn-forbidden');
        }
    }
});

//$('#create').on('click', function (event) {
//    event.preventDefault();
//    $('#RouterModalContainer').load($(this).attr('url'), '', function () {
//        $('#RouterModalContainer').modal({
//            backdrop: false,
//            show: true
//        });
//    });
//});

function getRouterList(page, limit, search) {
//    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'RouterAction/RouterList',
        data: {page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            var total = array[0];
            var totalp = 1;
            if (total != 0) {
                totalp = Math.ceil(total / limit);
            }
            options = {
                totalPages: totalp
            };
            $('#pageDivider').bootstrapPaginator(options);
            pageDisplayUpdate(page, totalp);
            var tableStr = "";
            for (var i = 1; i < array.length; i++) {
                var obj = array[i];
                var uuid = obj.uuid;
                var name = decodeURIComponent(obj.name);
                var username = decodeURIComponent(obj.username);
                var power = obj.power;
                var showid = "rt-" + uuid.substring(0, 8);
                var ips=obj.ips;
                var ip = obj.ip;
                var network;
                if (ip == "") {
                    network = '<a>(基础网络)</a>';
                } else {
                    network = '<a>(基础网络)&nbsp;/&nbsp;' + ip + '</a>';
                }
                var iconStr = new Array("stopped", "running", "process", "process", "process", "process");
                var nameStr = new Array("已关机", "活跃", "创建中", "销毁中", "启动中", "关机中");
                var stateStr = '<td><span class="icon-status icon-' + iconStr[power] + '" name="stateicon">'
                    + '</span><span name="stateword">' + nameStr[power] + '</span></td>';
                var thistr = '<tr rowid="' + uuid + '" rtname="' + name + '"><td class="rcheck"><input type="checkbox" name="rtrow"></td><td><a class="rtid">' + showid + '</a></td>'
                    + '<td>' + name + '</td><td>' + username + '</td>' + stateStr + '<td name="sip">' + network + '</td><td name="eip">' + ips + '</td>'
                    + '<td class="time">' + decodeURIComponent(obj.createdate) + '</td></tr>';
                tableStr += thistr;
            }
            $('#router_tablebody').html(tableStr);
        }
    });
}

$('#router_tablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var uuid = $(this).parent().parent().attr('rowid');
    var form = $("<form></form>");
    form.attr("action", "router/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="routerUuid" value="' + uuid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

function getInfoList() {
    var infoList = "";
    $('input[name="rtrow"]:checked').each(function () {
        infoList += "[rt-" + $(this).parent().parent().attr("rowid").substring(0, 8) + "]&nbsp;";
    });
    return infoList;
}

function showbox(type) {
    var infoList = getInfoList();
    var infoArray = new Array("启动路由器", "销毁路由器", "关闭路由器");
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
                    $('input[name="rtrow"]:checked').each(function () {
                        var uuid = $(this).parent().parent().attr("rowid");
                        if (type == 0) {
                            startRouter(uuid);
                        } else if (type == 1) {
                            destroyRouter(uuid);
                        } else if (type == 2) {
                            var force = $('#force')[0].checked;
                            shutdownRouter(uuid, force);
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
$('#destroy').on('click', function (event) {
    event.preventDefault();
    showbox(1);
});
$('#shutdown').on('click', function (event) {
    event.preventDefault();
    showbox(2);
});

function getVlanList(page, limit, search) {
    $('#tablebody').html("");
}

function startRouter(uuid) {
    var thistr = $("#router_tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-stopped");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('启动中');
    $.ajax({
        type: 'post',
        url: 'RouterAction/StartUp',
        data: {uuid: uuid},
        dataType: 'text',
        complete:function(data){
        	 reloadList(1);
        }
    });
}

function destroyRouter(uuid) {
	 $.ajax({
	        type: 'get',
	        url: 'NetworkAction/checkRouter',
	        data: {routerid: uuid},
	        dataType: 'json',
	        async:false,
	        success: function (obj) {
	        	if(obj.result){
	        		deleteRouter(uuid);
	        	}else{
	        		var names=obj.netnames;
	        		bootbox.confirm({
	        			buttons: {  
	        			    cancel: {  
	        			        label: '取消',  
	        			    },
	        			    confirm: {  
	        			        label: '确认',  
	        			    }
	        			},  
	        		 	title: "提示信息",
	        			message: '<div style="margin:25px"><h5>'+names+'连接在该路由，删除此路由将导致'+names+'无法和vlan通讯,您确定要删除吗？<h5></div>',  
	        			callback: function(result) {  
	        			    if(result) {
	        			    	deleteRouter(uuid);
	        			    }
	        			}
	        		});
	        	}
        	}
	 });
	 
}

function deleteRouter(uuid){
	var thistr = $("#router_tablebody").find('[rowid="' + uuid + '"]');
	var thisicon = thistr.find('[name="stateicon"]');
	thisicon.removeClass("icon-stopped");
	thisicon.removeClass("icon-running");
	thisicon.addClass('icon-process');
	thistr.find('[name="stateword"]').text('销毁中');
	$.ajax({
		type: 'post',
		url: 'RouterAction/Destroy',
		data: {uuid: uuid},
		dataType: 'text',
		complete: function (obj) {
			reloadList(1);
			if (obj == "no") {
				thisicon.removeClass("icon-stopped");
				thisicon.removeClass('icon-process');
				thisicon.addClass("icon-running");
				thistr.find('[name="stateword"]').text('活跃');
				bootbox.dialog({
					message: '<div class="alert alert-danger" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;无法删除，依然有私有网络依赖于该路由器</div>',
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
			}else{
				//删除vxlan中路由信息
				 $.ajax({
					type : 'post',
					url : 'NetworkAction/clearRouter',
					data : {
						routerid : uuid
					},
					dataType : 'json',
					async : false,
					success : function(data) {
					}
				});
			}
		}
	});
}

function shutdownRouter(uuid, force) {
	 $.ajax({
	        type: 'get',
	        url: 'NetworkAction/checkRouter',
	        data: {routerid: uuid},
	        dataType: 'json',
	        async:false,
	        success: function (obj) {
	        	if(obj.result){
	        		shutdown_router(uuid,force);
	        	}else{
	        		var names=obj.netnames;
	        		bootbox.confirm({
	        			buttons: {  
	        				confirm: {  
	        			        label: '确认',  
	        			    },
	        			    cancel: {  
	        			        label: '取消',  
	        			    }
	        			},  
	        		 	title: "提示信息",
						message: '<div class="alert alert-danger" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;关闭此路由将导致'+names+'无法和vlan通讯!</div>',
	        			callback: function(result) {  
	        			    if(result) {
	        			    	shutdown_router(uuid,force);
	        			    }
	        			}
	        		});
	        	}
     	}
	 });
}


function shutdown_router(uuid,force){
	var thistr = $("#router_tablebody").find('[rowid="' + uuid + '"]');
    thistr.find('[name="stateicon"]').removeClass("icon-running");
    thistr.find('[name="stateicon"]').addClass('icon-process');
    thistr.find('[name="stateword"]').text('关机中');
    thistr.find('.console').remove();
	$.ajax({
		type: 'post',
		url: 'RouterAction/ShutDown',
		data: {uuid: uuid, force: force},
		dataType: 'text',
		complete:function(){
			reloadList(1);
		}
	});
}

$('#link').on('click', function (event) {
    event.preventDefault();
    var $checks= $('input[name="switchrow"]:checked').parent().parent();
    if($checks.length==1){
    	var routerid=$checks.eq(0).attr("routerid");
    	if(routerid!=""){
    		alert("该Vxlan已加入路由，请先离开路由！");
    		return;
    	}
    }else{
    	alert("请选择一个Vxlan操作");
    	return;
    }
    
    $('#VnetModalContainer').load($(this).attr('url'), '', function () {
        $('#VnetModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#unlink').on('click', function (event) {
    event.preventDefault();
    var $checks= $('input[name="switchrow"]:checked').parent().parent();
    var routerid=$checks.eq(0).attr("routerid");
    if($checks.length==1){
    	if(routerid==""){
    		alert("该Vxlan未加入路由！");
    		return;
    	}
    }else{
    	alert("请选择一个Vxlan操作");
    	return;
    }
    
    var uuid =$checks.eq(0).attr("id");;
    unlink(uuid);
    
});

function unlink(uuid) {
	var content = "<div class='alert alert-warning'>私有网络正在离开路由器</div>";
    $.ajax({
        type: 'post',
        url: 'NetworkAction/UnlinkRouter',
        data: {vnetId: uuid},
        dataType: 'json',
        success: function (obj) {
            if (obj.result) {
            	changeModelDiv(2);
            }else{
            	alert("离开路由失败!");
            }
        }
    });
}