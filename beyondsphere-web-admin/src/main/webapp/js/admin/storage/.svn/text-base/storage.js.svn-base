reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getStorageList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="srrow"]').each(function () {
        $(this).attr("checked", false);
        $(this).change();
    });
}

function allDisable() {
    $("#delete").addClass('btn-forbidden');
    $("#load").addClass('btn-forbidden');
    $("#update").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    allDisable();
    var status = $(this).get(0).checked;
    $('input[name="srrow"]').attr('checked', false);
    $(this).get(0).checked = status;
    if (status == true) {
        $("#load").removeClass('btn-forbidden');
        $('#update').removeClass('btn-forbidden');
        if ($(this).attr('srsize') == 0) {
            $("#delete").removeClass('btn-forbidden');
        } else {
            $("#delete").addClass('btn-forbidden');
        }
    }
});

$('#create').on('click', function (event) {
    event.preventDefault();
    $('#SRModalContainer').attr('type', 'new');
    $('#SRModalContainer').load($(this).attr('url'), '', function () {
        $('#SRModalContainer').modal({
            backdrop: false,
            show: true
        });
        
    });
});

$('#update').on('click', function (event) {
    event.preventDefault();
    $('#SRModalContainer').attr('type', 'edit');
    $('#SRModalContainer').load($(this).attr('url'), '', function () {
        $('#SRModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#load').on('click', function (event) {
    event.preventDefault();
    $('#SRModalContainer').load($(this).attr('url'), '', function () {
        $('#SRModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

function getStorageList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'StorageAction/StorageList',
        data: {page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
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
                    var srid = obj.srid;
                    var srname = decodeURIComponent(obj.srname);
                    var srDesc = decodeURIComponent(obj.srDesc);
                    var srAddress = obj.srAddress;
                    var createDate = obj.createDate;
                    var srType = obj.srType;
                    var srDir = obj.srDir;
                    var showid = "sr-" + srid.substring(0, 8);
                    var srsize = obj.srsize;
                    var thistr = '<tr srid="' + srid + '" srname="' + srname + '" srtype="' + srType + '" srdesc="' + srDesc + '">'
                    	+ '<td class="rcheck"><input type="checkbox" name="srrow" srsize=' + srsize + '></td>' 
                    	+ '<td><a>' + showid + '</a></td>'
                    	+ '<td>' + srname + '</td>'
                    	+ '<td>' + srAddress + '</td>'
                    	+ '<td>' + srDir + '</td>'
                    	+ '<td>' + srType.toUpperCase() + '</td>'
                    	+ '<td class="time">' + createDate + '</td></tr>';
                    tableStr += thistr;
                }
                $('#tablebody').html(tableStr);
            }
        }
    });
}

$('#delete').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="srrow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("srname") + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除存储&nbsp;'
            + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="srrow"]:checked').each(function () {
                        deletesr($(this).parent().parent().attr("srid"), $(this).parent().parent().attr("srname"));
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
});

function deletesr(srid, srname) {
    $.ajax({
        type: 'post',
        url: 'StorageAction/Delete',
        data: {srid: srid, srname: srname},
        dataType: 'json',
        success: function (array) {
            if (array.length == 1) {
                var result = array[0].result;
                if (result) {
                    var thistr = $("#tablebody").find('[srid="' + srid
                        + '"]');
                    $(thistr).remove();
                }
            }
        }
    });
}

$('#unload').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="srrow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("srname") + "]&nbsp;";
    });
    bootbox.dialog({
        className: "bootbox-large",
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;从机架上卸载&nbsp;'
            + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="srrow"]:checked').each(function () {
                        unbind($(this).parent().parent().attr("volumeuuid"));
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
});
