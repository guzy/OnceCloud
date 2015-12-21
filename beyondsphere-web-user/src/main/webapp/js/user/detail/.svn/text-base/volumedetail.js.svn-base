getVolumeBasicList();

$('#VolumeModalContainer').on('hide', function (event) {
    getVolumeBasicList();
});

$('#modify').on('click', function (event) {
    event.preventDefault();
    var url = $("#platformcontent").attr('basePath') + 'user/modal/modify';
    var volumeUuid = $("#platformcontent").attr("volumeUuid");
    var volumeName = $("#volumename").text();
    var volumeDesc = $("#volumedesc").text();
    $('#VolumeModalContainer').load(url, {"modifyType": "volume", "modifyUuid": volumeUuid, "modifyName": volumeName, "modifyDesc": volumeDesc}, function () {
        $('#VolumeModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('.btn-refresh').unbind();
$('.btn-refresh').on('click', function (event) {
    event.preventDefault();
    getVolumeBasicList();
});

$('#depend-list').on('click', '#depenid', function (event) {
    event.preventDefault();
    var uuid = $(this).attr('depenUuid');
    var form = $("<form></form>");
    form.attr("action", "/instance/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="instanceUuid" value="' + uuid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#depend-list').on('click', '#snapshotid', function (event) {
    event.preventDefault();
    var resourceUuid = $(this).attr('rsuuid');
    var resourceName = $(this).attr('rsname');
    var form = $("<form></form>");
    form.attr("action", "/snapshot/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="resourceUuid" value="' + resourceUuid + '" />');
    form.append(input);
    var input2 = $('<input type="text" name="resourceName" value="' + resourceName + '" />');
    form.append(input2);
    var input3 = $('<input type="text" name="resourceType" value="volume" />');
    form.append(input3);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

function getVolumeBasicList() {
    var volumeUuid = $("#platformcontent").attr("volumeUuid");
    $('#basic-list').html("");
    $.ajax({
        type: 'get',
        url: '/VolumeAction/VolumeDetail',
        data: {uuid: volumeUuid},
        dataType: 'json',
        success: function (obj) {
            var volumeName = decodeURIComponent(obj.volumeName);
            var volumeStatus = obj.volumeStatus;
            var stateStr = '';
            var showstr = '';
            var showuuid = "vol-" + volumeUuid.substring(0, 8);
            if (volumeStatus == 2) {
                stateStr = '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td>';
            } else if (volumeStatus == 3) {
                stateStr = '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">安装中</span></td>';
            } else if (volumeStatus == 5) {
                stateStr = '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">卸载中</span></td>';
            } else if (volumeStatus == 6) {
                stateStr = '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">删除中</span></td>';
            } else if (volumeStatus == 1) {
                stateStr = '<td><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">可用</span></td>';
            } else if (volumeStatus == 4) {
                stateStr = '<td><span class="icon-status icon-using" name="stateicon"></span><span name="stateword">使用中</span></td>';
            }
            showstr = "<a class='id'>" + showuuid + '</a>';
            var volumeSize = obj.volumeSize.toFixed(2) + '&nbsp;GB';
            var volumeDependency = obj.volumeDependency;
            var backupDate = obj.backupDate;
            var backup = '&nbsp;';
            if ('&nbsp;' != backupDate) {
                backupDate = decodeURIComponent(backupDate);
                backup = '<a class="id" id="snapshotid" rsuuid="' + volumeUuid + '" rsname="' + volumeName + '">bk-' + volumeUuid.substring(0, 8) + '</a>';
            }
            if ('&nbsp;' != volumeDependency) {
                volumeDependency = '<a class="id" id="depenid" depenUuid="' + volumeDependency + '">i-' + volumeDependency.substring(0, 8) + '</a>';
            }
            var volumeDescription = decodeURIComponent(obj.volumeDescription);
            var createDate = obj.createDate;
            var useDate = decodeURIComponent(obj.useDate);
            $('#basic-list').html('<dt>ID</dt><dd>'
                + showstr + '</dd><dt>名称</dt><dd id="volumename">'
                + volumeName + '</dd><dt>描述</dt><dd id="volumedesc">'
                + volumeDescription + '</dd><dt>状态</dt><dd>'
                + stateStr + '</dd><dt>大小</dt><dd>'
                + volumeSize + '</dd><dt>创建时间</dt><dd class="time">'
                + createDate + '</dd><dt>运行时间</dt><dd class="time">'
                + useDate + '</dd>');
            $('#depend-list').html('<dt>应用主机</dt><dd>'
                + volumeDependency + '</dd><dt>备份链</dt><dd>'
                + backup + '</dd><dt>距上次备份时间</dt><dd class="time">'
                + backupDate + '</dd>');
        }
    });
}