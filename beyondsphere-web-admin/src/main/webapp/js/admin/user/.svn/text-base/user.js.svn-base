reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getUserList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="userrow"]').each(function () {
        $(this).attr("checked", false);
        $(this).change();
    });
}

function allDisable() {
	$("#modify").addClass('btn-forbidden');
    $("#delete").addClass('btn-forbidden');
    $("#location").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var count = 0;
    $('input[name="userrow"]:checked').each(function () {
        count++;
    });
    if (count ==1){
//    	$("#modify").removeClass('btn-forbidden');
    	$("#location").removeClass('btn-forbidden');
    }
    if (count > 0) {
        $("#delete").removeClass('btn-forbidden');
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

$('#modify').on('click', function (event) {
    event.preventDefault();
    $('#UserModalContainer').attr("type", "edit");
    $('#UserModalContainer').load('admin/modal/modifyuserinfo', '', function () {
        $('#UserModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});
$("#location").on('click',function (event){
	event.preventDefault();
	$('#UserModalContainer').attr("type", "edit");
	$('#UserModalContainer').load($(this).attr('url'), '', function () {
        $('#UserModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
	
});
function getUserList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'UserAction/UserList',
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
                    var username = decodeURIComponent(obj.username);
                    var userid = obj.userid;
                    var usercom = decodeURIComponent(obj.usercom);
                    var userdate = obj.userdate;
                    var userlevel = obj.userlevel;
                    var usermail = obj.usermail;
                    var userphone = obj.userphone;
                    var uservoucher = obj.uservoucher;
                    var userrole =decodeURIComponent(obj.userrole);
                    //var areaId =decodeURIComponent(obj.areaId);
                    //alert(areaId+"-----");
                    var voucherStr = '';
                    if (uservoucher == 0) {
                        voucherStr = '<td></td>';
                    } else {
                        voucherStr = '<td>￥' + uservoucher + '<a class="permit" style="margin-left:10px">同意</a><a class="deny" style="margin-left:10px">拒绝</a></td>';
                    }
                    var levelstr = "<a><span class='glyphicon glyphicon-user' style='margin-right:7px'></span>平台用户</a>";
                    if (userlevel == 0) {
                        levelstr = "<a><span class='glyphicon glyphicon-star' style='margin-right:7px'></span>管理员</a>";
                    } else if (userlevel == 2) {
                        levelstr = "<a><span class='glyphicon glyphicon-star' style='margin-right:7px'></span>试用用户</a>";
                    }
                    var thistr = '<tr userid="' + userid + '" username="' + username + '"><td class="rcheck"><input type="checkbox" name="userrow"></td>'
                        + '<td><a class="username">' + username + '</a></td><td>' + usermail + '</td><td>' + userphone + '</td><td>' + usercom + '</td><td>' + levelstr
                        + '</td><td class="time">' + userdate + '</td><td>' + userrole + '</td></tr>';
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
    $('input[name="userrow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("username") + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除用户&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="userrow"]:checked').each(function () {
                        deleteUser($(this).parent().parent().attr("userid"), $(this).parent().parent().attr("username"));
                        
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

$('#tablebody').on('click', '.permit', function (event) {
    var thistd = $(this).parent();
    var userid = thistd.parent().attr("userid");
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;同意该申请？</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $.ajax({
                        type: 'post',
                        url: '/VoucherAction/Confirm',
                        data: {userid: userid},
                        dataType: 'json',
                        success: function (obj) {
                            if (obj.result) {
                                thistd.html("");
                            }
                        },
                        error: function () {
                        }
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

$('#tablebody').on('click', '.deny', function (event) {
    var thistd = $(this).parent();
    var userid = thistd.parent().attr('userid');
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;拒绝该申请？</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $.ajax({
                        type: 'post',
                        url: '/VoucherAction/Deny',
                        data: {userid: userid},
                        dataType: 'json',
                        success: function (obj) {
                            if (obj.result) {
                                thistd.html('');
                            }
                        },
                        error: function () {
                        }
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

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='userrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='userrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});

function deleteUser(userid, username) {
    $.ajax({
        type: 'get',
        url: 'UserAction/Delete',
        data: {userid: userid, username: username},
        dataType: 'json',
        success: function (obj) {
            if (obj.result) {
                var thistr = $("#tablebody").find('[userid="' + userid + '"]');
                $(thistr).remove();
                reloadList(1);
            }
        }
    });
}