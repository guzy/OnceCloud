reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getVolumeList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="volumerow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}

function allDisable() {
    $("#bind").addClass('btn-forbidden');
    $("#unbind").addClass('btn-forbidden');
    $("#delete").addClass('btn-forbidden');
    $("#backup").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var used = 0;
    var process = 0;
    var usable = 0;
    var total = 0;
    $('input[name="volumerow"]:checked').each(function () {
        var stateicon = $(this).parent().parent().find('[name="stateicon"]');
        if (stateicon.hasClass('icon-running')) {
            usable++;
        } else if (stateicon.hasClass('icon-using')) {
            used++;
        } else {
            process++;
        }
        total++;
    });
    if (total > 0 && process == 0) {
        $("#backup").removeClass('btn-forbidden');
        if (used > 0 && usable == 0) {
            $("#unbind").removeClass('btn-forbidden');
        } else if (usable > 0 && used == 0) {
        	if(total == 1){
        		$("#bind").removeClass('btn-forbidden');
        	}
        	$("#delete").removeClass('btn-forbidden');
        } 
    }
});

$('#create, #bind').on('click', function (event) {
    event.preventDefault();
    $('#VolumeModalContainer').load($(this).attr('url'), '', function () {
        $('#VolumeModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

function getInfoList() {
    var infoList = "";
    $('input[name="volumerow"]:checked').each(function () {
        infoList += "[i-" + $(this).parent().parent().attr("rowid").substring(0, 8) + "]&nbsp;";
    });
    return infoList;
}

function showbox(type) {
    var infoList = getInfoList();
    var infoArray = new Array("删除硬盘", "卸载硬盘");
    var showMessage = '';
    var showTitle = '';
    if (type == 1) {
        showMessage = '<div class="alert alert-warning" style="margin:10px; color:#c09853">'
            + '<span class="glyphicon glyphicon-warning-sign"></span>&nbsp;物理卸载硬盘时，需要注意以下几点:'
            + '<br/><br/>1. 卸载硬盘时会丢失位于缓存中的数据；<br/>2. 为保证数据的完整性，最好确保该硬盘在主机的操作系统中处于非加载状态。</div>';
        showTitle = infoArray[type] + '&nbsp;' + infoList + '?';
    } else {
        showMessage = '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;'
            + infoArray[type] + '&nbsp;' + infoList + '?</div>';
        showTitle = "提示";
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
                    $('input[name="volumerow"]:checked').each(function () {
                        var uuid = $(this).parent().parent().attr("rowid");
                        if (type == 0) {
                            deleteVolume(uuid);
                        } else if (type == 1) {
                            unbind(uuid);
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

$('#delete').on('click', function (event) {
    event.preventDefault();
    showbox(0);
});

$('#unbind').on('click', function (event) {
    event.preventDefault();
    showbox(1);
});

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='volumerow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='volumerow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});


$('#platformcontent').on('click', '.backup', function (event) {
    event.preventDefault();
    var thisurl = $(this).attr('url');
    bootbox.dialog({
        className: "bootbox-large",
        message: '<div class="alert alert-warning" style="margin:10px; color:#c09853"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;对已经绑定主机的硬盘进行在线备份时，需要注意以下几点:<br/><br/>1. 备份只能捕获在备份任务开始时已经写入磁盘的数据，不包括当时位于缓存里的数据；<br/>2. 为了保证数据的完整性，请在创建备份前暂停所有文件的写操作，或进行离线备份。</div>',
        title: "提示",
        buttons: {
            main: {
                label: "创建备份",
                className: "btn-primary",
                callback: function () {
                    $('#VolumeModalContainer').load(thisurl, '', function () {
                        $('#VolumeModalContainer').modal({
                            backdrop: false,
                            show: true
                        });
                    });
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

function getVolumeList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: '/VolumeAction/VolumeList',
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
                var volumeid = obj.volumeid;
                var volumename = decodeURIComponent(obj.volumename);
                var volumedepen = obj.volumedepen;
                var depenname = decodeURIComponent(obj.depenname);
                var volState = obj.volState;
                var usedStr = "";
                if (volState == 2) {
                    usedStr = usedStr + '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td>';
                } else if (volState == 3) {
                    usedStr = usedStr + '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">安装中</span></td>';
                } else if (volState == 5) {
                    usedStr = usedStr + '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">卸载中</span></td>';
                } else if (volState == 6) {
                    usedStr = usedStr + '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">删除中</span></td>';
                } else if (volState == 1) {
                    usedStr = usedStr + '<td><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">可用</span></td>';
                } else if (volState == 4) {
                    usedStr = usedStr + '<td><span class="icon-status icon-using" name="stateicon"></span><span name="stateword">使用中</span></td>';
                }
                var volumesize = obj.volumesize.toFixed(2);
                var createdate = obj.createdate;
                var backupdate = obj.backupdate;
                var backupStr = decodeURIComponent(backupdate);
                if (backupdate == "") {
                    backupStr = '<a class="glyphicon glyphicon-camera backup" url="/snapshot/create?rsid=' + volumeid + '&rstype=volume&rsname=' + volumename + '"></a>';
                }
                if (depenname != "") {
                    depenname = '<span class="glyphicon glyphicon-cloud"></span>&nbsp;&nbsp;' + depenname;
                }
                var showid = "vol-" + volumeid.substring(0, 8);
                var showstr = "<a class='id'>" + showid + '</a>';
                tableStr = tableStr + '<tr rowid="' + volumeid + '"><td class="rcheck"><input type="checkbox" name="volumerow"></td><td>'
                    + showstr + '</td><td name="volumename">' + volumename + '</td>'
                    + usedStr + '<td vmuuid="' + volumedepen + '">' + depenname + '</td><td name="size">'
                    + volumesize + '</th><td name="backuptime" class="time">' + backupStr + '</td><td name="createtime" class="time">'
                    + decodeURIComponent(createdate) + '</td></tr>';
            }
            $('#tablebody').html(tableStr);
        }
    });
    allDisable();
}

$('#tablebody').on('click', '.id', function(event){
	event.preventDefault();
    var uuid = $(this).parent().parent().attr('rowid');
    var form = $("<form></form>");
    form.attr("action", "/volume/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="volumeUuid" value="' + uuid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});


function deleteVolume(uuid) {
    var thistr = $('#tablebody').find('[rowid="' + uuid + '"]');
    if (thistr.size() == 1) {
        thistr.find('[name="stateicon"]').removeClass("icon-running").addClass('icon-process');
        thistr.find('[name="stateword"]').text('删除中');
    }
    $.ajax({
        type: 'post',
        url: '/VolumeAction/DeleteVolume',
        data: {volumeUuid: uuid},
        dataType: 'json',
        success: function (obj) {
        }
    });
}

function unbind(uuid) {
    var thistr = $('#tablebody').find('[rowid="' + uuid + '"]');
    if (thistr.size() == 1) {
        thistr.find('[name="stateicon"]').removeClass("icon-using").addClass('icon-process');
        thistr.find('[name="stateword"]').text('卸载中');
    }
    $.ajax({
        type: 'post',
        url: '/VolumeAction/Unbind',
        data: {volumeUuid: uuid},
        dataType: 'json',
        success: function (obj) {
        }
    });
}