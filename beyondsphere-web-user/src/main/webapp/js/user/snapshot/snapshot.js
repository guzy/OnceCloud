reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getSnapshotList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="snapshotrow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}

function allDisable() {
    $("#delete").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var total = 0;
    $('input[name="snapshotrow"]:checked').each(function () {
        total++;
    });
    if (total > 0) {
        $("#delete").removeClass('btn-forbidden');
    }
});

function getSnapshotList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: '/SnapshotAction/SnapshotList',
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
                var resourceUuid = obj.resourceUuid;
                var resourceType = obj.resourceType;
                var resourceName = decodeURIComponent(obj.resourceName);
                var snapshotCount = obj.snapshotCount;
                var snapshotSize = obj.snapshotSize.toFixed(2);
                var backupDate = obj.backupDate;
                var showname;
                if (resourceType == "instance") {
                    showname = '<span class="glyphicon glyphicon-cloud"></span>&nbsp;&nbsp;主机';
                } else {
                    showname = '<span class="glyphicon glyphicon-inbox"></span>&nbsp;&nbsp;硬盘';
                }
                var showuuid = "bk-" + resourceUuid.substring(0, 8);
                var thistr = '<tr rsuuid="' + resourceUuid + '" rstype="' + resourceType
                    + '" rsname="' + resourceName + '"><td class="rcheck"><input type="checkbox" name="snapshotrow"></td><td id="aa"><a class="viewDetail" href="javascript:void(0)">' + showuuid + '</a></td><td>'
                    + resourceName + '</td><td state="on"><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">可用</span></td><td>' + showname + '</td><td name="size">' + snapshotSize
                    + '</td><td name="node">' + snapshotCount
                    + '</td><td name="backupdate" class="time">' + decodeURIComponent(backupDate)
                    + '</td></tr>';
                tableStr += thistr;
            }
            $('#tablebody').html(tableStr);
        }
    });
}

$('#delete').on('click', function (event) {
    event.preventDefault();
    showbox(0);
});

function showbox(type) {
    var infoList = getInfoList();
    var infoArray = new Array("删除备份链");
    var showMessage = '';
    var showTitle = '';
    showMessage = '<div class="alert alert-info" style="margin:10px">'
        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;'
        + infoArray[type] + '&nbsp;' + infoList + '?</div>';
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
                    $('input[name="snapshotrow"]:checked').each(function () {
                        var rsuuid = $(this).parent().parent().attr("rsuuid");
                        var rstype = $(this).parent().parent().attr("rstype");
                        if (type == 0) {
                            deleteSnapshot(rsuuid, rstype);
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

function getInfoList() {
    var infoList = "";
    $('input[name="snapshotrow"]:checked').each(function () {
        infoList += "[bk-" + $(this).parent().parent().attr("rsuuid").substring(0, 8) + "]&nbsp;";
    });
    return infoList;
}

function deleteSnapshot(rsuuid, rstype) {
	$("#tablebody").find('[rsuuid="' + rsuuid + '"]').find("[id = 'aa']").html('<a href="javascript:void(0)">' + "bk-" + rsuuid.substring(0, 8) + '</a>');
    $.ajax({
        type: 'get',
        url: '/SnapshotAction/DeleteSnapshotSeries',
        data: {resourceUuid: rsuuid, resourceType: rstype},
        dataType: 'json',
        success: function (obj) {
            if (obj.result == true) {
                $("#tablebody").find('[rsuuid="' + rsuuid + '"]').remove();
                reloadList(1);
            }
        },
        error: function(obj) {
        	$("#tablebody").find('[rsuuid="' + rsuuid + '"]').find("[id = 'aa']").html('<a class = "viewDetail" href="javascript:void(0)">' + "bk-" + rsuuid.substring(0, 8) + '</a>');
        }
    });
}

$('#tablebody').on('click', '.viewDetail', function (event) {
    event.preventDefault();
    var resourceUuid = $(this).parent().parent().attr('rsuuid');
    var resourceName = $(this).parent().parent().attr('rsname');
    var resourceType = $(this).parent().parent().attr('rstype');
    var form = $("<form></form>");
    form.attr("action", "/snapshot/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="resourceUuid" value="' + resourceUuid + '" />');
    form.append(input);
    var input2 = $('<input type="text" name="resourceName" value="' + resourceName + '" />');
    form.append(input2);
    var input3 = $('<input type="text" name="resourceType" value="' + resourceType + '" />');
    form.append(input3);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='snapshotrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='snapshotrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});
