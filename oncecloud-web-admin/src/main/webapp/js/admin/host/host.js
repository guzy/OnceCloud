reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getHostList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        }
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function allDisable() {
    $("#update").addClass('btn-forbidden');
    $("#delete").addClass('btn-forbidden');
    $("#add2pool").addClass('btn-forbidden');
    $("#remove4pool").addClass('btn-forbidden');
    $("#recover").addClass('btn-forbidden');
    $("#register").addClass('btn-forbidden');
    $("#power").addClass('btn-forbidden');
    $("#startServer").addClass('btn-forbidden');
    $("#stopServer").addClass('btn-forbidden');
    $("#power").attr('disabled',"disabled");
    $("#startServer").attr('disabled',"disabled");
    $("#stopServer").attr('disabled',"disabled");
}

function removeAllCheck() {
    var boxes = document.getElementsByName("hostrow");
    for (var i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
        $(boxes[i]).change();
    }
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var bindcount = 0;
    var unbindcount = 0;
    var count = 0;
    $('input[name="hostrow"]:checked').each(function () {
        var state = $(this).parent().parent().find('[state]')
            .attr('state');
        if (state == 'load') {
            bindcount++;
        } else {
            unbindcount++;
        }
        count++;
    });
    if (count == 1) {
        var powerstatus = -1;
        var hostmem = 0;
        var hostcpu = 0;
        $('#update').removeClass('btn-forbidden');
        $("#recover").removeClass('btn-forbidden');
        $("#register").removeClass('btn-forbidden');
        $("#power").removeClass('btn-forbidden');
        $("#power").removeAttr('disabled');
        $('input[name="hostrow"]:checked').each(function () {
        	powerstatus = $(this).parent().parent().attr("powerstatus");
        	hostmem = $(this).parent().parent().attr("hostmem");
        	hostcpu = $(this).parent().parent().attr("hostcpu");
        });
        if(hostmem!=0 && hostcpu !=0){
        	if(powerstatus == 0){
                $("#startServer").removeClass('btn-forbidden');
                $("#startServer").removeAttr('disabled');
            }else if(powerstatus == 1){
                $("#stopServer").removeClass('btn-forbidden');
                $("#stopServer").removeAttr('disabled');
            }
        }
    }
    if (count == bindcount) {
		$("#remove4pool").removeClass('btn-forbidden');
	} else if (count == unbindcount) {
		$("#add2pool").removeClass('btn-forbidden');
		$("#delete").removeClass('btn-forbidden');
	}
});

