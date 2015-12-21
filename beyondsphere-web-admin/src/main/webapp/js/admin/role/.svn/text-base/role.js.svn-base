reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getRoleList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="rolerow"]').each(function () {
        $(this).attr("checked", false);
        $(this).change();
    });
}

function allDisable() {
    $("#delete").addClass('btn-forbidden');
    $("#setauth").addClass('btn-forbidden');
    $("#setauth").attr('disabled',"disabled");
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var count = 0;
    $('input[name="rolerow"]:checked').each(function () {
        count++;
    });
    if (count > 0) {
        $("#delete").removeClass('btn-forbidden');
        if(count == 1)
        	{
        	  $("#setauth").removeClass('btn-forbidden');
        	  $("#setauth").removeAttr('disabled');
        	}
    }
});

$('#create').on('click', function (event) {
    event.preventDefault();
    $('#UserModalContainer').attr("type", "new");
    $('#UserModalContainer').load($(this).attr('url'), '', function () {
        $('#UserModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#setauth').on('click', function (event) {
    event.preventDefault();
    $('#UserModalContainer').attr("type", "new");
    $('#UserModalContainer').load($(this).attr('url'), '', function () {
        $('#UserModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

function getRoleList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'RoleAction/RoleList',
        data: {page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                var totalnum = array.length;
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
                for (var i = 0; i < array.length; i++) {
                	var obj = array[i];
                    var roleName = decodeURIComponent(obj.roleName);
                    var roleId = obj.roleId;
                    var roleIntroduce = decodeURIComponent(obj.roleIntroduce);
                    var roleRemarks = decodeURIComponent(obj.roleRemarks);
                    var thistr = '<tr roleid="' + roleId + '" roleName="' + roleName + '"><td class="rcheck"><input type="checkbox" name="rolerow"></td>'
                        + '<td><a class="roleName">' + roleName + '</a></td><td>' + roleIntroduce + '</td><td>' + roleRemarks + '</td></tr>';
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
    $('input[name="rolerow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("roleName") + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-danger" style="margin:10px">删除角色前，必要要确保没有用户还在使用该角色，否则将删除失败！</div><div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除角色&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="rolerow"]:checked').each(function () {
                        deleteRole($(this).parent().parent().attr("roleid"), $(this).parent().parent().attr("roleName"));
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


function deleteRole(roleid, roleName) {
    $.ajax({
        type: 'get',
        url: 'RoleAction/Delete',
        data: {roleid: roleid, roleName: roleName},
        dataType: 'json',
        success: function (obj) {
            if (obj.result) {
                var thistr = $("#tablebody").find('[roleid="' + roleid + '"]');
                $(thistr).remove();
            }
        }
    });
}

