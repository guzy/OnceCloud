getSnapshotBasicList();
getSnapshotDetailList();

$('#SnapshotModalContainer').on('hide', function (event) {
    getSnapshotDetailList();
});

$('.btn-refresh').unbind();
$('.btn-refresh').on('click', function (event) {
    event.preventDefault();
    getSnapshotBasicList();
});

$('.bk-refresh').on('click', function (event) {
    event.preventDefault();
    getSnapshotDetailList();
});

$('#snapshot-graph').on('mouseenter', '.new-snapshot', function (event) {
    event.preventDefault();
    $(this).find('.tip').css('display', 'inline-block');
});

$('#snapshot-graph').on('mouseleave', '.new-snapshot', function (event) {
    event.preventDefault();
    $(this).find('.tip').css('display', 'none');
});

$('#snapshot-list').contextMenu({
    selector: 'ul',
    build: function ($trigger, e) {
        return {
            callback: function (key, options) {
                var snapshotId = $(this).attr("snapshotId");
                var snapshotName = $(this).attr("snapshotName");
                if (key == "menu-rollback") {
                    bootbox.dialog({
                        message: '<div class="alert alert-warning" style="margin:10px; color:#c09853"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;回滚备份时，需要注意以下几点:<br/><br/>1. 对主机备份进行回滚会将主机关机；<br/>2. 对硬盘备份进行回滚会将硬盘所挂载的主机关机。<br/><br/>回滚备份&nbsp;[' + snapshotName + ']&nbsp;?</div>',
                        title: "提示",
                        buttons: {
                            main: {
                                label: "确定",
                                className: "btn-primary",
                                callback: function () {
                                    rollbackSnapshot(snapshotId);
                                }
                            },
                            cancel: {
                                label: "取消",
                                className: "btn-default",
                                callback: function () {
                                }
                            }
                        }
                    });
                } else if (key == "menu-delete") {
                    bootbox.dialog({
                        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除备份&nbsp;[' + snapshotName + ']&nbsp;?</div>',
                        title: "提示",
                        buttons: {
                            main: {
                                label: "确定",
                                className: "btn-primary",
                                callback: function () {
                                    deleteOneSnapshot(snapshotId);
                                }
                            },
                            cancel: {
                                label: "取消",
                                className: "btn-default",
                                callback: function () {
                                }
                            }
                        }
                    });
                }
            },
            items: {
                "menu-rollback": {icon: "time", name: "回滚"},
                "menu-delete": {icon: "trash", name: "删除"}
            }
        };
    }
});

function getSnapshotDetailList() {
    var resourceUuid = $('#platformcontent').attr("resourceUuid");
    var resourceType = $('#platformcontent').attr("resourceType");
    $('#snapshot-graph').html("");
    $.ajax({
        type: 'get',
        url: '/SnapshotAction/DetailList',
        data: {resourceUuid: resourceUuid, resourceType: resourceType},
        dataType: 'json',
        success: function (array) {
            var height = 16 + 32 * array.length;
            var svgStr = '<svg width="40" height="' + height + '"><g transform="translate(20,16)">';
            var listStr = '<div class="snapshot-list" id="snapshot-list">';
            listStr = listStr + '<a class="new-snapshot" id="backup" url="/snapshot/create?rsid=null&rstype=null&rsname=null"><span class="tip">新建备份</span></a>';
            for (var i = 0; i < array.length; i++) {
                var obj = array[i];
                var snapshotId = obj.snapshotId;
                var snapshotName = decodeURIComponent(obj.snapshotName);
                var snapshotSize = obj.snapshotSize;
                var backupDate = obj.backupDate;
                var showid = "ss-" + snapshotId.substring(0, 8);
                var title = '<ul snapshotId="' + snapshotId + '" snapshotName="' + snapshotName + '">';
                if (i == 0) {
                    svgStr = svgStr + '<path d="M0,16L0,-10" class="is-head"></path><circle id="' + showid + '" r="5" cx="0" cy="16"></circle>';
                } else {
                    var current = 32 * i - 10;
                    var step = 16 + 32 * i;
                    svgStr = svgStr + '<path d="M0,' + current + 'L0,' + step + '"></path><circle id="' + showid + '" r="5" cx="0" cy="' + step + '"></circle>';
                }
                if (i % 2 == 1) {
                    var title = '<ul snapshotId="' + snapshotId + '" snapshotName="' + snapshotName + '" class="odd">';
                }
                listStr = listStr + title + '<li class="background"></li><li class="id"><span>'
                    + showid + '</span></li><li class="time">' + backupDate + '</li><li>'
                    + snapshotSize.toFixed(2) + '&nbsp;GB</li><li><span>'
                    + snapshotName + '</span>&nbsp;</li></ul>';
            }
            $('#snapshot-graph').html(svgStr + '</g></svg>' + listStr + '</div>');
        }
    });
}

