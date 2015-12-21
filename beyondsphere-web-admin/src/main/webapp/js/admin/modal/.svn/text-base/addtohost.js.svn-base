$("#VolumeModalContainer").on("hidden", function () {
    $(this).removeData("modal");
    $(this).children().remove();
});

var options = {
    bootstrapMajorVersion: 3,
    currentPage: 1,
    totalPages: 1,
    numberOfPages: 0,
    onPageClicked: function (e, originalEvent, type, page) {
        getServerList(page, 5, "");
    },
    shouldShowPage: function (type, page, current) {
        switch (type) {
            case "first":
            case "last":
                return false;
            default:
                return true;
        }
    }
}
$('#loadP').bootstrapPaginator(options);

var boxes = document.getElementsByName("srrow");
for (var i = 0; i < boxes.length; i++) {
    if (boxes[i].checked == true) {
        getServerList(1, 5, "", $(boxes[i]).parent().parent().attr("srid"), $(boxes[i]).parent().parent().attr("srname"));
    }
}

$('#load2server').on('click', function (event) {
    event.preventDefault();
    var selected = $('.serverlist').find('.selected');
    var loadHostid = selected.get(0).getAttribute("hostid");
    var boxes = document.getElementsByName("srrow");
    for (var i = 0; i < boxes.length; i++) {
        if (boxes[i].checked == true) {
            load2server(loadHostid, $(boxes[i]).parent().parent().attr("srid"));
        }
    }
    removeAllCheck();
});

$('#cancelload').on('click', function (event) {
    event.preventDefault();
    removeAllCheck();
});

function getServerList(page, limitnum, search, sruuid, srname) {
    $.ajax({
        type: 'get',
        url: 'HostAction/LoadList',
        data: {page: page, limit: limitnum, search: search, sruuid: sruuid},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                if (array.length == 1) {
                    $(".modal-body").html("<div class='alert alert-info' style='margin-bottom:0'>如果存在未注册的服务器，请先注册再添加存储；如果不存在且列表为空，表明所有服务器均已添加存储&nbsp;[" + srname + "]</div>");
                } else {
                    var totalnum = array[0];
                    var totalp = Math.ceil(totalnum / limitnum);
                    options = {
                        totalPages: totalp
                    }
                    $('#loadP').bootstrapPaginator(options);
                    modalDisplayUpdate(page, totalp);
                    var btable = document.getElementById("serverlist");
                    btable.innerHTML = "";
                    var tableStr = "";
                    for (var i = 1; i < array.length; i++) {
                        var obj = array[i];
                        var hostid = obj.hostid;
                        var hostName = decodeURIComponent(obj.hostname);
                        var showuuid = "i-" + hostid.substring(0, 8);
                        if (i == 1) {
                            tableStr = tableStr + '<div class="image-item selected" hostid="'
                                + hostid + '"><div class="image-left">' + hostName + '</div>ID:&nbsp;&nbsp;' + showuuid + '</div>';
                        } else {
                            tableStr = tableStr + '<div class="image-item" hostid="'
                                + hostid + '"><div class="image-left">' + hostName + '</div>ID:&nbsp;&nbsp;' + showuuid + '</div>';
                        }
                    }
                    btable.innerHTML = tableStr;
                }
            }
        }
    });
}

function load2server(hostuuid, sruuid) {
    $.ajax({
        type: 'get',
        url: 'StorageAction/LoadToServer',
        data: {sruuid: sruuid, hostuuid: hostuuid},
        dataType: 'json',
        success: function () {
            
        }
    });
}

$('.serverlist').on('click', '.image-item', function (event) {
    event.preventDefault();
    $('div', $('.serverlist')).removeClass('selected');
    $(this).addClass('selected');
});

function modalDisplayUpdate(current, total) {
    $('#currentPload').html(current);
    $('#totalPload').html(total);
}

function removeAllCheck() {
    var boxes = document.getElementsByName("srrow");
    for (var i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
        $(boxes[i]).change();
    }
}