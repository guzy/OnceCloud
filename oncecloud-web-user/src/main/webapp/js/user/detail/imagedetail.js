getImageBasicList();

$('#ImageModalContainer').on('hide', function (event) {
    getImageBasicList();
});

$('#modify').on('click', function (event) {
    event.preventDefault();
    var url = $("#platformcontent").attr('basePath') + 'user/modal/modify';
    var imageUuid = $("#platformcontent").attr("imageUuid");
    var imageName = $("#imagename").text();
    var imageDesc = $("#imagedesc").text();
    $('#ImageModalContainer').load(url, {"modifyType": "image", "modifyUuid": imageUuid, "modifyName": imageName, "modifyDesc": imageDesc}, function () {
        $('#ImageModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('.btn-refresh').unbind();
$('.btn-refresh').on('click', function (event) {
    event.preventDefault();
    getImageBasicList();
});

$('#basic-list').on('click', '#owner', function (event) {
    event.preventDefault();
    var userid = $(this).attr('ownerid');
    var username = $(this).attr('username');
    var form = $("<form></form>");
    form.attr("action", "/user/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="userid" value="' + userid + '" />');
    var input1 = $('<input type="text" name="username" value="' + username + '" />');
    form.append(input);
    form.append(input1);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

function getImageBasicList() {
    var imageUuid = $("#platformcontent").attr("imageUuid");
    $('#basic-list').html("");
    $.ajax({
        type: 'get',
        url: '/ImageAction/Imagedetail',
        data: {uuid: imageUuid},
        dataType: 'json',
        success: function (obj) {
            var imageName = decodeURIComponent(obj.imageName);
            var imageDesc = decodeURIComponent(obj.imageDesc);
            var imageUID = obj.imageUID;
            var imageDisk = obj.imageDisk.toFixed(2) + '&nbsp;GB';
            var poolUuid = obj.poolUuid;
            var hostPlat = decodeURIComponent(obj.imagePlatform);
            var imageStatus = obj.imageStatus;
            var owner = decodeURIComponent(obj.imageUser);
            var stateStr = '';
            var userLevel = $('#platformcontent').attr("userLevel");
            var showuuid = "img-" + imageUuid.substring(0, 8);
            var sight = '公有';
            var showstr = "<a class='showuuid'>" + showuuid + '</a>';
            if (imageStatus == 1) {
                stateStr = '<td><span class="icon-status icon-running" name="stateicon">'
                    + '</span><span name="stateword">可用</span></td>';
            }
            if ('&nbsp;' != poolUuid) {
                poolUuid = '<a class="id" id="hostid" poolUuid="' + poolUuid + '">pool-' + poolUuid.substring(0, 8) + '</a>';
            }
            if (owner != null && userLevel == 0 && imageUID != 1) {
                sight = sight + '</dd><dt>所属用户</dt><dd><a id="owner" ownerid="'
                    + imageUID + '" username="' + owner + '">' + owner + '</a>'
            }
            var createDate = obj.createDate;
            var useDate = decodeURIComponent(obj.useDate);
            $('#basic-list').html('<dt>ID</dt><dd>'
                + showstr + '</dd><dt>名称</dt><dd id="imagename">'
                + imageName + '</dd><dt>描述</dt><dd id="imagedesc">'
                + imageDesc + '</dd><dt>状态</dt><dd>'
                + stateStr + '</dd><dt>容量</dt><dd>'
                + imageDisk + '</dd><dt>可见范围</dt><dd>'
                + sight + '</dd><dt>平台</dt><dd>'
                + hostPlat + '</dd><dt>创建时间</dt><dd class="time">'
                + createDate + '</dd><dt>创建于</dt><dd class="time">'
                + useDate + '</dd>');
            $('#depend-list').html('<dt>资源池</dt><dd>'
                + poolUuid + '</dd>');
        }
    });
}