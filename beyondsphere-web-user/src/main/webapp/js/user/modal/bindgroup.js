$(document).ready(function () {

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
    $('#bindPS').bootstrapPaginator(options);
    getInstanceList(1, 5, "");
    
    $("#resourceRemove").on("click", function () {
        removeAllCheck();
    });
    var rsta = [];
    $('#bindGroupToVM').on('click', function (event) {
        event.preventDefault();
        var groupUuid = $('.modal-content').attr('groupUuid');
        $('input[name="grouprow"]:checked').each(function () {
            groupUuid = $(this).parent().parent().attr('rowid');
        });
        var vmboxes = document.getElementsByName("image-item");
        var rsuuidStr = '[';
        var flag = 0;
        rsta.data = [];
        for (var i = 0; i < vmboxes.length; i++) {
            if ($(vmboxes[i]).hasClass('selected')) {
                var data = {};
                flag++;
                var vmuuid = $(vmboxes[i]).attr("vmuuid");
                if (flag == 1) {
                    rsuuidStr = rsuuidStr + vmuuid;
                } else {
                    rsuuidStr = rsuuidStr + ',' + vmuuid;
                }
                data.name = $(vmboxes[i]).attr("vmname");
                data.uuid = vmuuid.substring(0, vmuuid.length - 1);
                rsta.data.push(data);
            }
        }
        rsuuidStr = rsuuidStr + ']';
        bindvn(groupUuid, rsuuidStr);
        removeAllCheck();
        reloadList(1);
    });

    $('#cancelbind').on('click', function (event) {
        event.preventDefault();
        removeAllCheck();
    });

    $('.instancelist').on('click', '.image-item', function (event) {
        event.preventDefault();
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
        var count = 0;
        $('div[name="image-item"]').each(function () {
            if ($(this).hasClass('selected')) {
                count++;
            }
        });
        if (count == 0) {
            $('#bindGroupToVM').attr('disabled', true);
        } else {
            $('#bindGroupToVM').attr('disabled', false);
        }
    });

    
});

function getInstanceList(page, limit, search) {
    $('#instancelist').html("");
    $.ajax({
        type: 'get',
        url: '/GroupAction/VMList',
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
            $('#bindPS').bootstrapPaginator(options);
            pageDisplayUpdate(page, totalp);
            var tableStr = "";
            if (0 != totalnum) {
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var vmuuid = obj.vmid;
                    var showuuid = "i-"+obj.vmid.substr(0, 8);
                    var vmName = decodeURIComponent(obj.vmname);
                    tableStr = tableStr + '<div name="image-item" class="image-item" vmname="' + vmName + '" vmuuid="'
                        + vmuuid + '"><div class="image-left">'
                        + vmName + '</div>ID:&nbsp;&nbsp;' + showuuid + '</div>';
                }
            } else {
                $('#alert').html('没有可选择的资源');
            }
            $('#instancelist').html(tableStr);
        },
        error: function () {
        }
    });
}

function bindvn(groupUuid, rsuuidStr) {
    $.ajax({
        type: 'post',
        url: '/GroupAction/BindGroup',
        data: {groupUuid: groupUuid, rsuuidStr: rsuuidStr},
        dataType: 'text',
        success: function () {
            if ($("#rsTable").html() == '<span class="none">尚无资源</span>') {
                $("#rsTable").html("");
            }
            $.each(rsta.data, function (index, rs) {
                $("#rsTable").append('<div style="padding-left:102px"><a>' + rs.name +
                    '</a><a id="remove-resource" rsUuid="' + rs.uuid + '"><span class="glyphicon glyphicon-remove delete-resource"></span></a></div>');
            });

        },
        error: function () {
        }
    });
}

function pageDisplayUpdate(current, total) {
    $('#currentPS').html(current);
    $('#totalPS').html(total);
}

function removeAllCheck() {
    $('input[name="grouprow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}