$('#create').on('click', function (event) {
    event.preventDefault();
    $('#ServerModalContainer').attr('type', 'new');
    $('#ServerModalContainer').load($(this).attr('url'), '',
        function () {
            $('#ServerModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
});

$('#startServer').on('click', function(event){
	event.preventDefault();
	var hostUuid = '';
	$('input[name="hostrow"]:checked').each(function () {
		hostUuid = $(this).parent().parent().attr('hostid');
    });
	$.ajax({
        type: 'post',
        url: 'PowerAction/StartPowerByhostUuid',
        data: {
       	  hostUuid: hostUuid,
        },
        dataType: 'json',
        complete: function (array) {
        	reloadList(1);
        }
    });
});

$('#stopServer').on('click', function(event){
	event.preventDefault();
	var hostUuid = '';
	$('input[name="hostrow"]:checked').each(function () {
		hostUuid = $(this).parent().parent().attr('hostid');
    });
	$.ajax({
        type: 'post',
        url: 'PowerAction/ShutDownPowerByhostUuid',
        data: {
       	  hostUuid: hostUuid,
        },
        dataType: 'json',
        complete: function (array) {
        	reloadList(1);
        }
    });
});


$('#power').on('click', function (event) {
    event.preventDefault();
    $('#ServerModalContainer').attr('type', 'power');
    $('#ServerModalContainer').load($(this).attr('url'), '',
        function () {
            $('#ServerModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
});

$('#update').on('click', function (event) {
    event.preventDefault();
    $('#ServerModalContainer').attr('type', 'edit');
    $('#ServerModalContainer').load($(this).attr('url'), '',
        function () {
            $('#ServerModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
});

$("#recover").on('click', function(event) {
	event.preventDefault();
    $('#ServerModalContainer').load('admin/modal/recover', '',
        function () {
            $('#ServerModalContainer').modal({
                backdrop: false,
                show: true
            });
    	});
});

$("#register").on('click', function(event) {
	event.preventDefault();
    $('#ServerModalContainer').load('admin/modal/register', '',
        function () {
            $('#ServerModalContainer').modal({
                backdrop: false,
                show: true
            });
    	});
});

$('#tablebody').on('click', '.srdetail', function (event) {
    event.preventDefault();
    var hostuuid = $(this).parent().parent().attr('hostid');
    var hostname = $(this).parent().parent().attr('hostname');
    var url = basePath + 'admin/modal/storageofhost';
    $('#ServerModalContainer').load(url, {
        "hostuuid": hostuuid,
        "hostname": hostname
    }, function () {
        $('#ServerModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#tablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var hostid = $(this).parent().parent().attr('hostid');
    var form = $("<form></form>");
    form.attr("action", "host/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="hostid" value="' + hostid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#add2pool').on('click', function (event) {
    event.preventDefault();
    var boxes = document.getElementsByName("hostrow");
    var count = 1;
    var hostmem = 0;
    var hostcpu = 0;
    var hosttype = '';
    var uuidStr = '[';
    var teshu = '['
    for (var i = 0; i < boxes.length; i++) {
        if (boxes[i].checked == true) {
        	var hostuuid = $(boxes[i]).parent().parent().attr('hostid');
        	hosttype = $(boxes[i]).parent().parent().attr('hosttype');
        	var srsize=$(boxes[i]).parent().parent().attr('srsize');
        	if(hosttype == "beyondDocker"){
            	
        	}else if(hosttype == "beyondCloud"){
        		if(srsize==0){
        			var hostid="host-"+hostuuid.substr(0,8);
            		alert(hostid+"存储为0，请先挂载存储，然后加入资源池！");
            		continue;
            	}
        	}else if(hosttype == "vSphere"){
        		
        	}else if(hosttype == "KVM"){
        		
        	}
    		hostmem = $(boxes[i]).parent().parent().attr('hostmem');
    		hostcpu = $(boxes[i]).parent().parent().attr('hostcpu');
    		if (count == 1) {
    			uuidStr = uuidStr + '"' + hostuuid + '"';
    			count++;
    		} else {
    			uuidStr = uuidStr + ',"' + hostuuid + '"';
    		}
        }
    }
    uuidStr = uuidStr + ']';
    if(hosttype == "beyondDocker"){
    	uuidStr.replace(new RegExp('"', "gm"), '_');
        $('#ServerModalContainer').load("admin/modal/addtopool", {
            "uuidjsonstr": uuidStr,
            "hosttype":hosttype
        }, function () {
            $('#ServerModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
	}else if(hosttype == "beyondCloud"){
		if(hostmem == 0 && hostcpu==0){
	    	alert("请注册服务器，再加入资源池");
	    	return;
	    }
		$.ajax({
	        type: 'get',
	        url: 'HostAction/Issamesr',
	        data: {uuidjsonstr: uuidStr},
	        dataType: 'json',
	        success: function (array) {
	            var obj1 = array[0];
	            array.shift();
	            if (obj1.isSuccess) {
	                uuidStr.replace(new RegExp('"', "gm"), '_');
	                $('#ServerModalContainer').load("admin/modal/addtopool", {
	                    "uuidjsonstr": uuidStr,
	                    "hosttype":hosttype
	                }, function () {
	                    $('#ServerModalContainer').modal({
	                        backdrop: false,
	                        show: true
	                    });
	                });
	            } else {
	                bootbox.dialog({
	                    message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;组建资源池的服务器存储必须一致&nbsp;</div>',
	                    title: "提示",
	                    buttons: {
	                        main: {
	                            label: "确定",
	                            className: "btn-primary",
	                            callback: function () {
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
	          }
	      });
	}else if(hosttype == "vSphere"){
		
	}else if(hosttype == "KVM"){
		
	}
    
});

$('#remove4pool').on('click', function (event) {
    event.preventDefault();
    var boxes = document.getElementsByName("hostrow");
    var infoList = "";
    for (var i = 0; i < boxes.length; i++) {
        if (boxes[i].checked == true) {
            infoList += "[" + $(boxes[i]).parent().parent().attr("hostname")
                + "]&nbsp;";
        }
    }
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;服务器&nbsp;'
            + infoList + '离开资源池?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    for (var i = 0; i < boxes.length; i++) {
                        if (boxes[i].checked == true) {
                            ejectPool($(boxes[i]).parent().parent()
                                .attr("hostid"));
                        }
                    }
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

function ejectPool(hostid) {
    $.ajax({
        type: 'post',
        url: 'HostAction/RemoveFromPool',
        data: {hostuuid: hostid},
        dataType: 'json',
        success: function (array) {
            var obj = array[0];
            if (obj.isSuccess) {
                var thistr = $("#tablebody").find('[hostid="' + hostid
                    + '"]');
                var thistd = thistr.find('[state]');
                thistd.html("<a></a>");
                thistd.attr("state", "unload");
            }
        }
    });
}

function getHostList(page, limit, search) {
    $.ajax({
        type: 'get',
        url: 'HostAction/HostList',
        data: {page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                var totalnum = array[0];
                var totalp = Math.ceil(totalnum / limit);
                options = {
                    totalPages: totalp
                }
                $('#pageDivider').bootstrapPaginator(options);
                pageDisplayUpdate(page, totalp);
                var btable = document.getElementById("tablebody");
                var tableStr = "";
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var hostname = decodeURIComponent(obj.hostname);
                    var hostdesc = decodeURIComponent(obj.hostdesc);
                    var hostid = obj.hostid;
                    var hostip = obj.hostip;
                    var createdate = obj.createdate;
                    var hosttype = obj.hosttype;
                    var hostid = obj.hostid;
                    var hostcpu = obj.hostcpu;
                    var hostmem = obj.hostmem;
                    var powerstatus = obj.powerstatus;
                    var showid = "host-" + hostid.substring(0, 8);
                    var srsize = obj.srsize;
                    var poolid = obj.poolid;
                    var state = "load";
                    var showpoolid = '';
                    if (poolid == "") {
                        state = "unload";
                    }
                    var poolname = decodeURIComponent(obj.poolname);
                    var mytr = '<tr hostid="' + hostid + '"hosttype="' + hosttype + '"hostname="'
                        + hostname + '" hostip="' + hostip + '"hostcpu="' + hostcpu + '"hostmem="' + hostmem + '"hostdesc="' + hostdesc + '" powerstatus="' + powerstatus + '" srsize="' + srsize + '">'
                        + '<td class="rcheck"><input type="checkbox" name="hostrow"></td>'
                        + '<td><a class="id">' + showid + '</a></td>'
                        + '<td>' + hostname + '</td>'
                        + '<td>' + hostip + '</td>'
                        + '<td>' + hostcpu + '&nbsp;核</td>'
                        + '<td>' + hostmem + '&nbsp;MB</td>'
                        + '<td state="' + state + '">' + poolname + '</td>'
                        +'<td><a class="srdetail" size='
                        + srsize
                        + '><span class="glyphicon glyphicon-hdd"></span>&nbsp;&nbsp;'
                        + srsize + '</a></td>'
                        + '<td class="time">' + createdate + '</td></tr>';
                    tableStr += mytr;
                }
                btable.innerHTML = tableStr;
            }
        }
    });
}

$('#delete').on('click', function (event) {
    event.preventDefault();
    var boxes = document.getElementsByName("hostrow");
    var infoList = "";
    for (var i = 0; i < boxes.length; i++) {
        if (boxes[i].checked == true) {
            infoList += "[" + $(boxes[i]).parent().parent().attr("hostname")
                + "]&nbsp;";
        }
    }
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除服务器&nbsp;'
            + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    for (var i = 0; i < boxes.length; i++) {
                        if (boxes[i].checked == true) {
                        	if($(boxes[i]).parent().parent().attr("srsize") >= 1){
                        		bootbox.alert("为了保证存储正常挂载，请先卸载存储再进行删除服务器操作！");
                        		return;
                        	}
                            deleteHost($(boxes[i]).parent().parent()
                                .attr("hostid"), $(boxes[i])
                                .parent().parent().attr("hostname"));
                        }
                    }
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

function deleteHost(hostid, hostname) {
    $.ajax({
        type: 'post',
        url: 'HostAction/Delete',
        data: {hostid: hostid, hostname: hostname},
        dataType: 'json',
        success: function (array) {
            if (array.length == 1) {
                var result = array[0].result;
                if (result) {
                    var thistr = $("#tablebody").find('[hostid="'
                        + hostid + '"]');
                    $(thistr).remove();
                }
            }
        }
    });
}

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='hostrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='hostrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});
