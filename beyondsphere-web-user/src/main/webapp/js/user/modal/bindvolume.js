$("#VolumeModalContainer").on("hidden", function () {
    $(this).removeData("modal");
    $(this).children().remove();
});

getInstanceList(1, 5, "");

$("#bind2vm").on('click', function (event) {
    event.preventDefault();
    var selected = $('.instancelist').find('.selected');
    var bindVmuuid = selected.get(0).getAttribute("vmuuid");
    $('input[name="volumerow"]:checked').each(function () {
        bind2instance(bindVmuuid, $(this).parent().parent().attr("rowid"));
    });
    removeAllCheck();
});

var options = {
    bootstrapMajorVersion: 3,
    currentPage: 1,
    totalPages: 1,
    numberOfPages: 0,
    onPageClicked: function (e, originalEvent, type, page) {
        getInstanceList(page, 5, "");
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
$('#loadV').bootstrapPaginator(options);

function getInstanceList(page, limit, search) {
    $("#instancelist").html("");
    $.ajax({
        type: 'get',
        url: '/VMAction/VMListOfVolume',
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
            }
            $('#loadV').bootstrapPaginator(options);
            modalPageUpdate(page, totalp);
            var tableStr = "";
            for (var i = 1; i < array.length; i++) {
                var obj = array[i];
                var vmuuid = obj.vmid;
                var vmName = decodeURIComponent(obj.vmname);
                var showuuid = "i-" + vmuuid.substring(0, 8);
                if (i == 1) {
                    tableStr = tableStr + '<div class="image-item selected" vmuuid="'
                        + vmuuid + '"><div class="image-left">' + vmName + '</div>ID:&nbsp;&nbsp;' + showuuid + '</div>';
                } else {
                    tableStr = tableStr + '<div class="image-item" vmuuid="'
                        + vmuuid + '"><div class="image-left">' + vmName + '</div>ID:&nbsp;&nbsp;' + showuuid + '</div>';
                }
            }
            $('#instancelist').html(tableStr);
        }
    });
}

$('.instancelist').on('click', '.image-item', function (event) {
    event.preventDefault();
    $('div', $('.instancelist')).removeClass('selected');
    $(this).addClass('selected');
});

function bind2instance(vmuuid, volumeuuid) {
    var thistr = $('#tablebody').find('[rowid="' + volumeuuid + '"]');
    if (thistr.size() == 1) {
        thistr.find('[name="stateicon"]').removeClass("icon-running").addClass('icon-process');
        thistr.find('[name="stateword"]').text('安装中');
    }
    $.ajax({
        type: 'post',
        url: '/VolumeAction/Bind',
        data: {volumeUuid: volumeuuid, vmUuid: vmuuid},
        dataType: 'json',
        success: function (obj) {
        }
    });
}

function modalPageUpdate(current, total) {
    $('#currentPS').html(current);
    $('#totalPS').html(total);
}

function removeAllCheck() {
    $('input[name="volumerow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}