function rollbackSnapshot(id) {
    var rsuuid = $("#platformcontent").attr("resourceUuid");
    var rstype = $("#platformcontent").attr("resourceType");
    $.ajax({
        type: 'post',
        url: '/SnapshotAction/Rollback',
        data: {snapshotId: id, resourceUuid: rsuuid, resourceType: rstype},
        dataType: 'json',
        success: function (obj) {
            if (obj.exist == true) {
                if (obj.result == true) {
                    // for the future
                }
            }
        }
    });
}

function deleteOneSnapshot(id) {
    var rsuuid = $("#platformcontent").attr("resourceUuid");
    var rstype = $("#platformcontent").attr("resourceType");
    $.ajax({
        type: 'post',
        url: '/SnapshotAction/DeleteSnapshot',
        data: {snapshotId: id, resourceUuid: rsuuid, resourceType: rstype},
        dataType: 'json',
        success: function (obj) {
            if (obj.result == true) {
                getSnapshotDetailList();
            }
        }
    });
}

$('#snapshot-graph').on('click', '#backup', function (event) {
    event.preventDefault();
    var thisurl = $(this).attr('url');
    bootbox.dialog({
        className: "bootbox-large",
        message: '<div class="alert alert-warning" style="margin:10px; color:#c09853"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;当对资源进行在线备份时，需要注意以下几点:<br/><br/>1.在线备份是指正在运行的主机或已绑定主机的硬盘；<br/>2. 备份只能捕获在备份任务开始时已经写入磁盘的数据，不包括当时位于内存里的数据；<br/>3. 为了保证数据的完整性，请在创建备份前暂停所有文件的写操作，或进行离线备份。</div>',
        title: "提示",
        buttons: {
            main: {
                label: "创建备份",
                className: "btn-primary",
                callback: function () {
                    $('#SnapshotModalContainer').load(thisurl, '', function () {
                        $('#SnapshotModalContainer').modal({
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
                }
            }
        }
    });
});

function getSnapshotBasicList() {
    var resourceUuid = $('#platformcontent').attr("resourceUuid");
    var resourceType = $('#platformcontent').attr("resourceType");
    $('#basic-list').html("");
    $('#depend-list').html("");
    $.ajax({
        type: 'get',
        url: '/SnapshotAction/BasicList',
        data: {resourceUuid: resourceUuid, resourceType: resourceType},
        dataType: 'json',
        success: function (obj) {
            var resourceName = decodeURIComponent(obj.resourceName);
            var snapshotCount = obj.snapshotCount;
            var snapshotSize = obj.snapshotSize;
            var backupDate = obj.backupDate;
            var showid = "bk-" + resourceUuid.substring(0, 8);
            var backStatus = obj.backStatus;
            var showname;
            var resourceId = '';
            if (resourceType == "instance") {
                showname = '<span class="glyphicon glyphicon-cloud"></span>&nbsp;&nbsp;主机';
                if (backStatus == 0) {
                	resourceId = 'i-' + resourceUuid.substring(0, 8) + '<span style="color:#b94a48">&nbsp;(已销毁)</span>';
                } else {
	                resourceId = '<a class="id" id="rsuuid" type="' + resourceType + '" uuid="' + resourceUuid + '">i-' + resourceUuid.substring(0, 8) + '</a>';
                }
            } else {
                showname = '<span class="glyphicon glyphicon-inbox"></span>&nbsp;&nbsp;硬盘';
                if (backStatus == 0) {
                	resourceId = 'vol-' + resourceUuid.substring(0, 8) + '<span style="color:#b94a48">&nbsp;(已销毁)</span>';
                } else {
	                resourceId = '<a class="id" id="rsuuid" type="' + resourceType + '" uuid="' + resourceUuid + '">vol-' + resourceUuid.substring(0, 8) + '</a>';
                }
            }
            $('#basic-list').html('<dt>备份链&nbsp;ID</dt><dd><a href="javascript:void(0)">'
                + showid + '</a></dd><dt>状态</dt><dd><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">可用</span></dd>'
                + '<dt>总量</dt><dd>' + snapshotSize.toFixed(2) + '&nbsp;GB</dd><dt>备份点</dt><dd>'
                + snapshotCount + '&nbsp;个</dd><dt>距上次备份时间</dt><dd class="time">' + decodeURIComponent(backupDate) + '</dd>');
            $('#depend-list').html('<dt>应用资源</dt><dd resourceUuid="' + resourceUuid + '">'
                + resourceId + '</dd><dt>资源名称</dt><dd>'
                + resourceName + '</dd><dt>资源类型</dt><dd>' + showname + '</dd>');
        }
    });
}